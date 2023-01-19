package com.alfredsson.lifelog.repository;

import com.alfredsson.lifelog.db.MysqlDatabase;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

    private static MysqlDatabase db;

    public UserRepository() {
        db = MysqlDatabase.getInstance();
    }


    public static boolean usernameExists(String username) {
        db = MysqlDatabase.getInstance();
        Connection conn = db.getConnection();
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean login(String username, String password) {
        db = MysqlDatabase.getInstance();
        Connection conn = db.getConnection();
        String sql = "SELECT password FROM users WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String stored_hash = rs.getString("password");
                return checkPassword(password, stored_hash);
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static int createNew(String username, String password) {
        String hashedpassword = hashPassword(password);
        System.out.println(hashedpassword);

        db = MysqlDatabase.getInstance();
        Connection conn = db.getConnection();
        String sql = "INSERT INTO users (username,password) VALUES (?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, username);
            stmt.setString(2, hashedpassword);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static int workload = 12;

    public static String hashPassword(String password) {
        String salt = BCrypt.gensalt(workload);
        String hashed_password = BCrypt.hashpw(password, salt);

        return (hashed_password);
    }

    public static boolean checkPassword(String password, String stored_hash) {
        boolean password_verified = false;

        if (null == stored_hash || !stored_hash.startsWith("$2a$")) {
            return false;
        }
        password_verified = BCrypt.checkpw(password, stored_hash);

        return(password_verified);
    }


}
