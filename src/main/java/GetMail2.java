import header.RequestBody;
import service.DataService;
import thread.GetMailThread;
import thread.InboxThread;
import util.CreateBodyRequest;
import util.FileUtil;

import java.io.IOException;
import java.util.concurrent.*;

public class GetMail2 {
    public static void main(String[] args) throws IOException {
        ScheduledExecutorService fixedPool =
                Executors.newScheduledThreadPool(6);
        CompletionService<String> service = new ExecutorCompletionService<String>(fixedPool);
        for (int i=1501;i<2001;i++){
            String email = "tuananh"+i+"@vintomaper.com";
            RequestBody gmailCode = CreateBodyRequest.createGetMailCode(email);
            fixedPool.schedule(new GetMailThread(gmailCode,email), 3, TimeUnit.SECONDS);
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        fixedPool.shutdown();
        while (!fixedPool.isTerminated()) {

        }
        System.out.println(DataService.verifyAccount.size());
        for (String data: DataService.verifyAccount){
            System.out.println(data);
        }

    }
}
