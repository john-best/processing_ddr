import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.Scanner;

public class ddr extends PApplet {  
    private Arrow UArrow, LArrow, RArrow, DArrow;
    private ArrayList<Arrow> arrows = new ArrayList<Arrow>();
    public static boolean keys[] = new boolean[4];
    public static boolean keysHeld[] = new boolean[4];
    private int score, combo, speed, songDelay = 0;
    private int accuracy, BPM, BPS, FPB = -1;
    protected static PImage arrowImage, arrowGreyImage, arrowFlash, arrowHold1, arrowHold2, arrowDisabledImage;
    private SongReader songList, song;
    private String songSelection = "";
    private ArrayList<String> notes;
    private int songCounter = 0;
    private int oldColor = -1;
    private int timeOffset;
    private boolean songStarted, gameOver = false;

    public static void main(String[] args)
    {
        PApplet.main(new String[] {"ddr"});
    }

    public void settings() {
        size(800, 800);
    }

    public void setup() {
        size(800, 800); // processing 2
        frameRate(30);
        imageMode(CENTER);
        arrowGreyImage = loadImage("assets/ddr_receptor.png");
        arrowImage = loadImage("assets/ddr_note.png");  
        arrowFlash = loadImage("assets/ddr_flash.png");
        arrowHold1 = loadImage("assets/ddr_receptor_hold.png").get(0, 0, 80, 80);
        arrowHold2 = loadImage("assets/ddr_receptor_hold.png").get(80, 0, 80, 80);
        arrowDisabledImage = loadImage("assets/ddr_note_disabled.png").get(0, 0, 64, 64);
        //song = new SongReader("song1.txt");
        //song.read();
        songList = new SongReader(); // pass no argument = read entire directory
        songList.init(); // instantiate songReader

        System.out.println("Here are the list of available songs for you to choose");

        ArrayList<String> sl = songList.getSongList();
        ArrayList<String> sfl = songList.getSongFileList();

        for (int i = 0; i < sl.size(); i++) {
            System.out.println(i + ": " + sl.get(i)); // print possible songs to be played
        } 

        Scanner s = new Scanner(System.in);
        int selection = 0;
        do { 
            System.out.print("Please enter a selection (number): ");
            selection = s.nextInt();

            if (selection > sl.size() || selection < 0) System.out.println("Invalid selection!");
        } while (selection > sl.size() || selection < 0);
        
        // can only pass through if valid selection
        
        songSelection = sl.get(selection);
        song = new SongReader(sfl.get(selection));  // is a .txt file
        song.read(); // read the file, populate variables

        BPM = song.getBPM();
        notes = song.getNotes();
        speed = song.getSpeed();
        songDelay = (int)(song.getDelay() * 1000);
        BPS = BPM / 30;
        FPB = 30 / BPS;
        accuracy = -1;

        LArrow = new Arrow(this, 274, 100, 'L', true, 0, FPB, 'N');
        DArrow = new Arrow(this, 358, 100, 'D', true, 0, FPB, 'N');
        UArrow = new Arrow(this, 442, 100, 'U', true, 0, FPB, 'N');
        RArrow = new Arrow(this, 526, 100, 'R', true, 0, FPB, 'N');

        timeOffset = millis(); // offset from opening application and actually beginning the game
    }

    public void handleNotes() {
        if (0 < notes.size()) {
            while (notes.get(0).equals("")) { notes.remove(0);}
            int value = Integer.parseInt(notes.get(0).split(":")[1]);

            if (millis() - timeOffset >= value) { // check time, if the time has passed, show note
                int color = (int)(Math.random() * 4);
                while (oldColor == color) { color = (int)(Math.random() * 4); }
                oldColor = color;
                char c;
                switch (color) {
                    case 0: c = 'R'; break;
                    case 1: c = 'B'; break;
                    case 2: c = 'G'; break;
                    case 3: c = 'Y'; break;
                    default: c = 'G'; break;
                } // grouped arrows share a color
                
                /*
                 * Syntax in the file is NoteNoteNoteNote:time
                 * ex 1001:1250 for left and right arrows at 1.25 seconds elapsed
                 */

                if (notes.get(0).split(":")[0].substring(0, 1).equals("1")) {
                    arrows.add(new Arrow(this, 274, 800, 'L', false, speed, FPB, c));
                }
                if (notes.get(0).split(":")[0].substring(1, 2).equals("1")) {
                    arrows.add(new Arrow(this, 358, 800, 'D', false, speed, FPB, c));
                }
                if (notes.get(0).split(":")[0].substring(2, 3).equals("1")) {
                    arrows.add(new Arrow(this, 442, 800, 'U', false, speed, FPB, c));
                }
                if (notes.get(0).split(":")[0].substring(3, 4).equals("1")) {
                    arrows.add(new Arrow(this, 526, 800, 'R', false, speed, FPB, c));
                }
                if (notes.get(0).split(":")[0].equals("0000")) { gameOver = true; } // this is how i check for game over
                notes.remove(0); // delete note bc already shown
            }
        }
    }

