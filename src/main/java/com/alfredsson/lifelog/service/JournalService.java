package com.alfredsson.lifelog.service;

import com.alfredsson.lifelog.model.Journal;
import com.alfredsson.lifelog.repository.JournalRepository;

import java.util.List;

public class JournalService {

 //   private static JournalRepository journalRepository;

   /* public JournalService() {
        this.journalRepository = new JournalRepository();
    } */


    static String date = "2023-01-19";


    public static String getJournal(String username) {

        List<Journal> title = JournalRepository.getContent(username, date);

        return title.toString();
    }

    //public void addJournal(String username, Journal )
}
