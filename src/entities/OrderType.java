package entities;

import java.io.Serializable;

/**
 * this is OrderType class that hold information about the order type
 * 
 * @author Ayman Rizeq
 * @date 14/12/2020
 */
public enum OrderType implements Serializable {
	PLANNEDTRAVELER, /* an order that has been placed using the application for a nonsubscriber */
	PLANNEDSUBSCRIBER, /* an order that has been placed using the application for a subscriber */
	PLANNEDGUIDE, /* an order that has been placed using the application for a group guide */
	UNPLANNEDTRAVELER, /* an order that has been placed at the park(mezdamen) for a nonsubscriber */
	UNPLANNEDSUBSCRIBER, /* an order that has been placed at the park(mezdamen) for a subscriber */
	UNPLANNEDGUIDE/* an order that has been placed at the park(mezdamen) for a group guide */
}
