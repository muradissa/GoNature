package entities;


import java.io.Serializable;

/**
 * class that holds all necessary information for worker
 * @author Murad Eissa
 * @date 14/12/2020
 */
@SuppressWarnings("serial")
public class Worker implements Serializable {


	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String workerId;
	private String email;	
    private Park park;
	
	/**
	 * 
	 * @param userName
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param workerId
	 * @param email
	 * @param position
	 */
	public Worker (String userName,String password,String firstName,String lastName,String workerId,String email,Park park) {
		this.userName=userName;
		this.password=password;
		this.firstName=firstName;
		this.lastName=lastName;
		this.workerId=workerId;
		this.email=email;
		this.park=park;
	}	
	/**
	 * @return the userName
	 * 	getUserName method return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName
	 * setUserName method set new String in userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}	
	/** 
	 * @return the password
	 * getPassword method return the password
	 */
	public String getPassword() {
		return password;
	}
	/** 
	 * @param password
	 * setPassword method set new String in password
	 */
	public void setPassword(String password) {
		this.password = password;
	}	
	/**
	 * @return the firstName
	 * getFirstName method return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName
	 * setFirstName method set new String in firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}	
	/** 
	 * @return the lastName
	 * getLastName method return the lastName
	 */
	public String getLastName() {
		return lastName;
	}	
	/**
	 * @param lastName
	 * setLastName method set new String in lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}	
	/**
	 * @return the workerId
	 * getWorkerId method return the workerId
	 */
	public String getWorkerId() {
		return workerId;
	}	
	/** 
	 * @param workerId
	 * setWorkerId method set new String in workerId
	 */
	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}
	/**
	 * @return the email
	 * getEmail method return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email
	 * setEmail method set new String in email
	 */
	public void setEmail(String email) {
		this.email = email;
	}  	
	
	/**
	 * 
	 * @return park field
	 */
	public Park getPark() {
		return park;
	}
	/**
	 * sets the park field using given parameter
	 * @param park
	 */
	public void setPark(Park park) {
		this.park = park;
	}
	/**
	 * returns string representation of worker
	 */
	@Override
	public String toString() {
		return "Worker [userName=" + userName + ", password=" + password + ", firstName=" + firstName + ", lastName="
				+ lastName + ", workerId=" + workerId + ", email=" + email + "]";
	}
	
    /**
     * 
     * @param otherWorker
     * @return true if the workers are equal and false otherwise
     */
	@Override
    public boolean equals(Object otherWorker) {
    	
    	if(otherWorker instanceof Worker) {
    		
    	if(userName.equals(((Worker) otherWorker).getUserName()))
    		if(password.equals(((Worker) otherWorker).getPassword())) 
    			if(firstName.equals(((Worker) otherWorker).getFirstName())) 
    				if(lastName.equals(((Worker) otherWorker).getLastName())) 
    					if(workerId.equals(((Worker) otherWorker).getWorkerId())) 
    						if(email.equals(((Worker) otherWorker).getEmail())) 
    							return true;
    	}   	   	
        return false;
    }
	

}
