package de.xslt;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class XSLTDemo extends HttpServlet {
    public void doGet (HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException {       
	res.setContentType("text/html");
	PrintWriter out = res.getWriter();
	Document doc=createDocument();
	Element catalog = doc.createElement("catalog");
	Element article = doc.createElement("article");
	Element name = doc.createElement("name");
	Element price = doc.createElement("price");
	name.appendChild(doc.createTextNode("Java Servlets und ServerPages"));
	price.appendChild(doc.createTextNode("40,88"));
	article.appendChild(name);
	article.appendChild(price);
	catalog.appendChild(article);
	Element article2 = doc.createElement("article");
	Element name2 = doc.createElement("name");
	Element price2 = doc.createElement("price");
	name2.appendChild(doc.createTextNode("PHP 4 + MySQL"));
	price2.appendChild(doc.createTextNode("30,75"));
	article2.appendChild(name2);
	article2.appendChild(price2);
	catalog.appendChild(article2);
	doc.appendChild(catalog);
	transform(doc, out, "stylesheet.xsl");
    }

    /** 
     * transforms a stylesheet with a node to             
     * the PrintWriter 
     * @param node the node to transform the stylesheet with     
     * @param PrintWriter the PrintWriter to write the transformed  document to
     * @param stylesheet the Stylesheet to transform     
     */          
    private void transform (Node node, PrintWriter out, String stylesheet) {           
	try {           
	    TransformerFactory tFactory =  TransformerFactory.newInstance();                      
	    Transformer transformer = tFactory.newTransformer(new StreamSource(getServletContext().getRealPath("/styles/" +stylesheet)));  
	    transformer.transform (new DOMSource (node), new StreamResult(out));                          
	} catch (TransformerConfigurationException e) {       
	    out.println ("Error in stylesheet<BR>");           
	    out.println (e.getMessage ());             
	} catch (Exception e) {       
	    out.println ("Error while processing<BR>");    
	    out.println (e.getMessage ());            
	}             
    } // end transform

    /** 
     * creates an empty DOM Document      
     * @returns Document the new empty DOM Document or null if              * an error occures        
     */          
    public Document createDocument () {  
	try {           
	    return DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();          
	} catch (javax.xml.parsers.ParserConfigurationException e) {  
            return null;              
	}            
    }
}
