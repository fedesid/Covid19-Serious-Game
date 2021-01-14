import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Country {

    static ArrayList<Country> countriesList = new ArrayList<>();

    String country;
    GamePanel gp;

    int initialPopulation;
    int healthyPopulation;
    int infectedPopulation;
    int deadPopulation;
    int healedPopulation;

    BufferedImage flag;

    public Country(String country){

        ReadCountries rc = new ReadCountries("src/countries.txt");
        this.country = country;
        initialPopulation = rc.countriesStats.get(country);
        healthyPopulation = initialPopulation;
        infectedPopulation = 0;
        deadPopulation = 0;
        healedPopulation = 0;

        try {
            flag = ImageIO.read(new File("src/Sprites/Country/"+country+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{

            causeDeaths.start();
            causeInfections.start();

        }catch (IllegalThreadStateException e){
            e.printStackTrace();
        }


    }

    public void linkGamePanel(GamePanel gp){
        this.gp = gp;
    }

    public void checkPopulation(){
        if(healthyPopulation <= 0){
            gp.endGame();
        }
    }

    public void takeDamage(int damage){
        //checkPopulation();
        int infected = getInitialPopulation()/10000;
        if( infected < getHealthyPopulation() ){
            this.infectedPopulation += infected;
            this.healthyPopulation = getInitialPopulation() - getInfectedPopulation() - getDeadPopulation();
        }
        //checkPopulation();
    }

    public void causeDeaths(int ratio){
        int deaths = getInitialPopulation()/ratio;
        if(deaths < getInfectedPopulation()){
            this.deadPopulation += deaths;

        }else{
            this.deadPopulation += getInfectedPopulation();
        }
        this.infectedPopulation = getInitialPopulation() - getHealthyPopulation() - getDeadPopulation();
        this.healthyPopulation = getInitialPopulation() - getInfectedPopulation() - getDeadPopulation();
    }

    public void causeInfections(int ratio){
        //checkPopulation();
        int infections = getInitialPopulation()/ratio;
        if(infections < getHealthyPopulation() ){
            this.infectedPopulation += infections;

        }else{
            this.infectedPopulation += getHealthyPopulation();
        }
        this.healthyPopulation = getInitialPopulation() - getInfectedPopulation() - getDeadPopulation();
        this.deadPopulation = getInitialPopulation() - getHealthyPopulation() - getInfectedPopulation();
        //checkPopulation();
    }

    public void healPopulation(int ratio){
        int cured = getInfectedPopulation()/ratio;
        this.healedPopulation += cured;
        this.healthyPopulation += cured;
        this.infectedPopulation = getInitialPopulation() - getHealthyPopulation() - getDeadPopulation();
        this.deadPopulation = getInitialPopulation() - getHealthyPopulation() - getInfectedPopulation();

    }

    public int getInitialPopulation() {
        return initialPopulation;
    }

    public int getHealthyPopulation() {
        return healthyPopulation;
    }

    public int getInfectedPopulation() {
        return infectedPopulation;
    }

    public int getDeadPopulation() {
        return deadPopulation;
    }

    public int getHealedPopulation() {
        return healedPopulation;
    }

    public String getCountry() {
        return country;
    }

    public BufferedImage getFlag() {
        return flag;
    }

    public void draw(Graphics g){

        int height = 20;
        int x = GamePanel.SCREEN_WIDTH/3;
        int y = (GamePanel.SCREEN_HEIGHT/20)*1;
        int width = GamePanel.SCREEN_WIDTH - x*2;

        g.setColor(new Color(0xFFD500));
        g.fillRect(x,y, width, height);

        g.setColor(new Color(0x3CA82D));
        g.fillRect(x,y, (int) ( ((double)this.healthyPopulation /(double)this.initialPopulation)*(width)  ), height);

        g.setColor(new Color(0xDE2B2B));
        g.fillRect(x+width - (int) ( ((double)this.deadPopulation /(double)this.initialPopulation)*(width)), y, (int)( ((double)this.deadPopulation /(double)this.initialPopulation)*(width)), height  );

        g.setColor(Color.white);
        g.drawRect(x, y, width, height);

        //Debugging purposes
        /*
        g.setColor(Color.green);
        g.drawString( "Initial\t\t: " + String.valueOf(initialPopulation), 50, 80 );
        g.drawString( "Initial\t\t: " + String.valueOf(getHealthyPopulation() + getInfectedPopulation() + getDeadPopulation()), 50, 90 );
        g.drawString( "Healthy\t\t: " + String.valueOf(healthyPopulation), 50, 100 );
        g.drawString( "Infected\t\t: " + String.valueOf(infectedPopulation), 50, 110 );
        g.drawString( "Dead\t\t: " + String.valueOf(deadPopulation), 50, 120 );
         */

    }

    public void drawFlag(Graphics g, int xpos, int xp, int ypos, int yp, int size){

        Menus.drawCenteredImage(flag, size, xpos, xp, ypos, yp, g);
        Menus.drawCenteredString(this.getCountry(), xpos, xp, ypos+size*2/3, yp+size*2/3, new Font("LATO", Font.ITALIC, 30), new Color(0xFFFFFF), g);

    }

    Thread causeDeaths = new Thread(new Runnable() {
        public void run() {
            while(true) {

                if(gp.state == GamePanel.STATE.GAME){

                    causeDeaths((int) (920-(Math.round(Vaccine.difficultyLvl))*8));

                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    Thread causeInfections = new Thread(new Runnable() {
        public void run() {
            while(true) {

                if(gp.state == GamePanel.STATE.GAME){

                    if(Virus.totalNumberOfContacts!=0){
                        causeInfections((int) (920-(Math.round(Vaccine.difficultyLvl))*8));
                    }

                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

}
