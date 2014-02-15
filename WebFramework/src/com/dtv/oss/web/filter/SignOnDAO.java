package com.dtv.oss.web.filter;

import org.xml.sax.InputSource;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

// jaxp 1.0.1 imports
import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.dtv.oss.service.factory.HomeFactory;

/**
 * This class provides the data bindings for the screendefinitions.xml
 * and the requestmappings.xml file.
 * The data obtained is maintained by the ScreenFlowManager
 */

public class SignOnDAO {

    // xml tag constants
    public static final String SIGNON_FORM_LOGIN_PAGE = "signon-form-login-page";
    public static final String SIGNON_FORM_ERROR_PAGE = "signon-form-error-page";
    public static final String SECURITY_CONSTRAINT = "security-constraint";
    public static final String WEB_RESOURCE_COLLECTION = "web-resource-collection";
    //public static final String WEB_RESOURCE_NAME = "web-resource-name";
    public static final String URL_PATTERN = "url-pattern";
    public static final String AUTH_CONSTRAINT = "auth-constraint";
    public static final String ROLE_NAME = "role-name";


    private String signOnLoginPage = null;
    private String signOnErrorPage = null;
    private HashMap protectedResources = null;

    public SignOnDAO(URL configURL) {
        Element root = loadDocument (configURL);
        // hujinpeng 2008-03-05 注释以前的方法，解析xml得到权限信息
        //protectedResources = getProtectedResources(root);
        // hujinpeng 2008-03-05 使用新的方法，从数据库得到权限信息
        protectedResources = getProtectedResourcesSql();
    }

    public String getSignOnPage() {
        return signOnLoginPage;
    }

    public String getSignOnErrorPage() {
        return signOnErrorPage;
    }

    public HashMap getProtectedResources() {
        return protectedResources;
    }
    
    public HashMap getProtectedResourcesSql() {
    	HashMap resources = new HashMap();
    	String urlPattern = "";
    	String role = "";
    	
    	Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select * from t_systemprivilegeresource";
			DataSource ds = HomeFactory.getFactory().getDataSource();
            con = ds.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()){
				urlPattern = rs.getString("ACTION").trim();
				role = String.valueOf(rs.getInt("PRIVID"));
				ArrayList roles = new ArrayList();
				roles.add(role);
		    	ProtectedResource resource = new ProtectedResource(urlPattern, roles);
		        resources.put(urlPattern, resource);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			}
		}
        return resources;
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
            signOnLoginPage = getTagValue(root, SIGNON_FORM_LOGIN_PAGE ).trim();
            signOnErrorPage = getTagValue(root, SIGNON_FORM_ERROR_PAGE ).trim();
            return root;
        } catch (SAXParseException err) {
            System.err.println ("SignOnDAO ** Parsing error" + ", line " +
                        err.getLineNumber () + ", uri " + err.getSystemId ());
            System.err.println("SignOnDAO error: " + err.getMessage ());
        } catch (SAXException e) {
            System.err.println("SignOnDAO error: " + e);
        } catch (java.net.MalformedURLException mfx) {
            System.err.println("SignOnDAO error: " + mfx);
        } catch (java.io.IOException e) {
            System.err.println("SignOnDAO error: " + e);
        } catch (Exception pce) {
            System.err.println("SignOnDAO error: " + pce);
        }
        return null;
    }

    private HashMap getProtectedResources(Element root) {
        HashMap resources = new HashMap();
        // get the signon page and signon error page
        signOnLoginPage = getTagValue(root, SIGNON_FORM_LOGIN_PAGE ).trim();
        signOnErrorPage = getTagValue(root, SIGNON_FORM_ERROR_PAGE ).trim();

        // get protected pages //
        NodeList outterList = root.getElementsByTagName(SECURITY_CONSTRAINT);
        for (int outterLoop = 0; outterLoop < outterList.getLength(); outterLoop++) {
            Element element = (Element)outterList.item(outterLoop);
            // get  roles that can see this page
            ArrayList roles = new ArrayList();

            NodeList authList = element.getElementsByTagName(AUTH_CONSTRAINT);
            for (int authLoop = 0; authLoop < authList.getLength(); authLoop++)
            {
                Element elementRole = (Element)authList.item(authLoop);
                NodeList roleList = elementRole.getElementsByTagName(ROLE_NAME);
                for (int roleLoop = 0; roleLoop < roleList.getLength(); roleLoop++)
                {
                    Node roleNode = roleList.item(roleLoop);
                    if (roleNode != null)
                    {
                        Node roleNodeSub = roleNode.getFirstChild();
                        String roleName = roleNodeSub.getNodeValue();
                        roles.add(roleName);
                    }
                }
            }

            NodeList list = element.getElementsByTagName(WEB_RESOURCE_COLLECTION);
            for (int loop = 0; loop < list.getLength(); loop++) {
                Node node = list.item(loop);
                if (node != null) {
                    //String resourceName = getSubTagValue(node, WEB_RESOURCE_NAME);
                    String urlPattern = getSubTagValue(node, URL_PATTERN);
                    ProtectedResource resource = new ProtectedResource(urlPattern, roles);
                    if (!resources.containsKey(urlPattern)) {
                         resources.put(urlPattern, resource);
                    } else {
                        System.err.println("*** Non Fatal errror: Protected Resource " + urlPattern +
                                       " defined more than once in screen definitions file");
                    }
                }
            }
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


