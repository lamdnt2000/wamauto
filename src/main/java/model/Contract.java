package model;

import java.io.Serializable;

public class Contract implements Serializable {
    private String id;
    private String hash;

    public Contract() {
    }

    public Contract(String id, String hash) {
        this.id = id;
        this.hash = hash;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
