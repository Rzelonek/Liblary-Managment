package rzelonek.libsys.controlers;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import rzelonek.libsys.App;
import javafx.scene.canvas.*;

import rzelonek.libsys.db.UserData;
import rzelonek.libsys.model.User;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import rzelonek.libsys.auth.Auth;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class PrimaryController {

    @FXML
    private Label resultLabel;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void switchToSecondary() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Use the username and password as needed
        // ...

        Auth auth = new Auth();
        if (auth.authenticate(username, password)) {
            App.setRoot("secondary");
        } else {
            resultLabel.setText("Failed to log in");

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
                resultLabel.setText("");
            }));
            timeline.setCycleCount(1);
            timeline.play();
        }

    }

    @FXML
    private Canvas drawArea;
    private GraphicsContext gc;

    @FXML
    private Canvas canvas;

    @FXML
    private void initialize() {

    }

    @FXML
    private void testlogin() {
        try {
            App.setRoot("secondary");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
