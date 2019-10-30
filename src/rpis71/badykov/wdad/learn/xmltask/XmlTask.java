package rpis71.badykov.wdad.learn.xmltask;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class XmlTask {
    private final String filePath;

    XmlTask(String filePath) {
        this.filePath = filePath;
    }
    
    public List<Reader> negligentReaders() {return null;}
    public void removeBook (Reader reader, Book book){}
    public void addBook (Reader reader, Book book){}
    public List<Book> debtBooks (Reader reader){return null;}

    int earningsTotal(String officiantSecondName, LocalDate searchingDate) throws NoSuchDayException {
        Document doc = parseDocument();
        assert doc != null;
        NodeList dates = doc.getElementsByTagName("date");
        Element dateTag = null;

        for (int i = 0; i < dates.getLength(); i++) {
            LocalDate thisDate;
            dateTag = (Element) dates.item(i);
            thisDate = parseDate(dateTag);
            if (searchingDate.isEqual(thisDate)) {
                break;
            }
            dateTag = null;
        }
        if (dateTag == null) {
           throw new NoSuchDayException("Заданный день отсутствует.");
        }
        NodeList orders = dateTag.getElementsByTagName("order");
        Element order, currentTotalCost;
        String thisSecondName;
        double totalCost = 0;
        for (int i = 0; i < orders.getLength(); i++) {
            order = (Element) orders.item(i);
            thisSecondName = order.getElementsByTagName("officiant").item(0).getAttributes().getNamedItem("secondname").getNodeValue();
            if (officiantSecondName.equals(thisSecondName)) {
                currentTotalCost = (Element) order.getElementsByTagName("totalcost").item(0);
                if (currentTotalCost != null) {
                    totalCost += Double.parseDouble((currentTotalCost.getTextContent()));
                } else {
                    int newTotalCost = 0;
                    NodeList items = order.getElementsByTagName("item");
                    Element item;
                    for (int j = 0; j < items.getLength(); j++) {
                        item = (Element) items.item(j);
                        newTotalCost += Integer.parseInt(item.getAttribute("cost"));
                    }
                    Element totalCostElement = doc.createElement("totalcost");
                    totalCostElement.appendChild(doc.createTextNode(String.valueOf(newTotalCost)));
                    order.appendChild(totalCostElement);
                    changeDocument(doc);
                    totalCost += newTotalCost;
                }
            }
        }

        return (int) totalCost;
    }

    void removeDay(LocalDate removingDate) {
        Document doc = parseDocument();
        assert doc != null;
        NodeList dates = doc.getElementsByTagName("date");
        Element dateTag;
        LocalDate thisDate;
        for (int i = 0; i < dates.getLength(); i++) {
            dateTag = (Element) dates.item(i);
            thisDate = parseDate(dateTag);
            if (removingDate.isEqual(thisDate)) {
                dateTag.getParentNode().removeChild(dateTag);
            }
        }
        changeDocument(doc);
    }

    void changeOfficiantName(String oldFirstName, String oldSecondName, String newFirstName, String newSecondName) {
        Document doc = parseDocument();
        assert doc != null;
        NodeList officiants = doc.getElementsByTagName("officiant");
        Element officiant;
        String thisFirstName, thisSecondName;
        for (int i = 0; i < officiants.getLength(); i++) {
            officiant = (Element) officiants.item(i);
            thisFirstName = officiant.getAttribute("firstname");
            thisSecondName = officiant.getAttribute("secondname");
            if (oldFirstName.equalsIgnoreCase(thisFirstName) &&
                    oldSecondName.equals(thisSecondName)) {
                officiant.setAttribute("firstname", newFirstName.toLowerCase());
                officiant.setAttribute("secondname", newSecondName.toLowerCase());
            }
        }
        changeDocument(doc);
    }

    private LocalDate parseDate(Element dateTag) {
        return LocalDate.parse(String.format("%s-%s-%s",
                dateTag.getAttribute("year"),
                dateTag.getAttribute("month"),
                dateTag.getAttribute("day")));
    }

    public Document parseDocument() {
        try {
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setValidating(true);
            return domFactory.newDocumentBuilder().parse(new InputSource(filePath));
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void changeDocument(Document doc) {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "restaurant.dtd");
            transformer.transform(new DOMSource(doc), new StreamResult(filePath));
            System.out.println("XML file updated successfully");
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }
}
