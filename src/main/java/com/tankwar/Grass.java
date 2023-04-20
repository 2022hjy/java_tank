package com.tankwar;

import java.awt.*;

public class Grass extends GameObject {
    public Grass(String img, int x, int y, GamePanel gamePanel) {
        super(img,x,y,gamePanel);
    }

    @Override
    public void paintSelf(Graphics g) {
        g.drawImage(img,x,y,null);
    }

    @Override
    public Rectangle getRec() {
        return null;
    }

}
