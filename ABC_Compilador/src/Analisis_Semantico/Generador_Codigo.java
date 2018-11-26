/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analisis_Semantico;

import Compilador.ErrorToken;
import Compilador.ScannerABC;
import java.util.ArrayList;

/**
 *
 * @author ayma-93
 */
public class Generador_Codigo {
    
    private Pila_Semantica pila_semantica;
    private Tabla_Simbolos tabla_simbolos;
    private GenerarLabels generador_lbls;
    private String codigo;
    private String cod_tmp;
    private boolean generar;
    private ArrayList<String> registros;

    public Generador_Codigo() {
        this.pila_semantica = new Pila_Semantica();
        this.tabla_simbolos = new Tabla_Simbolos();
        this.generador_lbls = new GenerarLabels();
        this.codigo = "";
        this.cod_tmp = "";
        this.generar = true;
        this.registros = new ArrayList();
        this.registros.add("ax");
        this.registros.add("bx");
    }
    
    public void recordar_identificador(String id) {
        pila_semantica.push(new RS_Identificador(id));
    }
    
    public void recordar_tipo(String tipo) {
        pila_semantica.push(new RS_Tipo(tipo));
    }
    
    public void recordar_RS_DO(String tipo, String valor) {
        if(valor == null && !registros.contains(tipo)){
            Simbolo s = tabla_simbolos.buscarSimblolo(tipo);
            if(s == null)
                ScannerABC.errores.add(new ErrorToken(tipo,"ERROR_SEMANTICO","Error Semántico: Funcion " + tipo + " ya declarada. Linea: -" , 0));            
        }
        pila_semantica.push(new RS_DataObject(tipo, valor));
    }

    public void recordar_operador(String operador) {
         pila_semantica.push(new RS_Operador(operador));
    }

       public void guardar_variables_TS(String tipo, int linea) {
        RegistroSemantico top = pila_semantica.top();
        
        while (top instanceof RS_Identificador) {
            boolean resultado = tabla_simbolos.agregar_var_global(((RS_Identificador) top).getNombre(), tipo, linea);
            
            generar = resultado;
            
            pila_semantica.pop();
            
            codigo += ((RS_Identificador) top).getNombre();

            switch (tipo.toUpperCase()) {
                case "INT":
                    codigo += "\t dd dup (?)" + System.lineSeparator();
                    break;
                case "SHORTINT":
                    codigo += "\t  dw dup (?)" + System.lineSeparator();
                    break;
                case "LONGTINT":
                    codigo += "\t dw 3 dup (?) " + System.lineSeparator();
                    break;
                case "CHAR":
                    codigo += "\t db dup (?)" + System.lineSeparator();
                    break;
                case "BOOLEAN":
                    codigo += "\t db dup (?)" + System.lineSeparator();
                    break;
                case "REAL":
                    codigo += "\t dd dup (?) " + System.lineSeparator();
                    break;
                case "STRING":
                    codigo += "\t dw 30 dup (?) " + System.lineSeparator();
                    break;
                default:
                    break;
            }

            top = pila_semantica.top();
        }
    }

    public void guardar_constantes_TS(String nombre, String tipo, Object valor, int linea) {
        boolean resultado = tabla_simbolos.agregar_const_global(nombre, tipo, valor,linea);
        pila_semantica.pop();
        generar = resultado;
    }
    
    public void iniciar_variables() {
        codigo += "datos segment " + System.lineSeparator();
    }
    
    public void finalizar_variables() {
        codigo += "datos ends " + System.lineSeparator()+ System.lineSeparator();
    }

    public void inicializar_pila(){
        codigo += "pila segment stack \'stack\'" + System.lineSeparator();
        codigo += "dw 256 dup (?)" + System.lineSeparator();
        codigo += "pila ends" + System.lineSeparator()+ System.lineSeparator();
    }
    public void iniciar_codigo() {
        codigo += "codigo segment" + System.lineSeparator();
        codigo += "assume  cs:codigo, ds:datos, ss:pila" + System.lineSeparator()+ System.lineSeparator();
    }
    
