package com.example._420_final.GUI;

import com.example._420_final.Control.Booking;
import com.example._420_final.Control.Event;
import com.example._420_final.Control.User;
import com.example._420_final.Control.WaitlistRow;
import com.example._420_final.Management.EventManagement;
import com.example._420_final.Management.UserManagement;
import com.example._420_final.Management.WaitListManagement;
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

public class WaitlistView extends VBox {

    private final ComboBox<String> eventIdCombo = new ComboBox<>();
    private final TableView<WaitlistRow> table = new TableView<>();
    private final Label notificationLabel = new Label();

    public WaitlistView() {
        setSpacing(10);
        setPadding(new Insets(12));

        Label title = new Label("Waitlist Management");

        buildTopBar();
        buildTable();

        notificationLabel.setWrapText(true);

        getChildren().addAll(
                title,
                buildControlsRow(),
                table,
                new Separator(),
                new Label("Notifications:"),
                notificationLabel
        );

        refreshEventIds();
        refreshTable();
        consumePromotionNotificationIfAny();
    }

    private void buildTopBar() {
        eventIdCombo.setPromptText("Select eventId");
        eventIdCombo.setOnAction(e -> {
            refreshTable();
            consumePromotionNotificationIfAny();
        });
    }

    private HBox buildControlsRow() {
        Button refreshBtn = new Button("Refresh");
        refreshBtn.setOnAction(e -> {
            refreshEventIds();
            refreshTable();
            consumePromotionNotificationIfAny();
        });

        Button removeSelectedBtn = new Button("Remove selected waitlisted booking");
        removeSelectedBtn.setOnAction(e -> removeSelected());

        HBox row = new HBox(10, new Label("Event:"), eventIdCombo, refreshBtn, removeSelectedBtn);
        HBox.setHgrow(eventIdCombo, Priority.ALWAYS);
        eventIdCombo.setMaxWidth(Double.MAX_VALUE);
        return row;
    }

    private void buildTable() {
        TableColumn<WaitlistRow, String> bookingIdCol = new TableColumn<>("Booking ID");
        bookingIdCol.setCellValueFactory(c -> c.getValue().bookingIdProperty());

        TableColumn<WaitlistRow, String> userIdCol = new TableColumn<>("User ID");
        userIdCol.setCellValueFactory(c -> c.getValue().userIdProperty());

        TableColumn<WaitlistRow, String> userNameCol = new TableColumn<>("Name");
        userNameCol.setCellValueFactory(c -> c.getValue().userNameProperty());

        TableColumn<WaitlistRow, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(c -> c.getValue().emailProperty());

        TableColumn<WaitlistRow, String> createdAtCol = new TableColumn<>("Timestamp");
        createdAtCol.setCellValueFactory(c -> c.getValue().createdAtProperty());

        table.getColumns().addAll(bookingIdCol, userIdCol, userNameCol, emailCol, createdAtCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    }

    private void refreshEventIds() {
        List<String> ids = EventManagement.getEventList()
                .stream()
                .map(Event::getEventId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        String current = eventIdCombo.getValue();
        eventIdCombo.setItems(FXCollections.observableArrayList(ids));

        if (current != null && ids.contains(current)) {
            eventIdCombo.setValue(current);
        } else if (!ids.isEmpty()) {
            eventIdCombo.setValue(ids.get(0));
        } else {
            eventIdCombo.setValue(null);
        }
    }

    private void refreshTable() {
        String eventId = eventIdCombo.getValue();
        if (eventId == null || eventId.isBlank()) {
            table.setItems(FXCollections.observableArrayList());
            return;
        }

        List<Booking> waitlisted = WaitListManagement.getWaitlistForEvent(eventId);

        ObservableList<WaitlistRow> rows = FXCollections.observableArrayList();
        UserManagement um = new UserManagement();

        for (Booking b : waitlisted) { //for each returned booking, it looks up the user(to show name/ email) and builds a waitlistrow for the table
            User u = um.getUser(b.getUserId());
            String name = (u == null) ? "(unknown)" : u.getName();
            String email = (u == null) ? "(unknown)" : u.getEmail();

            rows.add(new WaitlistRow(
                    b.getBookingId(),
                    b.getUserId(),
                    name,
                    email,
                    b.getCreatedAt()
            ));
        }

        table.setItems(rows);
    }

    private void removeSelected() {
        String eventId = eventIdCombo.getValue();
        WaitlistRow selected = table.getSelectionModel().getSelectedItem();

        if (eventId == null || eventId.isBlank()) {
            notificationLabel.setText("Select an event first.");
            return;
        }
        if (selected == null) {
            notificationLabel.setText("Select a waitlisted booking row to remove.");
            return;
        }

        boolean removed = WaitListManagement.removeWaitlistedBooking(eventId, selected.getBookingId()); //to remove an entry it calls
        if (removed) {
            notificationLabel.setText("Removed waitlisted bookingId=" + selected.getBookingId() + " for eventId=" + eventId);
        } else {
            notificationLabel.setText("Could not remove booking (maybe it was already removed).");
        }

        refreshTable(); //then refreshes table
        consumePromotionNotificationIfAny();
    }

    private void consumePromotionNotificationIfAny() { //calls
        String msg = WaitListManagement.consumeLastPromotionNotification();
        if (msg != null && !msg.isBlank()) { //if there is a promotion message, it displays it
            notificationLabel.setText(msg);
        }
    }
}