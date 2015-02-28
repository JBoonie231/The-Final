import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.ArrayList;

public class Screen extends JPanel implements Runnable
{
    private static int topScore;
    
    private Image openingScreen, finalFight, endScreen, pass, fail;
    private Background background;
    private Hero hero;
    private Prof prof;
    private Thread animator;
    private Rectangle csDoor;
    private ArrayList<EC> ec = new ArrayList<EC>(10);
    protected MusicGuy[] musicGuys = new MusicGuy[5];
    private FrisbeeGuy[] frisbeeGuys = new FrisbeeGuy[8];
    private String[] profLines = new String[3];
    protected ArrayList<Frisbee> frisbees = new ArrayList<Frisbee>();
    
    private String first1, first2, first3, first4, first5, instructions1, instructions2, 
                   pressSpace, time, score, again, top, placeHolder;
                   
    private Font smaller, small, medium, large, xlarge;
    private boolean inGame, endGame, ending, skip, skip2, finish;
    private int clock = 300;
    private int slowDown = 6;
    private int finalScore = 70;
    private int num, num2, num3, ecAdder, decriment;
    private final int DELAY = 5;
    
    public Screen() 
    {
        addKeyListener(new Controller());
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        
        ImageIcon ii = new ImageIcon(this.getClass().getResource("Opening Screen.png"));
        openingScreen = ii.getImage();
        ii = new ImageIcon(this.getClass().getResource("Final Fight.png"));
        finalFight = ii.getImage();
        ii = new ImageIcon(this.getClass().getResource("End Screen.png"));
        endScreen = ii.getImage();
        ii = new ImageIcon(this.getClass().getResource("PASS.gif"));
        pass = ii.getImage();
        ii = new ImageIcon(this.getClass().getResource("FAIL.gif"));
        fail = ii.getImage();
        
        smaller = new Font("Helvetica", Font.BOLD, 5*TheFinal.SCALE);
        small = new Font("Helvetica", Font.BOLD, 10*TheFinal.SCALE);
        medium = new Font("Helvetica", Font.BOLD, 15*TheFinal.SCALE);
        large = new Font("Helvetica", Font.BOLD, 20*TheFinal.SCALE);
        xlarge = new Font("Helvetica", Font.BOLD, 30*TheFinal.SCALE);
        
        hero = new Hero(this);
        prof = new Prof();
        background = new Background();
        animator = new Thread(this);
        
        csDoor = new Rectangle(4184, 434, 17, 113);
        
        ec.add(new EC(0, 0, background));
        ec.add(new EC(31, 433, background));
        ec.add(new EC(564, 346, background));
        ec.add(new EC(1283, 489, background));
        ec.add(new EC(1455, 446, background));
        ec.add(new EC(3605, 348, background));
        ec.add(new EC(3603, 198, background));
        ec.add(new EC(1009, 387, background));
        ec.add(new EC(1667, 251, background));
        ec.add(new EC(2833, 430, background));
        ec.add(new EC(4160, 311, background));
        
        musicGuys[0] = new MusicGuy(2197, 495, false, background);
        musicGuys[1] = new MusicGuy(2826, 495, true, background);
        musicGuys[2] = new MusicGuy(3498, 495, false, background);
        musicGuys[3] = new MusicGuy(3598, 495, true, background);
        musicGuys[4] = new MusicGuy(3698, 495, true, background);
        
        frisbeeGuys[0] = new FrisbeeGuy(906, 495, false, background, this);
        frisbeeGuys[1] = new FrisbeeGuy(1095, 495, true, background, this);
        frisbeeGuys[2] = new FrisbeeGuy(1547, 350, false, background, this);
        frisbeeGuys[3] = new FrisbeeGuy(1747, 350, true, background, this);
        frisbeeGuys[4] = new FrisbeeGuy(2669, 495, false, background, this);
        frisbeeGuys[5] = new FrisbeeGuy(2990, 495, true, background, this);
        frisbeeGuys[6] = new FrisbeeGuy(3476, 400, false, background, this);
        frisbeeGuys[7] = new FrisbeeGuy(3694, 400, true, background, this);
        
        first1 = "Finally! Finished my final project! But I only";
        first2 = "have one minute to get to class! Prof. Porto";
        first3 = "will dock my project's points if I'm late! ";
        first4 = "Maybe I can pick up some extra credit(EC)";
        first5 = "along the way. . .";
        
        instructions1 = "Use the LEFT and RIGHT keys to move and";
        instructions2 = "the SPACE bar to jump.";
        
        profLines[0] = "It's about time! Let me see that project.";
        profLines[1] = "Hmmm. . . I suppose it's passable.";
        profLines[2] = "However, you were LATE!!!  HALF OFF!!!";
        
        pressSpace = "press the SPACE bar to continue";
        
        time =  "TIME               :";
        score = "SCORE           :";
        top =   "HIGH SCORE :";
        again = "press SPACE to play again";
        
        animator.start();
    }
    
