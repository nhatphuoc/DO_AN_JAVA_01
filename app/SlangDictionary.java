

package app;

import java.io.*;
import java.util.*;

public class SlangDictionary {
    public static void main(String[] args) {
        Map<String, String> slangMap = new HashMap<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("slang.txt"));
            String line;
            int i = 1;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("`");
                if (parts.length == 2) {
                    slangMap.put(parts[0].trim(), parts[1].trim());
                } else {
                    System.out.println("Line " + i + " is not properly formatted: " + line);
                }
                i++;
            }
            reader.close();

            TestFrame7.runAppUI(slangMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

