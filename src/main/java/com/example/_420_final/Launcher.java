package com.example._420_final;

import com.example._420_final.Management.FileManagement;
import javafx.application.Application;

public class Launcher {
    public static void main(String[] args) {

        FileManagement.loadFiles("/Files/users.csv", "/Files/events.csv", "/Files/bookings.csv");
        Runtime.getRuntime().addShutdownHook(new Thread(FileManagement::saveAll));

        Application.launch(HelloApplication.class, args);
    }
}
