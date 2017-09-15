package com.github.regissda.restaurantvoting;

/**
 * Created by MSI on 14.09.2017.
 */
public class AutorizedUser {
    private static  String userName = "testuser1";

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        AutorizedUser.userName = userName;
    }
}
