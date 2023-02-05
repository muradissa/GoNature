package entities;

import java.io.Serializable;

/**
 * @author Adi
 * this is creditcard class that hold information about the cardHolder and about the card 
 */
public class CreditCard implements Serializable {
	
	String cardNumber;
	String expireDate;
	String cvv;
	String cardHolderId;
		
	/**
	 * this Constructor CreditCard with specific cardNumber , expireDate and cvvString and cardHolderId 
	 * @param cardNumber
	 * @param expireDate
	 * @param cvvString
	 * @param cardHolderId
	 */
	public CreditCard(String cardNumber, String expireDate, String cvvString, String cardHolderId) {
		this.cardNumber = cardNumber;
		this.expireDate = expireDate;
		this.cvv = cvvString;
		this.cardHolderId = cardHolderId;
	}
	
	public String getCardNumber() {
		return cardNumber;
	}
    /**
     * @param set new Sting in cardNumber 
     */
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	/**
	 * 
	 * @return expireDate
	 */
	public String getExpireDate() {
		return expireDate;
	}
	/**
	 * 
	 * @param set new Sting in ExpireDate
	 */
	public void setExpireDate(String ExpireDate) {
		this.expireDate = ExpireDate;
	}
	/**
	 * 
	 * @return cvv
	 */
	public String getCvv() {
		return cvv;
	}
    /**
     * 
     * @param set new Sting in cvvString
     */
	public void setCvv(String cvvString) {
		this.cvv = cvvString;
	}
	/**
	 * 
	 * @return cardHolderId
	 */
	public String getCardHolderId() {
		return cardHolderId;
	}
	/**
	 * 
	 * @param set new Sting in cardHolderId
	 */
	public void setCardHolderId(String cardHolderId) {
		this.cardHolderId = cardHolderId;
	}
	
}