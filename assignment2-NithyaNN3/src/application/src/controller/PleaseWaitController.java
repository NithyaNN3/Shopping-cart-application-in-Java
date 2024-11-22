package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class PleaseWaitController {

    @FXML
    private TextField lblTime;

    @FXML
	public void handleCollectionTime(String collectionTime) {
		// shows user the collection time
		System.out.println("Collection: " + collectionTime);
		lblTime.setText(collectionTime);
	}
    
}
