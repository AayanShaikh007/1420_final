package com.example._420_final.Control;

public class Guest extends User{

    public Guest(){
    }

    public Guest(String userId, String name, String email){
        super(userId, name, email);
        setUserBook(new Booking[1]);
    }
}
