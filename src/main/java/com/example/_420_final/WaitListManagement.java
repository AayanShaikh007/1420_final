package com.example._420_final;

import java.io.*;
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

    /**
     * Promotes the first waitlisted booking (FIFO) to Confirmed.
     * Returns the promoted booking or null if none exists.
     */
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

    /**
     * UI can call this after refresh to display the latest promotion message once.
     */
    public static String consumeLastPromotionNotification() { //reads the msg then clears it so it doesn't show again and again
        String msg = lastPromotionNotification;
        lastPromotionNotification = null;
        return msg;
    }

    // --- NEW PHASE 2 PERSISTENCE FUNCTIONS ---

    public static void saveWaitlists() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("waitlists.txt"))) {
            for (Map.Entry<String, Deque<Booking>> entry : waitlistsByEventId.entrySet()) {
                String eventId = entry.getKey();
                for (Booking b : entry.getValue()) {
                    // Format: EventId,BookingId,UserId,CreatedAt,Status
                    writer.println(eventId + "," + b.getBookingId() + "," + b.getUserId() + "," +
                            b.getCreatedAt() + "," + b.getBookingStatus());
                }
            }
        } catch (IOException e) {
            System.err.println("Waitlist Save Error: " + e.getMessage());
        }
    }

    public static void loadWaitlists() {
        File file = new File("waitlists.txt");
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length < 5) continue;

                String eventId = p[0];
                Booking b = new Booking(p[1], p[2], p[3], p[4], p[5]);

                // Put it back into the map
                waitlistsByEventId
                        .computeIfAbsent(eventId, k -> new java.util.ArrayDeque<>())
                        .addLast(b);
            }
        } catch (Exception e) {
            System.err.println("Waitlist Load Error: " + e.getMessage());
        }
    }
}
