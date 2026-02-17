public class Concert extends Event{
    private int ageRestriction;

    public Concert(){
    }

    public Concert(String eventId, String title, String dateTime, String location, int capacity, String status, int ageRestriction){
        super(eventId, title, dateTime, location, capacity, status);
        this.ageRestriction = ageRestriction;
    }
    public void print(){
        super.print();
        System.out.println("Age Restriction: " + ageRestriction);
    }
    public void printOrder(){
        super.printOrder();
        System.out.println("7. Age Restriction: " + ageRestriction);
    }

    @Override
    public void setSpecificData(String data) {
        this.ageRestriction = Integer.parseInt(data);
    }


    public int getAgeRestriction(){
        return ageRestriction;
    }

    public void setAgeRestriction(int ageRestriction){
        this.ageRestriction = ageRestriction;
    }
}
