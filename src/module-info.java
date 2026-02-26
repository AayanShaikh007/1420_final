module com.example._420final {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example._420final to javafx.fxml;
    exports com.example._420final;
}