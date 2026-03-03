package com.example._420_final;

import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class BookingView extends VBox {

    public BookingView() {
        Label title = new Label("Booking Management Screen");
        getChildren().add(title);
    }
}