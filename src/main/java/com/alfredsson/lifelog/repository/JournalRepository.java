package com.alfredsson.lifelog.repository;

import com.alfredsson.lifelog.db.MysqlDatabase;
import com.alfredsson.lifelog.model.Journal;
import com.alfredsson.lifelog.model.Page;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

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

    public void addContent(String username, Page test) {
        Connection conn = db.getConnection();
        String sql = "SELECT id FROM users WHERE username=?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();
            int user_id = 0;

            if(user_id == 0) {
                System.out.println("Summ wrong here");
            }

                user_id = rs.getInt("id");


            sql = "INSERT INTO Journal (user_id, date, title, content)" +
                    "VALUES (?, ?, ?, ?)";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, user_id);
            pstmt.setDate(2, (Date) test.getDate());
            pstmt.setString(3, test.getTitle());
            pstmt.setString(4,test.getContent());

            pstmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}