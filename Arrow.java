import processing.core.PApplet;
public class Arrow extends Floater  
{   
    public Arrow(PApplet applet, int x, int y, char o, boolean grey, int speed, int FPB_, char c)
    {
        super(applet);
        setX(x); setY(y); setOrientation(o); setColor(0); setIsGrey(grey); setDirectionY(-1 * (speed)); count = 0; FPB = FPB_;
        if (grey) {
            img = ddr.arrowGreyImage;
            img2 = img.get(64, 0, 64, 64);
            img3 = img.get(128, 0, 64, 64);
            img = img.get(0, 0, 64, 64);
        }
        else {
            img = ddr.arrowImage;
            if (c == 'R') {
                for (int i = 0; i < 16; i++) {
                    arrowImgs[i] = img.get(960 - (i * 64), 960  - (0 * 64), 64, 64);    
                }
            }
            if (c == 'B') {
                for (int i = 0; i < 16; i++) {
                    arrowImgs[i] = img.get(960 - (i * 64), 960  - (1 * 64), 64, 64);    
                }
            }
            if (c == 'G') {
                for (int i = 0; i < 16; i++) {
                    arrowImgs[i] = img.get(960 - (i * 64), 960  - (2 * 64), 64, 64);    
                }

            }

            if (c == 'Y') {
                for (int i = 0; i < 16; i++) {
                    arrowImgs[i] = img.get(960 - (i * 64), 960  - (3 * 64), 64, 64);    
                }
            }
        }

    }

    public void setIsGrey(boolean b) { isGrey = b; }

    public void setX(int x) { myCenterX = x; }

    public int getX() { return (int)myCenterX; }

    public void setY(int y) { myCenterY = y; }

    public double getY() { return myCenterY; } 

    public void setDirectionY(double y) { myDirectionY = y; }

    public double getDirectionY() { return myDirectionY; }

    public void setColor(int n) { myColor = n; }

    public void setOrientation(char o) { myOrientation = o; }

    public char getOrientation() { return myOrientation; }

    public void disable() {
        for (int i = 0; i < arrowImgs.length; i++) {
            arrowImgs[i] = ddr.arrowDisabledImage;
        }
    }

    public void show() {
        if (count > 15) { count = 0; }
        applet.pushMatrix();
        applet.translate((float)myCenterX, (float)myCenterY);

        if (myOrientation == 'U') {} 
        else if (myOrientation == 'D') applet.rotate(applet.radians(180));
        else if (myOrientation == 'R') applet.rotate(applet.radians(90));
        else if (myOrientation == 'L') applet.rotate(applet.radians(270));

        if (isGrey) {
            if (applet.frameCount % FPB < (FPB - 4)) applet.image(img, 0, 0); 
            else if (applet.frameCount % FPB < (FPB - 1)) applet.image(img2, 0, 0);
            else  applet.image(img3, 0, 0); // play the arrow to the bpm... TODO: actually make it work to the bpm
        }
        else {
            applet.image(getColorArrayList()[count], 0, 0);
            if (applet.frameCount % 3 == 1) count++; // arrow changes image every 2 frames
        }

        applet.popMatrix();
    }
}