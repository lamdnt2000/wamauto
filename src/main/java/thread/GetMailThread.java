package thread;

import header.RequestBody;
import header.VerifyBody;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import service.DataService;
import util.CommonUtils;
import util.RequestUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetMailThread extends Thread implements Runnable{
    private RequestBody requestBody;
    private String email;

    public GetMailThread(RequestBody requestBody, String email) {
        this.requestBody = requestBody;
        this.email = email;
    }

    @Override
    public void run(){
        try {
            String response = "";
            int count = 0;
            do{
                response = RequestUtil.asyncRequest(requestBody);

                if (response.length()==0){

                    Thread.sleep(3000);
                    count++;
                    if (count==3){
                        System.out.println(email);
                        break;
                    }
                }
                else{
                    HashMap<String, Object> stringObjectHashMap  = CommonUtils.convertResponseToHashMap(response);
                    List<Object> objectList = CommonUtils.convertObjectToList(stringObjectHashMap.get("data"));
                    String data= CommonUtils.convertObjectToStringJson(objectList.get(0));
                        HashMap<String, Object> dataResult = CommonUtils.convertResponseToHashMap(data);
                        String url = "https://cryptogmail.com/api/emails/" + (String) dataResult.get("id");
                    RequestBody mailBody = new RequestBody();
                    mailBody.setUrl(url);
                    mailBody.setMethod("GET");
                    String getMailBody = RequestUtil.asyncRequestHtml(mailBody,false);
                    DataService.verifyAccount.add(getMailBody);



                }
            }while ((response.length()==0));


        }
        catch (NullPointerException e) {
            this.interrupt();
            System.out.println(e.getMessage());
        }catch (IOException e) {

            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
