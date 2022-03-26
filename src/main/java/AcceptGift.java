import header.RequestBody;
import model.UserData;
import service.DataService;
import thread.*;
import util.CreateBodyRequest;
import util.FileUtil;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.*;

public class AcceptGift {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ScheduledExecutorService fixedPool =
                Executors.newScheduledThreadPool(6);
        CompletionService<String> service = new ExecutorCompletionService<String>(fixedPool);
        try {
            FileUtil.readData("data.bat");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        for (String email: DataService.data.keySet()){
            UserData userData = DataService.data.get(email);
            if (userData.getContract()!=null) {
                RequestBody inboxBody = CreateBodyRequest.createInboxBody(email);
                fixedPool.schedule(new InboxThread(inboxBody, email), 1, TimeUnit.SECONDS);
            }
        }

        fixedPool.shutdown();
        while (!fixedPool.isTerminated()) {

        }
    }
}
