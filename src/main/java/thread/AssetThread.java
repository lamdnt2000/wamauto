package thread;

import header.RequestBody;
import model.UserData;
import service.DataService;
import util.CommonUtils;
import util.RequestUtil;

import java.io.IOException;
import java.util.HashMap;

public class AssetThread extends Thread implements Runnable{
    private RequestBody requestBody;

    public AssetThread(RequestBody requestBody) {
        this.requestBody = requestBody;
    }
    @Override
    public void run(){
        try {
            String response = RequestUtil.asyncRequest(requestBody);
            HashMap<String,Object> data = CommonUtils.convertResponseToHashMap(response);
            String authToken = (String) data.get("auth_token");
            UserData userData = (UserData) requestBody.getData();
            String email = userData.getLoginBody().getEmail();
            synchronized (DataService.data) {
                UserData userDataHash = DataService.data.get(email);
                userDataHash.setAuth_token(authToken);
                data.put(email,userDataHash);
            }
            this.join();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
