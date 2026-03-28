package com.example._420_final.GUI;

import com.example._420_final.Control.*;
import com.example._420_final.Management.EventManagement;
import com.example._420_final.Management.UserManagement;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class UserView extends VBox {

    private final UserManagement userManagement = new UserManagement();
    private final EventManagement eventManagement = new EventManagement();

    // Add User form elements
    private final ComboBox<String> typeCombo = new ComboBox<>(
            FXCollections.observableArrayList("Student", "Staff", "Guest")
    );
    private final TextField userIdField = new TextField();
    private final TextField nameField = new TextField();
    private final TextField emailField = new TextField();
    private final Label statusLabel = new Label();

    // List + details elements
    private final ListView<User> userListView = new ListView<>();
    private final Label detailsLabel = new Label("Select a user to view details.");
    private final ListView<String> bookingsListView = new ListView<>();

    public UserView() {
        setSpacing(12);
        setPadding(new Insets(12));

        Label title = new Label("User Management");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // UI Setup
        typeCombo.setValue("Student");
        userIdField.setPromptText("User ID");
        nameField.setPromptText("Name");
        emailField.setPromptText("Email");

        Button addBtn = new Button("Add User");
        addBtn.setOnAction(e -> addUser());

        Button refreshBtn = new Button("Refresh");
        refreshBtn.setOnAction(e -> refreshUsers());

        HBox buttons = new HBox(10, addBtn, refreshBtn);

        VBox form = new VBox(8,
                new Label("Add User"),
                new Label("Type"), typeCombo,
                new Label("User ID"), userIdField,
                new Label("Name"), nameField,
                new Label("Email"), emailField,
                buttons,
                statusLabel
        );
        form.setPadding(new Insets(10));
        form.setStyle("-fx-border-color: #d0d0d0; -fx-border-radius: 4; -fx-background-radius: 4;");

        // Custom Cell Factory to display User info nicely in the list
        userListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(User u, boolean empty) {
                super.updateItem(u, empty);
                if (empty || u == null) {
                    setText(null);
                } else {
                    setText(u.getUserId() + " - " + u.getName() + " (" + userTypeOf(u) + ")");
                }
            }
        });

        // listener for selecting a user
        userListView.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null) showUserDetails(newV);
        });

        VBox listPane = new VBox(8, new Label("All Users"), userListView);
        VBox.setVgrow(userListView, Priority.ALWAYS);

        detailsLabel.setWrapText(true);

        VBox detailsPane = new VBox(8,
                new Label("User Details"),
                detailsLabel,
                new Label("Current bookings:"),
                bookingsListView
        );
        VBox.setVgrow(bookingsListView, Priority.ALWAYS);

        HBox main = new HBox(12, form, listPane, detailsPane);
        HBox.setHgrow(listPane, Priority.ALWAYS);
        HBox.setHgrow(detailsPane, Priority.ALWAYS);

        getChildren().addAll(title, main);
        refreshUsers();
    }

    /**
     * Updated addUser calls the backend createUserGui method.
     * This keeps the validation logic in UserManagement.java where it belongs.
     */
    private void addUser() {
        String type = safe(typeCombo.getValue());
        String userId = safe(userIdField.getText());
        String name = safe(nameField.getText());
        String email = safe(emailField.getText());

        if (userId.isEmpty() || name.isEmpty() || email.isEmpty()) {
            statusLabel.setText("Fill in User ID, Name, and Email.");
            return;
        }

        // Delegate to UserManagement
        String result = userManagement.createUserGui(type, userId, name, email);
        statusLabel.setText(result);

        // If successful, clean the fields and update list
        if (result.startsWith("Success")) {
            userIdField.clear();
            nameField.clear();
            emailField.clear();
            refreshUsers();

            // Find the newly created user to highlight them
            User newUser = userManagement.getUser(userId);
            if (newUser != null) {
                userListView.getSelectionModel().select(newUser);
                userListView.scrollTo(newUser);
            }
        }
    }

    private void refreshUsers() {
        userListView.setItems(FXCollections.observableArrayList(UserManagement.getUserList()));
    }

    private void showUserDetails(User user) {
        Booking[] bookings = user.getUserBook();
        int capacity = (bookings == null) ? 0 : bookings.length;
        int used = 0;

        bookingsListView.getItems().clear();
        if (bookings != null) {
            for (Booking b : bookings) {
                if (b == null) continue;
                used++;

                String title = "(unknown event)";
                Event ev = eventManagement.getEvent(b.getEventId());
                if (ev != null && ev.getTitle() != null) title = ev.getTitle();

                bookingsListView.getItems().add(
                        b.getEventId() + " - " + title +
                                " | status=" + b.getBookingStatus() +
                                " | time=" + b.getCreatedAt()
                );
            }
        }

        detailsLabel.setText(
                "User ID: " + nvl(user.getUserId()) +
                        "\nName: " + nvl(user.getName()) +
                        "\nEmail: " + nvl(user.getEmail()) +
                        "\nType: " + userTypeOf(user) +
                        "\nBookings: " + used + " / " + capacity
        );
    }

    private static String userTypeOf(User user) {
        if (user instanceof Student) return "Student";
        if (user instanceof Staff) return "Staff";
        if (user instanceof Guest) return "Guest";
        return "User";
    }

    private static String safe(String s) {
        return s == null ? "" : s.trim();
    }

    private static String nvl(String s) {
        return (s == null || s.isBlank()) ? "(none)" : s;
    }
}