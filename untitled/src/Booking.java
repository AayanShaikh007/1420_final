public class Booking {
    private String bookingId; // has to be unique
    private String userId;
    private String eventId;
    private String createdAt;
    private String bookingStatus; // Confirmed / Waitlisted / Cancelled


    public Booking(){
    }

    public Booking(String bookingId, String userId, String eventId, String createdAt, String bookingStatus){
        this.bookingId = bookingId;
        this.userId = userId;
        this.eventId = eventId;
        this.createdAt = createdAt;
        this.bookingStatus = bookingStatus;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getEventId() {
        return eventId;
    }

    public String getUserId() {
        return userId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}

