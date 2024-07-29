package com.mycompany.snake_game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{
    
    final static int HEIGHT = 600;
    final static int WIDTH = 600;
    static final int unitSize=25;
    static final int gameUnits = (WIDTH*HEIGHT)/unitSize;
    static final int delay = 105;
    final int x[] = new int[gameUnits];
    final int y[] = new int[gameUnits];
    int bodyParts = 6;
    int score=0;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running=false;
    Timer timer;
    Random random;
    
    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();

    }

    public void startGame(){
        newApple();
        running=true;
        timer= new Timer(delay,this);
        timer.start();
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    
    public void draw(Graphics g){
        if(running){
//             for(int i=0;i<HEIGHT/unitSize;i++){
//                g.drawLine(i*unitSize,0,i*unitSize,HEIGHT);
//                g.drawLine(0, i*unitSize,WIDTH, i*unitSize);
//               }
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, unitSize, unitSize);
         
            for(int i=0;i<bodyParts;i++){
                 if(i==0){
                    g.setColor(Color.green); 
                    g.fillRect(x[i],y[i], unitSize, unitSize);
                }
                else{
                    //g.setColor(new Color(45,180,0)); 
                    g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i],y[i], unitSize, unitSize);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink free",Font.BOLD,25));
            FontMetrics metric = getFontMetrics(g.getFont());
            g.drawString("Score : "+score, (WIDTH - metric.stringWidth("Score :  "+score))/2, g.getFont().getSize());
        
        }
        else{
            gameOver(g);
        }
    }
    public void newApple(){
        appleX = random.nextInt((int)WIDTH/unitSize)*unitSize;
        appleY = random.nextInt((int)HEIGHT/unitSize)*unitSize;
    }
    
    public void checkApple(){
        if((x[0]==appleX)&&(y[0]==appleY)){
            bodyParts++;
            score++;
            newApple();
        }
    }
    public void checkCollision(){
        for(int i=bodyParts;i>0;i--){
            if((x[0]==x[i])&&(y[0]==y[i])){
                running=false;
            }
       }
        if(x[0]<0){
            running= false;
        }
        if(x[0]>WIDTH){
            running=false;
        }
        if(y[0]<0){
            running = false;
        }
        if(y[0]>HEIGHT){
            running = false;
        }
        if(!running){
            timer.stop();
        }
    }
    public void move(){
        for(int i=bodyParts;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
            switch(direction){
                case 'R':
                    x[0] = x[0] + unitSize;
                    break;
                case 'L':
                    x[0] = x[0] - unitSize;
                    break;
                case 'U':
                    y[0] = y[0] - unitSize;
                    break;
                case 'D':
                    y[0] = y[0] + unitSize;
                    break;
        }
    }
    public void gameOver(Graphics g){
         g.setColor(Color.red);
            g.setFont(new Font("Ink free",Font.BOLD,40));
            FontMetrics metric = getFontMetrics(g.getFont());
            g.drawString("Score : "+score, (WIDTH - metric.stringWidth("Score : "+score))/2, g.getFont().getSize());
        
        
        g.setColor(Color.red);
        g.setFont(new Font("Ink free",Font.BOLD,75));
        FontMetrics metric1 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (WIDTH - metric1.stringWidth("Game Over"))/2, HEIGHT/2);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
       if(running){
           move();
           checkApple();
           checkCollision();
       }
       repaint();
    }
    
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction!= 'R'){
                        direction='L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction!= 'L'){
                        direction='R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction!= 'D'){
                        direction='U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction!= 'U'){
                        direction='D';
                    }
                    break;
            }
        }
    }
}
