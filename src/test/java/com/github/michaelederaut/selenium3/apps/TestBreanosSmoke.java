package com.github.michaelederaut.selenium3.apps;

import java.io.IOException;
import java.util.Arrays;
// import java.util.HashSet;
import java.util.List;
// import java.util.Set;

import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.remote.RemoteWebDriver;
// import org.openqa.selenium.support.ui.ExpectedConditions;
// import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.michaelederaut.basics.ToolsBasics;
import com.github.michaelederaut.selenium3.framework.ByXp;
import com.github.michaelederaut.selenium3.framework.ByXp.Loc;
import com.github.michaelederaut.selenium3.framework.NavigationUtils;
import com.github.michaelederaut.selenium3.framework.NavigationUtils.ClickMode;
import com.github.michaelederaut.selenium3.framework.RemoteWebElementXp;
import com.github.michaelederaut.selenium3.platform.WaiterFactory;
import com.github.michaelederaut.selenium3.platform.XpathGenerators;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.Locator;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.LocatorVariant;
import com.github.michaelederaut.selenium3.sitelib.BrowserTypes;

import junit.framework.Assert;

public class TestBreanosSmoke {
   
	public static final String S_url_breanos = "http://www.breanos.com/";
	
	public static final String S_lnk_txt_breanos_products_de = "Produkte";
	public static final String S_lnk_txt_breanos_products_en = "Products";
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
		
		List<RemoteWebElementXp>  AO_web_elements_navbar_dummy;
		RemoteWebElementXp  O_web_ele_breanos_prducts_1, 
		                    O_web_ele_breanos_prducts_2_1, O_web_ele_breanos_prducts_2_2,
		                    O_web_ele_home_page, O_web_element_kontakt, 
		                    O_web_element_name_input, O_web_element_tel_nr_input, O_web_element_email_input,
		                    O_web_element_betreff_input, O_web_element_nachricht_input;
		 
		RemoteWebElementXp  O_web_element_suche_input_field_xp;
		// Object O_res_click_1,  O_res_click_2, O_res_click_3;

		Class O_clazz;
		Logger O_logger;
		
		String S_msg_1, S_parent_wdw_handle,S_parent_wdw_title, S_clazz_name_full;
	//	Set<String> AS_sub_wdw_handles;
		int I_nbr_rows_f1, I_nbr_rows_f0;

		BrowserTypes  E_browser_type;
		NavigationUtils O_nav_utils;
		
		// I N I T 
		
		O_clazz = TestBreanosSmoke.class;
		S_clazz_name_full = O_clazz.getName();
		O_logger = LogManager.getLogger(O_clazz);
		
		O_logger.traceEntry();
			
  //    E_browser_type = BrowserTypes.Edge;
  //    	E_browser_type = BrowserTypes.InternetExplorer;
     	E_browser_type = BrowserTypes.FireFox;

	    O_nav_utils = new NavigationUtils(E_browser_type);
	    
	    O_nav_utils.FO_get_fluent(
	    		S_url_breanos,
	    		WaiterFactory.I_timeout_long * 10, 
	    		WaiterFactory.I_poll_std);
// W O R K	    
         S_parent_wdw_handle = NavigationUtils.O_rem_drv.getWindowHandle();
         S_parent_wdw_title =  NavigationUtils.O_rem_drv.getTitle();
         S_msg_1 = "Parent winwdow title: " + S_parent_wdw_title + "\'  --- handle: " + S_parent_wdw_handle + "\'";
         System.out.println(S_msg_1);
    
//	O_web_element_ivm_jobsite = NavigationUtils.O_rem_drv.findElementByLinkText(S_lnk_txt_ivm_job_site);
    
    O_web_ele_breanos_prducts_1 = (RemoteWebElementXp)NavigationUtils.O_rem_drv.findElement(
    		ByXp.loc(Locator.className, LocatorVariant.and,
    				new String[] {"dropdown", "mega", "mega-align-left" /*, "open" */},
    				"li"));
    
    O_web_ele_breanos_prducts_2_1 = (RemoteWebElementXp)O_web_ele_breanos_prducts_1.findElement(Loc.loc(
    		  Locator.linkText, S_lnk_txt_breanos_products_de, "a"));
    
