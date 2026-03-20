package com.example._420_final.Management;

import com.example._420_final.Control.Booking;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WaitListManagement {

    // eventId -> waitlisted bookings (FIFO)
    private static final Map<String, Deque<Booking>> waitlistsByEventId = new HashMap<>(); //for each eventID we store a queue

    // Simple “notification channel” for UI/testing
    private static String lastPromotionNotification = null;

    public static void addToWaitlist(Booking booking) {
        if (booking == null || booking.getEventId() == null) return;

        waitlistsByEventId
                .computeIfAbsent(booking.getEventId(), k -> new ArrayDeque<>()) //get queue for the event, if missing, create it, then append to the end
                .addLast(booking); //new waitlisted booking goes to the back
    }

    public static List<Booking> getWaitlistForEvent(String eventId) { //looks up queue, if none exists, returns empty list, otherwise returns a new ArrayList
        Deque<Booking> q = waitlistsByEventId.get(eventId);
        if (q == null) return List.of();
        return new ArrayList<>(q); // preserve current order, so it will display row 1 as "first" etc
    }

    public static boolean removeWaitlistedBooking(String eventId, String bookingId) { //finds the event's queue, removes the one booking whose bookingId matches, leaves others where they are
        Deque<Booking> q = waitlistsByEventId.get(eventId);
        if (q == null || q.isEmpty()) return false;

        boolean removed = q.removeIf(b -> b.getBookingId().equalsIgnoreCase(bookingId));//removes matching elements without reordering queue
        if (q.isEmpty()) {
            waitlistsByEventId.remove(eventId);
        }
        return removed;
    }


    public static Booking promoteNext(String eventId) {
        Deque<Booking> q = waitlistsByEventId.get(eventId); //checks waitlist queue for that event
        if (q == null || q.isEmpty()) return null;

        Booking promoted = q.removeFirst(); //pops the front of the queue
        promoted.setBookingStatus("Confirmed"); //changes status to confirmed

        if (q.isEmpty()) {
            waitlistsByEventId.remove(eventId); //if empty/nobody to promote, event capacity should inc by 1
        }

        lastPromotionNotification = //displays notification
                "Promotion: userId=" + promoted.getUserId() +
                " was promoted to Confirmed for eventId=" + promoted.getEventId();

        return promoted;
    }


    public static String consumeLastPromotionNotification() { //reads the msg then clears it so it doesn't show again and again
        String msg = lastPromotionNotification;
        lastPromotionNotification = null;
        return msg;
    }
}
