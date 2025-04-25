package com.example.essatconnect;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Student {
    private String cin;
    private String fullname;
    private String email;
    private String studentClass;
    private String password;

    // Constructeur
    public Student(String cin, String fullname, String email, String studentClass, String password) {
        this.cin = cin;
        this.fullname = fullname;
        this.email = email;
        this.studentClass = studentClass;
        this.password = password;
    }
    public Student() {
        // Constructeur par défaut requis par Firebase
    }

    // Getters et setters
    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
