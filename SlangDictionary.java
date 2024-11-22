import java.io.*;
import java.util.*;

public class SlangDictionary {
    public static void main(String[] args) {
        Map<String, String> slangMap = new HashMap<>();
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader("slang.txt"));
            String line;
            int i=1;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("`");
                
                if (parts.length == 2) {
                    slangMap.put(parts[0].trim(), parts[1].trim());
                } else {
                    System.out.println(i+": "+line);
                }
                i++;
            }
            
            reader.close();
            
            // for (Map.Entry<String, String> entry : slangMap.entrySet()) {
            //     System.out.println("Slang: " + entry.getKey() + " - Meaning: " + entry.getValue());
            // }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
