package textProcessing;

import exceptions.FatalException;
import main.QueryGenerator;
public class TeXGenerator {
	private AttributePlacer somePlacer = null;
	private QueryGenerator someGen = null;
	public TeXGenerator(QueryGenerator someGen) throws FatalException{
		this.someGen = someGen;
		somePlacer = new AttributePlacer();
	}
	public String generateHeadTeX(String tag,String[][] attributes, 
			int parity) throws FatalException{
		Object[] queryParameters = {tag,attributes.length,0};
		String text = someGen.queryTag(queryParameters);
		System.out.println("TT"+text);
		for(int index = 0; index<attributes.length;index++){
			text = somePlacer.placeAttribute(text, attributes[index][0],
					                               attributes[index][1]); 
		}
		System.out.println(text);
		return text;
	}
	public String generateHeadTeX(String tag,int parity) throws FatalException{
		Object[] queryParameters = {tag,0,0};
		String text = someGen.queryTag(queryParameters);
		return text;
	}
	public String generateTailTeX(String tag){
		Object[] queryParameters = {tag,0,1};
		return someGen.queryTag(queryParameters);
	}
}
