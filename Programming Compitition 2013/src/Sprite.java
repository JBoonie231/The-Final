
public class Sprite extends GraphicalImages
{
    protected int bx, by, bxW, byH, frame;
    
    public int getBX()
    {
        if (flipped)
            return bxW;
        return bx;
    }
    
    public int getBXW()
    {
        if(flipped)
            return bx;
        return bxW;
    }
}
