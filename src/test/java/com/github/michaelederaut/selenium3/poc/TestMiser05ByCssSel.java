package com.github.michaelederaut.selenium3.poc;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.WebElement;

import com.github.michaelederaut.selenium3.framework.ByCssS;
import com.github.michaelederaut.selenium3.framework.NavigationUtils;
import com.github.michaelederaut.selenium3.platform.WaiterFactory;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.Locator;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.LocatorVariant;
import com.github.michaelederaut.selenium3.sitelib.BrowserTypes;

public class TestMiser05ByCssSel {
	public static final String S_url_miser = "https://geizhals.at/?cat=gra16_512";

	public static void main(String[] args) {
		WebElement  O_web_element_miser_loc_01_1;
		List<WebElement> AO_web_element_miser_loc_1_01;
		
		BrowserTypes  E_browser_type;
		NavigationUtils O_nav_utils;
		String S_xpath;
		
		E_browser_type = BrowserTypes.FireFox;
//		E_browser_type = BrowserTypes.InternetExplorer;
	    O_nav_utils = new NavigationUtils(E_browser_type);
	    
	    O_nav_utils.FO_get_fluent(
	    		S_url_miser,
	    		WaiterFactory.I_timeout_long, 
	    		WaiterFactory.I_poll_std);
	    
	    S_xpath = "//a[@title='Geizhals']";
	    O_web_element_miser_loc_01_1 = NavigationUtils.O_rem_drv.findElement(ByCssS.loc(
			Locator.xpath, LocatorVariant.regular, S_xpath));   // OK
	    Assert.assertNotNull(O_web_element_miser_loc_01_1);
	    
	    return;
	}

}
