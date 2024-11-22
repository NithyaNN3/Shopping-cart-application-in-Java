module assignment2 {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.base;
	requires junit;
	requires org.junit.jupiter.api;
	
	opens controller to javafx.graphics, javafx.fxml;
	opens application to javafx.graphics, javafx.fxml;
}
