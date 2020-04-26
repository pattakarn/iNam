package com.sungkunn.inam.old_ver.singletion;

class UserData {

    private String uid;
    private String email;
    private String name;
    private String providerID;

    private static final UserData ourInstance = new UserData();

    static UserData getInstance() {
        return ourInstance;
    }

    private UserData() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProviderID() {
        return providerID;
    }

    public void setProviderID(String providerID) {
        this.providerID = providerID;
    }
}
