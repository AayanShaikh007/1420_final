public class Event {
    private String eventId; // needs to be unique work on this later
    private String title;
    private String dateTime;
    private String location;
    private int capacity;
    private String  status; // Active / Cancelled

    public Event(){
    }

    public Event(String eventId, String title, String dateTime, String location, int capacity, String status){
        this.eventId = eventId;
        this.title = title;
        this.dateTime = dateTime;
        this.location = location;
        this.capacity = capacity;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getEventId() {
        return eventId;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getLocation() {
        return location;
    }

    public String getTitle() {
        return title;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
