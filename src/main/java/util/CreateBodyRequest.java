package util;

import header.GiftBody;
import header.LoginBody;
import header.RequestBody;
import header.SendCode;
import model.Contract;
import model.UserData;
import service.DataService;

public class CreateBodyRequest {
    public static String LOGIN_URL = "https://dia-backend.numbersprotocol.io/auth/token/login/";
    public static String WALLET_URL = "https://dia-backend.numbersprotocol.io/api/v3/wallets/asset-wallet/";
    public static String ASSET_URL = "https://dia-backend.numbersprotocol.io/api/v3/assets/?offset=0&limit=100&is_original_owner=true";
    public static String xapiKey = "2pdpDecq.oAYsGfQTqECXBzXEUHXfWMiSpJYSYeDU";
    public static String MINT_URL = "https://dia-backend.numbersprotocol.io/api/v3/assets/";
    public static String GIFT_URL = "https://dia-backend.numbersprotocol.io/api/v3/transactions/";
    public static String INBOX_URL = "https://dia-backend.numbersprotocol.io/api/v3/transactions/inbox/?limit=1";
    public static String RECEIVE_URL = "https://dia-backend.numbersprotocol.io/api/v3/transactions/";
    public static RequestBody createLoginBody(String email, String password){
        RequestBody requestBody = new RequestBody();
        requestBody.setMethod("POST");
        requestBody.setUrl(LOGIN_URL);
        LoginBody loginBody = new LoginBody(email,password);
        requestBody.setData(loginBody);
        return requestBody;
    }

    public static RequestBody createWalletBody(String email){
        RequestBody requestBody = new RequestBody();
        requestBody.setToken(getKeyFromEmail(email));
        requestBody.setMethod("GET");
        requestBody.setUrl(WALLET_URL);
        return requestBody;
    }

    public static RequestBody createAssetBody(String email){
        RequestBody requestBody = new RequestBody();
        requestBody.setToken(getKeyFromEmail(email));
        requestBody.setMethod("GET");
        requestBody.setUrl(ASSET_URL);
        return  requestBody;
    }
    public static RequestBody createGiftBody(String email,String receiver){
        UserData userData = getUserDateFromEmail(email);
        RequestBody requestBody = new RequestBody();
        requestBody.setMethod("POST");
        requestBody.setToken(getKeyFromEmail(email));
        requestBody.setUrl(GIFT_URL);
        GiftBody giftBody = new GiftBody(receiver,userData.getContract().getId());
        requestBody.setData(giftBody);
        return requestBody;
    }

    public static RequestBody createInboxBody(String email){
        RequestBody requestBody = new RequestBody();
        requestBody.setToken(getKeyFromEmail(email));
        requestBody.setMethod("GET");
        requestBody.setUrl(INBOX_URL);
        return requestBody;
    }

    public static RequestBody createReceiveBody(String email,String uuid){
        RequestBody requestBody = new RequestBody();
        requestBody.setMethod("POST");
        requestBody.setToken(getKeyFromEmail(email));
        String url = RECEIVE_URL+uuid+"/accept/";
        requestBody.setUrl(url);


        return requestBody;
    }
    public static RequestBody createMintBody(String email){
        RequestBody requestBody = new RequestBody();
        String id = getIdAssestFromEmail(email);
        String url = MINT_URL+id+"/mint/";
        requestBody.setMethod("POST");
        requestBody.setUrl(url);
        requestBody.setXapiKey(xapiKey);
        requestBody.setToken(getKeyFromEmail(email));
        return  requestBody;
    }


    public static String getKeyFromEmail(String email){
        UserData userData = getUserDateFromEmail(email);
        return userData.getAuth_token();
    }

    public static String getIdAssestFromEmail(String email){
        UserData userData = DataService.data.get(email);
        Contract contract = userData.getContract();
        if (contract!=null){
            return contract.getId();
        }
        return null;
    }

    public static RequestBody createGetMailCode(String email){
        RequestBody requestBody = new RequestBody();
        requestBody.setUrl("https://cryptogmail.com/api/emails?inbox="+email);
        requestBody.setMethod("GET");
        return requestBody;
    }
    public static RequestBody createSendCode(String email){
        RequestBody requestBody = new RequestBody();
        requestBody.setUrl("https://dia-backend.numbersprotocol.io/auth/users/resend_activation/");
        requestBody.setMethod("POST");
        SendCode sendCode = new SendCode(email);
        requestBody.setData(sendCode);
        return requestBody;
    }
    public static RequestBody createMeBody(String email){
        RequestBody requestBody = new RequestBody();
        requestBody.setUrl("https://dia-backend.numbersprotocol.io/auth/users/me/");
        requestBody.setMethod("GET");
        requestBody.setToken(getKeyFromEmail(email));
        return requestBody;
    }

    public static UserData getUserDateFromEmail(String email){
        return DataService.data.get(email);
    }
}
