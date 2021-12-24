package util;

import header.LoginBody;

import model.UserData;
import service.DataService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class FileUtil {
    public final static String ACCOUNT_FILE="accounts.txt";
    public final static String DATA_FILE="data.bat";


    public static void readData() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(DATA_FILE);
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
}
