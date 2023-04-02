package interfacecompilador.interfacecompilador;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    private TextArea mensagem;

    @FXML
    private TextArea editor;

    @FXML
    private Label labelStatus;

    @FXML
    private Label labelLinhas;

    @FXML
    private TextArea linhas;

    private int qtdlinhas = 1;

    private static final FileChooser file = new FileChooser();

    private String pasta;

    private ArrayList<Integer> listaQuebraLinhas = new ArrayList<Integer>();

    public void initialize() {
        linhas.setText(String.valueOf(editor.getParagraphs().size()));
        labelLinhas.setVisible(false);
        file.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
    }

    public void novoArquivo() {
        labelStatus.setText("");
        editor.setText("");
        mensagem.setText("");
    }

    public void abrirArquivo() throws IOException {
        File selectedFile = file.showOpenDialog(editor.getScene().getWindow());
        if (selectedFile != null) {
            String stringDoArquivo = Files.readString(selectedFile.toPath(), StandardCharsets.UTF_8);
            editor.setText(stringDoArquivo);
            labelStatus.setText(selectedFile.getAbsolutePath());
            pasta = selectedFile.getAbsolutePath();
            mensagem.setText("");
        }
    }

    public void salvarArquivo() throws IOException {
        if (labelStatus.getText() == null || labelStatus.getText().isEmpty()) {
            File arquivo = file.showSaveDialog(editor.getScene().getWindow());
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
            writer.write(editor.getText());
            writer.close();
        }
    }
    public void copiarAquivo() {
        editor.copy();
    }

    public void colarnoArquivo() {
        editor.paste();
    }

    public void recortarArquivo() {
        editor.cut();
    }

    public void Equipe() {
        mensagem.setText("Equipe: Vinícius da Cunha Lopes e Ricardo Berndt");
    }

    public void compilar(){
        mensagem.setText("Programa Compilado com sucesso!");
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
    public void controlarlinhas()
    {
        var contagem = editor.getText().chars().filter (a -> a == '\n').count();

        if(contagem >= qtdlinhas)
        {
            qtdlinhas++;
            addLinhas();
        }else if (contagem < qtdlinhas)
        {
            removeLinhas();
            qtdlinhas--;
        }
    }
    public void addLinhas()
    {
        linhas.setText(linhas.getText().concat("\n" + qtdlinhas));
    }
    public void removeLinhas()
    {
        linhas.setText(linhas.getText().replaceFirst("\n" + qtdlinhas, ""));
    }
}