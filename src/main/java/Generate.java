import header.RequestBody;
import model.UserRegister;
import thread.CreateThread;
import thread.CreateThread2;
import util.CreateBodyRequest;

import java.util.concurrent.*;

public class Generate {
    public static void main(String[] args) {
        ScheduledExecutorService fixedPool =
                Executors.newScheduledThreadPool(6);
        CompletionService<String> service = new ExecutorCompletionService<String>(fixedPool);
        for (int i=5050;i<6000;i++){
            RequestBody requestBody = new RequestBody(CreateBodyRequest.xapiKey);
            requestBody.setMethod("POST");
            UserRegister userRegister = new UserRegister("numpro"+i,"numpro"+i+"@mentonit.net","123456abc");
            requestBody.setData(userRegister);
            fixedPool.schedule(new CreateThread2(requestBody),2, TimeUnit.SECONDS);
        }
        fixedPool.shutdown();
        while (!fixedPool.isTerminated()) {

        }
        System.out.println("finish create");
    }
}
