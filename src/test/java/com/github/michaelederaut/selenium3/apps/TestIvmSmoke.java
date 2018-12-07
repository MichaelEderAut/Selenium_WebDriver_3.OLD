package com.github.michaelederaut.selenium3.apps;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.michaelederaut.basics.ToolsBasics;
import com.github.michaelederaut.selenium3.framework.ByXp;
import com.github.michaelederaut.selenium3.framework.NavigationUtils;
import com.github.michaelederaut.selenium3.framework.RemoteWebElementXp;
import com.github.michaelederaut.selenium3.platform.WaiterFactory;
import com.github.michaelederaut.selenium3.platform.XpathGenerators;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.Locator;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.LocatorVariant;
import com.github.michaelederaut.selenium3.sitelib.BrowserTypes;

import junit.framework.Assert;

public class TestIvmSmoke {
   
	public static final String S_url_ivm = "https://www.ivm.at/";
	
	public static final String S_lnk_txt_ivm_job_site    = "Jobsite";
	public static final String S_lnk_txt_software_and_it = "Software & IT";
	public static final String[] AS_class_names_of_cookies_ok_button_2 = {"btn", "btn-primary"};
	public static final String[] AS_class_names_of_cookies_ok_button_5 = {"btn", "btn-primary", "jb", "accept", "blue"};

	public static WebElement O_web_element_xchange;
	public static String S_sub_wdw_handle_xchange;
//	public static String S_parent_wdw_handle_xchange;
	
