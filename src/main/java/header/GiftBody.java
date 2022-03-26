package header;

import java.io.Serializable;

public class GiftBody implements Serializable {
    private String email;
    private String caption = "Merry christmas my friend";
    private boolean create_contact;
    private String asset_id;

    public GiftBody() {
    }

    public GiftBody(String email, String asset_id) {
        this.email = email;
        this.asset_id = asset_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public boolean isCreate_contact() {
        return create_contact;
    }

    public void setCreate_contact(boolean create_contact) {
        this.create_contact = create_contact;
    }

    public String getAsset_id() {
        return asset_id;
    }

    public void setAsset_id(String asset_id) {
        this.asset_id = asset_id;
    }
}
