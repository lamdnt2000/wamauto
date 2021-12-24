package thread;

import header.RequestBody;
import model.UserData;
import service.DataService;
import util.CommonUtils;
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
        try {
            String response = RequestUtil.asyncRequest(requestBody);
            HashMap<String,Object> data = CommonUtils.convertResponseToHashMap(response);
            String authToken = (String) data.get("auth_token");
            UserData userData = (UserData) requestBody.getData();
            String email = userData.getLoginBody().getEmail();
            System.out.println(email+" fetch first");
            synchronized (DataService.data) {
                UserData userDataHash = DataService.data.get(email);
                userDataHash.setAuth_token(authToken);
                data.put(email,userDataHash);
            }
            this.join();
            System.out.println(email+" fetch first success");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
