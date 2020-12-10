import java.io.IOException;
import java.util.TimerTask;
import java.util.*;


public class Spawn extends TimerTask {
    String entity;
    GamePanel GamePanel;
    Random random;
    Virus virus;

    public Spawn(String entity, GamePanel gp){
        this.GamePanel = gp;
        this.entity = entity;
    }

    @Override
    public void run() {
        if(entity.equals("Virus")){
            for(int i=0; i<5; i++){

                try {
                    //spawnVirus();

                    //virus = new Virus(random.nextInt(GamePanel.SCREEN_WIDTH), random.nextInt(GamePanel.SCREEN_WIDTH), random.nextInt(GamePanel.player.getSpeed()-2)+1);
                    //virus.setPlayer(GamePanel.player);
                    //GamePanel.virusOnScreen.add(virus);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }else{
            System.out.println("HELLO");
        }


    }

    public void spawnVirus() throws IOException {
        virus = new Virus(random.nextInt(GamePanel.SCREEN_WIDTH), random.nextInt(GamePanel.SCREEN_WIDTH), random.nextInt(GamePanel.player.getSpeed()-2)+1);
        virus.setPlayer(GamePanel.player);
        GamePanel.virusOnScreen.add(virus);
    }
}
