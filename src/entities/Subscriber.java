package entities;

import java.io.Serializable;

/**
 * class for holding all necessary subscriber information
 * @author MICHEL
 * @date 14/12/2020
 */

@SuppressWarnings("serial")
public class Subscriber extends Traveler implements Serializable {
	
	private String subscriptionNumber;

	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String email;
	private int numberOfFamilyMembers;
	private CreditCard creditCard;
	
	/**
	 * @param subscriptionNumber
	 * @param idNumber
	 * @param firstName
	 * @param lastName
	 * @param phoneNumber
	 * @param email
	 * @param numberOfFamilyMembers
	 * @param creditCard

	 * This method is the constructor of the Subscriber class.
	 */
	public Subscriber(String subscriptionNumber, String idNumber, String firstName, String lastName, String phoneNumber,
			String email, int numberOfFamilyMembers, CreditCard creditCard) {
		super(idNumber,false);
		this.subscriptionNumber = subscriptionNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.numberOfFamilyMembers = numberOfFamilyMembers;
		this.creditCard = creditCard;
	}

	
	/**
	 * @return the subscription number.
	 * This method return the subscription number.
	 */
	public String getSubscriptionNumber() {
		return subscriptionNumber;
	}

	/**
	 * @param subscriptionNumber
	 * This method sets the subscription number.
	 */
	public void setSubscriptionNumber(String subscriptionNumber) {
		this.subscriptionNumber = subscriptionNumber;
	}

	/**
	 *@return the subscriber first name.
	 *This method returns the subscriber first name.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 *@param firstName
	 *This method sets the subscriber first name.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 *@return the subscriber Last name.
	 *This method returns the subscriber Last name.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 *@param lastName
	 *This method sets the subscriber last name.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 *@return the subscriber phone number.
	 *This method returns the subscriber phone number.
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 *@param phoneNumber
	 *This method sets the subscriber phone number.
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 *@return the subscriber email.
	 *This method returns the subscriber email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 *@param email
	 *This method sets the subscriber email.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 *@return the subscriber number of family members
	 *This method returns the subscriber number of family members.
	 */
	public int getNumberOfFamilyMembers() {
		return numberOfFamilyMembers;
	}

	/**
	 *@param numberOfFamilyMembers
	 *This method sets the subscriber number of family members.
	 */
	public void setNumberOfFamilyMembers(int numberOfFamilyMembers) {
		this.numberOfFamilyMembers = numberOfFamilyMembers;
	}

	/**
	 *@return the subscriber credit card.
	 *This method returns the subscriber credit card.
	 */
	public CreditCard getCreditCard() {
		return creditCard;
	}

	/**
	 *@param creditCard
	 *This method sets the subscriber credit card.
	 */
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}


}
