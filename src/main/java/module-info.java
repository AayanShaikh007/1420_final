module com.example._420_final {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example._420_final to javafx.fxml;
    exports com.example._420_final;
    exports com.example._420_final.GUI;
    opens com.example._420_final.GUI to javafx.fxml;
    exports com.example._420_final.Control;
    opens com.example._420_final.Control to javafx.fxml;
    exports com.example._420_final.Management;
    opens com.example._420_final.Management to javafx.fxml;
}