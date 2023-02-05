package unitTests;

import java.util.ArrayList;

public interface IReport {
	public String getReportMessage(ArrayList<Object> msg);

	public void addReportToDBMessage(ArrayList<Object> msg);

	public boolean checkReportIfExist(ArrayList<Object> msg);
}
