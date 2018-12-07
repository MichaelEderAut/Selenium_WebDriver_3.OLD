package com.github.michaelederaut.selenium3.sitelib;

import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.github.michaelederaut.basics.ToolsBasics;

import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

public enum BrowserTypes {
	
	FireFox(BrowserType.FIREFOX), InternetExplorer(BrowserType.IEXPLORE), Edge(BrowserType.EDGE);
	
	public static final String S_pn_ie_bin   = "C:\\Program Files\\Internet Explorer\\iexplore.exe";
	
	// currently not used
	// explorer.exe shell:Appsfolder\Microsoft.MicrosoftEdge_8wekyb3d8bbwe!MicrosoftEdge
	public static final String S_pn_edge_bin = "C:\\Windows\\SystemApps\\Microsoft.MicrosoftEdge_8wekyb3d8bbwe\\MicrosoftEdge.exe";
	
	public static final String S_pn_ff_bin   = "H:\\10\\Editors_Explorers\\web\\FireFox\\63.0.3\\firefox.exe";
	
	// Download latest version from https://github.com/SeleniumHQ/selenium/tree/master/cpp/prebuilt/x64/Release
	public static final String S_bnt_ie_srv_bin = "IEDriverServer.2018-11-11.64"; // "IEDriverServer.3.14.0.0.64";
	public static final String S_bn_ie_srv_bin = S_bnt_ie_srv_bin + "." + ToolsBasics.S_windows_suffix_exe;
	public static final String S_dna_ie_srv_bin = "H:\\10\\Editors_Explorers\\web\\IE_Server\\3.14.0.0";
	
	public static final String S_bnt_edge_srv_bin = "MicrosoftWebDriver";
    public static final String S_bn_edge_srv_bin = S_bnt_edge_srv_bin + "." + ToolsBasics.S_windows_suffix_exe;
    public static final String S_dna_edge_srv_bin =  "H:\\10\\Editors_Explorers\\web\\Edge_Server\\6.17134";
    public static final String S_pn_edge_srv_bin =  S_dna_edge_srv_bin + "\\" + S_bn_edge_srv_bin;
	
	public static final String S_bnt_ff_srv_bin = "geckodriver-v0.21.0-win64";
	public static final String S_bn_ff_srv_bin  = S_bnt_ff_srv_bin + "." + ToolsBasics.S_windows_suffix_exe;
	public static final String S_dna_ff_srv_bin = "H:\\10\\Editors_Explorers\\web\\FireFox\\Selenium_Server";
	public static final String S_pn_ff_srv_bin  =  S_dna_ff_srv_bin + "\\" + S_bn_ff_srv_bin;
	
	// public static final String S_pna_ie_srv_bin = S_dna_ie_srv_bin + "\\" + S_bn_ie_srv_bin;
	
	public static final int    I_ie_drv_srv_port = 5555;
	
	public  String S_pn_browser_binary;
	public  String S_pna_drv_srv_binary;
	public  String S_bn_drv_srv_bin  = null;
	public  File F_browser_binary    = null;
	public  File F_dna_srv_binary    = null;
	public  File F_pn_drv_srv_binary = null;
	
	private String S_broswer_name;
//	private RemoteWebDriver O_remote_driver;
	public Class<? extends RemoteWebDriver> OT_webdriver;
	
