package com.github.michaelederaut.selenium3.poc;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;

import com.github.michaelederaut.selenium3.framework.ByXp;
import com.github.michaelederaut.selenium3.framework.NavigationUtils;
import com.github.michaelederaut.selenium3.framework.RemoteWebElementXp;
// import com.github.michaelederaut.selenium3.platform.XpathGenerators.DomOffset;
import com.github.michaelederaut.basics.xpath2cssselector.DomRootElements.DomOffset;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.DomVectorExtendedSelector;
import com.github.michaelederaut.selenium3.platform.WaiterFactory;
import com.github.michaelederaut.selenium3.sitelib.BrowserTypes;

public class TestDomOffsetVectors {
	
	public static final String S_url_miser = "https://geizhals.at/?cat=gra16_512";
	
	public static void main(String[] args) {
		IOException E_io;
	
		BrowserTypes  E_browser_type;
		NavigationUtils O_nav_utils;
	    DomOffset       AO_dom_offset_vector[];
		
		List <WebElement> AO_web_elements;
		WebElement         O_web_element;
		ByXp.ByDomOffsets O_by_dom_offsets;
		RemoteWebElementXp O_web_element_xp;
		
		String S_by_dom_offsets;
		
	E_browser_type = BrowserTypes.FireFox;
    O_nav_utils = new NavigationUtils(E_browser_type);  // Explorer/Popup 1
    
    O_nav_utils.FO_get_fluent(
    		S_url_miser,
    		WaiterFactory.I_timeout_long, 
    		WaiterFactory.I_poll_std);
	
	AO_web_elements = NavigationUtils.O_rem_drv.findElements(ByXp.xpath(".//span"));
	O_web_element = AO_web_elements.get(0);
	O_web_element_xp = (RemoteWebElementXp)O_web_element;
	AO_dom_offset_vector = O_web_element_xp.SBO_xpath.AO_dom_offsets;
	O_by_dom_offsets     = new ByXp.ByDomOffsets(AO_dom_offset_vector);
	S_by_dom_offsets     = O_by_dom_offsets.toString();
	System.out.println("ByDomOffsets: " + S_by_dom_offsets);
	
	System.exit(0);
	}
}
