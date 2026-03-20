package com.example._420_final.Control;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class WaitlistRow {
    private final StringProperty bookingId = new SimpleStringProperty();
    private final StringProperty userId = new SimpleStringProperty();
    private final StringProperty userName = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty createdAt = new SimpleStringProperty();

    public WaitlistRow(String bookingId, String userId, String userName, String email, String createdAt) {
        this.bookingId.set(bookingId);
        this.userId.set(userId);
        this.userName.set(userName);
        this.email.set(email);
        this.createdAt.set(createdAt);
    }

    public String getBookingId() { return bookingId.get(); }
    public StringProperty bookingIdProperty() { return bookingId; }

    public String getUserId() { return userId.get(); }
    public StringProperty userIdProperty() { return userId; }

    public StringProperty userNameProperty() { return userName; }
    public StringProperty emailProperty() { return email; }
    public StringProperty createdAtProperty() { return createdAt; }
}
