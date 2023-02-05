package entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * this is DepartmentManager class that hold information about the department manager 
 * this class save all the information about DepartmentManager
 * DepartmentManager extends Worker 
 * @author Murad Eissa
 * @date 14/12/2020
 */
public class DepartmentManager extends Worker implements Serializable {

	ArrayList<Park> parks;
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<Park> getParks() {
		return parks;
	}

	/**
	 * 
	 * @param parks
	 */
	public void setParks(ArrayList<Park> parks) {
		this.parks = parks;
	}

	/**
	 * 
	 * @param userName
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param workerId
	 * @param email
	 */
	public DepartmentManager(String userName, String password, String firstName, String lastName, String workerId,String email) {
		super(userName, password, firstName, lastName, workerId, email,null);
		// TODO Auto-generated constructor stub
	}

	

}
