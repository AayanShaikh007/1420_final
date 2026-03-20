package com.example._420_final.Control;

public class Concert extends Event{
    private String ageRestriction;

    public Concert(){
    }

    public Concert(String eventId, String title, String dateTime, String location, int capacity, String status, String ageRestriction){
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
        try {
            Integer.parseInt(data);
            this.ageRestriction = data+ "+";
        }catch (Exception e){
            this.ageRestriction = data;
        }

    }

    @Override
    public void printSpecificData() {
        System.out.print(ageRestriction);;
    }

    public String getAgeRestriction(){
        return ageRestriction;
    }

    public void setAgeRestriction(String ageRestriction){
        this.ageRestriction = ageRestriction;
    }
}
