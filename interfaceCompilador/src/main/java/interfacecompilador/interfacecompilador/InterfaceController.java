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
    private Label labelLines;

    @FXML
    private TextArea linhas;

    private int qtdlinhas = 1;

    private static final FileChooser file = new FileChooser();

    private String pasta;

    private ArrayList<Integer> listaQuebraLinhas = new ArrayList<Integer>();

    public void initialize() {
        linhas.setText(String.valueOf(textAreaCode.getParagraphs().size()));
        labelLines.setVisible(false);
        file.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
    }

    public void novoArquivo() {
        labelStatus.setText("");
        textAreaCode.setText("");
        textAreaMessage.setText("");
    }

    public void abrirArquivo() throws IOException {
        File selectedFile = file.showOpenDialog(textAreaCode.getScene().getWindow());
        if (selectedFile != null) {
            String stringDoArquivo = Files.readString(selectedFile.toPath(), StandardCharsets.UTF_8);
            textAreaCode.setText(stringDoArquivo);
            labelStatus.setText(selectedFile.getAbsolutePath());
            pasta = selectedFile.getAbsolutePath();
            textAreaMessage.setText("");
        }
    }

    public void salvarArquivo() throws IOException {
        if (labelStatus.getText() == null || labelStatus.getText().isEmpty()) {
            File arquivo = file.showSaveDialog(textAreaCode.getScene().getWindow());
            if (arquivo == null) {
                return;
            }
            arquivo.createNewFile();
            labelStatus.setText(arquivo.getAbsolutePath());
            pasta = arquivo.getAbsolutePath();
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

    public void Equipe() {
        textAreaMessage.setText("Equipe: Vinícius da Cunha Lopes e Ricardo Berndt");
    }

    public void compilar() {
        textAreaMessage.setText("Programa Compilado com sucesso!");
    }


    public void chamarAcoesTela(KeyEvent keyEvent) throws IOException {
        if (keyEvent.isControlDown()) {
            switch (keyEvent.getCode()) {
                case S -> salvarArquivo();
                case N -> novoArquivo();
                case O -> abrirArquivo();
            }
        } else {
            if (keyEvent.getCode() == KeyCode.F7) {
                compilar();
            }
            if (keyEvent.getCode() == KeyCode.F1) {
                Equipe();
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
        linhas.setText(linhas.getText().concat("\n" + qtdlinhas));
    }

    public void removeLinhas() {
        linhas.setText(linhas.getText().replaceFirst("\n" + qtdlinhas, ""));
    }

    public void setUpScrolPane() {
        ScrollBar codeScrollBar = (ScrollBar) textAreaCode.lookup(".scroll-bar");
        ScrollBar linesScrollBar = (ScrollBar) linhas.lookup(".scroll-bar");
        codeScrollBar.valueProperty().bindBidirectional(linesScrollBar.valueProperty());
        setUpScrollPaneScrollBarPolicy(textAreaCode, ScrollPane.ScrollBarPolicy.ALWAYS);
        setUpScrollPaneScrollBarPolicy(linhas, ScrollPane.ScrollBarPolicy.NEVER);
        setUpScrollPaneScrollBarPolicy(textAreaMessage, ScrollPane.ScrollBarPolicy.ALWAYS);

    }

    private void setUpScrollPaneScrollBarPolicy(Node node, ScrollPane.ScrollBarPolicy policy) {
        ScrollPane scrollPaneCodigo = (ScrollPane) node.lookup(".scroll-pane");
        scrollPaneCodigo.setVbarPolicy(policy);
        scrollPaneCodigo.setHbarPolicy(policy);
    }
}