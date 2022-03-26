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
            String response="";
            int count =0;
            do{
                response = RequestUtil.asyncRequest(requestBody);
                if (response.length()==0){
                    count++;
                    Thread.sleep(1500);
                    if (count == 3) {
                        break;
                    }
                }
                else{
                    HashMap<String,Object> data = CommonUtils.convertResponseToHashMap(response);
                    String address = (String) data.get("address");
                    System.out.println(address);
                    String private_key = (String) data.get("private_key");
                    String id = (String) data.get("id");
                    Wallet wallet = new Wallet(id,address,private_key);
                    synchronized (DataService.data) {
                        UserData userDataHash = DataService.data.get(email);
                        userDataHash.setWallet(wallet);
                        DataService.data.put(email,userDataHash);
                    }
                }
            }while(response.length()==0);


        }
        catch (NullPointerException e) {
            this.interrupt();
            System.out.println(e.getMessage());
        }catch (IOException e) {
            DataService.missAccount.add(email);
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
