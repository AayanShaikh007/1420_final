package com.example._420_final;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static UserManagement userManager = new UserManagement();
    public static EventManagement eventManager = new EventManagement();

    @Override
    public void start(Stage stage) {

        TabPane tabs = new TabPane();
//different tabs in the window
        Tab usersTab = new Tab("Users", new UserView());
        Tab eventsTab = new Tab("Events", new EventView());
        Tab bookingTab = new Tab("Bookings", new BookingView());
        Tab waitlistTab = new Tab("Waitlist", new WaitlistView());

        usersTab.setClosable(false);
        eventsTab.setClosable(false);
        bookingTab.setClosable(false);
        waitlistTab.setClosable(false);

        tabs.getTabs().addAll(
                usersTab,
                eventsTab,
                bookingTab,
                waitlistTab
        );
        //size of window
        Scene scene = new Scene(tabs, 900, 600);
//title of the window
        stage.setTitle("Phase 1 Event Booking System");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}