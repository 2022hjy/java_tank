package com.tankwar;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.io.File;


public class Music{
    private static Clip start;//刚进入游戏的音乐

    private static Clip click;//菜单选择音乐
    private static Clip move;//玩家移动音乐
    private static Clip attack;//玩家射击音乐
    private static Clip explode;//爆炸音效

    public Music() {
    }

    private static Clip wall;//击中墙体音效
    static {
        File bgMusicStartFile = new File("D:\\java\\code\\TankWar1\\tank\\src\\main\\java\\music\\start.wav");
        File bgMusicClickFile = new File("D:\\java\\code\\TankWar1\\tank\\src\\main\\java\\music\\click.wav");
        File bgMusicAttackFile = new File("D:\\java\\code\\TankWar1\\tank\\src\\main\\java\\music\\atack.wav");
        File bgMusicMoveFile = new File("D:\\java\\code\\TankWar1\\tank\\src\\main\\java\\music\\move.wav");
        File bgMusicExplodeFile = new File("D:\\java\\code\\TankWar1\\tank\\src\\main\\java\\music\\explode.wav");
        File bgMusicWallFile = new File("D:\\java\\code\\TankWar1\\tank\\src\\main\\java\\music\\wall.wav");
        try {
            AudioInputStream audioInputStreamStart = AudioSystem.getAudioInputStream(bgMusicStartFile);
            start = AudioSystem.getClip();
            start.open(audioInputStreamStart);
            AudioInputStream audioInputStreamClick = AudioSystem.getAudioInputStream(bgMusicClickFile);
            click = AudioSystem.getClip();
            click.open(audioInputStreamClick);
            AudioInputStream audioInputStreamAttack = AudioSystem.getAudioInputStream(bgMusicAttackFile);
            attack = AudioSystem.getClip();
            attack.open(audioInputStreamAttack);
            AudioInputStream audioInputStreamStartMove = AudioSystem.getAudioInputStream(bgMusicMoveFile);
            move = AudioSystem.getClip();
            move.open(audioInputStreamStartMove);
            AudioInputStream audioInputStreamStartExplode = AudioSystem.getAudioInputStream(bgMusicExplodeFile);
            explode = AudioSystem.getClip();
            explode.open(audioInputStreamStartExplode);
            AudioInputStream audioInputStreamStartWall = AudioSystem.getAudioInputStream(bgMusicWallFile);
            wall = AudioSystem.getClip();
            wall.open(audioInputStreamStartWall);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Music(Image img, int x, int y, GamePanel gamePanel) {
    }

    public static void playBackground(){
        //循环播放
        move.setFramePosition(0);
        move.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public static void startPlay(){
        start.start();
        start.setFramePosition(0);
    }

    public static void clickPlay(){
        click.start();
        click.setFramePosition(0);
    }
    public static void attackPlay(){
        attack.start();
        //将进度条调为0
        attack.setFramePosition(0);
    }
    public static void movePlay(){
        move.start();
        move.setFramePosition(0);
    }
    public static void moveStop(){
        move.stop();
    }
    public static void explodePlay(){
        explode.start();
        explode.setFramePosition(0);
    }
    public static void wallPlay() {
        wall.start();
        wall.setFramePosition(0);
    }
}
