package model;

import java.io.Serializable;

public class Contract implements Serializable {
    private String id;
    private String uuid;

    public Contract() {
    }

    public Contract(String id, String uuid) {
        this.id = id;
        this.uuid = uuid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
