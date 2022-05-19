import com.github.javafaker.Faker;
import com.twocaptcha.TwoCaptcha;
import com.twocaptcha.captcha.HCaptcha;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import util.FileUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class Demo {

    public static void main(String[] args) throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        ScheduledExecutorService fixedPool =
                Executors.newScheduledThreadPool(2);
        for (int i=1;i<101;i++) {
            String email = "conkute"+i+"@scpulse.com";
            fixedPool.schedule(new SeleniumThread("https://play.wam.app/",email), 10, TimeUnit.SECONDS);
        }
        fixedPool.shutdown();
        while (!fixedPool.isTerminated()) {

        }
        System.out.println("finish click");
    }
}
