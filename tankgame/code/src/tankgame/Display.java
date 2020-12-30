package tankgame;

import animation2d.Bullet;
import animation2d.Tank;
import gamemap.BreakableWall;
import gamemap.PowerUp;
import gamemap.UnBreakableWall;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class Display extends JPanel {

    private BufferedImage bgImg;
    private BufferedImage lifeIcon1, lifeIcon2;

    private int mapWidth, mapHeight, windowWidth, windowHeight, minimapWidth, minimapHeight;

    private ArrayList<UnBreakableWall> unbreak_walls;
    private ArrayList<BreakableWall> break_walls;
    private ArrayList<Bullet> bullets;
    private ArrayList<PowerUp> power_ups;

    private Tank tank1, tank2;

    //player window
    BufferedImage t1_w, t2_w;

    //minimap image
    Image minimap;

    //tanks bound checking
    private int t1_w_x, t1_w_y, t2_w_x, t2_w_y;


    public Display(int mapWidth, int mapHeight, int windowWidth, int windowHeight, String ground_path, String[] imgPaths){
        super();
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        //minimap: 200 x 200
        this.minimapWidth = 200;
        this.minimapHeight = 200;

        //set map size: 1600 x 1600
        //setSize will resize the component to the specified size.
        this.setSize(mapWidth, mapHeight);
        /*setPreferredSize sets the preferred size. The component may not actually be this size
          depending on the size of the container it's in, or if the user re-sized the component manually.*/
        this.setPreferredSize(new Dimension(mapWidth, mapHeight));
        this.bgImg = setImage(ground_path);

        unbreak_walls = new ArrayList<>();
        break_walls = new ArrayList<>();
        bullets = new ArrayList<>();
        power_ups = new ArrayList<>();
    }

    public void paintComponent(Graphics g){
        try{
            getGameImage();
        }catch(ConcurrentModificationException e){
            e.printStackTrace();
        }

        super.paintComponent(g);

        //(img, w,h,observer)
        g.drawImage(t1_w, 0, 0, this); //tank1 window - top left corner of map
        g.drawImage(t2_w, windowWidth/2, 0, this); //tank2 window - bottom right corner of map

        //old minimap
        drawPlayerStatus(g);

        //borders
        g.setColor(Color.ORANGE);
        g.draw3DRect(0,0,(windowWidth / 2) - 1, windowHeight - 22, true);
        g.draw3DRect(windowWidth / 2, 0, (windowWidth / 2) - 1, windowHeight - 2, true);

        //draw minimap
        g.drawImage(minimap, (windowWidth / 2) - (minimapWidth / 2), 0, this);
        g.draw3DRect((windowWidth / 2) - (minimapWidth / 2), 0, minimapWidth, minimapHeight, true);

        //who win?
        if(tank1.getLife() == 0){
            g.setFont(new Font(g.getFont().getFontName(), Font.ROMAN_BASELINE, 84));
            g.drawString("Tank 2 wins", 150, windowHeight/2);
        }
        if(tank2.getLife() == 0){
            g.setFont(new Font(g.getFont().getFontName(), Font.ROMAN_BASELINE, 84));
            g.drawString("Tank 1 wins", 150, windowHeight/2);
        }

    }

    private void drawMapLayout(Graphics2D g) {
        unbreak_walls.forEach((x) -> {
            x.draw(g);
        });
        break_walls.forEach((x) -> {
            x.draw(g);
        });
        power_ups.forEach((x) -> {
            x.draw(g);
        });
    }


    private BufferedImage setImage(String path){
        try{
            return ImageIO.read(getClass().getClassLoader().getResource(path));
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }



    private void drawPlayerStatus(Graphics g) {

        int t1_health = this.tank1.getHealth() * 2;
        int t2_health = this.tank2.getHealth() * 2;

        int t1_lives = this.tank1.getLife();
        int t2_lives = this.tank2.getLife();

        int t1_health_x = 22;
        int t1_health_y = 758;

        int t2_health_x = 581;
        int t2_health_y = 758;

        int health_width = 200;
        int health_height = 1000;

        //offset of the life bar to the bottom of the screen
        int coord_offset = 4;
        int size_offset = 8;

        //Health frame background
        g.setColor(Color.DARK_GRAY);
        g.fillRect(t1_health_x, t1_health_y, health_width, health_height); //tank 1
        g.fillRect(t2_health_x, t2_health_y, health_width, health_height); //tank 2

        //Health color if the health is decreasing
        g.setColor(Color.GRAY);
        g.fillRect(t1_health_x + coord_offset, t1_health_y + coord_offset, health_width - size_offset, health_height - size_offset); //tank1
        g.fillRect(t2_health_x + coord_offset, t2_health_y + coord_offset, health_width - size_offset, health_height - size_offset); //tank2

        //Health available
        g.setColor(Color.GREEN);
        g.fillRect(t1_health_x + coord_offset, t1_health_y + coord_offset, t1_health - size_offset, health_height - size_offset); //tank1
        g.fillRect(t2_health_x + (health_width - t2_health) + coord_offset, t2_health_y + coord_offset, t2_health - size_offset, health_height - size_offset); //tank2

        //tank1 life bar display
        int t1_life_x = 230;
        int t1_life_y = 748;
        int t1_life_offset = 40;
        for(int i = 0; i < t1_lives; i++){
            g.drawImage(lifeIcon1, t1_life_x + (i * t1_life_offset), t1_life_y, this);
        }

        //tank2 life bar display
        int t2_life_x = 538;
        int t2_life_y = 748;
        int t2_life_offset = 40;
        for(int i = 0; i < t2_lives; i++){
            g.drawImage(lifeIcon2, t2_life_x - (i * t2_life_offset), t2_life_y, this);
        }
    }

    private void getGameImage() {
        //create background img
        //The TYPE_INT_ARGB represents Color as an int (4 bytes) with alpha channel in bits 24-31, red channels in 16-23, green in 8-15 and blue in 0-7.
        BufferedImage bimg = new BufferedImage(mapWidth, mapHeight, BufferedImage.TYPE_INT_ARGB);
        //create background img to draw
        Graphics2D g2 = bimg.createGraphics();

        //draw to graphic2d
        drawBackGround(g2);
        drawMapLayout(g2);
        drawTanks(g2);
        drawBullet(g2);

        //create sub_image from graphic2d
        playerViewBoundChecker();
        t1_w = bimg.getSubimage(this.t1_w_x, this.t1_w_y, windowWidth / 2, windowHeight);
        t2_w = bimg.getSubimage(this.t2_w_x, this.t2_w_y, windowWidth / 2, windowHeight);

        //update minimap that's interactive with the tanks
        minimap = bimg.getScaledInstance(minimapWidth, minimapHeight, Image.SCALE_SMOOTH);
    }

    private void playerViewBoundChecker() {
        if((this.t1_w_x = tank1.getTankCenterX() - (windowWidth / 4)) < 0){
            this.t1_w_x = 0;
        }else if(this.t1_w_x >= mapWidth - (windowWidth /2)){
            this.t1_w_x = (mapWidth - (windowWidth /2));
        }

        if((this.t1_w_y = tank1.getTankCenterY() - (windowWidth /2)) < 0){
            this.t1_w_y = 0;
        }else if(this.t1_w_y >= (mapHeight - windowHeight)){
            this.t1_w_y = (mapHeight - windowHeight);
        }

        if((this.t2_w_x = tank2.getTankCenterX() - (windowWidth / 4)) < 0){
            this.t2_w_x = 0;
        }else if(this.t2_w_x >= (mapWidth - (windowWidth / 2))){
            this.t2_w_x = (mapWidth - (windowWidth /2));
        }

        if((this.t2_w_y = tank2.getTankCenterY() - (windowHeight / 2)) < 0){
            this.t2_w_y = 0;
        }else if(this.t2_w_y >= (mapHeight - windowHeight)){
            this.t2_w_y = (mapHeight - windowHeight);
        }
    }

    private synchronized void drawBullet(Graphics2D g){
        Graphics2D g2 = g;
        try{

            bullets.forEach((x) ->{
                if(x.isVisible()){
                    x.drawBullet(this, g2);
                }
            });
        }catch(ConcurrentModificationException e){
            e.printStackTrace();
        }
    }

    private void drawTanks(Graphics2D g2) {

        this.tank1.draw(g2);
        this.tank2.draw(g2);
    }



    private void drawBackGround(Graphics2D g2) {
        for(int i = 0; i < 6; ++i){
            for(int j = 0; j < 8; ++j){
                g2.drawImage(this.bgImg, this.bgImg.getWidth() * i, this.bgImg.getHeight() * j, this);
            }
        }
    }

    public void setBullets(ArrayList<Bullet> b){
        this.bullets = b;
    }

    public void setTanks(Tank t1, Tank t2){
        this.tank1 = t1;
        this.tank2 = t2;
    }

    public void setLifeIcon(BufferedImage img1, BufferedImage img2){
        this.lifeIcon1 = img1;
        this.lifeIcon2 = img2;
    }

    public void setMapObjects(ArrayList<UnBreakableWall> unbreak_wall, ArrayList<BreakableWall> break_wall, ArrayList<PowerUp> power_up){
        this.unbreak_walls = unbreak_wall;
        this.break_walls = break_wall;
        this.power_ups = power_up;
    }

}
