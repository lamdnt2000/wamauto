import com.fasterxml.jackson.databind.util.JSONPObject;
import util.CommonUtils;
import util.FileUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ParseHtml {
    public static void main(String[] args) throws IOException {
        HashMap<String,Integer> result = FileUtil.readFile("checking.txt");
        HashMap<String,Integer> checking = FileUtil.readFile("wallet.txt");
        Integer count = 0;
        for (String wallet:checking.keySet()){
            if (result.containsKey(wallet)){

                count++;
            }
        }
        System.out.println("Total: "+count);
    }
}
