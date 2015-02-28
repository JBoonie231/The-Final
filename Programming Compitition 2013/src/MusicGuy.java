import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class MusicGuy extends Sprite
{
    private Image musicGuy;
    protected int clock, num;
    protected Background background;
    
    public MusicGuy(int xValue, int yValue, boolean flippedValue, Background bg)
    {
        ImageIcon ii = new ImageIcon(this.getClass().getResource("Music Guy.gif"));
        musicGuy = ii.getImage();
        
        background = bg;
        
        x = xValue;
        y = yValue;
        
        flipped = flippedValue;
        
        imgX = 0;
        imgY = 0;
        
        width = 33;
        height = 32;
        
        xW = x + width;
        yH = y + height;
        
        imgXW = imgX + width;
        imgYH = imgY + height;
        
    }
    
    public Image getMusicGuy()
    {
        bx = x - background.imgX;
        by = y - background.imgY;
        bxW = bx + width;
        byH = by + height;
        
        imgX = 33*(clock%2);
        imgXW = imgX + width;
        if(num%10 == 9)
            clock++;
        num++;
        return musicGuy;    
    }
    
    public Rectangle getBounds()
    {
        return new Rectangle(x - width*2, y + 10, width*6, height);
    }
} // end of MusicGuy
