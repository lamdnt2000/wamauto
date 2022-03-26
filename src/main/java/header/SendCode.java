package header;

import java.io.Serializable;

public class SendCode implements Serializable {
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public SendCode(String email) {

        this.email = email;
    }
}
