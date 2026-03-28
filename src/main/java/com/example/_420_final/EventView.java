package com.example._420_final;

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

    // UI Elements for Search/List
    private final TextField searchField = new TextField();
    private final ListView<String> eventListView = new ListView<>();

    private final ComboBox<String> typeFilterCombo = new ComboBox<>(
            FXCollections.observableArrayList("All", "Workshop", "Seminar", "Concert")
    );
    private final Button rosterBtn = new Button("View Event Roster");

    public EventView() {
        setSpacing(10);
        setPadding(new Insets(15));

        Label head = new Label("Event Management");
        head.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // --- SECTION: CREATE EVENT ---
        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(8);

        typeCombo.setValue("Workshop");
        idField.setPromptText("Event ID");
        titleField.setPromptText("Title");
        dateField.setPromptText("DD/MM/YYYY HH:mm");
        locField.setPromptText("Location");
        capField.setPromptText("Capacity");
        specField.setPromptText("Specific Data (Topic/Speaker/Age)");

        grid.add(new Label("Type:"), 0, 0); grid.add(typeCombo, 1, 0);
        grid.add(new Label("ID:"), 0, 1); grid.add(idField, 1, 1);
        grid.add(new Label("Title:"), 0, 2); grid.add(titleField, 1, 2);
        grid.add(new Label("Date:"), 0, 3); grid.add(dateField, 1, 3);
        grid.add(new Label("Location:"), 0, 4); grid.add(locField, 1, 4);
        grid.add(new Label("Capacity:"), 0, 5); grid.add(capField, 1, 5);
        grid.add(new Label("Specific:"), 0, 6); grid.add(specField, 1, 6);

        Button addBtn = new Button("Create Event");
        addBtn.setOnAction(e -> handleCreate());

        // --- SECTION: SEARCH/LIST ---
        searchField.setPromptText("Search by title...");
        searchField.setOnKeyReleased(e -> handleSearch());

        typeFilterCombo.setValue("All");
        typeFilterCombo.setOnAction(e -> handleSearch());
        HBox filterBar = new HBox(10, new Label("Search:"), searchField, new Label("Type:"), typeFilterCombo);

        Button cancelBtn = new Button("Cancel Event");
        cancelBtn.setOnAction(e -> handleCancel());

        // --- ROSTER SETUP ---
        rosterBtn.setOnAction(e -> handleViewRoster());
        HBox actionBox = new HBox(10, cancelBtn, rosterBtn);

        refreshList();

        getChildren().addAll(head, grid, addBtn, statusLabel, new Separator(),
                new Label("Search & Manage Events:"), filterBar, eventListView, actionBox);
    }

    private void handleCreate() {
        String res = eventManager.createEventGui(typeCombo.getValue(), idField.getText(),
                titleField.getText(), dateField.getText(), locField.getText(), capField.getText(), specField.getText());
        statusLabel.setText(res);
        refreshList();
    }

    private void handleSearch() {
        String query = searchField.getText().toLowerCase();
        String selectedType = typeFilterCombo.getValue();

        eventListView.getItems().clear();
        for (Event ev : EventManagement.getEventList()) {
            boolean matchesSearch = ev.getTitle().toLowerCase().contains(query);
            boolean matchesType = selectedType.equals("All") ||
                    ev.getClass().getSimpleName().equalsIgnoreCase(selectedType);

            if (matchesSearch && matchesType) {
                eventListView.getItems().add(ev.getEventId() + " | " + ev.getTitle() + " | " + ev.getStatus() + " | Cap: " + ev.getCapacity());
            }
        }
    }

    private void handleCancel() {
        String selected = eventListView.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        String id = selected.split(" \\| ")[0];
        statusLabel.setText(eventManager.cancelEventGui(id));
        refreshList();
    }

    // --- ROSTER CHECKING ---
    private void handleViewRoster() {
        String selected = eventListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            statusLabel.setText("Select an event first.");
            return;
        }
        String eventId = selected.split(" \\| ")[0];

        Stage rosterStage = new Stage();
        rosterStage.setTitle("Roster for " + eventId);

        ListView<String> rosterDisplay = new ListView<>();
        rosterDisplay.getItems().add("=== WAITLIST ===");

        List<Booking> waitlist = WaitListManagement.getWaitlistForEvent(eventId);
        if (waitlist.isEmpty()) {
            rosterDisplay.getItems().add("No one on waitlist.");
        } else {
            for (Booking b : waitlist) {
                rosterDisplay.getItems().add("Booking ID: " + b.getBookingId() + " | User: " + b.getUserId());
            }
        }

        VBox root = new VBox(10, new Label("Event Roster: " + eventId), rosterDisplay);
        root.setPadding(new Insets(15));
        rosterStage.setScene(new Scene(root, 300, 400));
        rosterStage.show();
    }

    private void refreshList() {
        handleSearch();
    }
}