package com.example._420_final;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class BookingView extends VBox {

    private BookingManagement bookingManager = new BookingManagement();

    public BookingView() {

        setSpacing(10);
        setPadding(new Insets(20));

        // TITLE
        Label title = new Label("Booking Management");
        title.setStyle("-fx-font-size:18px; -fx-font-weight:bold;");

        // INPUTS
        TextField userIdField = new TextField();
        userIdField.setPromptText("User ID");

        TextField eventIdField = new TextField();
        eventIdField.setPromptText("Event ID");

        // BUTTONS
        Button bookBtn = new Button("Book Event");
        Button cancelBtn = new Button("Cancel Booking");

        Label resultLabel = new Label();

        // ======================
        // BOOK EVENT ACTION
        // ======================
        bookBtn.setOnAction(e -> {

            String userId = userIdField.getText();
            String eventId = eventIdField.getText();

            String result =
                    bookingManager.bookEventGui(userId, eventId);

            resultLabel.setText(result);
        });

        // ======================
        // CANCEL BOOKING ACTION
        // ======================
        cancelBtn.setOnAction(e -> {

            String userId = userIdField.getText();
            String eventId = eventIdField.getText();

            String result =
                    bookingManager.cancelBookingGui(userId, eventId);

            resultLabel.setText(result);
        });

        // ADD EVERYTHING
        getChildren().addAll(
                title,
                new Label("User ID"),
                userIdField,
                new Label("Event ID"),
                eventIdField,
                bookBtn,
                cancelBtn,
                new Separator(),
                resultLabel
        );
    }
}