	@Test
	public static void main(String[] args) {
		IOException E_io;
		RuntimeException E_rt;
		RemoteWebDriver /*RemoteWebDriver */  O_rem_web_drv;
		WebDriverWait O_waiter;
		
		WebElement O_web_ele_ivm_jobsite_1, O_web_ele_ivm_jobsite_2, O_web_ele_ivm_jobsite_3, O_web_ele_ivm_jobsite_4, 
		           O_web_ele_ivm_jobs_software_and_it, 
		           O_web_ele_ivm_jobs_list_first, 
		           O_web_ele_ivm_jobs_list_last_1,
		           
		           O_web_ele_suche_button, 
		         
		           O_web_ele_cookie_button_1, 
		           O_web_ele_cookie_button_2, O_web_ele_cookie_button_3;
		RemoteWebElementXp  O_web_ele_sparte_last_1, O_web_ele_sparte_last_2, 
		                    O_web_ele_nummer_last_1, O_web_ele_nummer_last_2,
		                    O_web_ele_nummer_last_3, 
		                    O_web_ele_suchergebnis_1, O_web_ele_suchergebnis_2;
		
		List<? extends WebElement>AO_web_elements_merken;
		
		RemoteWebElementXp  O_web_element_suche_input_field_xp;
		WebElement  O_web_element_suche_input_field;
		Object O_res_click_1,  O_res_click_2, O_res_click_3;

		Class O_clazz;
		Logger O_logger;
		
		String S_msg_1, S_parent_wdw_handle,S_parent_wdw_title, S_xpath_of_input_field, S_clazz_name_full, S_cmd_javascript,
		       S_xpath, S_html_txt_1, S_html_txt_2, S_sparte_last, S_nummer_last;
	//	Set<String> AS_sub_wdw_handles;
		int I_nbr_rows_f1, I_nbr_rows_f0;

		BrowserTypes  E_browser_type;
		NavigationUtils O_nav_utils;
		
		// I N I T 
		
		O_clazz = TestIvmSmoke.class;
		S_clazz_name_full = O_clazz.getName();
		O_logger = LogManager.getLogger(O_clazz);
		
		O_logger.traceEntry();
			
        E_browser_type = BrowserTypes.FireFox;
//    	E_browser_type = BrowserTypes.InternetExplorer;
	    O_nav_utils = new NavigationUtils(E_browser_type);
	    
	    O_nav_utils.FO_get_fluent(
	    		S_url_ivm,
	    		WaiterFactory.I_timeout_long * 10, 
	    		WaiterFactory.I_poll_std);
// W O R K	    
         S_parent_wdw_handle = NavigationUtils.O_rem_drv.getWindowHandle();
         S_parent_wdw_title =  NavigationUtils.O_rem_drv.getTitle();
         S_msg_1 = "Parent winwdow title: " + S_parent_wdw_title + "\'  --- handle: " + S_parent_wdw_handle + "\'";
         System.out.println(S_msg_1);
    
//	O_web_element_ivm_jobsite = NavigationUtils.O_rem_drv.findElementByLinkText(S_lnk_txt_ivm_job_site);
    
    O_web_ele_ivm_jobsite_1 = NavigationUtils.O_rem_drv.findElement(ByXp.loc(Locator.linkText, S_lnk_txt_ivm_job_site));     
    O_web_ele_ivm_jobsite_2 = NavigationUtils.O_rem_drv.findElement(ByXp.loc(Locator.linkText, S_lnk_txt_ivm_job_site, "span"));
    O_web_ele_ivm_jobsite_3 = NavigationUtils.O_rem_drv.findElement(ByXp.loc(Locator.linkText, S_lnk_txt_ivm_job_site, "span", XpathGenerators.IGNORED_IDX));
    O_web_ele_ivm_jobsite_4 = NavigationUtils.O_rem_drv.findElement(ByXp.loc(Locator.className, "image-title"));
        		 
    Assert.assertNotNull(O_web_ele_ivm_jobsite_1);
	Assert.assertNotNull(O_web_ele_ivm_jobsite_2);
	Assert.assertNotNull(O_web_ele_ivm_jobsite_3);
	Assert.assertNotNull(O_web_ele_ivm_jobsite_4);
	
	O_web_ele_cookie_button_1 = null;
	
	try {
	   O_web_ele_cookie_button_1 = O_nav_utils.FO_find_fluent(ByXp.loc(
		//		new String[]{"btn", "btn-primary", "jb", "accept", "blue"}, "div"),
				Locator.className, 
				AS_class_names_of_cookies_ok_button_5, "div"),
				WaiterFactory.I_timeout_std,
				WaiterFactory.I_poll_std);
	} catch (TimeoutException | NoSuchElementException PI_E_time_out) {
		S_msg_1 = "OK - button for cookies not found with tag-name: \'" + "div" + "\' and\n" + 
	    "class-names: " + Arrays.toString(AS_class_names_of_cookies_ok_button_5);
	
		E_rt = new RuntimeException(S_msg_1, PI_E_time_out);
		E_rt.printStackTrace(System.out);
	    }
	
	O_web_ele_cookie_button_2 = NavigationUtils.O_rem_drv.findElement(
			ByXp.loc(
				Locator.className,
				LocatorVariant.and,
				AS_class_names_of_cookies_ok_button_2,
				"div"));

	O_web_ele_cookie_button_3 = NavigationUtils.O_rem_drv.findElement(
			ByXp.loc(
				Locator.className,
				LocatorVariant.and,
				AS_class_names_of_cookies_ok_button_5, "div"));
	
	if (O_web_ele_cookie_button_3 != null) {
		O_nav_utils.FO_click_js(O_web_ele_cookie_button_3);
//		O_web_ele_cookie_button.click();
	    }
	
//  O_web_element_ivm_jobsite.click();
    O_res_click_1 = O_nav_utils.FO_click_js(O_web_ele_ivm_jobsite_2);
//	Assert.assertNotNull(O_res_click_1);
    
    ToolsBasics.FV_sleep(1000);
    
// O_web_element_ivm_jobs_software_and_it = NavigationUtils.O_rem_drv.findElementByLinkText(S_lnk_txt_software_and_it);
//	Assert.assertNotNull(O_web_element_ivm_jobs_software_and_it);
    
    O_web_ele_ivm_jobs_software_and_it = O_nav_utils.FO_find_fluent(
    		ByXp.loc(Locator.linkText, LocatorVariant.regular, S_lnk_txt_software_and_it),
			WaiterFactory.I_timeout_std,
			WaiterFactory.I_poll_std);

    ToolsBasics.FV_sleep(2000);
    
	 O_res_click_2 = O_nav_utils.FO_click_js(O_web_ele_ivm_jobs_software_and_it);
	
	ToolsBasics.FV_sleep(2000);
	 
	 O_web_element_suche_input_field_xp = (RemoteWebElementXp)O_nav_utils.FO_find_fluent(
			ByXp.loc(Locator.id, "VolltextWort", "input"),
			WaiterFactory.I_timeout_std,
			WaiterFactory.I_poll_std);

	 Assert.assertNotNull(O_web_element_suche_input_field_xp);
	 S_xpath = NavigationUtils.FS_get_xpath(O_web_element_suche_input_field_xp);
	 
//	 O_driver_actions = new Actions(NavigationUtils.O_rem_drv);
	 
	 O_web_element_suche_input_field = NavigationUtils.O_rem_drv.findElementByXPath(S_xpath);
	 Assert.assertNotNull(O_web_element_suche_input_field);
	 
	 O_nav_utils.FO_click_act(O_web_element_suche_input_field);
	 
	 ToolsBasics.FV_sleep(1000);
   O_nav_utils.FO_sendkeys_act(O_web_element_suche_input_field, "Tester");
//    O_act_sendekys.perform();
	 
//	 O_web_ele_suche_button = NavigationUtils.O_rem_drv.findElement(ByXp.id("VolltextSubmit", "input"));
	 O_web_ele_suche_button = NavigationUtils.O_rem_drv.findElement(ByXp.loc(Locator.id, "VolltextSubmit", "input"));
	 Assert.assertNotNull(O_web_ele_suche_button);
	 
	 O_nav_utils.FO_click_js(O_web_ele_suche_button);
	
	 ToolsBasics.FV_sleep(5000);
	  
	 O_web_ele_ivm_jobs_list_first = NavigationUtils.O_rem_drv.findElement(
			 ByXp.loc(Locator.href, 
			 LocatorVariant.prefix, 
			 "/jobsite/job?job_ID=", 
			 "a"));
	 
//	 O_web_ele_ivm_jobs_list_first = O_nav_utils.FO_find_fluent(		 
//			 ByXp.loc(Locator.href, LocatorVariant.prefix, "/jobsite/job?job_ID=", "a"),
//				WaiterFactory.I_timeout_std * 20000,
//				WaiterFactory.I_poll_std);
	 
	  Assert.assertNotNull(O_web_ele_ivm_jobs_list_first);
	  ToolsBasics.FV_sleep(1000);
	  
	
//	  O_web_ele_ivm_jobs_list_last_1 = (RemoteWebElementXp)O_nav_utils.FO_find_fluent(
//			    ByXp.loc(Locator.href, LocatorVariant.prefix, "/jobsite/job?job_ID=", "a", XpathGenerators.LAST_IDX),
//				WaiterFactory.I_timeout_std * 20,
//				WaiterFactory.I_poll_std);
	  
	  O_web_ele_ivm_jobs_list_last_1 = NavigationUtils.O_rem_drv.findElement(
			  ByXp.loc(Locator.href, 
					  LocatorVariant.prefix,
					  "/jobsite/job?job_ID=", 
					  "a", 
					  XpathGenerators.LAST_IDX));
	  
	  Assert.assertNotNull(O_web_ele_ivm_jobs_list_last_1);
	  S_html_txt_1 = O_web_ele_ivm_jobs_list_last_1.getText();
	  
	  O_web_ele_sparte_last_1 = (RemoteWebElementXp)O_web_ele_ivm_jobs_list_last_1.findElement(ByXp.loc(Locator.xpath, "../.."));
	  O_web_ele_sparte_last_2 = (RemoteWebElementXp)O_web_ele_sparte_last_1.findElement(
			  ByXp.loc(Locator.className, 
					  LocatorVariant.or, 
					   new String[] {"noxsivm"}, // "noxsivm", 
					  "td", 
					  XpathGenerators.IGNORED_IDX, ""));
	  
	  Assert.assertNotNull(O_web_ele_sparte_last_2);
	
	// method .getText() has a bug --> temporary implementation restriction ???   
	//  S_sparte_last = O_web_ele_sparte_last_2.getText();
	  S_sparte_last = O_web_ele_sparte_last_2.S_txt_content;
	  System.out.println("Sparte-Last: " + S_sparte_last);
	  
	  O_web_ele_nummer_last_1 = (RemoteWebElementXp)O_web_ele_sparte_last_2.findElement(ByXp.loc(Locator.xpath, ".."));
	  O_web_ele_nummer_last_2 = (RemoteWebElementXp)O_web_ele_sparte_last_1.findElement(
			   ByXp.loc(Locator.className, LocatorVariant.regular, "nosivm", "td", XpathGenerators.IGNORED_IDX, ""));
	  S_nummer_last = O_web_ele_nummer_last_2.S_inner_txt;
	  System.out.println("Nummer-Last_1: " + S_nummer_last);
	  
	  O_web_ele_nummer_last_3 =  (RemoteWebElementXp)O_web_ele_ivm_jobs_list_last_1.findElement(ByXp.loc(Locator.xpath, "../..")).
	                             findElement(ByXp.loc(Locator.className, LocatorVariant.regular, "noxsivm", "td", XpathGenerators.IGNORED_IDX, "")).
	                             findElement(ByXp.loc(Locator.xpath, "..")).findElement(
	                            		 ByXp.loc(Locator.className,
	                            		 LocatorVariant.regular, 
	                            		  new String[] {"noxsivm", "nosivm", "nomivm"}, // "nosivm", 
	                            		 "td", 
	                            		 XpathGenerators.IGNORED_IDX, ""));
	  Assert.assertNotNull(O_web_ele_nummer_last_3);
	  S_nummer_last = O_web_ele_nummer_last_3.S_inner_txt;
	  System.out.println("Nummer-Last_3: " + S_nummer_last);
	  
	 O_web_ele_ivm_jobs_list_last_1.click();
	 ToolsBasics.FV_sleep(5000);
	 O_web_ele_suchergebnis_1 = (RemoteWebElementXp)NavigationUtils.O_rem_drv.findElement(ByXp.loc(
			 Locator.action, "javascript:history.back();", "form"));
	 Assert.assertNotNull(O_web_ele_suchergebnis_1);
	 O_web_ele_suchergebnis_2 =   (RemoteWebElementXp)O_web_ele_suchergebnis_1.findElement(ByXp.loc(Locator.value, "Suchergebnis", "input"));
	 Assert.assertNotNull(O_web_ele_suchergebnis_2);
	 O_web_ele_suchergebnis_2.click();
	 O_logger.trace("Completed");

	}

}


