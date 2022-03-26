package thread;

import header.RequestBody;
import model.Contract;
import model.UserData;
import service.DataService;
import util.CommonUtils;
import util.CreateBodyRequest;
import util.RequestUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class InboxThread extends Thread implements Runnable{
    private RequestBody requestBody;
    private String email;

    public InboxThread(RequestBody requestBody, String email) {
        this.requestBody = requestBody;
        this.email = email;
    }

    @Override
    public void run(){
        try {
            String response = "";
            int countBreak = 0;
            do{
                response = RequestUtil.asyncRequest(requestBody);
                if (response.length()==0){
                    System.out.println(this.getName()+" fetch again");
                    countBreak++;
                    Thread.sleep(3000);
                    if (countBreak==5){
                        break;
                    }
                }
                else{
                    HashMap<String,Object> data = CommonUtils.convertResponseToHashMap(response);
                    Integer count = (Integer) data.get("count");
                    System.out.println(count);
                    if (count!=0){
                        List<Object> results=CommonUtils.convertObjectToList(data.get("results"));
                        Object result = results.get(0);
                        String json = CommonUtils.convertObjectToStringJson(result);
                        HashMap<String,Object> resultHashMap = CommonUtils.convertResponseToHashMap(json);
                        String id = (String) resultHashMap.get("id");
                        System.out.println(id);
                        RequestBody receiveBody = CreateBodyRequest.createReceiveBody(email,id);
                        System.out.println(receiveBody.getUrl());
                        System.out.println(receiveBody.getToken());
                        String responseReceive = "";
                        do{
                            responseReceive = RequestUtil.asyncRequest(receiveBody);
                            System.out.println(this.getName()+" fetch again");
                        }while(response=="");
                        HashMap<String,Object> dataReceive = CommonUtils.convertResponseToHashMap(responseReceive);
                        if (dataReceive.get("id")!=null){
                            System.out.printf(email+" receive success");
                        }
                    }
                    else {
                        System.out.println(email + " No inbox");
                    }
                }
            }while(response=="");


        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
