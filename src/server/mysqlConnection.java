package server;

import java.awt.Desktop;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;

import GUI.ServerGUIController;
import controllers.inputValidator;
import entities.CreditCard;
import entities.DepartmentManager;
import entities.ManagerRequest;
import entities.Order;
import entities.OrderType;
import entities.ParameterChangeRequest;
import entities.ParameterType;
import entities.Park;
import entities.ParkManager;
import entities.ReportType;
import entities.Sale;
import entities.ServiceRepesentative;
import entities.Subscriber;
import entities.Traveler;
import entities.Worker;
import entities.reportHelper;

public class mysqlConnection {

	private static Connection conn = null;
	private static int subscriptionCounter;
	private static HashMap<String, Park> parksList;

	/**
	 * this method establishes a connection to the database , and initializes
	 * parkList hashmap and subscription counter from database(mysql)
	 * 
	 * @throws ParseException
	 */

	public static void connecttoDB() throws ParseException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println("Driver definition succeed");
		} catch (Exception ex) {
			/* handle the error */
			System.out.println("Driver definition failed");
		}
		try {
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/gonature?serverTimezone=CAT", "root",
					"Adidaher12");
			System.out.println("SQL connection succeed");
			parksList = new HashMap<String, Park>();
			getAllParks();
			initializeSubscriptionCounter();
		} catch (SQLException ex) {/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	/**
	 * this function take Traveler and add it to database in MySQL
	 * 
	 * @param list have in first index Traveler
	 * @return true if it's insert to databas another return false
	 */
	public static boolean addTravelerToDB(ArrayList<Object> list) {
		if (conn != null) {
			try {
				if (list.get(0) instanceof Traveler) {
					{
						PreparedStatement stmt = conn.prepareStatement("INSERT INTO travelers VALUES (?,?);");
						/// String IdNumber,boolean isGuide()
						stmt.setString(1, ((Traveler) list.get(0)).getIdNumber());
						stmt.setBoolean(2, ((Traveler) list.get(0)).isGuide());
						stmt.executeUpdate();
						return true;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static Connection getConn() {
		return conn;
	}

	/**
	 * this method returns orders that have specified visitDate and VisitTime,and
	 * are in the specified park, and have arrived=!entrance
	 * 
	 * @param arr have in first index (Date)-current date
	 * @param arr have in second index (Time)-current time
	 * @param arr have in third index (String)-the park name
	 * @param arr have in fourth index (boolean)-entrance,if entrance=true the
	 *            method return orders that havent entered the park,else returns
	 *            orders that have entered but haven't exited the park
	 * @return the method returns all orders that could enter/exit the park in this
	 *         time(according to entrance boolean parameter)
	 */
	public static ArrayList<Order> getRandomOrder(ArrayList<Object> arr) {
		// arr = Date date , Time time, String parkName,boolean entrance
		ArrayList<Order> result = new ArrayList<Order>();
		Date date = (Date) arr.get(0);
		Time time = (Time) arr.get(1);
		String parkName = (String) arr.get(2);
		boolean entrance = (boolean) arr.get(3);
		Time timeMinusStayTime = (Time) addTimes(time, '-', parksList.get(parkName).getStayTime());
		if (conn != null) {
			try {
				PreparedStatement ps = conn.prepareStatement(
						"select * from orders where visitTime <= ?  and visitTime >= ? and visitDate = ? and parkName = ? and arrived=?");
				ps.setTime(1, time);
				ps.setTime(2, timeMinusStayTime);
				ps.setDate(3, date);
				ps.setString(4, parkName);
				ps.setBoolean(5, !entrance);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					Order o = new Order(parksList.get(rs.getString("parkName")), rs.getDate("visitDate"),
							rs.getTime("visitTime"), rs.getInt("numOfvisitors"), rs.getString("email"),
							rs.getString("ordererId"), OrderType.valueOf(rs.getString("orderType").toUpperCase()),
							rs.getDouble("price"), rs.getString("orderStatus"), rs.getBoolean("payStatus"),
							rs.getBoolean("arrived"), rs.getString("phoneNumber"), rs.getTime("entrance"),
							rs.getTime("exit"));
					if (rs.getTime("exit") == null)// changed by ayman
						result.add(o);
				}
				return result;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * this method take SubscriptionNumber OR IdNumber to check if it's exist in
	 * DataBase
	 * 
	 * @param arr have in first index (String SubscriptionNumber OR String IdNumber)
	 * @return if we find this subscriber return it to client
	 */
	public static Subscriber checkSubscriptionNumber(ArrayList<Object> arr) {
		// arraylist = String SubscriptionNumber || String IdNumber
		String identifyNumber = (String) arr.get(0);
		Subscriber subscriber = null;
		String query;
//		System.out.println("");
		if (conn != null) {
			try {
				if (inputValidator.checkSubscription(identifyNumber)) {
					query = "SELECT * FROM subscribers where subscriptionNumber = '" + identifyNumber + "' ;";
//					System.out.println(query);
				} else if (inputValidator.checkId(identifyNumber)) {
					query = "SELECT * FROM subscribers where idNumber = '" + identifyNumber + "' ;";
//					System.out.println(query);
				} else
					return null;
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				if (rs.next()) {
					CreditCard cc = new CreditCard(rs.getString("cardNumber"), rs.getString("expireDate"),
							rs.getString("cvv"), rs.getString("idNumber"));
					subscriber = new Subscriber(rs.getString("subscriptionNumber"), rs.getString("idNumber"),
							rs.getString("firstName"), rs.getString("lastName"), rs.getString("phoneNumber"),
							rs.getString("email"), rs.getInt("numberOfFamilyMembers"), cc);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return subscriber;
	}

	/**
	 * method that checks if there is Traveler with the given id, if true returns
	 * the class with all his informations else returns null
	 * 
	 * @param arr have in first index (String idNumber)
	 * @return if we find this Traveler return it to client
	 */
	public static Traveler checkId(ArrayList<Object> arr) {
		// arraylist = String idNumber
		String idNumber = (String) arr.get(0);
		Traveler traveler = null;
		if (conn != null) {
			try {
				String query = "SELECT * FROM travelers where idNumber = '" + idNumber + "'";
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query);
//				System.out.println(query);
				if (rs.next())
					traveler = new Traveler(rs.getString("idNumber"), rs.getBoolean("isGuide"));
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		return traveler;
	}

	/**
	 * this function insert Subscriber to database in MYSQL
	 * 
	 * @param list have in first index (Subscriber )
	 * @return true if it's insert to databas another return false
	 */
	public static boolean addSubscriberToDB(ArrayList<Object> list) {
		if (conn != null) {
			try {
				if (list.get(0) instanceof Subscriber) {
					PreparedStatement stmt = conn
							.prepareStatement("INSERT INTO subscribers VALUES (?,?,?,?,?,?,?,?,?,?);");
					stmt.setString(1, ((Subscriber) list.get(0)).getSubscriptionNumber());
					stmt.setString(2, ((Subscriber) list.get(0)).getIdNumber());
					stmt.setString(3, ((Subscriber) list.get(0)).getFirstName());
					stmt.setString(4, ((Subscriber) list.get(0)).getLastName());
					stmt.setString(5, ((Subscriber) list.get(0)).getPhoneNumber());
					stmt.setString(6, ((Subscriber) list.get(0)).getEmail());
					stmt.setInt(7, ((Subscriber) list.get(0)).getNumberOfFamilyMembers());

					if (((Subscriber) list.get(0)).getCreditCard() == null) {
						stmt.setString(8, "");
						stmt.setString(9, "");
						stmt.setString(10, "");
					} else {
						stmt.setString(8, ((Subscriber) list.get(0)).getCreditCard().getCardNumber());
						stmt.setString(9, ((Subscriber) list.get(0)).getCreditCard().getExpireDate());
						stmt.setString(10, ((Subscriber) list.get(0)).getCreditCard().getCvv());
					}
					stmt.executeUpdate();
					subscriptionCounter++;
					return true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * this method search worker by usernamd and password if it's exist in database
	 * return it
	 * 
	 * @param arr have in first index (String username), second index (String
	 *            password)
	 * @return if we find this Worker return it to client
	 */
	public static Worker checkWorker(ArrayList<Object> arr) {
		// arraylist = String username,String password
		String username = (String) arr.get(0);
		String password = (String) arr.get(1);
		Worker worker = null;
		if (conn != null)
			try {
				String query = "Select * FROM workers WHERE userName = '" + username + "'AND password = '" + password
						+ "'";
				Statement st = conn.createStatement();

				// execute the query, and get a java resultset
				ResultSet rs = st.executeQuery(query);

				if (rs.next()) {
					String role = rs.getString("role");
					String firstName = rs.getString("firstName");
					String lastName = rs.getString("lastName");
					String workerId = rs.getString("workerId");
					String email = rs.getString("email");
					Park park = parksList.get(rs.getString("parkName"));
					switch (role) {
					case "Department Manager":
						DepartmentManager dp = new DepartmentManager(username, password, firstName, lastName, workerId,
								email);
						dp.setParks(new ArrayList<Park>(parksList.values()));
						return dp;

					case "Park Manager":
						return new ParkManager(username, password, firstName, lastName, workerId, email, park);

					case "Worker":
						return new Worker(username, password, firstName, lastName, workerId, email, park);

					case "Service Representative":
						return new ServiceRepesentative(username, password, firstName, lastName, workerId, email);

					default:
						break;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return worker;
	}

	/**
	 * this function take all parks data from database and put all of them in
	 * 'ArrayList <Park> parksList' this function is very usefully and Frequently
	 * used
	 */
	public static void getAllParks() {
		if (conn != null)
			try {
				String query = "Select * FROM parks";
				Statement st = conn.createStatement();
				// execute the query, and get a java resultset
				ResultSet rs = st.executeQuery(query);
				while (rs.next()) {
					Park p = new Park(rs.getString("name"), rs.getString("address"), rs.getTime("stayTime"),
							rs.getInt("maxNumOfVisitors"), rs.getInt("maxNumOfOrders"));
					p.setCurrentNumOfVisitors(rs.getInt("currentNumOfVisitors"));
					p.setCurrentNumOfUnplannedVisitors(rs.getInt("currentNumOfUnplannedVisitors"));
					parksList.put(p.getName(), p);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	/**
	 * this mehod take all the parks from database and send it to client
	 * 
	 * @param arr is empty 'null'
	 * @return All the parks in ArrayList<Park>
	 */
	public static ArrayList<Park> getAllParksForClient(ArrayList<Object> arr) {
		ArrayList<Park> arrParks = new ArrayList<Park>();
		if (conn != null)
			try {
				String query = "SELECT * FROM gonature.parks;";
				Statement st = conn.createStatement();
				// execute the query, and get a java resultset
				ResultSet rs = st.executeQuery(query);
				while (rs.next()) {
					Park p = new Park(rs.getString("name"), rs.getString("address"), rs.getTime("stayTime"),
							rs.getInt("maxNumOfVisitors"), rs.getInt("maxNumOfOrders"));
					p.setCurrentNumOfVisitors(rs.getInt("currentNumOfVisitors"));
					p.setCurrentNumOfUnplannedVisitors(rs.getInt("currentNumOfUnplannedVisitors"));
					arrParks.add(p);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		return arrParks;
	}

	/**
	 * this function change the subscriptionCounter to give him for the next
	 * traveler that he want to create subscription
	 */
	public static void initializeSubscriptionCounter() {
		String str = "";
		int res = 0;
		if (conn != null) {
			try {
				String query = "Select MAX(subscriptionNumber) FROM subscribers";
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query);
				if (rs.next())
					str = rs.getString(1);
				else
					str = "99999";
				res = Integer.parseInt(str) + 1;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		subscriptionCounter = res;
	}

	/**
	 * @param empty
	 * @return subscription Number to give him for the traveler that he want to
	 *         create subscription
	 */
	public static int getSubscriptionCounter() {
		return subscriptionCounter;
	}

	/**
	 * this method take order details and check if the traveler can create one and
	 * it's return true if he can another no
	 * 
	 * @param arr have in first index ( arrayList = Time y) second index(Date d)
	 *            ,Third index( int numOfvisitors), fourth index (String parkName)
	 * @return true if the date is availbiltity to create order another false
	 */
	public static boolean checkAvailibility(ArrayList<Object> arr) {
		// arrayList = Time y,Date d, int numOfvisitors,String parkName
		Time t = (Time) arr.get(0);
		Date d = (Date) arr.get(1);
		int numOfvisitors = (int) arr.get(2);
		String parkName = (String) arr.get(3);
		int max = 0;
		ArrayList<Order> orders = new ArrayList<Order>();
		statusOrderInWaitingList(null);// needs to be updated to be run on a different thread//
		if (conn != null)
			try {
				PreparedStatement ps = conn.prepareStatement(
						"Select * From orders Where visitDate=? and visitTime<? and visitTime>? and parkName = ? and orderStatus != 'unconfirmed'");
				ps.setDate(1, d);
				ps.setTime(2, addTimes(t, '+', parksList.get(parkName).getStayTime()));
				ps.setTime(3, addTimes(t, '-', parksList.get(parkName).getStayTime()));
				ps.setString(4, parkName);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					Order o = new Order(parksList.get(rs.getString("parkName")), rs.getDate("visitDate"),
							rs.getTime("visitTime"), rs.getInt("numOfvisitors"), rs.getString("email"),
							rs.getString("ordererId"), OrderType.valueOf(rs.getString("orderType").toUpperCase()),
							rs.getDouble("price"), rs.getString("orderStatus"), rs.getBoolean("payStatus"),
							rs.getBoolean("arrived"), rs.getString("phoneNumber"), rs.getTime("entrance"),
							rs.getTime("exit"));
					orders.add(o);
				}
				for (Order o : orders) {
					int i = 0;
					if (o.getVisitTime().compareTo(t) < 0) {
						ps = conn.prepareStatement(
								"select sum(numOfvisitors) from orders Where visitDate=? and visitTime>=? and visitTime<? and parkName = ? and orderStatus != 'unconfirmed'");
						ps.setDate(1, o.getVisitDate());
						ps.setTime(2, o.getVisitTime());
						Time t1 = o.getVisitTime();
						Time t2 = addTimes(t1, '+', parksList.get(parkName).getStayTime());
						ps.setTime(3, t2);
						ps.setString(4, parkName);
						rs = ps.executeQuery();
						if (rs.next()) {
							i = rs.getInt(1);
							if (i > max)
								max = i;
						}
					}
				}
				ps = conn.prepareStatement(
						"select sum(numOfvisitors) from orders Where visitDate=? and visitTime>=? and visitTime<? and parkName = ? and orderStatus != 'unconfirmed'");
				ps.setDate(1, d);
				ps.setTime(2, t);
				Time t2 = addTimes(t, '+', parksList.get(parkName).getStayTime());
				ps.setTime(3, t2);
				ps.setString(4, parkName);
				rs = ps.executeQuery();
				if (rs.next())
					max = (max > rs.getInt(1)) ? max : rs.getInt(1);
				System.out.println(rs.getInt(1));
				Park p = parksList.get(parkName);
//				System.out.println("//////////////////////////////////////////////////////////////////////////////");
//				System.out.println(max);
//				System.out.println(numOfvisitors);
//				System.out.println("//////////////////////////////////////////////////////////////////////////////");
				return (max + numOfvisitors <= p.getMaxNumOfOrders() ? true : false);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		return false;
	}

	/**
	 * this method insert new order to database in MYSQL and return true if it's
	 * done another return false
	 * 
	 * @param arr have in first index (Order )
	 * @return true if the order insert to database successfully another false
	 */
	public static boolean addOrder(ArrayList<Object> arr) {
		Order order = (Order) arr.get(0);
		if (conn != null)
			try {
				PreparedStatement ps = conn.prepareStatement("INSERT INTO orders values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				ps.setString(1, order.getPark().getName());
				ps.setDate(2, order.getVisitDate());
				ps.setTime(3, order.getVisitTime());
				ps.setInt(4, order.getNumOfVisitors());
				ps.setString(5, order.getEmail());
				ps.setString(6, order.getOrderer());
				ps.setString(7, order.getOrderType().toString().toUpperCase());
				ps.setDouble(8, order.getPrice());
				ps.setString(9, order.getOrderStatus());
				ps.setBoolean(10, order.getPayStatus());
				ps.setBoolean(11, false);
				ps.setTime(12, null);
				ps.setTime(13, null);
				ps.setString(14, order.getPhoneNumber());
				ps.executeUpdate();
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return false;
	}

	/**
	 * this method search in database order by (id,date,time,park name) if it's
	 * exist return it to client
	 * 
	 * @param arr have in first index (String id )
	 * @param arr have in second index (Date date )
	 * @param arr have in third index (Time time )
	 * @param arr have in fourth index (String parkName )
	 * @return Order if it's exist in database another return null
	 */
	public static Order searchOrder(ArrayList<Object> arr) {
		// arr = String id, Date date , Time time, String parkName
		String id = (String) arr.get(0);
		Date date = (Date) arr.get(1);
		Time time = (Time) arr.get(2);
		String parkName = (String) arr.get(3);
		Time t2 = (Time) addTimes(time, '-', parksList.get(parkName).getStayTime());
		ArrayList<Order> resultArrayList = new ArrayList<Order>();
		if (conn != null) {
			try {

				PreparedStatement ps = conn.prepareStatement(
						"select * from orders where ordererId = ?and visitTime <= ?  and visitTime > ? and visitDate = ? and parkName = ?");
				ps.setString(1, id);
				ps.setTime(2, time);
				ps.setTime(3, t2);
				ps.setDate(4, date);
				ps.setString(5, parkName);
//				System.out.println(ps.toString());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					if (rs.getTime("exit") == null)// changed by ayman
						return new Order(parksList.get(rs.getString("parkName")), rs.getDate("visitDate"),
								rs.getTime("visitTime"), rs.getInt("numOfvisitors"), rs.getString("email"),
								rs.getString("ordererId"), OrderType.valueOf(rs.getString("orderType").toUpperCase()),
								rs.getDouble("price"), rs.getString("orderStatus"), rs.getBoolean("payStatus"),
								rs.getBoolean("arrived"), rs.getString("phoneNumber"), rs.getTime("entrance"),
								rs.getTime("exit"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * this method take from database all the orders by idNumber and return it to
	 * client the mehod take from database just orders that visit day isn't pass
	 * 
	 * @param arr have in first index (String idNumber )
	 * @return return all orders that have same idNumber in database by
	 *         ArrayList<Order> to traveler
	 */
	public static ArrayList<Order> getAllOrdersForTraveler(ArrayList<Object> arr) {
		//
		ArrayList<Order> orders = new ArrayList<Order>();
		String idNumber = (String) arr.get(0);

		if (conn != null) {
			try {
				statusOrderInWaitingList(null);// needs to be updated to run on seperate threa/////
				PreparedStatement ps = conn
						.prepareStatement("select * from gonature.orders where ordererId = ? and  visitDate >= ? ;");
				ps.setString(1, idNumber);
				ps.setDate(2, Date.valueOf(LocalDate.now()));

//				System.out.println(ps.toString());
				ResultSet rs = ps.executeQuery();
				while (rs.next())
					orders.add(new Order(parksList.get(rs.getString("parkName")), rs.getDate("visitDate"),
							rs.getTime("visitTime"), rs.getInt("numOfvisitors"), rs.getString("email"),
							rs.getString("ordererId"), OrderType.valueOf(rs.getString("orderType").toUpperCase()),
							rs.getDouble("price"), rs.getString("orderStatus"), rs.getBoolean("payStatus"),
							rs.getBoolean("arrived"), rs.getString("phoneNumber"), rs.getTime("entrance"),
							rs.getTime("exit")));
				return orders;

			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * this method change status specific order in database to confirmed OR
	 * unconfirmed the method check if the status is unconfirmed to see if someone
	 * waiting at waitingList and give him the next place
	 * 
	 * @param arr have in first index (Order order)
	 * @param arr have in second index (String status )
	 * @return true if update the database successfully another false
	 */
	public static boolean updateOrderStatus(ArrayList<Object> arr) {
		// arr = Order order, String status
		Order order = (Order) arr.get(0);
		String status = (String) arr.get(1);
		if (conn != null)
			try {
				PreparedStatement ps = conn.prepareStatement(
						"update orders set orderStatus = ? where ordererId = ? and visitTime = ? and visitDate = ?");
				ps.setString(1, status);
				ps.setString(2, order.getOrderer());
				ps.setTime(3, order.getVisitTime());
				ps.setDate(4, order.getVisitDate());
				ps.executeUpdate();
				if (status.equals("unconfirmed"))
					updateWaitingList(null);
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return false;
	}

	/**
	 * this method is only right assuming that the lowest possible time is 8:00,and
	 * highest possible time is 19:00
	 * 
	 * @param Time t1
	 * @param char operation='+' or '-'
	 * @param Time t2
	 * @return t1(operation)t2 this method is only right assuming that the lowest
	 *         possible time is 8:00,and highest possible time is 19:00(according to
	 *         all parks openning times)
	 */
	@SuppressWarnings("deprecation")
	private static Time addTimes(Time t1, char operation, Time t2) {
		int hours1 = t1.getHours();
		int minutes1 = t1.getMinutes();
		int hours2 = t2.getHours();
		int minutes2 = t2.getMinutes();
		int totalhours, totalminutes;
		switch (operation) {
		case '+':
			totalminutes = (minutes1 + minutes2) % 60;
			totalhours = (hours1 + hours2) + (minutes1 + minutes2) / 60;
			if (totalhours > 19) {
				totalhours = 19;
				totalminutes = 1;
			}
			return new Time(totalhours, totalminutes, 0);
		case '-':
			totalminutes = minutes1 - minutes2;
			totalhours = hours1 - hours2;
			if (totalminutes < 0) {
				totalminutes = 60 + totalminutes;
				totalhours -= 1;
			}
			if (totalhours < 8) {
				totalhours = 7;
				totalminutes = 59;
			}
			return new Time(totalhours, totalminutes, 0);
		default:
			return null;
		}
	}

	/**
	 * this mehod take park name to check Park Vacancy and return this Park
	 * 
	 * @param list have in first index (String parkName)
	 * @return park by parkName if it's exist another will return null
	 */
	public static Park checkParkVacancy(ArrayList<Object> list) {
		String parkName = (String) list.get(0);
		Park park = parksList.get(parkName);
		Park p = new Park(park.getName(), park.getAddress(), park.getStayTime(), park.getMaxNumOfVisitor(),
				park.getMaxNumOfOrders());
		p.setCurrentNumOfVisitors(park.getCurrentNumOfVisitors());
		p.setCurrentNumOfUnplannedVisitors(park.getCurrentNumOfUnplannedVisitors());
		return p;
	}

	/**
	 * this method Update arrival status and entrance time at database in MYSQL for
	 * specific order
	 * 
	 * @param list have in first index (Order order)
	 */
	public static void updateOrderArrivalStatus(ArrayList<Object> list) {
		Order order = (Order) list.get(0);
		if (conn != null) {
			try {
				// String query = "update orders set arrived=? where ordererId=? and visitDate=?
				// and visitTime=?;";
				PreparedStatement stmt = conn.prepareStatement(
						"update orders set arrived=1 ,entrance=?, payStatus=1 where ordererId=? and visitDate=? and visitTime=?;");
				stmt.setTime(1, Time.valueOf(LocalTime.now()));
				stmt.setString(2, order.getOrderer());
				stmt.setDate(3, (Date) order.getVisitDate());
				stmt.setTime(4, order.getVisitTime());
				stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * this method Update exit time for specific order at database
	 * 
	 * @param list have in first index (Order order)
	 */
	public static void updateOrderArrivalStatusOnExit(ArrayList<Object> list) {
		Order order = (Order) list.get(0);
		if (conn != null) {
			try {
				// String query = "update orders set arrived=? where ordererId=? and visitDate=?
				// and visitTime=?;";
				PreparedStatement stmt = conn.prepareStatement(
						"update orders set orders.exit=? where ordererId=? and visitDate=? and visitTime=? and arrived=1;");

				stmt.setTime(1, Time.valueOf(LocalTime.now()));
				stmt.setString(2, order.getOrderer());
				stmt.setDate(3, (Date) order.getVisitDate());
				stmt.setTime(4, order.getVisitTime());
//				System.out.println(stmt);
				stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * this method take a new Order to update the database 'Table parks'numbers the
	 * visitor in park rightnow
	 * 
	 * @param list have in first index (Order order)
	 * @return numbers of visitors that exist in the park rightnow
	 */
	public static int updateNumberOfVisitors(ArrayList<Object> list) {
		Order order = (Order) list.get(0);
		Park park = parksList.get(order.getPark().getName());
		int numberOfVisitors = order.getNumOfVisitors();
		boolean isUnplanned = false;
		if (conn != null) {
			try {
				if (numberOfVisitors < 0 && park.getCurrentNumOfVisitors() == park.getMaxNumOfVisitor()) {// update in
																											// sql//
																											// that the
																											// park//
																											// stopped
																											// being//
																											// full
					PreparedStatement stmt2 = conn.prepareStatement(
							"update parkFullTimes set endTime=? where endTime IS NULL and parkName=?");
					stmt2.setTime(1, Time.valueOf(LocalTime.now()));
					stmt2.setString(2, park.getName());
					stmt2.executeUpdate();
				}
				park.setCurrentNumOfVisitors(park.getCurrentNumOfVisitors() + numberOfVisitors);
				if (order.getOrderType() == OrderType.UNPLANNEDGUIDE
						|| order.getOrderType() == OrderType.UNPLANNEDSUBSCRIBER
						|| order.getOrderType() == OrderType.UNPLANNEDTRAVELER) {
					park.setCurrentNumOfUnplannedVisitors(park.getCurrentNumOfUnplannedVisitors() + numberOfVisitors);
					isUnplanned = true;
				}
				PreparedStatement stmt = conn.prepareStatement(
						"update parks set currentNumOfVisitors=? ,CurrentNumOfUnplannedVisitors=?  where name=?");
				stmt.setInt(1, park.getCurrentNumOfVisitors());// changed by ayman
				stmt.setInt(2, park.getCurrentNumOfUnplannedVisitors());// changed by ayman
				stmt.setString(3, park.getName());
				stmt.executeUpdate();

				if (park.getCurrentNumOfVisitors() == park.getMaxNumOfVisitor())// update in sql that the park is now
																				// full
				{
					PreparedStatement stmt1 = conn
							.prepareStatement("Insert into timeParkFullVisitor values(?,?,?,null)");
					stmt1.setString(1, order.getPark().getName());
					stmt1.setDate(2, Date.valueOf(LocalDate.now()));
					stmt1.setTime(3, Time.valueOf(LocalTime.now()));
					stmt1.executeUpdate();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return park.getCurrentNumOfVisitors();
	}

	/**
	 * this method Should to take the sale in date visit should to do sql connection
	 * Sale getParkSales(String namePark) from name park and visit date should know
	 * if he have sale same park & date
	 * 
	 * @param String parkName , Date visitDate
	 * @param arr    have in first index (String parkName)
	 * @param arr    have in second index (Date visitDate )
	 * @return sale if we find in database 'Table sale' sale have the same park and
	 *         visit date
	 */
	public static Sale getParkSaleInVisitDate(ArrayList<Object> arr) {
		String parkName = (String) arr.get(0);
		Date visitDate = (Date) arr.get(1);
		ArrayList<Sale> sales = new ArrayList<>();
		Sale sale = null;
		if (conn != null) {
			try {
				/**
				 * select * from sales where sales.status ='confirmed' and sales.endDate >=
				 * ('"+visitDate2+"') and sales.startDate <=('"+visitDate2+"') and parkName
				 * ='"+parkName+"';"
				 */
				PreparedStatement ps = conn.prepareStatement(
						"select * from sales where  sales.status ='confirmed' and sales.endDate >= ? and sales.startDate <= ? and parkName = ? ");
				ps.setDate(1, visitDate);
				ps.setDate(2, visitDate);
				ps.setString(3, parkName);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					sale = new Sale(rs.getDate("startDate"), rs.getDate("endDate"), rs.getInt("salePercent"),
							rs.getString("saleName"), rs.getString("parkName"), rs.getString("status"),
							rs.getDate("sendDate"));
					sales.add(sale);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return sale;
	}

	/**
	 * this method insert to database 'Table waitingList' a new order the method
	 * insert the order just if it's doesn't exist another order in waitinglist
	 * 
	 * @param arr have in first index (Order )
	 * @return true if the order insert to database 'Table waitingList' another
	 *         return false
	 */
	public static boolean enterWaitingList(ArrayList<Object> arr) {
		Order order = (Order) arr.get(0);
		Time time = order.getVisitTime();
		String parkName = order.getPark().getName();

		if (conn != null)
			try {
				PreparedStatement ps = conn.prepareStatement(
						"select * from waitingList where visitTime < ?  and visitTime > ? and visitDate = ? and parkName = ? and orderStatus='waiting'");
				ps.setTime(1, addTimes(time, '+', parksList.get(parkName).getStayTime()));
				ps.setTime(2, addTimes(time, '-', parksList.get(parkName).getStayTime()));
				ps.setDate(3, order.getVisitDate());
				ps.setString(4, parkName);
				ResultSet rs = ps.executeQuery();
				if (!rs.next()) {
					ps = conn.prepareStatement("INSERT INTO waitingList values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					ps.setString(1, order.getPark().getName());
					ps.setDate(2, order.getVisitDate());
					ps.setTime(3, order.getVisitTime());
					ps.setInt(4, order.getNumOfVisitors());
					ps.setString(5, order.getEmail());
					ps.setString(6, order.getOrderer());
					ps.setString(7, order.getOrderType().toString().toUpperCase());
					ps.setDouble(8, order.getPrice());
					ps.setString(9, order.getOrderStatus());
					ps.setBoolean(10, false);
					ps.setBoolean(11, false);
					ps.setTime(12, null);
					ps.setString(13, order.getPhoneNumber());
					ps.setTime(14, null);
					ps.setTime(15, null);
					ps.executeUpdate();
					return true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return false;
	}

	/**
	 * this method delete specific order that exist or not in database 'Table orders
	 * & Table waitingList'
	 * 
	 * @param arr have in first index (Order )
	 * @return true if it's deleted successfully another return false
	 */
	public static boolean cancelOrderFromWaitingList(ArrayList<Object> arr) {
		Order order = (Order) arr.get(0);
		if (conn != null)
			try {
				PreparedStatement ps = conn.prepareStatement(
						"DELETE FROM waitingList  WHERE parkName = ? and visitDate = ? and visitTime = ? and ordererId = ?");
				ps.setString(1, order.getPark().getName());
				ps.setDate(2, order.getVisitDate());
				ps.setTime(3, order.getVisitTime());
				ps.setString(4, order.getOrderer());
				ps.executeUpdate();
				PreparedStatement ps2 = conn.prepareStatement(
						"delete from orders where ordererId=? and visitTime=? and visitDate=? and parkName=? ;");
				ps2.setString(1, order.getOrderer());
				ps2.setTime(2, order.getVisitTime());
				ps2.setDate(3, order.getVisitDate());
				ps2.setString(4, order.getPark().getName());
				ps2.executeUpdate();
				updateWaitingList(null);
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return false;
	}

	/**
	 * this method take order that been in waitinglist and confirm his order the
	 * method delete the order just from waitinglist table
	 * 
	 * @param arr have in first index (Order )
	 * @return true everytime
	 */
	public static boolean confirmOrderFromWaitingList(ArrayList<Object> arr) {
		Order order = (Order) arr.get(0);
		if (conn != null)
			try {
				PreparedStatement ps = conn.prepareStatement(
						"DELETE FROM waitingList  WHERE parkName = ? and visitDate = ? and visitTime = ? and ordererId = ?");
				ps.setString(1, order.getPark().getName());
				ps.setDate(2, order.getVisitDate());
				ps.setTime(3, order.getVisitTime());
				ps.setString(4, order.getOrderer());
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return true;
	}

	/**
	 * this method insert parameter to database 'Table parkparameters'
	 * 
	 * @param arr startdate , enddate , salePercent , saleName ,ParkName , Status
	 * @param arr have in first index (Date senddate)
	 * @param arr have in second index (String ParameterType)
	 * @param arr have in third index (String ParkName)
	 * @param arr have in fourth index (String status)
	 * @param arr have in fiveth index (String new value)
	 */
	public static void InsertParameter(ArrayList<Object> arr) {
		if (conn != null) {
			try {
				// arr[1] = senddate , arr[2] = parameterType , arr[3]
				// =parkName , arr[4] = Status arr[5]= value ,
				PreparedStatement stmt = conn.prepareStatement("INSERT INTO parkparameters VALUES ( ?, ?, ?, ?, ? );");
				stmt.setDate(1, (Date) arr.get(0)); // senddate
				stmt.setString(2, ((String) arr.get(1))); // ParameterType
				stmt.setString(3, (String) arr.get(2)); // ParkName
				stmt.setString(4, (String) arr.get(3)); // status
				stmt.setString(5, (String) arr.get(4)); // new value
				stmt.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * this method take park name and return all the Manager Request in
	 * ArrayList<ManagerRequest> that exist in database
	 * 
	 * @param arr have in first index (String ParkName )
	 * @return ArrayList<ManagerRequest> return all the Manager Request in specific
	 *         park
	 */
	public static ArrayList<ManagerRequest> getAllRequests(ArrayList<Object> arr) {
		String ParkName = (String) arr.get(0);
		ArrayList<ManagerRequest> result = new ArrayList<ManagerRequest>();
		if (conn != null) {
			try {
				PreparedStatement st = conn.prepareStatement("SELECT * FROM parkparameters where parkName = ? ;");
				st.setString(1, ParkName);
				ResultSet rs = st.executeQuery();
				while (rs.next()) {
					// need to change startdate to senddate
					result.add(new ParameterChangeRequest(ParameterType.valueOf(rs.getString("parameterType")),
							rs.getString("value"), parksList.get(rs.getString("parkName")), rs.getDate("sendDate"),
							rs.getString("status")));
				}
				st = conn.prepareStatement("SELECT * FROM sales where parkName = ? ;");
				st.setString(1, ParkName);
				rs = st.executeQuery();
				while (rs.next()) {
					result.add(new Sale(rs.getDate("startDate"), rs.getDate("endDate"), rs.getInt("salePercent"),
							rs.getString("saleName"), rs.getString("parkName"), rs.getString("status"),
							rs.getDate("sendDate")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * this method receive a parameter and update his status it receive
	 * newStatus,saleName ,parkName,startDate,OLDstatus
	 * 
	 * @param arr have in first index (String )
	 * @param arr have in second index (Date )
	 * @param arr have in third index (String )
	 * @param arr have in fourth index (String )
	 * @param arr have in fiveth index (String )
	 * @param arr have in sixth index (String )
	 * 
	 */
	public static void UpdateParameter(ArrayList<Object> arr) {
		// arr[0] = newStatus , arr[1] = STARTDAY , arr[2] =
		// ParameterType ,arr[3] = ParkName arr[4] = oldStatus, arr[5] = value

		if (conn != null) {
			try {
				// UPDATE parkparameters SET status= ' ' WHERE AND STARTDAY = ' ' AND EndDate =
				// ' ' And ParameterType =' ' and ParkName = ' ' And Status = ' ';
				String str = "UPDATE parkparameters set status = '";
				str += (String) arr.get(0);
				str += "' where sendDate = '";
				str += (Date) arr.get(1);

				str += "' And ParameterType = '" + (String) arr.get(2) + "' And ParkName = '" + (String) arr.get(3)
						+ "' and status = '" + (String) arr.get(4) + "';";
				Statement stmt;
				PreparedStatement ps;
				stmt = conn.createStatement();
				stmt.executeUpdate(str);
				if (((String) arr.get(0)).equals("confirmed"))
					switch ((String) arr.get(2)) {
					case "MAXNUMBEROFVISITORS":
						parksList.get((String) arr.get(3)).setMaxNumOfVisitor(Integer.valueOf((String) arr.get(5)));
						ps = conn.prepareStatement("update parks set maxNumOfVisitors = ? where name = ?");
						ps.setInt(1, Integer.valueOf((String) arr.get(5)));
						ps.setString(2, (String) arr.get(3));
						ps.executeUpdate();
						break;
					case "MAXNUMBEROFORDER":
						parksList.get((String) arr.get(3)).setMaxNumOfOrders(Integer.valueOf((String) arr.get(5)));
						ps = conn.prepareStatement("update parks set maxNumOfOrders = ? where name = ?");
						ps.setInt(1, Integer.valueOf((String) arr.get(5)));
						ps.setString(2, (String) arr.get(3));
						ps.executeUpdate();
						break;
					case "STAYTIME":
						parksList.get((String) arr.get(3)).setStayTime(Time.valueOf((String) arr.get(5)));
						ps = conn.prepareStatement("update parks set stayTime = ? where name = ?");
//						System.out.println(arr.get(5));
						ps.setTime(1, Time.valueOf((String) arr.get(5)));
						ps.setString(2, (String) arr.get(3));
						ps.executeUpdate();
						break;

					default:
						break;
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * this method receive a sale and status and insert it to database'Table sales'
	 * Receive : startdate, enddate , salePercent , saleName, ParkName , Status
	 * 
	 * @param arr have in first index (Date startdate)
	 * @param arr have in second index (Date enddate)
	 * @param arr have in third index (Integer salePercent)
	 * @param arr have in fourth index (String saleName )
	 * @param arr have in fiveth index (String ParkName )
	 * @param arr have in sixth index (String Status )
	 * @param arr have in seventh index (Date sendDate)
	 * @return true if the sale insert to database successfully another return false
	 */
	public static boolean InsertSale(ArrayList<Object> arr) {
		if (conn != null) {
			try {
				// arr[0] = startdate , arr[1] = enddate , arr[2] = salePercent , arr[3]
				// =saleName , arr[4]= ParkName , arr[5] = Status
				PreparedStatement ps = conn.prepareStatement(
						"SELECT * FROM sales WHERE parkName=? AND ((startdate >= ? AND  endDate <= ?) or (startDate <= ?  and endDate >= ?) or (startDate<= ? and enddate >= ?));");
				ps.setString(1, (String) arr.get(4));
				ps.setDate(2, (Date) arr.get(0));
				ps.setDate(3, (Date) arr.get(1));
				ps.setDate(4, (Date) arr.get(0));
				ps.setDate(5, (Date) arr.get(0));
				ps.setDate(6, (Date) arr.get(1));
				ps.setDate(7, (Date) arr.get(1));
				ResultSet rs = ps.executeQuery();
				if (rs.next())
					return false;
				PreparedStatement stmt = conn.prepareStatement("INSERT INTO sales VALUES ( ?, ?, ?, ?, ? , ?,? ) ");
				stmt.setDate(1, (Date) arr.get(0)); // startdate
				stmt.setDate(2, (Date) arr.get(1)); // enddate
				stmt.setInt(3, ((Integer) arr.get(2))); // salePercent
				stmt.setString(4, (String) arr.get(3)); // saleName
				stmt.setString(5, (String) arr.get(4)); // ParkName
				stmt.setString(6, (String) arr.get(5)); // Status
				stmt.setDate(7, (Date) arr.get(6)); // sendDate
				stmt.executeUpdate();
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * this method receive a sale and update his status it receive
	 * newStatus,saleName ,parkName,startDate,OLDstatus
	 * 
	 * @param arr have in first index (StringnewStatus )
	 * @param arr have in second index (String saleName )
	 * @param arr have in third index (String parkName )
	 * @param arr have in fourth index (Date startDate )
	 * @param arr have in fiveth index (StringOLDstatus )
	 */
	public static void UpdateSaleStatus(ArrayList<Object> arr) {

		// arr[0] = newStatus , arr[1] = saleName , arr[2] = parkName , arr[3] =
		// startDate ,arr[4] = OLDstatus

		if (conn != null) {
			try {
				// UPDATE SALE SET STATUS= ' ' WHERE SALENAME =' ' AND PARKNAME =' ' AND
				// STARTDAY = ' ' AND status = ' '
				String str = "UPDATE sales set status = '";
				str += (String) arr.get(0);
				str += "' where saleName = '";
				str += (String) arr.get(1);
				str += "' And parkName = '";
				str += (String) arr.get(2);
				str += "' And startDate = '" + (Date) arr.get(3) + "' And status = '" + (String) arr.get(4) + "';";
				Statement stmt;
				stmt = conn.createStatement();
				stmt.executeUpdate(str);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/***************************************************************************
	 * Visit Report
	 ******************************************************************************/

	/**
	 * this method prepare all the data to visit report and return back for client
	 * ArrayList<Time> to create the report
	 * 
	 * @param arr have in first index (Date )
	 * @param arr have in second index (String parkName )
	 * @return ArrayList<Time> to create the report
	 */
	@SuppressWarnings("deprecation")
	public static ArrayList<Time> MakeVisitReport(ArrayList<Object> arr) {
		Time tempTime;
		ArrayList<Time> result = new ArrayList<>();
		if (conn != null) {
			int month = ((Date) arr.get(0)).getMonth() + 1;
			int year = ((Date) arr.get(0)).getYear() + 1900;
			String parkName = (String) arr.get(1);
			try {
				// SQL SELECT query1
				String query = "Select * FROM orders WHERE month(visitDate) = '";
				query += month;
				query += "' and year(visitDate) = '";
				query += year + "' AND arrived = 1 And parkName ='" + parkName + "';";
//				System.out.println(query);
				Statement st = conn.createStatement();
				// execute the query's, and get a java resultset
				ResultSet rs = st.executeQuery(query);
				// Moving the cursor to the last row
				while (rs.next()) {
					tempTime = rs.getTime("visitTime");
					Time T1 = (Time) addTimes(tempTime, '+', new Time(4, 0, 0));
					if (T1.getHours() > 19)
						T1 = new Time(19, 0, 0);
					Time T2 = rs.getTime("entrance");
					result.add(T2);
					result.add(new Time(T1.getHours() - T2.getHours(), T1.getMinutes() - T2.getMinutes(),
							T1.getSeconds() - T2.getSeconds()));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return result;

	}

	/**
	 * this method take park name to get from database 'Table parks' value of the
	 * parameters
	 * 
	 * @param arr have in first index (String ParkName )
	 * @return ArrayList<Object> inside this ArrayList we have
	 *         (stayTime,maxNumOfVisitors,maxNumOfOrders)
	 */
	public static ArrayList<Object> getParkMangerParameters(ArrayList<Object> arr) {
		ArrayList<Object> result = new ArrayList<>();
		String ParkName = (String) arr.get(0);
		if (conn != null) {
			try {
				PreparedStatement stmt = conn.prepareStatement("Select * from parks where name = ?;");
				stmt.setString(1, ParkName); // parkname
				ResultSet rs = stmt.executeQuery();
				rs.next();
				result.add(rs.getTime("stayTime"));
				result.add(rs.getInt("maxNumOfVisitors"));
				result.add(rs.getInt("maxNumOfOrders"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * this method take from database 'Table subscribers' all the Subscribers and
	 * return him in ArrayList<Subscriber>
	 * 
	 * @param empty
	 * @return all the Subscribers in database in ArrayList<Subscriber>
	 */
	public static ArrayList<Subscriber> getAllSubscribers() {
		ArrayList<Subscriber> array = new ArrayList<>();
		if (conn != null)
			try {
				String query = "Select * FROM subscribers";
				Statement st = conn.createStatement();
				// execute the query, and get a java resultset
				ResultSet rs = st.executeQuery(query);
				while (rs.next()) {
					Subscriber s = new Subscriber(rs.getString("subscriptionNumber"), rs.getString("idNumber"),
							rs.getString("FirstName"), rs.getString("LastName"), null, null, 0, null);
					array.add(s);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return array;
	}

	/**
	 * this method take from database all the traveler that we have in database and
	 * return it in ArrayList<Traveler>
	 * 
	 * @param empty
	 * @return ArrayList<Traveler> that have inside all the traveler in database
	 *         'Tabel Traveler'
	 */
	public static ArrayList<Traveler> getAllGuides() {

		ArrayList<Traveler> array = new ArrayList<>();
		if (conn != null)
			try {
				String query = "Select * FROM travelers where isGuide=1";
				Statement st = conn.createStatement();

				// execute the query, and get a java resultset
				ResultSet rs = st.executeQuery(query);
				while (rs.next()) {
					Traveler t = new Traveler(rs.getString("idNumber"), true);
					array.add(t);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return array;
	}

	/**
	 * this method take from database all the orders in waitinglist by idNumber and
	 * return it to client
	 * 
	 * @param arr have in first index (String idNumber )
	 * @return ArrayList<Order> that have inside it all the orders in waitinglist by
	 *         idNumber
	 */
	public static ArrayList<Order> getAllOrdersInWaitingListForTraveler(ArrayList<Object> arr) {

		ArrayList<Order> orders = new ArrayList<Order>();
		String idNumber = (String) arr.get(0);

		if (conn != null) {
			try {
				PreparedStatement ps = conn.prepareStatement(
						"select * from gonature.waitinglist where ordererId = ? and  visitDate >= ? ");
				ps.setString(1, idNumber);
				ps.setDate(2, Date.valueOf(LocalDate.now()));

//				System.out.println(ps.toString());
				ResultSet rs = ps.executeQuery();
				while (rs.next())
					orders.add(new Order(parksList.get(rs.getString("parkName")), rs.getDate("visitDate"),
							rs.getTime("visitTime"), rs.getInt("numOfvisitors"), rs.getString("email"),
							rs.getString("ordererId"), OrderType.valueOf(rs.getString("orderType").toUpperCase()),
							rs.getDouble("price"), rs.getString("orderStatus"), rs.getBoolean("payStatus"),
							rs.getBoolean("arrived"), rs.getString("phoneNumber"), rs.getTime("entrance"),
							rs.getTime("exit")));
				return orders;

			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * this method refresh the database 'Table waitinglist' and check if can someone
	 * in waitinglist to move on 'Table orders'
	 * 
	 * @param arr is null
	 * @return true if refresh the database 'Table waitinglist' successfully another
	 *         false
	 */
	public static boolean updateWaitingList(ArrayList<Object> arr) {
		Order order;

		if (conn != null) {
			try {
				PreparedStatement ps = conn
						.prepareStatement("select * from gonature.waitinglist where  orderStatus ='waiting';");

//				System.out.println(ps.toString());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					ArrayList<Object> arr2 = new ArrayList<Object>();
					order = (new Order(parksList.get(rs.getString("parkName")), rs.getDate("visitDate"),
							rs.getTime("visitTime"), rs.getInt("numOfvisitors"), rs.getString("email"),
							rs.getString("ordererId"), OrderType.valueOf(rs.getString("orderType").toUpperCase()),
							rs.getDouble("price"), rs.getString("orderStatus"), rs.getBoolean("payStatus"),
							rs.getBoolean("arrived"), rs.getString("phoneNumber"), rs.getTime("entrance"),
							rs.getTime("exit")));

					arr2.add(rs.getTime("visitTime"));
					arr2.add(rs.getDate("visitDate"));
					arr2.add(rs.getInt("numOfvisitors"));
					arr2.add(rs.getString("parkName"));
					if (checkAvailibility(arr2)) {
						EchoServer.serverController.writeToConsole(
								"[" + "sending sms to: " + order.getPhoneNumber() + "  sending email to: "
										+ order.getEmail() + "  message : Congratulations ! a spot has opened up for "
										+ order.getVisitDate() + " on " + order.getVisitTime()
										+ " , you have 1 hour from now to confirm/cancel your place in the park]");
						arr2.clear();
						arr2.add(order);
						moveFromWaitingListToOrders(arr2);
					}
				}
				return true;

			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * this method take order that exist in waitinglist and confirm his status it's
	 * mean like his place in waitinglist is come rightnow
	 * 
	 * @param arr have in first index (Order )
	 * @return true if update the database 'Table waitinglist' successfully another
	 *         false
	 */
	@SuppressWarnings("deprecation")
	public static boolean moveFromWaitingListToOrders(ArrayList<Object> arr) {
		Order order = (Order) arr.get(0);
		if (conn != null) {
			if (addOrder(arr)) {
//				System.out.println("order added");
				Time t = Time.valueOf(LocalTime.now());
				t.setHours(t.getHours() + 1);
				try {
					PreparedStatement ps = conn.prepareStatement(
							"update waitingList set lastTimeToConfirm= ? ,orderStatus = ?  where ordererId = ? and visitTime = ? and visitDate = ? and parkName=?");

					ps.setTime(1, t);
					ps.setString(2, "confirmed");
					ps.setString(3, order.getOrderer());
					ps.setTime(4, order.getVisitTime());
					ps.setDate(5, order.getVisitDate());
					ps.setString(6, order.getPark().getName());
					ps.executeUpdate();
					t.setHours(t.getHours() - 1);
					return true;
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * this method Delete orders From database 'Table WaitingList & Table Orders'
	 * that time for confirm ord cancel passed
	 * 
	 * @param arr2 is null
	 * @return true if no exception come in code
	 */
	public static boolean statusOrderInWaitingList(ArrayList<Object> arr2) {
		Order order;
		if (conn != null) {
			try {
				PreparedStatement ps = conn.prepareStatement(
						"select * from waitingList where (lastTimeToConfirm <= ? and orderStatus = ?) or (visitDate<?) or (visitTime<? and visitDate=?) ;");

				ps.setTime(1, Time.valueOf(LocalTime.now()));
				ps.setString(2, "confirmed");
				ps.setDate(3, Date.valueOf(LocalDate.now()));
				ps.setTime(4, Time.valueOf(LocalTime.now()));
				ps.setDate(5, Date.valueOf(LocalDate.now()));
				// System.out.println(ps.toString());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					order = (new Order(parksList.get(rs.getString("parkName")), rs.getDate("visitDate"),
							rs.getTime("visitTime"), rs.getInt("numOfvisitors"), rs.getString("email"),
							rs.getString("ordererId"), OrderType.valueOf(rs.getString("orderType").toUpperCase()),
							rs.getDouble("price"), rs.getString("orderStatus"), rs.getBoolean("payStatus"),
							rs.getBoolean("arrived"), rs.getString("phoneNumber"), rs.getTime("entrance"),
							rs.getTime("exit")));
					EchoServer.serverController.writeToConsole("[" + "sending sms to: " + order.getPhoneNumber()
							+ "  sending email to: " + order.getEmail()
							+ "  message : Your place in the waiting list for " + order.getVisitDate() + " on "
							+ order.getVisitTime() + " ,was automatically cancelled becuase you haven't replied ]");
					ArrayList<Object> arr = new ArrayList<Object>();
					arr.add(order);
					cancelOrderFromWaitingList(arr);
				}
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * this method update status orders to unconfirmed at exist in database 'Table
	 * Orders' if time passed the method search in database 'Table orders' the
	 * orders that should to update his status order
	 * 
	 * @param arr is null
	 */
	@SuppressWarnings({ "deprecation" })
	public static void deleteUnconfirmedOrders(ArrayList<Object> arr) {
		Order order;
		if (conn != null) {
			try {
				Date currDate = Date.valueOf(LocalDate.now());
				Time currTime = Time.valueOf(LocalTime.now());
				PreparedStatement ps2 = conn.prepareStatement(
						"select * from orders where (visitDate<? and orderStatus='waiting') or (visitDate=? and orderStatus='waiting' and visitTime<?);");
				ps2.setDate(1, currDate);
				ps2.setDate(2, new Date(currDate.getYear(), currDate.getMonth(), currDate.getDate() + 1));
				ps2.setTime(3, new Time(currTime.getHours() - 2, currTime.getMinutes(), currTime.getSeconds()));
				ResultSet rs = ps2.executeQuery();
				while (rs.next()) {
					order = (new Order(parksList.get(rs.getString("parkName")), rs.getDate("visitDate"),
							rs.getTime("visitTime"), rs.getInt("numOfvisitors"), rs.getString("email"),
							rs.getString("ordererId"), OrderType.valueOf(rs.getString("orderType").toUpperCase()),
							rs.getDouble("price"), rs.getString("orderStatus"), rs.getBoolean("payStatus"),
							rs.getBoolean("arrived"), rs.getString("phoneNumber"), rs.getTime("entrance"),
							rs.getTime("exit")));
					EchoServer.serverController.writeToConsole("[" + "sending sms to: " + order.getPhoneNumber()
							+ "  sending email to: " + order.getEmail() + "  message : Your order for tommorow on "
							+ order.getVisitTime() + " ,was automatically cancelled becuase you haven't replied ]");
				}

				PreparedStatement ps = conn.prepareStatement(
						"update orders set orderStatus='unconfirmed' where (visitDate<? and orderStatus='waiting') or (visitDate=? and orderStatus='waiting' and visitTime<?);");
				ps.setDate(1, currDate);
				ps.setDate(2, new Date(currDate.getYear(), currDate.getMonth(), currDate.getDate() + 1));
				ps.setTime(3, new Time(currTime.getHours() - 2, currTime.getMinutes(), currTime.getSeconds()));
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * this method send notify in 'sms and email' to traveler for make choose if he
	 * want to come or not the traveler have 2 hours to confirm the order and if the
	 * time passed the status order will change to unconfirmed automation
	 * 
	 * @param arr is null
	 * 
	 */
	public static void notifyOrdersOneDayBefore(ArrayList<Object> arr) {
		Order order;
		if (conn != null) {
			try {
				PreparedStatement ps = conn.prepareStatement(
						"select * from orders where visitDate=? and visitTime=? and orderStatus='waiting';");
				Date currDate = Date.valueOf(LocalDate.now());
				Time currTime = Time.valueOf(LocalTime.now());
				ps.setDate(1, new Date(currDate.getYear(), currDate.getMonth(), currDate.getDate() + 1));
				ps.setTime(2, currTime);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					order = (new Order(parksList.get(rs.getString("parkName")), rs.getDate("visitDate"),
							rs.getTime("visitTime"), rs.getInt("numOfvisitors"), rs.getString("email"),
							rs.getString("ordererId"), OrderType.valueOf(rs.getString("orderType").toUpperCase()),
							rs.getDouble("price"), rs.getString("orderStatus"), rs.getBoolean("payStatus"),
							rs.getBoolean("arrived"), rs.getString("phoneNumber"), rs.getTime("entrance"),
							rs.getTime("exit")));
					EchoServer.serverController.writeToConsole("[" + "sending sms to: " + order.getPhoneNumber()
							+ "  sending email to: " + order.getEmail() + "  message : Your order is for tommorow! on "
							+ order.getVisitTime()
							+ " ,you have 2 hours from now to confirm/cancel on the GoNature application ]");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	////////////////////////////////// Reports///////////////////////////////////////////////////
	/**
	 * this method creates usage report using database
	 * 
	 * @param arr have in first index (Date )
	 * @param arr have in second index (String parkName )
	 * @return arraylist<reportHelper> containing the report information
	 */
	@SuppressWarnings("deprecation")
	public static ArrayList<reportHelper> createUsageReport(ArrayList<Object> arr) {

		ArrayList<Time> times = new ArrayList<>();
		HashMap<Date, ArrayList<Time>> result = new HashMap<>();

		ArrayList<reportHelper> report = new ArrayList<>();

		int month = ((Date) arr.get(1)).getMonth();
		int year = ((Date) arr.get(1)).getYear();
		YearMonth yMonth = YearMonth.of(year, month + 1);
		int days = yMonth.lengthOfMonth();
		String parkName = (String) arr.get(2);
		if (conn != null) {
			try {
				for (int i = 1; i <= days; i++) {
					Date currDate = new Date(year, month, i);
					PreparedStatement pStatement = conn.prepareStatement(
							"select startTime,endTime from parkFullTimes where parkName=? and visitDate=? ORDER BY startTime");
					pStatement.setString(1, parkName);
					pStatement.setDate(2, currDate);
					ResultSet rs = pStatement.executeQuery();
					while (rs.next()) {
						times.add(rs.getTime("startTime"));
						times.add(rs.getTime("endTime"));
					}
					Time endingTime = new Time(8, 0, 0);// park openning time
					ArrayList<Time> currArr = new ArrayList<>();
					for (int j = 0; j < times.size(); j += 2) {
						if (times.get(j).compareTo(endingTime) != 0) {
							currArr.add(endingTime);
							currArr.add(times.get(j));
						}
						endingTime = times.get(j + 1);
					}
					if (endingTime.compareTo(new Time(19, 0, 0)) != 0) {
						currArr.add(endingTime);
						currArr.add(new Time(19, 0, 0));
					}
					result.put(currDate, currArr);
					times.clear();
				}
			} catch (SQLException e) {
				e.printStackTrace();

			}

			for (int i = 1; i <= result.size(); i++) {

				ArrayList<Time> times2 = result.get(new Date(year, month, i));
				if (times2.size() != 0) {

					for (int j = 0; j < times2.size(); j++) {

						report.add(new reportHelper(new Date(year, month, i), times2.get(j) + "," + times2.get(j + 1)));
						j++;
					}
				}
			}

		}
		return report;
	}

	/*******************************
	 * all report table's neeed to start with "monthreport" field and "reportyear"
	 * field
	 *********************************/

	/*************************************************************
	 * Common Function's To Reports
	 ***************************************************************************/

	/**
	 * This method make report about order's that cancelled and order's than doesn't
	 * confirmed and cancelled * @param arr of integer
	 * 
	 * @return arrayList hold the number of the cancelled order's and about the
	 *         number of order's that doesn't confirmed and cancelled
	 * 
	 */
	@SuppressWarnings("deprecation")
	public static ArrayList<Integer> MakeCancelReport(ArrayList<Object> arr) {

		ArrayList<Integer> res = new ArrayList<>();
		String parkName = (String) arr.get(2);
		Date date = (Date) arr.get(1);
		int year = date.getYear() + 1900;
		int month = date.getMonth() + 1;
		if (conn != null) {
			// arr[0] = date
			try {

				String query = "Select COUNT(*) FROM orders WHERE month(visitDate)  = '";
				query += month;
				query += "' AND orderStatus = 'unconfirmed' AND   year(visitDate)  ='" + year + "' And  parkName ='"
						+ parkName + "';";

				String query2 = "Select COUNT(*) FROM orders WHERE  month(visitDate)  = '";
				query2 += month;
				query2 += "'  AND year(visitDate) ='" + year
						+ "' And orderStatus = 'confirmed' And arrived = 0 And  parkName ='" + parkName + "';";
				Statement st = conn.createStatement();

				// execute the query's, and get a java resultset
				ResultSet rs = st.executeQuery(query);
				// Moving the cursor to the last row
				rs.next();
				res.add(rs.getInt("count(*)"));

				// execute the query's, and get a java resultset
				ResultSet rs2 = st.executeQuery(query2);
				// Moving the cursor to the last row
				rs2.next();
				res.add(rs2.getInt("count(*)"));

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return res;

	}

	/**
	 * this method check if the report is already exist in the DB - it receive the
	 * report type and the month and year of the report all received parameters
	 * 
	 * @param arr have in first index (String reportType)
	 * @param arr have in second index (Date date)
	 * @param arr have in third index (String ParkName)
	 * @return boolean value if the report of the received month is existed in the
	 *         table or not
	 */
	@SuppressWarnings("deprecation")
	public static boolean IsReportExisted(ArrayList<Object> arr) {

		if (conn != null) {
			String reportType = (String) arr.get(0);
			// arr[1] = date
			String ParkName = (String) arr.get(2);
			Date date = (Date) arr.get(1);

			try {
				PreparedStatement ps = conn.prepareStatement(
						"select * from reports where month(reportDate) = ? and year(reportDate) = ?  and reportType = ? and park = ? ;");
				ps.setInt(1, date.getMonth() + 1);
				ps.setInt(2, date.getYear() + 1900);
				ps.setString(3, reportType);
				ps.setString(4, ParkName);

				ResultSet result = ps.executeQuery();
				if (result.next()) {
					// data exist
					return true;
				} else {
					// data not exist
					return false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;

	}

	/**
	 * this method get the report from the database and return it as byte array
	 * 
	 * @param arr have in first index (String reportType)
	 * @param arr have in second index (Date date )
	 * @param arr have in third index (String ParkName)
	 * @return byte[] array
	 */
	@SuppressWarnings("deprecation")
	public static String GetReport(ArrayList<Object> arr) {
		ResultSet rs = null;
		if (conn != null) {
			String reportType = (String) arr.get(0);
			Date date = (Date) arr.get(1);
			String ParkName = (String) arr.get(2);

			try {
				PreparedStatement st = conn.prepareStatement(
						"SELECT * FROM reports where park = ? and month(reportDate) = ? and year(reportDate) = ? and reportType = ? ;");
				st.setString(1, ParkName);
				st.setInt(2, date.getMonth() + 1);
				st.setInt(3, date.getYear() + 1900);
				st.setString(4, reportType);

				rs = st.executeQuery();
				if (rs.next()) {
					return rs.getString("Data");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * this method take report and insert it to database 'Table reports'
	 * 
	 * @param arr have in first index (String reportType )
	 * @param arr have in second index (Date date )
	 * @param arr have in third index (ArrayList<String> data )
	 * @param arr have in fourth index (String parkName )
	 */
	public static void InsertReports(ArrayList<Object> arr) {
		String reportType = (String) arr.get(0);
		Date date = (Date) arr.get(1);
		ArrayList<String> data = (ArrayList<String>) arr.get(2);
		String parkName = (String) arr.get(3);
		String str = "";
		for (int i = 0; i < data.size(); i++) {
			str += data.get(i) + " ";
		}
		try {
			PreparedStatement stmt;
			stmt = conn.prepareStatement("insert into reports values(?,?,?,?);");

			stmt.setString(1, reportType);
			stmt.setDate(2, date);
			stmt.setString(3, str);
			stmt.setString(4, parkName);
			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	/**
	 * this method prepare all the data of the all visitors report from the sql
	 * 
	 * @param arr have in first index (Date )
	 * @param arr have in first index (String parkName )
	 * @return ArrayList of long hold the number of total PLANNED_TRAVELER
	 *         ,PLANNED_SUBSCRIBER , PLANNED_GUIDE ,UNPLANNED_TRAVELER
	 *         ,UNPLANNED_SUBSCRIBER ,UNPLANNED_GUIDE in the sql in this month
	 */
	@SuppressWarnings("deprecation")
	public static ArrayList<Long> MakeTotalVisitorsReport(ArrayList<Object> arr) {
		ArrayList<Long> result = new ArrayList<Long>();
		long PLANNEDTRAVELER = 0, PLANNEDSUBSCRIBER = 0, PLANNEDGUIDE = 0, UNPLANNEDTRAVELER = 0,
				UNPLANNEDSUBSCRIBER = 0, UNPLANNEDGUIDE = 0;
		if (conn != null) {
			int month = ((Date) arr.get(1)).getMonth() + 1;
			int year = ((Date) arr.get(1)).getYear() + 1900;
			String parkName = (String) arr.get(2);
//			System.out.println(parkName + "" + month + "" + year);
			try {
				// SQL SELECT query1
				String query = "Select * FROM orders WHERE month(visitDate) = '";
				query += month;
				query += "' and year(visitDate) = '";
				query += year + "' AND arrived = 1 And parkName ='" + parkName + "';";
				Statement st = conn.createStatement();
				// execute the query's, and get a java resultset
				ResultSet rs = st.executeQuery(query);
//				System.out.println(query);
				// Moving the cursor to the last row
				while (rs.next()) {
					if (rs.getString("orderType").equals("PLANNEDTRAVELER"))
						PLANNEDTRAVELER += rs.getInt("numOfVisitors");
					if (rs.getString("orderType").equals("PLANNEDSUBSCRIBER"))
						PLANNEDSUBSCRIBER += rs.getInt("numOfVisitors");
					if (rs.getString("orderType").equals("PLANNEDGUIDE"))
						PLANNEDGUIDE += rs.getInt("numOfVisitors");
					if (rs.getString("orderType").equals("UNPLANNEDTRAVELER"))
						UNPLANNEDTRAVELER += rs.getInt("numOfVisitors");
					if (rs.getString("orderType").equals("UNPLANNEDSUBSCRIBER"))
						UNPLANNEDSUBSCRIBER += rs.getInt("numOfVisitors");
					if (rs.getString("orderType").equals("UNPLANNEDGUIDE"))
						UNPLANNEDGUIDE += rs.getInt("numOfVisitors");
				}
				result.add(PLANNEDTRAVELER);
				result.add(PLANNEDSUBSCRIBER);
				result.add(PLANNEDGUIDE);
				result.add(UNPLANNEDTRAVELER);
				result.add(UNPLANNEDSUBSCRIBER);
				result.add(UNPLANNEDGUIDE);
				result.addAll(getNumByDayOfWeek((Date) arr.get(1), parkName, OrderType.PLANNEDTRAVELER.toString()));
				result.addAll(getNumByDayOfWeek((Date) arr.get(1), parkName, OrderType.PLANNEDSUBSCRIBER.toString()));
				result.addAll(getNumByDayOfWeek((Date) arr.get(1), parkName, OrderType.PLANNEDGUIDE.toString()));
				result.addAll(getNumByDayOfWeek((Date) arr.get(1), parkName, OrderType.UNPLANNEDTRAVELER.toString()));
				result.addAll(getNumByDayOfWeek((Date) arr.get(1), parkName, OrderType.UNPLANNEDSUBSCRIBER.toString()));
				result.addAll(getNumByDayOfWeek((Date) arr.get(1), parkName, OrderType.UNPLANNEDGUIDE.toString()));

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * this method get (day , park name , traveler type) and take from database
	 * 'Table Orders' number the orders is specfic park by date and time
	 * 
	 * @param Date   d
	 * @param String ParkName
	 * @param String TravelerType
	 * @return ArrayList<Long> inside the array we return numbers the orders that we
	 *         take from database
	 */
	@SuppressWarnings("deprecation")
	public static ArrayList<Long> getNumByDayOfWeek(Date d, String ParkName, String TravelerType) {
		int month = d.getMonth() + 1;
		int year = d.getYear() + 1900;
		ArrayList<Long> arr = new ArrayList<Long>();
		ResultSet rs;
		if (conn != null) {
			try {
				PreparedStatement ps;
				for (int i = 1; i < 8; i++) {
					ps = conn.prepareStatement(
							"select sum(numOfVisitors) from orders where parkName = ? and month(visitDate) = ? and  year(visitDate) = ? and  DAYOFWEEK(visitDate) = ? and arrived = 1 and orderType = ? ");
					ps.setString(1, ParkName);
					ps.setInt(2, month);
					ps.setInt(3, year);
					ps.setInt(4, i);
					ps.setString(5, TravelerType);

					rs = ps.executeQuery();
					if (rs.next())
						arr.add(rs.getLong(1));
//					System.out.println(arr);

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return arr;
	}

	/**********************************************************
	 * monethly income Report
	 ***************************************************************/

	/**
	 * this method prepare all the data to income report
	 * 
	 * @param arr have in first index (Order ) : Date , parkName
	 * @param arr have in second index (Order )
	 * @param arr have in third index (Order )
	 * @return income of the received month
	 */
	@SuppressWarnings("deprecation")
	public static ArrayList<Double> MakeMonethlyIncomeReport(ArrayList<Object> arr) {

		double Income = 0, PLANNEDTRAVELER = 0, PLANNEDSUBSCRIBER = 0, PLANNEDGUIDE = 0, UNPLANNEDTRAVELER = 0,
				UNPLANNEDSUBSCRIBER = 0, UNPLANNEDGUIDE = 0;
		if (conn != null) {

			int month = ((Date) arr.get(1)).getMonth() + 1;
			int year = ((Date) arr.get(1)).getYear() + 1900;
			String parkName = (String) arr.get(2);
			try {
				// Select * FROM orders WHERE month(visitDate) = 4 and year(visitDate) = ' ';

				// SQL SELECT query1
				String query = "Select * FROM orders WHERE month(visitDate) = '";
				query += month;
				query += "' and year(visitDate) = '";
				query += year + "' AND arrived = true And parkName ='" + parkName + "';";
//				System.out.println(query);
				// System.out.println(query);
				Statement st = conn.createStatement();
				// execute the query's, and get a java resultset
				ResultSet rs = st.executeQuery(query);
				// Moving the cursor to the last row
				while (rs.next()) {
					if (rs.getString("orderType").equals("PLANNEDTRAVELER"))
						PLANNEDTRAVELER += rs.getDouble("price");
					if (rs.getString("orderType").equals("PLANNEDSUBSCRIBER"))
						PLANNEDSUBSCRIBER += rs.getDouble("price");
					if (rs.getString("orderType").equals("PLANNEDGUIDE"))
						PLANNEDGUIDE += rs.getDouble("price");
					if (rs.getString("orderType").equals("UNPLANNEDTRAVELER"))
						UNPLANNEDTRAVELER += rs.getDouble("price");
					if (rs.getString("orderType").equals("UNPLANNEDSUBSCRIBER"))
						UNPLANNEDSUBSCRIBER += rs.getDouble("price");
					if (rs.getString("orderType").equals("UNPLANNEDGUIDE"))
						UNPLANNEDGUIDE += rs.getDouble("price");
					Income += rs.getDouble("price");

				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		ArrayList<Double> result = new ArrayList<>();
		result.add(PLANNEDTRAVELER);
		result.add(PLANNEDSUBSCRIBER);
		result.add(PLANNEDGUIDE);
		result.add(UNPLANNEDTRAVELER);
		result.add(UNPLANNEDSUBSCRIBER);
		result.add(UNPLANNEDGUIDE);
		result.add(Income);
		return result;

	}

	/**
	 * this method take (date,park name, time) and check number of visitor in park
	 * at specific date and time the method return ArrayList<Integer> that have
	 * number of orders that we take from database 'Table Orders'
	 * 
	 * @param Date   d
	 * @param String parkName
	 * @param String t
	 * @return ArrayList<Integer> that have number of orders that we take from
	 *         database
	 */
	public static ArrayList<Integer> numOfVisitorsByTime(Date d, String parkName, String t) {
		ArrayList<Integer> arr = null;
		int month = d.getMonth() + 1, year = d.getYear() + 1900;

		if (conn != null) {
			arr = new ArrayList<Integer>();
			try {
				ResultSet rs;
				PreparedStatement ps = conn.prepareStatement(
						"SELECT sum(numOfVisitors) FROM orders where parkName = ? and month(visitDate) = ? and year(visitDate) = ? and arrived = 1 and orderType=? and hour(entrance)>=8 and  hour(entrance)<12; ");

				ps.setString(1, parkName);
				ps.setInt(2, month);
				ps.setInt(3, year);
				ps.setString(4, t);

				rs = ps.executeQuery();
				if (rs.next())
					arr.add(rs.getInt(1));
				ps = conn.prepareStatement(
						"SELECT sum(numOfVisitors) FROM orders where parkName = ? and month(visitDate) = ? and year(visitDate) = ? and arrived = 1 and orderType=? and hour(entrance)>=12 and  hour(entrance)<16; ");

				ps.setString(1, parkName);
				ps.setInt(2, month);
				ps.setInt(3, year);
				ps.setString(4, t);

				rs = ps.executeQuery();

				if (rs.next())
					arr.add(rs.getInt(1));

				ps = conn.prepareStatement(
						"SELECT sum(numOfVisitors) FROM orders where parkName = ? and month(visitDate) = ? and year(visitDate) = ? and arrived = 1 and orderType=? and hour(entrance)>=16 and  hour(entrance)<=23; ");

				ps.setString(1, parkName);
				ps.setInt(2, month);
				ps.setInt(3, year);
				ps.setString(4, t);

				rs = ps.executeQuery();

				if (rs.next())
					arr.add(rs.getInt(1));

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return arr;
	}

	/**
	 * this method take (date,park name, order type) and check number of visitor in
	 * park at specific date and order type the method return ArrayList<Integer>
	 * that have number of orders that we take from database 'Table Orders'
	 * 
	 * @param Date   d
	 * @param String parkName
	 * @param String orderType
	 * @return ArrayList<Integer> that have number of orders that we take from
	 *         database 'Table Orders'
	 */
	public static ArrayList<Integer> numOfVisitorsByStayTime(Date d, String parkName, String orderType) {
		ArrayList<Integer> arr = null;
		int month = d.getMonth() + 1, year = d.getYear() + 1900;

		if (conn != null) {
			arr = new ArrayList<Integer>();
			try {
				ResultSet rs;
				PreparedStatement ps = conn.prepareStatement(
						"SELECT sum(numOfVisitors) FROM orders Where parkName = ? And month(visitDate) = ? and year(visitDate) = ? And  orderType=? and (orders.exit- entrance) <= 20000;");
				ps.setString(1, parkName);
				ps.setInt(2, month);
				ps.setInt(3, year);
				ps.setString(4, orderType);

				rs = ps.executeQuery();

				if (rs.next())
					arr.add(rs.getInt(1));

				ps = conn.prepareStatement(
						"SELECT sum(numOfVisitors) FROM orders Where parkName = ? And month(visitDate) = ? and year(visitDate) = ? And  orderType=? and (orders.exit- entrance) > 20000 And (orders.exit- entrance) <= 30000;");
				ps.setString(1, parkName);
				ps.setInt(2, month);
				ps.setInt(3, year);
				ps.setString(4, orderType);

				rs = ps.executeQuery();

				if (rs.next())
					arr.add(rs.getInt(1));

				ps = conn.prepareStatement(
						"SELECT sum(numOfVisitors) FROM orders Where parkName = ? And month(visitDate) = ? and year(visitDate) = ? And  orderType=? and (entrance - orders.exit) > 30000 ");
				ps.setString(1, parkName);
				ps.setInt(2, month);
				ps.setInt(3, year);
				ps.setString(4, orderType);

				rs = ps.executeQuery();

				if (rs.next())
					arr.add(rs.getInt(1));

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return arr;
	}

	/***************************************************************************
	 * Visit Report
	 ******************************************************************************/
	/**
	 * this method create visit report from data that we have in database
	 * 
	 * @param arr have in first index (Date date )
	 * @param arr have in second index (String parkName )
	 * @return ArrayList<Integer>
	 */
	public static ArrayList<Integer> makeVisitReport(ArrayList<Object> arr) {
		Date date = (Date) arr.get(0);
		String parkName = (String) arr.get(1);
		ArrayList<Integer> report = new ArrayList<>();
		OrderType orderType = OrderType.PLANNEDTRAVELER;
		report.addAll(numOfVisitorsByTime(date, parkName, "PLANNEDTRAVELER")); // 0 1 2
		report.addAll(numOfVisitorsByStayTime(date, parkName, "PLANNEDTRAVELER")); // 3 4 5

		report.addAll(numOfVisitorsByTime(date, parkName, "PLANNEDSUBSCRIBER")); // 6 7 8
		report.addAll(numOfVisitorsByStayTime(date, parkName, "PLANNEDSUBSCRIBER")); // 9 10 11

		report.addAll(numOfVisitorsByTime(date, parkName, "PLANNEDGUIDE")); // 12 13 14
		report.addAll(numOfVisitorsByStayTime(date, parkName, "PLANNEDGUIDE")); // 15 16 17

		report.addAll(numOfVisitorsByTime(date, parkName, "UNPLANNEDTRAVELER")); // 18 19 20
		report.addAll(numOfVisitorsByStayTime(date, parkName, "UNPLANNEDTRAVELER")); // 21 22 23

		report.addAll(numOfVisitorsByTime(date, parkName, "UNPLANNEDSUBSCRIBER")); // 24 25 26
		report.addAll(numOfVisitorsByStayTime(date, parkName, "UNPLANNEDSUBSCRIBER")); // 27 28 29

		report.addAll(numOfVisitorsByTime(date, parkName, "UNPLANNEDGUIDE")); // 30 31 32
		report.addAll(numOfVisitorsByStayTime(date, parkName, "UNPLANNEDGUIDE")); // 33 34 35
//		System.out.println(report);
		return report;
	}
}
