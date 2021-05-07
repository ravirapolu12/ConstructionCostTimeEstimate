package com.cts.conctes.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import com.cts.conctes.exception.ConstructionEstimationException;
import com.cts.conctes.model.ConstructionProject;
import com.cts.conctes.util.ApplicationUtil;
public class CostAndTimeEstDAO {
public static Connection connection = null;
public boolean insertConstructionProject(ArrayList <ConstructionProject> constProjects)
throws ConstructionEstimationException {
boolean recordsAdded = false;
int index=0;
int size=constProjects.size();
connection=DBConnectionManager.getInstance().getConnection();
String query="insert into constructionproject values(?, ?, ?, ?, ?, ?, ?);";
for(ConstructionProject cp: constProjects) {
	
	try {
		 PreparedStatement ps=connection.prepareStatement(query);
		 ps.setString(1, cp.getProjectId());
		 ps.setDate(2, ApplicationUtil.utilToSqlDateConverter(cp.getPlannedDOStart()));
		 ps.setString(3, cp.getTypeOfProject());
		 ps.setString(4, cp.getStructure());
		 ps.setDouble(5, cp.getAreaInSqFt());
		 ps.setDouble(6, cp.getEstimatedCostInlac());
		 ps.setDouble(7, cp.getEstimatedTimeInMonths());
		 int row=ps.executeUpdate();
		 if(row>0)
			 index+=1;
		 if(index==size) {
			 recordsAdded=true;
		 }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
}
return recordsAdded;

}
public ArrayList <ConstructionProject> getConstructionProjectsData() throws ConstructionEstimationException
{
ArrayList <ConstructionProject> consApplicants = new
ArrayList<ConstructionProject>();
connection=DBConnectionManager.getInstance().getConnection();
String query="select * from constructionproject";
try {
	PreparedStatement ps=connection.prepareStatement(query);
	ResultSet rs=ps.executeQuery();
	while(rs.next()) {
		String id=rs.getString(1);
		Date d=rs.getDate(2);
		String typeProject=rs.getString(3);
		String structure=rs.getString(4);
		double areaSqFt=rs.getDouble(5);
		double costInLac=rs.getDouble(6);
		double timeInMonths=rs.getDouble(7);
		consApplicants.add(new ConstructionProject(id, d, typeProject, structure, areaSqFt, costInLac, timeInMonths));
	}
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

return consApplicants;
}
}