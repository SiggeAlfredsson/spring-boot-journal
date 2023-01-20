package com.alfredsson.lifelog.repository;

import com.alfredsson.lifelog.db.MysqlDatabase;
import com.alfredsson.lifelog.model.Journal;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JournalRepository {
    private static MysqlDatabase db;

    public JournalRepository() {
        db = MysqlDatabase.getInstance();
    }

    public static List<Journal> getContent(String username, String date) {
        db = MysqlDatabase.getInstance();
        Connection conn = db.getConnection();

        String sql = "SELECT title, content FROM journals WHERE owner_username = ? AND date = ? ";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, date);
            ResultSet rs = stmt.executeQuery();
            List<Journal> journals = new ArrayList<>();
            while (rs.next()) {
                Journal journal = new Journal();
                journal.setTitle(rs.getString("title"));
                journal.setContent(rs.getString("content"));
                journals.add(journal);
            }
            return journals;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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