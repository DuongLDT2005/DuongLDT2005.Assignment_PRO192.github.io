package com.example.student_demo.model.user;

public  class User {
   	private String id;
   	private String username;
   	private String password;
   	private String role;
   	private String name;
   	private String email;
   	private long phonenumber;
   	private boolean status;//on==true or off==false

    public User(String email, String id, String name, String password, long phonenumber, String role, boolean status, String username) {
        this.email = email;
        this.id = id;
        this.name = name;
        this.password = password;
        this.phonenumber = phonenumber;
        this.role = role;
        this.status = status;
        this.username = username;
    }

    public User(String email, String name, String password, long phonenumber, String role, boolean status, String username) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.phonenumber = phonenumber;
        this.role = role;
        this.status = status;
        this.username = username;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public long getPhonenumber() {
        return phonenumber;
    }

    public boolean isStatus() {
        return status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhonenumber(long phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    
    
}
