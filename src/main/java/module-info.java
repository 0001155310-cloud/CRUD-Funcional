module com.senai.crudjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    requires mysql.connector.j;

    opens com.senai.crudjavafx to javafx.fxml;
    opens com.senai.crudjavafx.controller to javafx.fxml;
    opens com.senai.crudjavafx.model to javafx.base;

    exports com.senai.crudjavafx;
    exports com.senai.crudjavafx.controller;
}