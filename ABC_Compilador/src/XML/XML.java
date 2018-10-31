/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XML;

import Compilador.ScannerABC;
import Compilador.Identificador;
import Compilador.ErrorToken;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author orlandojose
 */
public class XML 
{
    public static void writeXML(ScannerABC scanner, String nombreArchivo) 
    {
        try 
        {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("Compilador");
            doc.appendChild(rootElement);

             
            Element tokens = doc.createElement("Tokens");
            rootElement.appendChild(tokens);
            
            for(Identificador token: scanner.getTokenslist())
            {
                // user elements
                Element userNode = doc.createElement("Token");
		tokens.appendChild(userNode);

                /*
		// set attribute to user element
	        Attr attr = doc.createAttribute("ID");
                attr.setValue(user.getId());
		userNode.setAttributeNode(attr);
                */
                
                // name elements
                Element nombre = doc.createElement("Nombre");
                nombre.appendChild(doc.createTextNode(token.getNombre()));
		userNode.appendChild(nombre);

		// password elements
		Element tipo = doc.createElement("Tipo");
		tipo.appendChild(doc.createTextNode(token.getTipoToken().name()));
		userNode.appendChild(tipo);
                
                Element apariciones = doc.createElement("Apariciones");
		apariciones.appendChild(doc.createTextNode(token.getApariciones()));
		userNode.appendChild(apariciones);
            }
            
            Element errors = doc.createElement("Errores");
            rootElement.appendChild(errors);
            
            for(ErrorToken error: scanner.getErrores())
            {
                
                Element nodoError = doc.createElement("Error");
                errors.appendChild(nodoError);

                Element nombreError = doc.createElement("Tipo");
                nombreError.appendChild(doc.createTextNode(error.getTipoError()));
                nodoError.appendChild(nombreError);
                
                Element nombreToken = doc.createElement("Token");
                nombreToken.appendChild(doc.createTextNode(error.getToken()));
                nodoError.appendChild(nombreToken);
                
                Element apariciones = doc.createElement("Apariciones");
                apariciones.appendChild(doc.createTextNode(error.getApariciones()));
                nodoError.appendChild(apariciones);
                
                Element detalleError = doc.createElement("Detalle");
                detalleError.appendChild(doc.createTextNode(error.getDetalle()));
                nodoError.appendChild(detalleError);
            }
            
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(nombreArchivo+".xml"));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);
            //System.out.println("File saved!");
        } 
        catch (ParserConfigurationException | TransformerException pce) 
        {
            pce.printStackTrace();
        }
    }
    
    public static void convertXMLToHTML(Source xml, Source xslt, String nombreArchivo) 
    {
	StringWriter sw = new StringWriter();
 
	try 
        {
            FileWriter fw = new FileWriter(nombreArchivo+".html");
        
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer trasform = tFactory.newTransformer(xslt);
            trasform.transform(xml, new StreamResult(sw));
            fw.write(sw.toString());
            fw.close();

            System.out.println(nombreArchivo+".html generado correctamente.");
 
	} 
        catch (IOException | TransformerConfigurationException e) 
        {
            e.printStackTrace();
	} 
        catch (TransformerFactoryConfigurationError e) 
        {
            e.printStackTrace();
	} 
        catch (TransformerException e) 
        {
            e.printStackTrace();
	}
    }
    
}
