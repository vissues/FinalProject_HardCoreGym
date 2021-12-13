package com.example.hardcoregym;

public class AppUser_Info {

    public String FirstName, LastName, Email,PhoneNumber,DateOfBirth ;

    AppUser_Info(){}

    AppUser_Info(String firstName, String lastName, String phoneNumber, String dateOfBirth){
        this.FirstName= firstName;
        this.LastName=lastName;
        this.PhoneNumber= phoneNumber;
        this.DateOfBirth = dateOfBirth;


    }

    public AppUser_Info(String firstName, String lastName, String email, String phoneNumber, String dateOfBirth) {
        this.FirstName = firstName;
        this.LastName = lastName;
        this.Email = email;
        this.PhoneNumber =  phoneNumber;
        this.DateOfBirth = dateOfBirth;
    }
}
