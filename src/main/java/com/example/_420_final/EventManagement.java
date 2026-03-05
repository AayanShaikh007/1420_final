package com.example._420_final;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EventManagement {
    private static ArrayList<Event> eventList = new ArrayList<>();

    public EventManagement() {}

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

    public void updateEvent(String eventId) {
        System.out.println("Update logic placeholder for: " + eventId);
    }

    // all the gui methods

    public String createEventGui(String type, String id, String title, String date, String loc, String cap, String specificData) {
        if (checkId(id)) return "Error: Event ID exists.";
        if (checkDate(date)) return "Error: Use dd/MM/yyyy HH:mm";

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
                    newEvent = new Concert(id, title, date, loc, capacity, "Active", Integer.parseInt(specificData));
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
        WaitListManagement.getWaitlistForEvent(eventId).clear();
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

    // utilities

    public Event getEvent(String eventId) {
        for (Event e : eventList) { if (e.getEventId().equalsIgnoreCase(eventId)) return e; }
        return null;
    }

    public boolean checkId(String id) {
        return eventList.stream().anyMatch(e -> e.getEventId().equalsIgnoreCase(id));
    }

    public boolean checkDate(String date) {
        return !date.matches("^\\d{2}/\\d{2}/\\d{4} ([01]\\d|2[0-3]):[0-5]\\d$");
    }

    public boolean checkCapacity(int cap) { return cap <= 0; }

    public static ArrayList<Event> getEventList() { return eventList; }
}