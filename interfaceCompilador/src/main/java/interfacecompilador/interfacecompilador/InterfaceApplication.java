package interfacecompilador.interfacecompilador;

import javafx.application.Application;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;

import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.scene.layout.Border;

import java.io.IOException;

public class InterfaceApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InterfaceApplication.class.getResource("interfaceCompilador.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);

        TextArea areaCodigo = (TextArea) scene.lookup("#editor");
        stage.minHeightProperty().set(600);
        stage.minWidthProperty().set(900);
        stage.setScene(scene);
        scene.getRoot().applyCss();
        colocarScroll(scene);
        stage.show();
    }

    private void colocarScroll(Scene scene) {
        TextArea areaCodigo = (TextArea) scene.lookup("#editor");
        areaCodigo.setStyle("-fx-font-family: monospace");
        TextArea mensagem = (TextArea) scene.lookup("#mensagem");
        mensagem.setStyle("-fx-font-family: monospace");
        TextArea linhas = (TextArea) scene.lookup("#linhas");
        linhas.setStyle("-fx-font-family: monospace");
        ScrollBar n1 = (ScrollBar) areaCodigo.lookup(".scroll-bar");
        if (n1 != null) {
            ScrollBar n2 = (ScrollBar) linhas.lookup(".scroll-bar");
            if (n2 != null) {
                n1.valueProperty().bindBidirectional(n2.valueProperty());
            }
        }
        setScrollPanePolicy(areaCodigo, ScrollPane.ScrollBarPolicy.ALWAYS);
        setScrollPanePolicy(mensagem, ScrollPane.ScrollBarPolicy.ALWAYS);
        setScrollPanePolicy(linhas, ScrollPane.ScrollBarPolicy.NEVER);
    }

    private void setScrollPanePolicy(Node node, ScrollPane.ScrollBarPolicy policy) {
        ScrollPane scrollPaneCodigo = (ScrollPane) node.lookup(".scroll-pane");
        scrollPaneCodigo.setVbarPolicy(policy);
        scrollPaneCodigo.setHbarPolicy(policy);
    }

    public static void main(String[] args) {
        launch();
    }

}