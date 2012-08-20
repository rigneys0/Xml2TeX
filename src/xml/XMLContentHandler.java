package xml;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import exceptions.FatalException;
import fileWriter.TexWriter;
import textProcessing.*;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;
import org.xml.sax.XMLReader;
import org.xml.sax.Locator;
public class XMLContentHandler implements ContentHandler {
	private TeXGenerator someGenerator;
	private TexWriter someWriter;
	public XMLContentHandler(TeXGenerator someGenerator,TexWriter someWriter)
		throws FatalException{
		this.someGenerator = someGenerator;
		this.someWriter = someWriter;
	}
	public void parse(String uri) throws FatalException{
		try{
			SAXParserFactory someFactory = SAXParserFactory.newInstance();
			XMLReader someReader = someFactory.newSAXParser().getXMLReader();
			someReader.setContentHandler(this);
			someReader.parse(uri);
		}
		catch(IOException eIO){
			throw new FatalException(eIO);
		}
		catch(ParserConfigurationException ePC){
			throw new FatalException(ePC);
		}
		catch(SAXException eSAX){
			throw new FatalException(eSAX);
		}
	}//parse
	@Override
	public void setDocumentLocator(Locator someLocator){
	}//setDocumentLocator
	@Override
	public void startDocument() throws SAXException{
		System.out.println("Starting Parsing....");
	}//startDocument
	@Override
	public void endDocument() throws SAXException{
		System.out.println("Finishing Parsing....");
	}//endDocument
	@Override
	public void processingInstruction(String target, String Data) 
			throws SAXException{	
	}//processingInstructions
	@Override
	public void startPrefixMapping(String prefix,String uri){
	}//startPrefixMapping
	@Override
	public void endPrefixMapping(String prefix)throws SAXException{
	}//endPrefixMapping
	@Override
	public void startElement(String nameSpaceUri,String localName,
			String rawName,Attributes attrs)throws SAXException,FatalException{
		if(getName(localName,rawName).equals("xml")){
			return;
		}
		String tex = "";
		int parity = 0;
		if(attrs.getLength()==0){
			tex = someGenerator.generateHeadTeX(getName(localName,rawName),
						parity);
		}
		else{
			String[][] attributeValPairs = getAttributes(attrs);
			parity= attributeValPairs.length;
			tex = someGenerator.generateHeadTeX(getName(localName,rawName),
			                              attributeValPairs,parity); 
		}
		someWriter.addTeXItem(tex);
	}//startElement
	@Override
	public void endElement(String nameSpaceUri,String localName,
			String rawName) throws SAXException, FatalException{
		String tex = someGenerator.generateTailTeX(getName(localName,rawName));
		someWriter.addTeXItem(tex);
	}//endElement
	@Override
	public void characters(char[] characters, int start, int end)
			throws SAXException,FatalException{
		String data = new String(characters,start,end);
		data=data.trim();
		if(data.equals("")){
			return;
		}
		someWriter.addTeXItem(data);
	}//characters
	@Override
	public void ignorableWhitespace(char[] space,int start, int end) 
			throws SAXException{
	}//ignorableWhiteSpace
	@Override
	public void skippedEntity(String name) throws SAXException {
	}//skippedEntity
	private String getName(String localName,String rawName){
		if(localName.equals("")){
			return rawName;
		}
		return localName;
	}//getName
	private String[][] getAttributes(Attributes attrs){
		String[][] attributes = new String[attrs.getLength()][2];
		for(int i=0;i<attrs.getLength();i++){
			attributes[i][0]=attrs.getLocalName(i);
			attributes[i][1]=attrs.getValue(i);
			System.out.println(attributes[i][0] + " " + attributes[i][1]);
		}
		return attributes;
	}//getAttributes
}
