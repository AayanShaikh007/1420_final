package com.example._420_final;

import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class WaitlistView extends VBox {

    public WaitlistView() {
        Label title = new Label("Waitlist Screen");
        getChildren().add(title);
    }
}