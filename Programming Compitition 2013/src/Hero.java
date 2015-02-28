import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Hero extends Sprite
{
    private String imageFileName = "Hero Sprites.gif";
    protected Screen screen;
    protected int dx, dy, jumpValue, bxV, ec, hit, num, clock;
    private boolean falling, grounded, slow;
    private Rectangle[] platforms = new Rectangle[28];
    private Rectangle[] walls = new Rectangle[14];
    private Rectangle[] leftWalls = new Rectangle[2];
    
    public Hero(Screen s)
    {
        imageFileName = "Hero Sprites.gif";
        ImageIcon ii = new ImageIcon(this.getClass().getResource(imageFileName));
        image = ii.getImage();
        
        screen = s;
        
        x = 70;
        
        bx = 150 - width;
        by = 132 - height;
        bxW = bx + width;
        byH = bx + height;
        
        grounded = true;
        
        platforms[0] = new Rectangle(0, 532, 4200, 1);
        platforms[1] = new Rectangle(1325, 390, 735, 1);
        platforms[2] = new Rectangle(3533, 269, 165, 1);
        platforms[3] = new Rectangle(848, 475, 66, 1);
        platforms[4] = new Rectangle(1125, 475, 66, 1);
        platforms[5] = new Rectangle(2083, 475, 66, 1);
        platforms[6] = new Rectangle(2293, 475, 66, 1);
        platforms[7] = new Rectangle(3347, 475, 66, 1);
        platforms[8] = new Rectangle(830, 438, 39, 1);
        platforms[9] = new Rectangle(1191, 438, 39, 1);
        platforms[10] = new Rectangle(2116, 438, 39, 1);
        platforms[11] = new Rectangle(3401, 438, 39, 1);
        platforms[12] = new Rectangle(3700, 438, 39, 1);
        platforms[13] = new Rectangle(3765, 342, 39, 1);
        platforms[14] = new Rectangle(789, 413, 50, 1);
        platforms[15] = new Rectangle(1217, 413, 50, 1);
        platforms[16] = new Rectangle(2142, 413, 50, 1);
        platforms[17] = new Rectangle(3427, 413, 50, 1);
        platforms[18] = new Rectangle(3726, 413, 50, 1);
        platforms[19] = new Rectangle(3728, 317, 50, 1);
        platforms[20] = new Rectangle(743, 375, 67, 1);
        platforms[21] = new Rectangle(1250, 375, 67, 1);
        platforms[22] = new Rectangle(2175, 375, 67, 1);
        platforms[23] = new Rectangle(3460, 375, 67, 1);
        platforms[24] = new Rectangle(3759, 375, 67, 1);
        platforms[25] = new Rectangle(3678, 279, 67, 1);
        platforms[26] = new Rectangle(519, 390, 189, 1);
        platforms[27] = new Rectangle(3975, 390, 225, 1);
        
        walls[0] = new Rectangle(0, 392, 24, 148);
        walls[1] = new Rectangle(658, 422, 5, 64);
        walls[2] = new Rectangle(667, 391, 34, 32);
        walls[3] = new Rectangle(1325, 391, 36, 158);
        walls[4] = new Rectangle(2006, 423, 10, 63);
        walls[5] = new Rectangle(2018, 391, 35, 32);
        walls[6] = new Rectangle(2451, 391, 48, 32);
        walls[7] = new Rectangle(2489, 425, 8, 61);
        walls[8] = new Rectangle(3195, 391, 39, 32);
        walls[9] = new Rectangle(3190, 423, 5, 63);
        walls[10] = new Rectangle(3541, 282, 148, 12);
        walls[11] = new Rectangle(3982, 391, 47, 33);
        walls[12] = new Rectangle(4022, 425, 5, 61);
        walls[13] = new Rectangle(4194, 391, 6, 158);
        
        leftWalls[0] = new Rectangle(518, 289, 32, 112);
        leftWalls[1] = new Rectangle(1355, 404, 20, 133);
        
    }
    
    public void collisionCheck()
    {
        if (falling && platformCheck())
        {
            grounded = true;
            falling = false;
            jumpValue = 0;
            frame = 0;
        }
        if (grounded && !platformCheck())
        {
            grounded = false;
            falling = true;
        }
        if(!grounded)
        {
            for(int i = 0; i < walls.length; i++)
            {
                if (getHeadBounds().intersects(walls[i]))
                    falling = true;
            }
        }
        for(Frisbee i: screen.frisbees)
        {
            if (getBounds().intersects(i.getBounds()))
            {
                hit = 100;
                falling = true;
            }
        }
        slow = false;
        for(MusicGuy i: screen.musicGuys)
        {
            if (getBounds().intersects(i.getBounds()))
                slow = true;
        }
        
    }
    
    public boolean platformCheck()
    {
        for(int i = 0; i < platforms.length; i++)
        {
            if (getFootBounds().intersects(platforms[i]))
                return true;
        }
        for(Frisbee i: screen.frisbees)
        {
            if (getFootBounds().intersects(i.getPlatform()))
                return true;
        }
        return false;
    }
    
    public boolean wallsCheck()
    {
        for(int i = 0; i < walls.length; i++)
        {
            if (getBounds().intersects(walls[i]))
                return true;
        }
        return false;
    }
    
    public void move()
    {
        collisionCheck();
        
        if (hit != 0)
        {
            if (hit > 90)
                frame = 19*SPEED;
            else if (hit > 10)
                frame = 20*SPEED;
            else
                frame = 19*SPEED;
            hit--;
        }
        else
        {
            if (!grounded)
            {
                if (falling)
                {
                    frame = 15*SPEED;
                    y++;
                }
                else
                    jump();
            }
            
            if((x <= 0 && dx + bxV < 0) || (x + 300 >= 4200 && dx + bxV > 0) || bxV != 0)
            {
                bxV+=dx;
                if (bxV < -90)
                    bxV = -90;
                if (bxV > 144)
                    bxV = 144;
            }
            else
                x+=dx;
            
            y+=dy;
            
            if (wallsCheck())
            {
                x-=dx;
            }
            for(Rectangle i: leftWalls)
            {
                if(getBounds().intersects(i.getBounds()))
                    x = i.x + i.width - bx;
            }

            if (grounded)
            {
                frame+=dx;
                if (frame > 0 && dx < 0)
                    frame = dx;
                if (frame < 0 && dx > 0)
                    frame = dx + 2*SPEED;
                if (frame < -11*SPEED || frame > 11*SPEED)
                    frame = dx - 2*SPEED;
            }
            else
            {
                if(!falling)
                {
                    if (jumpValue > 20)
                        frame = 14*SPEED;
                    else if (jumpValue > 10)
                        frame = 13*SPEED;
                    else
                        frame = 12*SPEED;
                }
                else
                        frame = 15*SPEED;
            }
            if (slow)
            {
                falling = true;
                
                if(clock%10 == 0)
                    num++;
                if(num%2 == 0)
                    frame = 22*SPEED;
                else
                    frame = 23*SPEED;
                if(clock%3 != 0)
                    x-=dx;
                clock++;
            }
        }// end of hit else
        
        heroFrame(frame);
    }
    
    public void heroFrame(int f)
    {
        // stand frame
        if (f == 0)
        {
            imgX = 0;
            imgY = 12;
            width = 30;
            height = 34;
        }
        // run frames
        if (f == 1 || f == -1)
        {
            imgX = 31;
            imgY = 12;
            width = 30;
            height = 34;
        }
        if (f == 2*SPEED || f == -2*SPEED)
        {
            imgX = 61;
            imgY = 12;
            width = 23;
            height = 34;
        }
        if (f == 3*SPEED || f == -3*SPEED)
        {
            imgX = 84;
            imgY = 11;
            width = 26;
            height = 35;
        }
        if (f == 4*SPEED || f == -4*SPEED)
        {
            imgX = 110;
            imgY = 12;
            width = 31;
            height = 34;
        }
        if (f == 5*SPEED || f == -5*SPEED)
        {
            imgX = 142;
            imgY = 13;
            width = 34;
            height = 33;
        }
        if (f == 6*SPEED || f == -6*SPEED)
        {
            imgX = 176;
            imgY = 13;
            width = 28;
            height = 33;
        }
        if (f == 7*SPEED || f == -7*SPEED)
        {
            imgX = 205;
            imgY = 12;
            width = 25;
            height = 34;
        }
        if (f == 8*SPEED || f == -8*SPEED)
        {
            imgX = 231;
            imgY = 11;
            width = 27;
            height = 35;
        }
        if (f == 9*SPEED || f == -9*SPEED)
        {
            imgX = 259;
            imgY = 12;
            width = 28;
            height = 34;
        }
        if (f == 10*SPEED || f == -10*SPEED)
        {
            imgX = 290;
            imgY = 13;
            width = 34;
            height = 33;
        }
        if (f == 11*SPEED || f == -11*SPEED)
        {
            imgX = 324;
            imgY = 13;
            width = 29;
            height = 33;
        }
        // jump frames
        if (f == 12*SPEED)
        {
            imgX = 353;
            imgY = 9;
            width = 24;
            height = 37;
        }
        if (f == 13*SPEED)
        {
            imgX = 382;
            imgY = 5;
            width = 15;
            height = 41;
        }
        if (f == 14*SPEED)
        {
            imgX = 403;
            imgY = 0;
            width = 19;
            height = 46;
        }
        if (f == 15*SPEED)
        {
            imgX = 425;
            imgY = 5;
            width = 23;
            height = 41;
        }
        if (f == 16*SPEED)
        {
            imgX = 450;
            imgY = 4;
            width = 27;
            height = 42;
        }
        if (f == 17*SPEED)
        {
            imgX = 482;
            imgY = 8;
            width = 24;
            height = 38;
        }
        if (f == 18*SPEED)
        {
            imgX = 507;
            imgY = 14;
            width = 30;
            height = 32;
        }
        // Damage frames
        if (f == 19*SPEED)
        {
            imgX = 537;
            imgY = 10;
            width = 26;
            height = 36;
        }
        if (f == 20*SPEED)
        {
            imgX = 566;
            imgY = 8;
            width = 29;
            height = 38;
        }
        // Victory frame
        if (f == 21*SPEED)
        {
            imgX = 597;
            imgY = 1;
            width = 28;
            height = 45;
        }
        // Covering ears frames
        if (f == 22*SPEED)
        {
            imgX = 625;
            imgY = 10;
            width = 21;
            height = 36;
        }
        if (f == 23*SPEED)
        {
            imgX = 646;
            imgY = 10;
            width = 22;
            height = 36;
        }
        xW = x + width;
        yH = y + height;
        bx = 150 - width + bxV;
        by = 132 - height;
        bxW = bx + width;
        byH = by + height;
        imgXW = imgX + width;
        imgYH = imgY + height;
    }
    
    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_SPACE && grounded)
        {
            jump();
            grounded = false;
        }
        
        if (key == KeyEvent.VK_LEFT)
        {
            dx = -1;
            flipped = true;
        }
        
        if (key == KeyEvent.VK_RIGHT)
        {
            dx = 1;
            flipped = false;
        }
            
    }
    
    public void jump()
    {
        if (!falling && jumpValue < 70)
        {
            jumpValue++;
            y--;
        }
        else
            falling = true;
    }
    
    public void keyReleased(KeyEvent e)
    {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_SPACE)
            falling = true;
        
        if (key == KeyEvent.VK_LEFT)
        {
            dx = 0;
            frame = 0;
        }
        
        if (key == KeyEvent.VK_RIGHT)
        {
            dx = 0;
            frame = 0;
        }
            
    }
    
    public Rectangle getBounds()
    {
        return new Rectangle(bx + x,by + y + 400, width, height);
    }
    
    public Rectangle getFootBounds()
    {
        return new Rectangle(bx + x,by + y + 400 + height - 1, width, 1);
    }
    
    public Rectangle getHeadBounds()
    {
        return new Rectangle(bx + x + 1,by + y + 400, width - 1, 1);
    }
    
} // end of Hero class
