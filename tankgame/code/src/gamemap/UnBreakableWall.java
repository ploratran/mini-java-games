package gamemap;

import animation2d.Tank;
import tankgame.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

public class UnBreakableWall extends Wall implements Observer {

    Rectangle wall_rect;
    private int width, height;

    public UnBreakableWall(BufferedImage img, int x, int y, int width, int height){
        super(img, x, y, width, height);
        this.width = img.getWidth();
        this.height = img.getHeight();
        wall_rect = new Rectangle(x, y, width, height);
    }

    public void draw(Graphics graphics){

        graphics.drawImage(img, this.x, this.y, this.width, this.height, this);
    }

    public Rectangle getWallRectangle() {
        return wall_rect;
    }


    public void updateMove(){

        Tank t1 = Game.getTank(1);
        Tank t2 = Game.getTank(2);

        if(t1.collision(this)){
            if(t1.x > (this.x)){
                t1.x += 3;
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

    @Override
    public void update(Observable o, Object arg) {

        updateMove();
    }
}