    public void paint(Graphics g) 
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        
        /* When the game reaches its final screen, it will use the following code to display the 
         * score and stats for the run through.
         */
        if(ending)
        {
            g2d.drawImage(endScreen, 0, 0, 300*TheFinal.SCALE, 200*TheFinal.SCALE, this);
            g2d.setFont(large);
            
            if(num > 140*slowDown)
            {
                if(finalScore >= 70)
                    g2d.drawImage(pass, 0, 0, 300*TheFinal.SCALE, 200*TheFinal.SCALE, this);
                else
                    g2d.drawImage(fail, 0, 0, 300*TheFinal.SCALE, 200*TheFinal.SCALE, this);
            }
            if(num > 20*slowDown)
            {
                g2d.drawString(time, 30*TheFinal.SCALE,70*TheFinal.SCALE);
                g2d.drawString("" + (60 - clock), 210*TheFinal.SCALE,70*TheFinal.SCALE);
            }
            if(num > 40*slowDown)
            {
                g2d.drawImage(ec.get(0).getEC(), 
                      260*TheFinal.SCALE, 75*TheFinal.SCALE, 
                      (260 + 15)*TheFinal.SCALE, (75 + 15)*TheFinal.SCALE, 
                      0,0, 
                      22,22,
                      this); 
                g2d.drawString(hero.ec + "/10", 210*TheFinal.SCALE,90*TheFinal.SCALE);
            }
            if(num > 60*slowDown)
            {
                if(num == 140*slowDown)
                {
                    finalScore += hero.ec*5 + ecAdder;
                    hero.ec = 0;
                    ecAdder = 0;
                }
                if(hero.ec > 0 && ecAdder == 0)
                {
                    ecAdder = 5;
                    hero.ec--;
                }
                if(ecAdder > 0 && num > 65*slowDown)
                {
                    finalScore++;
                    ecAdder--;
                    num-=5*slowDown;
                }
                g2d.drawString(score, 30*TheFinal.SCALE,110*TheFinal.SCALE);
                g2d.drawString("" + finalScore, 210*TheFinal.SCALE,110*TheFinal.SCALE);
            }
            if(num > 80*slowDown)
            {
                g2d.drawString(top, 30*TheFinal.SCALE,130*TheFinal.SCALE);
                g2d.drawString("" + topScore, 210*TheFinal.SCALE,130*TheFinal.SCALE);
                if(finalScore > topScore)
                    topScore = finalScore;
            }
            if(num > 100*slowDown)
            {
                g2d.setFont(xlarge);
                
                if(finalScore > 100)
                {
                    g2d.setColor(Color.blue);
                    g2d.drawString("A+", 250*TheFinal.SCALE,170*TheFinal.SCALE);
                }
                else if(finalScore >= 90)
                {
                    g2d.setColor(Color.green);
                    g2d.drawString("A", 250*TheFinal.SCALE,170*TheFinal.SCALE);
                }
                else if(finalScore >= 80)
                {
                    g2d.setColor(Color.green);
                    g2d.drawString("B", 250*TheFinal.SCALE,170*TheFinal.SCALE);
                }
                else if(finalScore >= 70)
                {
                    g2d.setColor(Color.yellow);
                    g2d.drawString("C", 250*TheFinal.SCALE,170*TheFinal.SCALE);
                }
                else if(finalScore >= 60)
                {
                    g2d.setColor(Color.orange);
                    g2d.drawString("D", 250*TheFinal.SCALE,170*TheFinal.SCALE);
                }
                else
                {
                    g2d.setColor(Color.red);
                    g2d.drawString("F", 250*TheFinal.SCALE,170*TheFinal.SCALE);
                }
            }
            if(num > 160*slowDown)
            {
                
                g2d.setFont(small);
                g2d.setColor(Color.white);
                g2d.drawString(again, 90*TheFinal.SCALE,180*TheFinal.SCALE);
            }
            num++;
        }
        
