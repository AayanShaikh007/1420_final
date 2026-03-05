package com.example._420_final;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

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
        dateField.setPromptText("dd/MM/yyyy HH:mm");
        locField.setPromptText("Location");
        capField.setPromptText("Capacity");
        specField.setPromptText("Topic / Speaker / Age");

        grid.addRow(0, new Label("Type:"), typeCombo, new Label("ID:"), idField);
        grid.addRow(1, new Label("Title:"), titleField, new Label("Date:"), dateField);
        grid.addRow(2, new Label("Location:"), locField, new Label("Capacity:"), capField);
        grid.addRow(3, new Label("Specific Data:"), specField);

        Button addBtn = new Button("Create Event");
        addBtn.setOnAction(e -> handleCreate());

        // --- SECTION: SEARCH & LIST ---
        searchField.setPromptText("Search by title...");
        searchField.setOnKeyReleased(e -> handleSearch());

        Button cancelBtn = new Button("Cancel Selected Event");
        cancelBtn.setStyle("-fx-background-color: #ff9999;");
        cancelBtn.setOnAction(e -> handleCancel());

        refreshList();

        getChildren().addAll(head, grid, addBtn, statusLabel, new Separator(),
                new Label("Search & Manage Events:"), searchField, eventListView, cancelBtn);
    }

    private void handleCreate() {
        String res = eventManager.createEventGui(typeCombo.getValue(), idField.getText(),
                titleField.getText(), dateField.getText(), locField.getText(), capField.getText(), specField.getText());
        statusLabel.setText(res);
        refreshList();
    }

    private void handleSearch() {
        var results = eventManager.searchByTitleGui(searchField.getText());
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

    private void refreshList() {
        eventListView.getItems().clear();
        for (Event ev : EventManagement.getEventList()) {
            eventListView.getItems().add(ev.getEventId() + " | " + ev.getTitle() + " | " + ev.getStatus() + " | Cap: " + ev.getCapacity());
        }
    }
}