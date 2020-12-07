import java.awt.*;

public class Virus extends Entity {

    Player player;
    int size;
    double distance;
    int speed;

    public Virus(int xpos, int ypos, int size, int speed){
        super(xpos, ypos);
        this.size=size;
        this.speed=speed;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    @Override
    public void chase(){

        //distance = GamePanel.calcDistance(xpos, ypos, player.getXpos(), player.getYpos());
        int dx = player.getXpos() - xpos;
        int dy = player.getYpos() - ypos;

        if(dx > 0){
            xpos += 1*speed;
        }else {
            xpos -= 1*speed;
        }

        if(dy > 0){
            ypos += 1*speed;
        }else{
            ypos -= 1*speed;
        }

    }

    @Override
    public void paintComponent(Graphics g) {

    }

    @Override
    public void draw(Graphics g) {

        g.setColor(new Color(0x17000000, true));
        g.fillOval(xpos-100/speed,ypos-100/speed,200/speed,200/speed);
        g.setColor(new Color(0xFF0000));
        g.drawOval(xpos-100/speed,ypos-100/speed,200/speed,200/speed);

        g.setColor(new Color(0x6422BA));

        g.fillOval(xpos-size/2, ypos-size/2, size,size);

        for(int j=0; j<360; j+=45){
            g.fillOval((int) (Math.cos(Math.toRadians(j))*size + xpos), (int) (Math.sin(Math.toRadians(j))*size + ypos), 5, 5);
        }

/*
        for(int i=0; i<360; i+=90){
            g.drawLine( (int)Math.cos(Math.toRadians(i))+xpos, (int)Math.sin(Math.toRadians(i))+ypos, (int)Math.cos(Math.toRadians(i))+10+xpos, (int)Math.sin(Math.toRadians(i))+10+ypos);
        }

 */

        //g.fillOval(xpos, ypos, 20, 30);

    }
}
