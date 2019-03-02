package com.github.michaelederaut.selenium3.poc;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.github.michaelederaut.selenium3.framework.NavigationUtils;
import com.github.michaelederaut.selenium3.platform.CssSGenerators;
import com.github.michaelederaut.selenium3.platform.WaiterFactory;
import com.github.michaelederaut.selenium3.sitelib.BrowserTypes;

public class TestCssS01 {

	private static final String S_bn_html  = "test_01.html";
	private static final String S_bnr_html = "/" + S_bn_html;
	
	public static void main(String[] args) {
		
		IOException E_io;
		RuntimeException E_rt;
		
		List <WebElement> AO_web_elements;
		WebElement O_web_ele;
		
		BrowserTypes  E_browser_type;
		NavigationUtils O_nav_utils;
		
		URL O_url_test_01_html;
		String  S_msg_1, S_msg_2, S_url_test_01_html, S_pna_html, S_pnr_html,
		        S_id, S_name, S_lnk_txt, S_tag_name, S_line_out;
		int i1, I_nbr_elems_found_01_f1, I_nbr_elems_found_02_f1; 
		
		O_url_test_01_html = CssSGenerators.class.getResource(S_bnr_html);
		try {
				if (O_url_test_01_html == null) {
	               S_msg_1 = "Unable to find file: \'" + S_bn_html  + "\'.";
				   E_io = new IOException(S_msg_1);
				   throw E_io; }

			S_pnr_html = O_url_test_01_html.getPath();
			}
		 
	    catch(IOException PI_E_io) {
				 S_msg_2 = "Unable to read any data from file: \"" + S_bn_html + "\"" ;
				 E_rt = new RuntimeException(S_msg_2, PI_E_io);
				 throw E_rt;
             }
		System.out.println("S_pnr_html: " + S_pnr_html);
		if (!S_pnr_html.startsWith("/")) {
	       S_pnr_html = "/" + S_pnr_html;
		   }
		S_pna_html = "file://" + S_pnr_html;
		System.out.println("S_pna_html: " + S_pna_html);
		
		E_browser_type = BrowserTypes.FireFox;
//		E_browser_type = BrowserTypes.InternetExplorer;
	    O_nav_utils = new NavigationUtils(E_browser_type); 
		
	    O_nav_utils.FO_get_fluent(
	    		S_pna_html,
	    		WaiterFactory.I_timeout_long, 
	    		WaiterFactory.I_poll_std);
	    
	    AO_web_elements =  NavigationUtils.O_rem_drv.findElements(By.cssSelector(".container,even:nth-of-type(2)"));
	    I_nbr_elems_found_01_f1 = AO_web_elements.size();
	    System.out.println("Nbr_elems_found: " +  I_nbr_elems_found_01_f1);
	    for (i1 = 0; i1 < I_nbr_elems_found_01_f1; i1++) {
	    	O_web_ele  = AO_web_elements.get(i1);
	    	S_tag_name = O_web_ele.getTagName();
	    	S_id       = O_web_ele.getAttribute("id");
	    	S_name     = O_web_ele.getAttribute("name");
	    	if (S_tag_name.equals("span")) {
	    	    S_lnk_txt  = O_web_ele.getText();
	    	   }
	    	else {
	    	   S_lnk_txt  = "N/A";
	    	   }
	    	S_line_out = String.format("%2d - tag: %s - id: %s - text: %s", i1, S_tag_name, S_id, S_lnk_txt);
	    	System.out.println(S_line_out);
	        }
		return;
		
	}
}

//			   } catch (IOException PI_E_io) {
//				 S_msg_2 = "Unable to convert an xpath to css-selector";
//					 E_rt = new RuntimeException(S_msg_2, PI_E_io);
//					 throw E_rt;