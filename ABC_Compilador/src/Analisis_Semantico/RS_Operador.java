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
public class RS_Operador extends RegistroSemantico{
    public String operador;

    public RS_Operador(String operador) {
        this.operador = operador;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }
    
    
}
