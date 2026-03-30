package com.example._420_final.GUI;

import com.example._420_final.Control.*;
import com.example._420_final.Management.EventManagement;
import com.example._420_final.Management.WaitListManagement;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class EventView extends VBox {
    private final EventManagement eventManager = new EventManagement();

    // UI Elements for Creation
    private final ComboBox<String> typeCombo = new ComboBox<>(FXCollections.observableArrayList("Workshop", "Seminar", "Concert"));
    private final TextField idField = new TextField();
    private final TextField titleField = new TextField();
    private final TextField dateField = new TextField();
    private final TextField locField = new TextField();
    private final TextField capField = new TextField();
    private final TextField specField = new TextField();
    private final Label statusLabel = new Label();

    // UI Elements for Search/Filter
    private final TextField searchField = new TextField();
    private final ChoiceBox<String> filterTypeChoice = new ChoiceBox<>(FXCollections.observableArrayList("All", "Workshop", "Seminar", "Concert"));
    private final ListView<String> eventListView = new ListView<>();

    public EventView() {
        setSpacing(10);
        setPadding(new Insets(15));

        Label head = new Label("Event Management");
        head.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // --- SECTION: CREATE/UPDATE EVENT ---
        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(8);

        typeCombo.setValue("Workshop");
        idField.setPromptText("Event ID");
        titleField.setPromptText("Title");
        dateField.setPromptText("yyyy-MM-ddTHH:mm");
        locField.setPromptText("Location");
        capField.setPromptText("Capacity");
        specField.setPromptText("Topic/Speaker/Age");

        grid.addRow(0, new Label("Type:"), typeCombo);
        grid.addRow(1, new Label("ID:"), idField);
        grid.addRow(2, new Label("Title:"), titleField);
        grid.addRow(3, new Label("Date:"), dateField);
        grid.addRow(4, new Label("Location:"), locField);
        grid.addRow(5, new Label("Capacity:"), capField);
        grid.addRow(6, new Label("Specific:"), specField);

        Button addBtn = new Button("Add Event");
        addBtn.setOnAction(e -> handleCreate());
        Button updateBtn = new Button("Update Event");
        updateBtn.setOnAction(e -> handleUpdate());

        HBox formBtns = new HBox(10, addBtn, updateBtn);

        // --- SECTION: SEARCH & FILTER ---
        searchField.setPromptText("Search by Title...");
        filterTypeChoice.setValue("All");

        Button searchBtn = new Button("Search/Filter");
        searchBtn.setOnAction(e -> handleSearchAndFilter());

        HBox filterBar = new HBox(10, searchField, filterTypeChoice, searchBtn);

        // --- SECTION: LIST & ROSTER ---
        Button cancelBtn = new Button("Cancel Selected Event");
        cancelBtn.setOnAction(e -> handleCancel());

        Button rosterBtn = new Button("View Waitlist Roster");
        rosterBtn.setOnAction(e -> handleViewRoster());

        HBox actionBtns = new HBox(10, cancelBtn, rosterBtn);

        getChildren().addAll(head, grid, formBtns, statusLabel, new Separator(),
                new Label("Search & Filter"), filterBar, eventListView, actionBtns);
        refreshList();
    }

    private void handleCreate() {
        String res = eventManager.createEventGui(typeCombo.getValue(), idField.getText(),
                titleField.getText(), dateField.getText(), locField.getText(), capField.getText(), specField.getText());
        statusLabel.setText(res);
        refreshList();
    }

    private void handleUpdate() {
        String res = eventManager.updateEventGui(idField.getText(),
                titleField.getText(), dateField.getText(), locField.getText(), capField.getText(), specField.getText());
        statusLabel.setText(res);
        refreshList();
    }

    private void handleSearchAndFilter() {
        String query = searchField.getText().toLowerCase();
        String type = filterTypeChoice.getValue();
        List<Event> results = eventManager.filterByTypeGui(type);

        if (!query.isEmpty()) {
            results = results.stream()
                    .filter(ev -> ev.getTitle().toLowerCase().contains(query))
                    .toList();
        }

        eventListView.getItems().clear();
        for (Event ev : results) {
            eventListView.getItems().add(ev.getEventId() + " | " + ev.getTitle() + " | " + ev.getStatus() + " | Cap: " + ev.getCapacity());
        }
    }

    private void handleCancel() {
        String selected = eventListView.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        String id = selected.split(" \\| ")[0];
        statusLabel.setText(eventManager.cancelEventGui(id));
        refreshList();
    }

    private void handleViewRoster() {
        String selected = eventListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            statusLabel.setText("Please select an event first.");
            return;
        }
        String id = selected.split(" \\| ")[0];

        Stage popup = new Stage();
        popup.setTitle("Waitlist Roster: " + id);

        VBox box = new VBox(10);
        box.setPadding(new Insets(15));

        Label listTitle = new Label("Waitlisted Users for " + id + ":");
        ListView<String> rosterList = new ListView<>();

        List<Booking> waitlist = WaitListManagement.getWaitlistForEvent(id);
        if (waitlist.isEmpty()) {
            rosterList.getItems().add("Waitlist is empty.");
        } else {
            for (Booking b : waitlist) {
                rosterList.getItems().add("Booking ID: " + b.getBookingId() + " | User: " + b.getUserId());
            }
        }

        Button close = new Button("Close");
        close.setOnAction(e -> popup.close());

        box.getChildren().addAll(listTitle, rosterList, close);
        popup.setScene(new Scene(box, 300, 400));
        popup.show();
    }

    private void refreshList() {
        eventListView.getItems().clear();
        for (Event ev : EventManagement.getEventList()) {
            eventListView.getItems().add(ev.getEventId() + " | " + ev.getTitle() + " | " + ev.getStatus() + " | Cap: " + ev.getCapacity());
        }
    }
}