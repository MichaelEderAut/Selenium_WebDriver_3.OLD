package com.github.michaelederaut.selenium3.sitelib;

import java.util.List;

import com.sun.jna.Pointer;

// import com.sun.jna.PointerType;
// import tools.hvb.basics.WinRegistry;
//import com.github.sarxos.winreg.WindowsRegistry;
//import com.github.sarxos.winreg.HKey;
//import com.github.sarxos.winreg.RegistryException;
import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.Win32Exception;
import com.sun.jna.platform.win32.WinReg;
import com.sun.jna.platform.win32.WinReg.HKEY;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverLogLevel;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.ie.InternetExplorerOptions;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeDriverService.Builder;
// import org.apache.http.impl.auth.BasicScheme;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

// import com.github.michaelederaut.selenium3.platform.DriverServers;
import com.github.michaelederaut.basics.ExecUtils;
import com.github.michaelederaut.basics.ToolsBasics;


public class RemoteWebDrivers /* extends RemoteWebDriver */ {
	
	public static class ShutDownThread extends Thread {
		
	//	public String S_bn_drv_srv = null;
		public String S_pna_drv_srv = null;
		public ShutDownThread(final String PI_S_pna_drv_srv) {
			this.S_pna_drv_srv = PI_S_pna_drv_srv;
		}
	}
	
	// default = 100 for 2K monitors 
	// for 4K monitors try 200
	// public static final int I_requested_zoom_factor_ie = 200; 
	public static       int I_requested_zoom_factor_ie = 100;     
	public static final int IE_ZOOM_REGISTRY_SCALE_FACTOR = 500;
	public static final HKEY REG_KEY_HANDLE = WinReg.HKEY_CURRENT_USER;
	public static final String REG_KEY_PATH_ZOOM = "Software\\Microsoft\\Internet Explorer\\Zoom";
	public static final String REG_KEY_NAME_ZOOM = "ZoomFactor";
	public static final String REG_KEY_NAME_ZONES = 
			"HKEY_CURRENT_USER\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Internet Settings\\Zones";
	
	public static BrowserTypes E_browser_type;
	public static String S_pn_browser_binary;
	public static RemoteWebDriver FO_webdriver_factory (Class<? extends RemoteWebDriver> PI_OT_clazz) {
		
		RuntimeException E_rt; 
		AssertionError E_assert;
		
	//	WindowsRegistry O_win_reg;
		RemoteWebDriver O_retval;
		FirefoxProfile O_ff_prf;
		FirefoxBinary O_ff_bin;
		FirefoxOptions O_ff_opts;
		
		IllegalArgumentException E_ill_arg;
		ShutDownThread O_thread_shutdown;
		
		GraphicsEnvironment O_graph_env;
		GraphicsDevice AO_graph_dev[], O_graph_dev, O_graph_dev_default;
		Toolkit O_default_toolkit;
		DisplayMode O_display_mode_curr;
		Dimension O_dim_screen_size_tk, O_dim_screen_size_ge;
		Runtime O_runtime;
		String S_msg_1, S_msg_2;
		Boolean B_ignore_zoom_setting;
		Integer I_zoom_factor_raw;
		int I_zoom_factor_percent_old, I_zoom_factor_new_raw_expexted, I_zoom_factor_percent_new_act,
		    I_nbr_graph_dev_f1;
		double L_width_tk,  L_height_tk,  L_height_ge, L_width_ge;
	//	Object O_zoom_factor_raw;
		
		O_retval = null;
		
		O_default_toolkit = Toolkit.getDefaultToolkit();
		O_dim_screen_size_tk = O_default_toolkit.getScreenSize();
		L_height_tk = O_dim_screen_size_tk.getHeight();
		L_width_tk = O_dim_screen_size_tk.getWidth();
		
		O_graph_env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		AO_graph_dev = O_graph_env.getScreenDevices();
		O_graph_dev_default = O_graph_env.getDefaultScreenDevice();
		O_display_mode_curr = O_graph_dev_default.getDisplayMode();
		L_width_ge = O_display_mode_curr.getWidth();  // Graphics Environment
		L_height_ge = O_display_mode_curr.getHeight();
		
		if (FirefoxDriver.class.isAssignableFrom(PI_OT_clazz)) {
			E_browser_type = BrowserTypes.FireFox;
			
			O_ff_bin = new FirefoxBinary(E_browser_type.F_browser_binary);
			
			O_ff_prf = new FirefoxProfile();

			O_ff_prf.setPreference("browser.startup.homepage", 0);
			O_ff_prf.setPreference("browser.startup.homepage_override.mstone", "ignore");
			O_ff_prf.setPreference("startup.homepage_welcome_url.additional", "about:blank");
			O_ff_prf.setAcceptUntrustedCertificates(true);
			System.setProperty("webdriver.gecko.driver", BrowserTypes.S_pn_ff_srv_bin);

			// WARNING: The FirefoxDriver constructor taking FirefoxBinary and FirefoxProfile has been deprecated. 
			// Please use the FirefoxDriver(FirefoxOptions) constructor, configuring the FirefoxOptions like this: 
			// new FirefoxOptions().setBinary(binary).setProfile(profile)
			O_ff_opts = new FirefoxOptions();
			O_ff_opts.setBinary(O_ff_bin);
			O_ff_opts.setLogLevel(FirefoxDriverLogLevel.INFO);  // TRACE is most detailed.
			O_ff_opts.setProfile(O_ff_prf);
			//O_retval = new FirefoxDriver(O_ff_bin, O_ff_prf);
			O_retval = new FirefoxDriver(O_ff_opts);
		    }
		else if (InternetExplorerDriver.class.isAssignableFrom(PI_OT_clazz)) {
			
			// appium.io/docs/en/drivers/windows/
			E_browser_type = BrowserTypes.InternetExplorer;
			InternetExplorerDriverService O_ie_service;
			DesiredCapabilities O_ie_capabilities = DesiredCapabilities.internetExplorer();
            InternetExplorerOptions O_ie_options;
	//		O_ie_capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, false);	
    //   sqa.stackexchange.com/questions/9496/webdriver-clicking-button-issue-in-ie-11/13061		
			O_ie_capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);    
	 		O_ie_capabilities.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, "accept");
	 		O_ie_capabilities.setCapability("ignoreProtectedModeSettings", true);
	 		O_ie_capabilities.setCapability("disable-popup-blocking", true);
	 		O_ie_capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
	 		
