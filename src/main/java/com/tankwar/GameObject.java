package com.tankwar;

import java.awt.*;

/**
 * 游戏父类 游戏元素的共同属性
 */
public abstract class GameObject {
    //游戏元素图片
    Image img;
    //游戏元素的横纵坐标
    int x;
    int y;
    //游戏元素的宽，高
    int width;
    int height;
    //游戏元素的移动速度
    int speed;
    //游戏元素的移动方向
    Direction direction;
    //引入主界面
    GamePanel gamePanel;

    public GameObject() {

    }
    public GameObject(String img,int x,int y,GamePanel gamePanel) {
        this.img=Toolkit.getDefaultToolkit().getImage(img);
        this.x=x;
        this.y=y;
        this.gamePanel=gamePanel;
    }

    public Image getImag() {
        return img;
    }
    public void setImg(String img) {
        this.img=Toolkit.getDefaultToolkit().getImage(img);
    }


    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x=x;
    }


    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }


    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }


    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }


    public double getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }


    public Direction getDirection() {
        return direction;
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }


    public GamePanel getGamePanel() {
        return gamePanel;
    }
    public void setGamePanel(GamePanel gamepanel) {
        this.gamePanel = gamePanel;
    }

    //继承元素绘制自己的方法
    public abstract void paintSelf(Graphics g);

    //获取当前游戏元素的矩形,是为碰撞检测而写
    public abstract Rectangle getRec();
}