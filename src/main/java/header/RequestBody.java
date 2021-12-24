package header;


import util.CommonUtils;


import java.io.Serializable;

public class RequestBody implements Serializable {
    private String url;
    private String method;
    private Object data;
    private String userAgent="Mozilla/5.0 (Linux; Android 7.1.2; G011A Build/N2G48H; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/68.0.3440.70 Mobile Safari/537.36";
    private String xapiKey;
    public String xrequestWith = "io.numbersprotocol.capturelite";
    private String token="";
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
}
