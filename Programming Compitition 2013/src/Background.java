import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Background extends GraphicalImages
{
    
    private Image background, layer1, layer2, layer3, layer4, foreground;
    protected int layer2X, layer2Y, layer2XW, layer2YH;
    protected int layer1X, layer1Y, layer1XW, layer1YH;
    
    
    public Background()
    {
        ImageIcon ii = new ImageIcon(this.getClass().getResource("Player Background.png"));
        image = ii.getImage();
        
        x = 0;
        y = 0;
        width = 300;
        height = 200;
        xW = x + width;
        yH = y + height;
        imgX = 0;
        imgY = 400;
        imgXW = imgX + width;
        imgYH = imgY + height;
        
        ii = new ImageIcon(this.getClass().getResource("Player Foreground.png"));
        foreground = ii.getImage();
        
        ii = new ImageIcon(this.getClass().getResource("Player Layer 2.png"));
        layer2 = ii.getImage();
        
        layer2X = 0;
        layer2Y = 464;
        layer2XW = layer2X + width;
        layer2YH = layer2Y + height;
        
        ii = new ImageIcon(this.getClass().getResource("Player Layer 1.png"));
        layer1 = ii.getImage();
        
        layer1X = 0;
        layer1Y = 664;
        layer1XW = layer2X + width;
        layer1YH = layer2Y + height;
        
    }
    
    public void adjust(Hero hero)
    {
        hero.move();
        imgX = hero.x;
        imgY = 400 + hero.y;
        imgXW = imgX + width;
        imgYH = imgY + height;
        
        layer2X = hero.x/2;
        layer2Y = (464 + hero.y)/2;
        layer2XW = layer2X + width;
        layer2YH = layer2Y + height;
        
        layer1X = hero.x/4;
        layer1Y = (664 + hero.y)/4;
        layer1XW = layer1X + width;
        layer1YH = layer1Y + height;
        
    }
    
    public Image getForeground()
    {
        return foreground;
    }
    
    public Image getLayer2()
    {
        return layer2;
    }
    
    public Image getLayer1()
    {
        return layer1;
    }
} // end of Background
