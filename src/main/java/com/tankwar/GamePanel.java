package com.tankwar;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 窗口类 继承jframe
 */
public class GamePanel extends JFrame{
    private long t=1000;
    private int minute1=0;
    private int minute2=0;
    private int second1=0;
    private int second2=0;
    private long t3;
    private long t1=0;
    private long t2=0;
    //是否运行
    boolean run=true;
    //关卡
    static int level=1;//控制关卡 也控制了人机坦克的攻击速度和生成坦克数量 ->实现难度提升
    //定义双缓存图片
    Image offScreenImage=null;
    /*
     * //游戏状态
     * 0 未开始，1 单人模式，5 双人模式
     * 2 游戏暂停，3 游戏失败，4 游戏胜利
     */
   public static int state=0;
   public static int loginwin=0;

   public static String Username;

    //游戏是否开始
    private boolean start=false;
    //临时变量
    private int a=1;
    //窗口长宽
    private static int width=1260;
    private static int height=800;
    //字体
    Font ft=new Font("宋体",Font.BOLD,50);
    //基地
    private Base base=new Base("src\\main\\java\\images\\base.gif",670,750,this);
    //重绘次数
    public int count=0;
    //敌人数量
    private int enemyCount=0;
    ///随机生成的墙体数量
    private int wallCount=100;
    private boolean d=true;
    private int[]xrr=new int[wallCount];
    private int[]yrr=new int[wallCount];
    //物体集合
    public List<Bullet>bulletList=new ArrayList<>();
    public List<Tank>tankList=new ArrayList<>();
    public List<Bot>botList=new ArrayList<>();
    public List<Bullet>removeList=new ArrayList<>();//用于删除tank图像；在foreach中不能调用remove
    public List<Wall>wallList=new ArrayList<>();
    public List<Grass>grassList=new ArrayList<>();
    public List<Fe>feList=new ArrayList<>();
    public List<Base> baseList = new ArrayList<>();
    public List<EnemyBullet>enemyBulletsList=new ArrayList<>();
    public List<EnemyBullet>removeList2=new ArrayList<>();
    public List<Explode>explodeList=new ArrayList<>();
    public List<Blood>BloodList=new ArrayList<>();
    //玩家
     PlayerOne playerOne = new PlayerOne("src/main/java/images/player1/p1tankU.gif", 500, 700,
            "src/main/java/images/player1/p1tankU.gif","src/main/java/images/player1/p1tankD.gif",
            "src/main/java/images/player1/p1tankL.gif","src/main/java/images/player1/p1tankR.gif", this);
    private PlayerTwo playerTwo = new PlayerTwo("images/player2/p1tankU.gif", 780, 700,
            "src/main/java/images/player2/p2tankU.gif","src/main/java/images/player2/p2tankD.gif",
            "src/main/java/images/player2/p2tankL.gif","src/main/java/images/player2/p2tankR.gif", this);

    /**
     * 随机函数 int wallCount=100; 赋予xrr[]，yrr[]位置信息
     */
    public void random() {
        Random r=new Random();
        for(int i=0;i<wallCount;) {
            int x=r.nextInt(21);
            int y=r.nextInt(8)+3;
            if(i>0) {
                boolean repeat=false;
                for(int j=0;j<i;j++) {
                    if(xrr[j]==x*60&&yrr[j]==y*60) {
                        repeat=true;
                    }
                }
                if(!repeat) {
                    xrr[i]=x*60;
                    yrr[i]=y*60;
                    i++;
                }else {
                    continue;
                }
            }else {
                xrr[i]=x*60;
                yrr[i]=y*60;
                i++;
            }
        }
    }

    /*
     * 重置
     */

    public void reset() {
        count=0;
        enemyCount=0;
        playerOne = new PlayerOne("src/main/java/images/player1/p1tankU.gif", 500, 700,
                "src/main/java/images/player1/p1tankU.gif","src/main/java/images/player1/p1tankD.gif",
                "src/main/java/images/player1/p1tankL.gif","src/main/java/images/player1/p1tankR.gif", this);
        playerTwo = new PlayerTwo("images/player2/p1tankU.gif", 500, 700,
                "src/main/java/images/player2/p2tankU.gif","src/main/java/images/player2/p2tankD.gif",
                "src/main/java/images/player2/p2tankL.gif","src/main/java/images/player2/p2tankR.gif", this);
        feList.clear();
        bulletList.clear();
        tankList.clear();
        botList.clear();
        removeList.clear();
        wallList.clear();
        grassList.clear();
        baseList.clear();
        enemyBulletsList.clear();
        removeList2.clear();
        explodeList.clear();
    }
    /*
     * 添加墙，基地
     */

