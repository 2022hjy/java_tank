package com.tankwar;

import java.awt.*;

public class Life {
    private int x;
    private int y;
    private int width=55;
    private int height=6;
    private int life;
    public Life() {
        super();
        // TODO Auto-generated constructor stub
    }
    public Life(int x, int y,int life) {
        super();
        this.x = x;
        this.y = y;
        this.life=life;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void draw(Graphics g){

        Color c=g.getColor();
        g.setColor(Color.RED);
        g.drawRect(x,y-10,width,height);
        int w=width*life/100;
        g.fillRect(x,y-10,w,height);
        g.setColor(c);
    }
}
