package ArquivosGals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Semantico implements Constants
{
    private String operador; 	// tipo string
    private ArrayList<String> codigo;  	// tipo lista de string
    private Map<String, Integer> simbolos = new HashMap<>();
    private ArrayList<String> listaid;
    private static final Stack<String> pilhaRotulo = new Stack<>();
    Stack<Integer> pilha_tipos; // tipo pilha int64 - 1 float64 - 2 String - 3 bool - 4

    private int contagem = 0;
    private static final Integer _Float = 2;
    private static final Integer _Int = 1;
    private static final Integer _String = 3;
    private static final Integer _Bool = 4;

    private final String TEMP_INT = "_temp_int";
    private final String TEMP_FLOAT = "_temp_float";
    private final String TEMP_STRING = "_temp_string";
    private final String TEMP_BOOL = "_temp_bool";

    public Semantico(){
        operador = "";
        codigo = new ArrayList<String>();
        pilha_tipos = new Stack<Integer>();
        listaid = new ArrayList<>();

        simbolos.put(TEMP_INT, 1);
        simbolos.put(TEMP_FLOAT, 2);
        simbolos.put(TEMP_STRING, 3);
        simbolos.put(TEMP_BOOL, 4);

    }


    public void executeAction(int action, Token token)	throws SemanticError
    {
        switch (action) {

            case 1 : metodo_acao01();
                break;
            case 5:metodo_acao05(token);
                break;
            case 6: metodo_acao06(token);
                break;
            case 2 : metodo_acao02();
                break;
            case 3: metodo_acao03();
                break;
            case 4: metodo_acao04();
                break;
            case 7: metodo_acao07();
                break;
            case 8: metodo_acao08();
                break;
            case 9: metodo_acao09(token);
                break;
            case 10: metodo_acao10();
                break;
            case 11 : metodo_acao11();
                break;
            case 12: metodo_acao12();
                break;
            case 13: metodo_acao13();
                break;
            case 14: metodo_acao14();
                break;
            case 15: metodo_acao15();
                break;
            case 16: metodo_acao16();
                break;
            case 17: metodo_acao17();
                break;
            case 18: metodo_acao18();
                break;
            case 19: metodo_acao19();
                break;
            case 20: metodo_acao20(token);
                break;
            case 21: metodo_acao21();
                break;
            case 22: metodo_acao22(token);
                break;
            case 23: metodo_acao23(token);
                break;
            case 24: metodo_acao24();
                break;
            case 25: metodo_acao25();
                break;
            case 26: metodo_acao26();
                break;
            case 27: metodo_acao27();
                break;
            case 28: metodo_acao28();
                break;
            case 29: metodo_acao29();
                break;
            case 30: metodo_acao30(token);
                break;
            case 31: metodo_acao31();
                break;
            case 32: metodo_acao32();
                break;
            case 33: metodo_acao33();
                break;
        }
        System.out.println("Ação #"+action+", Token: "+token);
    }

    private boolean verificaCompativel(String tipoVar, Integer tipoValor) {
        if ("float64".equals(tipoVar)) {
            return Arrays.asList(_Float, _Int).contains(tipoValor);
        } else if ("int64".equals(tipoVar)) {
            return _Int.equals(tipoValor);
        } else if ("string".equals(tipoVar)) {
            return _String.equals(tipoValor);
        } else if ("bool".equals(tipoVar)) {
            return _Bool.equals(tipoValor);
        }

        return false;
    }

    private String getClasseIl(Integer tipoVariavel) {

        switch (tipoVariavel) {
            case 1 -> {
                return "int64";
            }
            case 2 -> {
                return "float64";
            }
            case 3 -> {
                return "string";
            }
            case 4 -> {
                return "bool";
            }
        }
        throw new RuntimeException("O tipo de variável %s não possui uma classe definida".formatted(tipoVariavel));
    }


    private String getClasseIlMai(Integer tipoVariavel) {

        switch (tipoVariavel) {
            case 1 -> {
                return "Int64";
            }
            case 2 -> {
                return "Double";
            }
            case 3 -> {
                return "String";
            }
            case 4 -> {
                return "Boolean";
            }
        }
        throw new RuntimeException("O tipo de variável %s não possui uma classe definida".formatted(tipoVariavel));
    };

    private String ajustarFormatacaoNumeros(String num) {
        return num.replace(".", "").replace(",",".");
    }
    public void metodo_acao05(Token token){
        pilha_tipos.add(1);
        codigo.add("ldc.i8 ".concat(ajustarFormatacaoNumeros(token.getLexeme())+"\n"));
        codigo.add("conv.r8 \n");

    }
    public void metodo_acao06(Token token){
        pilha_tipos.add(2);
        codigo.add("ldc.r8 ".concat(ajustarFormatacaoNumeros(token.getLexeme())+"\n"));
    }

    public void metodo_acao14()
    {
        Integer tipo = pilha_tipos.pop();
        if(tipo == 1)
        {
            codigo.add("conv.r8 \n");
        }
        codigo.add("call void [mscorlib]System.Console::Write(" + getClasseIl(tipo) + ")\n");
    }
    public void metodo_acao15()
    {
        codigo.add(".assembly extern mscorlib {}\n");
        codigo.add(".assembly _codigo_objeto {}\n");
        codigo.add(".module _codigo_objeto.exe\n");
        codigo.add("\n");
        codigo.add(".class public _UNICA{\n");
        codigo.add(".method static public void _principal() {\n");
        codigo.add(".entrypoint\n");

    }

    public void metodo_acao16(){
        codigo.add(" ret \n");
        codigo.add("}\n");
        codigo.add("}\n");
    }

    public void adicionaPilhaTipoAritimetico(Integer tipo , Integer tipo2){
        if(tipo.equals( 2) || tipo2.equals(2))
        {
            pilha_tipos.add(2);
        }else
        {
            pilha_tipos.add(1);
        }

    }
    public void metodo_acao01()
    {
        int tipo1 = pilha_tipos.pop();
        int tipo2 = pilha_tipos.pop();

        adicionaPilhaTipoAritimetico(tipo1,tipo2);
        codigo.add("add \n");
    }
    public void metodo_acao02()
    {
        int tipo1 = pilha_tipos.pop();
        int tipo2 = pilha_tipos.pop();

        adicionaPilhaTipoAritimetico(tipo1,tipo2);
        codigo.add("sub \n");
    }

    public void metodo_acao03()
    {
        int tipo1 = pilha_tipos.pop();
        int tipo2 = pilha_tipos.pop();

        adicionaPilhaTipoAritimetico(tipo1,tipo2);
        codigo.add("mul \n");

        if (pilha_tipos.peek().equals(1)) codigo.add("conv.i8");
    }
    public void metodo_acao04() throws SemanticError
    {
        int tipo1 = pilha_tipos.pop();
        int tipo2 = pilha_tipos.pop();

        adicionaPilhaTipoAritimetico(tipo1,tipo2);
        codigo.add("div \n");
    }

    public void metodo_acao07()
    {
    }
    public void metodo_acao08()
    {
        codigo.add("ldc.i8 -1 \n");
        codigo.add("conv.r8 \n");
        codigo.add("mul \n");
    }

    public void metodo_acao09(Token token)
    {
        operador = token.getLexeme();
    }
    public void metodo_acao10()
    {
        int tipo1 = pilha_tipos.pop();
        int tipo2 = pilha_tipos.pop();

        pilha_tipos.add(4);

        switch (operador)
        {
            case ">": codigo.add("cgt \n"); break;
            case "<": codigo.add("clt \n"); break;
            case "==": codigo.add("ceq \n"); break;
            case ">=":
                codigo.add("cgt \n");
                codigo.add("ldc.i4.0 \n");
                codigo.add("ceq \n");
                break;
            case "!=":
                codigo.add("ceq \n");
                codigo.add("ldc.i4.0 \n");
                codigo.add("ceq \n");
                break;

        }
    }

    public void metodo_acao11()
    {
        pilha_tipos.add(4);
        codigo.add("ldc.i4.1 \n");
    }
    public void metodo_acao12()
    {
        pilha_tipos.add(4);
        codigo.add("ldc.i4.0 \n");
    }
    public void metodo_acao13() throws SemanticError
    {
        codigo.add("ldc.i4.1 \n");
        codigo.add("xor \n");
    }

    public void metodo_acao17(){
        codigo.add("ldstr " + "\"\\n\" " +"\n");
        codigo.add("call void [mscorlib]System.Console::Write(string) \n");
    }
    public void metodo_acao18() throws SemanticError {
        Integer tipo1 = pilha_tipos.pop();
        Integer tipo2 = pilha_tipos.pop();

        pilha_tipos.push(4);
        codigo.add("and \n");
    }
    public void metodo_acao19() throws SemanticError{
        Integer tipo1 = pilha_tipos.pop();
        Integer tipo2 = pilha_tipos.pop();


        pilha_tipos.push(4);
        codigo.add("or \n");
    }
    public void metodo_acao20(Token token)throws SemanticError {
        pilha_tipos.push(3);
        codigo.add("ldstr "+ token.getLexeme() + " \n");
    }

    public void metodo_acao21() {
        codigo.add(".locals(int64 _temp_int, float64 _temp_float, string _temp_str, bool _temp_bool)\n");
    }

    public void metodo_acao22(Token token) {
        listaid.add(token.getLexeme());
    }
    public void metodo_acao23(Token token) throws SemanticError {


        if (simbolos.get(token.getLexeme()) != null) {
            codigo.add("ldloc " + token.getLexeme() + " \n");

            if (simbolos.get(token.getLexeme()).equals(1)) {
                codigo.add("conv.r8  \n");
            }

            pilha_tipos.push(simbolos.get(token.getLexeme()));

        } else {
            throw new SemanticError("identificador " + token.getLexeme() + " não declarado");
        }

    }
    public void metodo_acao24() throws SemanticError {

        Integer vartipo = pilha_tipos.pop();

        for (int i = 0; i < listaid.size() - 1; i++) {
            codigo.add("dup \n");
        }

        for (String id : listaid) {

            if (simbolos.get(id) ==  null || simbolos.get(id) == 0 ) {
                codigo.add(".locals (%s %s)".formatted(getClasseIl(vartipo), id) + " \n");
                simbolos.put(id, vartipo);
            } else {
                if (!verificaCompativel(getClasseIl(vartipo), simbolos.get(id))) {
                    throw new SemanticError("Tipos incompatíveis em comando de atribuição");
                }
            }
            if (simbolos.get(id).equals(1)) {
                codigo.add("conv.i8");
            }
            codigo.add("stloc " + id + " \n");
        }
        listaid.clear();
    }

    private void metodo_acao25(){

        Integer tipo1 = pilha_tipos.pop();

        switch (tipo1) {
            case 1 -> {
                codigo.add("conv.i8 \n");
                codigo.add("stloc _temp_int \n");
                listaid.add(TEMP_INT);
            }
            case 2 -> {
                codigo.add("stloc _temp_float \n");
                listaid.add(TEMP_FLOAT);
            }
            case 3 -> {
                codigo.add("stloc _temp_str \n");
                listaid.add(TEMP_STRING);
            }
            case 4 -> {
                codigo.add("stloc _temp_bool \n");
                listaid.add(TEMP_BOOL);
            }
        }
    }

    private void metodo_acao26() throws SemanticError {

        contagem++;
        String rotulo = "r_" + contagem;

        Integer tipoExpressao = pilha_tipos.peek();
        codigo.add("brfalse " + pilhaRotulo.peek()+ " \n");

        String varTemp = listaid.get(listaid.size() - 1);
        listaid.remove(listaid.size() - 1);

        if(varTemp != null){
            codigo.add("ldloc " + varTemp+ " \n");
        }

        Integer vartipo = pilha_tipos.pop();

        for (int i = 0; i < listaid.size() - 1; i++) {
            codigo.add("dup \n");
        }

        for (String id : listaid) {

            if (simbolos.get(id) ==  null || simbolos.get(id) == 0 ) {
                codigo.add(".locals (%s %s)".formatted(getClasseIl(vartipo), id) + " \n");
                simbolos.put(id, vartipo);
            } else {
                if (!verificaCompativel(getClasseIl(vartipo), simbolos.get(id))) {
                    throw new SemanticError("Tipos incompatíveis em comando de atribuição");
                }
            }
            if (simbolos.get(id).equals(1)) {
                codigo.add("conv.i8");
            }
            codigo.add("stloc " + id + " \n");
        }

        codigo.add(rotulo + ": \n");
    }

    private void metodo_acao27() throws SemanticError {

        for (String id : listaid) {

            Integer tipoVar = simbolos.get(id);

            // Verifica se a variável já foi declarada
            if (tipoVar == null || tipoVar == 0) {
                throw new SemanticError("Identificador %s não declarado".formatted(id));
            }

            codigo.add("call string [mscorlib]System.Console::ReadLine() \n");

            if (!tipoVar.equals(_String))
            {
                codigo.add("call " + getClasseIl(tipoVar) + " [mscorlib]System." + getClasseIlMai(tipoVar) + "::Parse(string) \n");
            }

            codigo.add("stloc " + id + " \n");
        }
        listaid.clear();

    }

    private void metodo_acao28(){

        pilha_tipos.pop();

        contagem++;

        pilhaRotulo.push("r_" + contagem);

        codigo.add("brfalse " + pilhaRotulo.peek() + " \n");
    }


    private void metodo_acao29() {
        codigo.add(pilhaRotulo.pop() + ": \n");
    }
    public void metodo_acao30(Token token) {
        contagem++;

        codigo.add("br r_" + contagem);

        String rotulo1 = pilhaRotulo.pop();
        codigo.add(rotulo1 + ":");

        pilhaRotulo.add("r_" + contagem);
    }

    private void metodo_acao31() {

        contagem++;
        pilhaRotulo.add("r_" + contagem);

        codigo.add(pilhaRotulo.peek() + ": \n");
    }

    private void metodo_acao32() {
        pilhaRotulo.add("r_" + contagem);
        codigo.add("brtrue " + pilhaRotulo.peek() + " \n");
    }

    private void metodo_acao33(){


        String r2 = pilhaRotulo.pop();
        String r1 = pilhaRotulo.pop();

        codigo.add("br " + r1 + " \n");
        codigo.add(r2 + ": \n");
    }


    public void appendToFileCodigoIl(File file){
        try {
            FileWriter fileWriter = new FileWriter(file);

            StringBuilder stringCodigo = new StringBuilder();
            codigo.forEach(x-> {
                stringCodigo.append(x).append("\n");

            });

            fileWriter.write(stringCodigo.toString());
            fileWriter.close();


        } catch (IOException e) {
            System.out.print(e.getMessage());
        }

    }
}