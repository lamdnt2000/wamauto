package thread;

import header.RequestBody;
import util.RequestUtil;

import java.io.IOException;

public class CreateThread2 extends Thread implements Runnable{
    private RequestBody requestBody;

    public CreateThread2(RequestBody requestBody) {
        this.requestBody = requestBody;
    }
    @Override
    public void run(){
        try {
            RequestUtil.postRequest2(requestBody,RequestUtil.URL_REGISTER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
