/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vtd_con_xpath_vtdgen;
import com.ximpleware.*;
import com.ximpleware.xpath.*;
import java.io.*;
/**
 *
 * @author mchamparini
 */
public class Vtd_con_xpath_VTDGen {

    /**
     * @param args the command line arguments
     */
  public static void main(String argv[]){
     
	
	VTDGen vg = new VTDGen();
	 //C:\Users\mchamparini\Documents\NetBeansProjects\Vtd_con_xpath_VTDGen\data\file_vtd.xml
         //C:\\Users\\mchamparini\\Documents\\NetBeansProjects\\Vtd_con_xpath_VTDGen\\data\\etlexpmx_BTS_20160608020227_2001054.xml
        if (vg.parseFile("C:\\Users\\mchamparini\\Documents\\Test_GSM_HOV_2016060800\\2016060802\\etlexpmx_BTS_20160608020227_2001054.xml",true)){
		try {
                    
		   // vg.parse(true);  // set namespace awareness to true
		    VTDNav vn = vg.getNav();
		    AutoPilot ap = new AutoPilot(vn);
                    AutoPilot ap0 = new AutoPilot(vn);
     	            //ap.declareXPathNameSpace("ns1","/OMeS/PMSetup/PMMOResult/");
                    ap.selectXPath("/OMeS/PMSetup/PMMOResult/PMTarget");
                    //ap0.selectXPath("PMTarget");
                    int result = -1;
		    int count = 0;                   
		    while((result = ap.evalXPath())!=-1){
			//System.out.print(""+result+"  ");     
			System.out.print("Element name ==> "+vn.toString(result));
			int t = vn.getText(); // get the index of the text (char data or CDATA)
                       // System.out.println(""+t);
			if (t!=-1)
			  System.out.println(" Text  ==> "+vn.toNormalizedString(t));
			System.out.println("\n ============================== ");
			count++;
		    }
		    System.out.println("Total # of element "+count);
		}
	        catch (NavException e){
	   	     System.out.println(" Exception during navigation "+e);
     		}
	        catch (XPathParseException e){
	     
     		}
	        catch (XPathEvalException e){
	    
     		}
        }
  }
}
