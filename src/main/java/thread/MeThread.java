package thread;

import header.RequestBody;
import model.Contract;
import model.UserData;
import service.DataService;
import util.CommonUtils;
import util.CreateBodyRequest;
import util.RequestUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class MeThread extends Thread implements Runnable{
    private RequestBody requestBody;
    private String email;

    public MeThread(RequestBody requestBody, String email) {
        this.requestBody = requestBody;
        this.email = email;
    }

    @Override
    public void run(){
        try {
            String response = RequestUtil.asyncRequest(requestBody);
            HashMap<String,Object> data = CommonUtils.convertResponseToHashMap(response);
            Boolean isVerify = (Boolean) data.get("email_verified");
            if (!isVerify){
                RequestBody getMail = CreateBodyRequest.createGetMailCode(email);
               /* GetMailThread getMailThread = new GetMailThread(getMail,email);
                getMailThread.start();*/
            }


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
