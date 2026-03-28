package com.example._420_final;

import com.example._420_final.Management.UserManagement;

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

        if (event == null) {
            System.out.println("Event not found");
            return;
        }
        if (user == null) {
            System.out.println("User not found");
            return;
        }
        if (event.getStatus() != null && event.getStatus().equalsIgnoreCase("cancelled")) {
            System.out.println("Event is cancelled; no new bookings allowed.");
            return;
        }

        if(user.alreadyIn(eventId)){
            System.out.println("You have already booked this event");
        } else if(event.getCapacity() == 0){
            System.out.println("The event is full, you will be wait listed");

            String bookingId = generateUniqueId();
            String formattedTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            Booking waitlisted = new Booking(bookingId, userId, eventId, formattedTime, "Waitlisted");

            bookingList.add(waitlisted);
            WaitListManagement.addToWaitlist(waitlisted);

        } else if(user.isFull()) {
            System.out.println("Your allowed booking are full, you will be wait listed for this event");

            String bookingId = generateUniqueId();
            String formattedTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            Booking waitlisted = new Booking(bookingId, userId, eventId, formattedTime, "Waitlisted");

            bookingList.add(waitlisted);
            WaitListManagement.addToWaitlist(waitlisted);

        } else {
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
                            String formattedTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
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
        Event event = e.getEvent(eventId);

        if (event == null) {
            System.out.println("Event not found");
            return;
        }
        if (user == null) {
            System.out.println("User not found");
            return;
        }

        while (test){
            System.out.println("Are you sure you want to cancelled (Y/N):");
            String ans = myObj.nextLine();
            if (ans.equalsIgnoreCase("y")){
                user.cancelledBooked(eventId);

                // If there is someone on the waitlist, promote them; otherwise free a seat
                Booking promoted = WaitListManagement.promoteNext(eventId);
                if (promoted == null) {
                    event.setCapacity(event.getCapacity() + 1);
                } else {
                    // Optional: actually put promoted booking into the user's confirmed bookings array
                    User promotedUser = u.getUser(promoted.getUserId());
                    if (promotedUser != null) {
                        for (int i = 0; i < promotedUser.getUserBook().length; i++) {
                            if (promotedUser.getUserBook()[i] == null) {
                                promotedUser.getUserBook()[i] = promoted;
                                break;
                            }
                        }
                    }
                    System.out.println("Automatic promotion occurred: " + promoted.getUserId() + " promoted for event " + eventId);
                }

                test = false;
            }else if (ans.equalsIgnoreCase("n")){
                System.out.println("The event is still going");
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

    /**
     * GUI-friendly booking: no console prompts.
     * Returns a human-readable result message for the UI.
     */
    public String bookEventGui(String userId, String eventId) {
        Event event = e.getEvent(eventId);
        User user = u.getUser(userId);

        if (event == null) return "Event not found";
        if (user == null) return "User not found";
        if (event.getStatus() != null && event.getStatus().equalsIgnoreCase("cancelled")) {
            return "Event is cancelled; no new bookings allowed.";
        }
        if (user.alreadyIn(eventId)) {
            return "User already has a booking for this event.";
        }

        String bookingId = generateUniqueId();
        String formattedTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        if (event.getCapacity() == 0 || user.isFull()) {
            Booking waitlisted = new Booking(bookingId, userId, eventId, formattedTime, "Waitlisted");
            bookingList.add(waitlisted);
            WaitListManagement.addToWaitlist(waitlisted);
            return "Created WAITLISTED booking (" + bookingId + ").";
        }

        Booking confirmed = new Booking(bookingId, userId, eventId, formattedTime, "Confirmed");
        for (int i = 0; i < user.getUserBook().length; i++) {
            if (user.getUserBook()[i] == null) {
                user.getUserBook()[i] = confirmed;
                break;
            }
        }
        bookingList.add(confirmed);
        event.setCapacity(event.getCapacity() - 1);
        return "Created CONFIRMED booking (" + bookingId + ").";
    }

    /**
     * GUI-friendly cancellation: cancels the user's confirmed booking for eventId if present,
     * then promotes from waitlist if possible.
     */
    public String cancelBookingGui(String userId, String eventId) {
        Event event = e.getEvent(eventId);
        User user = u.getUser(userId);

        if (event == null) return "Event not found";
        if (user == null) return "User not found";

        // Cancel confirmed booking held in user's array
        user.cancelledBooked(eventId);

        Booking promoted = WaitListManagement.promoteNext(eventId);
        if (promoted == null) {
            event.setCapacity(event.getCapacity() + 1);
            return "Cancelled booking. No one to promote; capacity increased.";
        }

        // Put promoted booking into promoted user's confirmed booking array
        User promotedUser = u.getUser(promoted.getUserId());
        if (promotedUser != null) {
            for (int i = 0; i < promotedUser.getUserBook().length; i++) {
                if (promotedUser.getUserBook()[i] == null) {
                    promotedUser.getUserBook()[i] = promoted;
                    break;
                }
            }
        }
        return "Cancelled booking. Promotion happened: userId=" + promoted.getUserId() + " for eventId=" + eventId;
    }
}