	private BrowserTypes (final String PI_S_browser_name) {
	
		IllegalArgumentException E_ill_arg;
		IOException E_io;
		RuntimeException E_rt;
		String S_msg_1, S_msg_2;
		
	switch (PI_S_browser_name)	{
	
	case BrowserType.FIREFOX: 
		OT_webdriver = FirefoxDriver.class;
		S_pn_browser_binary = S_pn_ff_bin;
		if (StringUtils.isBlank(S_pn_ff_bin)) {
			S_msg_1 = "Invalid pathname for " + PI_S_browser_name + " runtime exectuable: \"" + S_pn_browser_binary + "\"";
		    E_io = new IOException(S_msg_1);
		    S_msg_2 = "Unable to instantiate " + OT_webdriver.getSimpleName();
		    E_rt = new RuntimeException(S_msg_2, E_io);
		    throw E_rt;
		   }	
		
		F_browser_binary = new File (S_pn_browser_binary);
		if (!F_browser_binary.isFile()) {
		   S_msg_1 = "Unable to locate file: \"" + S_pn_browser_binary + "\"";
		   E_io = new IOException(S_msg_1);
	       S_msg_2 = "Unable to instantiate " + OT_webdriver.getSimpleName();
		   E_rt = new RuntimeException(S_msg_2, E_io);
		   throw E_rt;
		   }
		if (!F_browser_binary.canExecute()) {
			S_msg_1 = "Unable to exectute file: \"" + S_pn_browser_binary + "\"";
			E_io = new IOException(S_msg_1);
			S_msg_2 = "Unable to instantiate " + OT_webdriver.getSimpleName();
			E_rt = new RuntimeException(S_msg_2, E_io);
			throw E_rt;
			}
		      
		  F_dna_srv_binary = new File(S_dna_ff_srv_bin);
		  if (!F_dna_srv_binary.isDirectory()) {
			  S_msg_1 = "Unable to locate folder: \"" + S_dna_ff_srv_bin + "\"";
			   E_io = new IOException(S_msg_1);
			   S_msg_2 = "Unable to instantiate " + OT_webdriver.getSimpleName();
			   E_rt = new RuntimeException(S_msg_2, E_io);
			   throw E_rt; 
		       }
		     this.S_bn_drv_srv_bin     = S_bn_ff_srv_bin;
		     this.S_pna_drv_srv_binary = S_dna_ff_srv_bin + "\\" + this.S_bn_drv_srv_bin;
		     F_pn_drv_srv_binary = new File (S_pna_drv_srv_binary);
		     if (!F_pn_drv_srv_binary.isFile()) {
				   S_msg_1 = "Unable to locate Gecko-Driver: \"" + S_pna_drv_srv_binary + "\"";
				   E_io = new IOException(S_msg_1);
				   S_msg_2 = "Unable to instantiate " + OT_webdriver.getSimpleName();
				   E_rt = new RuntimeException(S_msg_2, E_io);
				   throw E_rt;
				   }
		     if (!F_pn_drv_srv_binary.canExecute()) {
				 S_msg_1 = "Unable to execute Gecko-Driver: \"" + S_pna_drv_srv_binary + "\"";
				 E_io = new IOException(S_msg_1);
				 S_msg_2 = "Unable to instantiate " + OT_webdriver.getSimpleName();
				 E_rt = new RuntimeException(S_msg_2, E_io);
				 throw E_rt;
				 }
		break; // FireFox
	
	case BrowserType.IE: 
	case BrowserType.IEXPLORE:
		OT_webdriver = InternetExplorerDriver.class;
		S_pn_browser_binary = S_pn_ie_bin;
		if (StringUtils.isBlank(S_pn_browser_binary)) {
		   S_msg_1 = "Invalid pathname for MS Internet Explorer runtime exectuable: \"" + S_pn_browser_binary + "\"";
		   E_io = new IOException(S_msg_1);
		   S_msg_2 = "Unable to instantiate " + OT_webdriver.getSimpleName();
		   E_rt = new RuntimeException(S_msg_2, E_io);
		   throw E_rt;
		   }
	
	    F_browser_binary = new File(S_pn_browser_binary);
		if (!F_browser_binary.isFile()) {
			   S_msg_1 = "Unable to locate file: \"" + S_pn_browser_binary + "\"";
			   E_io = new IOException(S_msg_1);
			   S_msg_2 = "Unable to instantiate " + OT_webdriver.getSimpleName();
			   E_rt = new RuntimeException(S_msg_2, E_io);
			   throw E_rt;
			   }
		if (!F_browser_binary.canExecute()) {
			   S_msg_1 = "Unable to execute file: \"" + S_pn_browser_binary + "\"";
			   E_io = new IOException(S_msg_1);
			   S_msg_2 = "Unable to instantiate " + OT_webdriver.getSimpleName();
			   E_rt = new RuntimeException(S_msg_2, E_io);
			   throw E_rt;
			   }
		
		F_dna_srv_binary = new File(S_dna_ie_srv_bin);
		if (!F_dna_srv_binary.isDirectory()) {
			S_msg_1 = "Unable to locate folder: \"" + S_dna_ie_srv_bin + "\"";
			   E_io = new IOException(S_msg_1);
			   S_msg_2 = "Unable to instantiate " + OT_webdriver.getSimpleName();
			   E_rt = new RuntimeException(S_msg_2, E_io);
			   throw E_rt; 
		       }
		this.S_bn_drv_srv_bin = S_bn_ie_srv_bin;
		this.S_pna_drv_srv_binary = S_dna_ie_srv_bin + "\\" + this.S_bn_drv_srv_bin;
		this.F_pn_drv_srv_binary = new File(S_pna_drv_srv_binary);
		
		if (!F_pn_drv_srv_binary.isFile()) {
		   S_msg_1 = "Unable to locate file: \"" + S_pna_drv_srv_binary + "\""; 
		   E_io = new IOException(S_msg_1);
		   S_msg_2 = "Unable to instantiate " + OT_webdriver.getSimpleName();
		   E_rt = new RuntimeException(S_msg_2, E_io);
		   throw E_rt;
		   }
		if (!F_pn_drv_srv_binary.canExecute()) {
		   S_msg_1 = "Unable to execute file: \"" + S_pna_drv_srv_binary + "\"";
		   E_io = new IOException(S_msg_1);
		   S_msg_2 = "Unable to instantiate " + OT_webdriver.getSimpleName();
		   E_rt = new RuntimeException(S_msg_2, E_io);
		   throw E_rt;
		   }
		
		break;  // MS Internet Explorer
	case BrowserType.EDGE:
		OT_webdriver = EdgeDriver.class;
		S_pn_browser_binary = S_pn_edge_bin;
		if (StringUtils.isBlank(S_pn_browser_binary)) {
		   S_msg_1 = "Invalid pathname for " + PI_S_browser_name + " runtime exectuable: \"" + S_pn_browser_binary + "\"";
		   E_io = new IOException(S_msg_1);
		   S_msg_2 = "Unable to instantiate " + OT_webdriver.getSimpleName();
		   E_rt = new RuntimeException(S_msg_2, E_io);
		   throw E_rt;
		   }
	
	    F_browser_binary = new File(S_pn_browser_binary);
		if (!F_browser_binary.isFile()) {
			   S_msg_1 = "Unable to locate file: \"" + S_pn_browser_binary + "\"";
			   E_io = new IOException(S_msg_1);
			   S_msg_2 = "Unable to instantiate " + OT_webdriver.getSimpleName();
			   E_rt = new RuntimeException(S_msg_2, E_io);
			   throw E_rt;
			   }
		if (!F_browser_binary.canExecute()) {
			   S_msg_1 = "Unable to execute file: \"" + S_pn_browser_binary + "\"";
			   E_io = new IOException(S_msg_1);
			   S_msg_2 = "Unable to instantiate " + OT_webdriver.getSimpleName();
			   E_rt = new RuntimeException(S_msg_2, E_io);
			   throw E_rt;
			   }
		
		F_dna_srv_binary = new File(S_dna_edge_srv_bin);
		if (!F_dna_srv_binary.isDirectory()) {
			S_msg_1 = "Unable to locate folder: \"" + S_dna_edge_srv_bin + "\"";
			   E_io = new IOException(S_msg_1);
			   S_msg_2 = "Unable to instantiate " + OT_webdriver.getSimpleName();
			   E_rt = new RuntimeException(S_msg_2, E_io);
			   throw E_rt; 
		       }
		this.S_bn_drv_srv_bin = S_bn_edge_srv_bin;
		this.S_pna_drv_srv_binary = S_dna_edge_srv_bin + "\\" + this.S_bn_drv_srv_bin;
		this.F_pn_drv_srv_binary = new File(S_pna_drv_srv_binary);
		
		if (!F_pn_drv_srv_binary.isFile()) {
		   S_msg_1 = "Unable to locate file: \"" + S_pna_drv_srv_binary + "\""; 
		   E_io = new IOException(S_msg_1);
		   S_msg_2 = "Unable to instantiate " + OT_webdriver.getSimpleName();
		   E_rt = new RuntimeException(S_msg_2, E_io);
		   throw E_rt;
		   }
		if (!F_pn_drv_srv_binary.canExecute()) {
		   S_msg_1 = "Unable to execute file: \"" + S_pna_drv_srv_binary + "\"";
		   E_io = new IOException(S_msg_1);
		   S_msg_2 = "Unable to instantiate " + OT_webdriver.getSimpleName();
		   E_rt = new RuntimeException(S_msg_2, E_io);
		   throw E_rt;
		   }
		break; // Edge
	default: 
		 S_msg_1 = "Invalid Browser-Type: \'" + PI_S_browser_name + "\'";
		 E_ill_arg = new IllegalArgumentException(S_msg_1);
		 throw E_ill_arg; 	
	     }
	}
}
