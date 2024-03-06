package rzelonek.libsys;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.io.FileNotFoundException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static public void setRoot(String fxml) throws IOException {
        try {
            scene.setRoot(loadFXML(fxml));
        } catch (IOException e) {
            String resourcePath = fxml + ".fxml";
            String currentPath = Paths.get("").toAbsolutePath().toString();
            String errorMessage = "Failed to load FXML file: " + resourcePath + "\n";
            errorMessage += "Directory not found: " + resourcePath + "\n";
            errorMessage += "Current path: " + currentPath;
            throw new IOException(errorMessage, e);
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        String resourcePath =  fxml + ".fxml";
        URL resourceUrl = App.class.getResource(resourcePath);
        if (resourceUrl == null) {
            System.out.println("Error: Directory not found: " + resourcePath);
            throw new FileNotFoundException("Directory not found: " + resourcePath);
        }
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml +  ".fxml"));
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            String errorMessage = "Failed to load FXML file: " + resourcePath + "\n";
            errorMessage += "Resource URL: " + resourceUrl + "\n";
            errorMessage += "Current path: " + Paths.get("").toAbsolutePath();
            throw new IOException(errorMessage, e);
        }
    }

    public static void main(String[] args) {
        launch();
    }


}

