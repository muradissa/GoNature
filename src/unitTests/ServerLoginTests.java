package unitTests;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controllers.WorkerLoginCC;
import entities.DepartmentManager;
import entities.ParkManager;
import entities.ServiceRepesentative;
import entities.Worker;
import server.mysqlConnection;

class ServerLoginTests {

	private String username;
	private String password;
	private Worker expected;
	private Worker result;
	ArrayList<Object> parametersForMySql;
	private boolean expectedWorkerRole;

	@BeforeEach
	void setUp() throws Exception {
		parametersForMySql = new ArrayList<Object>();
	}

	@BeforeAll
	public static void setUpClass() {
		try {
			mysqlConnection.connecttoDB();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * testing nonexist worker in data base by sending nonexist username in database
	 * 
	 * 
	 */
	@Test
	void testWorkerNotExist() {
		username = "aaa";
		password = "murad123";

		parametersForMySql.add(username);
		parametersForMySql.add(password);
		result = mysqlConnection.checkWorker(parametersForMySql);
		expected = null;

		assertEquals(result, expected);

	}

	/**
	 * Class Worker have equals method because that we do just one assertEqual . the
	 * row in mySQl => Table.worker ('omri', 'omri123', 'Omri', 'Brazi',
	 * '111222333', 'omri@randatmail.com', 'Haifa Park', 'Park Manager') => Exist
	 * 
	 */
	@Test
	void testWorkerExist() {
		username = "omri";
		password = "omri123";

		parametersForMySql.add(username);
		parametersForMySql.add(password);
		result = mysqlConnection.checkWorker(parametersForMySql);

		expected = new Worker("omri", "omri123", "Omri", "Brazi", "111222333", "omri@randatmail.com", null);

		assertEquals(result, expected);

	}

	/**
	 * This test checks if the role of the worker result is DepartmentManager The
	 * method uses the username and the password to find the relevant worker the row
	 * in mySQl => Table.worker ('ayman', 'ayman123', 'Ayman', 'Rizeq', '111444777',
	 * 'ayman@gmail.com', NULL, 'Department Manager') => Exist
	 * 
	 */
	@Test
	void testDepartmentManagerRole() {
		expectedWorkerRole = true;
		username = "ayman";
		password = "ayman123";
		parametersForMySql.add(username);
		parametersForMySql.add(password);
		result = mysqlConnection.checkWorker(parametersForMySql);

		if (result instanceof DepartmentManager)
			assertTrue(expectedWorkerRole);
		else
			assertTrue(false);

	}

	/**
	 * This test checks if the role of the worker result is ParkManager The method
	 * uses the username and the password to find the relevant worker row in mySQl
	 * => Table.worker ('omri', 'omri123', 'Omri', 'Brazi', '111222333',
	 * 'omri@randatmail.com', 'Haifa Park', 'Park Manager') => Exist
	 * 
	 */
	@Test
	void testParkManagerRole() {
		expectedWorkerRole = true;
		username = "omri";
		password = "omri123";
		parametersForMySql.add(username);
		parametersForMySql.add(password);
		result = mysqlConnection.checkWorker(parametersForMySql);

		if (result instanceof ParkManager)
			assertTrue(expectedWorkerRole);
		else
			assertTrue(false);

	}

	/**
	 * This test checks if the role of the worker result is ServiceRepesentative The
	 * method uses the username and the password to find the relevant worker row in
	 * mySQl => Table.worker('worker100', 'worker100', 'Aida', 'Richards',
	 * '100200300', 'a.richards@randatmail.com', NULL, 'Service
	 * Representative')=>Exist
	 * 
	 */
	@Test
	void testServiceRepresentativeRole() {
		expectedWorkerRole = true;
		username = "worker100";
		password = "worker100";

		parametersForMySql.add(username);
		parametersForMySql.add(password);
		result = mysqlConnection.checkWorker(parametersForMySql);

		if (result instanceof ServiceRepesentative)
			assertTrue(expectedWorkerRole);
		else
			assertTrue(false);

	}

	/**
	 * This test checks if the role of the worker result is normal Worker The method
	 * uses the username and the password to find the relevant worker mySQl =>
	 * Table.worker('worker109', 'worker109', 'James', 'Gray', '100200309',
	 * 'j.gray@randatmail.com', 'Haifa Park', 'Worker')=>Exist
	 * 
	 */
	@Test
	void testWorkerRole() {
		expectedWorkerRole = true;
		username = "worker109";
		password = "worker109";

		parametersForMySql.add(username);
		parametersForMySql.add(password);
		result = mysqlConnection.checkWorker(parametersForMySql);

		if (!(result instanceof ServiceRepesentative) && !(result instanceof ParkManager)
				&& !(result instanceof DepartmentManager) && (result instanceof Worker))
			assertTrue(expectedWorkerRole);
		else
			assertTrue(false);

	}

	/**
	 * sending wrong password and correct username, the method should return null.
	 * because the resultSet is empty
	 * 
	 */
	@Test
	void testWorkerCorrectUserNameWrongPassword() {
		username = "omri";
		// correct password 'omri123'
		password = "omri1234";

		parametersForMySql.add(username);
		parametersForMySql.add(password);
		result = mysqlConnection.checkWorker(parametersForMySql);

		expected = null;
		assertEquals(result, expected);
	}

	/**
	 * sending correct password and wrong username, the method should return null.
	 * because the resultSet is empty
	 */
	@Test
	void testWorkerCorrectPasswordtWrongUserName() {
		// correct username 'amri'
		username = "amri";
		password = "omri123";

		parametersForMySql.add(username);
		parametersForMySql.add(password);
		result = mysqlConnection.checkWorker(parametersForMySql);

		expected = null;

		assertEquals(result, expected);
	}

}
