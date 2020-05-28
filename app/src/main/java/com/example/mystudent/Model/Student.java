package com.example.mystudent.Model;

public class Student {

    private String id;
    private String number;
    private String name;
    private String course;
    private byte[] phto;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public byte[] getPhto() {
        return phto;
    }

    public void setPhto(byte[] phto) {
        this.phto = phto;
    }

}
