import processing.core.PApplet;
public class Arrow extends Floater  
{   
    public Arrow(PApplet applet, int x, int y, char o, boolean grey)
    {
        super(applet);
        setX(x); setY(y); setOrientation(o); setColor(0); setIsGrey(grey); setDirectionY(-2); count = 0;
        if (grey) {
            img = ddr.arrowGreyImage;
            img2 = img.get(64, 0, 64, 64);
            img3 = img.get(128, 0, 64, 64);
            img = img.get(0, 0, 64, 64);
        }
        else {
            img = ddr.arrowImage;
            int c = (int)(Math.random() * 4);
            for (int i = 0; i < 16; i++) {
                arrowImgs[i] = img.get(960 - (i * 64), 960  - (c * 64), 64, 64);    
            }
        }

    }

    public void setIsGrey(boolean b) { isGrey = b; }

    public void setX(int x) { myCenterX = x; }

    public int getX() { return (int)myCenterX; }

    public void setY(int y) { myCenterY = y; }

    public int getY() { return (int)myCenterY; } 

    public void setDirectionY(double y) { myDirectionY = y; }

    public double getDirectionY() { return myDirectionY; }

    public void setColor(int n) { myColor = n; }

    public void setOrientation(char o) { myOrientation = o; }

    public char getOrientation() { return myOrientation; }

    public void show() {
        if (count > 15) { count = 0; }
        if (myOrientation == 'U') {
            applet.pushMatrix();
            applet.translate((float)myCenterX, (float)myCenterY);
            if (isGrey) {
                if (applet.frameCount % 30 < 26) applet.image(img, 0, 0); 
                else if (applet.frameCount % 30 < 28) applet.image(img2, 0, 0);
                else  applet.image(img3, 0, 0);
            }
            else {
                if (applet.frameCount % 8 < 4) applet.image(getColorArrayList()[count++], 0, 0);
                else applet.image(getColorArrayList()[count], 0, 0);
            }
            applet.popMatrix();
        }   

        if (myOrientation == 'D') {
            applet.pushMatrix();
            applet.translate((float)myCenterX, (float)myCenterY);
            applet.rotate(applet.radians(180));
            if (isGrey) {
                if (applet.frameCount % 30 < 26) applet.image(img, 0, 0); 
                else if (applet.frameCount % 30 < 28) applet.image(img2, 0, 0);
                else  applet.image(img3, 0, 0);
            }
            else {
                if (applet.frameCount % 8 < 4) applet.image(getColorArrayList()[count++], 0, 0);
                else applet.image(getColorArrayList()[count], 0, 0);
            }
            applet.popMatrix();
        }

        if (myOrientation == 'R') {
            applet.pushMatrix();
            applet.translate((float)myCenterX, (float)myCenterY);
            applet.rotate(applet.radians(90));
            if (isGrey) {
                if (applet.frameCount % 30 < 26) applet.image(img, 0, 0); 
                else if (applet.frameCount % 30 < 28) applet.image(img2, 0, 0);
                else  applet.image(img3, 0, 0);
            }
            else {
                if (applet.frameCount % 8 < 4) applet.image(getColorArrayList()[count++], 0, 0);
                else applet.image(getColorArrayList()[count], 0, 0);
            }
            applet.popMatrix();
        }

        if (myOrientation == 'L') {
            applet.pushMatrix();
            applet.translate((float)myCenterX, (float)myCenterY);
            applet.rotate(applet.radians(270));
            if (isGrey) {
                if (applet.frameCount % 30 < 26) applet.image(img, 0, 0); 
                else if (applet.frameCount % 30 < 28) applet.image(img2, 0, 0);
                else  applet.image(img3, 0, 0);
            }
            else {
                if (applet.frameCount % 8 < 4) applet.image(getColorArrayList()[count++], 0, 0);
                else applet.image(getColorArrayList()[count], 0, 0);
            }
            applet.popMatrix();
        }
    }
}