        /* After the player has run through the main course, the following code will be used to
         * dispay the class room and a short dialog with the proffesor.
         */
        else if(endGame)
        {
            g2d.drawImage(finalFight, 0, 0, 300*TheFinal.SCALE, 200*TheFinal.SCALE, this);
            
            g2d.drawImage(hero.getImage(), 
                          (100-hero.width)*TheFinal.SCALE, (140-hero.height)*TheFinal.SCALE, 
                          100*TheFinal.SCALE, 140*TheFinal.SCALE, 
                          hero.getImgX(), hero.getImgY(), 
                          hero.getImgXW(), hero.getImgYH(), 
                          this); 
            g2d.drawImage(prof.getImage(), 
                          prof.getBX()*TheFinal.SCALE, (140-prof.height)*TheFinal.SCALE, 
                          prof.getBXW()*TheFinal.SCALE, 140*TheFinal.SCALE, 
                          prof.getImgX(), prof.getImgY(), 
                          prof.getImgXW(), prof.getImgYH(), 
                          this); 
            if(num3 >= 1)
            {
                g2d.setFont(small);
                g2d.setColor(Color.black);
                g2d.drawString("SCORE : " + finalScore, 110*TheFinal.SCALE,20*TheFinal.SCALE);
            }
            if(!skip2)
            {
                g2d.setFont(small);
                g2d.setColor(Color.white);
                g2d.fillRect(6*TheFinal.SCALE,143*TheFinal.SCALE,291*TheFinal.SCALE,42*TheFinal.SCALE);
                g2d.setColor(Color.blue);
                g2d.fillRect(9*TheFinal.SCALE,146*TheFinal.SCALE,285*TheFinal.SCALE,36*TheFinal.SCALE);
                g2d.setColor(Color.white);
                g2d.drawString(placeHolder, 30*TheFinal.SCALE,160*TheFinal.SCALE);

                g2d.drawString(pressSpace, 70*TheFinal.SCALE,180*TheFinal.SCALE);
                
            }
        }
        
        // The following code is used to display the main course of the game and all of the elements therein.
        
