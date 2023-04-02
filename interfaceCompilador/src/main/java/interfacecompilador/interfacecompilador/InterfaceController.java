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
        removerLinhas();
    }

    public void abrirArquivo() throws IOException {
        File selectedFile = file.showOpenDialog(editor.getScene().getWindow());
        if (selectedFile != null) {
            String stringDoArquivo = Files.readString(selectedFile.toPath(), StandardCharsets.UTF_8);
            editor.setText(stringDoArquivo);
            labelStatus.setText(selectedFile.getAbsolutePath());
            pasta = selectedFile.getAbsolutePath();
            mensagem.setText("");
            adicionarLinhas();

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
    private void adicionarLinhas() {
        int linhasExistentes = getQuantidadeLinhas();
        int tamUltimaLinhaAnterior = getTamanhoUltimaLinha();
        IntStream.range(0, editor.getParagraphs().size()).forEach(x -> {
            int linhaAtual = x + 1;
            if (linhaAtual > linhasExistentes) {
                linhas.setText(linhas.getText().concat("\n" + linhaAtual));
            }
        });
        redefinirLarguraLista(tamUltimaLinhaAnterior);
    }

    private void removerLinhas() {
        int linhasExistentes = getQuantidadeLinhas();
        int tamUltimaLinhaAnterior = getTamanhoUltimaLinha();
        IntStream.range(1, linhasExistentes).forEach(x -> {
            int linhaAtual = x + 1;
            if (linhaAtual > editor.getParagraphs().size()) {
                linhas.setText(linhas.getText().replaceFirst("\n" + linhaAtual, ""));
            }
        });
        redefinirLarguraLista(tamUltimaLinhaAnterior);
    }

    private int getTamanhoUltimaLinha() {
        String[] todasLinhas = linhas.getText().split("\n");
        return todasLinhas[todasLinhas.length - 1].length();
    }

    private int getQuantidadeLinhas() {
        return linhas.getText().split("\n").length;
    }

    private void redefinirLarguraLista(int tamUltimaLinhaAnterior) {
        int tamUltimaLinha = getTamanhoUltimaLinha();
        if (tamUltimaLinhaAnterior != tamUltimaLinha) {
            double newScale = (tamUltimaLinha == 2 ? 0.6 : (tamUltimaLinha == 1 ? 1 : 0.5));
            linhas.setMinWidth(20 * tamUltimaLinha * newScale);
            linhas.setMaxWidth(20 * tamUltimaLinha * newScale);
        }
    }

    public void alterarContadorDeLinhaAoDigitar(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)
                || (keyEvent.getCode().equals(KeyCode.V) && keyEvent.isControlDown())) {
            adicionarLinhas();
        }
        if (keyEvent.getCode().equals(KeyCode.BACK_SPACE) || keyEvent.getCode().equals(KeyCode.DELETE)
                || (keyEvent.getCode().equals(KeyCode.X) && keyEvent.isControlDown())) {
            if (editor.getParagraphs().size() != getQuantidadeLinhas()) {
                removerLinhas();
            }
        }
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

    public void novaListaQuebraLinhas() {

        String text = editor.getText();

        listaQuebraLinhas.clear();

        for (int i=0; i<text.length(); i++) {
            if ('\n' == text.charAt(i)) {
                listaQuebraLinhas.add(i);
            }
        }
    }

}