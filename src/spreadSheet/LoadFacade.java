package spreadSheet;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * LoadFacade - This provides a facade into the standard XML API for loading a worksheet.
 *    
 * @author Eric McCreath
 *
 */

public class LoadFacade {
	NodeList nl;
	int nodepos;
	Node currentelement;

	public static LoadFacade load(File file) {
		DocumentBuilderFactory dbf;
		DocumentBuilder db;
		 LoadFacade res = new LoadFacade();
		try {
			// load the xml tree
			dbf = DocumentBuilderFactory.newInstance();

			db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);

			// parse the tree and obtain the person info
			Node drawinge = doc.getFirstChild();

			res.nl = drawinge.getChildNodes();
		
        
           res.nodepos = 0;
           return res;
		} catch (Exception e) {
			System.err.println("Problem loading " + file);
		}
		return null;
	}
	
	public String nextElement() {
		 if (nodepos == nl.getLength()) return null;
		 currentelement = nl.item(nodepos);
		 nodepos++;
		 return currentelement.getNodeName();
	}
	
	public String getText(String name) {
		NodeList list = currentelement.getChildNodes();
		for (int i=0;i<list.getLength();i++) {
			if (list.item(i).getNodeName().equals(name)) {
				return extracttext(list.item(i));
			}
		}
		return null;
	}
	
	private String extracttext(Node item) {
		return item.getTextContent();
	}


}
