package entities;

/**
 * 
 * this is an enum class for the report types
 *
 */
public enum ReportType {

	cancelled_order_report{
		public String toString() {
			return "Cancelled Order Report";
		}
	}, number_of_visitors_report{
		public String toString() {
			return "Number Of visitors Report";
		}
	}, Uses_Report{
		public String toString() {
			return "Uses Report";
		}
	}, Earnings_report{
		public String toString() {
			return "Income Report";
		}
	}, Visits_Report{
		public String toString() {
			return "Visits Report";
		}
	};

	
}
