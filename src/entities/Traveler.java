package entities;

import java.io.Serializable;

/**
 * class for holding all necessary traveler information
 * @author MICHEL
 * @date 14/12/2020
 */

@SuppressWarnings("serial")
public class Traveler implements Serializable {
	
	private String idNumber;

	private boolean isGuide;
	/**
	 * @param idNumber-id number of the traveler
	 * @param isGuide-boolean indication whether this traveler is a guide or not
	 * This method is the constructor of the Traveler class.
	 */
	public Traveler(String idNumber, boolean isGuide) {
		this.idNumber = idNumber;
		this.isGuide = isGuide;
	}
		
	/**
	 * @return ID number of the traveler.
	 * This method returns the ID number of the traveler.
	 */
	public String getIdNumber() {
		return idNumber;
	}
	
	/**
	 * @param idNumber
	 * This method sets the ID number of the traveler.
	 */
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
		
	/**
	 * @return
	 * This method returns true if the traveler is a guide and if not it returns false.
	 */
	public boolean isGuide() {
		return isGuide;
	}
		
	/**
	 * @param isGuide
	 * This method sets the guide status of the traveler.
	 */
	public void setGuide(boolean isGuide) {
		this.isGuide = isGuide;
	}

}
