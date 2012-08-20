package textProcessing;

import exceptions.FatalException;

public class AttributePlacer {
	public AttributePlacer(){
	}//constructor
	public String placeAttribute(String text,String attribute,String value)
		throws FatalException{
		String newText="";
		if(text==null){
			throw new FatalException(new NullPointerException());
		}
		attribute = "%".concat(attribute+"%");
		String[] parts = text.split(attribute);
		if(parts.length>1){
			newText = parts[0]+value+parts[1];
		}
		else{
			newText = parts[0]+value;
		}
		return newText;
	}//placeAttribute
}
