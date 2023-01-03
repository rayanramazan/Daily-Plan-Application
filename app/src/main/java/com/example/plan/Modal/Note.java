package com.example.plan.Modal;

public class Note {

    private String pulisherid;
    private String subject;
    private String date;
    private String noteid;
    private String tb;
    private String ark;
    private String face;
    private String health;
    private String importand;
    private String rotin;
    private String thanks;
    private String today;


    public Note(String subject,String pulisherid, String date, String noteid, String tb, String ark, String face, String health, String importand, String rotin, String thanks, String today) {
        this.pulisherid = pulisherid;
        this.subject = subject;
        this.date = date;
        this.noteid = noteid;
        this.tb = tb;
        this.ark = ark;
        this.face = face;
        this.health = health;
        this.importand = importand;
        this.rotin = rotin;
        this.thanks = thanks;
        this.today = today;
    }

    public Note() {
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPulisherid() {
        return pulisherid;
    }

    public void setPulisherid(String pulisherid) {
        this.pulisherid = pulisherid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNoteid() {
        return noteid;
    }

    public void setNoteid(String noteid) {
        this.noteid = noteid;
    }

    public String getTb() {
        return tb;
    }

    public void setTb(String tb) {
        this.tb = tb;
    }

    public String getArk() {
        return ark;
    }

    public void setArk(String ark) {
        this.ark = ark;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getImportand() {
        return importand;
    }

    public void setImportand(String importand) {
        this.importand = importand;
    }

    public String getRotin() {
        return rotin;
    }

    public void setRotin(String rotin) {
        this.rotin = rotin;
    }

    public String getThanks() {
        return thanks;
    }

    public void setThanks(String thanks) {
        this.thanks = thanks;
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }
}
