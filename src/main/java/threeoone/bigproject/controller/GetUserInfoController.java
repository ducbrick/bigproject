package threeoone.bigproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class GetUserInfoController {
    @FXML
    private Label testLabel;
    @FXML
    private TextField txtfield;
    @FXML
    private Button button;
    String name;

    public void getName(ActionEvent event) {
        name = txtfield.getText();
        System.out.println(name);
    }
}
