package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.x5.template.Chunk;
import com.x5.template.Theme;

import db.DBException;
import model.Order;
import model.OrderLine;
import model.Service;

public class CreateContractsController {

	  SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
	  ServiceController serviceController = new ServiceController();
	  String providedServices = "";
	  Date date = new Date();    
	  
	  public String getProvidedServices() {
		  providedServices = "";
		  try {
			ArrayList<Service> services = serviceController.getAllServicesFromDB();
			
			for(Service service: services) {
				providedServices += service.getName() + ", ";  
			  }
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  
		  
		  
		  
		  return providedServices;
	  }
	
	 public boolean writeTemplatedFile(String businessName, String businessRepresentative, String VATNumber, String customerName, String customerEmail) throws IOException
	 {
	     Theme theme = new Theme();
	     Chunk chunk = theme.makeChunk("handling", "txt");
	     
	    

	     // replace static values below with user input
	     chunk.set("residence", "Slovak");         
	     chunk.set("businessName", businessName);
	     chunk.set("businessRepresentative", businessRepresentative);
	     chunk.set("vatNumber", VATNumber);
	     chunk.set("signatureCity", "Bratislava");
	     chunk.set("signatureDate", formatter.format(date));
	     chunk.set("customerName", customerName);
	     chunk.set("providerName", "BH – Profit SK, s.r.o.");
	     try {
	     String outfilePath = "themes/contract_" + customerEmail + ".txt";
	     File file = new File(outfilePath);
	     FileWriter out = new FileWriter(file);

	     chunk.render(out);

	     out.flush();
	     out.close();
	     return true;
	     } catch (Exception e) {
	    	 e.printStackTrace();
	    	 return false;
	     }
	 }
	 
	 public boolean writeServiceFile(String customerName) throws IOException
	 {
	     Theme theme = new Theme();
	     Chunk chunk = theme.makeChunk("providingServicesTemplate", "txt");
	     
	    

	     // replace static values below with user input
	     chunk.set("residence", "Slovak");         
	     
	     chunk.set("signatureCity", "Bratislava");
	     chunk.set("signatureDate", formatter.format(date));
	     chunk.set("customerName", customerName);
	     chunk.set("providerName", "BH – Profit SK, s.r.o.");
	     chunk.set("officeAddress", "Bratislava, Namestie Osloboditelov 52");
	     chunk.set("pricelistUpdateDate", "1.1.2021");
	     chunk.set("minimalRevenue", "100 €");
	     
	     chunk.set("providedServices", getProvidedServices());
	     
	     try {
	     String outfilePath = "themes/provided_Services" + customerName + ".txt";
	     File file = new File(outfilePath);
	     FileWriter out = new FileWriter(file);

	     chunk.render(out);

	     out.flush();
	     out.close();
	     return true;
	     } catch (Exception e) {
	    	 e.printStackTrace();
	    	 return false;
	     }
	 }


}
