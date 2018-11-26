/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analisis_Semantico;

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

    public Generador_Codigo() {
        this.pila_semantica = new Pila_Semantica();
        this.tabla_simbolos = new Tabla_Simbolos();
        this.generador_lbls = new GenerarLabels();
        this.codigo = "";
        this.cod_tmp = "";
        this.generar = true;
    }
    
    
    
    public void recordarIdentificador(String id) {
        pila_semantica.push(new RS_Identificador(id));
    }
    
    public void recordarTipo(String tipo) {
        pila_semantica.push(new RS_Tipo(tipo));
    }
    
    public void recordarDO(String tipo, String valor) {
        pila_semantica.push(new RS_DataObject(tipo, valor));
    }

    //public void recordarOperacion(String operador) {

    //public void guardarFuncionEnTsimbolo(String nombre) {

    //public void startFunction(){

    //public void recordarFuncion() {

    //public void guardarVariablesEnTSimbolos(String tipo) {

    //public void iniciarVar() {

    //public void iniciarCode() {

    //public void guardarConstanteEnTSimbolos(String nombre, String tipo, Object valor) {


  //  public void evalBinaria() {

   // private boolean isOperacion(String operador) {

  //  private int realizarOperacion(int op1, int op2, String operador) {

   // private Float realizarOperacion(Float op1, Float op2, String operador) {

  //  public void generarCodigoAsignacion(String tipo) {

   // public void preIncDec() {

  //  public void postIncDec() {

  //  private void generarCodigoIncDec(RS_DO operando, RS_Operacion operador) {


  //  public void start_if() {

  //  public void else_if() {

  //  public void end_if() {

   // public void start_while() {

    //private RS_Operacion generarCodigoCmp() {

    //public void evalExp_if() {


    //private void generarCodigoJump(String operador, String label) {

   // public void evalExp_While() {


    //public void end_while() {

}
