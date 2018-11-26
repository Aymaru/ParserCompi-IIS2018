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
public class RS_IF extends RegistroSemantico {
    private String else_label;
    private String end_label;

    public RS_IF() {
        this.else_label = GenerarLabels.generar_label();
        this.end_label = GenerarLabels.generar_label();
    }

    public String getElse_label() {
        return else_label;
    }

    public String getEnd_label() {
        return end_label;
    }
    
    
}
