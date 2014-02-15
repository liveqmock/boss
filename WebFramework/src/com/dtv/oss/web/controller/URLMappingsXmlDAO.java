package com.dtv.oss.web.controller;

/**
 * <p>Title: BOSS 2</p>
 * <p>Description: BOSS second iterative development project</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Shanghai DigiVision Technology Co. Ltd</p>
 * @author Leon Liu
 * @version 1.2
 */

import org.xml.sax.InputSource;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
// jaxp 1.0.1 imports
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import java.net.URL;
import java.util.HashMap;

//import com.dtv.oss.web.util.tracer.Debug;
//import com.dtv.oss.web.controller.URLMapping;
import com.dtv.oss.web.flow.ScreenFlowData;

/**
 * This class provides the data bindings for the screendefinitions.xml
 * and the requestmappings.xml file.
 * The data obtained is maintained by the ScreenFlowManager
 */

public class URLMappingsXmlDAO {

    // constants
    public static final String URL_MAPPING = "url-mapping";
    public static final String EVENT_MAPPING = "event-mapping";
    public static final String EXCEPTION_MAPPING = "exception-mapping";
    public static final String URL = "url";
    public static final String LANGUAGE = "language";
    public static final String TEMPLATE = "template";
    public static final String RESULT = "result";
    public static final String NEXT_SCREEN = "screen";
    public static final String PROCESSS_ACTION = "isAction";
    public static final String REQUIRES_SIGNIN = "requiresSignin";
    public static final String USE_FLOW_HANDLER = "useFlowHandler";
    public static final String FLOW_HANDLER_CLASS = "class";
    public static final String WEB_ACTION_CLASS = "web-action-class";
    public static final String EJB_ACTION_CLASS = "ejb-command-class";
    public static final String EVENT_CLASS = "event-class";
    public static final String HANDLER_RESULT = "handler-result";
    public static final String FLOW_HANDLER = "flow-handler";
    public static final String EXCEPTION_CLASS = "exception-class";
    public static final String DEFAULT_SCREEN = "default-screen";

    public static final String KEY = "key";
    public static final String VALUE= "value";
    public static final String DIRECT="direct";
    public static final String SCREEN= "screen";
    public static final String SCREEN_NAME = "screen-name";
    public static final String PARAMETER = "parameter";

    public static Element loadDocument(String location) {
        Document doc = null;
        try {
            URL url = new URL(location);
            InputSource xmlInp = new InputSource(url.openStream());
            //for os specific path style
            //java.io.FileInputStream stream = new java.io.FileInputStream(location);
            //InputSource xmlInp = new InputSource(stream);

            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder parser = docBuilderFactory.newDocumentBuilder();
            doc = parser.parse(xmlInp);
            Element root = doc.getDocumentElement();
            root.normalize();
            return root;
        } catch (SAXParseException err) {
            System.err.println ("URLMappingsXmlDAO ** Parsing error" + ", line " +
                        err.getLineNumber () + ", uri " + err.getSystemId ());
            System.err.println("URLMappingsXmlDAO error: " + err.getMessage ());
        } catch (SAXException e) {
            System.err.println("URLMappingsXmlDAO error: " + e);
        } catch (java.net.MalformedURLException mfx) {
            System.err.println("URLMappingsXmlDAO error: " + mfx);
        } catch (java.io.IOException e) {
            System.err.println("URLMappingsXmlDAO error: " + e);
        } catch (Exception pce) {
            System.err.println("URLMappingsXmlDAO error: " + pce);
        }
        return null;
    }


    public static ScreenFlowData loadScreenFlowData(String location) {
        Element root = loadDocument(location);
        HashMap exceptionMappings = getExceptionMappings(root);
        String defaultScreen  = getTagValue(root, DEFAULT_SCREEN);
        return new ScreenFlowData(exceptionMappings, defaultScreen);
    }


    public static HashMap loadRequestMappings(String location) {
        Element root = loadDocument(location);
        return getRequestMappings(root);
    }


    public static HashMap loadExceptionMappings(String location) {
        Element root = loadDocument(location);
        return getExceptionMappings(root);
    }

    public static HashMap loadEventMappings(String location) {
        Element root = loadDocument(location);
        return getEventMappings(root);
    }

