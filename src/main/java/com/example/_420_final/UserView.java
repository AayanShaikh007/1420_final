package com.example._420_final;

import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class UserView extends VBox {

    public UserView() {
        Label title = new Label("User Management Screen");
        getChildren().add(title);
    }
}