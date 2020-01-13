package com.example.sqlite_login;

import java.util.ArrayList;

public class StudentDetails {
    private String name, programme, password, row_id;
    private int level, id_number;
    private static ArrayList<StudentDetails> details;

    public StudentDetails(String name, String programme, String password, int level, int id_number, String row_id) {
        this.name = name;
        this.programme = programme;
        this.password = password;
        this.row_id = row_id;
        this.level = level;
        this.id_number = id_number;
    }

    public StudentDetails(){}

    public static ArrayList<StudentDetails> getDetails() {
        return details;
    }

    public String getRow_id() {
        return row_id;
    }

    public void setRow_id(String row_id) {
        this.row_id = row_id;
    }

    public static void setDetails(ArrayList<StudentDetails> details) {
        StudentDetails.details = details;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getProgramme() { return programme; }

    public void setProgramme(String programme) { this.programme = programme; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public int getLevel() { return level; }

    public void setLevel(int level) { this.level = level; }

    public int getId_number() { return id_number; }

    public void setId_number(int id_number) { this.id_number = id_number; }
}
