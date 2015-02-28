/**
 *  PROJECT TITLE      : The Final
    PURPOSE OF PROJECT : 2013 MiraCosta College Programming Competition
    DATE               : 4/28/201
    AUTHOR             : Joshua M Boone
 */

import javax.swing.JFrame;

public class TheFinal extends JFrame 
{
    public static final int SCALE = 3; //multiplier for screen size
    
    public TheFinal() 
    {

        add(new Screen());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300*SCALE, 200*SCALE);
        setLocationRelativeTo(null);
        setTitle("The Final");
        setVisible(true);
        setResizable(false);
    }

    public static void main(String[] args) 
    {
        new TheFinal();
    }
} // end of TheFinal