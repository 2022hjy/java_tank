package com.tankwar;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PlayerOne extends Tank {

    private boolean up=false;
    private boolean down=false;
    private boolean left=false;
    private boolean right=false;

    public PlayerOne(String img, int x, int y, String upImage, String downImage, String leftImage, String rightImage,
                     GamePanel gamePanel) {
        super(img, x, y, upImage, downImage, leftImage, rightImage, gamePanel);
        // TODO Auto-generated constructor stub
    }

    /*
     * 按下键盘时坦克持续运动
     */
    public void keyPressed(KeyEvent e) {
        int key=e.getKeyCode();
        switch(key) {
            case KeyEvent.VK_A:
                left=true;
                Music.playBackground();
                break;
            case KeyEvent.VK_D:
                right=true;
                Music.playBackground();
                break;
            case KeyEvent.VK_W:
                up=true;
                Music.playBackground();
                break;
            case KeyEvent.VK_S:
                down=true;
                Music.playBackground();
                break;
            case KeyEvent.VK_SPACE:
                this.attack();

                break;
            default:

                break;
        }
    }
    /*
     * 松开键盘时，坦克停止运动
     */
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_A:
                left = false;
                Music.moveStop();//关音乐
                break;
            case KeyEvent.VK_S:
                down = false;
                Music.moveStop();
                break;
            case KeyEvent.VK_D:
                right = false;
                Music.moveStop();
                break;
            case KeyEvent.VK_W:
                up = false;
                Music.moveStop();
                break;
            default:
                break;
        }
    }

    /*
     * 坦克移动
     */
    public void move() {
        if(left) {
            leftWard();
        }else if(right) {
            rightWard();
        }else if (up) {
            upWard();
        }else if (down) {
            downWard();
        }else {
            return;
        }
    }

    @Override
    public void paintSelf(Graphics g) {
        //todo
        Life life=new Life(x, y, tanklife);
        life.setLife(tanklife);
        life.draw(g);
        eatBlood();
        g.drawImage(img,x,y,null);
        move();
    }



    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
    }

}
