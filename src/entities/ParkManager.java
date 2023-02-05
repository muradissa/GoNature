package entities;
import java.io.Serializable;
/**
 * 
 * @author Murad Eissa
 * @date 14/12/2020
 */
import java.util.Date;
// import java.sql.Date;

/**
 * this is ParkManager class that hold information about the park manager
 * @author Group'2'
 *
 */
public class ParkManager extends Worker implements Serializable {

	
	/**
	 * 
	 * @param userName
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param workerId
	 * @param email
	 * @param park
	 */
	public ParkManager(String userName, String password, String firstName, String lastName, String workerId,String email,Park park) {
		super(userName, password, firstName, lastName, workerId, email,park);
		// TODO Auto-generated constructor stub
	}
}