    public void inicio_programa(){
        codigo += "inicio:" + System.lineSeparator();
        codigo += "mov ax,ds" + System.lineSeparator();
        codigo += "mov es,ax" + System.lineSeparator();
        codigo += "mov ax,datos" + System.lineSeparator();
        codigo += "mov ds,ax" + System.lineSeparator();
        codigo += "mov ax,pila" + System.lineSeparator();
        codigo += "mov ss,ax" + System.lineSeparator();
        codigo += "mov si,80h" + System.lineSeparator();
        codigo += "mov cl,byte ptr es:[si]" + System.lineSeparator();
        codigo += "xor ch,ch" + System.lineSeparator()+ System.lineSeparator();
    }
    
    public void fin_programa(){
        codigo += "salir:" + System.lineSeparator()+ System.lineSeparator();
        codigo += "    mov ax, 4C00h" + System.lineSeparator();
        codigo += "    int 21h" + System.lineSeparator()+ System.lineSeparator();
        codigo += "codigo ends" + System.lineSeparator();
        codigo += "end inicio" + System.lineSeparator();
    }

    public void evalBinaria() {
        RS_DataObject operando2 = (RS_DataObject) pila_semantica.pop();
        RS_Operador operador = (RS_Operador) pila_semantica.pop();
        RS_DataObject operando1 = (RS_DataObject) pila_semantica.pop();      

        if (!isOperacion(operador.getOperador())) {
            pila_semantica.push(operando1);
            pila_semantica.push(operador);
            pila_semantica.push(operando2);
            return;
        }
        if (operando1.getTipo().equals(operando2.getTipo())) {
            
            String tipo = operando1.getTipo();

            if (tipo.equals("Int") && operando1.getValor() != null) {
                int operando1Int = Integer.parseInt(operando1.getValor());
                int operando2Int = Integer.parseInt(operando2.getValor());
                
                int resultado = realizarOperacion(operando1Int, operando2Int, operador.getOperador());
                
                pila_semantica.push(new RS_DataObject("Int", Integer.toString(resultado)));
                return;
            } else if (tipo.equals("Float") && operando1.getValor() != null) {
                Float operando1Float = Float.parseFloat(operando1.getValor());
                Float operando2Float = Float.parseFloat(operando2.getValor());

                Float resultado = realizarOperacion(operando1Float, operando2Float, operador.getOperador());
                pila_semantica.push(new RS_DataObject("Float", Float.toString(resultado)));
                return;
            }

        }

        //es una operacion que tiene identificadores
        //validar que existen
        //validar tipos
        //generar codigo
        codigo += "     mov ax, ";
        if ((operando1.getTipo().equals("Int") || operando1.getTipo().equals("Float")) && operando1.getValor() != null) {
            //primer operador es un entero o un flotante
            codigo += operando1.getValor() + System.lineSeparator();
        } else {
            codigo += operando1.getValor()  + System.lineSeparator();
        }

        switch (operador.getOperador().toUpperCase()) {
            case "+":
                codigo += "     add ax, ";
                break;
            case "-":
                codigo += "     sub ax, ";
                break;
            case "*":
                codigo += "     mul ";
                break;
            case "/":
            case "DIV":
            case "MOD":
                codigo += "     div ";

        }

        if ((operando2.getTipo().equals("Int") || operando2.getTipo().equals("Float")) && operando2.getValor() != null) {
            //segunfo operador es un entero o un flotante
            codigo += operando2.getValor()  + System.lineSeparator();
        } else {
            codigo += operando2.getValor() + System.lineSeparator();
        }

        if (operador.getOperador().toUpperCase().equals("MOD")) {
            recordar_RS_DO("dx", null);
            return;
        }
        //crear rs_do resultado
        //push pila
        recordar_RS_DO("ax", null);

    }

    private boolean isOperacion(String operador) {
        return operador.equals("+") || operador.equals("-") || operador.equals("*") || operador.equals("/")
                || operador.toUpperCase().equals("DIV") || operador.toUpperCase().equals("MOD");
    }

    private int realizarOperacion(int op1, int op2, String operador) {
        switch (operador) {
            case "+":
                return op1 + op2;
            case "-":
                return op1 - op2;
            case "*":
                return op1 * op2;
            case "/":
            case "DIV":
                return op1 / op2;
            case "MOD":
                return op1 % op2;
        }
        return 0;
    }

