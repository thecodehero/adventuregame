import java.util.*;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
public class Room implements SaveGame {
    private int room;    
    
    public Room(int room) {
        this.room = room;      
        
    }
    
    //method to save room
    public void save(FileWriter writer) throws IOException
    {
        writer.write(getRoom() + "\n");        
    }
    
    //method to load room
    public void loadRoom(Scanner reader) throws IOException
    {
        setRoom(Integer.parseInt(reader.next()));        
    }
    
    
    
    //getter and setter methodds
    public void setRoom(int roomName) {
       room = roomName;
    }
    public int getRoom() {
        return room;
    }
    
    
    
}