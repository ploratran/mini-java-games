package animation2d;

import gamemap.BreakableWall;
import gamemap.UnBreakableWall;
import tankgame.Game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Observable;
import java.util.Observer;

public class Bullet extends Animation implements Observer {

    private final Tank t1 = Game.getTank(1);
    private final Tank t2 = Game.getTank(2);
    private final BufferedImage bullet;
    private int dmg;
    private Game game;
    private int angle;
    public int sizeX;
    public int sizeY;
    public static Tank currentTank;
    public boolean visible;

    public Bullet(Game g, BufferedImage img, int speed, Tank t, int dmg){
        super(img, t.getTankCenterX(), t.getTankCenterY(), speed);
        this.bullet = img;
        this.dmg = dmg;
        this.sizeX = img.getWidth(null);
        this.sizeY = img.getHeight(null);
        currentTank = t;
        this.angle = currentTank.getAngle();
        this.visible = true;
        this.game = g;
    }

    @Override
    public boolean isVisible() {
        return this.visible;
    }

    public void drawBullet(ImageObserver img, Graphics2D graphics){
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), 0, 0);
        graphics.drawImage(bullet, rotation, img);
    }

    private void updateMove() {
        x += Math.round(speed * Math.cos(Math.toRadians(angle)));
        y += Math.round(speed * Math.sin(Math.toRadians(angle)));

        if(t1.collision(this) && (visible) && (currentTank != t1) && (visible) && (t1.coolDown <= 0)){
            visible = false;
            t1.bulletDamage(this.dmg);
        }else if((t2.collision(this)) && (visible) && (currentTank != t2) && (visible) && (t2.coolDown <= 0)){
            visible = false;
            t2.bulletDamage(dmg);
        }else{ //animation2d collide with Breakable and Unbreakable UnBreakableWall
            for(int i = 0; i < game.getWallSize(); ++i){
                UnBreakableWall tempWall = game.getWalls().get(i);
                //unbreakable gamemap collision
                if((tempWall.getWallRectangle().intersects(this.x, this.y, this.width, this.height)) && visible){
                    this.visible = false;
                }

                for(int j = 0; j < game.getBreakableWallSize(); ++j){
                    BreakableWall tempWall2 = game.getBreakableWalls().get(j);
                    if((tempWall2.getWallRectangle().intersects(this.x, this.y, this.width, this.height)) && visible){
                        game.getBreakableWalls().remove(j);
                        tempWall2.breakWall();
                        this.visible = false;
                    }
                }
            }
        }
    }


    @Override
    public void update(Observable o, Object arg) {
        updateMove();
    }


}
