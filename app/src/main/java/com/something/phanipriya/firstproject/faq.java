package com.something.phanipriya.firstproject;


public class faq {

   public String querytitle;
    public String password;
    public faq(){
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQuerytitle() {
        return querytitle;
    }

    public void setQuerytitle(String querytitle) {
        this.querytitle = querytitle;
    }

    public faq(String title1, String passsword) {
        this.querytitle = title1;

        this.password=passsword;
    }



}

