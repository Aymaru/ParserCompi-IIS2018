/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analisis_Semantico;

import java.util.ArrayList;

/**
 *
 * @author orlandojose
 */
public class Simbolo 
{
    private String nombre; //var_funcionx
    private String tipo;
    private String scope; //global, constante, funcion, locales
    private String funcion;

    private ArrayList<Simbolo> parametros;
    private Object valor;
    private int linea;

    //funcion
    public Simbolo(String nombre, String tipo, String scope, ArrayList<Simbolo> parametros, int linea) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.scope = scope;
        this.parametros = parametros;
        this.linea = linea;
    }

    //variable global
    public Simbolo(String nombre, String tipo, String scope, int linea) {
        
        this.nombre = nombre;
        this.tipo = tipo;
        this.scope = scope;
        this.linea = linea;
    }

    //variable local
    public Simbolo(String nombre, String tipo, String scope, String funcion, int linea) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.scope = scope;
        this.funcion = funcion;
        this.linea = linea;
    }
    
    //const local
    public Simbolo(String nombre, String tipo, String scope, String funcion, Object valor, int linea) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.scope = scope;
        this.funcion = funcion;
        this.linea = linea;
        this.valor = valor;
    }

    
    // Constante 
    public Simbolo(String nombre, String tipo, String scope, Object valor, int linea) {
        this.nombre = nombre;
        this.scope = scope;
        this.valor = valor;
        this.tipo = tipo;
        this.linea = linea;
    }
    
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public ArrayList<Simbolo> getParametros() {
        return parametros;
    }

    public void setParametros(ArrayList<Simbolo> parametros) {
        this.parametros = parametros;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    
    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

}
