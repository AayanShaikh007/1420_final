package com.example._420_final;

import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class EventView extends VBox {

    public EventView() {
        Label title = new Label("Event Management Screen");
        getChildren().add(title);
    }
}