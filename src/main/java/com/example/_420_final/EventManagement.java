package com.example._420_final;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EventManagement {
    private static ArrayList<Event> eventList= new ArrayList<Event>();
    String eventId;
    String title;
    String dateTime;
    String location;
    int capacity;
    String  status; // Active / Cancelled
    String type;
    String typedata;
    Scanner myObj = new Scanner(System.in);
    boolean unique, unending;
    int update;
    ArrayList<Booking> blist = BookingManagement.getBookingList();
    public EventManagement(){
    }



    public void createEvent(){
        unique = true;
        System.out.println("Create event data needed: ");

        while(unique){
            System.out.print("eventId: ");
            eventId = myObj.nextLine();
            unique = checkId(eventId);
        }

        System.out.print("title: ");
        title = myObj.nextLine();
        unique = true;

        while (unique){
            System.out.print("Event date and time (dd/MM/yyyy HH:mm): ");
            dateTime = myObj.nextLine();
            unique = checkDate(dateTime);
        }

        System.out.print("Location: ");
        location = myObj.nextLine();
        unique = true;

        while (unique){
            System.out.println("Event capacity (needs to be more than 1): ");
            capacity = myObj.nextInt();
            myObj.nextLine();
            unique = checkCapacity(capacity);
        }
        unique = true;

        while (unique){
            System.out.print("Event status (Active / Cancelled): ");
            status = myObj.nextLine();
            unique = checkStatus(status);
        }
        unique = true;
        while (unique){
            System.out.print("Workshop\nSeminar\nConcert\nType of event: ");
            type = myObj.nextLine();
            unique = checkType(type);
        }

        typedata = checkDataType(type);

        if(type.equalsIgnoreCase("workshop")){
            Workshop w = new Workshop(eventId, title, dateTime, location, capacity, status,typedata);
            eventList.add(w);
        }else if(type.equalsIgnoreCase("seminar")){
            Seminar s = new Seminar(eventId, title, dateTime, location, capacity, status,typedata);
            eventList.add(s);
        } else if (type.equalsIgnoreCase("concert")) {
            Concert c = new Concert(eventId, title, dateTime, location, capacity, status, Integer.parseInt(typedata));
            eventList.add(c);
        }


    }

    public void updateEvent(String eventId){
        unending= unique = true;
        Event event;
        if(eventId.equalsIgnoreCase("")) {
            while (true) {
                System.out.print("Write the id of the event you want to update: ");
                eventId = myObj.nextLine();
                event = getEvent(eventId);
                if (event != null) {
                    break;
                } else {
                    System.out.println("The id writen was not found");
                }
            }
        }else {
            event = getEvent(eventId);
        }
        while (unending){
        System.out.println("Event Data: \n");
        event.printOrder();
        System.out.println("8. Exit");

        while (unending){
            System.out.print("Which data you want to update (write the number): ");
            update = myObj.nextInt();
            myObj.nextLine();
            unending = checkUpdate(update);
        }
        unique = true;
        switch (update){
            case 1:
                while(unique){
                    System.out.print("new eventId: ");
                    eventId = myObj.nextLine();
                    unique = checkId(eventId);
                }
                event.setEventId(eventId);
                break;
            case 2:
                System.out.print("new title: ");
                event.setTitle(myObj.nextLine());
                break;
            case 3:
                while (unique){
                    System.out.print("Event date and time (dd/MM/yyyy HH:mm): ");
                    dateTime = myObj.nextLine();
                    unique = checkDate(dateTime);
                }
                event.setDateTime(dateTime);
                break;
            case 4:
                System.out.print("New Location: ");
                event.setLocation(myObj.nextLine());
                break;
            case 5:
                while (unique){
                    System.out.println("Event capacity (needs to be more than 1): ");
                    capacity = myObj.nextInt();
                    myObj.nextLine();
                    unique = checkCapacity(capacity);
                }
                event.setCapacity(capacity);
                break;
            case 6:
                while (unique){
                    System.out.print("Event status (Active / Cancelled): ");
                    status = myObj.nextLine();
                    unique = checkStatus(status);
                }// if cancelled add the method cancel after creating it
                if(status.equalsIgnoreCase("Cancelled")){
                    cancelEvent(eventId);

                }
                event.setStatus(status);

                break;
            case 7:
                while (unique){
                    if(event instanceof Workshop){

                        typedata = checkDataType("workshop");
                    }else if(event instanceof Seminar){
                        typedata = checkDataType("seminar");
                    }else if(event instanceof Concert){
                        typedata = checkDataType("concert");
                    }
                    unique = false;
                }
                event.setSpecificData(typedata);
                break;
            case 8:
                unending =false;
                unique = false;
                break;
            default:
                break;
        }
        }



    }

    public void cancelEvent(String eventId){
        Event event;
        UserManagement u =new UserManagement();
        if(eventId.equalsIgnoreCase("")){
            while (true){
                System.out.print("Write the id of the event you want to Cancel: ");
                eventId = myObj.nextLine();
                event = getEvent(eventId);
                if(event != null){
                    break;
                }else {
                    System.out.println("The id writen was not found");
                }
            }
        }else{
            event = getEvent(eventId);
        }

        event.setStatus("Cancelled");
        if(!blist.isEmpty()){
            for(Booking b: blist){
                if(b.getEventId().equalsIgnoreCase(eventId)){
                    u.getUser(b.getUserId()).cancelledBooked(eventId);
                }
            }
        }

        //cancelled from the waitlist too, when added

    }
    public Event getEvent(String eventId){
        for(Event event : eventList){
            if(eventId.equals(event.getEventId())){
                return event;
            }
        }
        return null;
    }
    public String checkDataType(String type){
        if (type.equalsIgnoreCase("workshop")){
            System.out.print("What is the topic: ");
            typedata = myObj.nextLine();
        } else if (type.equalsIgnoreCase("seminar")) {
            System.out.print("What is the name of the speaker: ");
            typedata = myObj.nextLine();
        } else if (type.equalsIgnoreCase("concert")) {
            String pattern = "[0-9]{1,2}";
            do {
                System.out.print("What is the age restriction: ");
                typedata = myObj.nextLine();
            }while (!typedata.matches(pattern));
        }
        return typedata;
    }
    public boolean checkType(String type){
        List<String> acceptableAnswers = List.of("workshop", "seminar", "concert");
        if (!acceptableAnswers.contains(type.toLowerCase())) {
            System.out.println("The type given is not accepted");
            return true;
        }
        return false;
    }

    public boolean checkDate(String dateTime){
        String regex = "^\\d{2}/\\d{2}/\\d{4} ([01]\\d|2[0-3]):[0-5]\\d$";
        if (!dateTime.matches(regex)) {
            System.out.println("Format must be dd/MM/yyyy HH:mm");
            return true;
        }

        return false;
    }

    public boolean checkCapacity(int capacity){
        if (capacity> 0){
            return false;
        }else {
            System.out.println("Capacity needs to be at least 1.");
            return true;
        }
    }

    public boolean checkUpdate(int update) {
        return !(update > 0 && update <=7);
    }

    public boolean checkStatus(String status){
        List<String> acceptableAnswers = List.of("active", "cancelled");
        if (!acceptableAnswers.contains(status.toLowerCase())) {
            System.out.println("The type given is not accepted");
            return true;
        }
        return false;
    }

    public boolean checkId(String eventId){
        int size = eventList.size();
        if(size > 0){
            for (Event event : eventList) {
                if (eventId.equals(event.getEventId())) {
                    System.out.println("The event Id is already being used");
                    return true;
                }
            }

        }
        return false;
    }

    public void listEvent(){
        for (Event e : eventList){
            System.out.println("");
            e.print();
            System.out.println("");
        } // we need to change this when we get the gui
    }

    public Event searchByTitle(String title){
        for(Event event : eventList){
            if(title.equalsIgnoreCase(event.getEventId())){
                return event;
            }
        }
        return null;
    }

    public void filterByType(String type){
        for (Event event : eventList) {
            if(type.equalsIgnoreCase("workshop")){
                if (event instanceof Workshop) {
                    event.print();
                }
            }else if(type.equalsIgnoreCase("seminar")){
                if (event instanceof Seminar) {
                    event.print();
                }
            }else if(type.equalsIgnoreCase("Concert")){
                if (event instanceof Concert) {
                    event.print();
                }
            }
                // This one needs to change when we get the GUI
        }

    }
    public static  ArrayList<Event> getEventList() {
        return eventList;
    }
    /*
● Cancel an event: Mark an event as Cancelled so it is no longer bookable.
○ all Confirmed and Waitlisted bookings for that event are automatically set to
Cancelled, and the waitlist becomes empty.

*/
}