	 		// for 2K monitors and zoom factor 100 recommended setting is false, 
	 		// for 4K monitors and zoom factor >100 recommended setting is true,
	 		// https://github.com/SeleniumHQ/selenium/blob/master/cpp/iedriver/CommandHandlers/NewSessionCommandHandler.cpp
	 		// https://github.com/SeleniumHQ/selenium/blob/master/cpp/iedriver/BrowserFactory.cpp
	 	
	 		if (L_height_ge <= 1200) {
	 			I_requested_zoom_factor_ie = 100;
	 		    }
	 		else if (L_height_ge <= 1600) {
	 			I_requested_zoom_factor_ie = 150;
	 		    }
	 		else {
	 			I_requested_zoom_factor_ie = 200;
	 		    }
	 		O_ie_capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true); 	// default = false
	 		O_ie_capabilities.setJavascriptEnabled(true);
	 		
			System.setProperty("webdriver.ie.driver", E_browser_type.S_pna_drv_srv_binary);
			System.setProperty("webdriver.ie.driver.loglevel", InternetExplorerDriverLogLevel.ERROR.name());  // recommended setting
		//	System.setProperty("webdriver.ie.driver.loglevel", InternetExplorerDriverLogLevel.TRACE.name());  // for detailed debugging
			O_ie_service = InternetExplorerDriverService.createDefaultService();
			
