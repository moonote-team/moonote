/************************************************/
/* program: Entry.java                          */
/* author: Yathavan                             */
/* purpose: db class representation             */
/* project: UofT Hackathon 2021                 */
/************************************************/

package com.example.moonote.Journal;

import java.sql.Time;

public class Entry
{
    private String description;
    private Time time;

    public Entry()
    {
        description = "";
        time = null;
    }

    public Entry(String description, Time time)
    {
        this.description = description;
        this.time = time;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Time getTime()
    {
        return time;
    }

    public void setTime(Time time)
    {
        this.time = time;
    }
}
