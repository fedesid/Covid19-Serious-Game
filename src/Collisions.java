import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

// This class Collisions contains all the methods which check for these collision:
// player-borders_of_screen player-items player-virus bullets-virus
// they are pretty self explanatory
public class Collisions {

    public void checkCollision(GamePanel gamePanel){

        double distance;

        // top border
        if(gamePanel.player.getYpos() < 0){
            gamePanel.player.setYpos(0);
        }

        // right border
        distance = GamePanel.calcDistance(gamePanel.player.getXpos(), gamePanel.player.getYpos(), GamePanel.SCREEN_WIDTH, gamePanel.player.getYpos());
        if(distance < gamePanel.player.getPlayerSize()){
            gamePanel.player.setXpos(GamePanel.SCREEN_WIDTH - gamePanel.player.getPlayerSize());
        }

        // bottom border
        distance = GamePanel.calcDistance(gamePanel.player.getXpos(), gamePanel.player.getYpos(), gamePanel.player.getXpos(), GamePanel.SCREEN_HEIGHT);
        if(distance < gamePanel.player.getPlayerSize()){
            gamePanel.player.setYpos(GamePanel.SCREEN_HEIGHT - gamePanel.player.getPlayerSize());
        }

        // left border
        if(gamePanel.player.getXpos() < 0){
            gamePanel.player.setXpos(0);
        }
    }

    public void checkItem(GamePanel gamePanel){

        if( GamePanel.itemsOnScreen.size() != 0 ){
            for(Item item : GamePanel.itemsOnScreen){

                if( item.collision(gamePanel.player) ){

                    System.out.println("CONTACT");
                    System.out.println(item.getClass().toString());

                    if(item.getClass().toString().equals("class Gel")){
                        Inventory.items[0].upCurrentCount(5);

                    }else if(item.getClass().toString().equals("class Mask")){
                        Inventory.items[1].upCurrentCount(1);

                    }else if(item.getClass().toString().equals("class Vaccine")){
                        Inventory.items[2].upCurrentCount(1);

                    }
                    GamePanel.itemsOnScreen.remove(item);
                    break;

                }

            }
        }

    }

    public void checkVirus(GamePanel gamePanel){

        try{

            if(!gamePanel.player.maskOn){
                if(GamePanel.virusOnScreen.size() != 0){
                    int c=0;
                    for(Iterator<Virus> virusIterator = GamePanel.virusOnScreen.iterator(); virusIterator.hasNext();){
                        Virus virus = virusIterator.next();

                        if( virus.collision(gamePanel.player) ){ // collision check
                            // Virus has collided with player
                            gamePanel.country.takeDamage(virus.getDamage());

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

            GamePanel.bulletsOnScreen.removeAll(tempBullet);
        }catch (ConcurrentModificationException ignored){

        }

    }
}
