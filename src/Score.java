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

    public Score(String playerName, String country, double score, int kills, int contacts, int alive, int infected, int dead, int nOfGel, int nOfMask, int nOfVaccine) {
        this.playerName = playerName.replace(" ", "-");
        this.country = country;
        this.kills = kills;
        this.contacts = contacts;
        this.alive = alive;
        this.infected = infected;
        this.dead = dead;
        this.nOfGel = nOfGel;
        this.nOfMask = nOfMask;
        this.nOfVaccine = nOfVaccine;

        this.score = calcScore();
    }

    public double calcScore(){

        return 10*( (((double)(this.kills+1))/((double)(this.contacts+1))) * (this.nOfGel+2*this.nOfMask+3*this.nOfVaccine) );
    }

    public double getScore() {
        return score;
    }

    @Override
    public String toString() {
        return String.format("%-10s %-10s %-10.0f %-10d %-10d %-10d %-10d %-10d %-10d %-10d %-10d", playerName, country, score, kills, contacts, alive, infected, dead, nOfGel, nOfMask, nOfVaccine);
    }

    public String leaderboard(){
        return String.format("%-10s %-10s %-10.0f %-10d %-10d", playerName, country, score, kills, contacts);
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
