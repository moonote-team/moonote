/************************************************/
/* program: Entry.java                          */
/* author: Yathavan                             */
/* purpose: db class representation             */
/* project: UofT Hackathon 2021                 */
/************************************************/

package com.example.moonote.Journal;

public class Entry {
    private int _id;
    private String body;
    private Long date;
    private double sentiment;
    private double latitude, longitude;

    public Entry() {
        _id = 1;
        body = "";
        date = 0L;
        sentiment = 0.0;
        latitude = 0.0;
        longitude = 0.0;
    }

    public Entry(String body, Long date) {
        this.body = body;
        this.date = date;
    }

    public int get_id() {
        return _id;
    }

    public String getBody() {
        return body;
    }

    public Long getDate() {
        return date;
    }

    public Double getSentiment() {
        return sentiment;
    }
}
