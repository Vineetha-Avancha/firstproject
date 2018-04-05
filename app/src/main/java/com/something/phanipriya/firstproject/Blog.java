package com.something.phanipriya.firstproject;



public class Blog {

    private String title,description,name,branch,rollnumber,password;

    public String getTitle() {
        return title;
    }

    public Blog(){

    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getName() {
        return name;
    }

    public String getRollnumber() {
        return rollnumber;
    }

    public void setRollnumber(String rollnumber) {
        this.rollnumber = rollnumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Blog(String title, String description, String name, String branch, String rollnumber, String password) {
        this.title = title;
        this.description=description;
        this.name=name;
        this.branch=branch;
        this.rollnumber=rollnumber;
        this.password=password;
    }





}








