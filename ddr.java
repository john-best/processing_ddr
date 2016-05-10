import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;

public class ddr extends PApplet {  
    private Arrow UArrow, LArrow, RArrow, DArrow;
    private ArrayList<Arrow> arrows = new ArrayList<Arrow>();
    public static boolean keys[] = new boolean[4];
    public static boolean keysHeld[] = new boolean[4];
    private int score = 0;
    private int combo = 0;
    private int accuracy = -1;
    protected static PImage arrowImage, arrowGreyImage;
    public static void main(String[] args)
    {
        PApplet.main(new String[] {"ddr"});
    }

     public void settings() {
        size(800, 800);
    }

    public void setup() {
        //size(800, 800); // processing 2
        frameRate(60);
        imageMode(CENTER);
        arrowGreyImage = loadImage("assets/ddr_receptor.png");
        arrowImage = loadImage("assets/ddr_note.png");  
        LArrow = new Arrow(this, 274, 100, 'L', true);
        DArrow = new Arrow(this, 358, 100, 'D', true);
        UArrow = new Arrow(this, 442, 100, 'U', true);
        RArrow = new Arrow(this, 526, 100, 'R', true);

        for (int i = 0; i < 3; i++) {
            arrows.add(new Arrow(this, 526, 300 + (int)(Math.random() * 300), 'R', false));
            arrows.add(new Arrow(this, 358, 300 + (int)(Math.random() * 300), 'D', false));
            arrows.add(new Arrow(this, 442, 300 + (int)(Math.random() * 300), 'U', false));
            arrows.add(new Arrow(this, 274, 300 + (int)(Math.random() * 300), 'L', false));

        }

    }

    public void draw() {
        background(255);
        text("Score: " + score, 20, 20);
        if (combo > 2) text("Combo: " + combo, 20, 32);
        if (accuracy == 0) text("MISS", 380, 200);
        if (accuracy == 1) text("GOOD", 380, 200);
        if (accuracy == 2) text("GREAT", 380, 200);
        if (accuracy == 3) text("EXCELLENT", 380, 200);   
        text("FPS: " + (int)frameRate, 20, 44);
        fill(0);
        
        UArrow.show();
        DArrow.show();
        RArrow.show();
        LArrow.show();

        for (int i = 0; i < arrows.size(); i++) {
            arrows.get(i).show();
            arrows.get(i).move();
            checkArrows(i);
        }
    }

    public void keyPressed() {
        if (keyCode == LEFT)  {keys[0] = true;}
        if (keyCode == DOWN)  {keys[1] = true;}
        if (keyCode == UP) {keys[2] = true;}
        if (keyCode == RIGHT) {keys[3] = true;}
    }

    public void keyReleased() {
        if (keyCode == LEFT)  {keys[0] = false; keysHeld[0] = false;}
        if (keyCode == DOWN)  {keys[1] = false; keysHeld[1] = false;}
        if (keyCode == UP) {keys[2] = false; keysHeld[2] = false;}
        if (keyCode == RIGHT) {keys[3] = false; keysHeld[3] = false;}
    }   

    public void checkArrows(int i) {
        Arrow a = arrows.get(i);
        float distance = dist(0, a.getY(), 0, 100);
        boolean isTopArrow = true;
        for (int k = 0; k < arrows.size(); k++) {
            if (a.getY() > arrows.get(k).getY() && a != arrows.get(k)) {
                isTopArrow = false;
            }
        }

        if (isTopArrow) {
            if(a.getOrientation() == 'L' && keys[0] && !keysHeld[0]) {
                keysHeld[0] = true;
                if (distance < 64) {
                    if (distance < 16) {score += 1000; combo++; accuracy = 3;}
                    else if (distance < 24) {score += 800; combo++; accuracy = 2;}
                    else if (distance < 32) {score += 700; combo++; accuracy = 1;}
                    else {combo = 0; accuracy = 0;}
                    arrows.remove(i);
                }
            }   

            if(a.getOrientation() == 'D' && keys[1] && !keysHeld[1]) {
                keysHeld[1] = true;
                if (distance < 64) {
                    if (distance < 16) {score += 1000; combo++; accuracy = 3;}
                    else if (distance < 24) {score += 800; combo++; accuracy = 2;}
                    else if (distance < 32) {score += 700; combo++; accuracy = 1;}
                    else {combo = 0; accuracy = 0;}
                    arrows.remove(i);
                }
            }

            if(a.getOrientation() == 'U' && keys[2] && !keysHeld[2]) {
                keysHeld[2] = true;
                if (distance < 64) {
                    if (distance < 16) {score += 1000; combo++; accuracy = 3;}
                    else if (distance < 24) {score += 800; combo++; accuracy = 2;}
                    else if (distance < 32) {score += 700; combo++; accuracy = 1;}
                    else {combo = 0; accuracy = 0;}
                    arrows.remove(i);
                }
            }

            if(a.getOrientation() == 'R' && keys[3] && !keysHeld[3]) {
                keysHeld[3] = true;
                if (distance < 64) {
                    if (distance < 16) {score += 1000; combo++; accuracy = 3;}
                    else if (distance < 24) {score += 800; combo++; accuracy = 2;}
                    else if (distance < 32) {score += 700; combo++; accuracy = 1;}
                    else {combo = 0; accuracy = 0;}
                    arrows.remove(i);
                }  
            }
        }
    }
}