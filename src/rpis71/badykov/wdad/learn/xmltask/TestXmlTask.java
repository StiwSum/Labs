package rpis71.badykov.wdad.learn.xmltask;

import java.io.IOException;
import java.time.LocalDate;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class TestXmlTask {

    public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
       XmlTask task = new XmlTask("C:\\Users\\USER-RAF\\Documents\\NetBeansProjects\\FirstLab\\src\\rpis71\\badykov\\wdad\\learn\\xmltask\\library.xml");
        System.err.println(task.parseDocument().toString());

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document;
        document = builder.parse("ibrary.xml");
        Book obj = new Book();-*/
       
        System.out.println(parseDocument().toString());
    }
    
    static public Document parseDocument() {
        try {
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setValidating(true);
            return domFactory.newDocumentBuilder().parse(new InputSource("C:\\Users\\USER-RAF\\Documents\\NetBeansProjects\\FirstLab\\src\\rpis71\\badykov\\wdad\\learn\\xmltask\\library.xml"));
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
            return null;
        }
    }
}
