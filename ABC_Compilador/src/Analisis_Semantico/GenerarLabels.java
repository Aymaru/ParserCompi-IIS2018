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
public class GenerarLabels {
    
    private static int contador = 0;
    
    public static String generar_label(){
        contador++;
        return "label_" + contador;
    }
}
