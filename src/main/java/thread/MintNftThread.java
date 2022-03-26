package thread;

import header.RequestBody;
import model.Contract;
import model.UserData;
import service.DataService;
import util.CommonUtils;
import util.RequestUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class MintNftThread extends Thread implements Runnable{
    private RequestBody requestBody;
    private String email;

    public MintNftThread(RequestBody requestBody, String email) {
        this.requestBody = requestBody;
        this.email = email;
    }

    @Override
    public void run(){
        try {
            int response = RequestUtil.asyncRequestForm(requestBody);

            synchronized (DataService.data) {
                UserData userDataHash = DataService.data.get(email);
                if (response==200){
                    userDataHash.setMint(true);
                }
                else if(response==400){
                    userDataHash.setMint(true);
                    System.out.println(email+" already mint");
                }
                else{
                    System.out.println(response);
                }

                DataService.data.put(email,userDataHash);
            }


        }
        catch (NullPointerException e) {
            this.interrupt();
            System.out.println(e.getMessage());
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
