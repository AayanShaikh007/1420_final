import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EventManagement {
    private static ArrayList<Event> eventList= new ArrayList<Event>();
    public String eventId;
    public String title;
    public String dateTime;
    public String location;
    public int capacity;
    public String  status; // Active / Cancelled
    public String type;
    public String typedata;
    public Scanner myObj = new Scanner(System.in);
    public boolean unique, unending;
    public int update;

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

    public void updateEvent(){
        unending= unique = true;
        System.out.print("Write the id of the event you want to update: ");
        eventId = myObj.nextLine();
        Event event = getEvent(eventId);
        while (unending){
        System.out.println("Event Data: \n");
        event.printOrder();
        System.out.println("8. Exit");

        while (unique){
            System.out.print("Which data you want to update (write the number): ");
            update = myObj.nextInt();
            unique = checkupdate(update);
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
                event.setEventId(myObj.nextLine());
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
                event.setEventId(myObj.nextLine());
                break;
            case 5:
                while (unique){
                    System.out.println("Event capacity (needs to be more than 1): ");
                    capacity = myObj.nextInt();
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
                event.setStatus(status);
                break;
            case 7:
                while (unique){
                    System.out.print("Workshop\nSeminar\nConcert\nType of event: ");
                    type = myObj.nextLine();
                    unique = checkType(type);
                }

                event.setSpecificData(type);
                break;
            case 8:
                unending =false;
                break;
        }}



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
            return true;
        }else {
            System.out.println("Capacity needs to be at least 1.");
            return false;
        }
    }

    public boolean checkupdate(int update) {
        return (update > 0 && update <=7);
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

    /*Create an event: Add a new event with required fields (including event type and its
type-specific attribute). Validate inputs such as capacity (> 0) and prevent duplicate
eventIds.
● Update event information: Modify existing event details (e.g., title, dateTime, location,
capacity, and type-specific fields). The system must ensure updates keep the event in
a valid state.
● Cancel an event: Mark an event as Cancelled so it is no longer bookable.
○ all Confirmed and Waitlisted bookings for that event are automatically set to
Cancelled, and
○ the waitlist becomes empty.
● List events: Display all events with their key details (including capacity and status) so
the admin can easily browse what is available.
● Search and filter events: Provide basic search capabilities, including search by title
(partial and/or case-insensitive match) and filter by event type
(Workshop/Seminar/Concert) to narrow results.
2
*/
}
