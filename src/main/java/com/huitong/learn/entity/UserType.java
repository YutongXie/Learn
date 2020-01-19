package com.huitong.learn.entity;

public enum UserType {
    ADMIN(1, "ADMIN"),
    INTERNAL_USER(2,"INTERNAL_USER"),
    CUSTOMER(3, "CUSTOMER"),
    UNKNOWN(4, "UNKNOWN");

    private int code;
    private String name;
    private UserType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getName(int code) {
        for (UserType userType : UserType.values()) {
            if (userType.getCode() == code) {
                return userType.getName();
            }
        }
        return UserType.UNKNOWN.getName();
    }

    public static UserType getUserTpye(String name) {
        for(UserType userType : UserType.values()) {
            if(userType.getName().equals(name)) {
                return userType;
            }
        }
        return UserType.UNKNOWN;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
