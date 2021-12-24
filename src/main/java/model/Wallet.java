package model;

import java.io.Serializable;

public class Wallet implements Serializable {
    private String id;
    private String address;
    private String private_key;
    private Float numBalance=0F;

    public Wallet() {
    }

    public Wallet(String id, String address, String private_key) {
        this.id = id;
        this.address = address;
        this.private_key = private_key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrivate_key() {
        return private_key;
    }

    public void setPrivate_key(String private_key) {
        this.private_key = private_key;
    }

    public Float getNumBalance() {
        return numBalance;
    }

    public void setNumBalance(Float numBalance) {
        this.numBalance = numBalance;
    }
}
