import java.awt.*;
import java.util.Iterator;

public class SelectPlayerGraphics extends Menus {

    Button play, back;

    int btnSize = 300;

    public SelectPlayerGraphics(GamePanel gp){
        super(gp);

        play = new Button("PLAY", (GamePanel.SCREEN_WIDTH/3)-btnSize-50, (GamePanel.SCREEN_HEIGHT/2)+100, btnSize, btnSize/3,80,70,50);
        back = new Button("BACK", 20,20,100,50,20,30,20);

        buttons.add(play);
        buttons.add(back);

        int nOfLines = 12;
        int xunit = (GamePanel.SCREEN_WIDTH/nOfLines);
        int yunit = (GamePanel.SCREEN_HEIGHT/nOfLines);
        int xcount = 5;
        int ycount = 3;
        int xindex = (GamePanel.SCREEN_WIDTH/nOfLines)*xcount;
        int yindex = (GamePanel.SCREEN_HEIGHT/nOfLines)*ycount;

        System.out.println(Country.countriesList.size());
        for(Iterator<Country> countryIterator = Country.countriesList.iterator(); countryIterator.hasNext();){
            Country country = countryIterator.next();

            int xpos = xindex;
            int xposOpposite = xindex+xunit;
            int ypos = yindex;
            int yposOpposite = yindex+yunit;

            CountryGraphics countryGraphics = new CountryGraphics(country.getCountry(), country.getFlag(), xpos, xposOpposite, ypos, yposOpposite, 60);
            CountryGraphics.countryGraphicsList.add(countryGraphics);

            xcount+=2;
            if(xcount >= 10){
                ycount+=2;
                xcount = 5;
            }
            xindex = (GamePanel.SCREEN_WIDTH/nOfLines)*xcount;
            yindex = (GamePanel.SCREEN_HEIGHT/nOfLines)*ycount;
        }

    }

    public void draw(Graphics g){
        super.draw(g);

        int nOfLines = 12;
        for(int i=0; i<=nOfLines; i++){

            g.setColor(new Color(0xB9FF0000, true));
            //g.drawLine((GamePanel.SCREEN_WIDTH/nOfLines)*i, 0, (GamePanel.SCREEN_WIDTH/nOfLines)*i, GamePanel.SCREEN_HEIGHT );
            //g.drawLine(0, (GamePanel.SCREEN_HEIGHT/nOfLines)*i, GamePanel.SCREEN_WIDTH, (GamePanel.SCREEN_HEIGHT/nOfLines)*i);

        }

        g.setColor(Color.white);
        g.drawRect((GamePanel.SCREEN_WIDTH/nOfLines)*4, (GamePanel.SCREEN_HEIGHT/nOfLines)*2, (GamePanel.SCREEN_WIDTH/nOfLines)*7, (GamePanel.SCREEN_HEIGHT/nOfLines)*9);

        for(Iterator<CountryGraphics> countryGraphicsIterator = CountryGraphics.countryGraphicsList.iterator(); countryGraphicsIterator.hasNext();){
            CountryGraphics countryGraphics = countryGraphicsIterator.next();

            countryGraphics.drawSelection(g);

        }

    }

}
