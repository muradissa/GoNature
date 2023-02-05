package entities;

import java.io.Serializable;

/**
 * 
 * @author Omri Brazi
 * @date 14/12/2020
 */

import java.sql.Date;
import java.util.ArrayList;



/*
 * 
 * WaitingList - class to simulate the Waiting List.
 * @waitingList - the list of the unconfirmed Orders.
 * @param -'Park' the park that the waiting list  belongs to.
 * @date - the date of the orders in the waiting list.
 * 
 * 
 */
@SuppressWarnings("serial")
public class WaitingList implements Serializable {

	private ArrayList<Order> waitingList = new ArrayList<Order>();
	private Park park;
	private Date date;

	public ArrayList<Order> getWaitingList() {
		return waitingList;
	}

	public Park getPark() {
		return park;
	}

	public void setPark(Park park) {
		this.park = park;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	/*
	 * addToWaitingList - the method adding unconfirmed order to the list
	 * @unconfirmedOrder - the order that we add to the ArrayList
	 * in case of problem while adding the order we return false
	 * otherwise we return true
	 */
	
	public boolean addToWaitingList(Order unconfirmedOrder) {
			return waitingList.add(unconfirmedOrder);
		
	}
	
	/*
	 * removeFromWaitingList - the method removes order from the list
	 * @orderToRemoveOrder - the order that we remove from the ArrayList
	 * in case of problem while removing the order we return false
	 * otherwise we return true
	 */
	
	public boolean removeFromWaitingList(Order orderToRemove) {
		return waitingList.remove(orderToRemove);
	}
	
	
	
}
