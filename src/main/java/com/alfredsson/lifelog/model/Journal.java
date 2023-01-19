package com.alfredsson.lifelog.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Journal {

    private List<Page> pages;
    private String owner;

    public Journal(String owner) {
        this.owner = owner;
        this.pages = new ArrayList<>();
    }

    public String getOwner() {
        return owner;
    }

    public List<Page> getPages() {
        return pages;
    }
}
