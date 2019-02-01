package com.runninglight.shared;

public class LoginInfo {

    /** Username of the user logging in / being registered */
    private String userName;

    /** Password of the user logging in / being registered */
    private String password;

    /**
     * LoginInfo constructor
     *
     * @param userName Username of user
     * @param password Password of user
     *
     * @pre None
     * @post userName and password are assigned values
     */
    public LoginInfo(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
