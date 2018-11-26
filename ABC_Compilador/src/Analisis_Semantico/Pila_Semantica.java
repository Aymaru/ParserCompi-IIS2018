/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analisis_Semantico;

import java.util.ArrayList;

/**
 *
 * @author ayma-93
 */
public class Pila_Semantica {
    
    private ArrayList<RegistroSemantico> pila;

    public Pila_Semantica() {
        this.pila = new ArrayList<>();
    }
    
    public void push(RegistroSemantico RS){
        pila.add(RS);
    }
    
    public RegistroSemantico pop(){
        if (pila.size()>0){
            return pila.remove(pila.size()-1);
        }
        return null;
    }
    
    public RegistroSemantico top(){
        if (pila.size()>0){
            return pila.get(pila.size()-1);
        }
        return null;
    }
    
    public RegistroSemantico buscar(String nombre_tipo){
        for (int i = pila.size()-1; i >= 0; i--) {
            RegistroSemantico tmp = pila.get(i);
            if(tmp.getClass().getName().equals(nombre_tipo)){
                return tmp;
            }
        }
        return null;
    }
    
    
}
