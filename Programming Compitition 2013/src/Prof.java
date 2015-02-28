import java.awt.Image;

import javax.swing.ImageIcon;

public class Prof extends Sprite
{
    private String imageFileName = "Prof.gif";
    
    public Prof()
    {
        imageFileName = "Prof.gif";
        ImageIcon ii = new ImageIcon(this.getClass().getResource(imageFileName));
        image = ii.getImage();
        
        imgX = 84;
        imgY = 1;
        width = 34;
        height = 46;
        
        bx = 250 - width;
        bxW = 250;
        
        imgXW = imgX + width;
        imgYH = imgY + height;
        
        flipped = true;
    }
    
    public void profFrame(int f)
    {
        // pointing
        if (f == 0)
        {
            imgX = 0;
            imgY = 1;
            width = 42;
            height = 46;
        }
        // pointing, mouth open
        if (f == 1)
        {
            imgX = 42;
            imgY = 1;
            width = 42;
            height = 46;
        }
        // standing
        if (f == 2)
        {
            imgX = 84;
            imgY = 1;
            width = 34;
            height = 46;
        }
        // standing, mouth open
        if (f == 3)
        {
            imgX = 118;
            imgY = 1;
            width = 34;
            height = 46;
        }
        
        imgXW = imgX + width;
        imgYH = imgY + height;
        bx = 250 - width;
    }
    
} // end of Prof class
