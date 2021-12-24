package util;

import header.LoginBody;
import header.RequestBody;

public class CreateBodyRequest {
    public static String LOGIN_URL = "https://dia-backend.numbersprotocol.io/auth/token/login/";
    public static String xapiKey = "2pdpDecq.oAYsGfQTqECXBzXEUHXfWMiSpJYSYeDU";
    public static RequestBody createLoginBody(String email, String password){
        RequestBody requestBody = new RequestBody();
        requestBody.setMethod("POST");
        requestBody.setUrl(LOGIN_URL);
        LoginBody loginBody = new LoginBody(email,password);
        requestBody.setData(loginBody);
        return requestBody;
    }
}
