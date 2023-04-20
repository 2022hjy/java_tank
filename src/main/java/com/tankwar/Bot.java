package com.tankwar;

import java.awt.*;
import java.util.Random;

public class Bot extends Tank{
    //人机朝一个方向运动时间
    int moveTime=0;
    public Bot(String img, int x, int y, String upImage, String downImage, String leftImage, String rightImage,
               GamePanel gamePanel) {
        super(img, x, y, upImage, downImage, leftImage, rightImage, gamePanel);
    }
    /*
     * 人机移动
     */
    public void go() {
        attack();
        if(moveTime>=20) {
            direction=randomDirection();
            moveTime=0;
        }else{
            moveTime+=1;
        }
        switch(direction) {
            case UP:
                upWard();
                break;
            case DOWN:
                downWard();
                break;
            case RIGHT:
                rightWard();
                break;
            case LEFT:
                leftWard();
                break;
        }
    }
    /*
     * 人机移动随机方向
     */
    public Direction randomDirection() {
        Random r=new Random();
        int rnum=r.nextInt(4);
        switch(rnum) {
            case 0:
                return Direction.UP;
            case 1:
                return Direction.RIGHT;
            case 2:
                return Direction.LEFT;
            default:
                return Direction.DOWN;
        }
    }

    /*
     * 随机攻击
     */
    public void attack() {
        Point p=getHeadPoint();
        Random r=new Random();
        int rnum=r.nextInt(100+GamePanel.level*20);
        if(rnum<GamePanel.level+1) {
            EnemyBullet enemyBullet=new EnemyBullet("src/main/java/images/bullet/bulletYellow.gif",p.x,p.y,direction,gamePanel);
            this.gamePanel.enemyBulletsList.add(enemyBullet);
        }
    }

    /*
     * 绘制人机
     */
    @Override
    public void paintSelf(Graphics g) {
        //todo
        Life life=new Life(x, y, tanklife);
        life.setLife(tanklife);
        life.draw(g);

        g.drawImage(img,x,y,null);
        go();
    }
    @Override
    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
    }

}
