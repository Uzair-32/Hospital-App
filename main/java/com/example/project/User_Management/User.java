package com.example.project.User_Management;


// base class of all the other classes of package
public abstract class User {
    private String userID;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private String address;
    private int age;
    private String gender;
    private boolean accountStatus;

    // a default constructor 
    User() {

    }
    // Constructor to initialize all attributes
    public User(String userID, String name, String email, String phoneNumber, String password,
                String address, int age, String gender, boolean accountStatus) {
        setUserID(userID);
        setName(name);
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setPassword(password);
        setAddress(address);
        setAge(age);
        setGender(gender);
        setAccountStatus(accountStatus);
    }

    // Setters with Validation
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAccountStatus(boolean accountStatus) {
        this.accountStatus = accountStatus;
    }

    // Getters
    public String getUserID() { return userID; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getPassword() { return password; }
    public String getAddress() { return address; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public boolean isAccountStatus() { return accountStatus; }

    
    @Override
    public String toString() {
        return String.format("User ID: %s\nName: %s\nEmail: %s\nPhone: %s\nAge: %d\nGender: %c\nAccount Status: %s",
                userID, name, email, phoneNumber, age, gender,
                accountStatus ? "Active" : "Inactive");
    }
}
