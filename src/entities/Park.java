package entities;

import java.io.Serializable;
import java.sql.Time;

/**
 * this is Park class that hold information about the park 
 * @author Omri Brazi
 * @date 14/12/2020
 */
public class Park implements Serializable {

	/*
	 * class Park name- name of the park. parkManager - the manager of the Park.
	 * address - the address of the Park. stayTime - the maximum stay time the the
	 * visitors can stay in the park. maxNumOfVisitor - the maximum number of
	 * visitors that allowed to enter the park. maxNumOfOrders - the maximum number
	 * of visitors that can order a visit.
	 * 
	 */
	private String name;
	private String address;

	private Time stayTime; //////////////////////// * Default is 4 hours *///////////////////////

	private int maxNumOfVisitor;
	private int maxNumOfOrders;
	private int currentNumOfVisitors;

	public int getCurrentNumOfVisitors() {
		return currentNumOfVisitors;
	}

	public void setCurrentNumOfVisitors(int currentNumOfVisitors) {
		this.currentNumOfVisitors = currentNumOfVisitors;
	}

	public int getCurrentNumOfUnplannedVisitors() {
		return currentNumOfUnplannedVisitors;
	}

	public void setCurrentNumOfUnplannedVisitors(int currentNumOfUnplannedVisitors) {
		this.currentNumOfUnplannedVisitors = currentNumOfUnplannedVisitors;
	}

	private int currentNumOfUnplannedVisitors;

	/**
	 * 
	 * @param String name
	 * @param String address
	 * @param Time stayTime
	 * @param int maxNumOfVisitor
	 * @param int maxNumOfdOrders
	 */
	public Park(String name, String address, Time stayTime, int maxNumOfVisitor, int maxNumOfdOrders) {

		this.name = name;
		this.address = address;
		this.stayTime = stayTime;
		this.maxNumOfVisitor = maxNumOfVisitor;
		this.maxNumOfOrders = maxNumOfdOrders;
	}

	/**
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return stayTime
	 */
	public Time getStayTime() {
		return stayTime;
	}

	/**
	 * 
	 * @param set new Time in stayTime
	 */
	public void setStayTime(Time stayTime) {
		this.stayTime = stayTime;
	}

	/**
	 * 
	 * @return maxNumOfVisitor
	 */
	public int getMaxNumOfVisitor() {
		return maxNumOfVisitor;
	}

	/**
	 * 
	 * @param set new intg in maxNumOfVisitor
	 */
	public void setMaxNumOfVisitor(int maxNumOfVisitor) {
		this.maxNumOfVisitor = maxNumOfVisitor;
	}

	/**
	 * 
	 * @return maxNumOfOrders
	 */
	public int getMaxNumOfOrders() {
		return maxNumOfOrders;
	}

	/**
	 * 
	 * @param set new int in maxNumOdOrders
	 */
	public void setMaxNumOfOrders(int maxNumOdOrders) {
		this.maxNumOfOrders = maxNumOdOrders;
	}

	/**
	 * 
	 * @return address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 
	 * @param set new Sting in address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

}