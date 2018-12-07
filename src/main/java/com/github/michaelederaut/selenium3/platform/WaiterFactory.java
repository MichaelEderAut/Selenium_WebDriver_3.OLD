package com.github.michaelederaut.selenium3.platform;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
// import java.util.function.Function;
import com.google.common.base.Function;


import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

public class WaiterFactory {

	public static final int I_timeout_short = 2000;
	public static final int I_timeout_std   = 5000;
	public static final int I_timeout_long = 60000;
	
	public static final int I_poll_long    = 1000;
	public static final int I_poll_std     =  500;
	public static final int I_poll_short   =  200;
	
	public static final  HashSet<Class<? extends Throwable>> HO_ignored_exceptions_generic = new HashSet<Class<? extends Throwable>> (Arrays.asList(
			ElementNotVisibleException.class, 
			NoSuchElementException.class, 
			TimeoutException.class,
			InvalidSelectorException.class));	
	
	public static Wait<RemoteWebDriver>FO_create_waiter_elem (
			final RemoteWebDriver PI_O_web_drvr,
			final int PI_I_timeout_total,
			final int PI_I_polling_interval,
	        final Collection<Class<? extends Throwable>> PI_HE_ignore_exceptions
			)  {
		
		Wait<RemoteWebDriver> O_waiter;
			
		O_waiter = new FluentWait<RemoteWebDriver>(PI_O_web_drvr).
				       withTimeout(PI_I_timeout_total, TimeUnit.MILLISECONDS).
				       pollingEvery(PI_I_polling_interval, TimeUnit.MILLISECONDS).
				       ignoreAll(PI_HE_ignore_exceptions);
		
		return O_waiter;	
	}
	
	public static Wait<RemoteWebDriver>FO_create_waiter_elem (
			final RemoteWebDriver PI_O_web_drvr,
			final int PI_I_timeout_total,
			final int PI_I_polling_interval,
	        final Collection<Class<? extends Throwable>> PI_HE_ignore_exceptions,
	        final Function <RemoteWebDriver, WebElement> PI_B_func
			)  {
		
		Wait<RemoteWebDriver> O_waiter;
		
		O_waiter = WaiterFactory.FO_create_waiter_elem(
				PI_O_web_drvr,
				PI_I_timeout_total,
				PI_I_polling_interval, 
				PI_HE_ignore_exceptions);
		
		O_waiter.until(PI_B_func);
		
		return O_waiter;
		
	}
	
	
}
