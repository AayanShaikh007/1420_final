package com.example._420_final;

public abstract class Event {
    private String eventId;
    private String title;
    private String dateTime;
    private String location;
    private int capacity;
    private String  status; // Active / Cancelled

    public Event(){
    }

    public Event(String eventId, String title, String dateTime, String location, int capacity, String status){
        this.eventId = eventId;
        this.title = title;
        this.dateTime = dateTime;
        this.location = location;
        this.capacity = capacity;
        this.status = status;
    }

    public abstract void setSpecificData(String data);


    public void print(){
        System.out.println("EventId: " +eventId);
        System.out.println("Title: " +title);
        System.out.println("main.java.Event date: " +dateTime);
        System.out.println("main.java.Event Location: " +location);
        System.out.println("main.java.Event capacity: " +capacity);
        System.out.println("main.java.Event Status: " +status);
    }

    public void printOrder(){
        System.out.println("1. EventId: " +eventId);
        System.out.println("2. Title: " +title);
        System.out.println("3. main.java.Event date: " +dateTime);
        System.out.println("4. main.java.Event Location: " +location);
        System.out.println("5. main.java.Event capacity: " +capacity);
        System.out.println("6. main.java.Event Status: " +status);
    }


    public String getStatus() {
        return status;
    }

    public String getEventId() {
        return eventId;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getLocation() {
        return location;
    }

    public String getTitle() {
        return title;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
