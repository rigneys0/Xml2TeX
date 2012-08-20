package main;

import dbCOM.DBWrapper;
import exceptions.FatalException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryGenerator {
	private DBWrapper someWrapper;
	public QueryGenerator(String url)throws FatalException{
		someWrapper = new DBWrapper();
		String query = "SELECT tex FROM tagDB WHERE tagname=? AND parity=?"
					   + " AND tagPos=?";
		try{
			someWrapper.connect(url);
			someWrapper.prepareStatement(query);
		}
		catch(SQLException eSQL){
			throw new FatalException("Error connecting to database");
		}
	}//constructor
	public String queryTag(Object[] tagList)throws FatalException{
		try{
			ResultSet rs = someWrapper.executeQuery(tagList);
			String retValue = "";
			while(rs.next()){
				retValue+=rs.getString("tex");
			}
			System.out.println(retValue);
			return retValue;
		}
		catch(SQLException eSQL){
			throw new FatalException("Error retrieving tag "+tagList[0]);
		}
	}//queryTag
	public void terminate() throws FatalException{
		try{
			someWrapper.close();
		}
		catch(SQLException eSQL){
			throw new FatalException("Error closing database connection");
		}
	}//terminate
}
