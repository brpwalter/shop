import java.io.*;
import org.xml.sax.*;
import org.apache.xerces.parsers.*;

public class ParserDemo {

    public void performDemo(String uri) {
	System.out.println("Parsing XML file: " +uri);

	try {
	    XMLReader parser = new SAXParser();
	    parser.parse(uri);
	} catch (IOException e) {
	    System.out.println("Error reading URI: " +e.getMessage());
	} catch (SAXParseException e) {
	    System.out.println("Error parsing: " +e.getMessage() 
			       +"Line: " + e.getLineNumber() 
			       +" Column: " +e.getColumnNumber());
	} catch (SAXException e) {
	    System.out.println("Error parsing: " +e.getMessage());
	}
    }

    public static void main(String[] args) {
	String uri = args[0];
	ParserDemo parserDemo = new ParserDemo();
	parserDemo.performDemo(uri);
    }
}
