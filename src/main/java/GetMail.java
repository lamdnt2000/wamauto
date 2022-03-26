import header.RequestBody;
import service.DataService;
import thread.ConfirmThread;
import thread.GetMailThread;
import util.CommonUtils;
import util.CreateBodyRequest;
import util.FileUtil;
import util.RequestUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;

public class GetMail {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        FileUtil.readFile();
        ScheduledExecutorService fixedPool =
                Executors.newScheduledThreadPool(6);
        CompletionService<String> service = new ExecutorCompletionService<String>(fixedPool);
        List<String> emailSend = new ArrayList<>();
        for (String email: DataService.verifyAccount){
            RequestBody requestBody = CreateBodyRequest.createSendCode(email);

            fixedPool.schedule(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(email);
                        String response = RequestUtil.asyncRequest(requestBody);
                        System.out.println(response);
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, 5, TimeUnit.SECONDS);
        }


        fixedPool.shutdown();
        while (!fixedPool.isTerminated()) {

        }
        FileUtil.writeListToFile(DataService.verifyAccount,FileUtil.VERIFY_FILE);

    }
}
