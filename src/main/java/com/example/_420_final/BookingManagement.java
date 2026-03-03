package com.example._420_final;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class BookingManagement {
    EventManagement e = new EventManagement();
    UserManagement u = new UserManagement();
    ArrayList<Event> eList = EventManagement.getEventList();
    ArrayList<User> uList = UserManagement.getUserList();
    private static ArrayList<Booking> bookingList = new ArrayList<Booking>();
    Scanner myObj = new Scanner(System.in);

    public void bookEvent(String userId, String eventId){
        Event event = e.getEvent(eventId);
        User user = u.getUser(userId);
        if(user.alreadyIn(eventId)){
            System.out.println("You have already booked this event");
        } else if(event.getCapacity() == 0){
            System.out.println("The event is full, you will be wait listed"); // add waitlist code
        }else if(user.isFull()) {
            System.out.println("Your allowed booking are full, you will be wait listed for this event");// add waitlist code
        }else{
            if(event instanceof Concert){
                System.out.println("The Age Restriction for this event is " + ((Concert) event).getAgeRestriction());
            }
            boolean test = true;
            while (test){
                System.out.println("Do you want to book the event (Y/N): ");
                String ans = myObj.nextLine();
                if (ans.equalsIgnoreCase("y")){
                    for(int i = 0; i < user.getUserBook().length; i++){
                        if(user.getUserBook()[i] == null){
                            String bookingId = generateUniqueId();
                            LocalTime time = LocalTime.now();
                            DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
                            String formattedTime = time.format(format);
                            Booking tempBooked = new Booking(bookingId, userId, eventId, formattedTime, "Confirmed");
                            user.getUserBook()[i] = tempBooked;
                            bookingList.add(tempBooked);
                            break;
                        }
                    }
                    test =false;
                    event.setCapacity(event.getCapacity()-1);
                    System.out.println("The event is Booked");
                }else if (ans.equalsIgnoreCase("n")){
                    test =false;
                    System.out.println("The event was not Booked");
                }else{
                    System.out.println("the answer given is not accepted please try again.");
                }
            }
        }

    }

    public void cancelEvent(String userId, String eventId){
        boolean test = true;
        User user = u.getUser(userId);
        Event event =e.getEvent(eventId);

        while (test){
            System.out.println("Are you sure you want to cancelled (Y/N):");
            String ans = myObj.nextLine();
            if (ans.equalsIgnoreCase("y")){
                user.cancelledBooked(eventId);
                // add wait list here in the next if statement
                //if(waitlist.isEmpty){
                //  event.setCapacity(event.getCapacity+1);
                // }else{
                // code for waitlist here
                // }
                test = false;
            }else if (ans.equalsIgnoreCase("n")){
                System.out.println("The event is still going"); // change later
                test = false;
            }else{
                System.out.println("the answer given is not accepted please try again.");
            }
        }
    }

    public static String generateUniqueId(){
        Random random = new Random();
        String id;
        do {
            int number = 100000000 + random.nextInt(900000000);
            // guarantees 9 digits
            id = String.valueOf(number);

        } while (idExists(id)); // repeat if already used

        return id;
    }

    private static boolean idExists(String id) {
        for (Booking user : bookingList) {
            if (user.getBookingId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public void viewUserBooking(String userId){
        for (User us: uList){
            if(us.getUserId().equalsIgnoreCase(userId)){
                Booking[] book = us.getUserBook();
                for (Booking booking : book) {
                    booking.print();
                    e.getEvent(booking.getEventId()).print();
                }
            }

        }
    }


    public void viewEventRoster(String eventId){
        System.out.println("Confirmed List: ");
        for(Booking b: bookingList){
            if(b.getEventId().equalsIgnoreCase(eventId)){
                System.out.println(b.getUserId());
            }
        }
        /*  This is just an idea of how it would work
        System.out.println("WaitList List: ");
        for(WaitList w: WaitList){
            if(b.getEventId().equalsIgnoreCase(eventId)){
                System.out.println(b.getUserId());
            }
        }*/
    }

    public static ArrayList<Booking> getBookingList() {
        return bookingList;
    }
}
