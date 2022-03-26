import header.LoginBody;
import header.RequestBody;
import model.UserData;
import model.UserRegister;
import model.Wallet;
import service.DataService;
import thread.CreateThread;
import thread.GiftThread;
import thread.LoginThread;
import util.CreateBodyRequest;
import util.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

import static util.FileUtil.*;

public class Demo {
    public static void main(String[] args) throws IOException {

        try {
            FileUtil.readData("data.bat");
        }  catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        List<String> emails = new ArrayList<>();
        HashMap<String,Integer> result = FileUtil.readFile("checking.txt");
        System.out.println(result.size());
        for (String email: DataService.data.keySet()){
            UserData userData = DataService.data.get(email);
            Wallet wallet = userData.getWallet();
            if (wallet!=null){

                if ("0x6793b12d84510f5F3064f274b9052f094D9a9f29".equals(wallet.getAddress())){
                    System.out.println(wallet.getPrivate_key());
                    System.out.println(userData.getLoginBody().getEmail());
                }

            }

        }

    }
}
