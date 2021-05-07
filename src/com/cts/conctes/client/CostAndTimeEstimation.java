package com.cts.conctes.client;
import com.cts.conctes.exception.ConstructionEstimationException;
import com.cts.conctes.service.ConstructionProjectEstimationService;
public class CostAndTimeEstimation {
public static void main(String[] args) throws ConstructionEstimationException
{
ConstructionProjectEstimationService cpeService = new
ConstructionProjectEstimationService();
boolean isTrue=cpeService.addConstructionProjectDetails("inputfeed.txt");
if(isTrue) {
	System.out.println("All are added successfully into the database");
}
}
}