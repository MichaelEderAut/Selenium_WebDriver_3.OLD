package com.github.michaelederaut.selenium3.poc;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.io.StringReader;

import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.commons.text.StrBuilder;
import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultElement;
import org.dom4j.Node;
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
import com.github.michaelederaut.selenium3.platform.XpathConcatenator;
import com.github.michaelederaut.selenium3.platform.XpathGenerators;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.Locator;
import com.github.michaelederaut.selenium3.sitelib.BrowserTypes;
import com.sun.jna.platform.win32.WinReg.HKEY;

import com.github.michaelederaut.basics.HtmlUtils;

import junit.framework.Assert;

public class TestMiser02 {
   
	public static final String S_url_miser = "https://geizhals.at/?cat=gra16_512";
	
	@Test
	public static void main(String[] args) {
		IOException E_io;
		
		WebElement         O_web_element_miser_loc;
		SAXReader          O_sax_reader;
		StringReader       O_string_reader_page_source;
		Document           O_html_dom_document;
		Node               O_dom_node;
		List<Node>         AO_dom_nodes;
		DefaultElement     O_default_ele;
		
		RuntimeException E_rt;
		RemoteWebDriver /*RemoteWebDriver */  O_rem_web_drv;
	
		HKEY HKEY_CLASSES_ROOT;
		HtmlUtils.Components O_html_components;
	
//		JavascriptExecutor O_js_exexutor;
    	Object O_res_click, 
    	O_res_exec_1, O_res_exec_2, O_res_exec_3, O_res_exec_4, O_res_exec_5, O_res_exec_6,
    	O_res_exec_7, O_res_exec_8, O_res_exec_8_nbr_elems, O_res_exec_8_node;
	    StrBuilder SB_prefix_8, SB_idx_8;

	    AbstractMap<String, ? extends Object> HO_res_exec_8;
	    MutableObject<org.jsoup.nodes.Document> OM_jsoup_doc;
	    
		Class O_clazz;
		Logger O_logger;
		
		
		String S_msg_1, S_msg_2, S_url_actual, S_parent_wdw_handle, S_sub_wdw_handle, S_parent_wdw_title,
		S_clazz_name_short, S_clazz_name_full, S_res_xpath, S_xpath_8,  S_xpath_7_prefix,  S_xpath_7_index, S_xpath_class_names,
		S_cmd_js_1, S_cmd_js_2, S_cmd_js_3, S_cmd_js_4, S_cmd_js_5, S_cmd_js_6, S_cmd_js_7, S_cmd_js_8,
		S_page_source_raw, S_page_source_body, S_page_source_cleaned, S_dom_path;
		Set<String> AS_sub_wdw_handles;
		
	
		BrowserTypes  E_browser_type;
		NavigationUtils O_nav_utils;
		boolean B_is_indexed;
		int I_nbr_web_elements_f1;
		
		// I N I T 
		
		O_clazz = TestMiser01.class;
		S_clazz_name_full = O_clazz.getName();
		O_logger = LogManager.getLogger(O_clazz);
		
		O_logger.traceEntry();
		
		S_page_source_body = "<!DOCTYPE HTML>\n" +
		              "<html>\n" + 
                   "<head>\n" +
		              "</head>\n" +
                   "<body\n>" +
                   "</body\n>" + 
                   "<table id=\"xf_table\" class=\"\">" +
                   
                   "<thead>" +
                   "<tr>" +
                      "<th>Month</th>\n" +
                      "<th>Savings</th>\n" +
                   "</tr>\n" +
                   "</thead>\n" +
                   
                   "<tbody>\n" +
                   "<tr>\n" +
                   "<td>January</td>\n" +
                   "<td>$100</td>\n" +
                   "</tr>\n" +
                   "<tr>\n" +
                       "<td>February</td>" +
                       "<td>$80</td>" +
                   "</tr>" +
                   "</tbody>\n" +
                   
                   "<tfoot>\n" +
                    "<tr>\n" +
                    "<td id=\"sum_1\">Sum</td>\n" +
                    "<td>$180</td>\n" +
                    "</tr>\n" +
                    "</tfoot>\n" +
                    
                   "</table>\n" + 
                   "</html>";
        
		O_sax_reader        = new SAXReader(false);
        O_string_reader_page_source = new StringReader(S_page_source_body);
        
        O_html_dom_document = null;
        try {
		   O_html_dom_document = O_sax_reader.read(O_string_reader_page_source); // DefaultDocument
        } catch (DocumentException PI_E_doc) {
 			PI_E_doc.printStackTrace(System.err);
 		}
        
		HKEY_CLASSES_ROOT = new HKEY(0x80000000);
		
	    E_browser_type = BrowserTypes.FireFox;
//    	E_browser_type = BrowserTypes.InternetExplorer;
	    O_nav_utils = new NavigationUtils(E_browser_type);  // Explorer/Popup 1
	    
	    O_nav_utils.FO_get_fluent(
	    		S_url_miser,
	    		WaiterFactory.I_timeout_long, 
	    		WaiterFactory.I_poll_std);
		
         S_parent_wdw_handle = NavigationUtils.O_rem_drv.getWindowHandle();
         S_parent_wdw_title = NavigationUtils.O_rem_drv.getTitle();
         S_page_source_raw = /* "<!DOCTYPE HTML>" "\n"  + */  NavigationUtils.O_rem_drv.getPageSource();
       //  S_page_source_cleaned = HtmlUtils.
//         O_html_components = HtmlUtils.FO_split(S_page_source_raw);
//         S_page_source_body = O_html_components.S_body_outer;
//         S_page_source_body = "<html>\n" +  S_page_source_body + "\n</html>";
         
         OM_jsoup_doc = new MutableObject<org.jsoup.nodes.Document>();
         O_html_dom_document = HtmlUtils.FO_create_dom4j_doc(S_page_source_raw, OM_jsoup_doc);
         AO_dom_nodes = O_html_dom_document.selectNodes("//*[text()='R9 Fury X']");
          
         S_page_source_cleaned = O_html_dom_document.asXML();
         O_string_reader_page_source = new StringReader(S_page_source_cleaned);
 		
 		O_html_dom_document = null;
 		try {
 			O_html_dom_document = O_sax_reader.read(O_string_reader_page_source);
 		} catch (DocumentException PI_E_doc) {
 			PI_E_doc.printStackTrace(System.err);
 		}
         
 //       S_page_source_filtered = S_page_source_raw.replaceAll("<!--[\\s\\S]*?-->", "");
     
         S_msg_1 = "Parent winwdow title: " + S_parent_wdw_title + "\'  --- handle: " + S_parent_wdw_handle + "\'";
         System.out.println(S_msg_1);

	List <WebElement> AO_web_elements;

	NavigationUtils.O_rem_drv.getPageSource();
	O_web_element_miser_loc = NavigationUtils.O_rem_drv.findElement(By.xpath(".//span"));
	S_res_xpath = NavigationUtils.FS_get_xpath(O_web_element_miser_loc);
	
	AO_web_elements = NavigationUtils.O_rem_drv.findElements(ByXp.xpath(".//span"));
	Assert.assertNotNull(AO_web_elements);
	I_nbr_web_elements_f1 = AO_web_elements.size();
	Assert.assertTrue(I_nbr_web_elements_f1 >= 1);
	O_web_element_miser_loc = AO_web_elements.get(0);
	
	S_res_xpath = NavigationUtils.FS_get_xpath(O_web_element_miser_loc);
	System.out.println("Xpath 1:       " + S_res_xpath);
	
	// https://www.w3.org/TR/webdriver/
	// stackoverflow.com/questions/28051172/how-to-convert-xpath-to-java-code
	
	S_cmd_js_1 = "javascript:document.evaluate(\"" + 
            S_res_xpath + 
            "\", document.body, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue; return XPathResult.length" ;    
	O_res_exec_1 =  NavigationUtils.O_rem_drv.executeScript(S_cmd_js_1);  // null for xpath == ".//span"
	AO_dom_nodes = O_html_dom_document.selectNodes(S_res_xpath);
	
	S_cmd_js_2 = "return document.evaluate(\"" + 
            S_res_xpath + 
            "\", document.body, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue; " ;    
	O_res_exec_2 =  NavigationUtils.O_rem_drv.executeScript(S_cmd_js_2); 
	// rem-web-element (found by == null) for xpath == ".//span"
	
	S_cmd_js_3 = "var AO_results = {}; " +
			"AO_results = document.evaluate(\"" + 
            S_res_xpath + 
            "\", document.body, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue; " + 
            "return AO_results";
	O_res_exec_3 =  NavigationUtils.O_rem_drv.executeScript(S_cmd_js_3);
	// rem-web-element (found by == null) for xpath == ".//span"
	
	S_cmd_js_4 = "var AO_results = []; " +
			"AO_results = document.evaluate(\"" + 
            S_res_xpath + 
            "\", document.body, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null); " + 
            "return AO_results.length";    
	O_res_exec_4 =  NavigationUtils.O_rem_drv.executeScript(S_cmd_js_4); // null for xpath == ".//span"
	
//	var iterator = document.evaluate('//phoneNumber', documentNode, null, XPathResult.UNORDERED_NODE_ITERATOR_TYPE, null );
//
//	try {
//	  var thisNode = iterator.iterateNext();
//	  
//	  while (thisNode) {
//	    alert( thisNode.textContent );
//	    thisNode = iterator.iterateNext();
//	  }	
//	}
//	catch (e) {
//	  dump( 'Error: Document tree modified during iteration ' + e );
//	}
	
	S_cmd_js_5 = "var AO_results = []; var O_iterator, O_this_node, i1; " +
			"O_iterator = document.evaluate(\"" + 
            S_res_xpath + 
            "\", document.body, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null); " + 
            "i1 = 0; " +
             "O_this_node = O_iterator.iterateNext(); " +
             "while (O_this_node) { " +
               "console.log('idx: ' + i1); " +
               "AO_results.push(O_this_node); " +
               "O_this_node = O_iterator.iterateNext(); " +
               "i1++; " +
             "} " +
            "return [AO_results.length, i1]";    
	O_res_exec_5 =  NavigationUtils.O_rem_drv.executeScript(S_cmd_js_5);
	// Array[0..1] of Long for xpath == ".//span"
	
	S_cmd_js_6 = "var AO_results = []; var O_this_node; var i1; " +
			"AO_results = document.evaluate(\"" + 
            S_res_xpath + 
            "\", document.body, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null); " + 
            "O_this_node = AO_results.snapshotItem(0); " +
            "i1 = AO_results.snapshotLength; " +
            "return [O_this_node, i1]";    
	O_res_exec_6 =  NavigationUtils.O_rem_drv.executeScript(S_cmd_js_6);
	// [RemoteWebElement, Long] for xpath == ".//span"
	
	S_cmd_js_7 = "var O_this_node, i1, O_iterator; " +
			"O_iterator = document.evaluate(\"" + 
            S_res_xpath + 
            "\", document.body, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null); " + 
            "i1 = 0; " +
            "O_this_node = O_iterator.iterateNext(); " +
            "return O_this_node";    
	O_res_exec_7 =  NavigationUtils.O_rem_drv.executeScript(S_cmd_js_7);
	// Remote WebElement for xpath == ".//span"
	
	O_web_element_miser_loc = NavigationUtils.O_rem_drv.findElement(By.xpath(".//span"));
	
	SB_prefix_8 = new StrBuilder();
	SB_idx_8    = new StrBuilder();
	S_xpath_8 = ".//span[2]";
	B_is_indexed = XpathConcatenator.FB_is_indexed(S_xpath_8, SB_prefix_8, SB_idx_8);
	
	S_cmd_js_8 = 
			"var H_retval = {};  " + 
	        "var O_iterator, O_node_current, O_node_retval, i1; " +
			"O_iterator = document.evaluate(\"" + 
			S_xpath_8 + 
            "\", document.body, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null); " + 
            "i1 = 0; " +
             "O_node_retval = O_iterator.iterateNext(); " +
             "O_node_current = O_node_retval; " +
             "while (O_node_current) { " +
               "console.log('idx: ' + i1); " +
               "O_node_current = O_iterator.iterateNext(); " +
               "i1++; " +
             "} " +
             "H_retval = {I_nbr_elems: i1, O_node: O_node_retval}; " + 
             "return H_retval;";
	O_res_exec_8 =  NavigationUtils.O_rem_drv.executeScript(S_cmd_js_8);
	HO_res_exec_8 = (AbstractMap<String, ? extends Object>)O_res_exec_8;
	O_res_exec_8_nbr_elems =  HO_res_exec_8.get("I_nbr_elems"); // 701
	O_res_exec_8_node      =  HO_res_exec_8.get("O_node");      //  rem-web-element (found by == null) for xpath == ".//span"
	
//	O_web_element_miser_loc = NavigationUtils.O_rem_drv.findElement(ByXp.className("gh_loc_bt", "div"));
	O_web_element_miser_loc = NavigationUtils.O_rem_drv.findElement(ByXp.loc(Locator.className, "gh_loc_bt", "div"));
	
	Assert.assertNotNull(O_web_element_miser_loc);
	S_res_xpath = NavigationUtils.FS_get_xpath(O_web_element_miser_loc);
	System.out.println("Xpath 1 (div): " + S_res_xpath);
    
	
	Assert.assertNotNull(O_web_element_miser_loc);
	S_res_xpath = NavigationUtils.FS_get_xpath(O_web_element_miser_loc);
	System.out.println("Xpath 2:       " + S_res_xpath);
	
//	O_web_element_miser_loc = NavigationUtils.O_rem_drv.findElement(ByXp.className(new String[]{"gh_loc_bt", "mid"}, "div"));
	O_web_element_miser_loc = NavigationUtils.O_rem_drv.findElement(ByXp.loc(
			Locator.className,
			new String[]{"gh_loc_bt", "mid"},
			"div"));
	
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
