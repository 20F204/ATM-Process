import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;



public class ATMProcess {
	 public static Connection con;
	 public static Statement st;
	 public static PreparedStatement ps;
	 public static ResultSet rs;
	 public static Scanner sc=new Scanner(System.in);

	 
	 public static void create() throws ClassNotFoundException, SQLException{
		 con=DButil.getconnect();
		 ps= con.prepareStatement("create table ATM(DENOMINATION INT(5), NUMBER INT(50), VALUE INT(50))");
		ps.execute();
		System.out.println("ATMtable created");
	 }
	 public static void createCustomer() throws ClassNotFoundException, SQLException{
		 con=DButil.getconnect();
		 ps=con.prepareStatement("create table Customer(ACC_NO INT(5),acc_holder_name varchar(50),pin_number int(4),acc_balance int(10))");
	 ps.execute();
		System.out.println(" Customer  table created");

	 }
	 public static void activate() throws ClassNotFoundException, SQLException{
		 con=DButil.getconnect();
		PreparedStatement ps=con.prepareStatement("insert into ATM values(2000,0,0)");
		 ps.execute();
		 PreparedStatement ps1=con.prepareStatement("insert into ATM values(500,0,0)");
		 ps1.execute();
		 PreparedStatement ps2=con.prepareStatement("insert into ATM values(100,0,0)");
		 ps2.execute();
			System.out.println("ATM machine activated");

	 }
	 public static void addCustomer() throws ClassNotFoundException, SQLException{
		 con=DButil.getconnect();
		 PreparedStatement ps=con.prepareStatement("insert into Customer values(101,'Arun',4567,45678)");
		 ps.execute();
		 PreparedStatement ps1=con.prepareStatement("insert into Customer values(102,'varun',6789,36547)");
		 ps1.execute();
		 PreparedStatement ps2=con.prepareStatement("insert into Customer values(103,'sethu',3456,87655)");
		 ps2.execute();
		 PreparedStatement ps3=con.prepareStatement("insert into Customer values(104,'Aravii',2345,98765)");
		 ps3.execute();
		 PreparedStatement ps4=con.prepareStatement("insert into Customer values(105,'aadal',1234,23415)");
		 ps4.execute();
			System.out.println("Customer Account activated");

	 }
	 public static void addNewCustomer() throws ClassNotFoundException, SQLException{
		 int acc_no=106;
		 con=DButil.getconnect();
		 PreparedStatement ps=con.prepareStatement("insert into Customer values(?,?,?,?)");
         ps.setInt(1, acc_no);
         String name=sc.next();
         ps.setString(2, name);
         int pin=sc.nextInt();
         ps.setInt(3, pin);
         int initial=sc.nextInt();
         ps.setInt(4, initial);
         ps.execute();
         acc_no++;
	 }
	 
	 
	 public static void load_cash() throws ClassNotFoundException, SQLException{
		 con=DButil.getconnect();
		 PreparedStatement ps=con.prepareStatement("update ATM set NUMBER=NUMBER+?,VALUE=VALUE+? where DENOMINATION=?"); 
		 System.out.println("ENTER DENOMINATION TO LOAD ");
			int denomination = sc.nextInt();
			System.out.println("ENTER NUMBER OF DENOMINATION TO LOAD ");
			int number = sc.nextInt();
			int value = number * denomination;
			ps.setInt(1, number);
			ps.setInt(2, value);
			ps.setInt(3, denomination);
			ps.executeUpdate();
	   System.out.println("Your cash was added");
		PreparedStatement pst1 = con.prepareStatement("SELECT * FROM ATM ");
		rs = pst1.executeQuery();
	 }
	 
	 
	 
	 public static void show_Customer_details() throws ClassNotFoundException, SQLException{
		 con=DButil.getconnect();
		 PreparedStatement ps=con.prepareStatement("select * from Customer");
		 rs=ps.executeQuery();
		 System.out.println("Customer table");
		 while(rs.next()){
			 System.out.println(rs.getInt(1) + "  " + rs.getString(2)+ "  " + rs.getInt(3) + "  " + rs.getInt(4));
			 
		 }
	 }
	 
	public static void main(String[] args) {
	

	}

}
