import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Country {

    String country;
    GamePanel gp;

    int initialPopulation;
    int healthyPopulation;
    int infectedPopulation;
    int deadPopulation;

    BufferedImage flag;

    public Country(String country){

        ReadCountries rc = new ReadCountries("src/countries.txt");
        this.country = country;
        initialPopulation = rc.countriesStats.get(country);
        healthyPopulation = initialPopulation;
        infectedPopulation = 0;
        deadPopulation = 0;

        try {
            flag = ImageIO.read(new File("src/Sprites/Country/"+country+".png"));
        } catch (IOException e) {
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
        checkPopulation();
        int infected = getInitialPopulation()/10000;
        if( infected < getHealthyPopulation() ){
            this.infectedPopulation += infected;
            this.healthyPopulation = getInitialPopulation() - getInfectedPopulation() - getDeadPopulation();
        }
        checkPopulation();
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
        checkPopulation();
        int infections = getInitialPopulation()/ratio;
        if(infections < getHealthyPopulation() ){
            this.infectedPopulation += infections;

        }else{
            this.infectedPopulation += getHealthyPopulation();
        }
        this.healthyPopulation = getInitialPopulation() - getInfectedPopulation() - getDeadPopulation();
        this.deadPopulation = getInitialPopulation() - getHealthyPopulation() - getInfectedPopulation();
        checkPopulation();
    }

    public void healPopulation(int ratio){
        int cured = getInfectedPopulation()/ratio;

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

    public void draw(Graphics g){

        g.setColor(Color.yellow);
        g.fillRect(10,10, GamePanel.SCREEN_WIDTH-20, 10);

        g.setColor(Color.green);
        g.fillRect(10,10, (int) ( ((double)this.healthyPopulation /(double)this.initialPopulation)*((double)GamePanel.SCREEN_WIDTH ) -20 ), 10);

        g.setColor(Color.red);
        g.fillRect(GamePanel.SCREEN_WIDTH-20-(int) ( ((double)this.deadPopulation /(double)this.initialPopulation)*((double)GamePanel.SCREEN_WIDTH ) -20 ), 10, (int) ( ((double)this.deadPopulation /(double)this.initialPopulation)*((double)GamePanel.SCREEN_WIDTH ) -10 ), 10 );

        g.setColor(Color.green);
        g.drawString( "Initial\t\t: " + String.valueOf(initialPopulation), 50, 80 );
        g.drawString( "Initial\t\t: " + String.valueOf(getHealthyPopulation() + getInfectedPopulation() + getDeadPopulation()), 50, 90 );
        g.drawString( "Healthy\t\t: " + String.valueOf(healthyPopulation), 50, 100 );
        g.drawString( "Infected\t\t: " + String.valueOf(infectedPopulation), 50, 110 );
        g.drawString( "Dead\t\t: " + String.valueOf(deadPopulation), 50, 120 );

    }

    Thread causeDeaths = new Thread(new Runnable() {
        public void run() {
            while(true) {

                causeDeaths(1000);

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

                if(Virus.totalNumberOfContacts!=0){
                    causeInfections(1000);
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
