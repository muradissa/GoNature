package entities;
import java.io.Serializable;
import java.sql.Date;


/**
 * class that holds all necessary data for park sale
 * @author ayman
 * @date 14/12/2020
 */
@SuppressWarnings("serial")
public class Sale extends ManagerRequest implements Serializable {
	
	private Date startDate,endDate;
	private int salePercent;
	private String saleName;
	private String parkName;
	
	/**
	 * 
	 * @param startDate-the start date of the sale
	 * @param endDate-the end date of the sale
	 * @param salePercent-the percent of the sale
	 * @param saleName-the name of the sale
	 * @param parkName-the park name that the sale belongs to
	 * @param statuString-the status of the sale
	 * @param date-the issue date of the sale request
	 */
	public Sale(Date startDate, Date endDate, int salePercent, String saleName ,String parkName,String statuString, Date date) {
		super(statuString, date);
		this.startDate = startDate;
		this.endDate = endDate;
		this.salePercent = salePercent;
		this.saleName = saleName;
		this.parkName = parkName;
		super.name = saleName;
	}

	/**
	 * 
	 * @return startDate field
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * sets the startDate field using given parameter
	 * @param startDate
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	/**
	 * 
	 * @return endDate field
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * sets the endDate field using given parameter
	 * @param endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * 
	 * @return salePercent field
	 */
	public int getSalePercent() {
		return salePercent;
	}
	/**
	 * sets the salePercent field using given parameter
	 * @param salePercent
	 */
	public void setSalePercent(int salePercent) {
		this.salePercent = salePercent;
	}
	/**
	 * 
	 * @return saleName field
	 */
	public String getSaleName() {
		return saleName;
	}
	/**
	 * sets the saleName field using given parameter
	 * @param saleName
	 */
	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}
	/**
	 * 
	 * @return parkName field
	 */
	public String getParkName() {
		return parkName;
	}
	/**
	 * sets the parkName field using given parameter
	 * @param parkName
	 */
	public void setParkName(String parkName) {
		this.parkName = parkName;
	}


}