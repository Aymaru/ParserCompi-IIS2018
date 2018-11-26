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
public class RS_While extends RegistroSemantico{
    private String start_label;
    private String end_label;

    public RS_While() {
        this.start_label = GenerarLabels.generar_label();
        this.end_label = GenerarLabels.generar_label();
    }

    public String getStart_label() {
        return start_label;
    }

    public String getEnd_label() {
        return end_label;
    }
    
    
}
