package com.example.rushi.epic_thrillon;

/**
 * Created by rushi on 12/7/2017.
 */

public class User {

    String firstName,lastName,password,confirmPass;
    String mobileno;
    String email;

    public User(){

    }

    public User(String firstName,String lastName, String mobileno, String email,String password,String confirmPass) {
        this.firstName = firstName;
        this.lastName= lastName;
        this.mobileno = mobileno;
        this.email = email;
        this.password= password;
        this.confirmPass = confirmPass;
    }

    public String getFirstNameName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPass() {
        return confirmPass;
    }

    public String getMobileno() {
        return mobileno;
    }

    public String getEmail() {
        return email;
    }
}
