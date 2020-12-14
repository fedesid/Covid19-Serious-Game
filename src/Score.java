public class Score {

    private String playerName;
    private String country;
    private int kills;
    private int contacts;
    private int alive;
    private int infected;
    private int dead;

    public Score(String playerName, String country, int kills, int contacts, int alive, int infected, int dead) {
        this.playerName = playerName;
        this.country = country;
        this.kills = kills;
        this.contacts = contacts;
        this.alive = alive;
        this.infected = infected;
        this.dead = dead;
    }

    @Override
    public String toString() {
        return String.format("%-10s %-10s %-10d %-10d %-10d %-10d %-10d", playerName, country, kills, contacts, alive, infected, dead);
    }
}
