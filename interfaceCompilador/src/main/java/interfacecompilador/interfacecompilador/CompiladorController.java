package interfacecompilador.interfacecompilador;

import ArquivosGals.*;
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

public class CompiladorController {


    @FXML
    private TextArea textAreaMessage;

    @FXML
    private TextArea textAreaCode;

    @FXML
    private Label labelStatus;

    @FXML
    private TextArea textAreaLines;

    private int qtdlines = 1;

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
        editFile();
    }

    private void editFile() throws IOException {
        if (labelStatus.getText() != null && !labelStatus.getText().isEmpty()) {
            FileWriter writer = new FileWriter(labelStatus.getText(), Charset.availableCharsets().get("UTF-8"), false);
            writer.write(textAreaCode.getText());
            writer.close();
        }
    }

    public void copyFile() {
        textAreaCode.copy();
    }

    public void pasteFile() {
        textAreaCode.paste();
    }

    public void cutFile() {
        textAreaCode.cut();
    }

    public void showNames() {
        textAreaMessage.setText("Equipe: Vinícius da Cunha Lopes e Ricardo Berndt");
    }

// public void compile() {
// var input = textAreaCode.getText();
// StringBuilder mensagem = new StringBuilder();
// Lexico lexico = new Lexico();
// lexico.setInput(input);
// try {
// mensagem.append( "Linha | Classe | Lexema\n");
// Token token = lexico.nextToken();
// while( token != null) {
// mensagem.append(GetLineFromPosition(token.getPosition()) + " ");
// mensagem.append(SearchInTokenList(token.getId()) + " "); mensagem.append(token.getLexeme()+"\n");
// token= lexico.nextToken(); }
// mensagem.append("Programa compilado com sucesso");
// }
// catch(LexicalError e)
// {
// mensagem = new StringBuilder();
// if(e.getMessage().contains("símbolo inválido"))
// {
// mensagem.append("Erro na linha " + GetLineFromPosition(e.getPosition()) +
// " - " + textAreaCode.getText().charAt(e.getPosition()) + " - símbolo inválido");
// }else
// {
// mensagem.append( "Erro na linha:" + GetLineFromPosition(e.getPosition())+ " - " + e.getMessage());
// }
// }finally {
// textAreaMessage.setText(mensagem.toString());
// }
// }


    public void compile() {
        var input = textAreaCode.getText();
        StringBuilder mensagem = new StringBuilder();
        Lexico lexico = new Lexico();
        Sintatico sintatico = new Sintatico();
        Semantico semantico = new Semantico();
        //...
        lexico.setInput(input);
        //...
        try {
            sintatico.parse(lexico, semantico); // tradução dirigida pela sintaxe
            mensagem.append("Programa compilado com sucesso");
        }
    // mensagem: programa compilado com sucesso - área reservada para mensagens
        catch (LexicalError e) {
            mensagem = new StringBuilder();
            if (e.getMessage().contains("símbolo inválido")) {
                mensagem.append("Erro na linha " + GetLineFromPosition(e.getPosition()) +
                        " - " + textAreaCode.getText().charAt(e.getPosition()) + " - símbolo inválido");
            } else {
                mensagem.append("Erro na linha:" + GetLineFromPosition(e.getPosition()) + " - " + e.getMessage());
            }
//Trata erros léxicos, conforme especificação da parte 2 - do compilador
        } catch (SyntaticError e) {
            String linha = GetLineFromPosition(e.getPosition());
            mensagem = new StringBuilder()
                    .append("Erro na linha ")
                    .append(" " + linha)
                    .append(" - ")
                    .append(e.getMessage().replace("{0}", sintatico.getCurrentToken().getLexeme()));

//Trata erros sintáticos
//linha sugestão: converter getPosition em linha
//símbolo encontrado sugestão: implementar um método getToken no sintatico
//mensagem - símbolos esperados, alterar ParserConstants.java, String[] PARSER_ERROR
        } catch (SemanticError e) {
//Trata erros semânticos
        } finally {
            textAreaMessage.setText(mensagem.toString());
        }
    }

    public String SearchInTokenList(int id) {
        if (id == 2) {
            return "identificador";
        } else if (id == 3) {
            return "int";
        } else if (id == 4) {
            return "float";
        } else if (id == 5) {
            return "bin";
        } else if (id == 6) {
            return "string";
        } else if (id <= 26) {
            return "simbolo especial";
        } else if (id <= 36) {
            return "palavra reservada";
        }

        return "class not found";
    }

    public String GetLineFromPosition(Integer position) {
        Integer lines = 0;
        for (int i = 0; i < position; i++) {
            if (textAreaCode.getText().charAt(i) == '\n') {
                lines++;
            }
        }

        lines++;
        return lines.toString();
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

    public void controlLines() {
        var contagem = textAreaCode.getText().chars().filter(a -> a == '\n').count() + 1;

        if (contagem > qtdlines) {
            qtdlines++;
            addLines();
        } else if (contagem < qtdlines) {
            removeLines();
            qtdlines--;
        }
    }

    public void addLines() {
        textAreaLines.setText(textAreaLines.getText().concat("\n" + qtdlines));
    }

    public void removeLines() {
        textAreaLines.setText(textAreaLines.getText().replaceFirst("\n" + qtdlines, ""));
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