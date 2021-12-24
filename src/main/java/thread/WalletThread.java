package thread;

import header.RequestBody;
import model.UserData;
import model.Wallet;
import service.DataService;
import util.CommonUtils;
import util.RequestUtil;

import java.io.IOException;
import java.util.HashMap;

public class WalletThread extends Thread implements Runnable{
    private RequestBody requestBody;
    private String email;

    public WalletThread(RequestBody requestBody, String email) {
        this.requestBody = requestBody;
        this.email = email;
    }

    @Override
    public void run(){
        try {
            System.out.println(email+" fetch wallet");
            String response = RequestUtil.asyncRequest(requestBody);
            HashMap<String,Object> data = CommonUtils.convertResponseToHashMap(response);
            String address = (String) data.get("address");
            String private_key = (String) data.get("private_key");
            String id = (String) data.get("id");
            Wallet wallet = new Wallet(id,address,private_key);
            synchronized (DataService.data) {
                UserData userDataHash = DataService.data.get(email);
                userDataHash.setWallet(wallet);
                data.put(email,userDataHash);
            }
            System.out.println(email+" done fetch wallet");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
