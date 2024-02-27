package org.example.services;

import org.mindrot.jbcrypt.BCrypt;

public class UserServiceUtil {
    public static String getPassword(String password)
    {
        String hashedPassword= BCrypt.hashpw(password,BCrypt.gensalt());
        return hashedPassword;
    }
    public static Boolean checkPassword(String userPassword,String hashedPassword)
    {
        return BCrypt.checkpw(userPassword,hashedPassword);
    }


}
