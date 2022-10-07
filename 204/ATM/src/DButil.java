import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DButil {
public static Connection getconnect() throws ClassNotFoundException, SQLException{
	Class.forName("oracle.jdbc.driver.Oracledriver ");
	Connection con=DriverManager.getConnection(" jdbc:oracle:thin:@localhost:1521:xe","oracle","2610");
	return con;
}
}
