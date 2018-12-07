package com.github.michaelederaut.selenium3.apps;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// import org.jaxen.XPath;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
// import org.jaxen.expr.XPathFactory;

import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.github.michaelederaut.selenium3.framework.ByXp;
import com.github.michaelederaut.selenium3.framework.NavigationUtils;
import com.github.michaelederaut.selenium3.platform.WaiterFactory;
import com.github.michaelederaut.selenium3.sitelib.BrowserTypes;

import com.github.michaelederaut.selenium3.platform.XpathGenerators.Locator;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.LocatorVariant;

import junit.framework.Assert;
import net.sf.saxon.xpath.XPathFactoryImpl;

public class SearchMiser03 {
   
	public static final String S_url_miser = "https://geizhals.at/?cat=gra16_512";
	
	@Test
	public static void main(String[] args) {
		
		IOException E_io;
		RuntimeException E_rt;
		RemoteWebDriver /*RemoteWebDriver */  O_rem_web_drv;

//		XPathFactory     O_xpath_factory;
		XPathFactoryImpl O_xpath_factory_impl;
		XPath            O_xpath;
		XPathExpression O_xpath_expr_01, O_xpath_expr_02, O_xpath_expr_03;
		// xpath --> m_main_expr --> m_isTopLevel
		//                       --> m_		
		WebElement         O_web_element_miser_loc;
//		JavascriptExecutor O_js_exexutor;
//		Object O_res_click, O_res_execute_1, O_res_execute_2, O_res_execute_3, O_res_execute_4;

		Class O_clazz;
		Logger O_logger;
		
		String S_msg_1, S_msg_2, S_url_actual, S_parent_wdw_handle, S_sub_wdw_handle, S_parent_wdw_title,
		S_clazz_name_short, S_clazz_name_full, S_res_xpath, 
		S_xpath_for_parse_01, S_xpath_for_parse_02, S_xpath_for_parse_03;
		Set<String> AS_sub_wdw_handles;
		HashSet<Class<? extends Throwable>> HO_ignored_exceptions_generic;
	
//		Wait<RemoteWebDriver>  O_waiter_elem_std, O_waiter_elem_long;
		BrowserTypes  E_browser_type;
		NavigationUtils O_nav_utils;
		
		// I N I T 
		
		O_clazz = SearchMiser03.class;
		S_clazz_name_full = O_clazz.getName();
		O_logger = LogManager.getLogger(O_clazz);
		
		O_logger.traceEntry();
//		O_xpath = XPathFactory.newInstance().newXPath();
		O_xpath_factory_impl = new XPathFactoryImpl();
		O_xpath = O_xpath_factory_impl.newXPath();
		S_xpath_for_parse_01 = "div";  // m_isTopLevel: true
		O_xpath_expr_01 = null;
		try {
			O_xpath_expr_01 = O_xpath.compile(S_xpath_for_parse_01);
		} catch (XPathExpressionException PI_E_xpath) {
			S_msg_1 = "Error parsing \'" + S_xpath_for_parse_01 + "\'";
			E_rt = new RuntimeException(S_msg_1, PI_E_xpath);
			throw E_rt;
		    }
		
		S_xpath_for_parse_02 = "table | div";  // m_isTopLevel: false 
		O_xpath_expr_02 = null;
		try {
			O_xpath_expr_02 = O_xpath.compile(S_xpath_for_parse_02);
		} catch (XPathExpressionException PI_E_xpath) {
			S_msg_1 = "Error parsing \'" + S_xpath_for_parse_02 + "\'";
			E_rt = new RuntimeException(S_msg_1, PI_E_xpath);
			throw E_rt;
		    }
		
		S_xpath_for_parse_03 = "(table | div)";   // m_isTopLevel: false
		O_xpath_expr_03 = null;
		try {
			O_xpath_expr_03 = O_xpath.compile(S_xpath_for_parse_03);
		} catch (XPathExpressionException PI_E_xpath) {
			S_msg_1 = "Error parsing \'" + S_xpath_for_parse_03 + "\'";
			E_rt = new RuntimeException(S_msg_1, PI_E_xpath);
			throw E_rt;
		    }
		
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

	
	
	O_web_element_miser_loc = NavigationUtils.O_rem_drv.findElement(ByXp.className("gh_loc_bt"));
	Assert.assertNotNull(O_web_element_miser_loc);
	S_res_xpath = NavigationUtils.FS_get_xpath(O_web_element_miser_loc);
	System.out.println("Xpath 1:       " + S_res_xpath);
	
//	O_web_element_miser_loc = NavigationUtils.O_rem_drv.findElement(ByXp.className("gh_loc_bt", "div"));
	O_web_element_miser_loc = NavigationUtils.O_rem_drv.findElement(ByXp.loc(Locator.className, "gh_loc_bt", "div"));
	
	Assert.assertNotNull(O_web_element_miser_loc);
	S_res_xpath = NavigationUtils.FS_get_xpath(O_web_element_miser_loc);
	System.out.println("Xpath 1 (div): " + S_res_xpath);
    

	// Xpath: .//*[contains(concat(' ',normalize-space(@class),' '),' gh_loc_bt ')]
//	S_xpath_class_names = ".//*[contains(concat(' ',normalize-space(@class),' '),' gh_loc_bt ') and " + 
//	                          "contains(concat(' ',normalize-space(@class),' '),' mid ')]";
//	
	O_web_element_miser_loc = NavigationUtils.O_rem_drv.findElement(ByXp.xpath(S_xpath_for_parse_02));
	Assert.assertNotNull(O_web_element_miser_loc);
	S_res_xpath = NavigationUtils.FS_get_xpath(O_web_element_miser_loc);
	System.out.println("Xpath 2:       " + S_res_xpath);
	
//	O_web_element_miser_loc = O_nav_utils.FO_find_fluent(
//	ByXp.className("gh_loc_bt"), 
//	WaiterFactory.I_timeout_std,
//	WaiterFactory.I_poll_std);
	
	O_logger.traceExit();
	return;

	}

}