//			O_win_reg = WindowsRegistry.getInstance();
			
			Pointer O_pointer;
			
			O_pointer = REG_KEY_HANDLE.getPointer();
			B_ignore_zoom_setting = (Boolean)O_ie_capabilities.getCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING);
			if (B_ignore_zoom_setting == Boolean.TRUE) {
				I_zoom_factor_raw = null;
				try {
					I_zoom_factor_raw = Advapi32Util.registryGetIntValue(
					            REG_KEY_HANDLE, 
					            REG_KEY_PATH_ZOOM,
					            REG_KEY_NAME_ZOOM);
				} 
				catch (Win32Exception PI_E_win32) {
					S_msg_1 = "Unable to read zoom factor as Integer from registry with" + "\n" + 
				              "root:     " + REG_KEY_HANDLE.toString() + "\n" +
				              "key path: " + REG_KEY_PATH_ZOOM  + "\n" +
				              "key name: " + REG_KEY_NAME_ZOOM;
					E_rt = new RuntimeException(S_msg_1, PI_E_win32);
					throw E_rt;
				    }
				I_zoom_factor_percent_old = I_zoom_factor_raw / IE_ZOOM_REGISTRY_SCALE_FACTOR;
				
				System.out.println("Zoom-factor old: " + I_zoom_factor_percent_old + " percent");
				if (I_zoom_factor_percent_old != I_requested_zoom_factor_ie) {  // zoom factor has to be adjusted
					I_zoom_factor_new_raw_expexted = I_requested_zoom_factor_ie * IE_ZOOM_REGISTRY_SCALE_FACTOR;
					
					try {
						Advapi32Util.registrySetIntValue(REG_KEY_HANDLE, REG_KEY_PATH_ZOOM, REG_KEY_NAME_ZOOM, I_zoom_factor_new_raw_expexted);
					} catch (Win32Exception PI_E_win32) {
						S_msg_1 = "Unable to write zoom factor as Integer to registry with" + "\n" + 
					              "root:     " + REG_KEY_HANDLE.toString() + "\n" + REG_KEY_HANDLE.getPointer().getInt(0) + "\n" +
					              "key path: " + REG_KEY_PATH_ZOOM  + "\n" +
					              "key name: " + REG_KEY_NAME_ZOOM;
						E_rt = new RuntimeException(S_msg_1, PI_E_win32);
						throw E_rt;
					   }
					I_zoom_factor_raw = null;
					try {
						I_zoom_factor_raw = Advapi32Util.registryGetIntValue(
						           REG_KEY_HANDLE, 
						           REG_KEY_PATH_ZOOM,
						           REG_KEY_NAME_ZOOM);
					} 
					catch (Win32Exception PI_E_win32) {
						S_msg_1 = "Unable to read zoom factor as Integer from registry with" + "\n" + 
					              "root:     " + REG_KEY_HANDLE.toString() + "\n" +
					              "key path: " + REG_KEY_PATH_ZOOM  + "\n" +
					              "key name: " + REG_KEY_NAME_ZOOM;
						E_rt = new RuntimeException(S_msg_1, PI_E_win32);
						throw E_rt;
					    }
					I_zoom_factor_percent_new_act = I_zoom_factor_raw / IE_ZOOM_REGISTRY_SCALE_FACTOR;
					if (I_zoom_factor_percent_new_act != I_requested_zoom_factor_ie) {
						S_msg_1 = "Zoom factor set to: " + I_zoom_factor_percent_new_act + "% should be " + I_requested_zoom_factor_ie;
						E_assert = new AssertionError(S_msg_1);
						throw E_assert;
					    }
				} // if zoom factor has to be adjusted
			} // if zoom factor has to be checked for adjustment
			O_ie_options = new InternetExplorerOptions(O_ie_capabilities);
			O_ie_options.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
			O_retval = new InternetExplorerDriver(O_ie_service, O_ie_options);
			// org.openqa.selenium.SessionNotCreatedException
			// Browser zoom level was set to 200%. It should be set to 100%
		}  // end of IE
		else if (EdgeDriver.class.isAssignableFrom(PI_OT_clazz)) {
			URL O_url;
			Builder O_bldr_1, O_bldr_2, O_bldr_3;
			EdgeDriverService O_edge_service;
			
			E_browser_type = BrowserTypes.Edge;
			
			// O_edge_service = EdgeDriverService.createDefaultService();
			O_bldr_1 = new EdgeDriverService.Builder();
			O_bldr_2 = O_bldr_1.usingDriverExecutable(E_browser_type.F_pn_drv_srv_binary);
			O_bldr_3 = O_bldr_2.usingAnyFreePort();
			O_edge_service = O_bldr_3.build();
			try {
				O_edge_service.start();
			} catch (IOException PI_E_io) {
				S_msg_1 = "Unable to start \'" + O_edge_service.getClass().getName() + 
						  "\' from \"" + BrowserTypes.S_pn_edge_srv_bin + "\"" ;
				E_rt = new RuntimeException(S_msg_1, PI_E_io);
				throw E_rt;
			}
			O_url = O_edge_service.getUrl();
			O_retval = new EdgeDriver(O_edge_service);
		  }
		else {
			S_msg_1 = "RemoteWebDriver of type: '" + PI_OT_clazz.getName()  + "' not supported";
			E_ill_arg = new IllegalArgumentException(S_msg_1);
			throw E_ill_arg;
		}
                                   
		O_thread_shutdown = new ShutDownThread(E_browser_type.S_pna_drv_srv_binary)  {
			
		@Override
		public void run() {
			ExecUtils.FAL_get_processes_by_path(
			   this.S_pna_drv_srv,
			   true,  // kill
			   false); // by force
			ToolsBasics.FV_sleep(500); // 500 ms
			ExecUtils.FAL_get_processes_by_path(
			   this.S_pna_drv_srv,
			   true,  // kill
			   true); // by force
		    }
	    };

	O_runtime = Runtime.getRuntime();
	O_runtime.addShutdownHook(O_thread_shutdown);
		return O_retval;
	}
}
