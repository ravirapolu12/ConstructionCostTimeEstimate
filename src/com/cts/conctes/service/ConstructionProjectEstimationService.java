package com.cts.conctes.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.cts.conctes.dao.CostAndTimeEstDAO;
import com.cts.conctes.exception.ConstructionEstimationException;
import com.cts.conctes.model.ConstructionProject;
import com.cts.conctes.util.ApplicationUtil;
public class ConstructionProjectEstimationService {
public static ArrayList <ConstructionProject> buildConstructionProjectList(List <String>
consProjectRecords) {
	final String COMMADELIMITER = ",";
	ArrayList <ConstructionProject> consProjectRecordList = new
			ArrayList<ConstructionProject>();
	for(String s: consProjectRecords) {
		String[] s1=s.split(COMMADELIMITER);
		String id=s1[0];
		Date d=ApplicationUtil.stringToDateConverter(s1[1]);
		String typeproject=s1[2];
		String structure=s1[3];
		double areaInSqFt=Double.parseDouble(s1[4]);
		double costs[]=estimateTimeAndCostForConstruction(typeproject, structure, areaInSqFt);
		double estimatedCostInLac=costs[0];
		double estimatedTimeInMon=costs[1];
		ConstructionProject p=new ConstructionProject(id, d, typeproject, structure, areaInSqFt,  estimatedCostInLac, estimatedTimeInMon);
		consProjectRecordList.add(p);
	}
	return consProjectRecordList;
}
public boolean addConstructionProjectDetails(String inputFeed) throws
	ConstructionEstimationException {
	ArrayList<ConstructionProject> p=buildConstructionProjectList(ApplicationUtil.readFile(inputFeed));
	CostAndTimeEstDAO obj=new CostAndTimeEstDAO();
	if(obj.insertConstructionProject(p)) {
		ArrayList<ConstructionProject> p1=obj.getConstructionProjectsData();
		for(ConstructionProject cp: p1) {
			System.out.println(cp);
		}
		return true;
	}
	return false;
}
public static double[] estimateTimeAndCostForConstruction(String projectType,String
structure,double areaInSqFt)
{
	double costEstimateInRs=0.0,timeEstimateInMonths=0.0;
	double costs[] = {costEstimateInRs,timeEstimateInMonths};
/*
* The Cost Estimate and
*
Based on the type of the Project & the Structure , according to the required
area of Construction, the cost & time have to be calculated based on the base
data available in the table provided in the use case document:
For eg. If the Project Type is “Commercial” and the structure
is “Shopping Complex” the cost incurred for the construction of
per sq. ft is Rs.2600 and the time taken for the construction of
the 1000 sq ft of the same project is 0.23 Months,
calculation has to be performed on the similar basis
i.e Pro rata basis depending upon the type and the area of construction.
*/
	if(projectType.equals("Commercial")) {
		if(structure.equals("Shopping Complex")) {
			costs[0]=2600*areaInSqFt;
			costs[1]=0.23*areaInSqFt/1000;
		}
		else if(structure.equals("ResApartments")) {
			costs[0]=2750*areaInSqFt;
			costs[1]=0.24*areaInSqFt/1000;
		}
		else {
			costs[0]=2600*areaInSqFt;
			costs[1]=0.2*areaInSqFt/1000;
		}
	}
	else if(projectType.equals("Infrastructural")) {
		if(structure.equals("Bridge")) {
			costs[0]=10000*areaInSqFt;
			costs[1]=0.25*areaInSqFt/1000;
		}
		else if(structure.equals("FlyOver")) {
			costs[0]=14000*areaInSqFt;
			costs[1]=0.22*areaInSqFt/1000;
		}
		else {
			costs[0]=8000*areaInSqFt;
			costs[1]=(0.25/1000)*areaInSqFt;
		}
	}
	else {
		if(structure.equals("House")) {
			costs[0]=2250*areaInSqFt;
			costs[1]=0.26*areaInSqFt/1000;
		}
		else if(structure.equals("Apartments")) {
			costs[0]=2500*areaInSqFt;
			costs[1]=0.24*areaInSqFt/1000;
		}
		else {
			costs[0]=2750*areaInSqFt;
			costs[1]=0.23*areaInSqFt/1000;
		}
	}
	return costs;
}
}