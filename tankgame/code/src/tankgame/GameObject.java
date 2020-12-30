package tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public abstract class GameObject extends JComponent {
    public Rectangle rectangle;
    public int x, y;
    public int width, height;
    public BufferedImage img;

    //Constructor
    public GameObject(){}

    /*
     * Constructor an ImageObserver without xy-coordinates
     * @param img
     * @param observer
     */
    public GameObject(BufferedImage img, ImageObserver observer){
        this.x = 0; //set x and y to 0
        this.y = 0;
        this.img = img;
        try{
            //get width and height of the image
            this.width = img.getWidth(observer);
            this.height = img.getHeight(observer);
        }catch(NullPointerException e){
            e.printStackTrace();
            this.width = 0;
            this.height = 0;
        }
        this.rectangle.setBounds(this.x, this.y, this.width, this.height);
    }

    /*
     * Constructor an ImageObserver with xy-coordinates
     * @param img
     * @param observer
     * @param x
     * @param y
     */
    public GameObject(BufferedImage img, ImageObserver observer, int x, int y){
        this.x = x;
        this.y = y;
        this.img = img;
        try{
            this.width = img.getWidth(observer);
            this.height = img.getHeight(observer);
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        this.rectangle = new Rectangle(this.width, this.height, x, y);
    }

    /*
     * Constructor an ImageObserver with xy-coordinates, width-height
     * @param img
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public GameObject(BufferedImage img, int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.img = img;
        this.rectangle = new Rectangle(this.width, this.height, x, y);

    }

    public void setLocation(int newX, int newY){
        this.x = newX;
        this.y = newY;
        this.rectangle = new Rectangle(newX, newY);
    }

    public void setLocation(Point newLocation){
        this.x = newLocation.x;
        this.y = newLocation.y;
        this.rectangle.setLocation(newLocation);
    }


    public void setSize(int newWidth, int newHeight){
        this.width = newWidth;
        this.height = newHeight;
        this.rectangle.setSize(newWidth, newHeight);
    }


    public void setSize(Dimension dimension){
        this.rectangle.setSize(dimension);
    }

    //Getters of xy-coordinates and width-length of Rectangle bounded by tank => check for collision
    public int getX(){
        return this.x;
    }

    public int getY(){

        return  this.y;
    }

    public int getWidth(){

        return this.width;
    }

    public  int getHeight(){

        return this.height;
    }

    //
    public Point getLocation(){

        return new Point(this.x, this.y);
    }

    public Dimension getSize(){

        return this.rectangle.getSize();
    }

}

