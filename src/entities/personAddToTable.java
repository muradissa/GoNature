package entities;

import javafx.beans.property.SimpleStringProperty;
/**
 * 
 * @author Group2
 * This class was used to organize the values on table view
 *
 */

public class personAddToTable {
	
	private SimpleStringProperty fullName;
	private SimpleStringProperty id;
	private SimpleStringProperty subOrGuide;
	
	/**
	 * 
	 * @return fullName
	 */
	public String getFullName() {
		return fullName.get();
	}

	/**
	 * 
	 * @param fullName
	 */
	public void setFullName(String fullName) 
	{
		this.fullName.set(fullName);
	}

/**
 * 
 * @return id
 */
	public String getId() {
		return id.get();
	}

/**
 * 
 * @param id
 */
	public void setId(String id) {
		this.id.set(id);
	}

/**
 * 
 * @return subOrGuide
 */
	public String getSubOrGuide() {
		return subOrGuide.get();
	}

/**
 * 
 * @param subOrGuide 
 */
	public void setSubOrGuide(String subOrGuide) {
		this.subOrGuide.set(subOrGuide);
	}

/**
 * 
 * @param fullName
 * @param id
 * @param subOrGuide
 */
	public personAddToTable(SimpleStringProperty fullName, SimpleStringProperty id, SimpleStringProperty subOrGuide) {
		this.fullName = fullName;
		this.id = id;
		this.subOrGuide = subOrGuide;
	}
}
