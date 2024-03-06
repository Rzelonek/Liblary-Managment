package rzelonek.libsys.auth;

import rzelonek.libsys.db.UserData;
import rzelonek.libsys.model.User;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Auth {
    private final UserData userRepository = new UserData();

    private static final String SALT = "oGvZxgE'i0E+%qnVm7$#AZGL%x3Bua";
    public static User loggedUser = null;

    public boolean authenticate(String login, String password) {

        User userFromDB = userRepository.getByLogin(login);
        if (userFromDB != null && verifyPassword(password, userFromDB.getPassword())) {
            loggedUser = userFromDB;
            return true;
        }
        return false;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest((password + SALT).getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    private boolean verifyPassword(String password, String hashedPassword) {
        String hashedInput = hashPassword(password);
        return hashedInput.equals(hashedPassword);
    }

    private static String hashPassword2(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest((password + SALT).getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public static String ChangetoHashed(String password) {
        return hashPassword2(password);
    }

    // // Add admin user to the database for testing reasons
    // public void addAdminUser() {
    // String adminLogin = "admin";
    // String adminPassword = "admin123";
    // String role = "Admin";
    // String hashedPassword = hashPassword(adminPassword);
    // User adminUser = new User(1,adminLogin, hashedPassword , role);
    // userRepository.addUser(adminUser);
    // System.out.println( " Login: " + adminLogin + " HashedPassword: " +
    // hashedPassword + " Role: " + role);
    // }

}
