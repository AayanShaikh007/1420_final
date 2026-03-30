package com.example._420_final.Management;

import com.example._420_final.Control.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.stream.Collectors;

public class EventManagement {
    private static ArrayList<Event> eventList = new ArrayList<>();

    public EventManagement() {}

    public void updateEvent(String id) {
        System.out.println("Console update not fully implemented. Please use the GUI.");
    }

    // ... existing code ...

    public void createEvent() {
        System.out.println("Console creation redirected to GUI logic...");
    }

    public void listEvent() {
        for (Event e : eventList) { e.print(); }
    }

    public void cancelEvent(String eventId) {
        System.out.println(cancelEventGui(eventId));
    }

    public void searchByTitle(String title) {
        List<Event> results = searchByTitleGui(title);
        if (results.isEmpty()) System.out.println("No events found.");
        else for (Event e : results) e.print();
    }

    public void filterByType(String type) {
        for (Event e : filterByTypeGui(type)) { e.print(); }
    }

    // gui logic methods

    public String updateEventGui(String eventId, String type, String title, String date, String loc, String cap, String specificData) {
        Event event = getEvent(eventId);
        if (event == null) return "Error: Event not found.";

        if (title != null && !title.isBlank()) event.setTitle(title.trim());
        if (date != null && !date.isBlank()) {
            if (checkDate(date.trim())) return "Error: Use yyyy-MM-ddTHH:mm";
            event.setDateTime(date.trim());
        }
        if (loc != null && !loc.isBlank()) event.setLocation(loc.trim());

        if (cap != null && !cap.isBlank()) {
            try {
                int capacity = Integer.parseInt(cap.trim());
                if (checkCapacity(capacity)) return "Error: Capacity must be > 0.";
                event.setCapacity(capacity);
            } catch (Exception e) {
                return "Error: Capacity must be a number.";
            }
        }

        if (specificData != null && !specificData.isBlank()) {
            event.setSpecificData(specificData.trim());
        }

        return "Success: Updated " + eventId;
    }

    public String createEventGui(String type, String id, String title, String date, String loc, String cap, String specificData) {
        if (checkId(id)) return "Error: Event ID exists.";
        if (checkDate(date)) return "Error: Use yyyy-MM-ddTHH:mm";

        int capacity;
        try {
            capacity = Integer.parseInt(cap);
            if (checkCapacity(capacity)) return "Error: Capacity > 0.";
        } catch (Exception e) { return "Error: Capacity must be a number."; }

        Event newEvent;
        switch (type.toLowerCase()) {
            case "workshop": newEvent = new Workshop(id, title, date, loc, capacity, "Active", specificData); break;
            case "seminar":  newEvent = new Seminar(id, title, date, loc, capacity, "Active", specificData); break;
            case "concert":
                try {
                    if(specificData.equalsIgnoreCase("all ages")){
                        newEvent = new Concert(id, title, date, loc, capacity, "Active", specificData);
                    }else {
                        Integer.parseInt(specificData);
                        newEvent = new Concert(id, title, date, loc, capacity, "Active", specificData);
                    }
                } catch (Exception e) { return "Error: Age must be a number."; }
                break;
            default: return "Error: Invalid Type.";
        }
        eventList.add(newEvent);
        return "Success: Created " + title;
    }

    public String cancelEventGui(String eventId) {
        Event event = getEvent(eventId);
        if (event == null) return "Error: Not found.";
        event.setStatus("Cancelled");
        for (User u : UserManagement.getUserList()) { u.cancelledBooked(eventId); }
        WaitListManagement.removeWaitlistedBooking(eventId, ""); // Existing logic placeholder
        return "Event " + eventId + " Cancelled.";
    }

    public List<Event> searchByTitleGui(String query) {
        return eventList.stream()
                .filter(e -> e.getTitle().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Event> filterByTypeGui(String type) {
        if (type == null || type.equalsIgnoreCase("All")) return eventList;
        return eventList.stream()
                .filter(e -> e.getClass().getSimpleName().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    public String updateEventGui(String id, String title, String date, String loc, String cap, String specificData) {
        Event event = getEvent(id);
        if (event == null) return "Error: Event not found.";

        // validate date if changed
        if (!date.isBlank() && !date.equalsIgnoreCase(event.getDateTime())) {
            if (checkDate(date)) return "Error: Use yyyy-MM-ddTHH:mm";
            event.setDateTime(date);
        }

        // validate capacity 
        if (!cap.isBlank()) {
            try {
                int capacity = Integer.parseInt(cap);
                if (checkCapacity(capacity)) return "Error: Capacity must be > 0.";
                
                // Ensure new capacity covers existing confirmed bookings
                int confirmed = BookingManagement.countBookingsForEvent(id);
                if (capacity < confirmed) {
                    return "Error: New capacity (" + capacity + ") is less than confirmed bookings (" + confirmed + ").";
                }
                event.setCapacity(capacity);
            } catch (Exception e) { return "Error: Capacity must be a number."; }
        }

        if (!title.isBlank()) event.setTitle(title);
        if (!loc.isBlank()) event.setLocation(loc);

        // update specific data for user. 
        if (!specificData.isBlank()) {
            if (event instanceof Workshop) ((Workshop) event).setTopic(specificData);
            else if (event instanceof Seminar) ((Seminar) event).setSpeakerName(specificData);
            else if (event instanceof Concert) {
                try {
                    if(!specificData.equalsIgnoreCase("all ages")) Integer.parseInt(specificData);
                    ((Concert) event).setAgeRestriction(specificData);
                } catch (Exception e) { return "Error: Age must be a number or 'all ages'."; }
            }
        }

        return "Success: Event " + id + " updated.";
    }

    // --- UTILITIES ---

    public static Event getEvent(String eventId) {
        for (Event e : eventList) { if (e.getEventId().equalsIgnoreCase(eventId)) return e; }
        return null;
    }

    public boolean checkId(String id) {
        return eventList.stream().anyMatch(e -> e.getEventId().equalsIgnoreCase(id));
    }

    public boolean checkDate(String date) {
        try {
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime.parse(date, formatter);
            return false;
        } catch (DateTimeParseException e) {
            return true;
        }
    }

    public boolean checkCapacity(int cap) { return cap <= 0; }

    public List<Event> searchEventsGui(String query, String typeFilter, String statusFilter) {
        String q = query == null ? "" : query.trim().toLowerCase();
        String type = typeFilter == null ? "All" : typeFilter.trim();
        String status = statusFilter == null ? "All" : statusFilter.trim();

        return eventList.stream()
                .filter(e -> q.isEmpty() || (e.getTitle() != null && e.getTitle().toLowerCase().contains(q)))
                .filter(e -> type.equalsIgnoreCase("All")
                        || e.getClass().getSimpleName().equalsIgnoreCase(type))
                .filter(e -> status.equalsIgnoreCase("All")
                        || (e.getStatus() != null && e.getStatus().equalsIgnoreCase(status)))
                .collect(Collectors.toList());
    }

    public static ArrayList<Event> getEventList() {
        return eventList;
    }
}