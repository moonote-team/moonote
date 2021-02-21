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
    private Double sentiment;
    private Double latitude, longitude;

    public Entry() {
        _id = 1;
        body = "";
        date = 0L;
        sentiment = 0.0d;
        latitude = 0.0;
        longitude = 0.0;
    }

    public Entry(String body, Long date) {
        this.body = body;
        this.date = date;
    }

    public Entry(String body, Long date, int _id) {
        this.body = body;
        this.date = date;
        this._id = _id;
    }

    public Entry(String body, Long date, int _id, Double sentiment) {
        this.body = body;
        this.date = date;
        this._id = _id;
        this.sentiment = sentiment;
    }

    public Entry(String body, Long date, Double latitude, Double longitude)
    {
        this.body = body;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Entry(String body, Long date, int _id, Double latitude, Double longitude)
    {
        this.body = body;
        this.date = date;
        this._id = _id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int get_id() {
        return _id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String newBody) {
        this.body = newBody;
    }

    public Long getDate() {
        return date;
    }

    public Double getSentiment() {
        return sentiment;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setSentiment(Double sentiment) {
        this.sentiment = sentiment;
    }
}
