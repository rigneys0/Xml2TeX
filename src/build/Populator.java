package build;

import java.sql.SQLException;

import textProcessing.AttributePlacer;

import dbCOM.DBWrapper;
import exceptions.FatalException;

public class Populator {
	private DBWrapper someWrapper = null;
	private final String update=
			"INSERT INTO tagDB(tagName,tagPos,parity,tex)"+
	                           "VALUES('%name%',%position%,%parity%,'%tex%')";
	public Populator(DBWrapper someWrapper, String url) throws FatalException{
		this.someWrapper = someWrapper;
		try {
			someWrapper.connect(url);
		} 
		catch (SQLException e) {
			throw new FatalException("Populating database interrupted");
		}
	}
	public void addTag(String[][] params) throws FatalException{
		try {
			String sql = update;
			AttributePlacer somePlacer = new AttributePlacer();
			for(int index=0;index<params.length;index++){
				sql = somePlacer.placeAttribute(sql,params[index][0],
						                         params[index][1]);
			}
			System.out.println("PP"+sql);
			someWrapper.executeUpdate(sql);
		} catch (SQLException e) {
			throw new FatalException("unable to add tag " + params[0]);
		}
	}
}