        else if(inGame)
        {
            
            g2d.drawImage(background.getLayer1(), 
                          background.getX()*TheFinal.SCALE, background.getY()*TheFinal.SCALE, 
                          background.getXW()*TheFinal.SCALE, background.getYH()*TheFinal.SCALE, 
                          background.layer1X, background.layer1Y, 
                          background.layer1XW, background.layer1YH, 
                          this); 
            g2d.drawImage(background.getLayer2(), 
                          background.getX()*TheFinal.SCALE, background.getY()*TheFinal.SCALE, 
                          background.getXW()*TheFinal.SCALE, background.getYH()*TheFinal.SCALE, 
                          background.layer2X, background.layer2Y, 
                          background.layer2XW, background.layer2YH, 
                          this); 
            g2d.drawImage(background.getImage(), 
                          background.getX()*TheFinal.SCALE, background.getY()*TheFinal.SCALE, 
                          background.getXW()*TheFinal.SCALE, background.getYH()*TheFinal.SCALE, 
                          background.getImgX(), background.getImgY(), 
                          background.getImgXW(), background.getImgYH(), 
                          this); 
            for (FrisbeeGuy i : frisbeeGuys)
            {
                g2d.drawImage(i.getFrisbeeGuy(), 
                              i.getBX()*TheFinal.SCALE, i.by*TheFinal.SCALE, 
                              i.getBXW()*TheFinal.SCALE, i.byH*TheFinal.SCALE, 
                              i.imgX, i.imgY, 
                              i.imgXW, i.imgYH, 
                              this); 
            }
            for (MusicGuy i : musicGuys)
            {
                g2d.drawImage(i.getMusicGuy(), 
                              i.getBX()*TheFinal.SCALE, i.by*TheFinal.SCALE, 
                              i.getBXW()*TheFinal.SCALE, i.byH*TheFinal.SCALE, 
                              i.imgX, i.imgY, 
                              i.imgXW, i.imgYH, 
                              this); 
            }
            g2d.drawImage(hero.getImage(), 
                          hero.getBX()*TheFinal.SCALE, hero.by*TheFinal.SCALE, 
                          hero.getBXW()*TheFinal.SCALE, hero.byH*TheFinal.SCALE, 
                          hero.getImgX(), hero.getImgY(), 
                          hero.getImgXW(), hero.getImgYH(), 
                          this); 
            for (EC i : ec)
            {
                g2d.drawImage(i.getEC(), 
                              i.bx*TheFinal.SCALE, i.by*TheFinal.SCALE, 
                              i.bxW*TheFinal.SCALE, i.byH*TheFinal.SCALE, 
                              i.imgX, i.imgY, 
                              i.imgXW, i.imgYH, 
                              this); 
            }
            
            for (Frisbee i : frisbees)
            {
                g2d.drawImage(i.getFrisbee(), 
                              i.getBX()*TheFinal.SCALE, i.by*TheFinal.SCALE, 
                              i.getBXW()*TheFinal.SCALE, i.byH*TheFinal.SCALE, 
                              i.imgX, i.imgY, 
                              i.imgXW, i.imgYH, 
                              this); 
            }
            g2d.drawImage(background.getForeground(), 
                          background.getX()*TheFinal.SCALE, background.getY()*TheFinal.SCALE, 
                          background.getXW()*TheFinal.SCALE, background.getYH()*TheFinal.SCALE, 
                          background.getImgX(), background.getImgY(), 
                          background.getImgXW(), background.getImgYH(), 
                          this); 
            g2d.drawImage(ec.get(0).getEC(), 
                          32*TheFinal.SCALE, 165*TheFinal.SCALE, 
                          (32 + 15)*TheFinal.SCALE, (165 + 15)*TheFinal.SCALE, 
                          0,0, 
                          22,22,
                          this); 
            String s = "x " + hero.ec;
            String t = "TIME: " + clock;
            if (num%50 == 0 && clock != 0)
                clock--;
            num++;
            
            g2d.setColor(Color.white);
            g2d.setFont(small);
            g2d.drawString(s, 50*TheFinal.SCALE, 177*TheFinal.SCALE);
            if (clock == 0)
            {
                g2d.setColor(Color.red);
                g2d.drawString("TIME: 0", 230*TheFinal.SCALE, 177*TheFinal.SCALE);
            }
            else
                g2d.drawString(t, 230*TheFinal.SCALE, 177*TheFinal.SCALE);
            
            // This section of code displays the first dialog box.
            
            if(!skip)
            {
                g2d.setColor(Color.white);
                g2d.fillRect(30*TheFinal.SCALE,30*TheFinal.SCALE,240*TheFinal.SCALE,140*TheFinal.SCALE);
                g2d.setColor(Color.blue);
                g2d.fillRect(35*TheFinal.SCALE,35*TheFinal.SCALE,230*TheFinal.SCALE,130*TheFinal.SCALE);
                g2d.setColor(Color.white);
                g2d.drawString(first1, 50*TheFinal.SCALE,50*TheFinal.SCALE);
                g2d.drawString(first2, 50*TheFinal.SCALE,60*TheFinal.SCALE);
                g2d.drawString(first3, 50*TheFinal.SCALE,70*TheFinal.SCALE);
                g2d.drawString(first4, 50*TheFinal.SCALE,80*TheFinal.SCALE);
                g2d.drawString(first5, 50*TheFinal.SCALE,90*TheFinal.SCALE);
                g2d.drawString(instructions1, 50*TheFinal.SCALE,120*TheFinal.SCALE);
                g2d.drawString(instructions2, 50*TheFinal.SCALE,130*TheFinal.SCALE);
                g2d.drawString(pressSpace, 70*TheFinal.SCALE,160*TheFinal.SCALE);
                
            }
        }
        
