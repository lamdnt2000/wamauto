package util;

import header.RequestBody;
import model.UserRegister;
import thread.CreateThread;

import java.util.concurrent.*;

public class Demo {
    public static void main(String[] args) {
        ScheduledExecutorService fixedPool =
                Executors.newScheduledThreadPool(6);
        CompletionService<String> service = new ExecutorCompletionService<String>(fixedPool);
        for (int i=3430;i<5001;i++){
            RequestBody requestBody = new RequestBody(CreateBodyRequest.xapiKey);
            requestBody.setMethod("POST");
            UserRegister userRegister = new UserRegister("numpro"+i,"numpro"+i+"@mentonit.net","123456abc");
            requestBody.setData(userRegister);
            fixedPool.schedule(new CreateThread(requestBody),2,TimeUnit.SECONDS);
        }
        fixedPool.shutdown();
        while (!fixedPool.isTerminated()) {

        }
        System.out.println("finish create");

    }
}
