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
        registros = new ArrayList();
        registros.add("ax");
        registros.add("bx");
    }
    
    public void recordarIdentificador(String id) {
        pila_semantica.push(new RS_Identificador(id));
    }
    
    public void recordarTipo(String tipo) {
        pila_semantica.push(new RS_Tipo(tipo));
    }
    
    public void recordarDO(String tipo, String valor) {
        if(valor == null && !registros.contains(tipo)){
            Simbolo s = tabla_simbolos.buscarSimblolo(tipo);
            if(s == null)
                ScannerABC.errores.add(new ErrorToken(tipo,"ERROR_SEMANTICO","Error Semántico: Funcion " + tipo + " ya declarada. Linea: -" , 0));            
        }
        pila_semantica.push(new RS_DataObject(tipo, valor));
    }

    public void recordarOperador(String operador) {
         pila_semantica.push(new RS_Operador(operador));
    }

    //public void guardarFuncionEnTsimbolo(String nombre) {

    //public void startFunction(){

    //public void guardarVariablesEnTSimbolos(String tipo) {

    //public void iniciarVar() {

    //public void iniciarCode() {

    //public void guardarConstanteEnTSimbolos(String nombre, String tipo, Object valor) {

    //public void evalBinaria() {

    //private boolean isOperacion(String operador) {

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

    //public void start_if() {

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
    //public void start_while() {

    //private RS_Operacion generarCodigoCmp() {

    //public void evalExp_if() {

    //private void generarCodigoJump(String operador, String label) {

    //public void evalExp_While() {
    
    //public void end_while() {

}
