package com.alfredsson.lifelog.service;

import com.alfredsson.lifelog.model.Journal;
import com.alfredsson.lifelog.repository.JournalRepository;

public class JournalService {

    private JournalRepository journalRepository;

    public JournalService() {
        this.journalRepository = new JournalRepository();
    }

    public Journal getJournal(String username) {
        Journal list = journalRepository.getPages(username);

        if(list==null) {
            list = new Journal(username);
        }
        return list;
    }

    //public void addJournal(String username, Journal )
}
