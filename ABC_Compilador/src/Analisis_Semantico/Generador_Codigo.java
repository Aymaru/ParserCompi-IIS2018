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
    
    public void guardar_funcion_TS(String id, String tipo){
        
        RegistroSemantico primero;
        RegistroSemantico segundo;
        ArrayList<Simbolo> parametros = new ArrayList<>();        
        primero = pila_semantica.pop();
        
        while(primero.getClass().getName().equals("Analisis_Semantico.RS_Identificador") && !((RS_Identificador) primero).getNombre().equals(id)){
            segundo = pila_semantica.pop();
            
            Simbolo tmp = new Simbolo(((RS_Identificador) primero).getNombre(), ((RS_Tipo) segundo).getTipo(), id, 0);
            parametros.add(tmp);
            
            primero = pila_semantica.pop();
        }
        
         tabla_simbolos.agregar_funcion(id, tipo, parametros, 0);
    }
    
    public void recordar_identificador(String id) {
        System.out.println("recordando id");
        pila_semantica.push(new RS_Identificador(id));
    }
    
    public void recordar_tipo(String tipo) {
        System.out.println("recordando tipo");
        pila_semantica.push(new RS_Tipo(tipo));
    }
    
    public void recordar_RS_DO(String tipo, String valor) {
        System.out.println("recordando DO "+valor);
        
        if(valor == null && !registros.contains(tipo)){
            Simbolo s = tabla_simbolos.buscarSimbolo(tipo);
            if(s == null)
                ScannerABC.errores.add(new ErrorToken(tipo,"ERROR_SEMANTICO","Error Semántico: Funcion " + tipo + " ya declarada. Linea: -" , 0));            
        }
        pila_semantica.push(new RS_DataObject(tipo, valor));
    }

    public void recordar_operador(String operador) {
        System.out.println("recordando operador");
        System.out.println(operador);
         pila_semantica.push(new RS_Operador(operador));
    }

    public void guardar_variables_TS(String tipo, int linea) {
        System.out.println("guardando variable");
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
       /*
    public void guardar_constantes_TS(String nombre, String tipo, Object valor, int linea) {
        
        boolean resultado = tabla_simbolos.agregar_const_global(nombre, tipo, valor,linea);
        
        RegistroSemantico top = pila_semantica.top();
        
        generar = resultado;
        codigo += ((RS_Identificador) top).getNombre();

        switch (tipo.toUpperCase()) {
            case "INT":
                codigo += "\t dd "+ valor.toString() + System.lineSeparator();
                break;
            case "SHORTINT":
                codigo += "\t  dw "+ valor.toString() + System.lineSeparator();
                break;
            case "LONGTINT":
                codigo += "\t dw 3  "+ valor.toString() + System.lineSeparator();
                break;
            case "CHAR":
                codigo += "\t db "+ valor.toString() + System.lineSeparator();
                break;
            case "BOOLEAN":
                codigo += "\t db "+ valor.toString() + System.lineSeparator();
                break;
            case "REAL":
                codigo += "\t dd "+ valor.toString() + System.lineSeparator();
                break;
            case "STRING":
                codigo += "\t dw 30 "+ valor.toString() + System.lineSeparator();
                break;
            default:
                break;
        }
        
        pila_semantica.pop();
    }*/
    
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

    public void eval_exp_binaria() {
        System.out.println("evaluando exp bin");
        
        RegistroSemantico operando2 = pila_semantica.pop();
        RegistroSemantico operador =  pila_semantica.pop();
        RegistroSemantico operando1 = pila_semantica.pop();   
        
        System.out.println("punto 1");
        
        
        
        /*if(operando1 == null)
            System.out.println("orlando imvecil");
        System.out.println(operador.getOperador());
        String s = operador.getOperador();
        System.out.println("punto 1.5");
        if (!isOperacion(s)) {
            System.out.println("punto 1.1");
            pila_semantica.push(operando1);
            pila_semantica.push(operador);
            pila_semantica.push(operando2);
            return;
        }
        System.out.println("punto 2");
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
*/
    }

    private boolean isOperacion(String operador) {
        System.out.println("punto 1.1.1");
        return operador.equals("+") || operador.equals("-") || operador.equals("*") || operador.equals("/")
                || operador.toUpperCase().equals("O_DIV") || operador.toUpperCase().equals("O_MOD");
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

    public void generarCodigoAsignacion() {
        RegistroSemantico valorAsignar = (RS_DataObject) pila_semantica.pop();
        RS_Operador operadorAsignacion = (RS_Operador) pila_semantica.pop();
        RegistroSemantico identificador =  pila_semantica.pop();
        
        Simbolo tmp; 
        
        if(identificador.getClass().getName().equals("Analisis_Semantico.RS_Identificador")){            
            tmp = tabla_simbolos.buscarSimbolo(((RS_Identificador)identificador).getNombre());
            
            if(tmp == null){
                System.out.println("Identificador no definido..."); 

                tabla_simbolos.agregar_var_global(((RS_Identificador)identificador).getNombre(),"error".toUpperCase(),0);
                generar = false;

            } else if(tmp.getScope().toLowerCase().equals("constante")) {
                System.out.println("Identificador es constante...");

                generar = false;
            } else if (tmp.getScope().toLowerCase().equals("funcion")){
                System.out.println("Identificador es funcion...");

                generar = false;
            }else{
                if(valorAsignar.getClass().getName().equals("Analisis_Semantico.RS_DataObject")){
                    System.out.println(tmp.getTipo());
                    System.out.println(((RS_DataObject) valorAsignar).getTipo());
                    if (tmp.getTipo().toUpperCase().equals(((RS_DataObject) valorAsignar).getTipo().toUpperCase())){
                        
                        codigo += "mov " + ((RS_Identificador) identificador).getNombre() + ", ";
                        codigo += ((RS_DataObject) valorAsignar).getValor() + System.lineSeparator() + System.lineSeparator();
                    } else {
                        System.out.println("Error: Tipos incorrectos 1");
                    }
                } else {
                    Simbolo tmp2 = tabla_simbolos.buscarSimbolo(((RS_Identificador) valorAsignar).getNombre());
                    if (tmp.getTipo().equals(tmp2.getTipo())){
                        
                        codigo += "mov ax, " + ((RS_Identificador) valorAsignar).getNombre() + System.lineSeparator();
                        codigo += "mov " + ((RS_Identificador) identificador).getNombre() + ", ax" + System.lineSeparator() + System.lineSeparator();
                    } else {
                        System.out.println("Error: Tipos incorrectos 2");
                    }
                    
                }
            } 
        } 
        
    }/*else if (!tmp.getTipo().toLowerCase().equals(tipo.toLowerCase())) {
                System.out.println("Identificador no es del tipo correcto...");

                generar = false;*/

    //public void preIncDec() {

    //public void postIncDec() {

    //private void generarCodigoIncDec(RS_DO operando, RS_Operacion operador) {


    public void start_if(){
        RegistroSemantico RS_IF = new RS_IF();
        this.pila_semantica.push(RS_IF);
    }
    
    
    public void else_if() {
                    System.out.println("else if");
        RegistroSemantico rs = this.pila_semantica.buscar("Analisis_Semantico.RS_IF");
        
        codigo += "     jmp " + ((RS_IF) rs).getEnd_label() + System.lineSeparator();
        codigo += " " + ((RS_IF) rs).getElse_label() + ":"  + System.lineSeparator();
    }

    public void end_if() {
                    System.out.println("end if");
        RegistroSemantico rs = this.pila_semantica.pop();
        while (!(rs instanceof RS_IF)) {
            rs = this.pila_semantica.pop();
        }
        codigo += " " + ((RS_IF) rs).getEnd_label() + ":"  + System.lineSeparator();
    }
    
    public void eval_exp_if() {
        System.out.println("evaluando if");
        RS_Operador operador = generarCodigoCmp();
        System.out.println("evaluando if 1");
        RS_IF rs = (RS_IF) pila_semantica.buscar("Analisis_Semantico.RS_IF");
        System.out.println("evaluando if 2");
        System.out.println(operador.getOperador());
        if (rs == null)
            System.out.println("ES NULL");
        generarCodigoJump(operador.getOperador(), rs.getElse_label());
        System.out.println("evaluando if 3");
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
    
    public void eval_exp_while() {
        System.out.println("evaluando while");
        RS_Operador operador = generarCodigoCmp();

        RS_While rs = (RS_While) pila_semantica.buscar("Analisis_Semantico.RS_While");
        generarCodigoJump(operador.getOperador(), rs.getEnd_label());

    }

    private RS_Operador generarCodigoCmp() {
        System.out.println("generando codigo cmp");
        RegistroSemantico operando2 = pila_semantica.pop();
        RS_Operador operador = (RS_Operador) pila_semantica.pop();
        RegistroSemantico operando1 =  pila_semantica.pop();
        System.out.println("pase 1");
        codigo += "     mov ax, ";
        
        if (operando1.getClass().getName().equals("Analisis_Semantico.RS_DataObject")) {
            System.out.println("pase 1.1");
            codigo += ((RS_DataObject) operando1).getValor() + System.lineSeparator();
            System.out.println("pase 1.2");
        } else {
            if (tabla_simbolos.buscarSimbolo(((RS_Identificador) operando1).getNombre()) != null){
               System.out.println("pase 1.3");
                codigo += ((RS_Identificador) operando1).getNombre()+ System.lineSeparator(); 
                System.out.println("pase 1.4");
            }
            else{
                System.out.println("pase 1.5");
                System.out.println(((RS_Identificador) operando1).getNombre());
                tabla_simbolos.agregar_var_global(((RS_Identificador) operando1).getNombre(), "error", 0);
                System.out.println("pase 1.6");
                generar = false;
            }
        }
        System.out.println("pase 2");
        codigo += "     mov bx, ";

        if (operando2.getClass().getName().equals("Analisis_Semantico.RS_DataObject")) {
            System.out.println("pase 2.1");
            codigo += ((RS_DataObject) operando2).getValor() + System.lineSeparator();
            System.out.println("pase 2.2");            
        } else {
            if(tabla_simbolos.buscarSimbolo(((RS_Identificador) operando2).getNombre()) != null){
                            System.out.println("pase 2.3");
                codigo += ((RS_Identificador) operando2).getNombre() + System.lineSeparator();
                            System.out.println("pase 2.4");
            } else {
                            System.out.println("pase 2.5");
                tabla_simbolos.agregar_var_global(((RS_Identificador) operando2).getNombre(), "error", 0);
                            System.out.println("pase 2.6");
                generar = false;
            }
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
        if (generar)
            return codigo;
        return ("Hay errores semanticos, no se genera el codigo");
    }

    public Tabla_Simbolos getTabla_simbolos() {
        return tabla_simbolos;
    }

    
    

}
