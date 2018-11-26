/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compilador;

/**
 *
 * @author orlandojose
 */
public class Simbolo 
{
    public Object name;
    public Object type;
    public String ambito;
    public String linea;

    public Simbolo(Object name, Object type,String ambito, String linea) {
        this.name = name;
        this.type = type;
        this.ambito = ambito;
        this.linea = linea;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }
    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public String getAmbito() {
        return ambito;
    }

    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    @Override
    public String toString() {
        return "Nombre = " + name + ", Tipo = " + type + ", Ambito = " + ambito + ","+ linea;
    }
}
