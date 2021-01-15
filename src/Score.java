/*
    This class represents the Score of the player
    The score is influenced by many factors, all of which can be seen in the constructor of this class.
 */
public class Score implements Comparable<Score> {

    private String playerName;
    private String country;
    private double score;
    private int kills;
    private int contacts;
    private int alive;
    private int infected;
    private int dead;
    private int nOfGel;
    private int nOfMask;
    private int nOfVaccine;
    private int healed;
    private int timeToDevelop;

    public Score(String playerName, String country, double score, int kills, int contacts, int alive, int infected, int dead, int healed, int nOfGel, int nOfMask, int nOfVaccine, int timeToDevelop) {
        this.playerName = playerName.replace(" ", "-");
        this.country = country;
        this.kills = kills;
        this.contacts = contacts;
        this.alive = alive;
        this.infected = infected;
        this.dead = dead;
        this.healed = healed;
        this.nOfGel = nOfGel;
        this.nOfMask = nOfMask;
        this.nOfVaccine = nOfVaccine;
        this.timeToDevelop = timeToDevelop;

        this.score = calcScore();
    }

    // This methods calculates the score.
    // I came up with the formula thus it might not be the most accurate and fair way of scoring
    public double calcScore(){

        return  ( ( ( (double) (2*healed + alive)/(double) (infected + dead) ) +1 ) * ( (double) (kills+1)/ (double) (contacts+1) ) * ( ( (nOfGel+1) + (nOfMask+1)*2 ) + ( ( (Math.pow(nOfVaccine, 2)*100)+1 ) / ((double)(timeToDevelop/2)+1) )*20) );

        //return 10*( (((double)(this.kills+1))/((double)(this.contacts+1))) * (this.nOfGel+2*this.nOfMask+3*this.nOfVaccine) );
    }

    public double getScore() {
        return score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getCountry() {
        return country;
    }

    public double getKills() {
        return kills;
    }

    public double getContacts() {
        return contacts;
    }

    @Override
    public String toString() {
        return String.format("%-10s %-10s %-10.0f %-10d %-10d %-10d %-10d %-10d %-10d %-10d %-10d %-10d %-10d", playerName, country, score, kills, contacts, alive, infected, dead, healed, nOfGel, nOfMask, nOfVaccine, timeToDevelop);
    }

    // This method is used to print the score on the leaderboard menu
    public String leaderboard(){
        return String.format("%s %s %s %s %s", playerName, country, (int)score, kills, contacts);
    }

    @Override
    public int compareTo(Score o) {
        if(this.score > o.score){
            return -1;
        }else if(this.score < o.score){
            return 1;
        }
        return 0;
    }
}
