package animation2d;

import tankgame.Game;
import tankgame.GameObject;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

public class Tank extends Animation implements Observer {

    private Tank tank1, tank2;
    public int coolDown = 0;
    //initial health is 100%
    public int health = 100;
    public int life = 3;
    private int angle = 0;
    private int mapSizeX, mapSizeY;
    public int spwanPointX, spawnPointY;
    private int left, right, up, down;
    private int shootKey;
    private int shootCoolDown = 0;
    private boolean shoot;
    private boolean UpPressed, DownPressed, RightPressed, LeftPressed;
    private Game game;
    private boolean isDead;

    //Constructor
    public Tank(){}

    public Tank(Game game, BufferedImage img, int x, int y, int speed, int left, int right, int up, int down, int shootKey){
        super(img, x, y, speed);
        this.left = left;
        this.right = right;
        this.up = up;
        this.down = down;
        this.shootKey = shootKey;
        this.LeftPressed = false;
        this.RightPressed = false;
        this.UpPressed = false;
        this.DownPressed = false;
        this.shoot = false;
        this.spwanPointX = x;
        this.spawnPointY = y;
        this.game = game;
        /* setBounds reshapes the bounding rectangle for this component.
         * @param  x: coordinate of the upper left corner of the rectangle
         * @param  y: coordinate of the upper left corner of the rectangle
         * @param width: the width of the rectangle
         * @param height: the height of the rectangle
         */
        this.setBounds(0, 0, 10, 10); //setBounds(x, y, width, height)
        this.mapSizeX = game.getMapWidth();
        this.mapSizeY = game.getMapHeight();
    }

    /*
     * Method to decrease tank health after enemy shoot
     * @param dmg: taking damage by other tank shoots a bullet that collide.
     * Access in Bullet.java class
     */
    public void bulletDamage(int dmg){
        //health decrease after each shoot from enemy
        if(coolDown <= 0){
            this.health -= dmg;
        }
    }

    //This method use to regenerate health after each tank dies until liveCount is 0
    public void healthUp(){
        if(this.health < 100){
            this.health = 100;
        }
    }

    //@anthony code
    public void toggleUpPressed() {
        this.UpPressed = true;
    }

    public void toggleDownPressed() {
        this.DownPressed = true;
    }

    public void toggleRightPressed() {
        this.RightPressed = true;
    }

    public void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    public void unToggleUpPressed() {
        this.UpPressed = false;
    }

    public void unToggleDownPressed() {
        this.DownPressed = false;
    }

    public void unToggleRightPressed() {
        this.RightPressed = false;
    }

    public void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    public void toggleShootOn(){
        this.shoot = true;
    }

    public void unToggleShootOff(){
        this.shoot = false;
    }


    //SETTER and GETTER of Angle
    //set angle of tank2 facing inward when game first starts
    public void setAngle(int angle){
        this.angle = angle;
    }

    //use for Bullet.java class
    public int  getAngle(){
        return this.angle;
    }

    //set tank respawn after it loses back to the original location
    public void setOtherTank(Tank t){
        this.tank1 = new Tank();
        this.tank1 = this;
        this.tank2 = new Tank();
        this.tank2 = t;
    }

    //return the center-XY point from the tank
    public int getTankCenterX(){
        return x + ((img.getWidth(null)) / 2);
    }

    public int getTankCenterY(){
        return y + (img.getHeight(null) / 2);
    }

    //return current health of tank
    public int getHealth(){
        return health;
    }

    public int getLife(){
        return this.life;
    }

    //get Keys
    public int getUpKey(){
        return this.up;
    }

    public int getDownKey(){
        return this.down;
    }

    public int getLeftKey(){
        return this.left;
    }

    public int getRightKey(){
        return this.right;
    }

    public int getShootKey(){
        return this.shootKey;
    }

    //Check for collision of 2 tanks
    public boolean collision(GameObject object) {
        rectangle = new Rectangle(this.x, this.y, this.width, this.height);
        Rectangle rectangle1 = new Rectangle (object.getX(), object.getY(), object.getWidth(), object.getHeight());
        if(this.rectangle.intersects(rectangle1) && (!isDead)) {
            return true;
        }
        return false;
    }

    //Draw using Graphics2D class to draw the tanks
    public void draw(Graphics2D graphic){
        tank1 = Game.getTank(1);
        tank2 = Game.getTank(2);

        this.shootCoolDown -= 1;
        if(this.health <= 0){
            isDead = true;
        }
        if((health > 0) && (coolDown == 0) && (life > 0)){
            isDead = false;
            //@anthony code
            AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
            rotation.rotate(Math.toRadians(angle), img.getWidth(null) / 2, img.getHeight(null) / 2);
            graphic.drawImage(this.img, rotation, null);

            if(tank1.collision(tank2)){

                if(tank1.x > x){
                    tank1.x += speed * 1;
                    tank2.x -= speed * 1;

                }else if(tank1.x < x){
                    tank1.x -= speed * 1;
                    tank2.x += speed * 1;
                }

                if(tank1.y > y){
                    tank1.y += speed * 1;
                    tank2.y -= speed * 1;
                }else if(tank1.y < y){
                    tank1.y -= speed * 1;
                    tank2.y += speed * 1;
                }
            }
        }else if((isDead) && (coolDown == 0) && (life > 0)){ //tank still alive
            coolDown = 30; //player coolDown right after death

            //check if tank is cooling down, health regenerates back to 100, tank is still alive
            if(--life >= 0){
                if(life > 0){
                    health = 100;
                }
            }

            isDead = false;

            //lose tank re-alive at the current xy-coordinates
            x = spwanPointX;
            y = spawnPointY;
        }else{ //cool down decrease after each lose
            coolDown -= 1;
        }
    }

    /* FIX ME */
    public void updateMove(){
        if(LeftPressed){
            angle -= 1;
        }
        if(RightPressed){
            angle += 1;
        }
        if(UpPressed){
            x = (int) (x + Math.round((speed * Math.cos(Math.toRadians(angle)))));
            y = (int) (y + Math.round(speed * Math.sin(Math.toRadians(angle))));
            checkBoundaryLimit();
        }
        if(DownPressed){
            x = (int) (x - Math.round((speed * Math.cos(Math.toRadians(angle)))));
            y = (int) (y - Math.round(speed * Math.sin(Math.toRadians(angle))));
            checkBoundaryLimit();
        }
        if(angle == -1){
            angle = 359;
        }else if(angle == 361){
            angle = 1;
        }

        if(coolDown > 0){
            LeftPressed = false;
            RightPressed = false;
            UpPressed = false;
            DownPressed = false;
        }
    }

    private void checkBoundaryLimit() {
        if(x < 0){
            x = 0;
        }
        if(x >= mapSizeX){
            x = mapSizeX;
        }
        if(y < 0){
            y = 0;
        }
        if(y >= mapSizeY){
            y = mapSizeY;
        }
    }

    /* FIX ME */
    private void shoot(Tank t) {
        //public Bullet(Game g, BufferedImage img, int speed, Tank t, int dmg){}
        if(shoot && (shootCoolDown <= 0) && (coolDown <= 0) && (life > 0)){
            Bullet newBullet = new Bullet(this.game, game.getBulletImg(), 5, this, 10);
            game.getBullets().add(newBullet);
            game.addBulletToObservable(newBullet);
            this.shootCoolDown = 10;
        }
    }

    /* FIX ME */
    @Override
    public void update(Observable o, Object arg) {
        shoot(this);
        updateMove();
    }


}
