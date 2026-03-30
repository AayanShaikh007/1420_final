package com.example._420_final.Control;

public class User {
    private String userId;
    private String name;
    private String email;
    private Booking[] userBook;
    private String[] wait;


    public User() {
    }

    public User(String userId, String name, String email){
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Booking[] getUserBook() {
        return userBook;
    }

    public void setUserBook(Booking[] userBook){
        this.userBook = userBook;
    }

    public boolean isEmpty() {

        if (userBook == null) {
            return true;
        }

        for (Booking b : userBook) {
            if (b != null) {
                return false; // found a booking
            }
        }

        return true; // all null
    }
    public boolean isFull() {

        if (userBook == null) {
            return false;
        }

        for (Booking book : userBook) {
            if (book == null) {
                return false; // found empty space
            }
        }

        return true; // no empty spaces
    }

    public boolean alreadyIn(String eventId){
        if(!isEmpty()){
            for(Booking b: userBook){
                if (b == null) continue;
                if(b.getEventId().equalsIgnoreCase(eventId)){
                    return true;
                }
            }
        }
        return false;
    }

    public void cancelledBooked(String eventId){
            for(int i =0; i< userBook.length; i++){
                if(userBook[i] != null && userBook[i].getEventId().equalsIgnoreCase(eventId)){
                    // Skip setting "Cancelled" status since we want full removal
                    userBook[i] = null;
                }
            }
        }

    public void print(){
        System.out.println("UserId: " + userId);
        System.out.println("Email: " +email);
        System.out.println("Name: " +name);
        System.out.println("Bookings: ");
        if(!isEmpty()){
            for (Booking b: userBook){
                b.print();
            }
        }
    }
}

