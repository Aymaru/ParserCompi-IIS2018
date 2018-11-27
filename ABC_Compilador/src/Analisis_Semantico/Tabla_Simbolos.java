/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analisis_Semantico;

import static Compilador.Analizador_Sintactico.tablaSimbolos;
import Compilador.ErrorToken;
import Compilador.ScannerABC;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author ayma-93
 */
public class Tabla_Simbolos {
    
    
    private HashMap<String,Simbolo> tabla_simbolos;
    
    
 
    public Tabla_Simbolos() {
        this.tabla_simbolos = new HashMap<>();
    }
    
       //agregar simbolo
    // *agregar funcion
    
    public boolean agregar_funcion(String nombre, String tipo, ArrayList<Simbolo> parametros, int linea){
        
        if(tabla_simbolos.containsKey(nombre)){
            
            ScannerABC.errores.add(new ErrorToken(nombre,"ERROR_SEMANTICO","Error Semántico: Funcion " + nombre + " ya declarada. Linea: " + (linea), linea));
            return false;
        }
        
            
        Simbolo tmp = new Simbolo(nombre.toUpperCase(), tipo.toUpperCase(), "funcion", parametros , linea);
        tabla_simbolos.put(nombre, tmp);
        
        return true;
    }
    
    // *agregar var global
    
    public boolean agregar_var_global(String nombre, String tipo, int linea){
        if(tabla_simbolos.containsKey(nombre)){
            System.out.println("error sem");
            ScannerABC.errores.add(new ErrorToken(nombre,"ERROR_SEMANTICO","Error Semántico: Variable " + nombre + " ya declarada. Linea: " + (linea), linea));
            return false;
        }
        Simbolo tmp = new Simbolo(nombre.toUpperCase(), tipo.toUpperCase(), "variable_global", linea);
        
        tabla_simbolos.put(nombre, tmp);
        
        
        return true;
    }
    
    
    // *agregar const global
    
    public Boolean agregar_const_global(String nombre, String tipo, Object valor,  int linea)
    {
        if(tabla_simbolos.containsKey(nombre))
        {
            ScannerABC.errores.add(new ErrorToken(nombre,"ERROR_SEMANTICO","Error Semántico: Constante " + nombre + " ya declarada. Linea: " + (linea), linea));
            return false;
        }
      
        Simbolo s = new Simbolo(nombre.toUpperCase(), tipo.toUpperCase(), "constante", valor, linea);
        
        tabla_simbolos.put(nombre, s);
        return true;
 
    }
    
    
    // *agregar var local
    
    
    
    
    // *agregar const local
    
    
    
    
    // *elimiar
    public Boolean eliminarSimblolo(String key)
    {
        Simbolo tmp = tabla_simbolos.remove(key);
        if(tmp!=null)
            return true;
        return false;
    }
    
    
    // *buscar 
    public Simbolo buscarSimbolo(String key)
    {
        Simbolo tmp = tabla_simbolos.get(key);
        return tmp;
    }
    
    
    
    
    @Override
    public String toString() {
        String result = "Tabla de Simbolos \n-------------------\n";
        /*Iterator it = tabla_simbolos.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            result += pair.getValue().toString() + "\n";
        }*/
        for (Map.Entry<String, Simbolo> entry : tabla_simbolos.entrySet()) {
            Simbolo value = entry.getValue();
            result += value.getNombre()+ "  tipo: "+value.getTipo()+"\n";
            
            if(value.getScope().equals("funcion")){
                System.out.println("SIZE===="+value.getParametros().size());
                for (int i = 0; i < value.getParametros().size(); i++) {
                    Simbolo get = tablaSimbolos.get(i);
                    result += get.getNombre()+ "  tipo: "+get.getTipo()+"\n";
                }
            }
            
        }
        return result;
    }
    
}
