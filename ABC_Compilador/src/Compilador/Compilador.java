/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compilador;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.util.Scanner;


/**
 *
 * @author orlandojose
 */
public class Compilador 
{
    public final static int GENERAR = 1;
    public final static int EJECUTAR = 2;
    public final static int SALIR = 3;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        java.util.Scanner in = new Scanner(System.in);
        int valor = 0;
    
        while(valor!=3)
        {
            System.out.println("Elija una opcion: ");
            System.out.println("1) Generar");
            System.out.println("2) Ejecutar");
            System.out.println("3) Salir");
            System.out.print("Opcion: ");
            valor = in.nextInt();
            switch (valor) 
            {
                case GENERAR: 
                {
                    ScannerABC.errores.clear();
                    String path = System.getProperty("user.dir") + File.separator + "src"+ File.separator +"Compilador"+ File.separator +"Scanner"+ File.separator +"Analizador_Lexico.flex";


                    //ScannerABC x = new ScannerABC();

                    //x.generarLexer(path);

                    //String archLexico = System.getProperty("user.dir")+ "/src/Compilador/Scanner/Analizador_Lexico.flex";
                    //String archSintactico = System.getProperty("user.dir")+ "/src/Compilador/Parser/Analizador_Sintactico.cup";
                    String archLexico = System.getProperty("user.dir")+ File.separator + "src"+ File.separator +"Compilador"+ File.separator +"Analizador_Lexico.flex";
                    String archSintactico = System.getProperty("user.dir")+ File.separator + "src"+ File.separator +"Compilador"+ File.separator +"Analizador_Sintactico.cup";
                    String[] alexico = {archLexico};
                    String[] asintactico = {"-parser", "Analizador_Sintactico", archSintactico};       
                    jflex.Main.main(alexico);
                    try 
                    {   
                        java_cup.Main.main(asintactico);
                        java_cup.Main.dump_tables();
                        java_cup.Main.dump_grammar();
                        
                    } 
                    catch (Exception ex) 
                    {
                         try 
                         { 
                        java_cup.Main.dump_tables();
                        java_cup.Main.dump_grammar();
                         }
                         catch(Exception e)
                         {
                             
                         }
                        //Logger.getLogger(EjemploCUP.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    boolean mvAL = moverArch("Analizador_Lexico.java");
                    boolean mvAS = moverArch("Analizador_Sintactico.java");
                    boolean mvSym= moverArch("sym.java");
                    if(mvAL && mvAS && mvSym)
                    {
                        System.exit(0);
                    }
                    System.out.println("Generado!");
                    break;
                }
                case EJECUTAR:
                {
                    Scanner reader = new Scanner(System.in);  // Reading from System.in
                    System.out.println("Digite la ruta del archivo fuente (Sin extención): ");
                    String nombreArchivo = reader.nextLine();

                    ScannerABC s = new ScannerABC();
                    ScannerABC.errores.clear();
                    //System.out.println("--Alpha Reached--");
                    try
                    {   
                        ScannerABC.errores.clear();
                        ScannerABC.nombreTokens.clear();
                        ScannerABC.tokenslist.clear();
                        
                        FileReader fr = new FileReader(nombreArchivo+"");
                        BufferedReader bf = new BufferedReader(fr);

                        Analizador_Lexico lexico = new Analizador_Lexico(bf);

                        //System.out.println("--Bravo Reached--");
                        //System.out.println(ScannerABC.nombreTokens.size());

                        /*lexico.
                        File file=new File(System.getProperty("user.dir")+ "/src/Compilador/Analizador_Lexico.flex");
                        jflex.Main.generate(file);
                        */
                        Analizador_Sintactico sintactico = new Analizador_Sintactico(lexico);
                        //sintactico.debug_parse();
                        //System.out.println("--Charlie Reached--");
                        sintactico.parse();

                        /*
                        System.out.println("--Charlie Reached--");
                        System.out.println("1----" + ScannerABC.nombreTokens.size());

                        System.out.println("--Delta Reached--");
                        System.out.println(sintactico.resultado);

                        System.out.println("--Echo Reached--");
                        System.out.println("Cantidad de Errores: " + ScannerABC.errores.size());

                        System.out.println("--Foxtrot Reached--");
                        */
                        //System.out.println(ScannerABC.imprimir());

                        fr.close();

                        String codigoEnsamblador = sintactico.getGenerador().getCodigo();    ///Cambiar por string con codigo de ensamblador
                        File ensamblador = new File(nombreArchivo+".asm");

                        BufferedWriter writer = new BufferedWriter(new FileWriter(ensamblador));
                        writer.write(codigoEnsamblador);
                        writer.close();


                        XML.XML.writeXML(s,nombreArchivo);

                        Source xml = new StreamSource(new File(nombreArchivo+".xml"));
                        Source xslt = new StreamSource(System.getProperty("user.dir")+"/style.xsl");

                        XML.XML.convertXMLToHTML(xml, xslt, nombreArchivo);
                        File htmlFile = new File(nombreArchivo+".html");
                        Desktop.getDesktop().browse(htmlFile.toURI());

                    }
                    catch(Exception e)
                    {
                        System.out.println("--Hit Alpha--");
                        System.out.println(e.toString());
                        System.out.println(e.getCause());
                        System.out.println(ScannerABC.imprimirErrores());
                    }
                    break;
                    /*
                    String nombreArchivo = "prueba";
                    ScannerABC s = new ScannerABC();
                    ScannerABC.errores.clear();
                    System.out.println("--Alpha Reached--");
                    try
                    {   
                        ScannerABC.errores.clear();
                        ScannerABC.nombreTokens.clear();
                        ScannerABC.tokenslist.clear();
                        FileReader fr = new FileReader(nombreArchivo+"");
                        BufferedReader bf = new BufferedReader(fr);
                        
                        Analizador_Lexico lexico = new Analizador_Lexico(bf);

                        System.out.println("--Bravo Reached--");
                        System.out.println(ScannerABC.nombreTokens.size());

                        /*lexico.
                        File file=new File(System.getProperty("user.dir")+ "/src/Compilador/Analizador_Lexico.flex");
                        jflex.Main.generate(file);
                        
                        Analizador_Sintactico sintactico = new Analizador_Sintactico(lexico);
                        //sintactico.debug_parse();
                        //System.out.println("--Charlie Reached--");
                        sintactico.parse();

                        System.out.println("--Charlie Reached--");
                        System.out.println("1----" + ScannerABC.nombreTokens.size());

                        System.out.println("--Delta Reached--");
                        System.out.println(sintactico.resultado);

                        System.out.println("--Echo Reached--");
                        System.out.println("Cantidad de Errores: " + ScannerABC.errores.size());

                        System.out.println("--Foxtrot Reached--");
                        System.out.println(ScannerABC.imprimir());
                        
                        fr.close();
                        
                        XML.XML.writeXML(s,nombreArchivo);

                        Source xml = new StreamSource(new File(nombreArchivo+".xml"));
                        Source xslt = new StreamSource(System.getProperty("user.dir")+"/style.xsl");

                        XML.XML.convertXMLToHTML(xml, xslt, nombreArchivo);
                        File htmlFile = new File(nombreArchivo+".html");
                        Desktop.getDesktop().browse(htmlFile.toURI());
                        
                    }
                    catch(Exception e)
                    {
                        System.out.println("--Hit Alpha--");
                        System.out.println(e.toString());
                        System.out.println(e.getCause());
                        System.out.println(ScannerABC.imprimirErrores());
                    }
                    break;
                    */
                }
                case SALIR:
                    break;
                default: 
                {
                    System.out.println("Opcion no valida!");
                    break;
                }
            }
        }
        //Analizador_Sintactico = 
        
        /*
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Digite la ruta del archivo fuente (Sin extención): ");
        String nombreArchivo = reader.nextLine();
        try
        {
            x.probarLexerFile(nombreArchivo);
            Source xml = new StreamSource(new File(nombreArchivo+".xml"));
            Source xslt = new StreamSource(System.getProperty("user.dir")+"/style.xsl");

            XML.XML.convertXMLToHTML(xml, xslt, nombreArchivo);
            File htmlFile = new File(nombreArchivo+".html");
            Desktop.getDesktop().browse(htmlFile.toURI());
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
        }
                */
        
        
        
    }
    public static boolean moverArch(String archNombre) {
        boolean efectuado = false;
        File arch = new File(archNombre);
        if (arch.exists()) {
            System.out.println("\n*** Moviendo " + arch + " \n***");
            String nuevoDir = System.getProperty("user.dir") + File.separator +"src" + File.separator + "Compilador"+ File.separator + arch.getName();
            File archViejo = new File(nuevoDir);
            archViejo.delete();
            if (arch.renameTo(new File(nuevoDir))) {
                System.out.println("\n*** Generado " + archNombre + "***\n");
                efectuado = true;
            } else {
                System.out.println("\n*** No movido " + archNombre + " ***\n");
            }

        } else {
            System.out.println("\n*** Codigo no existente ***\n");
        }
        return efectuado;
    }
}
