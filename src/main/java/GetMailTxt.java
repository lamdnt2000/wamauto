import header.RequestBody;
import service.DataService;
import thread.GetMailThread;
import util.CreateBodyRequest;
import util.FileUtil;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

public class GetMailTxt {
    public static void main(String[] args) throws IOException {
        ScheduledExecutorService fixedPool =
                Executors.newScheduledThreadPool(6);
        CompletionService<String> service = new ExecutorCompletionService<String>(fixedPool);
        List<String> emails = FileUtil.readFileTxt("email.txt");
        for (String email: emails){
            RequestBody gmailCode = CreateBodyRequest.createGetMailCode(email);
            fixedPool.schedule(new GetMailThread(gmailCode,email), 3, TimeUnit.SECONDS);
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
