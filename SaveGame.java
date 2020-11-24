import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public interface SaveGame {
    
    //method to save data 
    public void save(FileWriter writer) throws IOException;
    
}