package com.tankwar;

import java.awt.*;
import java.util.List;

  /*
   *玩家坦克与人机坦克的父类
   */
public class Tank extends GameObject {


    //向上移动时的图片
    private String upImage;
    //向下移动时的图片
    private String downImage;
    //向右移动时的图片
    private String rightImage;
    //向左移动时的图片
    private String leftImage;
    //tank生命值 为了显示血条
    public int tanklife=100;
    //坦克size
    int width=54;
    int height=54;

    //坦克初始方向
    Direction direction=Direction.UP;
    //坦克速度
    private int speed=3;
    //攻击冷却
    boolean attackCoolDown=true;
    //攻击冷却事件毫秒间隔1000ms发射子弹
     private int attackCoolDownTime=500;
    //坦克是否活着
    public boolean alive=true;
    /*
     * 坦克坐标，方向，图片，方向，面板
     */
    public Tank(String img,int x,int y,String upImage,String downImage,String leftImage,String rightImage,GamePanel gamePanel) {
        super(img,x,y,gamePanel);
        this.upImage=upImage;
        this.leftImage=leftImage;
        this.downImage=downImage;
        this.rightImage=rightImage;
    }

    /*
     * 坦克移动
     */
    public void leftWard() {
        direction=Direction.LEFT;
        setImg(leftImage);
        if(!hitWall(x-speed,y)&&!moveToBorder(x-speed,y)&&alive&&!hitTank(x-speed,y)) {
            this.x-=speed;
        }
    }

    /*
     * 方向绘图，并且进行移动判定
     */
    public void rightWard() {
        direction=Direction.RIGHT;
        setImg(rightImage);
        if(!hitWall(x+speed,y)&&!moveToBorder(x+speed,y)&&alive&&!hitTank(x+speed,y)) {
            this.x+=speed;
        }
    }
    public void upWard() {
        direction=Direction.UP;
        setImg(upImage);
        if(!hitWall(x,y-speed)&&!moveToBorder(x,y-speed)&&alive&&!hitTank(x,y-speed)) {
            this.y-=speed;
        }
    }
    public void downWard() {
        direction=Direction.DOWN;
        setImg(downImage);
        if(!hitWall(x,y+speed)&&!moveToBorder(x,y+speed)&&alive&&!hitTank(x-speed,y+speed)) {
            this.y+=speed;
        }
    }

    /*
     * 射击
     */
    public void attack() {
        Point p=getHeadPoint();
        if(attackCoolDown&&alive) {
            Music.attackPlay();
            Bullet bullet=new Bullet("src/main/java/images/bullet/bulletGreen.gif",p.x-10,p.y-10, direction, this.gamePanel);
            this.gamePanel.bulletList.add(bullet);//将子弹添加至子弹集合
            attackCoolDown=false;
            new AttackCD().start();//线程开始
        }
    }

    /*
     * 坦克射击cd
     */
    public class AttackCD extends Thread{
        public void run() {
            attackCoolDown=false;
            try {
                //休眠1秒
                Thread.sleep(attackCoolDownTime);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
            //将攻击功能解除冷却状态
            attackCoolDown=true;
            this.stop();//不是很理解
        }
    }
    /*
     * 根据方向确定头部位置,x和y是左上角的点->用于子弹发射
     */
    public Point getHeadPoint() {
        switch(direction) {
            case UP:
                return new Point(x+width/2,y);
            case LEFT:
                return new Point(x,y+height/2);
            case DOWN:
                return new Point(x+width/2,y+height);
            case RIGHT:
                return new Point(x+width,y+height/2);
            default:
                return null;
        }
    }
    /*
     * 坦克与墙碰撞检测
     */
    public boolean hitWall(int x,int y) {
        //假设玩家坦克前进，下一个位置形成的矩形
        Rectangle next=new Rectangle(x,y,width,height);
        //地图里所有的墙体
        List<Wall>walls=this.gamePanel.wallList;
        List<Fe>fes=this.gamePanel.feList;
        //判断两个矩形是否相交
        for(Wall w:walls) {
            if(w.getRec().intersects(next)) {
                return true;
            }
        }
        for(Fe f:fes) {
            if(f.getRec().intersects(next)) {
                return true;
            }
        }
        return false;
    }
    /*
     * 人机碰撞检测
     */
    public boolean hitTank(int x,int y) {
        int a=0;
        //假设人机坦克前进，下一个位置形成的矩形
        Rectangle next=new Rectangle(x,y,width,height);
        //地图里所有的人机
        List<Bot>bots=this.gamePanel.botList;
        //判断两个矩形是否相交
        for(Bot b:bots) {
            if(b.getRec().intersects(next)) {
                a++;
                if(a==2) {
                    return true;
                }
            }
        }
        return false;
    }
    /*
     * 边界与坦克碰撞检测
     */
    public boolean moveToBorder(int x,int y) {
        if(x<10) {
            return true;
        }else if(x>this.gamePanel.getWidth()-width) {
            return true;
        }
        if(y<30) {
            return true;
        }else if(y>this.gamePanel.getHeight()-height) {
            return true;
        }
        return false;
    }

      //坦克吃血块
      public void eatBlood(){
          Rectangle next=this.getRec();
          List<Blood>blood=this.gamePanel.BloodList;
          for(Blood blood1:blood) {
              if(blood1.getRec().intersects(next)) {
                  if(this.tanklife!=100&&blood1.isLive()&&this.alive&&this.getRec().intersects(blood1.getRec())){
                      blood1.setLive(false);
                      this.tanklife = 100;
                  break;
              }
              }
          }
    }


    /*
    得到tank的图像范围
     */
    @Override
    public Rectangle getRec(){
        return new Rectangle(x,y,width,height);
    }
    /*
     *绘制tank图像
     */
    @Override
    public void paintSelf(Graphics g) {
        //todo
        eatBlood();
        g.drawImage(img, x, y, null);
        eatBlood();
    }
}
