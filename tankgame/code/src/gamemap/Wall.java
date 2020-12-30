package gamemap;

import tankgame.GameObject;

import java.awt.image.BufferedImage;

public class Wall extends GameObject {

    public Wall(BufferedImage img, int x, int y, int width, int height){
        super(img, x, y, width, height);
    }
}
