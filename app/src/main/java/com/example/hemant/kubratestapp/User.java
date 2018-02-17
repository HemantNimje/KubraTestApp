package com.example.hemant.kubratestapp;

/**
 * Created by Hemant on 2/16/2018.
 */

public class User {
    // User name
    private String mName;

    // User address
    private String mAddress;

    /**
     * Create new user object
     *
     * @param name    is name for the user
     * @param address is the address of the user
     */

    public User(String name, String address) {
        mName = name;
        mAddress = address;
    }

    // Get user name
    public String getName() {
        return mName;
    }

    // Set user name
    public void setName(String name) {
        mName = name;
    }

    // Get user address
    public String getAddress() {
        return mAddress;
    }

    // Set user address
    public void setAddress(String address) {
        mAddress = address;
    }
}
