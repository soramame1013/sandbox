package jp.ac.o_hara.site;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class SimpleDAO {
	//データベ－スへの接続を行う
	protected final Connection createConnection() {
		Connection db = null;
		if (System.getenv("DATABASE_URL") != null) {
			try {
				URI dbUri = new URI(System.getenv("DATABASE_URL"));
				String username = dbUri.getUserInfo().split(":")[0];
				String password = dbUri.getUserInfo().split(":")[1];
				String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();

				db = DriverManager.getConnection(dbUrl, username, password);
			} catch (URISyntaxException | SQLException e) {
				e.printStackTrace();
			}
		} else {
	    	try {
				Context con = new InitialContext();
				DataSource ds = (DataSource)con.lookup("java:comp/env/jdbc/samplesite");
				db = ds.getConnection();
	    	} catch (SQLException | NamingException e) {
				e.printStackTrace();
	    	}
		}
		return db;
	}
	
	//データベースへの接続を終了する
	protected final void closeConnection( Connection db ) {
		try {
			db.close();
		} catch (SQLException e) {
			db = null;
			e.printStackTrace();
		}
	}
	
	//特定のSQLを実行する
	public final boolean execSQL( String sql ) {
		Connection db = this.createConnection();
    	//PreparedStatement ps = null;
    	boolean result = false;
    	if( !sql.startsWith("CREATE") && !sql.startsWith("DROP") && !sql.startsWith("INSERT") && !sql.startsWith("UPDATE")) { return result; }
    	try (PreparedStatement ps = db.prepareStatement(sql)) {
			//ps = db.prepareStatement(sql);
			ps.executeUpdate();
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(db);
		}
    	return result;
	}
}
