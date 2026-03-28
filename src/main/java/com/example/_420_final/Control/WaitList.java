package com.example._420_final.Control;

import java.util.LinkedList;
import java.util.Queue;

public class WaitList {
    private Queue<Booking> waitList;
    private String eventId;



    public WaitList(){

    }

    public WaitList(Queue<Booking> waitList, String eventId){
        this.eventId = eventId;
        this.waitList = new LinkedList<>();
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setWaitList(Queue<Booking> waitList) {
        this.waitList = waitList;
    }

    public Queue<Booking> getWaitList() {
        return waitList;
    }
}
