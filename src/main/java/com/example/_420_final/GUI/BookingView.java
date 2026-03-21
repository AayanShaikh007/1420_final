package com.example._420_final.GUI;

import com.example._420_final.Control.Booking;
import com.example._420_final.Control.Event;
import com.example._420_final.Control.User;
import com.example._420_final.Management.BookingManagement;
import com.example._420_final.Management.EventManagement;
import com.example._420_final.Management.UserManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BookingView extends VBox {

    private final BookingManagement bookingManager = new BookingManagement();
    private final ComboBox<String> userCombo = new ComboBox<>();
    private final ComboBox<String> eventCombo = new ComboBox<>();
    private final TableView<Booking> bookingTable = new TableView<>();
    private final Label resultLabel = new Label();

    public BookingView() {
        setSpacing(10);
        setPadding(new Insets(20));

        Label title = new Label("Booking Management");
        title.setStyle("-fx-font-size:18px; -fx-font-weight:bold;");

        buildSelectors();
        buildBookingTable();

        Button bookBtn = new Button("Book Event");
        Button cancelBtn = new Button("Cancel Selected Booking");
        Button updateBtn = new Button("Update Selected Booking");
        Button refreshBtn = new Button("Refresh");

        bookBtn.setOnAction(e -> {
            String userId = userCombo.getValue();
            String eventId = eventCombo.getValue();

            if (userId == null || userId.isBlank()) {
                resultLabel.setText("Please select a user.");
                return;
            }
            if (eventId == null || eventId.isBlank()) {
                resultLabel.setText("Please select an event.");
                return;
            }

            String result = bookingManager.bookEventGui(userId, eventId);
            resultLabel.setText(result);
            refreshAll();
        });

        cancelBtn.setOnAction(e -> {
            Booking selected = bookingTable.getSelectionModel().getSelectedItem();
            if (selected == null) {
                resultLabel.setText("Please select a booking from the list to cancel.");
                return;
            }

            String result = bookingManager.cancelBookingGui(selected.getUserId(), selected.getEventId());
            resultLabel.setText(result);
            refreshAll();
        });

        updateBtn.setOnAction(e -> {
            Booking selected = bookingTable.getSelectionModel().getSelectedItem();
            String newEventId = eventCombo.getValue();

            if (selected == null) {
                resultLabel.setText("Please select a booking from the table to update.");
                return;
            }
            if (newEventId == null || newEventId.isBlank()) {
                resultLabel.setText("Please select a new event from the dropdown.");
                return;
            }

            String result = bookingManager.updateBookingGui(selected.getBookingId(), newEventId);
            resultLabel.setText(result);
            refreshAll();
        });

        refreshBtn.setOnAction(e -> refreshAll());

        HBox selectors = new HBox(10,
                new VBox(5, new Label("User"), userCombo),
                new VBox(5, new Label("Event"), eventCombo)
        );
        HBox.setHgrow(userCombo, Priority.ALWAYS);
        HBox.setHgrow(eventCombo, Priority.ALWAYS);
        userCombo.setMaxWidth(Double.MAX_VALUE);
        eventCombo.setMaxWidth(Double.MAX_VALUE);

        HBox buttons = new HBox(10, bookBtn, updateBtn, cancelBtn, refreshBtn);

        getChildren().addAll(
                title,
                selectors,
                buttons,
                new Separator(),
                new Label("Current Bookings:"),
                bookingTable,
                new Separator(),
                resultLabel
        );

        refreshAll();
    }

    private void buildSelectors() {
        userCombo.setPromptText("Select user");
        eventCombo.setPromptText("Select event");
    }

    private void buildBookingTable() {
        TableColumn<Booking, String> bookingIdCol = new TableColumn<>("Booking ID");
        bookingIdCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getBookingId()));

        TableColumn<Booking, String> userIdCol = new TableColumn<>("User ID");
        userIdCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getUserId()));

        TableColumn<Booking, String> eventIdCol = new TableColumn<>("Event ID");
        eventIdCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getEventId()));

        TableColumn<Booking, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getBookingStatus()));

        TableColumn<Booking, String> createdAtCol = new TableColumn<>("Created At");
        createdAtCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getCreatedAt()));

        bookingTable.getColumns().addAll(bookingIdCol, userIdCol, eventIdCol, statusCol, createdAtCol);
        bookingTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    }

    private void refreshAll() {
        refreshUsers();
        refreshEvents();
        refreshBookings();
    }

    private void refreshUsers() {
        List<String> ids = UserManagement.getUserList()
                .stream()
                .map(User::getUserId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        String current = userCombo.getValue();
        userCombo.setItems(FXCollections.observableArrayList(ids));

        if (current != null && ids.contains(current)) {
            userCombo.setValue(current);
        } else if (!ids.isEmpty()) {
            userCombo.setValue(ids.getFirst());
        } else {
            userCombo.setValue(null);
        }
    }

    private void refreshEvents() {
        List<String> ids = EventManagement.getEventList()
                .stream()
                .map(Event::getEventId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        String current = eventCombo.getValue();
        eventCombo.setItems(FXCollections.observableArrayList(ids));

        if (current != null && ids.contains(current)) {
            eventCombo.setValue(current);
        } else if (!ids.isEmpty()) {
            eventCombo.setValue(ids.getFirst());
        } else {
            eventCombo.setValue(null);
        }
    }

    private void refreshBookings() {
        ObservableList<Booking> rows = FXCollections.observableArrayList(BookingManagement.getBookingList());
        bookingTable.setItems(rows);
    }
}