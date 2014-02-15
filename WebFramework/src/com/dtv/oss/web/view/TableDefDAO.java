package com.dtv.oss.web.view;

import org.xml.sax.InputSource;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.SAXException;


// jaxp 1.0.1 imports
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import java.net.URL;
import java.util.HashMap;

public class TableDefDAO {
    public static final String DEF_TABLE = "table";
    public static final String DEF_TABLE_NAME = "name";
    public static final String DEF_TABLE_DISPLAY = "display";
    public static final String DEF_FIELD = "field";
    public static final String DEF_FIELD_NAME = "name";
    public static final String DEF_FIELD_DISPLAY = "display";

    private HashMap tableResources = null;

    public TableDefDAO(URL configURL) {
        Element root = loadDocument (configURL);
        tableResources = getTableResources(root);
    }

    public HashMap getTableResources() {
        return tableResources;
    }

    private  Element loadDocument(URL url) {
        Document doc = null;
        try {
            InputSource xmlInp = new InputSource(url.openStream());

            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder parser = docBuilderFactory.newDocumentBuilder();
            doc = parser.parse(xmlInp);
            Element root = doc.getDocumentElement();
            root.normalize();
            return root;
        } catch (SAXParseException err) {
            System.err.println ("TableDefDAO ** Parsing error" + ", line " +
                        err.getLineNumber () + ", uri " + err.getSystemId ());
            System.err.println("TableDefDAO error: " + err.getMessage ());
        } catch (SAXException e) {
            System.err.println("TableDefDAO error: " + e);
        } catch (java.net.MalformedURLException mfx) {
            System.err.println("TableDefDAO error: " + mfx);
        } catch (java.io.IOException e) {
            System.err.println("TableDefDAO error: " + e);
        } catch (Exception pce) {
            System.err.println("TableDefDAO error: " + pce);
        }
        return null;
    }

    private HashMap getTableResources(Element root) {
        HashMap resources = new HashMap();

        NodeList outterList = root.getElementsByTagName(DEF_TABLE);
        for (int outterLoop = 0; outterLoop < outterList.getLength(); outterLoop++) {
            Element element = (Element)outterList.item(outterLoop);
            String sName = getTagValue(element, DEF_TABLE_NAME);
            String sDisplay = getTagValue(element, DEF_TABLE_DISPLAY);

            // get  fields
            HashMap fields = new HashMap();

            NodeList fieldList = element.getElementsByTagName(DEF_FIELD);
            for (int fieldLoop = 0; fieldLoop < fieldList.getLength(); fieldLoop++)
            {
                Element elementfield = (Element)fieldList.item(fieldLoop);
                String sf1 = getTagValue(elementfield, DEF_FIELD_NAME);
                String sf2 = getTagValue(elementfield, DEF_FIELD_DISPLAY);
                fields.put(sf1, sf2);
            }

            TableResource resource = new TableResource(sName, sDisplay, fields);
            resources.put(sName, resource);
        }
        return resources;
    }

    private String getSubTagAttribute(Element root, String tagName, String subTagName, String attribute) {
        String returnString = "";
        NodeList list = root.getElementsByTagName(tagName);
        for (int loop = 0; loop < list.getLength(); loop++) {
            Node node = list.item(loop);
            if (node != null) {
                NodeList  children = node.getChildNodes();
                for (int innerLoop =0; innerLoop < children.getLength(); innerLoop++) {
                    Node  child = children.item(innerLoop);
                    if ((child != null) && (child.getNodeName() != null) && child.getNodeName().equals(subTagName) ) {
                        if (child instanceof Element) {
                            return ((Element)child).getAttribute(attribute);
                        }
                    }
                } // end inner loop
            }
        }
        return returnString;
    }

    private String getSubTagValue(Node node, String subTagName) {
        String returnString = "";
        if (node != null) {
            NodeList  children = node.getChildNodes();
            for (int innerLoop =0; innerLoop < children.getLength(); innerLoop++) {
                Node  child = children.item(innerLoop);
                if ((child != null) && (child.getNodeName() != null) && child.getNodeName().equals(subTagName) ) {
                    Node grandChild = child.getFirstChild();
                    if (grandChild.getNodeValue() != null) return grandChild.getNodeValue();
                }
            } // end inner loop
        }
        return returnString;
    }

    private String getSubTagValue(Element root, String tagName, String subTagName) {
        String returnString = "";
        NodeList list = root.getElementsByTagName(tagName);
        for (int loop = 0; loop < list.getLength(); loop++) {
            Node node = list.item(loop);
            if (node != null) {
                NodeList  children = node.getChildNodes();
                for (int innerLoop =0; innerLoop < children.getLength(); innerLoop++) {
                    Node  child = children.item(innerLoop);
                    if ((child != null) && (child.getNodeName() != null) && child.getNodeName().equals(subTagName) ) {
                        Node grandChild = child.getFirstChild();
                        if (grandChild.getNodeValue() != null) return grandChild.getNodeValue();
                    }
                } // end inner loop
            }
        }
        return returnString;
    }

    private String getTagValue(Element root, String tagName) {
        String returnString = "";
        NodeList list = root.getElementsByTagName(tagName);
        for (int loop = 0; loop < list.getLength(); loop++) {
            Node node = list.item(loop);
            if (node != null) {
                Node child = node.getFirstChild();
                if ((child != null) && child.getNodeValue() != null) return child.getNodeValue();
            }
        }
        return returnString;
    }

}