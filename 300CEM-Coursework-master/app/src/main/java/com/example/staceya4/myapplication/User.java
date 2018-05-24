package com.example.staceya4.myapplication;

/**
 * Created by astaceya4 on 08/11/2017.
 */

public class User {
    private String forename;
    private String surname;
    private String email;
    private String username;
    private String password;

    public User(String forename, String surname, String email, String username, String password){
        this.forename = forename;
        this.surname = surname;
        this.email = email;
        this.username = username;
        this.password = password;

    }

    public String getForename() { return forename;}
    public void setForename(String forename) {this.forename = forename;}

    public String getSurname() { return surname;}
    public void setSurname(String surname) {this.surname = surname;}

    public String getEmail() { return email;}
    public void setEmail(String email) {this.email = email;}

    public String getUsername() { return username;}
    public void setUsername(String username) {this.username = username;}

    public String getPassword() { return password;}
    public void setPassword(String password) {this.password = password;}







}
