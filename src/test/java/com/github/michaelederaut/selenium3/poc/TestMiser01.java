package com.github.michaelederaut.selenium3.poc;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Wait;

import com.github.michaelederaut.selenium3.framework.ByXp;
import com.github.michaelederaut.selenium3.framework.NavigationUtils;
import com.github.michaelederaut.selenium3.platform.WaiterFactory;
import com.github.michaelederaut.selenium3.platform.XpathGenerators;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.Locator;
import com.github.michaelederaut.selenium3.sitelib.BrowserTypes;

import junit.framework.Assert;

public class TestMiser01 {
   
	public static final String S_url_miser = "https://geizhals.at/?cat=gra16_512";
	
	@Test
	public static void main(String[] args) {
		IOException E_io;
		
		RuntimeException E_rt;
		RemoteWebDriver /*RemoteWebDriver */  O_rem_web_drv;
		FirefoxBinary O_ff_bin;
		
		WebElement         O_web_element_miser_loc_1, O_web_element_miser_loc_2, O_web_element_miser_loc_3;
//		JavascriptExecutor O_js_exexutor;
//		Object O_res_click, O_res_execute_1, O_res_execute_2, O_res_execute_3, O_res_execute_4;

		Class O_clazz;
		Logger O_logger;
		
		String S_msg_1, S_msg_2, S_url_actual, S_parent_wdw_handle, S_sub_wdw_handle, S_parent_wdw_title,
		S_clazz_name_short, S_clazz_name_full, S_res_xpath, S_xpath_class_names;
		Set<String> AS_sub_wdw_handles;
		HashSet<Class<? extends Throwable>> HO_ignored_exceptions_generic;
	
//		Wait<RemoteWebDriver>  O_waiter_elem_std, O_waiter_elem_long;
		BrowserTypes  E_browser_type;
		NavigationUtils O_nav_utils;
		
		// I N I T 
		
		O_clazz = TestMiser01.class;
		S_clazz_name_full = O_clazz.getName();
		O_logger = LogManager.getLogger(O_clazz);
		
		O_logger.traceEntry();
			
	    E_browser_type = BrowserTypes.FireFox;
//		E_browser_type = BrowserTypes.InternetExplorer;
	    O_nav_utils = new NavigationUtils(E_browser_type);  // Explorer/Popup 1
	    
	    O_nav_utils.FO_get_fluent(
	    		S_url_miser,
	    		WaiterFactory.I_timeout_long, 
	    		WaiterFactory.I_poll_std);
		
         S_parent_wdw_handle = O_nav_utils.O_rem_drv.getWindowHandle();
         S_parent_wdw_title = O_nav_utils.O_rem_drv.getTitle();
         S_msg_1 = "Parent winwdow title: " + S_parent_wdw_title + "\'  --- handle: " + S_parent_wdw_handle + "\'";
         System.out.println(S_msg_1);

	List <WebElement> AO_web_elements;

	O_web_element_miser_loc_1 = NavigationUtils.O_rem_drv.findElement(By.className("gh_loc_bt"));
	Assert.assertNotNull(O_web_element_miser_loc_1);
	S_res_xpath = NavigationUtils.FS_get_xpath(O_web_element_miser_loc_1);
	System.out.println("Xpath 1:       " + S_res_xpath);
	
//	O_web_element_miser_loc = NavigationUtils.O_rem_drv.findElement(ByXp.className("gh_loc_bt", "div"));
	O_web_element_miser_loc_1 = NavigationUtils.O_rem_drv.findElement(ByXp.loc(Locator.className, "gh_loc_bt", "div"));
	Assert.assertNotNull(O_web_element_miser_loc_1);
	S_res_xpath = NavigationUtils.FS_get_xpath(O_web_element_miser_loc_1);
	System.out.println("Xpath 1 (div): " + S_res_xpath);
    

	// Xpath: .//*[contains(concat(' ',normalize-space(@class),' '),' gh_loc_bt ')]
	S_xpath_class_names = ".//*[contains(concat(' ',normalize-space(@class),' '),' gh_loc_bt ') and " + 
	                          "contains(concat(' ',normalize-space(@class),' '),' mid ')]";
	
	O_web_element_miser_loc_1 = NavigationUtils.O_rem_drv.findElement(ByXp.loc(Locator.xpath, S_xpath_class_names));
	O_web_element_miser_loc_2 = NavigationUtils.O_rem_drv.findElement(By.xpath(S_xpath_class_names));
	O_web_element_miser_loc_3 = NavigationUtils.O_rem_drv.findElement(ByXp.xpath(S_xpath_class_names));
	Assert.assertNotNull(O_web_element_miser_loc_1);
	S_res_xpath = NavigationUtils.FS_get_xpath(O_web_element_miser_loc_1);
	System.out.println("Xpath 2:       " + S_res_xpath);
	
//	O_web_element_miser_loc = NavigationUtils.O_rem_drv.findElement(ByXp.className(new String[]{"gh_loc_bt", "mid"}, "div"));
    O_web_element_miser_loc_1 = NavigationUtils.O_rem_drv.findElement(
    		ByXp.loc(
    		  Locator.className, 
    		  new String[]{"gh_loc_bt", "mid"}, 
    		  "div"));
	Assert.assertNotNull(O_web_element_miser_loc_1);
	S_res_xpath = NavigationUtils.FS_get_xpath(O_web_element_miser_loc_1);
	System.out.println("Xpath 2:       " + S_res_xpath);
	
	O_logger.traceExit();
	return;

	}

}
