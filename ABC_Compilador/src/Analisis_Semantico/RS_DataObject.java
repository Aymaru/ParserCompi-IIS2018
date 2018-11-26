/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analisis_Semantico;

/**
 *
 * @author Orlando José Hidalgo Ramírez
 * @author Aymarú Castillo Flores
 */
public class RS_DataObject extends RegistroSemantico{
    private String tipo;
    private String valor;

    public RS_DataObject(String tipo, String valor) {
        this.tipo = tipo;
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public String getValor() {
        return valor;
    }

    
    
}
