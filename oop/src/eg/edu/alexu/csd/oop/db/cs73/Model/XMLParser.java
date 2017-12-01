package eg.edu.alexu.csd.oop.db.cs73.Model;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import eg.edu.alexu.csd.oop.db.cs73.Model.DBObjects.Column;
import eg.edu.alexu.csd.oop.db.cs73.Model.DBObjects.Record;
import eg.edu.alexu.csd.oop.db.cs73.Model.DBObjects.Table;
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

public class XMLParser {
    @SuppressWarnings("rawtypes")
	public void saveTableToXML(String path, String dtdPath, Table databaseTable) throws FileNotFoundException {
        Document dom;
        Element col = null, rec;

        // instance of a DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use factory to get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // create instance of DOM
            dom = db.newDocument();

            // create the root element
            Element rootEle = dom.createElement("table");
            rootEle.setAttribute("name", databaseTable.getName());

            // create data elements and place them under root

            for (Column column : databaseTable.getColumns()) {
                col = dom.createElement("column");
                col.setAttribute("name", column.getName());
                col.setAttribute("type", column.getType());
                for (Object object : column.getRecords()) {
                    Record record = (Record) object;
                    /*if(record.getValue().getClass().getSimpleName().equalsIgnoreCase("string")){

                    }*/
                    rec = dom.createElement("record");
                    if(record.getValue() != null)
                        rec.setAttribute("value", record.getValue().toString());
                    else
                        rec.setAttribute("value", "null");
                    //rec.appendChild(dom.createTextNode(record.getValue().toString()));
                    col.appendChild(rec);
                }
                rootEle.appendChild(col);
            }
            dom.appendChild(rootEle);

            try {
                // Creating the DTD
                getDTD(dtdPath);

                Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.setOutputProperty(OutputKeys.METHOD, "xml");
                tr.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
                //tr.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,  dtd.getPublicId());
                tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,  dtdPath);
                tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                // send DOM to file
                tr.transform(new DOMSource(dom), new StreamResult(new FileOutputStream(path)));
                //tr.transform(new DOMSource(dtd));

            } catch (TransformerException te) {
                System.out.println(te.getMessage());
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        } catch (ParserConfigurationException pce) {
            System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
        }
    }

    public void getDTD(String dtdPath){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(dtdPath)));

            bw.write("<!ELEMENT table (column+)>");
            bw.newLine();
            bw.write("<!ATTLIST table name CDATA #REQUIRED>");
            bw.newLine();
            bw.newLine();
            bw.write("<!ELEMENT column (record*)>");
            bw.newLine();
            bw.write("<!ATTLIST column \n\tname CDATA #REQUIRED\n\ttype CDATA #REQUIRED>");
            bw.newLine();
            bw.newLine();
            bw.write("<!ELEMENT record EMPTY>");
            bw.newLine();
            bw.write("<!ATTLIST record value CDATA #IMPLIED>");
            bw.newLine();
            bw.newLine();

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked", "resource" })
	public Table loadTableFromXML(String path) {
        Table loadedTable = null;
        Document dom;
        InputStream inputStream;
        Reader reader = null;
        try {
            File xml = new File(path);
            inputStream = new FileInputStream(xml);
            reader = new InputStreamReader(inputStream, "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
        	throw new RuntimeException(e.getMessage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
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
            loadedTable = new Table(doc.getAttribute("name"));
            ArrayList<Column> cols = new ArrayList<>();

            for (int i = 0; i < rootNode.getLength(); i++) {
                Node colNode = rootNode.item(i);
                if (colNode.getNodeName().equals("#text"))
                    continue;
                //Element colEle = (Element) rootNode.item(i).get;
                NodeList colNL = colNode.getChildNodes();
                String colName = colNode.getAttributes().getNamedItem("name")
                        .toString().replaceAll("^\\w*=\"", "").replaceAll("\"$","");
                String colType = colNode.getAttributes().getNamedItem("type")
                        .toString().replaceAll("^\\w*=\"", "").replaceAll("\"$","");
                Column col = new Column(colName, colType);
                //put("id", colNode.getNodeName());
                for (int j = 0; j < colNL.getLength(); j++) {
                    Node recItem = colNL.item(j);
                    if (recItem.getNodeName().equals("#text"))
                        continue;
                    //shapeMap.put(prop.getNodeName(), prop.getTextContent());
                    Record record = null;
                    String recordValue = recItem.getAttributes().getNamedItem("value")
                            .toString().replaceAll("^\\w*=\"", "").replaceAll("\"$","");
                    if(!recItem.getAttributes().getNamedItem("value").equals("null"))
                        record = new Record(recordValue);
                    else
                        record = new Record(null);
                    col.addRecord(record);
                }
                //loadedTable.addColumn(col);
                cols.add(col);
            }

            loadedTable.setColumns(cols);

        } catch (ParserConfigurationException pce) {
            throw new RuntimeException(pce.getMessage());
        } catch (SAXException se) {
        	throw new RuntimeException(se.getMessage());
        } catch (IOException ioe) {
        	throw new RuntimeException(ioe.getMessage());
        }
        return loadedTable;
    }
}