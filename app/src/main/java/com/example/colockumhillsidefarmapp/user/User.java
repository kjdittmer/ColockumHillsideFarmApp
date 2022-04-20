package com.example.colockumhillsidefarmapp.user;

import com.example.colockumhillsidefarmapp.GlobalStorage;

public class User {

    private TypeOfUser typeOfUser;
    private static User instance;
    private String user;

    public User() {
        this.typeOfUser = null;
    }

    public static User getInstance() {
        if(instance == null){
            instance = new User();
        }
        return instance;
    }

    public void setCustomer(String user) {
        this.user = user;
        typeOfUser = TypeOfUser.CUSTOMER;
    }
}
