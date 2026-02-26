public class Workshop extends Event {
    private String topic;


    public Workshop(){
    }

    public Workshop(String eventId, String title, String dateTime, String location, int capacity, String status, String topic){
        super(eventId, title, dateTime, location, capacity, status);
        this.topic = topic;
    }

    @Override
    public void setSpecificData(String data) {
        this.topic = data;
    }


    public void print(){
        super.print();
        System.out.println("Topic: " + topic);
    }

    public void printOrder(){
        super.printOrder();
        System.out.println("7. Topic: " + topic);
    }

    public String getTopic(){
        return topic;
    }

    public void setTopic(String topic){
        this.topic = topic;
    }
}
