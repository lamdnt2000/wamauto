package util;

import header.LoginBody;

import model.UserData;
import service.DataService;

import javax.xml.crypto.Data;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class FileUtil {
    public final static String ACCOUNT_FILE="accounts.txt";
    public final static String WALLET_FILE="wallet.txt";
    public final static String JSON_FILE="json.txt";
    public final static String VERIFY_FILE="verify.txt";
    public final static String DATA_FILE="data.bat";
    public final static String MISS_ACCOUNT="mistaccount.txt";
    public final static String MISS_IMAGE="missimage.txt";

    public static void readFile() throws IOException {
        FileReader fileReader = new FileReader(VERIFY_FILE);
        int count = 0;
        try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line = "";
            DataService.data = new ConcurrentHashMap<>();
            while ((line=bufferedReader.readLine())!=null){
                DataService.verifyAccount.add(line);
            }
        }
    }

    public static HashMap<String,Integer> readFile(String filename) throws IOException {
        FileReader fileReader = new FileReader(filename);
        HashMap<String,Integer> result = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line = "";

            while ((line=bufferedReader.readLine())!=null){
                result.put(line.toLowerCase(),1);
            }
        }
        return result;
    }

    public static void readData(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(fileName);
        ConcurrentHashMap<String,UserData> concurrentHashMaps = new ConcurrentHashMap<>();
        try(ObjectInputStream objectInputStream  = new ObjectInputStream(fileInputStream)){
            DataService.data = (ConcurrentHashMap<String, UserData>) objectInputStream.readObject();

        }

    }

    public static void readAccount() throws IOException {
        checkExistToCreateOne(new File(DATA_FILE));
        FileReader fileReader = new FileReader(ACCOUNT_FILE);
        int count = 0;
        try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line = "";
            DataService.data = new ConcurrentHashMap<>();
            while ((line=bufferedReader.readLine())!=null){
                if (line.length()>1) {
                    LoginBody account = new LoginBody(line,"123456abc");
                    UserData userData = new UserData();
                    userData.setLoginBody(account);
                    DataService.data.put(line, userData);
                    count++;
                }
            }
        }
        System.out.printf("Import accout success");
        System.out.println("total: "+count);
        writeData();
    }

    public static void writeData() throws IOException {
        checkExistToCreateOne(new File(DATA_FILE));
        FileOutputStream outputStream = new FileOutputStream(DATA_FILE);
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)){
            objectOutputStream.writeObject(DataService.data);
            objectOutputStream.flush();
        }
        System.out.println("Save data to file success");
    }

    public static void checkExistToCreateOne(File file) throws IOException {
        if (!file.isFile()){
            System.out.println("You dont have data file. Created one!");
            file.createNewFile();
        }
    }

    public static void writeListToFile(List<String> typeList,String fileName) throws IOException {
        checkExistToCreateOne(new File(fileName));
        FileWriter fileWriter = new FileWriter(fileName,true);
        try(PrintWriter printWriter = new PrintWriter(fileWriter)){
            for (String string:typeList){
                System.out.println(string);
                printWriter.println(string);
            }
            printWriter.flush();
        }
        fileWriter.close();
        System.out.println("Save data to file success");
    }
    public static List<String> readFileTxt(String filePath) throws IOException {
        FileReader fileReader = new FileReader(filePath);
        int count = 0;
        List<String> data = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line = "";
            while ((line=bufferedReader.readLine())!=null){
                data.add(line);
            }
        }
        return data;
    }
}
