package com.tankwar;

import java.awt.*;

/**
 * 爆炸类 用动画演示
 */
public class Explode extends GameObject{

    public Explode(String img,int x,int y,GamePanel gamePanel) {
        super(img,x,y,gamePanel);
    }
    @Override
    public void paintSelf(Graphics g) {
        g.drawImage(img,x-25,y-30,null);//x,y为图片左上角的坐标，调整图片位置，使图片居中
    }

    @Override
    public Rectangle getRec() {
        // TODO Auto-generated method stub
        return null;
    }
}
