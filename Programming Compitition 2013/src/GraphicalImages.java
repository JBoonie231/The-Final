import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public abstract class GraphicalImages
{
    public static final int SPEED = 15;// Animation speed
    protected String imageFileName;
    
    protected int x, y, xW, yH, imgX, imgY, imgXW, imgYH;
    protected int width, height;
    protected Image image;
    protected boolean flipped;
    
    public Image getImage()
    {
        return image;
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public int getXW()
    {
        return xW;
    }
    
    public int getYH()
    {
        return yH;
    }
    
    public int getImgX()
    {
        return imgX;
    }
    
    public int getImgY()
    {
        return imgY;
    }
    
    public int getImgXW()
    {
        return imgXW;
    }
    
    public int getImgYH()
    {
        return imgYH;
    }
    
    public int getWidth()
    {
        return width;
    }
    
    public int getHeight()
    {
        return height;
    }
    
    public boolean isFlipped()
    {
        return flipped;
    }
    
    public void setFlipped(boolean f)
    {
        flipped = f;
    }
    
    public Rectangle getBounds()
    {
        return new Rectangle(x, y, width, height);
    }
} // end of GraphicalImages class
