package rzelonek.libsys.controlers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import rzelonek.libsys.App;
import rzelonek.libsys.auth.Auth;

public class SecondaryController {

    @FXML
    private Label username;

    @FXML
    private HBox managementTools; // Add this line

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void openbooklist() throws IOException {
        App.setRoot("booklist");
    }

    @FXML
    private void userlist() throws IOException {
        App.setRoot("userlist");
    }

    @FXML
    private void initialize() {
        Auth auth = new Auth();
        username.setText(auth.loggedUser.getLogin());

        // Check if the current user's usergroup is "admin"
        if (!auth.loggedUser.getRole().equalsIgnoreCase("ADMIN")) {
            managementTools.setVisible(false); // Hide the management tools
        }
    }

    @FXML
    private void openrentlist() throws IOException {
        App.setRoot("userprofile");
    }

    @FXML
    private void openavbbookks() throws IOException {
        App.setRoot("avalivlebooks");
    }

}
