/************************************************/
/* program: Entry.java                          */
/* author: Yathavan                             */
/* purpose: db class representation             */
/* project: UofT Hackathon 2021                 */
/************************************************/

package com.example.moonote.Journal;

public class Entry {
    private Long _id;
    private String body;
    private Long date;
    private double sentiment;
    private double latitude, longitude;

    public Entry() {
        _id = 0L;
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

    public Entry(Long _id, String body, Long date) {
        this._id = _id;
        this.body = body;
        this.date = date;

    }

    public Long get_id() {
        return _id;
    }

    public String getBody() {
        return body;
    }

    public Long getDate() {
        return date;
    }
}
