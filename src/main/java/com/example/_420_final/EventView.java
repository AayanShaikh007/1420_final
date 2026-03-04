package com.example._420_final;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class EventView extends VBox {
    private final EventManagement eventManagement = new EventManagement();

    public EventView() {
        setSpacing(10);
        setPadding(new Insets(20));

        Label titleLabel = new Label("Event Management");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Input Fields
        TextField idField = new TextField(); idField.setPromptText("Event ID (e.g., E001)");
        TextField titleField = new TextField(); titleField.setPromptText("Event Title");
        TextField dateField = new TextField(); dateField.setPromptText("Date (dd/MM/yyyy HH:mm)");
        TextField locField = new TextField(); locField.setPromptText("Location");
        TextField capField = new TextField(); capField.setPromptText("Capacity");

        // Type selection and the "Specific Data" field required by your subclasses
        ComboBox<String> typeBox = new ComboBox<>(FXCollections.observableArrayList("Workshop", "Seminar", "Concert"));
        typeBox.setPromptText("Select Event Type");

        TextField specField = new TextField();
        specField.setPromptText("Specific Detail (Topic / Speaker / Age)");

        Button addBtn = new Button("Create Event");
        Label statusLabel = new Label();
        statusLabel.setWrapText(true);

        // Action: When button is clicked, send data to Management class
        addBtn.setOnAction(e -> {
            if (typeBox.getValue() == null) {
                statusLabel.setText("Error: Please select an event type.");
                return;
            }

            String result = eventManagement.createEventGui(
                    typeBox.getValue(),
                    idField.getText(),
                    titleField.getText(),
                    dateField.getText(),
                    locField.getText(),
                    capField.getText(),
                    specField.getText()
            );
            statusLabel.setText(result);

            // Clear fields on success
            if (result.startsWith("Successfully")) {
                idField.clear(); titleField.clear(); specField.clear();
            }
        });

        getChildren().addAll(
                titleLabel,
                new Label("Event Details:"), idField, titleField, dateField, locField, capField,
                new Label("Type Specifics:"), typeBox, specField,
                addBtn, statusLabel
        );
    }
}