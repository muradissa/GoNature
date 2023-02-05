package entities;

import java.io.Serializable;

/**
 * class holding all necessary data for service representative
 * @author Murad Eissa
 * @date 14/12/2020
 */
@SuppressWarnings("serial")
public class ServiceRepesentative extends Worker implements Serializable {

		
	/**
	 * 
	 * @param userName
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param workerId
	 * @param email
	 */
	public ServiceRepesentative(String userName, String password, String firstName, String lastName, String workerId,
			String email) {
		super(userName, password, firstName, lastName, workerId, email,null);
	}

}
