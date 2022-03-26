package header;


import util.CommonUtils;


import java.io.Serializable;
import java.net.CookieManager;

public class RequestBody implements Serializable {
    private String url;
    private String method;
    private Object data;
    private String userAgent="Mozilla/5.0 (Linux; Android 7.0; SM-G930V Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.125 Mobile Safari/537.36";
    private String xapiKey;
    public String xrequestWith = "io.numbersprotocol.capturelite";
    private String token;
    private String cookieManager;
    public RequestBody() {

    }

    public RequestBody(String xapiKey){
        this.xapiKey = xapiKey;
    }
    public String getJsonBody(){
        return CommonUtils.convertObjectToStringJson(this.data);
    }



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getXapiKey() {
        return xapiKey;
    }

    public void setXapiKey(String xapiKey) {
        this.xapiKey = xapiKey;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getXrequestWith() {
        return xrequestWith;
    }

    public void setXrequestWith(String xrequestWith) {
        this.xrequestWith = xrequestWith;
    }

    public String getCookieManager() {
        return cookieManager;
    }

    public void setCookieManager(String cookieManager) {
        this.cookieManager = cookieManager;
    }
}
