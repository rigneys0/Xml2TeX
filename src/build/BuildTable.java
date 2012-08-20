package build;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbCOM.DBWrapper;
import exceptions.FatalException;
public class BuildTable {
	private DBWrapper someWrapper = null;
	private final String buildFile ="tags.xml";
	public BuildTable(){
		someWrapper = new DBWrapper();
	}//constructor
	public void build(){
		String create = "CREATE TABLE tagDB("+
	                    "tagName varchar(50)," +
	                    "parity int,"+
	                    "tagPos int,"+
				        "tex varchar(90));";
		try{
			someWrapper.connect("jdbc:sqlite:tagDB");
			someWrapper.executeUpdate(create);
		}
		catch(SQLException eSQL){
			throw new FatalException(eSQL);
		}
	}//build
	public void populate() throws FatalException{
		Populator somePop = new Populator(someWrapper,"jdbc:sqlite:tagDB");
		TagReader someReader = new TagReader(somePop);
		someReader.parse(buildFile);
		/*try {
			someWrapper.close();
		} catch (SQLException eSQL) {
			throw new FatalException(eSQL);
		}*/
	}//populate
	public void test() throws SQLException{
		String query = "SELECT * FROM tagDB";
		ResultSet rs = someWrapper.executeQuery(query);
		while(rs.next()){
			System.out.println(rs.getString(1)+"\t"+rs.getInt(2)+"\t"
					        + rs.getString(3)+rs.getString(4));
		}
		someWrapper.close();
	}//test
	public static void main(String[] args) throws SQLException, ClassNotFoundException{
		BuildTable someBuild = new BuildTable();
		Class.forName("org.sqlite.JDBC");
		someBuild.build();
		someBuild.populate();
		someBuild.test();
	}
}
