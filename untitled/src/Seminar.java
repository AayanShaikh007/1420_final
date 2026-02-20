public class Seminar extends Event{
    private String speakerName;

    public Seminar(){
    }

    public Seminar(String eventId, String title, String dateTime, String location, int capacity, String status, String speakerName){
        super(eventId, title, dateTime, location, capacity, status);
        this.speakerName = speakerName;
    }
    public void print(){
        super.print();
        System.out.println("Name of Speaker: " + speakerName);
    }
    public void printOrder(){
        super.printOrder();
        System.out.println("7. Name of Speaker: " + speakerName);
    }

    @Override
    public void setSpecificData(String data) {
        this.speakerName = data;
    }

    public String getSpeakerName(){
        return speakerName;
    }

    public void setSpeakerName(String speakerName){
        this.speakerName = speakerName;
    }
}
