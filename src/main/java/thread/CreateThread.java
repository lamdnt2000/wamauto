package thread;

import header.RequestBody;
import util.RequestUtil;

import java.io.IOException;

public class CreateThread extends Thread implements Runnable{
    private RequestBody requestBody;

    public CreateThread(RequestBody requestBody) {
        this.requestBody = requestBody;
    }
    @Override
    public void run(){
        try {
            RequestUtil.postRequest(requestBody,RequestUtil.URL_REGISTER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