        // This code displays the opening screen.
        else
        {
            g2d.drawImage(openingScreen, 0, 0, 300*TheFinal.SCALE, 200*TheFinal.SCALE, this);
        }
        
        g.dispose();
    }
    
    public void run()
    {
        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (true) 
        {
            if(finish)
                break;
            else if(ending)
                repaint();
            else if(endGame)
            {
                if(ending)
                {
                    num = 0;
                    if(clock == 0)
                        finalScore /= 2;
                }
                if(num3 == 2)
                    prof.profFrame(0);
                else
                    prof.profFrame(2);
                num2++;
                
                if(!skip2)
                {
                    if(num2%100 > 50)
                    {
                        if(num3 == 2)
                            prof.profFrame(1);
                        else
                            prof.profFrame(3);
                    }
                    placeHolder = profLines[num3];
                }
                
                else
                {
                    if(clock == 0)
                    {
                        if(decriment == 0)
                            decriment = 200;
                        if(decriment > 165)
                        {
                            hero.heroFrame(20*hero.SPEED);
                            finalScore--;
                            decriment--;
                        }
                        else if(decriment > 1)
                        {
                            hero.heroFrame(20*hero.SPEED);
                            decriment--;
                        }
                        if(decriment == 1)
                            ending = true;
                    }
                    else
                    {
                        if(decriment == 0)
                            decriment = 200;
                        if(decriment > 1)
                        {
                            hero.heroFrame(21*hero.SPEED);
                            decriment--;
                        }
                        if(decriment == 1)
                            ending = true;
                    }
                }
                repaint();
            }
            else if(inGame)
            {
                background.adjust(hero);
                collisions();
                if(endGame)
                {
                    num = 0;
                    hero.heroFrame(0);
                }
                else
                {
                    repaint();
                    while(!skip)
                    {
                        try 
                        {
                            animator.sleep(1);
                        } 
                        catch (InterruptedException e) 
                        {
                            System.out.println("interrupted");
                        }
                    }
                }
            }
            else
                repaint();
            
            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0)
                sleep = 2;
            try 
            {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }

            beforeTime = System.currentTimeMillis();
        }
    }
    
    public void collisions()
    {
        for (EC i: ec)
        {
            if(hero.getBounds().intersects(i.getBounds()))
            {
                hero.ec++;
                ec.remove(i);
                break;
            }
        }
        
        for (FrisbeeGuy i: frisbeeGuys)
        {
            for (int z = frisbees.size() - 1; z >= 0 ; z--)
            {
                if(i.getBounds().intersects(frisbees.get(z).getBounds()))
                {
                    i.clock = 0;
                    i.frame = 0;
                    frisbees.remove(z);
                }
            }
        }
        
        if(hero.getBounds().intersects(csDoor))
            endGame = true;
    }
    
    // This class handles all of the user input.
    
    public class Controller extends KeyAdapter
    {
        public void keyPressed(KeyEvent e)
        {
            int key = e.getKeyCode();
            
            if(ending && key == KeyEvent.VK_SPACE)
            {
                if (num < 140*slowDown)
                    num = 140*slowDown;
                else
                {
                    finish = true;
                    new TheFinal();
                }
            }
            else if(endGame)
            {
                if(key == KeyEvent.VK_SPACE && !skip2)
                {
                    if(num3 >= 2)
                        skip2 = true;
                    else if(num3 >= 1 && clock !=0)
                        skip2 = true;
                    else
                        num3++;
                }
            }
            else if(inGame)
            {
                if(key == KeyEvent.VK_SPACE && !skip)
                    skip = true;
                else
                    hero.keyPressed(e);
            }
            else
            {
                if(key == KeyEvent.VK_SPACE)
                    inGame = true;
            }
        }
        
        public void keyReleased(KeyEvent e)
        {
            if(inGame)
                hero.keyReleased(e);
        }
    } // end of Controller Class
} // end of Screen