    O_web_ele_breanos_prducts_2_2 =  (RemoteWebElementXp)NavigationUtils.O_rem_drv.findElement(
    		        ByXp.loc(Locator.className, LocatorVariant.and,
    				new String[] {"dropdown", "mega", "mega-align-left" /*, "open" */},
    				"li")).
    		         findElement(ByXp.loc(Locator.linkText, S_lnk_txt_breanos_products_de, "a"));
   Assert.assertNotNull(O_web_ele_breanos_prducts_2_2);
    // ToolsBasics.FV_sleep(1000);
 
//   O_nav_utils.FO_click_act(O_web_ele_breanos_prducts_2);
//   ToolsBasics.FV_sleep(200);
//   O_nav_utils.FO_click_act(O_web_ele_breanos_prducts_2);
    // TODO Click js
    NavigationUtils.FO_click_js(O_web_ele_breanos_prducts_2_2, ClickMode.dispatchMouseEvent);
    
    ToolsBasics.FV_sleep(5000);
    
    O_web_ele_home_page = (RemoteWebElementXp)NavigationUtils.O_rem_drv.findElement(Loc.loc(
    		Locator.className, "navbar-brand", "a")).findElement(
    				ByXp.loc(Locator.alt, "Breanos GmbH", "img"));
    
    Assert.assertNotNull(O_web_ele_home_page);
    try {
		O_nav_utils.FO_click_act(O_web_ele_home_page);
	} catch (ElementNotInteractableException PI_E_element_not_interactible) {
	    S_msg_1 = "Problems when clicking on Breanos homepage.";
		E_rt = new RuntimeException(S_msg_1, PI_E_element_not_interactible);
		E_rt.printStackTrace(System.out);
	    }
    
    ToolsBasics.FV_sleep(2000);
    
    O_web_element_kontakt = (RemoteWebElementXp)NavigationUtils.O_rem_drv.findElement(Loc.loc(
    		Locator.linkText, "Kontakt", "a", 0, ".//"));
     Assert.assertNotNull(O_web_element_kontakt);
  
 //  O_nav_utils.FO_click_act(O_web_element_kontakt);
       O_nav_utils.FO_click_js(O_web_element_kontakt);
       ToolsBasics.FV_sleep(3000);
       O_web_element_name_input = (RemoteWebElementXp)NavigationUtils.O_rem_drv.findElement(Loc.loc(
    		Locator.id, "name193", "input"));
       Assert.assertNotNull(O_web_element_name_input);
       O_nav_utils.FO_sendkeys_act(O_web_element_name_input, "Michael Eder");
       
        O_web_element_tel_nr_input = (RemoteWebElementXp)NavigationUtils.O_rem_drv.findElement(Loc.loc(
    		Locator.id, "phone193", "input"));
         Assert.assertNotNull (O_web_element_tel_nr_input);
         O_nav_utils.FO_sendkeys_act(O_web_element_tel_nr_input, "+4312041466");
         
        O_web_element_email_input = (RemoteWebElementXp)NavigationUtils.O_rem_drv.findElement(Loc.loc(
    		Locator.id, "email193", "input"));
        Assert.assertNotNull (O_web_element_email_input);
        O_nav_utils.FO_sendkeys_act(O_web_element_email_input, "Michael.Eder.Vie@gmx.at");
        
        O_web_element_betreff_input = (RemoteWebElementXp)NavigationUtils.O_rem_drv.findElement(Loc.loc(
    		Locator.id, "input_custom_one193", "input"));
        Assert.assertNotNull (O_web_element_betreff_input);
        O_nav_utils.FO_sendkeys_act(O_web_element_betreff_input, "Neue Nachricht");
        
        O_web_element_nachricht_input = (RemoteWebElementXp)NavigationUtils.O_rem_drv.findElement(Loc.loc(
    		Locator.id, "message193", "textarea"));
        Assert.assertNotNull (O_web_element_nachricht_input);
        O_nav_utils.FO_sendkeys_act(O_web_element_nachricht_input, "Eine kurze Nachricht zu Testzwecken.");  
        
        ToolsBasics.FV_sleep(7000);
        
        O_web_ele_home_page = (RemoteWebElementXp)NavigationUtils.O_rem_drv.findElement(Loc.loc(
    		Locator.className, "navbar-brand", "a")).findElement(
    				ByXp.loc(Locator.alt, "Breanos GmbH", "img"));
    
    Assert.assertNotNull(O_web_ele_home_page);
     try {
		O_nav_utils.FO_click_act(O_web_ele_home_page);
	} catch (ElementNotInteractableException PI_E_element_not_interactible) {
		S_msg_1 = "Problems when clicking on Breanos homepage.";
		E_rt = new RuntimeException(S_msg_1, PI_E_element_not_interactible);
		E_rt.printStackTrace(System.out);
	    }
        
	 O_logger.trace("Test run completed");

	}

}
