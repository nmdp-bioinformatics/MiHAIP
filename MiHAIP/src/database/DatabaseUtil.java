package database;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DatabaseUtil
{
	static final String DATA_BASE_NAME = "cds";
	static final String TABLE_NAME = "cds_sequence";
	static final String FREQ_TABLE = "freq";

	static final String ID = "ID";
	static final String TANSCRIPT_ID = "TRANSCRIPT_ID";
	static final String SEQUENCE = "SEQ";
	static final String CHROME = "Chrome";
	static final String GENE_SYMBOL = "Gene_Symbol";
    static final String DESCRIPTION = "Description";
    
    static final String POSITION = "position";
    static final String A = "A";
    static final String T = "T";
    static final String C = "C";
    static final String G = "G";
    
	static Connection connection;

	public static void connectDatabase(){
		connection = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection= DriverManager.getConnection("jdbc:sqlite:"+DATA_BASE_NAME+".db");
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		System.out.println("Opened database successfully");
	}

	public static void createFreqTable(){
		try {
			Statement stmt = connection.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS " + FREQ_TABLE + "("
					+ ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ CHROME + " CHAR(10) NOT NULL,"
					+ POSITION + " CHAR(20) NOT NULL,"
					+ A + " CHAR(10),"
					+ T+" CHAR(10),"
					+ C+" CHAR(10),"
					+ G+" CHAR(10), "
					+"UNIQUE ("+CHROME+","+POSITION+ ")"
					+");";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Create freq table successfully");
	}
	
	public static void insertFreqData(String chrome, String position, String Afreq, String Tfreq, String Cfreq, String Gfreq){
		Statement stmt = null;
		try {
			stmt = connection.createStatement();
		} catch (SQLException e) {
			System.out.println("connection is broken. fail to insert " + chrome + position);
		}
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO "+ FREQ_TABLE);
		sb.append("(");
		sb.append(CHROME + ",");
		sb.append(POSITION + ",");
		sb.append(A + ",");
		sb.append(T + ",");
		sb.append(C + ",");
		sb.append(G + ")");
		sb.append("VALUES (");
		sb.append(wrapString(chrome)+ ",");
		sb.append(wrapString(position)+ ",");
		sb.append(wrapString(Afreq)+ ",");
		sb.append(wrapString(Tfreq)+ ",");
		sb.append(wrapString(Cfreq)+ ",");
		sb.append(wrapString(Gfreq));
		sb.append(");");
		try {
			stmt.executeUpdate(sb.toString());
			stmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("fail to insert duplicate data" + chrome +" "+ position);
		}
	}
	
	public static void createSeqTable(){
		try {
			Statement stmt = connection.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
					+ ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ CHROME + " CHAR(50) NOT NULL,"
					+ GENE_SYMBOL + " CHAR(50) NOT NULL,"
					+ DESCRIPTION + " CHAR(100) NOT NULL,"
					+ TANSCRIPT_ID+" CHAR(50) NOT NULL,"
					+SEQUENCE+" TEXT NOT NULL,"
					+"UNIQUE ("+CHROME+")"
					+");";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Create cds table successfully");
		
	}

	public static void insertSeqData(String chrome, String geneSymbol, String description,String id, String seq)  {
		Statement stmt = null;
		try {
			stmt = connection.createStatement();
		} catch (SQLException e) {
			System.out.println("connection is broken. fail to insert " + id);
		}
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO "+ TABLE_NAME);
		sb.append("(");
		sb.append(CHROME + ",");
		sb.append(GENE_SYMBOL + ",");
		sb.append(DESCRIPTION + ",");
		sb.append(TANSCRIPT_ID + ",");
		sb.append(SEQUENCE + ")");
		sb.append("VALUES (");
		sb.append(wrapString(chrome)+ ",");
		sb.append(wrapString(geneSymbol)+ ",");
		sb.append(wrapString(description)+ ",");
		sb.append(wrapString(id)+ ",");
		sb.append(wrapString(seq));
		sb.append(");");
		try {
			stmt.executeUpdate(sb.toString());
			stmt.close();
		} catch (SQLException e) {
			System.out.println("fail to insert duplicate data" + id);
		}

	}
	
	public static ResultSet getFreq(String chrome, String pos, String change1, String change2){
		String sql = "SELECT " + change1.toUpperCase() +","+ change2.toUpperCase()+ " FROM " + FREQ_TABLE +" WHERE " + CHROME + " = "+ "'"+chrome+ "'" + " and "+ POSITION + "="+"'"+pos+"'";
		ResultSet rs = null;
		try {
			rs = connection.createStatement().executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return  rs;
		
	}

	public static String getSequence(String id){
		Statement stmt = null;
		String seq = "";
		try {
			stmt = connection.createStatement();
		} catch (SQLException e) {
			System.out.println("connection is broken. fail to insert " + id);
		}
		String sql = "SELECT " +SEQUENCE + " FROM " + TABLE_NAME +" WHERE " + TANSCRIPT_ID + " = "+ wrapString(id);
		try {
			ResultSet rs = connection.createStatement().executeQuery(sql);
			if(rs.next()){
				seq = rs.getString(SEQUENCE);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return  seq;
	}
	
	public static List<String> getYtransIDs(){
		List<String> ids = new ArrayList<>();
		String sql = "SELECT " +TANSCRIPT_ID + " FROM " + TABLE_NAME +" WHERE " + CHROME + " = 'Y' ";
		try {
			ResultSet rs = connection.createStatement().executeQuery(sql);
			while(rs.next()){
				ids.add(rs.getString(TANSCRIPT_ID));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return  ids;
	}
	public static ResultSet getYdata(){
		String sql = "SELECT " +TANSCRIPT_ID +","+ SEQUENCE+ " FROM " + TABLE_NAME +" WHERE " + CHROME + " = 'Y' ";
		ResultSet rs = null;
		try {
			rs = connection.createStatement().executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return  rs;
	}

	public static void cleanUp(){
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Connection closed.");
	}

	private static  String wrapString(String str){
		return "'" +str+ "'";
	}
}
