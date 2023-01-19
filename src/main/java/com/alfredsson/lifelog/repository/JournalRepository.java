package com.alfredsson.lifelog.repository;

import com.alfredsson.lifelog.db.MysqlDatabase;
import com.alfredsson.lifelog.model.Journal;
import com.alfredsson.lifelog.model.Page;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.time.LocalDate;

public class JournalRepository {
    private static MysqlDatabase db;

    public JournalRepository() {
        db = MysqlDatabase.getInstance();
    }

    public Journal getPages(String username) {
        Connection conn = db.getConnection();
        Journal list = new Journal(username);
        String sql = "" +
                "SELECT * FROM journals " +
                "JOIN users " +
                "ON journal.user_id=users.id " +
                "WHERE users.username = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            if(!rs.next()) { return null; } //guardian clause

            System.out.println(username);
            do {
                Page test = new Page();
                test.setId(rs.getInt("user_id"));
                test.setDate(rs.getDate("date"));
                test.setTitle(rs.getString("title"));
                test.setContent(rs.getString("content"));

                list.getPages().add(test);
            } while(rs.next());


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public static void addContent(String username, String title, String content) {
        if(username==null){
            return;
        }

        db = MysqlDatabase.getInstance();
        Connection conn = db.getConnection();

        int shareable = 0;

        String sql = "INSERT INTO journals (owner_username, date, title, content, shareable)" +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, username);
            pstmt.setDate(2, Date.valueOf(LocalDate.now()));
            pstmt.setString(3, title);
            pstmt.setString(4, content);
            pstmt.setInt(5, shareable);

            pstmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}