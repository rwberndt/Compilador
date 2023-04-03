package interfacecompilador.interfacecompilador;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class InterfaceApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InterfaceApplication.class.getResource("interfaceCompilador.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.minHeightProperty().set(600);
        stage.minWidthProperty().set(900);
        stage.setScene(scene);
        scene.getRoot().applyCss();
        stage.show();
        InterfaceController controller = (InterfaceController) fxmlLoader.getController();
        controller.setUpScrolPane();
    }

    public static void main(String[] args) {
        launch();
    }
}