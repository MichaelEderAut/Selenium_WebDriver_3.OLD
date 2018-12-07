package com.github.michaelederaut.selenium3.apps;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ByIdOrName;

import com.github.michaelederaut.selenium3.framework.ByXp;
import com.github.michaelederaut.selenium3.framework.NavigationUtils;
import com.github.michaelederaut.selenium3.framework.RemoteWebElementXp;
import com.github.michaelederaut.selenium3.framework.RemoteWebElementXp.LocatorSelector;
import com.github.michaelederaut.selenium3.framework.RemoteWebElementXp.FoundBy;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.Locator;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.LocatorVariant;
import com.github.michaelederaut.selenium3.platform.WaiterFactory;
import com.github.michaelederaut.selenium3.platform.XpathConcatenator;
import com.github.michaelederaut.selenium3.sitelib.BrowserTypes;

import org.openqa.selenium.remote.RemoteWebDriver;

import junit.framework.Assert;
import net.sf.saxon.xpath.XPathFactoryImpl;

public class SearchMiser02 {
   
	public static final String S_url_miser = "https://geizhals.at/?cat=gra16_512";
	
	@Test
	public static void main(String[] args) {
		
		IOException E_io;
		RuntimeException E_rt;
		RemoteWebDriver /*RemoteWebDriver */  O_rem_web_drv;

		XPath O_xpath;
		javax.xml.xpath.XPathFactory  O_xpath_factory;
	//	XPathFactory O_xpath_factory;
		XPathExpression O_xpath_expr_01, O_xpath_expr_02, O_xpath_expr_03, O_xpath_expr_04;
		// xpath --> m_main_expr --> m_isTopLevel
		//                       --> m_		
		WebElement          O_web_element_miser_loc;
	
		RemoteWebElement    O_remote_web_element_01, O_remote_web_element_02,
		                    O_remote_web_element_01_1, O_remote_web_element_01_1_1, 
		                    O_remote_web_element_01_1_1_1, O_remote_web_element_01_1_1_2,
		                    O_remote_web_element_assert; 
		RemoteWebElementXp  O_remote_web_element_xp_01, O_remote_web_element_xp_01_1;
		
//		JavascriptExecutor O_js_exexutor;
//		Object O_res_click, O_res_execute_1, O_res_execute_2, O_res_execute_3, O_res_execute_4;

		Class O_clazz;
		Logger O_logger;
		
		boolean B_is_top_lvl_01, B_is_top_lvl_02, B_is_top_lvl_03, B_is_top_lvl_04;
		String S_msg_1, S_msg_2, S_url_actual, S_parent_wdw_handle, S_sub_wdw_handle, S_parent_wdw_title,
		S_clazz_name_short, S_clazz_name_full, S_res_xpath, 
		S_xpath_for_parse_04, S_xpath_for_parse_05, S_xpath_for_parse_06, S_xpath_for_parse_07,
		S_found_by_01, S_found_by_02, S_found_by_01_1, S_found_by_01_1_1, S_found_by_01_1_1_1, S_found_by_01_1_1_2;
		
		Set<String> AS_sub_wdw_handles;
		
//		Wait<RemoteWebDriver>  O_waiter_elem_std, O_waiter_elem_long;
		BrowserTypes  E_browser_type;
		NavigationUtils O_nav_utils;
		
		// I N I T 
		
		O_clazz = SearchMiser02.class;
		S_clazz_name_full = O_clazz.getName();
		O_logger = LogManager.getLogger(O_clazz);
		
		O_logger.traceEntry();
	//	O_xpath_factory = null; 
//		try {
		
		S_xpath_for_parse_04 = "//div";  // m_isTopLevel: true
		B_is_top_lvl_01 = XpathConcatenator.FB_is_top_level(S_xpath_for_parse_04);
		
	    O_xpath_factory = javax.xml.xpath.XPathFactory.newInstance();
		O_xpath = javax.xml.xpath.XPathFactory.newInstance().newXPath();
		
			
//		} catch (XPathFactoryConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace(System.err);
//			System.exit(1);
//		}
		
		S_xpath_for_parse_04 = "//div";  // m_isTopLevel: true
		B_is_top_lvl_01 = XpathConcatenator.FB_is_top_level(S_xpath_for_parse_04);
		O_xpath_expr_01 = null;
		try {
			O_xpath_expr_01 = O_xpath.compile(S_xpath_for_parse_04);
		} catch (XPathExpressionException PI_E_xpath) {
			S_msg_1 = "Error parsing \'" + S_xpath_for_parse_04 + "\'";
			E_rt = new RuntimeException(S_msg_1, PI_E_xpath);
			throw E_rt;
		    }
		
		S_xpath_for_parse_05 = "//table | //div";  // m_isTopLevel: false
		B_is_top_lvl_02 = XpathConcatenator.FB_is_top_level(S_xpath_for_parse_05);
			
		O_xpath_expr_02 = null;
		try {
			O_xpath_expr_02 = O_xpath.compile(S_xpath_for_parse_05);
		} catch (XPathExpressionException PI_E_xpath) {
			S_msg_1 = "Error parsing \'" + S_xpath_for_parse_05 + "\'";
			E_rt = new RuntimeException(S_msg_1, PI_E_xpath);
			throw E_rt;
		    }
		
		S_xpath_for_parse_06 = "(//table | //div)";   // m_isTopLevel: false
		B_is_top_lvl_03 = XpathConcatenator.FB_is_top_level(S_xpath_for_parse_06);
		O_xpath_expr_03 = null;
		try {
			O_xpath_expr_03 = O_xpath.compile(S_xpath_for_parse_06);
		} catch (XPathExpressionException PI_E_xpath) {
			S_msg_1 = "Error parsing \'" + S_xpath_for_parse_06 + "\'";
			E_rt = new RuntimeException(S_msg_1, PI_E_xpath);
			throw E_rt;
		    }
		
		S_xpath_for_parse_07 = "(//table | //div)/a";   // m_isTopLevel: true
		B_is_top_lvl_04 = XpathConcatenator.FB_is_top_level(S_xpath_for_parse_07);
		O_xpath_expr_04 = null;
		try {
			O_xpath_expr_04 = O_xpath.compile(S_xpath_for_parse_07);
		} catch (XPathExpressionException PI_E_xpath) {
			S_msg_1 = "Error parsing \'" + S_xpath_for_parse_07 + "\'";
			E_rt = new RuntimeException(S_msg_1, PI_E_xpath);
			throw E_rt;
		    }
		
        E_browser_type = BrowserTypes.FireFox;
	//	E_browser_type = BrowserTypes.InternetExplorer;
	    O_nav_utils = new NavigationUtils(E_browser_type);  // Explorer/Popup 1
	    
	    O_nav_utils.FO_get_fluent(
	    		S_url_miser,
	    		WaiterFactory.I_timeout_long, 
	    		WaiterFactory.I_poll_std);
		
         S_parent_wdw_handle = O_nav_utils.O_rem_drv.getWindowHandle();
         S_parent_wdw_title = O_nav_utils.O_rem_drv.getTitle();
         S_msg_1 = "Parent winwdow title: " + S_parent_wdw_title + "\'  --- handle: " + S_parent_wdw_handle + "\'";
         System.out.println(S_msg_1);

	List <WebElement> AO_web_elements_01, AO_web_elements_02, AO_web_elements_03, AO_web_elements_04, 
	AO_web_elements_05, AO_web_elements_06, AO_web_elements_07;
	LocatorSelector O_found_by_01, O_found_by_02;
	StringBuffer SB_tail = new StringBuffer();

	O_remote_web_element_01 = (RemoteWebElement)NavigationUtils.O_rem_drv.findElement(ByXp.xpath("//div"));
	S_found_by_01 = NavigationUtils.FS_get_xpath(O_remote_web_element_01);
	O_remote_web_element_01_1 = (RemoteWebElement)(O_remote_web_element_01.findElement(ByXp.id("gh_main")));
	S_found_by_01_1 = NavigationUtils.FS_get_xpath(O_remote_web_element_01_1);
	O_remote_web_element_assert = (RemoteWebElement)NavigationUtils.O_rem_drv.findElement(By.xpath(S_found_by_01_1));
	
	O_remote_web_element_01_1_1 = (RemoteWebElement)(O_remote_web_element_01_1.findElement(ByXp.xpath("../..")));
	S_found_by_01_1_1 = NavigationUtils.FS_get_xpath(O_remote_web_element_01_1_1);
	O_remote_web_element_assert = (RemoteWebElement)NavigationUtils.O_rem_drv.findElement(By.xpath(S_found_by_01_1_1));
	
	O_remote_web_element_01_1_1_1 = (RemoteWebElement)(O_remote_web_element_01_1_1.findElement(By.id("gh_main")));
	S_found_by_01_1_1_1 =  NavigationUtils.FS_get_xpath(O_remote_web_element_01_1_1_1);
	O_remote_web_element_assert = (RemoteWebElement)NavigationUtils.O_rem_drv.findElement(By.xpath(S_found_by_01_1_1_1));
	
	
	O_remote_web_element_01_1_1_2 = (RemoteWebElement)(O_remote_web_element_01_1_1.findElement(By.id("gh_wrap")));
	O_remote_web_element_02 = (RemoteWebElement)NavigationUtils.O_rem_drv.findElement(By.className("gh_srch_wrp"));
	S_found_by_02 = NavigationUtils.FS_get_xpath(O_remote_web_element_02);
	
	O_remote_web_element_xp_01 = (RemoteWebElementXp)NavigationUtils.O_rem_drv.findElement(ByXp.loc(Locator.xpath, "//div")); 
	O_remote_web_element_xp_01_1 = (RemoteWebElementXp)(O_remote_web_element_01.findElement(ByXp.loc(Locator.id, "gh_main")));
	O_remote_web_element_01_1 = O_remote_web_element_xp_01_1.FO_get_reduced_web_element();
	O_remote_web_element_01_1 = O_remote_web_element_xp_01_1.FO_get_reduced_web_element(new String[][]{{"xpath", "//div"}, {"id", "gh_main"}}); 
	
	AO_web_elements_01 = NavigationUtils.O_rem_drv.findElements(By.id("gh_wrap"));
	AO_web_elements_02 = NavigationUtils.O_rem_drv.findElements(ByXp.id("gh_wrap"));
	
	AO_web_elements_01 = NavigationUtils.O_rem_drv.findElements(By.linkText("Gainward"));
	
	AO_web_elements_02 = NavigationUtils.O_rem_drv.findElements(ByXp.linkText("Gainward"));
//	AO_web_elements_03 = NavigationUtils.O_rem_drv.findElements(ByXp.linkText("Gainward", "span"));
	AO_web_elements_03 = NavigationUtils.O_rem_drv.findElements(ByXp.Loc.loc(Locator.linkText, "Gainward", "span"));
	
	AO_web_elements_04 = NavigationUtils.O_rem_drv.findElements(ByXp.xpath(S_xpath_for_parse_04));
	O_remote_web_element_01 = (RemoteWebElementXp)AO_web_elements_04.get(0);
	O_remote_web_element_02 = (RemoteWebElement)AO_web_elements_04.get(1);
	O_remote_web_element_01_1 = (RemoteWebElementXp)O_remote_web_element_01.findElement(ByXp.id("gh_main"));
	O_remote_web_element_01_1_1 = (RemoteWebElementXp)O_remote_web_element_01_1.findElement(ByXp.xpath(".."));
	O_found_by_01 = RemoteWebElementXp.FO_get_by_locator(O_remote_web_element_01, SB_tail);
	O_found_by_02 = RemoteWebElementXp.FO_get_by_locator(O_remote_web_element_01_1, SB_tail);
	
	AO_web_elements_05 = NavigationUtils.O_rem_drv.findElements(ByXp.xpath(S_xpath_for_parse_05));
	AO_web_elements_06 = NavigationUtils.O_rem_drv.findElements(ByXp.xpath(S_xpath_for_parse_06));
	AO_web_elements_07 = NavigationUtils.O_rem_drv.findElements(ByXp.xpath(S_xpath_for_parse_07));
	
	O_web_element_miser_loc = NavigationUtils.O_rem_drv.findElement(ByXp.className("gh_loc_bt"));
	Assert.assertNotNull(O_web_element_miser_loc);
	S_found_by_01 = RemoteWebElementXp.FS_get_found_by((RemoteWebElement)O_web_element_miser_loc);
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
	O_web_element_miser_loc = NavigationUtils.O_rem_drv.findElement(ByXp.xpath(S_xpath_for_parse_05));
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
