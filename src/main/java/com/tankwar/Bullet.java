package com.tankwar;

import java.awt.*;
import java.util.List;

/**
 * 玩家子弹
 */
public class Bullet extends GameObject {
    //长宽
    private int width=10;
    private int height=10;
    //速度
    private int speed=7;
    //方向
    Direction direction;
    //初始化子弹
    public Bullet(String img, int x, int y, Direction direction, GamePanel gamePanel) {
        super(img,x,y,gamePanel);
        this.direction=direction;
    }

    /*
     * 子弹运动方向
     */
    public void go() {
        switch(direction) {
            case UP:
                upWard();
                break;
            case DOWN:
                downWard();
                break;
            case LEFT:
                leftWard();
                break;
            case RIGHT:
                rightWard();
                break;
        }
    }

    /*
     * 子弹运动坐标改变
     */
    private void rightWard() {
        // TODO Auto-generated method stub
        x+=speed;
    }

    private void downWard() {
        // TODO Auto-generated method stub
        y+=speed;
    }

    private void leftWard() {
        // TODO Auto-generated method stub
        x-=speed;
    }

    private void upWard() {
        // TODO Auto-generated method stub
        y-=speed;
    }

    /*
     * 子弹与坦克碰撞检测
     */
    public void hitBot() {
        //矩形类
        Rectangle next=this.getRec();
        List<Bot>bots=this.gamePanel.botList;
        //子弹和人机
        int lifelevel=50;
        for(Bot bot:bots) {
            if(bot.getRec().intersects(next)) {
                this.gamePanel.removeList.add(this);
                //todo
                if(bot.tanklife==lifelevel){
                    Explode[]arr=new Explode[7];
                    for(int i=0;i<4;i++) {
                        Explode explode=new Explode("src/main/java/images/explodeArray/explode" +(i+1)+".png", x, y, this.gamePanel);
                        this.gamePanel.explodeList.add(explode);
                    }
                    Music.explodePlay();
                    this.gamePanel.botList.remove(bot);

                }else if (bot.tanklife>lifelevel){
                    Music.wallPlay();
                    bot.tanklife-=lifelevel;
                }

                break;
            }
        }
    }

    /*
     * 子弹与子弹碰撞检测
     */
    public void hitEnemyBullet() {
        Rectangle next=this.getRec();
        List<EnemyBullet>enemyBullets=this.gamePanel.enemyBulletsList;
        for(EnemyBullet enemyBullet:enemyBullets) {
            if(enemyBullet.getRec().intersects(next)) {
                Music.wallPlay();
                this.gamePanel.removeList2.add(enemyBullet);
                this.gamePanel.removeList.add(this);
                break;
            }
        }
    }

    /*
     * 子弹和土墙碰撞
     */
    public void hitWall() {
        Rectangle next=this.getRec();
        List<Wall>walls=this.gamePanel.wallList;
        for(Wall w:walls) {
            if(w.getRec().intersects(next)) {
                Music.wallPlay();
                this.gamePanel.wallList.remove(w);
                this.gamePanel.removeList.add(this);
                break;
            }
        }
    }

    /*
     * 子弹与Fe
     */

    public void hitFe() {
        Rectangle next=this.getRec();
        List<Fe>fes=this.gamePanel.feList;
        for(Fe f:fes) {
            if(f.getRec().intersects(next)) {
                Music.wallPlay();
                this.gamePanel.removeList.add(this);
                break;
            }
        }
    }

    /*
     * 移动到边界
     */

    public void moveToBorder() {
        if(x<0||x>this.gamePanel.getWidth()) {
            this.gamePanel.removeList.add(this);
        }
        if(y<0||y>this.gamePanel.getHeight()) {
            this.gamePanel.removeList.add(this);
        }
    }

    /*
     * 击中基地
     */

    public void hitBase() {
        Rectangle next=this.getRec();
        for(Base base:gamePanel.baseList) {
            if(base.getRec().intersects(next)) {
                this.gamePanel.baseList.remove(base);
                this.gamePanel.removeList.add(this);
                this.gamePanel.state=3;
                break;
            }
        }
    }


    @Override
    public void paintSelf(Graphics g) {
        // TODO Auto-generated method stub
        g.drawImage(img,x,y,null);
        go();
        //碰撞检测
        hitBot();
        hitWall();
        hitBase();
        hitFe();
        hitEnemyBullet();
    }

    @Override
    public Rectangle getRec() {
        return new Rectangle(x,y,width,height);
    }

}
