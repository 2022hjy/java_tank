package com.tankwar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    public Login(){
        JFrame jFrame=new JFrame();
        jFrame.setTitle("坦克大战");

        jFrame.setLayout(null);

        JButton jButton1=new JButton("登录");
        JButton jButton2 = new JButton("退出");
        JButton jButton3 = new JButton("注册");
        JLabel jLabel1 = new JLabel("账号：");
        JLabel jLabel2 = new JLabel("密码：");
        JTextArea jTextArea1 = new JTextArea();
        JTextArea jTextArea2 = new JTextArea();

        jLabel1.setSize(100,40);
        jLabel2.setSize(100,40);
        jLabel1.setLocation(480,300);
        jLabel2.setLocation(480,350);

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

        jFrame.add(jButton1);
        jFrame.add(jButton2);
        jFrame.add(jButton3);
        jFrame.add(jLabel1);
        jFrame.add(jLabel2);
        jFrame.add(jTextArea1);
        jFrame.add(jTextArea2);

        jFrame.setVisible(true);
        jFrame.setSize(1260,800);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocation(100,30);

        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username=jTextArea1.getText().trim();
                String password=jTextArea2.getText().trim();
                Connection connection= JdbcUtil.getConnection();
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
                    return;
                }
                else {
                            try {
                                int t=0;
                                while (res.next()){
                                    if(password.equals(res.getString("password"))){
                                        jFrame.dispose();
                                        return;
                                    }
                                    else {
                                        JOptionPane.showMessageDialog(jFrame,"密码错误，请重新登录！");
                                        t=1;
                                    }
                                }
                                if(t==0)JOptionPane.showMessageDialog(jFrame,"账号不存在！");
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
            }
        });

        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username=jTextArea1.getText().trim();
                String password=jTextArea2.getText().trim();
                Connection connection=JdbcUtil.getConnection();
                String sql="INSERT INTO playerlist(username,password,level) values(?,?,1)";
                PreparedStatement psta=null;
                ResultSet res=null;

                try {
                    if (username==null||password==null||username.equals("")||password.equals("")){
                        JOptionPane.showMessageDialog(jFrame, "账号和密码都不能为空，注册失败！");
                    }else if(username.contains(" ")||password.contains(" ")||username.length()>15||password.length()>15){
                        JOptionPane.showMessageDialog(jFrame, "密码或者账号的格式错误，注册失败！");
                    }else if (username.length()<=15&&password.length()<=15){
                        psta=connection.prepareStatement(sql);
                        psta.setString(1,username);
                        psta.setString(2,password);
                        psta.executeUpdate();
                        GamePanel gamePanel=new GamePanel();
                        gamePanel.launch();
                        return;
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }

        });

    }



}

