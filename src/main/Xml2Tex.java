package main;
import java.io.File;
import build.BuildTable;

import exceptions.FatalException;
import fileWriter.TexWriter;
import textProcessing.TeXGenerator;
import xml.XMLContentHandler;

public class Xml2Tex {
	private TeXGenerator someTGen;
	private TexWriter someWriter;
	private QueryGenerator someQGen;
	private XMLContentHandler someHandler;
	private final String DB_DRIVER_URI = "org.sqlite.JDBC";
	private final String DB_URI = "jdbc:sqlite:tagDB";
	public String inputFile="";
	public String outputFile="";
	public Xml2Tex(String inputFile, String outputFile)throws FatalException{
		try{
			initialiseDrivers();
			checkForDB(DB_URI);
			this.inputFile = inputFile;
			this.outputFile = outputFile;
			someWriter = new TexWriter();
			someQGen = new QueryGenerator(DB_URI);
			someTGen = new TeXGenerator(someQGen);
			someHandler = new XMLContentHandler(someTGen,someWriter);
		}
		catch(ClassNotFoundException eCNF){
			throw new FatalException("Unable to load drivers");
		}
	}//constructor
	private void initialiseDrivers() throws ClassNotFoundException{
		Class.forName(DB_DRIVER_URI);
	}
	private void checkForDB(String uri){
		File someFile = new File("tagDB");
		if(!someFile.exists()){
			BuildTable someBuild = new BuildTable();
			someBuild.build();
			someBuild.populate();
		}
	}
	public void convert() throws FatalException{
		someHandler.parse(inputFile);
		someWriter.open(outputFile);
		someWriter.writeBatch();
		someWriter.close();
		someQGen.terminate();
	}//convert
	public static void main(String[] args)throws FatalException{
		try{
			Xml2Tex someConverter = new Xml2Tex(args[0],args[1]);
			someConverter.convert();
		}
		catch(FatalException eFatal){
			System.out.println("Error: "+eFatal.getMessage());
		}

	}
}
