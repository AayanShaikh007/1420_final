package com.example._420_final.GUI;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class MainView extends BorderPane {

    private final VBox nav = new VBox(10);

    public MainView() {
        setPadding(new Insets(12));

        buildNav();
        setLeft(nav);

        // Default screen
        setCenter(new UserView());
    }

    private void buildNav() {
        nav.setPadding(new Insets(12));
        nav.setPrefWidth(220);
        nav.setStyle("-fx-background-color: -fx-control-inner-background; -fx-border-color: #d0d0d0;");

        Label header = new Label("Main Menu");
        header.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button usersBtn = new Button("User Management");
        usersBtn.setMaxWidth(Double.MAX_VALUE);
        usersBtn.setOnAction(e -> setCenter(new UserView()));

        Button eventsBtn = new Button("Event Management");
        eventsBtn.setMaxWidth(Double.MAX_VALUE);
        eventsBtn.setOnAction(e -> setCenter(new EventView()));

        Button bookingsBtn = new Button("Booking Management");
        bookingsBtn.setMaxWidth(Double.MAX_VALUE);
        bookingsBtn.setOnAction(e -> setCenter(new BookingView()));

        Button waitlistBtn = new Button("Waitlist Management");
        waitlistBtn.setMaxWidth(Double.MAX_VALUE);
        waitlistBtn.setOnAction(e -> setCenter(new WaitlistView()));

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        nav.getChildren().addAll(
                header,
                new Separator(),
                usersBtn,
                eventsBtn,
                bookingsBtn,
                waitlistBtn,
                spacer
        );
    }
}
