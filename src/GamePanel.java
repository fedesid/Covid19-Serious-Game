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

    public enum STATE{
        MENU,
        GAME,
        LEADERBOARD,
        SELECTION,
        END
    };

    static public STATE state = STATE.MENU;

    public static final int SCREEN_WIDTH = 1200;
    public static final int SCREEN_HEIGHT = 800;
    static final int DELAY = 20;
    boolean running = false;

    Timer timer;
    Random random;
    InputHandler input;
    MouseHandler mouse;
    MouseWheelHandler mouseWheel;
    Inventory inventory;
    Score userScore;
    Country country;
    ReadScore rs;

    MenuGraphics menu;
    LeaderboardGraphics leaderboard;
    SelectPlayerGraphics selection;
    GameOverGraphics gameover;

    JTextField nameInputField;

    boolean moving = false;

    static ArrayList<Bullet> bulletsOnScreen = new ArrayList<>();
    static ArrayList<Virus> virusOnScreen = new ArrayList<>();
    static ArrayList<Item> itemsOnScreen = new ArrayList<>();
    Player player;
    Gel gel;
    Virus virus;

    String username;
    static String usercountry;

    static BufferedImage bg;

    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Image background = toolkit.getImage("src/Sprites/Background/main-pixel-art.gif");

    static {
        try {
            bg = ImageIO.read(new File("src/Sprites/Background/bg.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GamePanel() throws IOException {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(new Color(0x008C1C));
        this.setFocusable(true);

        /*
        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter name");
        username = userInput.nextLine();
        System.out.println("Enter country");
        usercountry = userInput.nextLine();
         */



        rs = new ReadScore("src/score.txt");

        mouse = new MouseHandler(this);
        this.setBackground(Color.black);



        timer = new Timer(DELAY, this);
        timer.start();

        nameInputField = new JTextField(8);
        //textField.setBounds(5, 5, 280, 50); // to get height, set large font
        nameInputField.setFont(nameInputField.getFont().deriveFont(50f));
        this.add(nameInputField, BorderLayout.SOUTH);
        nameInputField.setVisible(true);
        nameInputField.setBackground(new Color(1, true));
        nameInputField.setForeground(new Color(0xFFFFFF));

        //this.startGame();
        //this.addKeyListener(new MyKeyAdapter());

        try{
            enemySpawner.start();
            gelSpawner.start();
            maskSpawner.start();
            vaccineSpawner.start();

        }catch (IllegalThreadStateException e){
            e.printStackTrace();
        }

        ReadCountries rc = new ReadCountries("src/countries.txt");
        for(String key : rc.countriesStats.keySet()){
            Country c = new Country(key);
            Country.countriesList.add(c);
            System.out.println(key);
        }

        // INITIALIZING GAME SCREENS (MENUS)
        menu = new MenuGraphics(this);
        leaderboard = new LeaderboardGraphics(this);
        selection = new SelectPlayerGraphics(this);
        gameover = new GameOverGraphics(this);

    }

    public void startGame() throws IOException {

        itemsOnScreen.removeAll(itemsOnScreen);
        virusOnScreen.removeAll(virusOnScreen);
        bulletsOnScreen.removeAll(bulletsOnScreen);

        inventory = new Inventory(this);
        input = new InputHandler(this);
        mouseWheel = new MouseWheelHandler(this);
        country = new Country(usercountry);
        country.linkGamePanel(this);

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

        //gel = new Gel(1, random.nextInt(SCREEN_WIDTH-20)+10, random.nextInt(SCREEN_HEIGHT-20)+10);
        //Mask mask = new Mask(1, random.nextInt(SCREEN_WIDTH-20)+10, random.nextInt(SCREEN_HEIGHT-20)+10);
        //Vaccine vaccine = new Vaccine(1, random.nextInt(SCREEN_WIDTH-30)+10, random.nextInt(SCREEN_HEIGHT-30)+10);

        //itemsOnScreen.add(gel);
        //itemsOnScreen.add(mask);
        //itemsOnScreen.add(vaccine);

        running = true;
        //spawnTimer = new Timer();

        java.util.Timer t = new java.util.Timer();

    }

    public void endGame(){

        //timer.stop();

        userScore = new Score(player.name, player.country.country, 0, Virus.totalNumberOfVirusesKilled, Virus.totalNumberOfContacts, country.getHealthyPopulation(), country.getInfectedPopulation(), country.getDeadPopulation(), Inventory.items[0].getTotalQuantity(), Inventory.items[1].getTotalQuantity(), Inventory.items[2].getTotalQuantity());
        ReadScore.scores.add(userScore);
        Collections.sort(ReadScore.scores);
        WriteScore wr = new WriteScore("src/score.txt");
        System.out.println("EXIT");
        //System.exit(-1);

        itemsOnScreen.removeAll(itemsOnScreen);
        virusOnScreen.removeAll(virusOnScreen);
        bulletsOnScreen.removeAll(bulletsOnScreen);

        Inventory.clear();
        Virus.clear();

        state = STATE.END;

    }

    public void checkCollision(){

        double distance;

        // top border
        if(player.getYpos() < 0){
            player.setYpos(0);
        }

        // right border
        distance = calcDistance(player.getXpos(), player.getYpos(), SCREEN_WIDTH, player.getYpos());
        if(distance < player.getPlayerSize()){
            player.setXpos(SCREEN_WIDTH - player.getPlayerSize());
        }

        // bottom border
        distance = calcDistance(player.getXpos(), player.getYpos(), player.getXpos(), SCREEN_HEIGHT);
        if(distance < player.getPlayerSize()){
            player.setYpos(SCREEN_HEIGHT - player.getPlayerSize());
        }

        // left border
        if(player.getXpos() < 0){
            player.setXpos(0);
        }
    }

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

    public void checkItem(){

        if( itemsOnScreen.size() != 0 ){
            for(Item item : itemsOnScreen){

                //TODO extrapolate this method (check distance between item and player) you could create a method inside the Item abstract class
                if( item.collision(player) ){

                    System.out.println("CONTACT");
                    System.out.println(item.getClass().toString());

                    //TODO change the way the inventory works. Use classes instead of indexes i.e. Class mask, class gel, class vaccine

                    if(item.getClass().toString().equals("class Gel")){
                        Inventory.items[0].upCurrentCount(5);
                        //itemsOnScreen.add(new Gel(1, random.nextInt(SCREEN_WIDTH-30)+10, random.nextInt(SCREEN_HEIGHT-30)+10));

                    }else if(item.getClass().toString().equals("class Mask")){
                        Inventory.items[1].upCurrentCount(1);
                        //itemsOnScreen.add(new Mask(1, random.nextInt(SCREEN_WIDTH-30)+10, random.nextInt(SCREEN_HEIGHT-30)+10));

                    }else if(item.getClass().toString().equals("class Vaccine")){
                        Inventory.items[2].upCurrentCount(1);
                        //itemsOnScreen.add(new Vaccine(1, random.nextInt(SCREEN_WIDTH-30)+10, random.nextInt(SCREEN_HEIGHT-30)+10));

                    }
                    itemsOnScreen.remove(item);
                    break;

                }

            }
        }

    }

    public void checkVirus(){

        try{

            if(!player.maskOn){
                if(virusOnScreen.size() != 0){
                    int c=0;
                    for(Iterator<Virus> virusIterator = GamePanel.virusOnScreen.iterator(); virusIterator.hasNext();){
                        Virus virus = virusIterator.next();

                        if( virus.collision(player) ){ // collision check
                            // Virus has collided with player
                            country.takeDamage(virus.getDamage());

                            c++;
                        }

                    }

                    if(c!=0){
                        Virus.isInContact = true;
                    }else {
                        Virus.isInContact = false;
                    }

                }else{
                    Virus.isInContact = false;
                }
            }


        }catch (ConcurrentModificationException ignored){

        }

    }

    public void checkBulletCollision(){

        // list that contains all the bullets that did collide with a virus,
        // I keep track of this so that I will be able to remove them from the list of bullets that did not hit
        Collection<Bullet> tempBullet = new ArrayList<>();

        try{
            for(Iterator<Bullet> bulletIterator = GamePanel.bulletsOnScreen.iterator(); bulletIterator.hasNext();){
                Bullet bullet = bulletIterator.next();

                for(Iterator<Virus> virusIterator = GamePanel.virusOnScreen.iterator(); virusIterator.hasNext(); ){
                    Virus virus = virusIterator.next();

                    if(bullet.collision(virus)){

                        virus.takeDamage(bullet.getDamage(), virusIterator);

                        tempBullet.add(bullet);

                    }

                }

            }

            bulletsOnScreen.removeAll(tempBullet);
        }catch (ConcurrentModificationException ignored){

        }

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(background, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);

        draw(g);

    }

    public void draw(Graphics g){

        if(state == STATE.GAME){

            int virusSize = GamePanel.virusOnScreen.size(); // control variable
            // I was getting an error which I think it was caused when a new virus was created and added to the list while the program was inside this loop
            // The error was ConcurrentModificationException
            // I could enclose this part of the code with a try and catch block

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

            player.draw(g);

            if(Virus.isInContact && !player.maskOn){ // Show the user that they are taking damage
                g.setColor(new Color(0x28FF0000, true));
                g.fillRect(0,0,SCREEN_WIDTH, SCREEN_HEIGHT);
            }

            country.draw(g);
            inventory.draw(g);

        }else if(state == STATE.MENU){
            nameInputField.setVisible(false);
            menu.draw(g);

        }else if(state == STATE.LEADERBOARD){
            leaderboard.draw(g);

        }else if(state == STATE.SELECTION){
            selection.draw(g);

        }else if(state == STATE.END){
            gameover.draw(g);

        }

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if(state == STATE.GAME){
            if(running){
                player.movePlayer();
            }

            checkCollision();
            checkItem();
            checkVirus();
            checkBulletCollision();
            country.checkPopulation();

        }else if(state == STATE.MENU ){

        }else if(state == STATE.LEADERBOARD ){

        }

        repaint();

    }

    Thread enemySpawner = new Thread(new Runnable() {
        int nSpawns = 1;
        public void run() {
            while(true) {

                try{

                    if(state == STATE.GAME ){

                        if(nSpawns%2==0){
                            System.out.println("BLOCK");
                            for(int i=0; i<4; i++){
                                virus = new Virus(random.nextInt(SCREEN_WIDTH), random.nextInt(SCREEN_WIDTH), random.nextInt(player.getSpeed()-2)+1);
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
                    Thread.sleep(9000);
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
                    Thread.sleep(5000);
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
                    Thread.sleep(18000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    Thread vaccineSpawner = new Thread(new Runnable() {
        public void run() {
            while(true) {

                if(state == STATE.GAME){

                    Vaccine vaccine = new Vaccine(random.nextInt(SCREEN_WIDTH-100)+50, random.nextInt(SCREEN_HEIGHT-100)+50);

                    GamePanel.itemsOnScreen.add(vaccine);

                }

                try {
                    Thread.sleep(45000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

}