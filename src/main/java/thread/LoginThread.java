package thread;

import header.LoginBody;
import header.RequestBody;
import model.UserData;
import model.Wallet;
import service.DataService;
import util.CommonUtils;
import util.CreateBodyRequest;
import util.RequestUtil;

import java.io.IOException;
import java.util.HashMap;

public class LoginThread extends Thread implements Runnable{
    private RequestBody requestBody;

    public LoginThread(RequestBody requestBody) {
        this.requestBody = requestBody;
    }
    @Override
    public void run(){
        LoginBody loginBody = (LoginBody) requestBody.getData();
        String email = loginBody.getEmail();
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
                    String authToken = (String) data.get("auth_token");

                    if (authToken!=null) {
                        System.out.println(email);
                        synchronized (DataService.data) {
                            UserData userDataHash = new UserData();
                            if (!DataService.data.contains(email)){

                                userDataHash.setLoginBody(loginBody);
                                DataService.data.put(email,userDataHash);
                            }
                            else{
                                userDataHash = DataService.data.get(email);
                            }
                            userDataHash.setAuth_token(authToken);
                            DataService.data.put(email, userDataHash);
                        }
                        RequestBody walletBody = CreateBodyRequest.createWalletBody(email);
                        /*RequestBody assestThread = CreateBodyRequest.createAssetBody(email);*/
                        WalletThread walletThread = new WalletThread(walletBody,email);
                        walletThread.start();
                       /* AssetThread assetThread = new AssetThread(assestThread, email);
                        assetThread.start();*/

                    }
                    else{
                        System.out.println(email + " not regist yet");
                        DataService.missAccount.add(email);
                    }
                }
            }while(response.length()==0);

        }
        catch (NullPointerException e) {
            this.interrupt();
            System.out.println(e.getMessage());
        }
        catch (IOException e) {
            DataService.missAccount.add(email);
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