    private static String getSubTagAttribute(Element root, String tagName, String subTagName, String attribute) {
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

    public static HashMap getExceptionMappings(Element root) {
        HashMap exceptionMappings = new HashMap();
        NodeList list = root.getElementsByTagName(EXCEPTION_MAPPING);
        for (int loop = 0; loop < list.getLength(); loop++) {
            Node node = list.item(loop);
            if (node != null) {
                String exceptionClassName = "";
                String screen = null;
                // get exception nodes
                // need to be a element to get attributes
                if (node instanceof Element) {
                    Element element = ((Element)node);
                    exceptionClassName = element.getAttribute(EXCEPTION_CLASS);
                    screen = element.getAttribute(SCREEN);
                    exceptionMappings.put(exceptionClassName, screen);
                }

            }
        }
        return exceptionMappings;
    }

    public static HashMap getEventMappings(Element root) {
        HashMap eventMappings = new HashMap();
        NodeList list = root.getElementsByTagName(EVENT_MAPPING);
        for (int loop = 0; loop < list.getLength(); loop++) {
            Node node = list.item(loop);
            if (node != null) {
                String eventClassName = getSubTagValue(node,EVENT_CLASS);
                String ejbActionClass = getSubTagValue(node, EJB_ACTION_CLASS);
                if ((eventClassName != null) && !eventClassName.equals("")) {
                    eventMappings.put(eventClassName, new EventMapping(eventClassName, ejbActionClass));
                }
            }
        }
        return eventMappings;
    }
    public static HashMap getRequestMappings(Element root) {
        HashMap urlMappings = new HashMap();
        NodeList list = root.getElementsByTagName(URL_MAPPING);
        for (int loop = 0; loop < list.getLength(); loop++) {
            Node node = list.item(loop);
            if (node != null) {
                String url = "";
                String screen = null;
                String useActionString = null;
                String useFlowHandlerString = null;
                String requiresSigninString = null;
                String flowHandler = null;
                String webActionClass =null;
                String ejbActionClass = null;
                HashMap resultMappings = null;
                boolean useFlowHandler = false;
                boolean isAction = false;
                boolean requiresSignin = false;
                // get url mapping attributes
                // need to be a element to get attributes
                if (node instanceof Element) {
                    Element element = ((Element)node);
                    url = element.getAttribute(URL);
                    screen = element.getAttribute(NEXT_SCREEN);
                    useActionString = element.getAttribute(PROCESSS_ACTION);
                    useFlowHandlerString = element.getAttribute(USE_FLOW_HANDLER);
                }
                webActionClass = getSubTagValue(node, WEB_ACTION_CLASS);
                if (webActionClass != null) isAction= true;
                // get request handler
                if ((useFlowHandlerString != null) && useFlowHandlerString.equals("true")) useFlowHandler = true;
                // get flow handler
                if ((useFlowHandlerString != null) && useFlowHandlerString.equals("true")) useFlowHandler = true;
                if (useFlowHandler) {
                    // need to be a element to find sub nodes by name
                    if (node instanceof Element) {
                        Element element = (Element)node;
                        NodeList children = element.getElementsByTagName(FLOW_HANDLER);
                        Node flowHandlerNode = null;
                        if (children.getLength() >= 1) {
                            flowHandlerNode = children.item(0);
                        }
                        if (children.getLength() > 1) {
                                 System.err.println("Non fatal error: There can be only one <" + FLOW_HANDLER +
                                               "> definition in a <" + URL_MAPPING + ">");
                        }
                        // get the flow handler details
                        if (flowHandlerNode != null) {
                            if (flowHandlerNode instanceof Element) {
                                Element flowElement = (Element)flowHandlerNode;
                                flowHandler = flowElement.getAttribute(FLOW_HANDLER_CLASS);
                                NodeList results = flowElement.getElementsByTagName(HANDLER_RESULT);
                                if (results.getLength() > 0){
                                    resultMappings = new HashMap();
                                }
                                for (int resultLoop=0; resultLoop < results.getLength(); resultLoop++) {
                                    Node resultNode = results.item(resultLoop);
                                    if (resultNode instanceof Element) {
                                        Element resultElement = (Element)resultNode;
                                        String key = resultElement.getAttribute(RESULT);
                                        String value = resultElement.getAttribute(NEXT_SCREEN);
                                        if (!resultMappings.containsKey(key)) {
                                            resultMappings.put(key,value);
                                        } else {
                                            System.err.println("*** Non Fatal errror: Screen " + url + " <" + FLOW_HANDLER +
                                                           "> key " + "\"" + key + "\" defined more than one time");
                                        }
                                    }
                                } // end for
                            }
                        } // end if (flowHandler != null)
                    }
                } // end if (useFlowHandler)
                URLMapping mapping = new URLMapping(url, screen,
                                             isAction,
                                             useFlowHandler,
                                             webActionClass,
                                             flowHandler,
                                             resultMappings);

                if (!urlMappings.containsKey(url)) {
                    urlMappings.put(url, mapping);
                } else {
                    System.err.println("*** Non Fatal errror: Screen " + url +
                                       " defined more than once in screen definitions file");
                }
            }
        }
        return urlMappings;
    }

    public static String getSubTagValue(Node node, String subTagName) {
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

    public static String getSubTagValue(Element root, String tagName, String subTagName) {
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

    public static String getTagValue(Element root, String tagName) {
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


