package com.example.plan.Modal;

public class User {
    private String id;
    private String fullname;
    private String gender;
    private String age;
    private String imageurl;
    private String numplan;

    public User(String id, String fullname, String gender, String age, String imageurl, String numplan) {
        this.id = id;
        this.fullname = fullname;
        this.gender = gender;
        this.age = age;
        this.imageurl = imageurl;
        this.numplan = numplan;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getNumplan() {
        return numplan;
    }

    public void setNumplan(String numplan) {
        this.numplan = numplan;
    }
}
