package com.example._420_final.Management;
import com.example._420_final.Control.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class FileManagement {
    public static void loadFiles(String uFilePath,String eFilePath,String bFilePath ){
        loadUserFromCSV(uFilePath);
        loadEventsFromCSV(eFilePath);
        loadBookingFromCSV(bFilePath);
    }

    //reads the CSV for events and save it for use in the program
    public static void loadEventsFromCSV(String filePath) {

        try (InputStream is = FileManagement.class.getResourceAsStream(filePath);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            br.readLine(); // skip header

            String line;
            while ((line = br.readLine()) != null) {
                String cleaned = String.join(",",
                        Arrays.stream(line.split(",")).filter(s -> !s.isBlank()).toArray(String[]::new)
                );
                String[] d = cleaned.split(",");

                String id = d[0];
                String title = d[1];
                String date = d[2];
                String location = d[3];
                int capacity = Integer.parseInt(d[4]);
                String status = d[5];
                String type = d[6];
                String specific = d[7];
                Event event;

                switch (type.toLowerCase()) {
                    case "workshop":
                        event = new Workshop(id,title,date,location,capacity,status,specific);
                        break;

                    case "seminar":
                        event = new Seminar(id,title,date,location,capacity,status,specific);
                        break;

                    case "concert":
                        event = new Concert(id,title,date,location,capacity,status,specific);
                        break;

                    default:
                        continue;
                }

                EventManagement.getEventList().add(event);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //reads the CSV for users and save it for use in the program
    public static void loadUserFromCSV(String filePath) {

        try (InputStream is = FileManagement.class.getResourceAsStream(filePath);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            br.readLine(); // skip header

            String line;
            while ((line = br.readLine()) != null) {

                String[] d = line.split(",");

                String id = d[0];
                String name = d[1];
                String email = d[2];
                String userType = d[3];

                User user;

                switch (userType.toLowerCase()) {
                    case "student":
                        user = new Student(id,name,email);
                        break;

                    case "staff":
                        user = new Staff(id,name,email);
                        break;

                    case "guest":
                        user = new Guest(id,name,email);
                        break;

                    default:
                        continue;
                }

                UserManagement.getUserList().add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //reads the CSV for booking and save it for use in the program
    public static void loadBookingFromCSV(String filePath) {

        try (InputStream is = FileManagement.class.getResourceAsStream(filePath);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            br.readLine(); // skip header

            String line;
            while ((line = br.readLine()) != null) {

                String[] d = line.split(",");
                //bookingId,userId,eventId,createdAt,bookingStatus
                String bookingId = d[0];
                String userId = d[1];
                String eventId = d[2];
                String createdAt = d[3];
                String bookingStatus = d[4];
                Booking booking = new Booking(bookingId,userId,eventId,createdAt,bookingStatus);
                switch (bookingStatus.toLowerCase()) {
                    case "confirmed":
                        User user = UserManagement.getUser(userId);
                        for (int i = 0; i < user.getUserBook().length; i++) {
                            if (user.getUserBook()[i] == null) {
                                user.getUserBook()[i] = booking;
                                break;
                            }
                        }
                        Event event = EventManagement.getEvent(eventId);
                        assert event != null;
                        event.setCapacity(event.getCapacity() - 1);
                        break;

                    case "waitlisted":
                        WaitListManagement.addToWaitlist(booking);
                        break;

                    default:
                        continue;
                }

                BookingManagement.getBookingList().add(booking);



            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveAll(){
        saveUsersToCSV();
        saveEventsToCSV();
        saveBookingsToCSV();
    }
    public static void saveEventsToCSV(){
        try(FileWriter writer = new FileWriter("src/main/resources/Files/events.csv");) {
            writer.write("eventId,title,dateTime,location,capacity,status,eventType,topic,speakerName,ageRestriction\n");   // header
            String line = "";
            for (Event e: EventManagement.getEventList()){
                if(e instanceof Concert){
                    line = String.join(",",
                            e.getEventId(),
                            e.getTitle(),
                            e.getDateTime().toString(),
                            e.getLocation(),
                            String.valueOf(e.getCapacity()),
                            e.getStatus(),
                            "Concert",
                            "",   // empty field
                            "",   // empty field
                            ((Concert) e).getAgeRestriction()

                    );
                }
                if(e instanceof Seminar){
                    line = String.join(",",
                            e.getEventId(),
                            e.getTitle(),
                            e.getDateTime().toString(),
                            e.getLocation(),
                            String.valueOf(e.getCapacity()),
                            e.getStatus(),
                            "Seminar",
                            "",
                            ((Seminar) e).getSpeakerName(),
                            ""

                    );
                }
                if(e instanceof Workshop){
                    line = String.join(",",
                            e.getEventId(),
                            e.getTitle(),
                            e.getDateTime().toString(),
                            e.getLocation(),
                            String.valueOf(e.getCapacity()),
                            e.getStatus(),
                            "Workshop",
                            ((Workshop) e).getTopic(),
                            "",   // empty field
                            ""   // empty field

                    );
                }
                writer.write(line);
                writer.write("\n");
            }

            System.out.println("Events written successfully");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveUsersToCSV(){
        try(FileWriter writer = new FileWriter("src/main/resources/Files/users.csv");) {
            writer.write("userId,name,email,userType\n");   // header
            String line = "";
            for (User u: UserManagement.getUserList()){

                if(u instanceof Staff){
                    line = String.join(",",
                            u.getUserId(),
                            u.getName(),
                            u.getEmail(),
                            "Staff"
                    );
                }
                if(u instanceof Student){
                    line = String.join(",",
                            u.getUserId(),
                            u.getName(),
                            u.getEmail(),
                            "Student"
                    );
                }
                if(u instanceof Guest){
                    line = String.join(",",
                            u.getUserId(),
                            u.getName(),
                            u.getEmail(),
                            "Guest"
                    );
                }
                writer.write(line);
                writer.write("\n");
            }

            System.out.println("Users written successfully");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveBookingsToCSV(){
        try(FileWriter writer = new FileWriter("src/main/resources/Files/bookings.csv");) {
            writer.write("bookingId,userId,eventId,createdAt,bookingStatus\n");   // header
            String line = "";
            for (Booking b: BookingManagement.getBookingList()){
                line = String.join(",",
                        b.getBookingId(),
                        b.getUserId(),
                        b.getEventId(),
                        b.getCreatedAt(),
                        b.getBookingStatus()
                );
                writer.write(line);
                writer.write("\n");
            }
            System.out.println("Bookings written successfully");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
