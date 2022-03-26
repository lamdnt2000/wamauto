package thread;

import header.RequestBody;
import model.UserData;
import model.Wallet;
import service.DataService;
import util.CommonUtils;
import util.RequestUtil;

import java.io.IOException;
import java.util.HashMap;

public class ConfirmThread extends Thread implements Runnable{
    private RequestBody requestBody;
    private String email;

    public ConfirmThread(RequestBody requestBody, String email) {
        this.requestBody = requestBody;
        this.email = email;
    }

    @Override
    public void run(){
        try {
            String response = RequestUtil.asyncRequest(requestBody);
            System.out.println(email+" send success");

        }
        catch (NullPointerException e) {
            this.interrupt();
            System.out.println(e.getMessage());
        }catch (IOException e) {
            DataService.missAccount.add(email);
            System.out.println(e.getMessage());
        }
    }
}
