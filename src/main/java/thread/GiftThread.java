package thread;

import header.RequestBody;
import model.UserData;
import service.DataService;
import util.CommonUtils;
import util.RequestUtil;

import java.io.IOException;
import java.util.HashMap;

public class GiftThread extends Thread implements Runnable{
    private RequestBody requestBody;
    private String email;

    public GiftThread(RequestBody requestBody, String email) {
        this.requestBody = requestBody;
        this.email = email;
    }

    @Override
    public void run(){
        try {
            String response = RequestUtil.asyncRequest(requestBody);
            System.out.println(response);
            HashMap<String,Object> data = CommonUtils.convertResponseToHashMap(response);
            synchronized (DataService.data) {
                UserData userDataHash = DataService.data.get(email);
                if (data.get("status")!=null){
                    userDataHash.setSend(true);
                }
                else if ((Integer)data.get("status_code")==400){
                    userDataHash.setSend(true);
                    System.out.println("Already send gift");
                }
                else{
                    System.out.println(response);
                }
                DataService.data.put(email,userDataHash);
            }


        } catch (IOException e) {
            DataService.missAccount.add(email);
            System.out.println(e.getMessage());
            if (e.getMessage().contains("map")){
                try {
                    RequestUtil.asyncRequest(requestBody);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
