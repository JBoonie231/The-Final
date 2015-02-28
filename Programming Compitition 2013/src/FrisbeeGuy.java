import java.awt.Image;

import javax.swing.ImageIcon;

public class FrisbeeGuy extends Sprite
{
    private Image frisbeeGuy;
    private Screen screen;
    protected int clock, num;
    protected Background background;
    
    public FrisbeeGuy(int xValue, int yValue, boolean flippedValue, Background bg, Screen s)
    {
        ImageIcon ii = new ImageIcon(this.getClass().getResource("Frisbee Guy.gif"));
        frisbeeGuy = ii.getImage();
        
        screen = s;
        
        background = bg;
        
        x = xValue;
        y = yValue;
        
        flipped = flippedValue;
        
        if(flipped)
            frame = 2;
        
        imgX = 0;
        imgY = 0;
        
        width = 30;
        height = 32;
        
        xW = x + width;
        yH = y + height;
        
        imgXW = imgX + width;
        imgYH = imgY + height;
        
    }
    
    public Image getFrisbeeGuy()
    {
        bx = x - background.imgX;
        by = y - background.imgY;
        bxW = bx + width;
        byH = by + height;
        
        frisbeeGuyFrame(frame);
        
        imgXW = imgX + width;
        
        if(clock%50 == 0 && frame == 1)
            screen.frisbees.add(new Frisbee(x, y, flipped, background));
            
        if(clock%50 == 49)
            frame++;
        clock++;
        
        return frisbeeGuy;    
    }
    
    public void frisbeeGuyFrame(int f)
    {
        if (f == 0)
        {
            width = 30;
            height = 32;
            imgX = 39;
            imgY = 0;
        }
        else
        {
            width = 39;
            height = 32;
            imgX = 0;
            imgY = 0;
        }
    }
    
} // end of FrisbeeGuy
