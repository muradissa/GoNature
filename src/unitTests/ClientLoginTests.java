package unitTests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controllers.WorkerLoginCC;
import entities.DepartmentManager;
import entities.ParkManager;
import entities.ServiceRepesentative;
import entities.Worker;

class ClientLoginTests {

	private String username;
	private String password;
	private Worker result, expected;
	private WorkerLoginCC workerLogInController;
	private StubLogIn stubLogIn;
	private boolean expectedWorkerRole;

	@BeforeEach
	void setUp() throws Exception {

		stubLogIn = new StubLogIn();
		workerLogInController = new WorkerLoginCC();
		workerLogInController.setLogin(stubLogIn);

		username = "";
		password = "";
	}

	private class StubLogIn implements ILogIn {
		ArrayList<Worker> worker = new ArrayList<Worker>();
		ArrayList<Worker> ConnWorkers = new ArrayList<Worker>();

		public StubLogIn() {
			worker.add(new Worker("omri", "omri123", "Omri", "Brazi", "111222333", "omri@randatmail.com", null));
			worker.add(
					new DepartmentManager("murad", "murad123", "Murad", "Eissa", "123456789", "murad@randatmail.com"));
			worker.add(
					new ParkManager("ayman", "ayman123", "Ayman", "Riziq", "987654321", "ayman@randatmail.com", null));
			worker.add(new ServiceRepesentative("michel", "michel123", "michel", "diab", "202020202",
					"michel@randatmail.com"));
			worker.add(new Worker("adi", "adi123", "adi", "daher", "202020201", "adi@randatmail.com", null));

		}

		@Override
		public Worker getWorker(ArrayList<Object> list) {
			String name = (String) list.get(0);
			String password = (String) list.get(1);
			for (Worker w : worker)
				if (w.getUserName().equals(name) & w.getPassword().equals(password)) {
					if (ConnWorkers.contains(w))
						return null;
					ConnWorkers.add(w);
					return w;
				}

			return null;
		}

	}

	/**
	 * This test checks if we set the user name null and correct password,
	 * checkWorker should return null we expected from checkWorker null value. this
	 * test should pass
	 */
	@Test
	void testWorkerNullUserName() {
		username = null;
		password = "omri123";

		result = WorkerLoginCC.checkWorker(username, password);
		expected = null;

		assertEquals(result, expected);
	}

	/**
	 * This test checks if we set the password and correct username null ,
	 * checkWorker should return null we expected from checkWorker null value. this
	 * test should pass
	 */
	@Test
	void testWorkerNullPassword() {
		username = "omri123";
		password = null;

		result = WorkerLoginCC.checkWorker(username, password);
		expected = null;

		assertEquals(result, expected);
	}

	/**
	 * testing nonexist worker in data base by sending nonexist username in data
	 * base
	 */
	@Test
	void testWorkerNotExist() {
		username = "aaa";
		password = "murad123";

		result = WorkerLoginCC.checkWorker(username, password);
		expected = null;

		assertEquals(result, expected);

	}

	/**
	 * 
	 * sending existing worker details, ans expecting for the same Worker Class
	 * Worker have equals method because that we do just one assertEqual .
	 * 
	 */
	@Test
	void testWorkerExist() {
		username = "omri";
		password = "omri123";
		result = WorkerLoginCC.checkWorker(username, password);
		expected = new Worker("omri", "omri123", "Omri", "Brazi", "111222333", "omri@randatmail.com", null);

		assertEquals(result, expected);

		result = WorkerLoginCC.checkWorker(username, password);
		expected = null;
		assertEquals(result, expected);
	}

	/**
	 * This test checks if the role of the worker result is DepartmentManager The
	 * method uses the username and the password to find the relevant worker
	 * 
	 */
	@Test
	void testDepartmentManagerRole() {
		expectedWorkerRole = true;
		username = "murad";
		password = "murad123";
		result = WorkerLoginCC.checkWorker(username, password);
		expected = new DepartmentManager("murad", "murad123", "Murad", "Eissa", "123456789", "murad@randatmail.com");

		if (result instanceof DepartmentManager)
			assertTrue(expectedWorkerRole);
		else
			assertTrue(false);

	}

	/**
	 * This test checks if the role of the worker result is ParkManager The method
	 * uses the username and the password to find the relevant worker
	 * 
	 */
	@Test
	void testParkManagerRole() {
		expectedWorkerRole = true;
		username = "ayman";
		password = "ayman123";
		result = WorkerLoginCC.checkWorker(username, password);

		if (result instanceof ParkManager)
			assertTrue(expectedWorkerRole);
		else
			assertTrue(false);

	}

	/**
	 * This test checks if the role of the worker result is ServiceRepesentative The
	 * method uses the username and the password to find the relevant worker
	 * 
	 */
	@Test
	void testServiceRepresentativeRole() {
		expectedWorkerRole = true;
		username = "michel";
		password = "michel123";
		result = WorkerLoginCC.checkWorker(username, password);

		if (result instanceof ServiceRepesentative)
			assertTrue(expectedWorkerRole);
		else
			assertTrue(false);

	}

	/**
	 * This test checks if the role of the worker result is normal Worker The method
	 * uses the username and the password to find the relevant worker
	 * 
	 */
	@Test
	void testWorkerRole() {
		expectedWorkerRole = true;
		username = "adi";
		password = "adi123";
		result = WorkerLoginCC.checkWorker(username, password);

		if (!(result instanceof ServiceRepesentative) && !(result instanceof ParkManager)
				&& !(result instanceof DepartmentManager) && (result instanceof Worker))
			assertTrue(expectedWorkerRole);
		else
			assertTrue(false);

	}

	/**
	 * sending wrong password and correct username, the method should return null.
	 * 
	 */
	@Test
	void testWorkerCorrectUserNameWrongPassword() {
		username = "omri";
		// correct password 'omri123'
		password = "omri1234";

		result = WorkerLoginCC.checkWorker(username, password);
		expected = null;
		assertEquals(result, expected);
	}

	/**
	 * sending correct password and wrong username, the method should return null.
	 */
	@Test
	void testWorkerCorrectPasswordtWrongUserName() {
		// correct username 'amri'
		username = "amri";
		password = "omri123";

		result = WorkerLoginCC.checkWorker(username, password);
		expected = null;

		assertEquals(result, expected);
	}

}
