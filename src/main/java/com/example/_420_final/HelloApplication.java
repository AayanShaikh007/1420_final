package com.example._420_final;

import com.example._420_final.GUI.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        MainView root = new MainView();

        Scene scene = new Scene(root, 1000, 650);
        stage.setTitle("ENGG*1420 Event Booking System");
        stage.setScene(scene);
        stage.show();
    }
}