    public void add() {
        random();
        //添加固定围墙
        for(int i=0;i<21;i++) {
            wallList.add(new Wall("src/main/java/images/walls.gif",i*60,120,this));
        }
        wallList.add(new Wall("src/main/java/images/walls.gif",600,740,this));
        wallList.add(new Wall("src/main/java/images/walls.gif",600,680,this));
        wallList.add(new Wall("src/main/java/images/walls.gif",660,680,this));
        wallList.add(new Wall("src/main/java/images/walls.gif",720,680,this));
        wallList.add(new Wall("src/main/java/images/walls.gif",720,740,this));
        //随机生成游戏地图
        for(int i=0;i<wallCount;i++) {
            Random a=new Random();
            int num=a.nextInt(13);
            if(0<=num&&num<1) {
                Blood blood=new Blood(xrr[i]+10, yrr[i]+10, null);
                BloodList.add(blood);
            }else if(1<=num&&num<5){
                Grass g=new Grass("src/main/java/images/grass.gif",xrr[i], yrr[i], null);
                grassList.add(g);
            } else if (4<=num&&num<8) {
                Wall wall=new Wall("src/main/java/images/walls.gif",xrr[i], yrr[i], null);
                wallList.add(wall);
            }else if (8<=num&&num<13) {
                Fe f=new Fe("src/main/java/images/hardwalls.gif",xrr[i], yrr[i], null);
                feList.add(f);
            }
        }
        //添加基地
        baseList.add(base);
    }

    /**
     * 窗口绘制属性初始化
     */
    public void initfram() {
        //标题
        setTitle("坦克大战");
        //窗口初始化大小
        setSize(width,height);
        //使屏幕居中
        setLocationRelativeTo(null);
        //添加关闭事件
        setDefaultCloseOperation(3);
        //用户不能调整大小
        setResizable(false);
        //使窗口可见
        setVisible(true);
    }

