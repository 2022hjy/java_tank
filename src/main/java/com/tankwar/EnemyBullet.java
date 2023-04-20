package com.tankwar;

import java.awt.*;
import java.util.List;
/*
 * 人机子弹
 */
public class EnemyBullet extends Bullet{
    public EnemyBullet(String img, int x, int y, Direction direction, GamePanel gamePanel) {
        super(img, x, y, direction, gamePanel);
    }

    public void hitPlayer() {//攻击到玩家，玩家死亡
        Rectangle next=this.getRec();
        List<Tank>tanks=this.gamePanel.tankList;
        //子弹和坦克
        for(Tank tank:tanks) {
            if(tank.getRec().intersects(next)) {
                this.gamePanel.removeList2.add(this);
                //todo
                int lifelevel=0;
                if(GamePanel.level<=3){
                     lifelevel=20;
                }else if (GamePanel.level>3) {
                     lifelevel = 50;
                }
                if (tank.tanklife==lifelevel){
                    Explode[]arr=new Explode[7];
                    for(int i=0;i<4;i++) {
                        Explode explode=new Explode("images/explodeArray/explode" +(i+1)+".png", x, y, this.gamePanel);
                        this.gamePanel.explodeList.add(explode);
                    }
                    tank.alive=false;
                    Music.explodePlay();
                    this.gamePanel.tankList.remove(tank);

                } else if (tank.tanklife>lifelevel) {
                    tank.tanklife=tank.tanklife-lifelevel;
                    Music.wallPlay();
                }

                break;
            }
        }
    }
    @Override
    public void hitBase() {//攻击到基地，游戏结束
        Rectangle next=this.getRec();
        for(Base base:gamePanel.baseList) {
            if(base.getRec().intersects(next)) {
                this.gamePanel.baseList.remove(base);
                this.gamePanel.removeList2.add(this);
                Music.explodePlay();
                this.gamePanel.state=3;
                break;
            }
        }
    }
    public void hitWall() {//攻击到土墙，子弹与土均消失
        Rectangle next=this.getRec();
        List<Wall>walls=this.gamePanel.wallList;
        for(Wall w:walls) {
            if(w.getRec().intersects(next)) {
                this.gamePanel.wallList.remove(w);
                this.gamePanel.removeList2.add(this);
                break;
            }
        }
    }
    public void hitFe() {//攻击到铁墙，墙不变，子弹消失
        Rectangle next=this.getRec();
        List<Fe>fes=this.gamePanel.feList;
        for(Fe f:fes) {
            if(f.getRec().intersects(next)) {
                this.gamePanel.removeList2.add(this);
                break;
            }
        }
    }
    public void paintSelf(Graphics g) {
        g.drawImage(img,x,y,null);
        go();
        hitBase();
        hitWall();
        hitFe();
        hitPlayer();
    }
}
