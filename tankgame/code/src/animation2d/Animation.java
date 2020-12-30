package animation2d;

import tankgame.GameObject;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class Animation extends GameObject {
    protected int speed;

    //Constructor
    public Animation(){}


    public Animation(BufferedImage img, int x, int y, int speed){
        super(img,null,x,y);
        this.img = img;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.rectangle = new Rectangle(this.width, this.height, x, y);
    }
}

