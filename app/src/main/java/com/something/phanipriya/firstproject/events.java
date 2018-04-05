package com.something.phanipriya.firstproject;


public class events {

    public String eventtitle;
    public String event;
    public String password;
    public events(){
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEventtitle() {
        return eventtitle;
    }

    public void setEventtitle(String eventtitle) {
        this.eventtitle = eventtitle;
    }

    public events(String title1,String title2,String password) {
        this.eventtitle = title1;
        this.event=title2;
        this.password=password;
    }



}