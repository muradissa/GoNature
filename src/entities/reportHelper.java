package entities;

import java.io.Serializable;
import java.sql.Date;

/**
 * this class is a helper class,helps to produce and manage reports
 * @author adi+omri
 *
 */
@SuppressWarnings("serial")
public class reportHelper implements Serializable{
	Date date;
	String range;
	
	/**
	 * 
	 * @param date-the date of the report
	 * @param range-range of free times in the report
	 */
	public reportHelper(Date date, String range) {
		super();
		this.date = date;
		this.range = range;
	}
	/**
	 * 
	 * @return date field
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * sets the date field using parameter
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * 
	 * @return range field
	 */
	public String getRange() {
		return range;
	}
	/**
	 * sets the range field using parameter
	 * @param range
	 */
	public void setRange(String range) {
		this.range = range;
	}
	
	
}
