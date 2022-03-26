package thread;

import com.fasterxml.jackson.core.JsonProcessingException;
import header.RequestBody;
import model.Contract;
import model.UserData;
import model.Wallet;
import service.DataService;
import util.CommonUtils;
import util.CreateBodyRequest;
import util.RequestUtil;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class AssetThread extends Thread implements Runnable{
    private RequestBody requestBody;
    private String email;

    public AssetThread(RequestBody requestBody, String email) {
        this.requestBody = requestBody;
        this.email = email;
    }

    @Override
    public void run(){
        try {
            String response="";
            int countBreak =0;
            do {
                response = RequestUtil.asyncRequest(requestBody);
                if (response.length() == 0) {
                    countBreak++;
                    Thread.sleep(1500);
                    if (countBreak == 3) {
                        break;
                    }
                } else {
                    HashMap<String,Object> data = CommonUtils.convertResponseToHashMap(response);
                    Integer count = (Integer) data.get("count");
                    Contract contract = null;
                    if (count!=0){
                        List<Object> results=CommonUtils.convertObjectToList(data.get("results"));
                        Object result = results.get(0);
                        String json = CommonUtils.convertObjectToStringJson(result);
                        HashMap<String,Object> resultHashMap = CommonUtils.convertResponseToHashMap(json);
                        String id = (String) resultHashMap.get("id");
                        System.out.println(id);
                        String uuid = (String) resultHashMap.get("uuid");
                        contract = new Contract(id,uuid);
                        synchronized (DataService.data) {
                            UserData userDataHash = DataService.data.get(email);
                            userDataHash.setContract(contract);
                            data.put(email,userDataHash);
                            RequestBody mintBody = CreateBodyRequest.createMintBody(email);
                            MintNftThread mintNftThread = new MintNftThread(mintBody,email);
                            mintNftThread.start();
                        }

                    }
                    else{
                        System.out.println(email + " not photo yet");
                        DataService.missImage.add(email);
                    }
                }
            }while(response.length()==0);



        }
        catch (NullPointerException e) {
            this.interrupt();
            System.out.println(e.getMessage());
        }catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        catch (IOException e) {
            DataService.missAccount.add(email);
            System.out.println(e.getMessage());
        }
    }
}