    public void playMusic(String songURI) { // play music!!!
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(songURI).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void draw() {
        background(0);
        textSize(12);
        if (!gameOver) text("Score: " + score, 20, 20);
        text(songSelection, 20, 32);
        if (combo > 2) text("Combo: " + combo, 380, 220);
        if (accuracy == 0) text("BOO", 380, 200);
        if (accuracy == 1) text("GOOD", 380, 200);
        if (accuracy == 2) text("GREAT", 380, 200);
        if (accuracy == 3) text("EXCELLENT", 380, 200);   
        text("FPS: " + (int)frameRate, 20, 44);
        text("BPM: " + BPM, 20, 56);
        text(millis() - timeOffset, 20, 68);
        fill(255);

        if (gameOver) {
            textSize(64);
            text("COMPLETED!", 225, 400);
            text("Score: " + score, 225, 464);
            accuracy = -1;
            combo = 0;
        }

        if (millis() - timeOffset > songDelay && !songStarted) {
            playMusic(song.getSongURI());
            songStarted = true;
        } // play song when game starts
        
        if (!gameOver) {
            if (!keys[0])LArrow.show();
            if (!keys[1]) DArrow.show();
            if (!keys[2]) UArrow.show();
            if (!keys[3])RArrow.show();
        }

        handleNotes();

        for (int i = 0; i < arrows.size(); i++) {
            checkArrows(i);
        }

        for (int i = 0; i < arrows.size(); i++) {
            if (arrows.get(i).getY() < 0) { arrows.remove(i); accuracy = 0; score -= 100; combo = 0;}
        }
        
        for (int i = 0; i < arrows.size(); i++) {
            if (arrows.get(i).getY() < 36) arrows.get(i).disable();
        }
        
        for (int i = 0; i < arrows.size(); i++) {
            arrows.get(i).move();
            arrows.get(i).show();
        }

        if (keys[0]) { // highlight pressed arrow
            pushMatrix();
            translate(274, 100);
            rotate(radians(-90));
            image(arrowHold2, 0, 0);
            popMatrix();
        }

        if (keys[1]) {
            pushMatrix();
            translate(358, 100);
            rotate(radians(180));
            image(arrowHold2, 0, 0);
            popMatrix();
        }

        if (keys[2]) {
            pushMatrix();
            translate(442, 100);
            rotate(0);
            image(arrowHold2, 0, 0);
            popMatrix();
        }

        if (keys[3]) {
            pushMatrix();
            translate(526, 100);
            rotate(radians(90));
            image(arrowHold2, 0, 0);
            popMatrix();
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
        double distance = Math.abs(a.getY() - 100);
        boolean isTopArrow = true;
        for (int k = 0; k < arrows.size(); k++) {
            if (a.getY() > arrows.get(k).getY() && a != arrows.get(k)) {
                isTopArrow = false; // check if the arrow is the top arrow
            }
        }

        if (isTopArrow) {
            if(a.getOrientation() == 'L' && keys[0] && !keysHeld[0]) { // delete arrow and calculate score
                if (distance < 64) {
                    if (distance < 16) {
                        combo++; score += (1000 * (combo + 1)); accuracy = 3; // more combos, higher score!
                        pushMatrix();
                        translate(274, 100);
                        rotate(radians(270));
                        image(arrowFlash, 0, 0);
                        popMatrix();
                    }
                    else if (distance < 24) {combo++; score += (800 * (combo + 1));  accuracy = 2;}
                    else if (distance < 32) {score += (700 * (combo + 1)); combo = 0; accuracy = 1;}
                    else {combo = 0; accuracy = 0;}
                    arrows.remove(i);
                }
                keysHeld[0] = true;
            }   

            if(a.getOrientation() == 'D' && keys[1] && !keysHeld[1]) {

                if (distance < 64) {
                    if (distance < 16) {
                        combo++; score += (1000 * (combo + 1)); accuracy = 3;
                        pushMatrix();
                        translate(358, 100);
                        rotate(radians(180));
                        image(arrowFlash, 0, 0);
                        popMatrix();
                    }
                    else if (distance < 24) {combo++; score += (800 * (combo + 1));  accuracy = 2;}
                    else if (distance < 32) {score += (700 * (combo + 1)); combo = 0; accuracy = 1;}
                    else {combo = 0; accuracy = 0;}
                    arrows.remove(i);
                }
                keysHeld[1] = true;
            }

            if(a.getOrientation() == 'U' && keys[2] && !keysHeld[2]) {

                if (distance < 64) {
                    if (distance < 16) {
                        combo++; score += (1000 * (combo + 1)); accuracy = 3;
                        pushMatrix();
                        translate(442, 100);
                        image(arrowFlash, 0, 0);
                        popMatrix();
                    }
                    else if (distance < 24) {combo++; score += (800 * (combo + 1));  accuracy = 2;}
                    else if (distance < 32) {score += (700 * (combo + 1)); combo = 0; accuracy = 1;}
                    else {combo = 0; accuracy = 0;}
                    arrows.remove(i);
                }
                keysHeld[2] = true;
            }

            if(a.getOrientation() == 'R' && keys[3] && !keysHeld[3]) {

                if (distance < 64) {
                    if (distance < 16) {
                        combo++; score += (1000 * (combo + 1)); accuracy = 3;
                        pushMatrix();
                        translate(526, 100);
                        rotate(radians(90));
                        image(arrowFlash, 0, 0);
                        popMatrix();
                    }
                    else if (distance < 24) {combo++; score += (800 * (combo + 1));  accuracy = 2;}
                    else if (distance < 32) {score += (700 * (combo + 1)); combo = 0; accuracy = 1;}
                    else {combo = 0; accuracy = 0;}
                    arrows.remove(i);
                } 
                keysHeld[3] = true;
            }
        }
    }
}