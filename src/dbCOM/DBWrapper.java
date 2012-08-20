package dbCOM;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.sqlite.*;
@SuppressWarnings("unused")
public class DBWrapper{
	private Connection con;
	private Statement stmnt;
	private PreparedStatement prep;
	public DBWrapper(){
	}
	public static void setDrivers(String driver) 
			throws ClassNotFoundException{
			Class.forName(driver);
	}
	public void connect(String uri) throws SQLException{
		con = DriverManager.getConnection(uri);
		stmnt= con.createStatement();
	}
	public int executeUpdate(String operation)throws SQLException{
		return stmnt.executeUpdate(operation);
	}
	public ResultSet executeQuery(String operation)throws SQLException{
		return stmnt.executeQuery(operation);
	}
	public void prepareStatement(String statement)throws SQLException{
		prep = con.prepareStatement(statement);
	}
	public ResultSet executeQuery(Object[] parameters)throws SQLException{
		for(int index=0;index<parameters.length;index++){
			prep.setObject(index+1, parameters[index]);
		}
		return prep.executeQuery();
	}
	public int executeUpdate(Object[] parameters)throws SQLException{
		for(int index=0;index<parameters.length;index++){
			prep.setObject(index+1, parameters[index]);
		}
		return prep.executeUpdate();
	}
	public void close() throws SQLException{
		if(con!=null){
			con.close();
		}
	}
	
	public static void main(String args[]) throws SQLException, ClassNotFoundException{
		  DBWrapper someWrapper = new DBWrapper();
		  DBWrapper.setDrivers("org.sqlite.JDBC");
		  someWrapper.connect("jdbc:sqlite:tagDB");
		  String query = "SELECT * FROM tagDB";
		  ResultSet rs = someWrapper.executeQuery(query);
		  while(rs.next()){
			  System.out.println(rs.getString("tex"));
		  }
		  rs.close();
	  }
}
