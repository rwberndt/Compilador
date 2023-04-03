package interfacecompilador.interfacecompilador;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class InterfaceController {


    @FXML
    private TextArea textAreaMessage;

    @FXML
    private TextArea textAreaCode;

    @FXML
    private Label labelStatus;

    @FXML
    private TextArea textAreaLines;

    private int qtdlinhas = 1;

    private static final FileChooser file = new FileChooser();

    private String folder;

    public void initialize() {
        textAreaLines.setText(String.valueOf(textAreaCode.getParagraphs().size()));
        file.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
    }

    public void newFile() {
        labelStatus.setText("");
        textAreaCode.setText("");
        textAreaMessage.setText("");
    }

    public void openFile() throws IOException {
        File selectedFile = file.showOpenDialog(textAreaCode.getScene().getWindow());
        if (selectedFile != null) {
            String stringDoArquivo = Files.readString(selectedFile.toPath(), StandardCharsets.UTF_8);
            textAreaCode.setText(stringDoArquivo);
            labelStatus.setText(selectedFile.getAbsolutePath());
            folder = selectedFile.getAbsolutePath();
            textAreaMessage.setText("");
        }
    }

    public void saveFile() throws IOException {
        if (labelStatus.getText() == null || labelStatus.getText().isEmpty()) {
            File arquivo = file.showSaveDialog(textAreaCode.getScene().getWindow());
            if (arquivo == null) {
                return;
            }
            arquivo.createNewFile();
            labelStatus.setText(arquivo.getAbsolutePath());
            folder = arquivo.getAbsolutePath();
        }
        editarArquivoJaExistente();
    }

    private void editarArquivoJaExistente() throws IOException {
        if (labelStatus.getText() != null && !labelStatus.getText().isEmpty()) {
            FileWriter writer = new FileWriter(labelStatus.getText(), Charset.availableCharsets().get("UTF-8"), false);
            writer.write(textAreaCode.getText());
            writer.close();
        }
    }

    public void copiarAquivo() {
        textAreaCode.copy();
    }

    public void colarnoArquivo() {
        textAreaCode.paste();
    }

    public void recortarArquivo() {
        textAreaCode.cut();
    }

    public void showNames() {
        textAreaMessage.setText("Equipe: Vinícius da Cunha Lopes e Ricardo Berndt");
    }

    public void compile() {
        textAreaMessage.setText("Programa Compilado com sucesso!");
    }

    public void onKeyPressed(KeyEvent keyEvent) throws IOException {
        if (keyEvent.isControlDown()) {
            switch (keyEvent.getCode()) {
                case S -> saveFile();
                case N -> newFile();
                case O -> openFile();
            }
        } else {
            if (keyEvent.getCode() == KeyCode.F7) {
                compile();
            }
            if (keyEvent.getCode() == KeyCode.F1) {
                showNames();
            }
        }
    }

    public void controlarlinhas() {
        var contagem = textAreaCode.getText().chars().filter(a -> a == '\n').count();

        if (contagem >= qtdlinhas) {
            qtdlinhas++;
            addLinhas();
        } else if (contagem < qtdlinhas) {
            removeLinhas();
            qtdlinhas--;
        }
    }

    public void addLinhas() {
        textAreaLines.setText(textAreaLines.getText().concat("\n" + qtdlinhas));
    }

    public void removeLinhas() {
        textAreaLines.setText(textAreaLines.getText().replaceFirst("\n" + qtdlinhas, ""));
    }

    public void setUpScrolPane() {
        ScrollBar codeScrollBar = (ScrollBar) textAreaCode.lookup(".scroll-bar");
        ScrollBar linesScrollBar = (ScrollBar) textAreaLines.lookup(".scroll-bar");
        codeScrollBar.valueProperty().bindBidirectional(linesScrollBar.valueProperty());
        setUpScrollPaneScrollBarPolicy(textAreaCode, ScrollPane.ScrollBarPolicy.ALWAYS);
        setUpScrollPaneScrollBarPolicy(textAreaLines, ScrollPane.ScrollBarPolicy.NEVER);
        setUpScrollPaneScrollBarPolicy(textAreaMessage, ScrollPane.ScrollBarPolicy.ALWAYS);

    }

    private void setUpScrollPaneScrollBarPolicy(Node node, ScrollPane.ScrollBarPolicy policy) {
        ScrollPane scrollPaneCodigo = (ScrollPane) node.lookup(".scroll-pane");
        scrollPaneCodigo.setVbarPolicy(policy);
        scrollPaneCodigo.setHbarPolicy(policy);
    }
}