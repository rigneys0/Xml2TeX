package build;
import java.io.IOException;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.XMLReader;
import exceptions.FatalException;
public class TagReader implements ContentHandler {
	private Populator populator = null;
	private XMLReader someReader = null;
	public TagReader(Populator populator) throws FatalException{
		try{
			this.populator = populator;
			SAXParserFactory someFactory = SAXParserFactory.newInstance();
			SAXParser someParser = someFactory.newSAXParser();
			someReader = someParser.getXMLReader();
			someReader.setContentHandler(this);
		}
		catch(SAXException eSAX){
			throw new FatalException(eSAX);
		} catch (ParserConfigurationException ePCE) {
			throw new FatalException(ePCE);
		}
	}//constructor
	public void parse(String uri){
		try{
			someReader.parse(uri);	
		}
		catch(IOException eIO){
			throw new FatalException(eIO);
		}
		catch(SAXException eSAX){
			throw new FatalException(eSAX);
		}
	}
	@Override
	public void setDocumentLocator(Locator someLocator){
	}//setDocumentLocator
	@Override
	public void startDocument(){
	}//startDocument
	@Override
	public void endDocument(){
	}//endDocument
	@Override
	public void processingInstruction(String target, String data)
		throws SAXException{
	}//processingInstruction
	@Override
	public void startPrefixMapping(String prefix, String uri)
		throws SAXException{
	}//startPrefixMapping
	@Override
	public void endPrefixMapping(String prefix){
	}//endPrefixMapping
	@Override
	public void startElement(String uri, String localName, String rawName,
							  Attributes attrs) throws SAXException, 
							  							FatalException{
		try{
			if(rawName.compareTo("xml")!=0){
				populator.addTag(getAttributes(attrs));
			}
		}
		catch(NullPointerException eNP){
			throw new FatalException("incorrect number of attributes");
		}

	}//startElement
	@Override
	public void endElement(String uri, String localName, String rawName){
		
	}//endElement
	@Override
	public void characters(char[] data, int start, int end){
		
	}//characters
	@Override
	public void ignorableWhitespace(char[] data, int start, int end){
	}//ignorableWhitespace
	@Override
	public void skippedEntity(String name){
	}//skippedEntity
	
	private String[][] getAttributes(Attributes attrs){
		String[][] attributes = new String[attrs.getLength()][2];
		for(int i=0;i<attrs.getLength();i++){
			System.out.println("AA"+attrs.getLocalName(i));
			attributes[i][0]=attrs.getLocalName(i);
			attributes[i][1]=attrs.getValue(i);
			System.out.println(attributes[i][0] + " " + attributes[i][1]);
		}
		return attributes;
	}//getAttributes
	
}
