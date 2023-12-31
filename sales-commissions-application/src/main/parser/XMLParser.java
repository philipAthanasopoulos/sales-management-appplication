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
    //TODO: extract method
    protected void setAssociateReceipts() throws Exception{
        Document document = getDocumentFromFile(fileToParse);

        //Get all receipts
        ArrayList<Receipt> receipts = new ArrayList<>();
        NodeList receiptNodes = document.getElementsByTagName("Receipt");

        //extract receipts
        for(int index = 0; index < receiptNodes.getLength(); index++) {

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

                Receipt receipt = new Receipt(
                    Integer.parseInt(getDataFromElement(extractedAttribute, "ReceiptID")),
                    getDataFromElement(extractedAttribute, "Date"),
                    ProductType.valueOf(getDataFromElement(extractedAttribute, "Kind")),
                    Double.parseDouble(getDataFromElement(extractedAttribute, "Sales")),
                    Integer.parseInt(getDataFromElement(extractedAttribute, "Items")),
                    company
                );
                
                receipts.add(receipt);
            }
        }
        associateToParse.setReceipts(receipts);
    }

    @Override
    protected void setAssociateInfo() throws Exception{
        Document document = getDocumentFromFile(fileToParse);
        String name = getDataFromDocument(document, "Name");
        String afm = getDataFromDocument(document, "AFM");
        associateToParse.setName(name);
        associateToParse.setAfm(afm);
        associateToParse.setPersonalFile(fileToParse);
    }

    private Document getDocumentFromFile(File fileToParse) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(fileToParse);
        document.getDocumentElement().normalize();
        return document;
    }

    private String getDataFromElement(Element element, String tag){
        return element.getElementsByTagName(tag).item(0).getTextContent().trim();
    }

    private String getDataFromDocument(Document document, String tag){
        return document.getElementsByTagName(tag).item(0).getTextContent().trim();
    }
}


