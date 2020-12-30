package tankgame;

import KeyInput.KeyInput;
import animation2d.Bullet;
import animation2d.Tank;
import gamemap.BreakableWall;
import gamemap.PowerUp;
import gamemap.UnBreakableWall;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Game implements Runnable{

    //initialize window dimensions and title: height, width, title
    private String frame_title;
    private int frame_width, frame_height;
    private int map_width, map_height;

    //Game Window
    private JFrame frame;

    //Assets
    private String ground_path, unBreakWall_path, breakWall_path, powerUp_path, life_path, life1_path, life2_path, tank1_path, tank2_path, bullet_path;
    private String img_path[];

    //map
    private final int ROWS = 25;
    private final int COLUMNS = 25;
    private int[][] map;

    //Observable
    private final GameObservable observable;

    //JAVAFX Swing
    private Display display;


    //initialize Thread
    private Thread thread;
    private boolean running = false;

    //Game Objects
    private ArrayList<UnBreakableWall> walls;
    private ArrayList<BreakableWall> breakWalls;
    private ArrayList<Bullet> bullets;
    private ArrayList<PowerUp> pUps;

    //2 players initializations
    private static Tank tank1;
    private static Tank tank2;
    private KeyInput keyInput1;
    private KeyInput keyInput2;

    public Game() {
        this.observable = new GameObservable();
    }


    @Override
    public void run() {
        init();
        try {
            while(running) {
                this.observable.setChanged();
                this.observable.notifyObservers();
                tick();
                render();

                Thread.sleep(1000/144);
            }
        } catch (InterruptedException e) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, e);
        }

        stop();
    }

    private void tick(){
        //setup projectile for bullets in the display
        this.display.setBullets(bullets);
    }

    private void render(){
        this.display.repaint();
    }

    private void init(){

        setGameFrame();
        setResourcePaths();

        //initialize game display
        this.display = new Display(map_width, map_height, frame_width, frame_height, ground_path, img_path);

        setupMap();
        setTanks();
        //setUpFrame has to be last statement or else nothing is drawn to panel
        setupFrame();

    }


    //start & stop thread
    public synchronized void start(){

        if(running) {
            return;
        }

        running = true; //set running is true
        thread = new Thread(this); // pass Game() class
        thread.start(); //start thread by calling run()
    }

    public synchronized void stop(){

        if(!running){
            return;
        }

        running = false; //stop running

        try{
            thread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    private void setGameFrame(){
        this.frame_title = "Tank War";
        this.frame_width = 800; //Frame: 700 x 720
        this.frame_height = 822;
        this.map_width = 1600; //Map: 1200 x 1200
        this.map_height = 1600;
    }

    private void setResourcePaths(){

        ground_path = "tile.png";
        unBreakWall_path = "unbreak_wall.png";
        breakWall_path = "break_wall.png";
        powerUp_path = "powerUps.png";
//        life_path = "life.png";
        life1_path = "life_p1.gif";
        life2_path = "life_p2.gif";
        tank1_path = "tank_green.png";
        tank2_path = "tank_red.png";
        bullet_path = "bullet.gif";

        //0: empty, 1: empty, 2: unbreakWall,  3: breakWall,  4: powerUp, 5: bullet
        img_path = new String[]{tank1_path, tank2_path, unBreakWall_path, breakWall_path, powerUp_path, bullet_path};
        //img_path = new String[]{tank1_path, tank2_path, unBreakWall_path, breakWall_path, powerUp_path, life_path, bullet_path};

    }

    public void setupMap(){
        createMap();
        createMapObjects();
    }

    private void createMap(){
        //map: 25 x 25

        //0: empty, 1: empty, 2: unbreakWall,  3: breakWall,  4: powerUp, 5: bullet
        this.map = new int[][]{
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, // 0
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2}, // 1
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 2}, // 2
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2}, // 3
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2}, // 4
                {2, 0, 0, 0, 0, 0, 3, 3, 3, 0, 0, 0, 0, 2, 0, 0, 0, 3, 3, 3, 0, 0, 0, 0, 2}, // 5
                {2, 0, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 2, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 2}, // 6
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2}, // 7
                {2, 0, 0, 0, 0, 0, 3, 3, 3, 0, 0, 0, 0, 2, 0, 0, 0, 3, 3, 3, 0, 0, 0, 0, 2}, // 8
                {2, 0, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 2, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 2}, // 9
                {2, 0, 0, 0, 0, 3, 0, 4, 0, 3, 0, 0, 0, 0, 0, 0, 3, 0, 4, 0, 3, 0, 0, 0, 2}, // 10
                {2, 0, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 2}, // 11
                {2, 0, 0, 0, 0, 0, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 0, 0, 0, 0, 2}, // 12
                {2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2}, // 13
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2}, // 14
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2}, // 15
                {2, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 2}, // 16
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 2}, // 17
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 2}, // 18
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2}, // 19
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2}, // 20
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 2}, // 21
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2}, // 22
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2}, // 23
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, // 24
        };
    }

    private void createMapObjects() {
        walls = new ArrayList<>();
        breakWalls = new ArrayList<>();
        pUps = new ArrayList<>();
        BufferedImage objectImage;
        int cell_size = 64;
        int extra = 32;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                // 0: empty, 1: empty, 2: wall, 3: breakable wall, 4: health, 5: life, 6: bullet

                //Bounded - Breakable wall
                if(this.map[row][col] == 2){
                    objectImage = setImagePath(img_path[2]);
                    //System.out.println("print: " + objectImage.getWidth() + objectImage.getWidth() + cell_size);
                    walls.add(new UnBreakableWall(objectImage, col*cell_size, row*cell_size, objectImage.getWidth(), objectImage.getHeight()));
                    walls.add(new UnBreakableWall(objectImage, (col*cell_size) + extra, row*cell_size, objectImage.getWidth(), objectImage.getHeight()));
                    walls.add(new UnBreakableWall(objectImage, col*cell_size, (row*cell_size) + extra, objectImage.getWidth(), objectImage.getHeight()));
                    walls.add(new UnBreakableWall(objectImage, (col*cell_size) + extra, (row*cell_size) + extra, objectImage.getWidth(), objectImage.getHeight()));
                }

                //breakable wall
                if(this.map[row][col] == 3){
                    objectImage = setImagePath(img_path[3]);
                    breakWalls.add(new BreakableWall(objectImage, col*cell_size, row*cell_size, objectImage.getWidth(), objectImage.getHeight()));
                    breakWalls.add(new BreakableWall(objectImage, (col*cell_size) + extra, row*cell_size, objectImage.getWidth(), objectImage.getHeight()));
                    breakWalls.add(new BreakableWall(objectImage, col*cell_size, (row*cell_size) + extra, objectImage.getWidth(), objectImage.getHeight()));
                    breakWalls.add(new BreakableWall(objectImage, (col*cell_size) + extra, (row*cell_size) + extra, objectImage.getWidth(), objectImage.getHeight()));
                }

                //powerup
                if(this.map[row][col] == 4){
                    objectImage = setImagePath(img_path[4]);
                    pUps.add(new PowerUp(objectImage, (col*cell_size) + (extra/2), (row*cell_size) + (extra/2), objectImage.getWidth(), objectImage.getHeight()));
                }
            }
        }

        // add each object to the Observable
        walls.forEach((x) -> {
            this.observable.addObserver(x);
        });
        breakWalls.forEach((x) -> {
            this.observable.addObserver(x);
        });
        pUps.forEach((x)->{
            this.observable.addObserver(x);
        });


        // add each object to the Scene
        this.display.setMapObjects(this.walls, this.breakWalls, this.pUps);
    }

    private void setTanks(){
        BufferedImage tank1_img = setImagePath(img_path[0]);
        BufferedImage tank2_img = setImagePath(img_path[1]);

        //set initial coordinates of each tanks when game fist launches
        int tank1_x = 150;  //top left corner of map
        int tank1_y = 150;
        int tank2_x = 1400; //bottom right corner of map
        int tank2_y = 1400;
        int tank2_angle = 180;
        int tank_speed = 1;

        // public Tank(Game game, BufferedImage img, int x, int y, int speed, int left, int right, int up, int down, int shootKey)
        tank1 = new Tank(this, tank1_img, tank1_x, tank1_y, tank_speed, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_SPACE);
        tank2 = new Tank(this, tank2_img, tank2_x, tank2_y, tank_speed, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_SHIFT);

        ////tank2 faces inwards when game first launches
        tank2.setAngle(tank2_angle);

        //reset tank after losing life
        tank1.setOtherTank(tank2);
        tank2.setOtherTank(tank1);

        //connect key inputs with tanks
        this.keyInput1 = new KeyInput(tank1);
        this.keyInput2 = new KeyInput(tank2);

        //add tanks to observer list
        observable.addObserver(tank1);
        observable.addObserver(tank2);

        //add tanks to display
        this.display.setTanks(tank1, tank2);

        //initialize bullets
        this.bullets = new ArrayList<>();

        //set life icons
        this.display.setLifeIcon(setImagePath(this.life1_path), setImagePath(this.life2_path));


    }




    private void setupFrame(){
        //initialize new frame with title
        frame =  new JFrame();

        // GAME WINDOW
        //set title
        this.frame.setTitle(frame_title);
        //set size of frame
        this.frame.setSize(frame_width, frame_height);
        //set frame dimension
        this.frame.setPreferredSize(new Dimension(frame_width, frame_height));
        //set the game window so that user can resize it --> false for fixed screen size
        this.frame.setResizable(false);
        //set Location so that when the user open the screen is at the center of the screen
        this.frame.setLocationRelativeTo(null);
        //close the game properly when hitting the 'X' button on the left side of the game window
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.add(this.display);

        //jFrame by default is invisible --> make it visible when open the game
        this.frame.setLocationRelativeTo(null);

        this.frame.addKeyListener(keyInput1);
        this.frame.addKeyListener(keyInput2);

        //display frame to screen
        this.frame.pack();
        this.frame.setVisible(true);
    }


    public int getMapWidth() {
        return this.map_width;
    }

    public int getMapHeight() {
        return this.map_height;
    }

    //Use in Tank class to get the tank id (1: tank1, 2: tank2)
    public static Tank getTank(int tankNumber) {
        if(tankNumber == 1){
            return tank1;
        }else if(tankNumber == 2){
            return tank2;
        }else{
            return null;
        }
    }

    //access in Bullet.java
    public ArrayList<UnBreakableWall> getWalls(){
        return this.walls;
    }

    public int getWallSize(){
        return walls.size();
    }

    //get bullet image and bullet in Bullet.java
    public BufferedImage getBulletImg(){
        BufferedImage bullet = setImagePath(img_path[5]);
        return bullet;
    }

    public ArrayList<Bullet> getBullets(){
        return bullets;
    }

    //add Bullet to Observable and Observer
    public void addBulletToObservable(Bullet bullet){
        this.observable.addObserver(bullet);
    }

    //get breakable wall and its size
    public ArrayList<BreakableWall> getBreakableWalls(){
        return this.breakWalls;
    }

    public int getBreakableWallSize(){
        return breakWalls.size();
    }

    //Set image from its string filepath
    public BufferedImage setImagePath(String filepath){
        BufferedImage img = null;

        try{

            img = ImageIO.read(getClass().getClassLoader().getResource(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }
}

