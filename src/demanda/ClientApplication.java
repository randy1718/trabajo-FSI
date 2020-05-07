package demanda;

import demanda.entities.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;

public class ClientApplication extends Application {

    private static Stage window;

    /**
     *
     */
    public static Model model = new Model(Arrays.asList(
            "Pan tajado sandwich",
            "Torta blanca de limón",
            "Torta negra",
            "Muffins x12",
            "Ponqué de chocolate",
            "Palito de queso",
            "Tostadas",
            "Galleta frutos rojos",
            "Mini-croissant x12",
            "Tortillas de maíz"
    ));

    @Override
    public void start(Stage window) throws Exception {
        ClientApplication.window = window;

        Parent root = FXMLLoader.load(getClass().getResource("view/data_entry.fxml"));
        
        Scene scene = new Scene(root);

        window.setScene(scene);
        window.setResizable(false);
        window.show();
    }

    public static void reloadScene() throws IOException {
        Parent root = FXMLLoader.load(ClientApplication.class.getResource("view/data_entry.fxml"));

        Scene scene = new Scene(root);

        window.setScene(scene);
        window.setResizable(false);
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