    /*
     * 窗口的启动方法
     */
    public void launch() {
        //窗口初始化
        initfram();
        //初始化基地围墙;
        add();
        //添加键盘事件
        this.addKeyListener(new KeyMonitor());//回车也可以进行游戏;p暂停->run=false;玩家tank操作读取
        //游戏规则与绘制
        while(true) {
            if(botList.size()==0&&enemyCount==level+5) {
                state=4;//胜利
            }
            if(tankList.size()==0&&(state==1||state==5)) {
                state=3;//失败
            }
            if(state==1||state==5) {
                //生成人机tank 每重绘50次则生成一辆
                if(count%50==1&&enemyCount<(level+5)) {//与198行判定相关
                    boolean a=true;
                    Random r=new Random();
                    int rnum=r.nextInt(19)+1;
                    if(enemyCount>0) {
                        for(Bot b:botList) {
                            if(Math.abs(rnum*60-b.getX())<60) {
                                a=false;
                            }
                        }
                        if(a) {
                            botList.add(new Bot("src/main/java/images/enemy/enemy1U.gif", rnum*60, 60,
                                    "src/main/java/images/enemy/enemy1U.gif","src/main/java/images/enemy/enemy1D.gif",
                                    "src/main/java/images/enemy/enemy1L.gif","src/main/java/images/enemy/enemy1R.gif", this));
                            enemyCount++;
                        }
                    }else {
                        botList.add(new Bot("images/enemy/enemy1U.gif", rnum*60, 60,
                                "src/main/java/images/enemy/enemy1U.gif","src/main/java/images/enemy/enemy1D.gif",
                                "src/main/java/images/enemy/enemy1L.gif","src/main/java/images/enemy/enemy1R.gif", this));
                        enemyCount++;
                    }
                }
                if(count%2==1) {
                    if(!explodeList.isEmpty()) {
                        explodeList.remove(0);
                    }
                }
            }

            //state=0;->调用鼠标监视器->tankList.add(playerOne);state=1;start=true;->回到while(true);
            //state=3;->绘制gameover->调用鼠标监视器进行重开操作
            //state=4;->绘制win->调用鼠标监视器进行重开操作
            //state=1或者5;->重新绘制各元素和count++
            if(t1!=0){
                t2=System.currentTimeMillis();

            }

            if (run) {
                repaint();//调用了paint（）；
            }

            try {
                Thread.sleep(25);//该线程睡眠25毫秒，重绘速度控制,防止程序奔溃
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    //绘制各元素
    public void mpaint(Graphics g){
        for(Tank player:tankList) {
            player.paintSelf(g);
        }
        for(Bullet bullet:bulletList) {
            bullet.paintSelf(g);
        }
        bulletList.removeAll(removeList);

        for(EnemyBullet enemyBullet:enemyBulletsList) {
            enemyBullet.paintSelf(g);
        }
        enemyBulletsList.removeAll(removeList2);

        for(Explode explode:explodeList) {
            explode.paintSelf(g);
        }
        for(Bot bot:botList) {
            bot.paintSelf(g);
        }
        for(Wall wall:wallList) {
            wall.paintSelf(g);
        }
        for(Fe fe:feList) {
            fe.paintSelf(g);
        }
        for(Blood blood:BloodList) {
            blood.paintSelf(g);
        }
        for(Grass grass:grassList) {
            grass.paintSelf(g);
        }
        for(Base base:baseList) {
            base.paintSelf(g);
        }
    }

    public void timepaint(Graphics g) {
        g.drawString( "0"+ minute2+":"+second1+second2,1000,750);
        g.setColor(Color.white);
        g.setFont(ft);
    }



    /**
     * 绘制函数 双缓存技术->重写paint（）
     * @param g the specified Graphics window
     */

    @Override
    public void paint(Graphics g) {
        //创建和容器一样大小的Image图片
        if(offScreenImage==null) {
            offScreenImage=this.createImage(width,height);
        }
        //获得该图片的画布
        Graphics gImage=offScreenImage.getGraphics();
        //填充整个画布
        gImage.fillRect(0,0,width,height);
        if(state==0) {
            //添加图片
            gImage.drawImage(Toolkit.getDefaultToolkit().getImage("src/main/java/images/menu.gif"),0,0,this);
            //添加鼠标事件
            this.addMouseListener(new MouseMonitor());
        }
        else if(state==1||state==5) {
            //TODO
            if((t2-t1)>t){
                if(second2==9)
                {
                    second2=0;
                    if (second1>=5){
                        second2=0;
                        second1=0;
                        minute1++;
                    }else {
                        second1++;
                    }

                }else{
                    second2++;
                }
                t+=1000;
            }
            timepaint(gImage);
            gImage.drawString("关卡-"+level,30,750);
            gImage.setColor(Color.white);
            gImage.setFont(ft);
            //paint重绘游戏元素
            mpaint(gImage);
            gImage.drawString("关卡-"+level,30,750);
            gImage.setColor(Color.white);
            gImage.setFont(ft);
            timepaint(gImage);
            //重绘次数+1
            count++;
        } else if(state==2) {//暂停

        }else if(state==3) {
            gImage.drawImage(Toolkit.getDefaultToolkit().getImage("src/main/java/images/gameover.png"),(width-800)/2,(height-450)/2,this);
            //添加鼠标事件
            this.addMouseListener(new MouseMonitor());//set();start=false;state=0;add（）
        }else if(state==4) {
            gImage.drawImage(Toolkit.getDefaultToolkit().getImage("src/main/java/images/win.png"),(width-474)/2,(height-340)/2,this);
            this.addMouseListener(new MouseMonitor());//set()和add（）
        }
        //将缓冲区绘制好的图形整个绘制到容器的画布中
        g.drawImage(offScreenImage,0,0,null);

    }

    /*
     * 添加键盘监听（回车 ，p ，玩家操作）
     */
    private class KeyMonitor extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            int key=e.getKeyCode();
            switch(key) {
                case KeyEvent.VK_ENTER:
                    if(!start) {
                        tankList.add(playerOne);//将坦克添加至坦克集合
                        state=a;
                        start=true;
                    }
                    break;
                case KeyEvent.VK_P:
                    if(state!=2) {
                        a=state;
                        state=2;
                        run=false;
                    }else {
                        state=a;
                        run=true;
                        if(a==0) {
                            a=1;
                        }
                    }
                    break;
                default:
                    if(state==1) {
                        playerOne.keyPressed(e);
                    }
                    if(state==5) {
                        playerOne.keyPressed(e);
                        playerTwo.keyPressed(e);
                    }
                    break;
            }
        }
        @Override
        public void keyReleased(KeyEvent e) {
            playerOne.keyReleased(e);
            playerTwo.keyReleased(e);
        }
    }

    //添加鼠标监听 开始的选择 以及存放了重开一局的reset（）和add（）
    private class MouseMonitor extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            Point p = new Point(e.getX(), e.getY());
            if (!start && state == 0) {


                if (p.x > 500 && p.x < 800 && p.y > 350 && p.y < 490) {
                    Music.clickPlay();
                    tankList.add(playerOne);
                    state = 1;
                    start = true;
                    try {
                        Thread.sleep(25);//该线程睡眠25毫秒，重绘速度控制,防止程序奔溃
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    //TODO
                    t1=System.currentTimeMillis();
                    t=1000;
                    second1=0;
                    second2=0;
                    minute1=0;
                    minute2=0;
                    Music.startPlay();
                }else if(p.x > 500 && p.x < 800 && p.y > 490 && p.y < 600){
                    Music.clickPlay();
                    tankList.add(playerOne);
                    tankList.add(playerTwo);
                    state = 5;
                    start = true;
                    try {
                        Thread.sleep(25);//该线程睡眠25毫秒，重绘速度控制,防止程序奔溃
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    //TODO
                    t1=System.currentTimeMillis();
                    t=0;
                    second1=0;
                    second2=0;
                    minute1=0;
                    minute2=0;
                    Music.startPlay();
                }
            } else if (state == 3) {
                Connection connection= JdbcUtil.getConnection();
                String sql="update playerlist set level=? where username=?";
                PreparedStatement psta=null;
                try {
                    psta=connection.prepareStatement(sql);
                    psta.setInt(1, level);
                    psta.setString(2,Username);
                    psta.executeUpdate();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                JdbcUtil.close(connection,psta);
                reset();
                start = false;
                state = 0;
                add();
                t3=System.currentTimeMillis();
            } else if (state == 4) {
                level++;
                Connection connection=JdbcUtil.getConnection();
                String sql="update playerlist set level='"+level+"'where username='"+Username+"'";
                PreparedStatement psta=null;

                try {
                    psta=connection.prepareStatement(sql);
                    psta.executeUpdate();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                JdbcUtil.close(connection,psta);

                reset();
                start = false;
                state = 0;
                add();
                t3=System.currentTimeMillis();
            }
        }
    }

    public static void Login1(){
        JFrame jFrame=new JFrame();
        jFrame.setTitle("坦克大战");

        jFrame.setSize(width,height);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocation(100,30);
        ImageIcon img=new ImageIcon("src/main/java/images/Login_picture.jpg");
        JLabel label=new JLabel(img);
        label.setSize(img.getIconWidth(),img.getIconHeight());
        jFrame.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));
        JPanel pan=(JPanel)jFrame.getContentPane();
        pan.setOpaque(false);
        pan.setLayout(new FlowLayout());


        jFrame.setLayout(null);

        JButton jButton1=new JButton("登录");
        JButton jButton2 = new JButton("退出");
        JButton jButton3 = new JButton("注册");
        JLabel jLabel1 = new JLabel("账号：");
        JLabel jLabel2 = new JLabel("密码：");
        JTextArea jTextArea1 = new JTextArea();
        JTextArea jTextArea2 = new JTextArea();

        jLabel1.setSize(150,40);
        jLabel2.setSize(150,40);

        jLabel1.setFont(new Font("宋体",Font.BOLD,30));
        jLabel1.setForeground(Color.white);
        jLabel2.setFont(new Font("宋体",Font.BOLD,30));
        jLabel2.setForeground(Color.white);

        jLabel1.setLocation(450,300);
        jLabel2.setLocation(450,350);

        jButton1.setSize(70,30);
        jButton2.setSize(70,30);
        jButton3.setSize(70,30);
        jButton1.setLocation(500,420);
        jButton2.setLocation(680,420);
        jButton3.setLocation(590,420);

        jTextArea1.setSize(250,40);
        jTextArea1.setLocation(530,300);
        jTextArea2.setSize(250,40);
        jTextArea2.setLocation(530,350);

        jTextArea1.setFont(new Font("宋体",Font.BOLD,36));
        jTextArea2.setFont(new Font("宋体",Font.BOLD,36));

        pan.add(jButton1);
        pan.add(jButton2);
        pan.add(jButton3);
        pan.add(jLabel1);
        pan.add(jLabel2);
        pan.add(jTextArea1);
        pan.add(jTextArea2);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);


        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username=jTextArea1.getText().trim();
                String password=jTextArea2.getText().trim();
                Connection connection=JdbcUtil.getConnection();
                String sql="select * from playerlist where username=?";
                PreparedStatement psta=null;
                ResultSet res=null;
                try {
                    psta=connection.prepareStatement(sql);
                    psta.setString(1,username);
                    res=psta.executeQuery();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                if (username.equals("") || password.equals("")) {
                    JOptionPane.showMessageDialog(jFrame, "用户信息不允许为空！");
                    JdbcUtil.close(connection,psta,res);
                    return;
                }
                else {
                    try {
                        int t=0;
                        while (res.next()){
                            if(password.equals(res.getString("password"))){
                                jFrame.dispose();
                                GamePanel.Username=username;
                                level=res.getInt("level");
                                loginwin=1;
                                JdbcUtil.close(connection,psta,res);
                                return;
                            }
                            else {
                                JOptionPane.showMessageDialog(jFrame,"密码错误，请重新登录！");
                                t=1;
                            }
                        }
                        if(t==0)JOptionPane.showMessageDialog(jFrame,"账号不存在！");
                        JdbcUtil.close(connection,psta,res);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.dispose();
                loginwin=2;
            }
        });

        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username=jTextArea1.getText().trim();
                String password=jTextArea2.getText().trim();
                Connection connection=JdbcUtil.getConnection();
                String sql="INSERT INTO playerlist(username,password,level) values(?,?,1)";
                String sql1="select * from playerlist where username=?";
                PreparedStatement psta=null;
                PreparedStatement psta1=null;
                ResultSet res=null;

                try {
                    psta1=connection.prepareStatement(sql1);
                    psta1.setString(1,username);
                    res=psta1.executeQuery();
                }catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                try {
                    if (res.next()){
                        JOptionPane.showMessageDialog(jFrame, "账号已经存在，注册失败！");
                        JdbcUtil.close(connection,psta,res);
                    }else {
                        if (username==null||password==null||username.equals("")||password.equals("")){
                            JOptionPane.showMessageDialog(jFrame, "账号和密码都不能为空，注册失败！");
                            JdbcUtil.close(connection,psta,res);
                        }else if(username.contains(" ")||password.contains(" ")||username.length()>15||password.length()>15){
                            JOptionPane.showMessageDialog(jFrame, "密码或者账号的格式错误，注册失败！");
                            JdbcUtil.close(connection,psta,res);
                        }else if (username.length()<=15&&password.length()<=15){
                            psta=connection.prepareStatement(sql);
                            psta.setString(1,username);
                            psta.setString(2,password);
                            psta.executeUpdate();
                            loginwin=1;
                            level=res.getInt("level");
                            GamePanel.Username=username;
                            JdbcUtil.close(connection,psta,res);
                            return;
                        }
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
            });

    }

    public static void main(String[] args) throws InterruptedException {
        Login1();
    while (true){
        Thread.sleep(100);
    if(loginwin==1){
        GamePanel gamePanel = new GamePanel();
        gamePanel.launch();
        break;
    }
        if(loginwin==2){
            break;
        }
}


    }
}
