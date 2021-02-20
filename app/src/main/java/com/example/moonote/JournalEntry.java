package com.example.moonote;

public class JournalEntry {
    String text;
    String id;

    public JournalEntry(String text, String id) {
        this.text = text;
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public String getId() {
        return id;
    }
}
