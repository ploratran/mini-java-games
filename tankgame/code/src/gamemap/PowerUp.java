package gamemap;

import animation2d.Tank;
import tankgame.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

public class PowerUp extends Wall implements Observer {

    boolean isPicked = false;

    public PowerUp(BufferedImage img, int x, int y, int width, int height){
        super(img,x,y,width, height);
    }

    public void draw(Graphics2D graphic){
        if(!isPicked){
            graphic.drawImage(this.img, this.x, this.y, this);
        }
    }

    public void updateMove(){
        Tank t1 = Game.getTank(1);
        Tank t2 = Game.getTank(2);

        /*Place healthUp() after isPicked so that function healthUp() is invoked*/
        if(t1.collision(this)){
            isPicked = true;
            t1.healthUp();
        }
        if(t2.collision(this)){
            isPicked = true;
            t2.healthUp();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        updateMove();
    }
}
