package com.runninglight.shared;

import java.util.UUID;

public class User {

    /** Username of the user */
    private String userName;

    /** Password of the user */
    private String password;

    /** Unique 8-digit hex ID for the user */
    private String userID;

    /** Indicates the last index of the hex ID */
    private static final int ID_END = 7;

    /**
     * User constructor
     *
     * @param userName Username of the user
     * @param password Password of the user
     *
     * @pre none
     * @post Creates a User object with the given parameters and generates an ID
     */
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.userID = generateID();
    }

    /**
     * User constructor
     *
     * @param userName Username of the user
     * @param password Password of the user
     * @param userID ID of the user
     *
     * @pre none
     * @post Creates a User object with the given parameters
     */
    public User(String userName, String password, String userID) {
        this.userName = userName;
        this.password = password;
        this.userID = userID;
    }

    /**
     * Returns a random 8-digit hex value
     *
     * @pre none
     * @post returns a random 8-digit hex value
     */
    private String generateID(){
        String longID = UUID.randomUUID().toString();
        return longID.substring(0, ID_END);
    }

    @Override
    public boolean equals(Object other){
        if(other == null){
            return false;
        }
        if(this.getClass() != other.getClass()){
            return false;
        }
        User otherUser = (User)other;
        return this.userName.equals(otherUser.getUserName()) &&
                this.password.equals(otherUser.getPassword());
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
