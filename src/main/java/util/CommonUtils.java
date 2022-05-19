package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Driver;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {
    public static String convertObjectToStringJson(Object object){
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        try {
            json = objectMapper.writeValueAsString(object);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        finally {
            return json;
        }
    }


    public static String getUrlFromText(String txt){
        Pattern p = Pattern.compile("((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)",
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        Matcher m = p.matcher(txt);

        if (m.find()) {
            return txt.substring(m.start(0), m.end(0));
        }
        return "";
    }

    public static boolean refreshElementWhenNotFound(WebDriver driver, String url, String xpath, int timeOut, int limit, boolean isClick){
        WebDriverWait webDriverWait = new WebDriverWait(driver,timeOut);
        for (int i=0;i<limit;i++){
            try {
                webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
                if (isClick){
                    System.out.println("Element found");
                    WebElement element = driver.findElement(By.xpath(xpath));
                    element.click();
                }
                return true;
            }
            catch (NoSuchElementException e){
                System.out.println("Element not found refresh page now");
                driver.get(url);
            }

        }
        return false;

    }
}
