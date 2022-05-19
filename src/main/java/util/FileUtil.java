package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static List<String> readPrivateList() throws IOException {
        FileReader fileReader = new FileReader("link.txt");
        List<String> privaties = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line = "";

            while ((line=bufferedReader.readLine())!=null){
                String privateKey = line.trim();
                privaties.add(privateKey);
            }
        }
        return privaties;
    }


}
