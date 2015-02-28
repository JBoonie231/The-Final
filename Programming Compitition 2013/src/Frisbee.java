import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Frisbee extends Sprite
{
    private Image frisbee;
    protected int clock, num;
    protected Background background;
    
    public Frisbee(int xValue, int yValue, boolean flippedValue, Background bg)
    {
        ImageIcon ii = new ImageIcon(this.getClass().getResource("Frisbee.gif"));
        frisbee = ii.getImage();
        
        background = bg;
        
        flipped = flippedValue;
        
        if (flipped)
        {
            x = xValue - 12;
            y = yValue + 8;
        }
        else
        {
            x = xValue + 39;
            y = yValue + 8;
        }
        
        imgX = 0;
        imgY = 0;
        
        width = 12;
        height = 3;
        
        xW = x + width;
        yH = y + height;
        
        imgXW = imgX + width;
        imgYH = imgY + height;
        
    }
    
    public Image getFrisbee()
    {
        if (flipped)
            x-=1;
        else
            x+=1;
        
        bx = x - background.imgX;
        by = y - background.imgY;
        bxW = bx + width;
        byH = by + height;
        
        imgX = 12*(clock%5);
        imgXW = imgX + width;
        if(num%10 == 9)
            clock++;
        num++;
        return frisbee;    
    }
    
    public Rectangle getPlatform()
    {
        return new Rectangle(x, y, width, 1);
    }
    
    public Rectangle getBounds()
    {
        return new Rectangle(x, y + 1, width, height - 1);
    }
} // end of Frisbee
