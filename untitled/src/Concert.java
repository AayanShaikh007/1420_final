public class Concert extends Event{
    private int ageRestriction;

    public Concert(){
    }

    public Concert(String eventId, String title, String dateTime, String location, int capacity, String status, int ageRestriction){
        super(eventId, title, dateTime, location, capacity, status);
        this.ageRestriction = ageRestriction;
    }

    public int getAgeRestriction(){
        return ageRestriction;
    }

    public void setAgeRestriction(int ageRestriction){
        this.ageRestriction = ageRestriction;
    }
}
