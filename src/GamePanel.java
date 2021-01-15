import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel implements ActionListener {

    // Different states of the game i.e. "menues"
    public enum STATE{
        MENU,
        GAME,
        LEADERBOARD,
        SELECTION,
        END
    };

    // initializing state to menu so that the game loads that at the start
    static public STATE state = STATE.MENU;

    // window size
    public static final int SCREEN_WIDTH = 1200;
    public static final int SCREEN_HEIGHT = 800;
    static final int DELAY = 20; // game delay time
    boolean running = false;

    static double flashyDeath = 0.1; // this variable is used to make the effect of damage when the health is below 10%

    // Declaring singeltons that I will need later in the program
    Timer timer;
    Random random;
    InputHandler input;
    MouseHandler mouse;
    MouseWheelHandler mouseWheel;
    Inventory inventory;
    Score userScore;
    Country country;
    ReadScore rs;
    Collisions collisions;

    MenuGraphics menu;
    LeaderboardGraphics leaderboard;
    SelectPlayerGraphics selection;
    GameOverGraphics gameover;
    GameplayGraphics gameplay;

    JTextField nameInputField; // name input field

    boolean moving = false;

    // These arrays will contain objects that I need to keep track of
    static ArrayList<Bullet> bulletsOnScreen = new ArrayList<>();
    static ArrayList<Virus> virusOnScreen = new ArrayList<>();
    static ArrayList<Item> itemsOnScreen = new ArrayList<>();
    Player player;
    Gel gel;
    Virus virus;

    // TIME VARIABLES IN MILLISECONDS
    // spawn time of objects in milliseconds
    static int spawnTimeGel = 5000;
    static int spawnTimeMask = 15000;
    static int spawnTimeVaccine = 100;
    static int spawnTimeVirus = 9000;
    static int numberOfVirusSpawned = 4; // number of viruses that spawn every n seconds
    static int roundLength = 150000;
    static long START_TIME;

    // Declaring player name and country
    String username;
    static String usercountry;

    // Images that I use as background in the game
    static BufferedImage emptyBg;
    static BufferedImage mainscreen_bg;
    static BufferedImage leaderboard_bg;
    static BufferedImage playerselection_bg;
    static BufferedImage gameover_bg;

    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Image earth_and_moon = toolkit.getImage("src/Sprites/Background/transparent.gif"); // Animated gif

    static {
        try {
            emptyBg = ImageIO.read(new File("src/Sprites/Background/empty bg.png"));
            mainscreen_bg = ImageIO.read(new File("src/Sprites/Background/mainscreen.png"));
            leaderboard_bg = ImageIO.read(new File("src/Sprites/Background/leaderboard.png"));
            playerselection_bg = ImageIO.read(new File("src/Sprites/Background/playerselection.png"));
            gameover_bg = ImageIO.read(new File("src/Sprites/Background/gameover.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Constructor of GamePanel class
    // Here I initialize the fields that I need to be initialized as soon as the game starts
    public GamePanel() throws IOException {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(new Color(0x00FF32));
        this.setFocusable(true);

        rs = new ReadScore("src/score.txt");

        mouse = new MouseHandler(this);
        this.setBackground(Color.black);

        timer = new Timer(DELAY, this);
        timer.start();

        nameInputField = new JTextField(8);
        nameInputField.setFont(nameInputField.getFont().deriveFont(50f));
        this.add(nameInputField, BorderLayout.SOUTH);
        nameInputField.setVisible(true);
        nameInputField.setBackground(new Color(1, true));
        nameInputField.setForeground(new Color(0xFFFFFF));

        // Starting threads that handle the spawn of items and viruses
        try{
            enemySpawner.start();
            gelSpawner.start();
            maskSpawner.start();
            vaccineSpawner.start();

        }catch (IllegalThreadStateException e){
            e.printStackTrace();
        }

        // Reading the countires and loading them up in an array list
        ReadCountries rc = new ReadCountries("src/countries.txt");
        for(String key : rc.countriesStats.keySet()){
            Country c = new Country(key);
            Country.countriesList.add(c);
            //System.out.println(key);
        }

        // INITIALIZING GAME SCREENS (MENUS)
        menu = new MenuGraphics(this);
        leaderboard = new LeaderboardGraphics(this);
        selection = new SelectPlayerGraphics(this);
        gameover = new GameOverGraphics(this);
        gameplay = new GameplayGraphics(this);

    }

    // Method startGame. When run it initializes all the fields that will be needed during the actual gameplay
    public void startGame() throws IOException {

        itemsOnScreen.removeAll(itemsOnScreen);
        virusOnScreen.removeAll(virusOnScreen);
        bulletsOnScreen.removeAll(bulletsOnScreen);

        inventory = new Inventory(this);
        input = new InputHandler(this);
        mouseWheel = new MouseWheelHandler(this);
        country = new Country(usercountry);
        country.linkGamePanel(this);
        collisions = new Collisions();

        inventory.linkInput(input);
        mouse.linkInput(input);
        mouseWheel.linkInput(input);
        random = new Random();

        player = new Player(username, random.nextInt(SCREEN_WIDTH),random.nextInt(SCREEN_HEIGHT), 40,7, Color.red, input);
        player.linkInput(input);
        player.linkInventory(inventory);
        player.linkCountry(country);

        for(int i=0; i<5; i++){
            Virus virus = new Virus(random.nextInt(SCREEN_WIDTH), random.nextInt(SCREEN_WIDTH), random.nextInt(player.getSpeed()-2)+1);
            virus.setPlayer(player);
            virusOnScreen.add(virus);
            Virus.totalNumberOfViruses++;
        }

        running = true;

        java.util.Timer t = new java.util.Timer();

        START_TIME = System.currentTimeMillis();
        Vaccine.difficultyLvl = 1.2;
        Vaccine.timeToDevelop = 0;
        Vaccine.updateThreshold();
        adjustMask = 0;
        adjustGel = 0;
        adjustKills = 0;
        spawnTimeGel = 5000;
        spawnTimeMask = 15000;
        spawnTimeVaccine = 100;
        spawnTimeVirus = 9000;
        numberOfVirusSpawned = 4;
        roundLength = 150000;
        Vaccine.vaccineIsReady = false;

    }

    // Method endGame. When run it saves the score, writes it on the file and it prepares the fields to a possible new game start
    public void endGame(){

        userScore = new Score(player.name, player.country.country, 0, Virus.totalNumberOfVirusesKilled, Virus.totalNumberOfContacts, country.getHealthyPopulation(), country.getInfectedPopulation(), country.getDeadPopulation(), country.getHealedPopulation(), Inventory.items[0].getTotalQuantity(), Inventory.items[1].getTotalQuantity(), Inventory.items[2].getTotalQuantity(), Vaccine.timeToDevelop);
        ReadScore.scores.add(userScore);
        Collections.sort(ReadScore.scores);
        WriteScore wr = new WriteScore("src/score.txt");
        System.out.println("Game Ended");

        itemsOnScreen.removeAll(itemsOnScreen);
        virusOnScreen.removeAll(virusOnScreen);
        bulletsOnScreen.removeAll(bulletsOnScreen);

        Inventory.clear();
        Virus.clear();

        state = STATE.END;

    }

    // The four methods below are used to calculate distances and angles throughout the program when needed
    // Since I am working on a 2D plane I figured that this might come in handy
    public static double calcAngleSin(int x1, int y1, int x2, int y2){
        double hyp = calcDistance(x1, y1, x2, y2);
        return Math.toDegrees( Math.asin( (y1-y2)/hyp ) ) ;
    }
    public static double calcAngleCos(int x1, int y1, int x2, int y2){
        double hyp = calcDistance(x1, y1, x2, y2);
        return Math.toDegrees( Math.acos( (x1-x2)/hyp )-Math.PI/2 ) ;
    }

    public static double calcDistance(int x1, int y1, int x2, int y2){
        return Math.sqrt( Math.pow(Math.abs(x1-x2) , 2) + Math.pow( Math.abs( y1-y2) , 2) );
    }

    public static double calcSlope(double x1, double y1, double x2, double y2 ){
        System.out.println(Double.valueOf( (y1-y2)/(x2-x1) ) );
        return (y1-y2)/(x2-x1);
    }

    // Main paintComponent method
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(mainscreen_bg, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
        draw(g);

    }

    // Main draw method. Here I draw all the elements through the program
    // I achieve this by calling the individual draw methods of different object when needed
    public void draw(Graphics g){

        // When this statement is true I draw the elements that will be seen during the gameplay
        if(state == STATE.GAME){
            g.drawImage(emptyBg, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null); // draw background
            g.drawImage(earth_and_moon, SCREEN_WIDTH/4, SCREEN_HEIGHT/4, SCREEN_WIDTH/2, SCREEN_HEIGHT/2, null); // draw gif


            // Iterate through the array lists that contain the objects Virus, and Items
            // Thanks to inheritance I can simply call the draw method and it will know exactly what to do
            try{
                for(Iterator<Virus> virusIterator = GamePanel.virusOnScreen.iterator(); virusIterator.hasNext();){
                    Virus virus = virusIterator.next();

                    virus.chase();
                    virus.draw(g);
                }

                for(Iterator<Item> itemIterator = GamePanel.itemsOnScreen.iterator(); itemIterator.hasNext(); ){
                    Item item = itemIterator.next();

                    item.draw(g);
                }

                for(Iterator<Bullet> bulletIterator = GamePanel.bulletsOnScreen.iterator(); bulletIterator.hasNext(); ){
                    Bullet bullet = bulletIterator.next();

                    bullet.move();
                    bullet.draw(g);

                    if( bullet.clear() ){ // deleting the bullets after they leave the screen
                        GamePanel.bulletsOnScreen.remove(bullet);
                        break;
                    }
                }
            }catch (ConcurrentModificationException ignored){

            }

            player.draw(g); // drawing the player

            // When this if statement is true I draw a layer of red on the screen which indicates that the player is below 10% health
            if( (( (double)country.getHealthyPopulation()/(double)country.getInitialPopulation())*100) < 10 ){
                g.setColor(new Color(255,0,0, (int)(100*flashyDeath)));
                g.fillRect(0,0,SCREEN_WIDTH, SCREEN_HEIGHT);
            }

            if(flashyDeath > 0.0){
                flashyDeath += 0.05;
            }
            if(flashyDeath > 1.0){
                flashyDeath = 0.1;
            }

            if(Virus.isInContact && !player.maskOn){ // Show the user that they are taking damage
                g.setColor(new Color(0x28FF0000, true));
                g.fillRect(0,0,SCREEN_WIDTH, SCREEN_HEIGHT);
            }

            country.draw(g);
            inventory.draw(g);
            gameplay.draw(g);

        // drawing the menu when in state menu
        }else if(state == STATE.MENU){
            nameInputField.setVisible(false);
            menu.draw(g);

        // drawing the leaderboard when in state leaderboard
        }else if(state == STATE.LEADERBOARD){
            g.drawImage(leaderboard_bg, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
            leaderboard.draw(g);

        // drawing the player selection menu when inside the player selection menu
        }else if(state == STATE.SELECTION){
            g.drawImage(playerselection_bg, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
            selection.draw(g);

        // drawing the game over screen when in state end
        }else if(state == STATE.END){
            g.drawImage(gameover_bg, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
            gameover.draw(g);

        }

    }

    // variables that I use in the level logic
    static int adjustGel = 0;
    static int adjustMask = 0;
    static int adjustKills = 0;
    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        // While inside the actual gameplay I execute this actions
        if(state == STATE.GAME){
            if(running){
                player.movePlayer();
            }

            // check the collisions between player-borders_of_screen player-items player-virus bullets-virus
            collisions.checkCollision(this);
            collisions.checkItem(this);
            collisions.checkVirus(this);
            collisions.checkBulletCollision();
            country.checkPopulation();

            if( (System.currentTimeMillis() - START_TIME) > roundLength){
                System.out.println("GAME OVER: " + (System.currentTimeMillis() - START_TIME)/1000);
            }

            gameplay = new GameplayGraphics(this); // updates the timer on screen
            Vaccine.updateProgress(Inventory.items[0].getTotalQuantity() - adjustGel, Inventory.items[1].getTotalQuantity()-adjustMask, Virus.totalNumberOfVirusesKilled-adjustKills);

            if(Vaccine.progress >= 1){
                Vaccine.vaccineIsReady = true;
            }

            // When this if statement is true I change the values of some variables making the game harder
            if(Vaccine.vaccineIsReady){
                Vaccine.timeToDevelop = 360 - (int)(GameplayGraphics.timeLeft*360)/GamePanel.roundLength;
                System.out.println("Time to develop: " + Vaccine.timeToDevelop);
                spawnTimeMask -= Vaccine.difficultyLvl*100;
                spawnTimeVaccine = 15000;
                numberOfVirusSpawned += Math.round(Vaccine.difficultyLvl);

                adjustGel = Inventory.items[0].getTotalQuantity();
                adjustMask = Inventory.items[1].getTotalQuantity();
                adjustKills = Virus.totalNumberOfVirusesKilled;

                Vaccine vaccine = new Vaccine(random.nextInt(SCREEN_WIDTH-100)+50, random.nextInt(SCREEN_HEIGHT-100)+50);
                GamePanel.itemsOnScreen.add(vaccine);

                Vaccine.difficultyLvl += 0.5;
                Vaccine.updateThreshold();
                Vaccine.vaccineIsReady = false;
            }

            if(GameplayGraphics.timeLeft < 0){ // end the game when the time left is 0
                endGame();
            }

        }else if(state == STATE.MENU ){

        }else if(state == STATE.LEADERBOARD ){

        }

        repaint();

    }

    // The four threads below are used to spawn things around the screen on random positions at "random" times
    Thread enemySpawner = new Thread(new Runnable() {
        int nSpawns = 1;
        public void run() {
            while(true) {

                try{

                    // I had to make sure that elements were not spawning while outside the game screen
                    if(state == STATE.GAME ){

                        if(nSpawns%2==0){ // once in a while spawn more than 1 virus. The number is controlled by the variable numberOfVirusesSpawned which increases as the game proceeds
                            System.out.println("BLOCK");
                            for(int i=0; i<numberOfVirusSpawned; i++){
                                if(i%2==0){
                                    virus = new Virus(random.nextInt(100)+GamePanel.SCREEN_WIDTH, random.nextInt(GamePanel.SCREEN_HEIGHT), random.nextInt(player.getSpeed()-2)+1);
                                }else{
                                    virus = new Virus(random.nextInt(GamePanel.SCREEN_WIDTH), random.nextInt(100)+GamePanel.SCREEN_HEIGHT, random.nextInt(player.getSpeed()-2)+1);
                                }
                                virus.setPlayer(player);
                                virusOnScreen.add(virus);
                                Virus.totalNumberOfViruses++;
                            }

                        }else {

                            virus = new Virus(random.nextInt(100)+GamePanel.SCREEN_WIDTH, random.nextInt(100)+GamePanel.SCREEN_HEIGHT, random.nextInt(player.getSpeed()-2)+1);
                            virus.setPlayer(player);
                            GamePanel.virusOnScreen.add(virus);
                            nSpawns++;
                            Virus.totalNumberOfViruses++;

                        }

                    }

                }catch (IOException e){
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(spawnTimeVirus);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    Thread gelSpawner = new Thread(new Runnable() {
        public void run() {
            while(true) {

                if(state == STATE.GAME ){

                    Gel gel = new Gel(random.nextInt(SCREEN_WIDTH-100)+50, random.nextInt(SCREEN_HEIGHT-100)+50);

                    GamePanel.itemsOnScreen.add(gel);

                }

                try {
                    Thread.sleep(spawnTimeGel);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    Thread maskSpawner = new Thread(new Runnable() {
        int nSpawns=1;
        public void run() {

            while(true) {

                if(state == STATE.GAME){

                    Mask mask = new Mask(random.nextInt(SCREEN_WIDTH-100)+50, random.nextInt(SCREEN_HEIGHT-100)+50);

                    GamePanel.itemsOnScreen.add(mask);
                    nSpawns++;

                }

                try {
                    Thread.sleep(spawnTimeMask);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    Thread vaccineSpawner = new Thread(new Runnable() {
        public void run() {
            while(true) {

                if(state == STATE.GAME && Vaccine.vaccineIsReady){

                    //Vaccine vaccine = new Vaccine(random.nextInt(SCREEN_WIDTH-100)+50, random.nextInt(SCREEN_HEIGHT-100)+50);

                    //GamePanel.itemsOnScreen.add(vaccine);

                }

                try {
                    Thread.sleep(spawnTimeVaccine);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

}