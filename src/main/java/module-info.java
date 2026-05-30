module com.amaal.ghads {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    // السماح لـ JavaFX بالوصول إلى الحزمة الرئيسية والواجهات
    opens com.amaal.ghads to javafx.fxml;
    opens com.amaal.ghads.controller to javafx.fxml;

    // السماح لـ JavaFX بقراءة كلاسات الـ Model لعرضها في الجداول (TableView) لاحقاً
    opens com.amaal.ghads.model to javafx.base;

    exports com.amaal.ghads;
}