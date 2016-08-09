package com.example.databaseandroid2;

/**
 * Created by Ardians-PC on 8/9/2016.
 */
public class Student {
    private String name;
    private String address;
    private String nim;

    public Student(){}

    public Student(String nim, String name, String address) {
        this.name = name;
        this.address = address;
        this.nim = nim;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }
}
