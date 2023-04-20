package com.tankwar;

import java.awt.*;

public class Blood extends GameObject{

    private int width=40;
    private int height=40;

    private boolean live = true;

    public void setLive(boolean live) {
        this.live = live;
    }

    public boolean isLive() {
        return live;
    }

    public Blood(int x, int y, GamePanel gamePanel) {
        super(null, x, y, gamePanel);
    }

    public Rectangle getRect(){
        return new Rectangle(x,y,width,height);
    }

    @Override
    public void paintSelf(Graphics g) {
        if(!live){
            return;
        }
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);

        g.setColor(c);
    }

    @Override
    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
    }
}

