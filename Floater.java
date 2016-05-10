import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;
public abstract class Floater //Do NOT modify the Floater class! Make changes in the SpaceShip class 
{   
    protected PApplet applet;
    protected int myColor;   
    protected double myCenterX, myCenterY; //holds center coordinates   
    protected double myDirectionY;
    protected char myOrientation;
    protected boolean isGrey;
    protected PImage img, img2, img3;
    protected PImage[] arrowImgs = new PImage[16];
    protected int count;
    public Floater(PApplet applet_)
    {
        applet = applet_;
    }
    
    abstract public void setOrientation(char o);
    
    abstract public char getOrientation();
    
    abstract public void setX(int x);  

    abstract public int getX();   

    abstract public void setY(int y);   

    abstract public int getY();   
    
    abstract public void setDirectionY(double y);
    
    abstract public double getDirectionY();
    
    public void setColor(int n) { myColor = n; }
    
    public PImage[] getColorArrayList() { return arrowImgs; }

    public void move()   //move the floater in the current direction of travel
    {      
        //change the x and y coordinates by myDirectionX and myDirectionY          
        myCenterY += myDirectionY;     

        //wrap around screen    
        if(myCenterX > applet.width)
        {     
            myCenterX = 0;    
        }    
        else if (myCenterX < 0)
        {     
            myCenterX = applet.width;    
        }    
        if(myCenterY > applet.height)
        {    
            myCenterY = 0;    
        }   
        else if (myCenterY < 0)
        {     
            myCenterY = applet.height;    
        }   
    }   

    public void show ()  //Draws the floater at the current position  
    {
        
    }   
} 
