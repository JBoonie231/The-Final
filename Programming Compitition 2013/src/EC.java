import java.awt.Image;

import javax.swing.ImageIcon;

public class EC extends Sprite
{
    private Image ec;
    protected int clock, num;
    protected Background background;
    
    public EC(int xValue, int yValue, Background bg)
    {
        ImageIcon ii = new ImageIcon(this.getClass().getResource("EC.png"));
        ec = ii.getImage();
        
        background = bg;
        
        x = xValue;
        y = yValue;
        
        imgX = 0;
        imgY = 0;
        
        width = 22;
        height = 22;
        
        xW = x + width;
        yH = y + height;
        
        imgXW = imgX + width;
        imgYH = imgY + height;
        
    }
    
    public Image getEC()
    {
        bx = x - background.imgX;
        by = y - background.imgY;
        bxW = bx + width;
        byH = by + height;
        
        imgX = 22*(clock%5);
        imgXW = imgX + width;
        if(num%10 == 9)
            clock++;
        num++;
        return ec;    
    }
} // end of EC class
