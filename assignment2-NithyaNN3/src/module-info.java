module assignment2 {
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.controls;
	
	opens application to javafx.fxml;
    exports application;
}