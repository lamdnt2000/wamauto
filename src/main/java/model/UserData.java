package model;

import header.LoginBody;

import java.io.Serializable;

public class UserData implements Serializable {
    private LoginBody loginBody;
    private String auth_token;
    private Wallet wallet;
    private Contract contract;
    private boolean isMint=false;
    private boolean isSend=false;
    private boolean isReceive=false;
    public UserData() {
    }

    public LoginBody getLoginBody() {
        return loginBody;
    }

    public void setLoginBody(LoginBody loginBody) {
        this.loginBody = loginBody;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public boolean isMint() {
        return isMint;
    }

    public void setMint(boolean mint) {
        isMint = mint;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    public boolean isReceive() {
        return isReceive;
    }

    public void setReceive(boolean receive) {
        isReceive = receive;
    }
}
