import java.io.*;
import java.util.Arrays;    
import java.util.ArrayList;
public class SongReader {

    /* File should look like:
     * BPM:<BPM>
     * <line break>
     * <0/1><0/1><0/1><0/1>
     * <line break for new measure>
     * 
     * 0/1 for if arrow should show or not; hold arrows not yet implemented
     */

    private String songFile;
    private String fileContents;
    private int BPM = -1;
    private int speed = 0;
    private String songNotes;
    private String[] noteSections;
    private String[][] notes;
    private ArrayList<String> realNotes = new ArrayList<String>();
    private String songURI = "";
    private String artist = "";
    private String name = "";
    private double delay = 0;
    private ArrayList<String> songList = new ArrayList<String>();
    private ArrayList<String> songFileList = new ArrayList<String>();
    public SongReader(String songfile) {
        songFile = "songs/" + songfile;
    }

    public SongReader() {
    }

    public void init() {
        File folder = new File("songs/");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                if (listOfFiles[i].getName().startsWith("ddr_") && listOfFiles[i].getName().endsWith(".txt")) {
                    String songTitle = "";

                    try(BufferedReader br = new BufferedReader(new FileReader("songs/" + listOfFiles[i].getName()))) {
                        StringBuilder sb = new StringBuilder();
                        String line = br.readLine();

                        while (line != null) {
                            sb.append(line);
                            sb.append(System.lineSeparator());

                            if (line.startsWith("ARTIST:")) {
                                songTitle += line.split(":")[1] + " - ";
                            }

                            if (line.startsWith("TRACK:")) {
                                songTitle += line.split(":")[1];
                            }

                            line = br.readLine();
                        }
                    }
                    catch (Exception e) {
                    }

                    songList.add(songTitle);
                    songFileList.add(listOfFiles[i].getName());
                }
            }
        }

    }

    public void read() {

        try(BufferedReader br = new BufferedReader(new FileReader(songFile))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());

                if (line.startsWith("BPM:")) {
                    BPM = Integer.parseInt(line.split(":")[1]);
                }
                if (line.startsWith("SPEED:")) {
                    speed = Integer.parseInt(line.split(":")[1]);
                }
                if (line.startsWith("SONGFILE:")) {
                    songURI = line.split(":")[1];
                }
                if (line.startsWith("TRACK:")) {
                    name = line.split(":")[1];
                }
                if (line.startsWith("ARTIST:")) {
                    artist = line.split(":")[1];
                }
                if (line.startsWith("DELAY:")) {
                    delay = Double.parseDouble(line.split(":")[1]);
                }

                line = br.readLine();
            }

            fileContents = sb.toString();

            noteSections = sb.toString().split("\\r?\\n");
        }
        catch (Exception e) {
        }
        String[] noteSections2 = Arrays.copyOfRange(noteSections, 6, noteSections.length);
        noteSections = noteSections2;

        notes = new String[noteSections.length][24];

        for (int i = 0; i < noteSections.length; i++) {
            realNotes.add(noteSections[i]);
        }
    }    

    public ArrayList<String> getSongList() {
        return songList;
    }

    public ArrayList<String> getSongFileList() {
        return songFileList;
    }

    public int getBPM() {
        return BPM;
    }

    public int getSpeed() {
        return speed;
    }

    public ArrayList<String> getNotes() {
        return realNotes;
    }

    public String getSongURI() {
        return "songs/" + songURI;
    }

    public String getArtist() {
        return artist;
    }

    public String getName() {
        return name;
    }
    
    public double getDelay() {
        return delay;
    }
}