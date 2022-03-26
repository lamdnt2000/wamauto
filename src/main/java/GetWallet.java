import model.UserData;
import model.Wallet;
import service.DataService;
import util.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetWallet {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int count =0;
        List<String> wallet = new ArrayList<>();
        for (int i=0;i<4;i++){
            String filename = "data"+i*1000+".bat";
            if (i==0){
                filename ="data.bat";
            }
            FileUtil.readData(filename);
            for (String email: DataService.data.keySet()){
                UserData userData = DataService.data.get(email);
                Wallet wallets = userData.getWallet();
                if (wallets!=null){
                    System.out.println(wallets.getAddress());
                    wallet.add(wallets.getAddress());
                }

                count++;
            }
        }
        FileUtil.writeListToFile(wallet,FileUtil.WALLET_FILE);
    }
}
