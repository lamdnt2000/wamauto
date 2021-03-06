import me.shivzee.JMailTM;
import me.shivzee.callbacks.MessageListener;
import me.shivzee.exceptions.MessageFetchException;
import me.shivzee.util.JMailBuilder;
import me.shivzee.util.Message;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.CommonUtils;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static util.CommonUtils.checkValidElement;

public class SeleniumThread extends Thread {
    private String openLink;
    private String email;
    private boolean isClick = false;
    private boolean isSendMail = false;

    public SeleniumThread(String openLink, String email) {
        this.openLink = openLink;
        this.email = email;
    }

    public ChromeOptions settingChrome() {
        String proxy = "127.0.0.1:10000";
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--proxy-server=" + proxy);
        return chromeOptions;
    }

    @Override
    public void run() {
        try {
            ChromeOptions chromeOptions = settingChrome();
            chromeOptions.addArguments("--disable-gpu");
            Map<String, String> mobileEmulation = new HashMap<>();
            mobileEmulation.put("deviceName", "Nexus 5");
            chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
            WebDriver driver = new ChromeDriver(chromeOptions);
            driver.manage().deleteAllCookies();
            driver.manage().timeouts().pageLoadTimeout(25, TimeUnit.SECONDS);
            driver.manage().window().setSize(new Dimension(30, 650));
            driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);
            driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);
            driver.get(openLink);


            JMailTM mailer = null;
            try {
                mailer = JMailBuilder.createAndLogin(email, "sasuke903");
                mailer.init();
            } catch (LoginException e) {
                mailer = JMailBuilder.login(email, "sasuke903");
            }
            String emailGenerate = mailer.getSelf().getEmail();
            System.out.println("Email : " + emailGenerate);

            Thread.sleep(5000);
            CommonUtils.refreshElementWhenNotFound(driver, openLink, "//*[@id=\"left\"]", 5, 5, false);

            WebElement n = driver.findElement(By.className("signin"));
            n.click();
            Thread.sleep(3000);
            WebElement signIn = driver.findElement(By.id("email"));
            signIn.click();
            Thread.sleep(350);
            WebElement email = driver.findElement(By.xpath("//*[@id=\"email\"]"));
            email.sendKeys(emailGenerate);
            Thread.sleep(350);
            WebElement password = driver.findElement(By.xpath("//*[@id=\"__page\"]/div[2]/div/div[2]/div[2]/div/form/input[2]"));
            password.sendKeys("sasuke903");
            WebElement login = driver.findElement(By.xpath(
                    "//*[@id=\"__page\"]/div[2]/div/div[2]/div[2]/div/form/button[2]"
            ));
            login.click();
            Thread.sleep(12000);
            WebElement balance = checkValidElement(driver, "//*[@id=\"header-wallet\"]");
            Thread.sleep(350);
            int bal = Integer.parseInt(balance.getText());
            System.out.println(balance.getText());
            if (bal == 0) {
                WebElement accept = checkValidElement(driver, "//*[@id=\"accept\"]");
                Thread.sleep(350);
                if (accept != null) {
                    accept.click();
                } else {
                    driver.get("https://play.wam.app/account/");
                    Thread.sleep(5000);
                    WebElement resend = checkValidElement(driver, "//*[@id=\"identity\"]/div[2]/div[1]/div/div[2]/button");
                    if (resend != null) {
                        resend.click();
                    }
                }
                JMailTM finalMailer = mailer;
                mailer.openMessageListener(new MessageListener() {
                    @Override
                    public void onMessageReceived(Message message) {
                        String title = message.getSubject();
                        if (title.contains("email")) {
                            isSendMail = true;
                            String url = CommonUtils.getUrlFromText(message.getContent());
                            driver.get(url);
                            try {
                                Thread.sleep(12000);
                                WebElement resend = checkValidElement(driver, "//*[@id=\"identity\"]/div[2]/div[1]/div/div[2]/button");
                                if (resend != null) {
                                    resend.click();
                                } else {
                                    isClick = true;
                                    try {
                                        CommonUtils.refreshElementWhenNotFound(driver, openLink, "//*[@id=\"menu-feed\"]", 15, 3, true);
                                        Thread.sleep(2000);
                                        CommonUtils.refreshElementWhenNotFound(driver, openLink, "//*[@id=\"rewards-button\"]", 15, 3, true);
                                        Thread.sleep(1000);
                                        System.out.println("Claim daily");
                                        Thread.sleep(1000);
                                        CommonUtils.refreshElementWhenNotFound(driver, openLink, "//*[@id=\"rewards-center\"]/div[2]/ul/li[1]/div", 15, 3, true);
                                        Thread.sleep(1000);
                                        driver.close();
                                        finalMailer.closeMessageListener();
                                    } catch (InterruptedException e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                            } catch (InterruptedException e) {
                                System.out.println(e.getMessage());
                            }
                        }


                    }

                    @Override
                    public void onError(String s) {
                        if (s.contains("TimeoutException") && s.contains("identity")) {

                        } else {
                            System.out.println(s);
                        }
                    }

                });
                int count = 0;
                while (!isClick) {
                    Thread.sleep(3000);
                    count++;
                    System.out.println(count);
                    if (count >= 4) {
                        if (!isSendMail) {
                            driver.get("https://play.wam.app/account/");
                            Thread.sleep(5000);
                            WebElement resend = checkValidElement(driver, "//*[@id=\"identity\"]/div[2]/div[1]/div/div[2]/button");
                            if (resend != null) {
                                resend.click();
                            }

                        }
                    }
                }
            } else {
                driver.close();
            }


        } catch (InterruptedException | LoginException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public String getOpenLink() {
        return openLink;
    }

    public void setOpenLink(String openLink) {
        this.openLink = openLink;
    }
}