    private Float realizarOperacion(Float op1, Float op2, String operador) {
        switch (operador) {
            case "+":
                return op1 + op2;
            case "-":
                return op1 - op2;
            case "*":
                return op1 * op2;
            case "/":
            case "DIV":
                return op1 / op2;
            case "MOD":
                return op1 % op2;
        }
        return 0F;
    }

    //public void generarCodigoAsignacion(String tipo) {

    //public void preIncDec() {

    //public void postIncDec() {

    //private void generarCodigoIncDec(RS_DO operando, RS_Operacion operador) {


    public void start_if(){
        RegistroSemantico RS_IF = new RS_IF();
        this.pila_semantica.push(RS_IF);
    }
    
    
    public void else_if() {
        
        RegistroSemantico rs = this.pila_semantica.buscar("RS_If");
        
        codigo += "     jmp " + ((RS_IF) rs).getEnd_label() + System.lineSeparator();
        codigo += " " + ((RS_IF) rs).getElse_label() + ":"  + System.lineSeparator();
    }

    public void end_if() {
        
        RegistroSemantico rs = this.pila_semantica.pop();
        while (!(rs instanceof RS_IF)) {
            rs = this.pila_semantica.pop();
        }
        codigo += " " + ((RS_IF) rs).getEnd_label() + ":"  + System.lineSeparator();
    }
    
    public void evalExp_if() {
        RS_Operador operador = generarCodigoCmp();

        RS_IF rs = (RS_IF) pila_semantica.buscar("Analisis_Semantico.RS_If");

        generarCodigoJump(operador.getOperador(), rs.getElse_label());

    }
    
    public void start_while() {
        RS_While rs = new RS_While();
        codigo += " " + rs.getStart_label() + ":"  + System.lineSeparator();
        pila_semantica.push(rs);
    }

    public void end_while() {
        RegistroSemantico rs = pila_semantica.pop();
        while (!(rs instanceof RS_While)) {
            rs = pila_semantica.pop();
        }
        codigo += "     jmp " + ((RS_While) rs).getStart_label() + System.lineSeparator();
        codigo += " " + ((RS_While) rs).getEnd_label() + ":"  + System.lineSeparator();
    }
    
    public void evalExp_While() {
        RS_Operador operador = generarCodigoCmp();

        RS_While rs = (RS_While) pila_semantica.buscar("Analisis_Semantico.RS_While");
        generarCodigoJump(operador.getOperador(), rs.getEnd_label());

    }

    private RS_Operador generarCodigoCmp() {

        RS_DataObject operando2 = (RS_DataObject) pila_semantica.pop();
        RS_Operador operador = (RS_Operador) pila_semantica.pop();
        RS_DataObject operando1 = (RS_DataObject) pila_semantica.pop();

        codigo += "     mov ax, ";
        if ((operando1.getTipo().equals("Int") || operando1.getTipo().equals("Float")) && operando1.getValor() != null) {

            codigo += operando1.getValor() + System.lineSeparator();
            
        } else {
            codigo += operando1.getTipo() + System.lineSeparator();
        }

        codigo += "     mov bx, ";

        if ((operando2.getTipo().equals("Int") || operando2.getTipo().equals("Float")) && operando2.getValor() != null) {

            codigo += operando2.getValor() + System.lineSeparator();
        } else {
            codigo += operando2.getTipo() + System.lineSeparator();
        }

        codigo += "     cmp ax, bx"  + System.lineSeparator();

        return operador;
    }

    private void generarCodigoJump(String operador, String label) {
        switch (operador) {
            case "=":
                codigo += "     jne " + label + System.lineSeparator();
                break;
            case ">":
                codigo += "     jng " + label + System.lineSeparator();
                break;
            case ">=":
                codigo += "     jnge " + label + System.lineSeparator();
                break;
            case "<":
                codigo += "     jnl " + label + System.lineSeparator();
                break;
            case "<=":
                codigo += "     jnle " + label + System.lineSeparator();
                break;
            case "<>":
                codigo += "     je " + label + System.lineSeparator();
                break;
        }
    }

    public String getCodigo() {
        return codigo;
    }

    
    

}
