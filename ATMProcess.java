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
	 public static void checkBalance() throws SQLException, ClassNotFoundException {
				System.out.println("ENTER THE ACC_NO ");
				int acc_no = sc.nextInt();
				System.out.println("ENTER THE PIN NUMBER ");
				int pin_no = sc.nextInt();
				int pinNo = 0, accBal = 0;
				con = DButil.getconnect();
				PreparedStatement pst = con.prepareStatement("select PIN_NUMBER,ACC_BALANCE from CUSTOMER where ACC_NO=?");
				pst.setInt(1, acc_no);
				rs = pst.executeQuery();
				while (rs.next()) {
					pinNo = rs.getInt("PIN_NUMBER");
					accBal = rs.getInt("ACC_BALANCE");
				}
				if (pinNo == pin_no) {
					System.out.print("YOUR ACC NO " + acc_no + " BALANCE " + accBal);
				} else {
					System.out.println("CHECK YOUR PIN TRY AGAIN");
				}
			}
		
	 
	 
		public static void withdrawMoney() throws SQLException, ClassNotFoundException {
				System.out.println("ENTER THE ACC_NO ");
				int acc_no = sc.nextInt();
				System.out.println("ENTER THE PIN NUMBER ");
				int pin_no = sc.nextInt();
				System.out.println("ENTER THE AMOUNT TO DEBIT ");
				int debit = sc.nextInt();
				if (debit % 100 != 0) {
					System.out.println("ENTER VALID AMOUNT MULTIPLES OF 100 500");
					return;
				} else if (debit < 100) {
					System.out.println("MINIMUM CASH WITHDRAWN 100");
					return;
				} else if (debit > 10000) {
					System.out.println("DAILY LIMIT EXCEEDED");
					return;
				} else if (AtmBalance() < debit) {
					System.out.println("CASH NOT EXCEEDED YOU ENTERED");
					return;
				}
				int pinNo = 0, accBal = 0;
				con = DButil.getconnect();
				PreparedStatement pst = con.prepareStatement("select PIN_NUMBER,ACC_BALANCE from CUSTOMER where ACC_NO=?");
				pst.setInt(1, acc_no);
				rs = pst.executeQuery();
				while (rs.next()) {
					pinNo = rs.getInt("PIN_NUMBER");
					accBal = rs.getInt("ACC_BALANCE");
				}
				if (pinNo == pin_no && accBal >= debit) {
					accBal = accBal - debit;
					System.out.println("YOUR ACC NO " + acc_no + " BALANCE " + accBal);
					PreparedStatement pst1 = con.prepareStatement("update CUSTOMER set ACC_BALANCE=? where ACC_NO=?");
					pst1.setInt(1, accBal);
					pst1.setInt(2, acc_no);
					pst1.executeUpdate();
					System.out.println("WITHDRAWL SUCCESSFULLY");
					if (debit % 2000 == 0) {
						int c = debit / 2000;
						PreparedStatement pst2 = con
								.prepareStatement("update ATM set NUMBER=NUMBER-?, VALUE=VALUE-?  where DENOMINATION=? ");
						int value = c * 2000;
						pst2.setInt(1, c);
						pst2.setInt(2, value);
						pst2.setInt(3, 2000);
						pst2.executeUpdate();
					} else if (debit % 1000 == 0 && debit > 3000) {
						int c = debit / 2000;
						PreparedStatement pst2 = con
								.prepareStatement("update ATM set NUMBER=NUMBER-?, VALUE=VALUE-?  where DENOMINATION=? ");
						int value = c * 2000;
						pst2.setInt(1, c);
						pst2.setInt(2, value);
						pst2.setInt(3, 2000);
						pst2.executeUpdate();
						PreparedStatement pst3 = con
								.prepareStatement("update ATM set NUMBER=NUMBER-?, VALUE=VALUE-?  where DENOMINATION=? ");
						pst3.setInt(1, 2);
						pst3.setInt(2, 1000);
						pst3.setInt(3, 500);
						pst3.executeUpdate();
					} else {
						int c = debit / 2000;
						debit -= c * 2000;
						PreparedStatement pst2 = con
								.prepareStatement("update ATM set NUMBER=NUMBER-?, VALUE=VALUE-?  where DENOMINATION=? ");
						int value = c * 2000;
						pst2.setInt(1, c);
						pst2.setInt(2, value);
						pst2.setInt(3, 2000);
						pst2.executeUpdate();
						if (debit >= 1000) {
							debit -= 1000;
							PreparedStatement pst3 = con.prepareStatement(
									"update ATM set NUMBER=NUMBER-?, VALUE=VALUE-?  where DENOMINATION=? ");
							pst3.setInt(1, 2);
							pst3.setInt(2, 1000);
							pst3.setInt(3, 500);
							pst3.executeUpdate();
						}
						int d = debit / 100;
						PreparedStatement pst4 = con
								.prepareStatement("update ATM set NUMBER=NUMBER-?, VALUE=VALUE-?  where DENOMINATION=? ");
						pst4.setInt(1, d);
						pst4.setInt(2, debit);
						pst4.setInt(3, 100);
						pst4.executeUpdate();
					}

					return;
				} else if (accBal < debit) {
					System.out.println("INSUFFICIENT AMOUNT IN YOUR ACC");
					return;
				} else {
					System.out.println("CHECK YOUR PIN TRY AGAIN");
					return;
				}
			}


	
		public static void transferMoney() throws SQLException, ClassNotFoundException {
		 
				System.out.println("ENTER THE SENDER ACC_NO ");
				int send_acc_no = sc.nextInt();
				System.out.println("ENTER THE PIN NUMBER ");
				int pin_no = sc.nextInt();
				System.out.println("ENTER THE RECEIVE ACC_NO ");
				int rece_acc_no = sc.nextInt();
				System.out.println("ENTER THE AMOUNT TO TRANSFER ");
				int trans = sc.nextInt();
				if (trans < 1000) {
					System.out.println("MINIMUM CASH TRANSFER 1000");
					return;
				} else if (trans > 10000) {
					System.out.println("DAILY TRANSFER LIMIT EXCEEDED");
					return;
				}
				int pinNo = 0, accBal = 0;
				con = DButil.getconnect();
				PreparedStatement pst = con.prepareStatement("select PIN_NUMBER,ACC_BALANCE from CUSTOMER where ACC_NO=?");
				pst.setInt(1, send_acc_no);
				rs = pst.executeQuery();
				while (rs.next()) {
					pinNo = rs.getInt("PIN_NUMBER");
					accBal = rs.getInt("ACC_BALANCE");
				}
				if (pinNo == pin_no && accBal >= trans) {
					accBal = accBal - trans;
					System.out.println("YOUR ACC NO " + send_acc_no + " \nBALANCE " + accBal);
					PreparedStatement pst1 = con.prepareStatement("update CUSTOMER set ACC_BALANCE=? where ACC_NO=?");
					pst1.setInt(1, accBal);
					pst1.setInt(2, send_acc_no);
					pst1.executeUpdate();
					PreparedStatement pst2 = con
							.prepareStatement("update CUSTOMER set ACC_BALANCE=ACC_BALANCE+? where ACC_NO=?");
					pst2.setInt(1, trans);
					pst2.setInt(2, rece_acc_no);
					pst2.executeUpdate();
					System.out.println("TRANSFERED SUCCESSFULLY");
				} else {
					System.out.println("CHECK YOUR PIN TRY AGAIN");
				}
			}
		
		
		public static void checkAtmBalance() throws SQLException, ClassNotFoundException {
			con = DButil.getconnect();
			PreparedStatement pst1 = con.prepareStatement("SELECT * FROM ATM ");
			rs = pst1.executeQuery();
			System.out.println("DENOMINATION    	NUMBER    	VALUE");
			while (rs.next()) {
				System.out.println("	" + rs.getInt(1) + "		" + rs.getInt(2) + "   		 " + rs.getInt(3));
			}

			PreparedStatement pst = con.prepareStatement("select sum(value) from atm");
			rs = pst.executeQuery();
			while (rs.next()) {
				System.out.println("\n" + rs.getInt(1) + "   ");
			}
			System.out.println("thank you");
		}

		public static int AtmBalance() throws SQLException, ClassNotFoundException {
			int bal = 0;
			con = DButil.getconnect();
			PreparedStatement pst = con.prepareStatement("select sum(value) as bal from atm");
			rs = pst.executeQuery();
			while (rs.next()) {
				bal = rs.getInt("bal");
			}
			return bal;
		}

		public static void main(String args[]) throws ClassNotFoundException, SQLException {
			System.out.println("ROLL NO:20F204\n");
				System.out.print("1. Load cash to ATM\n2. Show customer Details\n3. Show ATM operations\n4. ExitS\n");
				System.out.print("ENTER THE CHOICE FROM MENU TO DO ");
				int cho1 = sc.nextInt();
				switch (cho1) {
				case 1:
					load_cash();
					break;
				case 2:
					show_Customer_details();
					break;
				case 3:
					System.out.print(
							"1. Check Balance\n2. Withdraw Money\n3. Transfer Money\n4. Check ATM Balance\n5. ExitS\n");
					System.out.print("ENTER THE CHOICE FROM MENU TO DO ");
					int cho2 = sc.nextInt();
					switch (cho2) {
					case 1:
						checkBalance();
						break;
					case 2:
						withdrawMoney();
						break;
					case 3:
						transferMoney();
						break;
					case 4:
						checkAtmBalance();
						break;
					case 5:
						System.out.print("PROGRAM TERMINATTED");
						break;
					default:
						System.out.println("ENTER YOUR OTHER CHOICE");
					}
					break;
				case 4:
					System.out.print("PROGRAM TERMINATTED");
					break;
				default:
					System.out.println("ENTER YOUR OTHER CHOICE");
				}
			}
}
	