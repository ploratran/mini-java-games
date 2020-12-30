package KeyInput;

import animation2d.Tank;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener{

    private Tank tank;
    private int leftKey;
    private int rightKey;
    private int upKey;
    private int downKey;
    private int shootKey;

    public KeyInput(Tank t){
        this.tank = t;
        this.leftKey = tank.getLeftKey();
        this.rightKey = tank.getRightKey();
        this.upKey = tank.getUpKey();
        this.downKey = tank.getDownKey();
        this.shootKey = tank.getShootKey();
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == this.leftKey){
            this.tank.toggleLeftPressed();
        }
        if(key == this.rightKey){
            this.tank.toggleRightPressed();
        }
        if(key == this.upKey){
            this.tank.toggleUpPressed();
        }
        if(key == this.downKey){
            this.tank.toggleDownPressed();
        }
        if(key == this.shootKey){
            this.tank.toggleShootOn();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == this.leftKey){
            this.tank.unToggleLeftPressed();
        }
        if(key == this.rightKey){
            this.tank.unToggleRightPressed();
        }
        if(key == this.upKey){
            this.tank.unToggleUpPressed();
        }
        if(key == this.downKey){
            this.tank.unToggleDownPressed();
        }
        if(key == this.shootKey){
            this.tank.unToggleShootOff();
        }
    }
}
