package eg.edu.alexu.csd.oop.db.cs73.Model;

import eg.edu.alexu.csd.oop.db.cs73.Model.DBObjects.DBContainer;
import eg.edu.alexu.csd.oop.db.cs73.Model.DBObjects.Table;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class XMLParser {
    public void saveToXML(Table databaseTable) throws FileNotFoundException {
        Document dom;
        Element shape = null, property = null;

        // instance of a DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use factory to get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // create instance of DOM
            dom = db.newDocument();

            // create the root element
            Element rootEle = dom.createElement("database");
            rootEle.setAttribute("name", databaseTable.getName());

            // create data elements and place them under root

            for (Table table : d) {
                shape = dom.createElement(map.get("id"));
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    property = dom.createElement(entry.getKey().toString());
                    property.appendChild(dom.createTextNode(entry.getValue().toString()));
                    shape.appendChild(property);
                }
                rootEle.appendChild(shape);
            }
            dom.appendChild(rootEle);

            try {
                Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.setOutputProperty(OutputKeys.METHOD, "xml");
                tr.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
                // tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,
                // "shapes.dtd");
                tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                // send DOM to file
                tr.transform(new DOMSource(dom), new StreamResult(new FileOutputStream(path)));

            } catch (TransformerException te) {
                System.out.println(te.getMessage());
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        } catch (ParserConfigurationException pce) {
            System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
        }
    }

    public ArrayList<Map<String, String>> readXML(File xml) {

        ArrayList<Map<String, String>> shapesMaps = new ArrayList<>();
        Document dom;
        InputStream inputStream;
        Reader reader = null;
        try {
            inputStream = new FileInputStream(xml);
            reader = new InputStreamReader(inputStream, "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputSource is = new InputSource(reader);
        is.setEncoding("ISO-8859-1");
        // Make an instance of the DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use the factory to take an instance of the document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // parse using the builder to get the DOM mapping of the
            // XML file
            dom = db.parse(is);
            Element doc = dom.getDocumentElement();
            NodeList rootNode = doc.getChildNodes();

            for (int i = 0; i < rootNode.getLength(); i++) {
                Map<String, String> shapeMap = new HashMap<>();
                Node shapeNode = rootNode.item(i);
                NodeList shapeNL = shapeNode.getChildNodes();
                if (shapeNode.getNodeName().equals("#text"))
                    continue;
                shapeMap.put("id", shapeNode.getNodeName());
                for (int j = 0; j < shapeNL.getLength(); j++) {
                    Node prop = shapeNL.item(j);
                    if (prop.getNodeName().equals("#text"))
                        continue;
                    shapeMap.put(prop.getNodeName(), prop.getTextContent());
                }
                shapesMaps.add(shapeMap);
            }

        } catch (ParserConfigurationException pce) {
            System.out.println(pce.getMessage());
        } catch (SAXException se) {
            System.out.println(se.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        return shapesMaps;
    }
}
