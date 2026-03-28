package com.example._420_final;

import java.io.*;
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

        Event e;
        try {
            int capacity = Integer.parseInt(cap);
            switch (type.toLowerCase()) {
                case "workshop": e = new Workshop(id, title, date, loc, capacity, "Active", specificData); break;
                case "seminar": e = new Seminar(id, title, date, loc, capacity, "Active", specificData); break;
                case "concert": e = new Concert(id, title, date, loc, capacity, "Active", Integer.parseInt(specificData)); break;
                default: return "Invalid Type";
            }
            eventList.add(e);
            saveEvents();
            return "Event created successfully.";
        } catch (Exception ex) {
            return "Error: " + ex.getMessage();
        }
    }

    public String cancelEventGui(String eventId) {
        Event event = getEvent(eventId);
        if (event == null) return "Error: Not found.";
        event.setStatus("Cancelled");
        for (User u : UserManagement.getUserList()) { u.cancelledBooked(eventId); }
        WaitListManagement.getWaitlistForEvent(eventId).clear();

        saveEvents();
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
        return !date.matches("^\\d{2}/\\d{2}/\\d{4} ([01]\\d|2[0-3]):([0-5]\\d)$");
    }

    public static ArrayList<Event> getEventList() {
        return eventList;
    }

    // new functions for persistence

    public static void saveEvents() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("events.txt"))) {
            for (Event e : eventList) {
                String type = e.getClass().getSimpleName();
                writer.print(type + "," + e.getEventId() + "," + e.getTitle() + "," +
                        e.getDateTime() + "," + e.getLocation() + "," +
                        e.getCapacity() + "," + e.getStatus() + ",");

                if (e instanceof Workshop) writer.println(((Workshop) e).getTopic());
                else if (e instanceof Seminar) writer.println(((Seminar) e).getSpeakerName());
                else if (e instanceof Concert) writer.println(((Concert) e).getAgeRestriction());
            }
        } catch (IOException e) {
            System.err.println("File Error: " + e.getMessage());
        }
    }

    public static void loadEvents() {
        File file = new File("events.txt");
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            eventList.clear();
            while ((line = reader.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length < 8) continue;

                String type = p[0], id = p[1], title = p[2], date = p[3], loc = p[4];
                int cap = Integer.parseInt(p[5]);
                String status = p[6], spec = p[7];

                Event ev = null;
                if (type.equals("Workshop")) ev = new Workshop(id, title, date, loc, cap, status, spec);
                else if (type.equals("Seminar")) ev = new Seminar(id, title, date, loc, cap, status, spec);
                else if (type.equals("Concert")) ev = new Concert(id, title, date, loc, cap, status, Integer.parseInt(spec));

                if (ev != null) eventList.add(ev);
            }
        } catch (Exception e) {
            System.err.println("Load Error: " + e.getMessage());
        }
    }
}