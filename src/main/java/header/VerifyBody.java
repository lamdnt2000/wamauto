package header;

public class VerifyBody {
    private String csrfmiddlewaretoken;
    private String uid;
    private String token;

    public VerifyBody(String csrfmiddlewaretoken, String uid, String token) {
        this.csrfmiddlewaretoken = csrfmiddlewaretoken;
        this.uid = uid;
        this.token = token;
    }

    public String getCsrfmiddlewaretoken() {
        return csrfmiddlewaretoken;
    }

    public void setCsrfmiddlewaretoken(String csrfmiddlewaretoken) {
        this.csrfmiddlewaretoken = csrfmiddlewaretoken;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public String toString(){
        return "csrfmiddlewaretoken="+csrfmiddlewaretoken+"&uid="+uid+"&token="+token;
    }
}
