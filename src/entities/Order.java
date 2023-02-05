package entities;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

/**
 * this is Order class that hold information about the traveler order  
 * @author Ayman Rizeq
 * @date 14/12/2020
 */

@SuppressWarnings("serial")
public class Order implements Serializable {
		
	private Park park;
	private Date visitDate;
	private Time visitTime;
	private int numOfVisitors;
	private String email;
	private String orderer;//the person who placed the order
	private OrderType orderType;
	private String orderStatus;
	private boolean payStatus;
	private double price;
	private boolean arrived;
	private String phoneNumber;
	private Time entrance;
	private Time exit;
	
	/**
	 * this Constructor have 8 parameters 
	 * @param Park park
	 * @param Date visitDate
	 * @param Time visitTime
	 * @param int numOfVisitors
	 * @param String email
	 * @param String orderer
	 * @param OrderType orderType
	 * @param double price
	 * @param String orderStatus
	 * @param boolean payStatus
	 */
	public Order(Park park, Date visitDate, Time visitTime, int numOfVisitors, String email, String orderer,OrderType orderType, double price,String orderStatus,boolean payStatus) {
		this.park = park;
		this.visitDate = visitDate;
		this.visitTime = visitTime;
		this.numOfVisitors = numOfVisitors;
		this.email = email;
		this.orderer = orderer;
		this.orderType = orderType;
		this.orderStatus = orderStatus;
		this.price = price;
		this.payStatus=payStatus;
	}
	/**
	 * this Constructor have 11 parameters
	 * @param Park park
	 * @param Date visitDate
	 * @param Time visitTime
	 * @param int numOfVisitors
	 * @param String email
	 * @param String orderer
	 * @param OrderType orderType
	 * @param double price
	 * @param String orderStatus
	 * @param boolean payStatus
	 * @param boolean arrived
	 */
	public Order(Park park, Date visitDate, Time visitTime, int numOfVisitors, String email, String orderer,OrderType orderType, double price,String orderStatus,boolean payStatus,boolean arrived) {
		this.park = park;
		this.visitDate = visitDate;
		this.visitTime = visitTime;
		this.numOfVisitors = numOfVisitors;
		this.email = email;
		this.orderer = orderer;
		this.orderType = orderType;
		this.orderStatus = orderStatus;
		this.price = price;
		this.payStatus=payStatus;
		this.arrived=arrived;
	}
	/**
	 * this Constructor have 12 parameters
	  * @param Park park
	 * @param Date visitDate
	 * @param Time visitTime
	 * @param int numOfVisitors
	 * @param String email
	 * @param String orderer
	 * @param OrderType orderType
	 * @param double price
	 * @param String orderStatus
	 * @param boolean payStatus
	 * @param boolean arrived
	 * @param String phoneNumber
	 */
	public Order(Park park, Date visitDate, Time visitTime, int numOfVisitors, String email, String orderer,OrderType orderType, double price,String orderStatus,boolean payStatus,boolean arrived,String phoneNumber) {
		this.park = park;
		this.visitDate = visitDate;
		this.visitTime = visitTime;
		this.numOfVisitors = numOfVisitors;
		this.email = email;
		this.orderer = orderer;
		this.orderType = orderType;
		this.orderStatus = orderStatus;
		this.price = price;
		this.payStatus=payStatus;
		this.arrived=arrived;
		this.phoneNumber=phoneNumber;
	}
	/**
	 * this Constructor have 14 parameters
	 * @param Park park
	 * @param Date visitDate
	 * @param Time visitTime
	 * @param int numOfVisitors
	 * @param String email
	 * @param String orderer
	 * @param OrderType orderType
	 * @param double price
	 * @param String orderStatus
	 * @param boolean payStatus
	 * @param boolean arrived
	 * @param String phoneNumber
	 * @param Time entrance
	 * @param Time exit
	 */
	public Order(Park park, Date visitDate, Time visitTime, int numOfVisitors, String email, String orderer,OrderType orderType, double price,String orderStatus,boolean payStatus,boolean arrived,String phoneNumber,Time entrance,Time exit) {
		this.park = park;
		this.visitDate = visitDate;
		this.visitTime = visitTime;
		this.numOfVisitors = numOfVisitors;
		this.email = email;
		this.orderer = orderer;
		this.orderType = orderType;
		this.orderStatus = orderStatus;
		this.price = price;
		this.payStatus=payStatus;
		this.arrived=arrived;
		this.phoneNumber=phoneNumber;
		this.entrance=entrance;
		this.exit=exit;
	}
	/**
	 * 
	 * @return entrance
	 */
	public Time getEntrance() {
		return entrance;
	}

	/**
	 * 
	 * @param set new Time in entrance
	 */
	public void setEntrance(Time entrance) {
		this.entrance = entrance;
	}

	/**
	 * 
	 * @return exit
	 */
	public Time getExit() {
		return exit;
	}

	/**
	 * 
	 * @param set new Time in exit
	 */
	public void setExit(Time exit) {
		this.exit = exit;
	}

	/**
	 * 
	 * @return park
	 */
	public Park getPark() {
		return park;
	}
	/**
	 * 
	 * @param set new Park in park
	 * 
	 */
	public void setPark(Park park) {
		this.park = park;
	}
	/**
	 * 
	 * @return visitDate
	 */
	public Date getVisitDate() {
		return visitDate;
	}
	/**
	 * 
	 * @param set new Date in visitDate
	 */
	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}
	/**
	 * 
	 * @return visitTime
	 */
	public Time getVisitTime() {
		return visitTime;
	}
	/**
	 * 
	 * @param set new Time in visitTime
	 */
    public void setVisitTime(Time visitTime) {
		this.visitTime = visitTime;
	}
	/**
	 * 
	 * @return numOfVisitors
	 */
	public int getNumOfVisitors() {
		return numOfVisitors;
	}
	/**
	 * 
	 * @param set new int in numOfVisitors
	 */
	public void setNumOfVisitors(int numOfVisitors) {
		this.numOfVisitors = numOfVisitors;
	}
	/**
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * 
	 * @param set new Sting in email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 
	 * @return orderer
	 */
	public String getOrderer() {
		return orderer;
	}
	/**
	 * 
	 * @param set new Sting in orderer
	 */
	public void setOrderer(String orderer) {
		this.orderer = orderer;
	}
	/**
	 * 
	 * @return orderType
	 */
	public OrderType getOrderType() {
		return orderType;
	}
	/**
	 * 
	 * @param set new OrderType in orderType
	 */
	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}
	
	public String getOrderStatus() {
		return orderStatus;
	}
	/**
	 * 
	 * @param set new Sting in orderStatus
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	/**
	 * 
	 * @return price
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * 
	 * @param set new double in price
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	/**
	 * 
	 * @return payStatus
	 */
	public boolean getPayStatus() {
		return payStatus;
	}
	/**
	 * 
	 * @param set new boolean in payStatus
	 */
	public void setPayStatus(boolean payStatus) {
		this.payStatus = payStatus;
	}
	
	/**
	 * 
	 * @return arrived
	 */
	public boolean isArrived() {
		return arrived;
	}

	/**
	 * 
	 * @param set new boolean in arrived
	 */
	public void setArrived(boolean arrived) {
		this.arrived = arrived;
	}

	/**
	 * 
	 * @return phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * 
	 * @param set new Sting in phoneNumber
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
}
