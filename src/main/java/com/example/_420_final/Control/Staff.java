package com.example._420_final.Control;

public class Staff extends User{

    public Staff(){
    }

    public Staff(String userId, String name, String email){
        super(userId, name, email);
        setUserBook(new Booking[5]);
    }
}
