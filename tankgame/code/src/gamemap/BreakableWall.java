package gamemap;

import animation2d.Tank;
import tankgame.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Observable;
import java.util.Observer;

public class BreakableWall extends UnBreakableWall implements Observer {

    Rectangle wall_rect;
    private int width, height;
    private boolean destroyed = false; // wall is not destroyed by bullet


    public BreakableWall(BufferedImage img, int x, int y, int width, int height){
        super(img, x, y, width, height);
        this.width = img.getWidth();
        this.height = img.getHeight();
        wall_rect = new Rectangle(x,y,width, height);
    }

    public void breakWall(){
        destroyed = true;
    }

    public void draw(Graphics graphics, ImageObserver observer){
        graphics.drawImage(this.img, this.x, this.y, observer);
    }

    public void updateMove(){
        if(!destroyed){
            Tank t1 = Game.getTank(1);
            Tank t2 = Game.getTank(2);

            if(t1.collision(this)){
                if(t1.x > (this.x)){
                    t1.x -= 3;
                }
                if(t1.x < (this.x)){
                    t1.x -= 3;
                }
                if(t1.y > (this.y)){
                    t1.y += 3;
                }
                if(t1.y < (this.y)){
                    t1.y -= 3;
                }
            }

            if(t2.collision(this)){
                if(t2.x > (this.x)){
                    t2.x += 3;
                }
                if(t2.x < (this.x)){
                    t2.x -= 3;
                }
                if(t2.y > (this.y)){
                    t2.y += 3;
                }
                if(t2.y < (this.y)){
                    t2.y -= 3;
                }
            }
        }
    }
    @Override
    public void update(Observable o, Object arg) {
        updateMove();
    }
}

