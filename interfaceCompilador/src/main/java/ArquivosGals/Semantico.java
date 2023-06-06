package ArquivosGals;

import java.util.Stack;
import java.util.ArrayList;

public class Semantico implements Constants
{
    private String operador; 	// tipo string
    private ArrayList<String> codigo;  	// tipo lista de string
    Stack<Integer> pilha_tipos; // tipo pilha int64 - 1 float64 - 2 String - 3 bool-4


    public void executeAction(int action, Token token)	throws SemanticError
    {
        switch (action) {

            case 1 : metodo_acao01();
            break;
            case 5:metodo_acao05(token);
            break;
        }
            System.out.println("Ação #"+action+", Token: "+token);
    }

    public void metodo_acao05(Token token){
        pilha_tipos.add(1);
        codigo.add("ldc.i8" + token.getLexeme());
        codigo.add("conv.r8");

    }
    public void metodo_acao06(Token token){
        pilha_tipos.add(2);
        codigo.add("ldc.i8" + token.getLexeme());
    }

    public void metodo_acao14()
    {
        Integer tipo = pilha_tipos.pop();
        if(tipo == 1)
        {
            codigo.add("conv.r8");
        }
    }
    public void metodo_acao15()
    {
      codigo.add(".assembly extern mscorlib{}"+
                 ".assembly _codigo_objeto{}"+
                 ".module _codigo_objeto.exe"+
                 ".class public _UUNICA{"+
                 ".method static public void _principal(){"+
                 ".entrypoint");
    }

    public void metodo_acao16(){
        codigo.add(" ret ");
    }

    public void metodo_acao01()
    {
        int tipo1 = pilha_tipos.pop();
        int tipo2 = pilha_tipos.pop();

        if(tipo1 == 2 || tipo2 == 2)
        {
            pilha_tipos.add(2);
        }else
        {
            pilha_tipos.add(1);
        }
        codigo.add("add");
    }
    public void metodo_acao02()
    {
        int tipo1 = pilha_tipos.pop();
        int tipo2 = pilha_tipos.pop();

        if(tipo1 == 2 || tipo2 == 2)
        {
            pilha_tipos.add(2);
        }else
        {
            pilha_tipos.add(1);
        }
        codigo.add("sub");
    }

    public void metodo_acao03()
    {
        int tipo1 = pilha_tipos.pop();
        int tipo2 = pilha_tipos.pop();

        if(tipo1 == 2 || tipo2 == 2)
        {
            pilha_tipos.add(2);
        }else
        {
            pilha_tipos.add(1);
        }
        codigo.add("mul");
    }
    public void metodo_acao04() throws SemanticError
    {
        int tipo1 = pilha_tipos.pop();
        int tipo2 = pilha_tipos.pop();

        if(tipo1 == tipo2)
        {
            pilha_tipos.add(tipo1);
        }else
        {
           throw new SemanticError("erro de tipos divisao");
        }
        codigo.add("div");
    }

    public void metodo_acao07()
    {
    }
    public void metodo_acao08()
    {
        codigo.add("ldc.i8 -1");
        codigo.add("conv.r8");
        codigo.add("mul");
    }
    public void metodo_acao10()
    {
        int tipo1 = pilha_tipos.pop();
        int tipo2 = pilha_tipos.pop();
        switch (operador)
        {
            case ">": codigo.add("cgt"); break;
            case "<": codigo.add("clt"); break;
            case "==": codigo.add("ceq"); break;
        }
    }

    public void metodo_acao11()
    {
        pilha_tipos.add(4);
        codigo.add("ldc.i4.1");
    }
    public void metodo_acao12()
    {
        pilha_tipos.add(4);
        codigo.add("ldc.i4.0");
    }
    public void metodo_acao13() throws SemanticError
    {
        int tipo1 = pilha_tipos.pop();
        if(tipo1 == 4)
        {
            pilha_tipos.add(4);
        }else
        {
            throw new SemanticError("erro de tipos boolean");
        }
        codigo.add("ldc.i4.1");
        codigo.add("xor");
    }


}
