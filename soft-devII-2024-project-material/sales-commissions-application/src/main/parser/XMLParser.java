package main.parser;

import main.domain.*;

import java.io.*;

import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser extends Parser {
 
    @Override
    public Associate parseAssociateFromFile(File file) throws IOException {

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        try{
            //Parse file
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            document.getDocumentElement().normalize();

            List<Receipt> receipts = new ArrayList<Receipt>();

            //Get name and AFM
            String name = getDataFromDocument(document, "Name");
            String afm = getDataFromDocument(document, "AFM");

            //Get all receipts
            NodeList receiptNodes = document.getElementsByTagName("Receipt");

            //extract receipts
            for(int index = 0; index < receiptNodes.getLength(); index ++) {

				Node attribute = receiptNodes.item(index);
				if (attribute.getNodeType() == Node.ELEMENT_NODE) {

					Element extractedAttribute = (Element) attribute;

                    Address address = new Address(
                        getDataFromElement(extractedAttribute, "Country"),
                        getDataFromElement(extractedAttribute, "City"),
                        getDataFromElement(extractedAttribute, "Street"),
                        Integer.valueOf(getDataFromElement(extractedAttribute, "Number"))
                    );

                    Company company = new Company(
                        getDataFromElement(extractedAttribute,"Company"),
                        address
                    );

                    receipts.add(new Receipt(
                        Integer.parseInt(getDataFromElement(extractedAttribute, "ReceiptID")),
                        getDataFromElement(extractedAttribute, "Date"),
                        ProductType.valueOf(getDataFromElement(extractedAttribute, "Kind")),
                        Double.parseDouble(getDataFromElement(extractedAttribute, "Sales")),
                        Integer.parseInt(getDataFromElement(extractedAttribute, "Items")),
                        company
                    ));
				}
			}
            return new Associate(name, afm, receipts, file);

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private String getDataFromElement(Element element, String tag){
        return element.getElementsByTagName(tag).item(0).getTextContent().trim();
    }

    private String getDataFromDocument(Document document, String tag){
        return document.getElementsByTagName(tag).item(0).getTextContent().trim();
    }
}


