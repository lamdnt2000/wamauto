import header.LoginBody;
import header.RequestBody;
import model.UserData;
import service.DataService;
import thread.GiftThread;
import thread.LoginThread;
import util.CreateBodyRequest;
import util.FileUtil;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.*;

public class LoginAndFetch {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ScheduledExecutorService fixedPool =
                Executors.newScheduledThreadPool(6);
        CompletionService<String> service = new ExecutorCompletionService<String>(fixedPool);
        for (int i=1;i<1001;i++){
            String email = "numpro"+i+"@mentonit.net";
            LoginBody loginBody = new LoginBody(email,"123456abc");
            RequestBody requestBody = CreateBodyRequest.createLoginBody(loginBody.getEmail(),loginBody.getPassword());

            fixedPool.schedule(new LoginThread(requestBody), 5, TimeUnit.SECONDS);
        }
        fixedPool.shutdown();
        while (!fixedPool.isTerminated()) {

        }
        try {
            Thread.sleep(30000);
            FileUtil.writeData();
            FileUtil.writeListToFile(DataService.missAccount,FileUtil.MISS_ACCOUNT);
            FileUtil.writeListToFile(DataService.missImage,FileUtil.MISS_IMAGE);
            System.out.println("done");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
