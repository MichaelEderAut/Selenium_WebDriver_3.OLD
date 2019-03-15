package com.github.michaelederaut.selenium3.framework;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import com.github.michaelederaut.basics.xpath2cssselector.DomRootElements;
import com.github.michaelederaut.basics.xpath2cssselector.DomRootElements.DomOffset;

import static com.github.michaelederaut.selenium3.platform.XpathGenerators.IGNORED_IDX;
import static com.github.michaelederaut.basics.props.PropertyContainerUtils.AT_s2;
import static com.github.michaelederaut.selenium3.platform.XpathGenerators.DEFAULT_PREFIX;
import static com.github.michaelederaut.selenium3.platform.XpathGenerators.DEFAULT_TAG;
import static com.github.michaelederaut.selenium3.platform.XpathGenerators.DEFAULT_TAG_LINK;

import com.github.michaelederaut.selenium3.framework.RemoteWebElementXp.LocatorSelectorXp;
import com.github.michaelederaut.selenium3.platform.XpathGenerators;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.LocatorVariant;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.Locator;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.LocatorEnums;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.LocatorRegularity;
import com.github.michaelederaut.basics.xpath2cssselector.DomRootElements;
// import com.github.michaelederaut.selenium3.platform.XpathGenerators.DomOffset;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.DomVectorExtendedSelector;

import regexodus.Pattern;

/**
 * @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium">Mr. Michael Eder</a>
 */
public abstract class ByXp extends By {
	
	/**
	 * {@link String} selector - attribute name
	 */
	public static final Class<?>[] AT_s1 = new Class<?>[] {String.class};
	
	/**
	 * {@link String} selector - attribute name, {@link String} html tag, <tt>int</tt> index
	 */
//	public static final Class<?>[] AT_strings_3 = new Class<?>[] {String.class, String.class, int.class};
	
	/**
	 * {@link String} selector - attribute name, {@link String} html tag, <tt>int</tt> index, {@link String}, xpath prefix
	 */
//	public static final Class<?>[] AT_strings_4 = new Class<?>[] {String.class, String.class, int.class, String.class};
	
	/**
	 * trailing dom offsets
	 */
//	public static final Class<?>[] AT_strings_5_int = new Class<?>[] {
//		String.class, 
//		String.class, 
//		int.class, 
//		String.class, 
//		int[].class};
	
	/**
	 * <ul>
	 * <li>{@link Locator} method or attribute-name used for locating the html element</li>
	 * <li>{@link String} selector used for locating the html element (<i>Selenium</i> parameter <tt>using</tt>)</li>
	 * </ul>
	 */	
	public static final Class<?>[] AT_e1_s1 = new Class<?>[] {Locator.class, String.class};
	
	public static final Class<?>[] AT_e1_s1_i1 = new Class<?>[] {
		Locator.class, 
		String.class,
		int.class};
		
	public static final Class<?>[] AT_e1_s1_i1_s1 = new Class<?>[] {
		Locator.class, 
		String.class,
		int.class,
		String.class};
		
	public static final Class<?>[] AT_e1_s1_ai = new Class<?>[] {
		Locator.class, 
		String.class,
		int[].class};
		
	public static final Class<?>[] AT_e1_s1_i1_ai = new Class<?>[] {
		Locator.class, 
		String.class,
		int.class,
		int[].class};	
		
	/**
	 * <ul>
	 * <li>{@link Locator} method or attribute-name used for locating the html element</li>
	 * <li>{@link String}[] selectors used for locating the html element (<i>Selenium</i> parameter <tt>using</tt>)</li>
	 * </ul>
	 */	
	public static final Class<?>[] AT_e1_as = new Class<?>[] {Locator.class, String[].class};
	
	public static final Class<?>[] AT_e1_as_i1 = 
			new Class<?>[] {
		       Locator.class, 
		       String[].class,
		       int.class};
	
	public static final Class<?>[] AT_e1_as_i1_s1 = 
			new Class<?>[] {
		       Locator.class, 
		       String[].class,
		       int.class,
		       String.class};	       
		       
	public static final Class<?>[] AT_e1_as_ai = 
			new Class<?>[] {
		       Locator.class, 
		       String[].class,
		       int[].class};	       
	
	public static final Class<?>[] AT_e1_as_i1_ai = 
			new Class<?>[] {
		       Locator.class, 
		       String[].class,
		       int[].class,
		       int.class};
		       
	/**
	 * <ul>
	 * <li>{@link Locator} method or attribute-name used for locating the html element</li>
	 * <li>{@link LocatorVariant} searching for <i>plain</i>, <i>partial</i>, the <i>prefix</i> or the <i>suffix</i> of the selector</li>
	 * <li>{@link String} selector used for locating the html element (<i>Selenium</i> parameter <tt>using</tt>)</li>
	 * </ul>
	 */	
	public static final Class<?>[] AT_e2_s1 = new Class<?>[] {Locator.class, LocatorVariant.class, String.class};
	
	public static final Class<?>[] AT_e2_s1_i1 =  new Class<?>[] {
		Locator.class,
		LocatorVariant.class,
		String.class,
	    int.class	
	    };
	
	public static final Class<?>[] AT_e2_s1_i1_s1 =  new Class<?>[] {
		Locator.class,
		LocatorVariant.class,
		String.class,
	    int.class	
	    };    
	    
	 public static final Class<?>[] AT_e2_s1_ai =  new Class<?>[] {
		Locator.class,
		LocatorVariant.class,
		String.class,
	    int[].class	
	    };   
	    
	 public static final Class<?>[] AT_e2_s1_i1_ai =  new Class<?>[] {
		Locator.class,
		LocatorVariant.class,
		String.class,
	    int.class,
	    int[].class,
	    };   
	    
	/**
	 * <ul>
	 * <li>{@link Locator} method or attribute-name used for locating the html element</li>
	 * <li>{@link LocatorVariant} searching for <i>plain</i>, <i>partial</i>, the <i>prefix</i> or the <i>suffix</i> of the selector</li>
	 * <li>{@link String}[] selectors used for locating the html element (<i>Selenium</i> parameter <tt>using</tt>)</li>
	 * </ul>
	 */	
	public static final Class<?>[] AT_e2_as = new Class<?>[] {Locator.class, LocatorVariant.class, String[].class};

	public static final Class<?>[] AT_e2_as_ai = new Class<?>[] {
		Locator.class, 
		LocatorVariant.class, 
		String[].class,
		int[].class};
	/**
	 * <ul>
	 * <li>{@link Locator} method or attribute-name used for locating the html element</li>
	 * <li>{@link String} selector used for locating the html element (<i>Selenium</i> parameter <tt>using</tt>)</li>
	 * <li>{@link String} html tag</li>
	 * </ul>
	 */	
	public static final Class<?>[] AT_e1_s2 = new Class<?>[] {Locator.class, String.class, String.class};
	
	public static final Class<?>[] AT_e1_s2_ai = new Class<?>[] {
		Locator.class, 
		String.class, 
		String.class,
		int[].class};
	
	
	/**
	 * <ul>
	 * <li>{@link Locator} method or attribute-name used for locating the html element</li>
	 * <li>{@link String}[] selectors used for locating the html element (<i>Selenium</i> parameter <tt>using</tt>)</li>
	 * <li>{@link String} html tag</li>
	 * </ul>
	 */	
	public static final Class<?>[] AT_e1_as_s1 = new Class<?>[] {Locator.class, String[].class, String.class};
	
	public static final Class<?>[] AT_e1_as_s1_ai = new Class<?>[] {
		Locator.class, 
		String[].class, 
		String.class,
		int[].class};
	
	/**
	 * <ul>
	 * <li>{@link Locator} method or attribute-name used for locating the html element</li>
	 * <li>{@link LocatorVariant} searching for <i>plain</i>, <i>partial</i>, the <i>prefix</i> or the <i>suffix</i> of the selector</li>
	 * <li>{@link String} selector used for locating the html element (<i>Selenium</i> parameter <tt>using</tt>)</li>
	 * <li>{@link String} html tag</li>
	 * </ul>
	 */	
	public static final Class<?>[] AT_e2_s2 = new Class<?>[] {
		   Locator.class, 
		   LocatorVariant.class, 
		   String.class, 
		   String.class};
	
	public static final Class<?>[] AT_e2_s2_ai = new Class<?>[] {
		Locator.class, 
		String.class, 
		String.class,
		int[].class
		};	   
		   
	/**
	 * <ul>
	 * <li>{@link Locator} method or attribute-name used for locating the html element</li>
	 * <li>{@link LocatorVariant} searching for <i>plain</i>, <i>partial</i>, the <i>prefix</i> or the <i>suffix</i> of the selector</li>
	 * <li>{@link String}[] selectors used for locating the html element (<i>Selenium</i> parameter <tt>using</tt>)</li>
	 * <li>{@link String} html tag</li>
	 * </ul>
	 */	
	public static final Class<?>[] AT_e2_as_s1 = new Class<?>[] {
		   Locator.class, 
		   LocatorVariant.class, 
		   String[].class, 
		   String.class};
		      
   /**
	 * <ul>
	 * <li>{@link Locator} method or attribute-name used for locating the html element</li>
	 * <li>{@link String} selector used for locating the html element (<i>Selenium</i> parameter <tt>using</tt>)</li>
	 * <li>{@link String} html tag</li>
	 * <li><tt>int</tt> index of the returned element</li>
	 * </ul>
	 */	
	public static final Class<?>[] AT_e1_s2_i1 = new Class<?>[] {
		Locator.class, 
		String.class, 
		String.class,
		int.class};
		
		public static final Class<?>[] AT_e1_s2_i1_ai = new Class<?>[] {
		Locator.class, 
		String.class, 
		String.class,
		int.class,
		int[].class
		};
		
/**
	 * <ul>
	 * <li>{@link Locator} method or attribute-name used for locating the html element</li>
	 * <li>{@link String}[] selectors used for locating the html element (<i>Selenium</i> parameter <tt>using</tt>)</li>
	 * <li>{@link String} html tag</li>
	 * <li><tt>int</tt> index of the returned element</li>
	 * </ul>
	 */	
	public static final Class<?>[] AT_e1_as_s1_i1 = new Class<?>[] {
		Locator.class, 
		String[].class, 
		String.class,
		int.class};	
		
	public static final Class<?>[] AT_e1_as_s1_i1_ai = new Class<?>[] {
		Locator.class, 
		String[].class, 
		String.class,
		int.class,
		int[].class};	
				
	/**
	 * <ul>
	 * <li>{@link Locator} method or attribute-name used for locating the html element</li>
	 * <li>{@link LocatorVariant} searching for <i>plain</i>, <i>partial</i>, the <i>prefix</i> or the <i>suffix</i> of the selector</li>
	 * <li>{@link String} selector used for locating the html element (<i>Selenium</i> parameter <tt>using</tt>)</li>
	 * <li>{@link String} html tag</li>
	 * <li><tt>int</tt> index of the returned element</li>
	 * </ul>
	 */	
	public static final Class<?>[] AT_e2_s2_i1 = new Class<?>[] {
		   Locator.class, 
		   LocatorVariant.class, 
		   String.class, 
		   String.class,
		   int.class};	
	
	public static final Class<?>[] AT_e2_s2_i1_ai = new Class<?>[] {
		   Locator.class, 
		   LocatorVariant.class, 
		   String.class, 
		   String.class,
		   int.class,
		   int[].class};		   
		   
	/**
	 * <ul>
	 * <li>{@link Locator} method or attribute-name used for locating the html element</li>
	 * <li>{@link LocatorVariant} searching for <i>plain</i>, <i>partial</i>, the <i>prefix</i> or the <i>suffix</i> of the selector</li>
	 * <li>{@link String}[] selectors used for locating the html element (<i>Selenium</i> parameter <tt>using</tt>)</li>
	 * <li>{@link String} html tag</li>
	 * <li><tt>int</tt> index of the returned element</li>
	 * </ul>
	 */	
	public static final Class<?>[] AT_e2_as_s1_i1 = new Class<?>[] {
		   Locator.class, 
		   LocatorVariant.class, 
		   String[].class, 
		   String.class,
		   int.class};	
		   
		   
   /**
	 * <ul>
	 * <li>{@link Locator} method or attribute-name used for locating the html element</li>
	 * <li>{@link String} selector used for locating the html element (<i>Selenium</i> parameter <tt>using</tt>)</li>
	 * <li>{@link String} html tag</li>
	 * <li><tt>int</tt> index of the returned element</li>
	 * <li>{@link String} xpath prefix</li>
	 * </ul>
	 */	
	public static final Class<?>[] AT_e1_s2_i1_s1 = new Class<?>[] {
		Locator.class, 
		String.class, 
		String.class,
		int.class,
		String.class};
	
				
 /**
	 * <ul>
	 * <li>{@link Locator} method or attribute-name used for locating the html element</li>
	 * <li>{@link String} selector used for locating the html element (<i>Selenium</i> parameter <tt>using</tt>)</li>
	 * <li>{@link String} html tag</li>
	 * <li><tt>int</tt> index of the returned element</li>
	 * <li>{@link String} xpath prefix</li>
	 * </ul>
	 */	
	public static final Class<?>[] AT_e1_as_s1_i1_s1 = new Class<?>[] {
		Locator.class, 
		String[].class, 
		String.class,
		int.class,
		String.class};		

 /**
	 * <ul>
	 * <li>{@link Locator} method or attribute-name used for locating the html element</li>
	 * <li>{@link String}[] selectors used for locating the html element (<i>Selenium</i> parameter <tt>using</tt>)</li>
	 * <li>{@link String} html tag</li>
	 * <li><tt>int</tt> index of the returned element</li>
	 * <li>{@link String} xpath prefix</li>
	 * <li><tt>{@link DomOffset}[]</tt> dom offsets for the context node</li>
	 * </ul>
	 */	
	public static final Class<?>[] AT_e1_as_s1_i1_s1_ao = new Class<?>[] {
		Locator.class, 
		String[].class, 
		String.class,
		int.class,
		String.class};		
		
	/**
	 * <ul>
	 * <li>{@link Locator} method or attribute-name used for locating the html element</li>
	 * <li>{@link LocatorVariant} searching for <i>plain</i>, <i>partial</i>, the <i>prefix</i> or the <i>suffix</i> of the selector</li>
	 * <li>{@link String} selector used for locating the html element (<i>Selenium</i> parameter <tt>using</tt>)</li>
	 * <li>{@link String} html tag</li>
	 * <li><tt>int</tt> index of the returned element</li>
     * <li>{@link String} xpath prefix</li>
	 * </ul>
	 */	
	public static final Class<?>[] AT_e2_s2_i1_s1 = new Class<?>[] {
		   Locator.class, 
		   LocatorVariant.class, 
		   String.class, 
		   String.class,
		   int.class,
		   String.class};			   

/**
	 * <ul>
	 * <li>{@link Locator} method or attribute-name used for locating the html element</li>
	 * <li>{@link LocatorVariant} searching for <i>plain</i>, <i>partial</i>, the <i>prefix</i> or the <i>suffix</i> of the selector</li>
	 * <li>{@link String}[] selectors used for locating the html element (<i>Selenium</i> parameter <tt>using</tt>)</li>
	 * <li>{@link String} html tag</li>
	 * <li><tt>int</tt> index of the returned element</li>
     * <li>{@link String} xpath prefix</li>
	 * </ul>
	 */	
	public static final Class<?>[] AT_e2_as_s1_i1_s1 = new Class<?>[] {
		   Locator.class, 
		   LocatorVariant.class, 
		   String[].class, 
		   String.class,
		   int.class,
		   String.class};		

   /**
	 * <ul>
	 * <li>{@link Locator} method or attribute-name used for locating the html element</li>
	 * <li>{@link String} selector used for locating the html element (<i>Selenium</i> parameter <tt>using</tt>)</li>
	 * <li>{@link String} html tag</li>
	 * <li><tt>int</tt> index of the returned element</li>
	 * <li>{@link String} xpath prefix</li>
	 * <li><tt>int[]</tt> dom offsets for the context node</li>
	 * </ul>
	 */	
	public static final Class<?>[] AT_e1_s2_i1_s1_ai = new Class<?>[] {
		Locator.class, 
		String.class, 
		String.class,
		int.class,
		String.class,
		int[].class};
		
		
    /**
	 * <ul>
	 * <li>{@link Locator} method or attribute-name used for locating the html element</li>
	 * <li>{@link String} selector used for locating the html element (<i>Selenium</i> parameter <tt>using</tt>)</li>
	 * <li>{@link String} html tag</li>
	 * <li><tt>int</tt> index of the returned element</li>
	 * <li>{@link String} xpath prefix</li>
	 * <li><tt>int[]</tt> dom offsets for the context node</li>
	 * </ul>
	 */	
	public static final Class<?>[] AT_e1_s1_as_i1_s1_ai = new Class<?>[] {
		Locator.class, 
		String[].class, 
		String.class,
		int.class,
		String.class,
		int[].class};		
		
		
	/**
	 * <ul>
	 * <li>{@link Locator} method or attribute-name used for locating the html element</li>
	 * <li>{@link LocatorVariant} searching for <i>plain</i>, <i>partial</i>, the <i>prefix</i> or the <i>suffix</i> of the selector</li>
	 * <li>{@link String} selector used for locating the html element (<i>Selenium</i> parameter <tt>using</tt>)</li>
	 * <li>{@link String} html tag</li>
	 * <li><tt>int</tt> index of the returned element</li>
     * <li>{@link String} xpath prefix</li>
     * <li><tt>int[]</tt> dom offsets for the context node</li>
	 * </ul>
	 */	
	public static final Class<?>[] AT_e2_s2_i1_s1_ai = new Class<?>[] {
		   Locator.class, 
		   LocatorVariant.class, 
		   String.class, 
		   String.class,
		   int.class,
		   String.class,
		   int[].class};			  		   
	
	/**
	 * <ul>
	 * <li>{@link Locator} method or attribute-name used for locating the html element</li>
	 * <li>{@link LocatorVariant} searching for <i>plain</i>, <i>partial</i>, the <i>prefix</i> or the <i>suffix</i> of the selector</li>
	 * <li>{@link String} selector used for locating the html element (<i>Selenium</i> parameter <tt>using</tt>)</li>
	 * <li>{@link String} html tag</li>
	 * <li><tt>int</tt> index of the returned element</li>
     * <li>{@link String} xpath prefix</li>
     * <li><tt>int[]</tt> dom offsets for the context node</li>
	 * </ul>
	 */	
	public static final Class<?>[] AT_e2_as_s1_i1_s1_ai = new Class<?>[] {
		   Locator.class, 
		   LocatorVariant.class, 
		   String[].class, 
		   String.class,
		   int.class,
		   String.class,
		   int[].class};		   
		   

//--------------------------------
	 /**
	 * <ul>
	 * <li>{@link Locator} method or attribute-name used for locating the html element</li>
	 * <li>{@link String}[] selectors used for locating the html element (<i>Selenium</i> parameter <tt>using</tt>)</li>
	 * <li>{@link String} html tag</li>
	 * <li><tt>int</tt> index of the returned element</li>
	 * <li>{@link String} xpath prefix</li>
    * <li><tt>{@link DomOffset}[]</tt> dom offsets for the context node</li>
	 * </ul>
	 */	
	public static final Class<?>[] AT_e1_s2_i1_s1_ao = new Class<?>[] {
		Locator.class, 
		String.class, 
		String.class,
		int.class,
		String.class,
		DomOffset[].class};	   
		   
		   
   /**
	 * <ul>
	 * <li>{@link Locator} method or attribute-name used for locating the html element</li>
	 * <li>{@link String}[] selectors used for locating the html element (<i>Selenium</i> parameter <tt>using</tt>)</li>
	 * <li>{@link String} html tag</li>
	 * <li><tt>int</tt> index of the returned element</li>
	 * <li>{@link String} xpath prefix</li>
    * <li><tt>{@link DomOffset}[]</tt> dom offsets for the context node</li>
	 * </ul>
	 */	
	public static final Class<?>[] AT_e1_as_i1_s1_ao = new Class<?>[] {
		Locator.class, 
		String[].class, 
		String.class,
		int.class,
		String.class,
		DomOffset[].class};
			
	/**
	 * <ul>
	 * <li>{@link Locator} method or attribute-name used for locating the html element</li>
	 * <li>{@link LocatorVariant} searching for <i>plain</i>, <i>partial</i>, the <i>prefix</i> or the <i>suffix</i> of the selector</li>
	 * <li>{@link String} selector used for locating the html element (<i>Selenium</i> parameter <tt>using</tt>)</li>
	 * <li>{@link String} html tag</li>
	 * <li><tt>int</tt> index of the returned element</li>
     * <li>{@link String} xpath prefix</li>
     * <li><tt>{@link DomOffset}[]</tt> dom offsets for the context node</li>
	 * </ul>
	 */	
	public static final Class<?>[] AT_e2_s2_i1_s1_ao = new Class<?>[] {
		   Locator.class, 
		   LocatorVariant.class, 
		   String.class, 
		   String.class,
		   int.class,
		   String.class,
		   DomOffset[].class};			   
	
	public static final Class<?>[] AT_e2_as_i1 = new Class<?>[] {
		   Locator.class, 
		   LocatorVariant.class, 
		   String[].class, 
		   int.class};	   
		   
	public static final Class<?>[] AT_e2_as_i1_ai = new Class<?>[] {
		   Locator.class, 
		   LocatorVariant.class, 
		   String[].class, 
		   int.class,
		   int[].class};		   
	/**
	 * <ul>
	 * <li>{@link Locator} method or attribute-name used for locating the html element</li>
	 * <li>{@link LocatorVariant} searching for <i>plain</i>, <i>partial</i>, the <i>prefix</i> or the <i>suffix</i> of the selector</li>
	 * <li>{@link String} selector used for locating the html element (<i>Selenium</i> parameter <tt>using</tt>)</li>
	 * <li>{@link String} html tag</li>
	 * <li><tt>int</tt> index of the returned element</li>
     * <li>{@link String} xpath prefix</li>
     * <li><tt>{@link DomOffset}[]</tt> dom offsets for the context node</li>
	 * </ul>
	 */	
	public static final Class<?>[] AT_e2_as_s1_i1_s1_ao = new Class<?>[] {
		   Locator.class, 
		   LocatorVariant.class, 
		   String[].class, 
		   String.class,
		   int.class,
		   String.class,
		   DomOffset[].class};	
		   
	@Deprecated	   
    public static final Class<?>[] AT_s2_i1_s1 = new Class<?>[] {String.class, String.class, int.class, String.class};
    
    @Deprecated	
    public static final Class<?>[] AT_s2_i1_s1_ai = new Class<?>[]{
    	String.class, 
    	String.class, 
    	int.class, 
    	String.class, 
    	int[].class};
    	
    @Deprecated	
    public static final Class<?>[] AT_s2_i1_s1_ao = new Class<?>[]{
    	String.class, 
    	String.class, 
    	int.class, 
    	String.class, 
    	DomOffset[].class};
    	
    public static final Class<?>[] AT_s3 = new Class<?>[] {String.class, String.class, String.class};
    public static final Class<?>[] AT_s3_ai = new Class<?>[] {String.class, String.class, String.class, int[].class};
    public static final Class<?>[] AT_s3_ao = new Class<?>[] {String.class, String.class, String.class, DomOffset[].class};
    
    public  static final Class<?>[] AT_s1_i1_s1 = new Class<?>[] {String.class, int.class, String.class};
    public  static final Class<?>[] AT_s1_i1_s1_ai = new Class<?>[] {String.class, int.class, String.class, int[].class};
    public  static final Class<?>[] AT_s1_i1_s1_ao = new Class<?>[] {String.class, int.class, String.class, DomOffset[].class};
    
	final static String S_std_field_name_of_selector = "O_selector";
	final static String S_class_name_by_xp = ByXp.class.getCanonicalName();
	
    public LocatorSelectorXp O_loc_sel_xp;
    
	public LocatorSelectorXp FO_get_loc_sel_xp(
			final LocatorEnums O_locator_enums) {
		  
		  RuntimeException     E_rt;
		  NullPointerException E_np;
		  
		  Class      <? extends ByXp> OT_clazz;
		  Constructor<? extends ByXp> M_ctor;
		  LocatorVariant E_locator_variant;
		  Locator        E_locator;
		  
		  Object O_selector;
		  String S_msg_1, S_msg_2, S_clazz_name,  S_selector, 
		          S_field_name_of_selector 
		    //    S_locator, S_clazz_name_tail
		          ;
		  boolean B_or;
		  LocatorSelectorXp O_retval_loc_sel_xp = null;
		  
		  OT_clazz = this.getClass();
		  S_clazz_name = OT_clazz.getSimpleName();
	//	  S_clazz_name_tail = S_clazz_name.substring(2);
		  S_field_name_of_selector = S_std_field_name_of_selector;
		  
		 E_locator_variant =  O_locator_enums.E_locator_variant;
		 E_locator         =  O_locator_enums.E_locator;
		
		              
		try {
			O_selector = FieldUtils.readDeclaredField(this, S_field_name_of_selector, true);
		} catch (IllegalAccessException | IllegalArgumentException PI_E_ill_arg) {
			S_msg_1 = "Unable to read field: \'" + S_field_name_of_selector + "\' from object of type: \'" + this.getClass().getName() + "\""; 
			E_rt = new RuntimeException(S_msg_1, PI_E_ill_arg);
			throw E_rt;
		    }
		  
		if (O_selector == null) {
			 S_msg_1 = "Result field of type: \'" + String.class.getName() + "\' must not be null.";
			 E_np = new NullPointerException(S_msg_1);
			 S_msg_2 =  "Unable to get field: \'" + S_field_name_of_selector + "\' from object of type: \'" + this.getClass().getName() + "\""; 
			 E_rt = new RuntimeException(S_msg_2, E_np);
			 throw E_rt;
		     }
		
		 if (O_selector instanceof String[]) {    // class-names 
			 S_selector = Arrays.toString((String[])O_selector);
			 S_field_name_of_selector = "className";
		     }
		 else if (O_selector instanceof DomOffset[]) {
			 S_selector = Arrays.toString((DomOffset[])O_selector);
		     }
		 else {
		    try {
			  S_selector = (String)O_selector;
		    } catch (ClassCastException PI_E_clsc) {
				S_msg_1 = "Unable to obtain field: \'" + S_field_name_of_selector + "\' from object of type: \'" + O_selector.getClass().getName() + "\""; 
				E_rt = new RuntimeException(S_msg_1, PI_E_clsc);
				throw E_rt;
			    } 
		  }
		 DomVectorExtendedSelector O_using;
		 O_using = new DomVectorExtendedSelector(S_selector);
		 O_retval_loc_sel_xp = new LocatorSelectorXp(
				  E_locator, 
				  E_locator_variant, 
				  O_using,
				  (String)null, // tag
				  IGNORED_IDX,
				  (String)null, // prefix
				  (Constructor<? extends ByXp>)null);
				//  (IndexedStrBuilder)null  // this.S_xpath_selector
				 
		  return O_retval_loc_sel_xp;   
	}	
	
	  public LocatorSelectorXp FO_get_loc_sel_xp() {
		  
		  Class         <? extends ByXp> OT_clazz;
		  LocatorEnums  O_locator_enums;
		  LocatorSelectorXp O_retval_by_locator_xp;
		  
		  OT_clazz = this.getClass();
		  O_locator_enums = XpathGenerators.FO_create_locator_enums(OT_clazz);
		  O_retval_by_locator_xp = this.FO_get_loc_sel_xp(O_locator_enums); 
		  return O_retval_by_locator_xp;
	  }
	  
      /**
  	   * 
  	   * @param locator &colon; {@link Locator} Attribute name used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}<br>
  	   *    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <tt>findElement</tt>(<tt>s</tt>) <tt>(By.</tt> ...<tt>)</tt>
  	   * @param using &colon; {@link String} Selector - attribute value used to search for one or more {@link RemoteWebElement RemoteWebElement(s)} 
  	   * @return {@link ByXp}: Extension of the <a href=https://docs.seleniumhq.org/projects/webdriver/>Selenium</a> {@link By} class.
  	   * 
  	   * @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium&nbsp;loc">Mr. Michael Eder</a>
  	   *
  	   */      
       public static ByXp loc(
    		   final Locator        locator,
    		   final String         using) {
    	   
    	   ByXp O_retval_by_xp;
    	   
    	   O_retval_by_xp = loc(
    			   locator,
    			   LocatorVariant.regular,
    			   using,
    			   XpathGenerators.DEFAULT_TAG,
    			   XpathGenerators.IGNORED_IDX,
    			   XpathGenerators.DEFAULT_PREFIX,
    			   (DomOffset[]) null);
    	   O_retval_by_xp.O_loc_sel_xp.M_ctor = ConstructorUtils.getAccessibleConstructor(
    			   Loc.class, 
    			   AT_e1_s1);
    	   return O_retval_by_xp;
       }
	     
     /**
  	   * 
  	   * @param locator &colon; {@link Locator} Attribute name used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}<br>
  	   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <tt>findElement</tt>(<tt>s</tt>) <tt>(By.</tt> ...<tt>)</tt>
  	   * @param using &colon; {@link String}[] Selectors - attribute values used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}<br> 
  	   * @return {@link ByXp}: Extension of the <a href=https://docs.seleniumhq.org/projects/webdriver/>Selenium</a> {@link By} class.
  	   * 
  	   * @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium&nbsp;loc">Mr. Michael Eder</a>
  	   *
  	   */      
       public static ByXp loc(
    		   final Locator        locator,
    		   final String[]        using) {
    	   
    	   ByXp O_retval_by_xp;
    	   
    	   O_retval_by_xp = loc(
    			   locator,
    			   LocatorVariant.regular,
    			   using,
    			   XpathGenerators.DEFAULT_TAG,
    			   XpathGenerators.IGNORED_IDX,
    			   XpathGenerators.DEFAULT_PREFIX,
    			   (DomOffset[]) null);
    	   O_retval_by_xp.O_loc_sel_xp.M_ctor = ConstructorUtils.getAccessibleConstructor(
    			   Loc.class, 
    			   AT_e1_as);
    	   return O_retval_by_xp;
       }
       
 	  /**
  	   * 
  	   * @param locator &colon; {@link Locator} Attribute name used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}<br>
  	   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <tt>findElement</tt>(<tt>s</tt>) <tt>(By.</tt> ...<tt>)</tt>
  	   * @param variant &colon; {@link LocatorVariant} defaults to <i>regular</i>
  	   * @param using &colon; {@link String} Selector - attribute value used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}<br>
  	   * @return {@link ByXp}: Extension of the <a href=https://docs.seleniumhq.org/projects/webdriver/>Selenium</a> {@link By} class.
  	   * 
  	   * @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium&nbsp;loc">Mr. Michael Eder</a>
  	   *
  	   */
        public static ByXp loc(
     		   final Locator        locator,
     		   final LocatorVariant variant,
     		   final String         using) {
     	   
     	   ByXp O_retval_by_xp;
     	   
     	   O_retval_by_xp = loc(
     			   locator,
     			   variant,
     			   using,
     			   XpathGenerators.DEFAULT_TAG,
     			   XpathGenerators.IGNORED_IDX,
     			   XpathGenerators.DEFAULT_PREFIX,
     			   (DomOffset[]) null); 
     	    O_retval_by_xp.O_loc_sel_xp.M_ctor = ConstructorUtils.getAccessibleConstructor(
    			   Loc.class, 
    			   AT_e2_s1);
     	   return O_retval_by_xp;
        }
        
 	  /**
  	   * 
  	   * @param locator &colon; {@link Locator} Attribute name used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}<br>
  	   *   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <tt>findElement</tt>(<tt>s</tt>) <tt>(By.</tt> ...<tt>)</tt>
  	   * @param variant &colon; {@link LocatorVariant} defaults to <i>regular</i>
  	   * @param using &colon; {@link String}[] Selectors - attribute values used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}<br> 
  	   * @return {@link ByXp}: Extension of the <a href=https://docs.seleniumhq.org/projects/webdriver/>Selenium</a> {@link By} class.
  	   * 
  	   * @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium&nbsp;loc">Mr. Michael Eder</a>
  	   *
  	   */
         public static ByXp loc(
     		   final Locator        locator,
     		   final LocatorVariant variant,
     		   final String[]       using) {
     	   
     	   ByXp O_retval_by_xp;
     	   
     	   O_retval_by_xp = loc(
     			   locator,
     			   variant,
     			   using,
     			   XpathGenerators.DEFAULT_TAG,
     			   XpathGenerators.IGNORED_IDX,
     			   XpathGenerators.DEFAULT_PREFIX,
     			   (DomOffset[]) null);
     	    O_retval_by_xp.O_loc_sel_xp.M_ctor = ConstructorUtils.getAccessibleConstructor(
    			   Loc.class, 
    			   AT_e2_as);
     	   return O_retval_by_xp;
        }
        
     /**
   	   * 
   	   * @param locator &colon; {@link Locator} Attribute name used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}<br>
   	   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <tt>findElement</tt>(<tt>s</tt>) <tt>(By.</tt> ...<tt>)</tt>
   	   * @param using &colon; {@link String} Selector - attribute value used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}
   	   * @param expectedTagName &colon; {@link String} HTML-tag, defaults to "<tt>&#42;</tt>"
   	   * @return {@link ByXp}: Extension of the <a href=https://docs.seleniumhq.org/projects/webdriver/>Selenium</a> {@link By} class.
   	   * 
   	   * @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium&nbsp;loc">Mr. Michael Eder</a>
   	   *
   	   */       
         public static ByXp loc(
      		   final Locator        locator,
      		   final String         using,
      		   final String         expectedTagName) {
      	   
      	   ByXp O_retval_by_xp;
      	   
      	   O_retval_by_xp = loc(
      			   locator,
      			   LocatorVariant.regular,
      			   using,
      			   expectedTagName,
      			   XpathGenerators.IGNORED_IDX,
      			   XpathGenerators.DEFAULT_PREFIX,
      			   (DomOffset[]) null);
      	    O_retval_by_xp.O_loc_sel_xp.M_ctor = ConstructorUtils.getAccessibleConstructor(
    			   Loc.class, 
    			   AT_e1_s2);
      	   return O_retval_by_xp;
         }
  
     /**
   	   * 
   	   * @param locator &colon; {@link Locator} Attribute name used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}<br>
   	   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <tt>findElement</tt>(<tt>s</tt>) <tt>(By.</tt> ...<tt>)</tt>
   	   * @param using &colon; {@link String}[] Selectors - attribute values used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}
   	   * @param expectedTagName &colon; {@link String} HTML-tag, defaults to "<tt>&#42;</tt>"
   	   * @return {@link ByXp}: Extension of the <a href=https://docs.seleniumhq.org/projects/webdriver/>Selenium</a> {@link By} class.
   	   * 
   	   * @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium&nbsp;loc">Mr. Michael Eder</a>
   	   *
   	   */  
         public static ByXp loc(
      		   final Locator        locator,
      		   final String[]       using,
      		   final String         expectedTagName) {
            	 	
           ByXp O_retval_by_xp;
      	   
      	   O_retval_by_xp = loc(
      			   locator,
      			   LocatorVariant.regular,
      			   using,
      			   expectedTagName,
      			   XpathGenerators.IGNORED_IDX,
      			   XpathGenerators.DEFAULT_PREFIX,
      			   (DomOffset[]) null);
      	    O_retval_by_xp.O_loc_sel_xp.M_ctor = ConstructorUtils.getAccessibleConstructor(
    			   Loc.class, 
    			   AT_e1_as_s1);
      	   return O_retval_by_xp;
            }
         
         /**
      	   * 
      	   * @param locator &colon; {@link Locator} Attribute name used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}<br>
      	   *    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <tt>findElement</tt>(<tt>s</tt>) <tt>(By.</tt> ...<tt>)</tt>
      	   * @param variant &colon; {@link LocatorVariant} defaults to <i>regular</i>
      	   * @param using &colon; {@link String} Selector - attribute value used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}
      	   * @param expectedTagName &colon; {@link String} HTML-tag, defaults to "<tt>&#42;</tt>"
      	   * @return {@link ByXp}: Extension of the <a href=https://docs.seleniumhq.org/projects/webdriver/>Selenium</a> {@link By} class.
      	   * 
      	   * @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium&nbsp;loc">Mr. Michael Eder</a>
      	   *
      	   */     
             public static ByXp loc(
          		   final Locator        locator,
          		   final LocatorVariant variant,
          		   final String         using,
          		   final String         expectedTagName) {
          	   
          	   ByXp O_retval_by_xp;
          	   
          	   O_retval_by_xp = loc(
          			   locator,
          			   variant,
          			   using,
          			   expectedTagName,
          			   XpathGenerators.IGNORED_IDX,
          			   XpathGenerators.DEFAULT_PREFIX,
          			   (DomOffset[]) null);
          	   O_retval_by_xp.O_loc_sel_xp.M_ctor = ConstructorUtils.getAccessibleConstructor(
    			   Loc.class, 
    			   AT_e2_s2);
          	   return O_retval_by_xp;
             }
     
         /**
      	   * 
      	   * @param locator &colon; {@link Locator} Attribute name used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}<br>
      	   *    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <tt>findElement</tt>(<tt>s</tt>) <tt>(By.</tt> ...<tt>)</tt>
      	   * @param variant &colon; {@link LocatorVariant} defaults to <i>regular</i>
      	   * @param using &colon; {@link String}[] Selectors - attribute values used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}
      	   * @param expectedTagName &colon; {@link String} HTML-tag, defaults to "<tt>&#42;</tt>"
      	   * @return {@link ByXp}: Extension of the <a href=https://docs.seleniumhq.org/projects/webdriver/>Selenium</a> {@link By} class.
      	   * 
      	   * @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium&nbsp;loc">Mr. Michael Eder</a>
      	   *
      	   */     
             public static ByXp loc(
          		   final Locator        locator,
          		   final LocatorVariant variant,
          		   final String[]       using,
          		   final String         expectedTagName) {
          	   
          	   ByXp O_retval_by_xp;
          	   
          	   O_retval_by_xp = loc(
          			   locator,
          			   variant,
          			   using,
          			   expectedTagName,
          			   XpathGenerators.IGNORED_IDX,
          			   XpathGenerators.DEFAULT_PREFIX,
          			   (DomOffset[]) null);
          	   O_retval_by_xp.O_loc_sel_xp.M_ctor = ConstructorUtils.getAccessibleConstructor(
    			   Loc.class, 
    			   AT_e2_as_s1);
          	   return O_retval_by_xp;
             }
             
     /**
   	   * 
   	   * @param locator &colon; {@link Locator} Attribute name used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}<br>
   	   *    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <tt>findElement</tt>(<tt>s</tt>) <tt>(By.</tt> ...<tt>)</tt>
   	   * @param using &colon; {@link String} Selector - attribute value used to search for one or more {@link RemoteWebElement RemoteWebElement(s)} 
   	   * @param expectedTagName &colon; {@link String} HTML-tag, defaults to "<tt>&#42;</tt>"
   	   * @param index &colon; <tt>int</tt> <ul>
   	   * <li>&ge; 0 for all regular indices</li> 
   	   * <li>{@link XpathGenerators#IGNORED_IDX ignored index}</li>
   	   * <li>{@link XpathGenerators#ALL_IDX all index}</li>
   	   * <li>{@link XpathGenerators#LAST_IDX last index}</li>
   	   * </ul>
   	   * @return {@link ByXp}: Extension of the <a href=https://docs.seleniumhq.org/projects/webdriver/>Selenium</a> {@link By} class.
   	   * 
   	   * @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium&nbsp;loc">Mr. Michael Eder</a>
   	   *
   	   */                   
       public static ByXp loc(
       		   final Locator        locator,
       		   final String         using,
       		   final String         expectedTagName,
       		   final int            index) {
       	   
       	   ByXp O_retval_by_xp;
       	   
       	   O_retval_by_xp = loc(
       			   locator,
       			   LocatorVariant.regular,
       			   using,
       			   expectedTagName,
       			   index,
       			   XpathGenerators.DEFAULT_PREFIX,
       			   (DomOffset[]) null);
       	   O_retval_by_xp.O_loc_sel_xp.M_ctor = ConstructorUtils.getAccessibleConstructor(
    			   Loc.class, 
    			   AT_e1_s2_i1);
       	   return O_retval_by_xp;
          }

       
     /**
   	   * 
   	   * @param locator &colon; {@link Locator} Attribute name used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}<br>
   	   *    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <tt>findElement</tt>(<tt>s</tt>) <tt>(By.</tt> ...<tt>)</tt>
   	   * @param using &colon; {@link String}[] Selectors - attribute values used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}
   	   * @param expectedTagName &colon; {@link String} HTML-tag, defaults to "<tt>&#42;</tt>"
   	   * @param index &colon; <tt>int</tt> <ul>
   	   * <li>&ge; 0 for all regular indices</li> 
   	   * <li>{@link XpathGenerators#IGNORED_IDX ignored index}</li>
   	   * <li>{@link XpathGenerators#ALL_IDX all index}</li>
   	   * <li>{@link XpathGenerators#LAST_IDX last index}</li>
   	   * </ul>
   	   * @return {@link ByXp}: Extension of the <a href=https://docs.seleniumhq.org/projects/webdriver/>Selenium</a> {@link By} class.
   	   * 
   	   * @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium&nbsp;loc">Mr. Michael Eder</a>
   	   *
   	   */         
       public static ByXp loc(
       		   final Locator        locator,
       		   final String[]       using,
       		   final String         expectedTagName,
       		   final int            index) {
    	
         ByXp O_retval_by_xp;
       	   
       	   O_retval_by_xp = loc(
       			   locator,
       			   LocatorVariant.regular,
       			   using,
       			   expectedTagName,
       			   index,
       			   XpathGenerators.DEFAULT_PREFIX,
       			   (DomOffset[]) null);
       	   O_retval_by_xp.O_loc_sel_xp.M_ctor = ConstructorUtils.getAccessibleConstructor(
    			   Loc.class, 
    			   AT_e1_as_s1_i1);
       	   return O_retval_by_xp;	      
       }
       
    	  /**
       	   * 
       	   * @param locator &colon; {@link Locator} Attribute name used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}<br>
       	   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <tt>findElement</tt>(<tt>s</tt>) <tt>(By.</tt> ...<tt>)</tt>
       	   * @param variant &colon; {@link LocatorVariant} defaults to <i>regular</i>
       	   * @param using &colon; {@link String} Selector - attribute value used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}<br> 
       	   * @param expectedTagName &colon; {@link String} HTML-tag, defaults to "<tt>&#42;</tt>"
       	   * @param index &colon; <tt>int</tt> <ul>
       	   * <li>&ge; 0 for all regular indices</li> 
       	   * <li>{@link XpathGenerators#IGNORED_IDX ignored index}</li>
       	   * <li>{@link XpathGenerators#ALL_IDX all index}</li>
       	   * <li>{@link XpathGenerators#LAST_IDX last index}</li>
       	   * </ul>
       	   * @return {@link ByXp}: Extension of the <a href=https://docs.seleniumhq.org/projects/webdriver/>Selenium</a> {@link By} class.
       	   * 
       	   * @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium&nbsp;loc">Mr. Michael Eder</a>
       	   *
       	   */     
           public static ByXp loc(
        		   final Locator        locator,
        		   final LocatorVariant variant,
        		   final String         using,
        		   final String         expectedTagName,
        		   final int            index) {
        	   
        	   ByXp O_retval_by_xp;
        	   
        	   O_retval_by_xp = loc(
        			   locator,
        			   variant,
        			   using,
        			   expectedTagName,
        			   index,
        			   XpathGenerators.DEFAULT_PREFIX,
        			   (DomOffset[]) null);
        	   O_retval_by_xp.O_loc_sel_xp.M_ctor = ConstructorUtils.getAccessibleConstructor(
    			   Loc.class, 
    			   AT_e2_s2_i1);
        	   return O_retval_by_xp;
           }
     
 
 /**
   * 
   * @param locator &colon; {@link Locator} Attribute name used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}<br>
   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <tt>findElement</tt>(<tt>s</tt>) <tt>(By.</tt> ...<tt>)</tt>
   * @param variant &colon; {@link LocatorVariant} defaults to <i>regular</i>
   * @param using &colon; {@link String}[] Selectors - attribute values used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}
   * @param expectedTagName &colon; {@link String} HTML-tag, defaults to "<tt>&#42;</tt>"
   * @param index &colon; <tt>int</tt> <ul>
   * <li>&ge; 0 for all regular indices</li> 
   * <li>{@link XpathGenerators#IGNORED_IDX ignored index}</li>
   * <li>{@link XpathGenerators#ALL_IDX all index}</li>
   * <li>{@link XpathGenerators#LAST_IDX last index}</li>
   * </ul>
   * @return {@link ByXp}: Extension of the <a href=https://docs.seleniumhq.org/projects/webdriver/>Selenium</a> {@link By} class.
   * 
   * @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium&nbsp;loc">Mr. Michael Eder</a>
   *
   */     
      public static ByXp loc(
    		   final Locator        locator,
    		   final LocatorVariant variant,
    		   final String[]         using,
    		   final String         expectedTagName,
    		   final int            index) {
        	 
          ByXp O_retval_by_xp;
    	   
    	  O_retval_by_xp = loc(
    			   locator,
    			   variant,
    			   using,
    			   expectedTagName,
    			   index,
    			   XpathGenerators.DEFAULT_PREFIX,
    			   (DomOffset[]) null);
    	   O_retval_by_xp.O_loc_sel_xp.M_ctor = ConstructorUtils.getAccessibleConstructor(
			   Loc.class, 
			   AT_e2_as_s1_i1);
    	   return O_retval_by_xp;
         }
           
     	  /**
       	   * 
       	   * @param locator &colon; {@link Locator} Attribute name used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}<br>
       	   *    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <tt>findElement</tt>(<tt>s</tt>) <tt>(By.</tt> ...<tt>)</tt>
       	   * @param using &colon; {@link String} selector
       	   * @param expectedTagName &colon; {@link String} HTML-tag, defaults to "<tt>&#42;</tt>"
       	   * @param index &colon; <tt>int</tt> <ul>
       	   * <li>&ge; 0 for all regular indices</li> 
       	   * <li>{@link XpathGenerators#IGNORED_IDX ignored index}</li>
       	   * <li>{@link XpathGenerators#ALL_IDX all index}</li>
       	   * <li>{@link XpathGenerators#LAST_IDX last index}</li>
       	   * </ul>
       	   * @param prefix &colon; {@link String} Prefix preceding the HTML-tag, defaults to "<tt>.//</tt>"
       	   * @return {@link ByXp}: Extension of the <a href=https://docs.seleniumhq.org/projects/webdriver/>Selenium</a> {@link By} class.
       	   * 
       	   * @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium&nbsp;loc">Mr. Michael Eder</a>
       	   *
       	   */            
           public static ByXp loc(
        		   final Locator        locator,
        		   final String         using,
        		   final String         expectedTagName,
        		   final int            index,
        		   final String         prefix) {
        	   
        	   ByXp O_retval_by_xp;
        	   
        	   O_retval_by_xp = loc(
        			   locator,
        			   LocatorVariant.regular,
        			   using,
        			   expectedTagName,
        			   index,
        			   prefix,
        			   (DomOffset[]) null);
        	   O_retval_by_xp.O_loc_sel_xp.M_ctor = ConstructorUtils.getAccessibleConstructor(
    			   Loc.class, 
    			   AT_e1_s2_i1_s1);
        	   
        	   return O_retval_by_xp;
           }  
          
         /**
       	   * 
       	   * @param locator &colon; {@link Locator} Attribute name used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}<br>
       	   *    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <tt>findElement</tt>(<tt>s</tt>) <tt>(By.</tt> ...<tt>)</tt>
       	   * @param using &colon; {@link String}[] Selectors - attribute values used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}
       	   * @param expectedTagName &colon; {@link String} HTML-tag, defaults to "<tt>&#42;</tt>"
       	   * @param index &colon; <tt>int</tt> <ul>
       	   * <li>&ge; 0 for all regular indices</li> 
       	   * <li>{@link XpathGenerators#IGNORED_IDX ignored index}</li>
       	   * <li>{@link XpathGenerators#ALL_IDX all index}</li>
       	   * <li>{@link XpathGenerators#LAST_IDX last index}</li>
       	   * </ul>
       	   * @param prefix &colon; {@link String} Prefix preceding the HTML-tag, defaults to "<tt>.//</tt>"
       	   * @return {@link ByXp}: Extension of the <a href=https://docs.seleniumhq.org/projects/webdriver/>Selenium</a> {@link By} class.
       	   * 
       	   * @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium&nbsp;loc">Mr. Michael Eder</a>
       	   *
       	   */            
           public static ByXp loc(
        		   final Locator        locator,
        		   final String[]       using,
        		   final String         expectedTagName,
        		   final int            index,
        		   final String         prefix) {
        	   return null;
        	   
           }
           
      	  /**
       	   * 
       	   * @param locator &colon; {@link Locator} Attribute name used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}<br>
       	   *    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <tt>findElement</tt>(<tt>s</tt>) <tt>(By.</tt> ...<tt>)</tt>
       	   * @param variant &colon; {@link LocatorVariant} defaults to <i>regular</i>
       	   * @param using &colon; {@link String} Selector - attribute value used to search for one or more {@link RemoteWebElement RemoteWebElement(s)} 
       	   * @param expectedTagName &colon; {@link String} HTML-tag, defaults to "<tt>&#42;</tt>"
       	   * @param index &colon; <tt>int</tt> <ul>
       	   * <li>&ge; 0 for all regular indices</li> 
       	   * <li>{@link XpathGenerators#IGNORED_IDX ignored index}</li>
       	   * <li>{@link XpathGenerators#ALL_IDX all index}</li>
       	   * <li>{@link XpathGenerators#LAST_IDX last index}</li>
       	   * </ul>
       	   * @param prefix &colon; {@link String} Prefix preceding the HTML-tag, defaults to "<tt>.//</tt>"
       	   * @return {@link ByXp}: Extension of the <a href=https://docs.seleniumhq.org/projects/webdriver/>Selenium</a> {@link By} class.
       	   * 
       	   * @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium&nbsp;loc">Mr. Michael Eder</a>
       	   *
       	   */     
            public static ByXp loc(
         		   final Locator        locator,
         		   final LocatorVariant variant,
         		   final String         using,
         		   final String         expectedTagName,
         		   final int            index,
         		   final String         prefix) {
         	   
         	   ByXp O_retval_by_xp;
         	   
         	   O_retval_by_xp = loc(
         			   locator,
         			   variant,
         			   using,
         			   expectedTagName,
         			   index,
         			   prefix,
         			   (DomOffset[]) null);
         	   O_retval_by_xp.O_loc_sel_xp.M_ctor = ConstructorUtils.getAccessibleConstructor(
    			   Loc.class, 
    			   AT_e2_s2_i1_s1);
         	   return O_retval_by_xp;
            }
 
         /** 
       	   * @param locator &colon; {@link Locator} Attribute name used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}<br>
       	   *    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <tt>findElement</tt>(<tt>s</tt>) <tt>(By.</tt> ...<tt>)</tt>
       	   * @param variant &colon; {@link LocatorVariant} defaults to <i>regular</i>
       	   * @param using &colon; {@link String}[] Selectors - attribute values used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}
       	   * @param expectedTagName &colon; {@link String} HTML-tag, defaults to "<tt>&#42;</tt>"
       	   * @param index &colon; <tt>int</tt> <ul>
       	   * <li>&ge; 0 for all regular indices</li> 
       	   * <li>{@link XpathGenerators#IGNORED_IDX ignored index}</li>
       	   * <li>{@link XpathGenerators#ALL_IDX all index}</li>
       	   * <li>{@link XpathGenerators#LAST_IDX last index}</li>
       	   * </ul>
       	   * @param prefix &colon; {@link String} Prefix preceding the HTML-tag, defaults to "<tt>.//</tt>"
       	   * @return {@link ByXp}: Extension of the <a href=https://docs.seleniumhq.org/projects/webdriver/>Selenium</a> {@link By} class.
       	   * 
       	   * @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium&nbsp;loc">Mr. Michael Eder</a>
       	   *
       	   */                
           public static ByXp loc(
         		   final Locator        locator,
         		   final LocatorVariant variant,
         		   final String[]       using,
         		   final String         expectedTagName,
         		   final int            index,
         		   final String         prefix) {
        	   ByXp O_retval_by_xp;
         	   
         	   O_retval_by_xp = loc(
         			   locator,
         			   variant,
         			   using,
         			   expectedTagName,
         			   index,
         			   prefix,
         			   (DomOffset[]) null);
         	   O_retval_by_xp.O_loc_sel_xp.M_ctor = ConstructorUtils.getAccessibleConstructor(
    			   Loc.class, 
    			   AT_e2_as_s1_i1_s1);
         	   return O_retval_by_xp;
             }
             
      	  /**
    	   * 
    	   * @param locator &colon; {@link Locator} Attribute name used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}<br>
    	   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <tt>findElement</tt>(<tt>s</tt>) <tt>(By.</tt> ...<tt>)</tt>
           * @param using &colon; {@link String} Selector - attribute value used to search for one or more {@link RemoteWebElement RemoteWebElement(s)} 
    	   * @param expectedTagName &colon; {@link String} HTML-tag, defaults to "<tt>&#42;</tt>"
    	   * @param index &colon; <tt>int</tt> <ul>
    	   * <li>&ge; 0 for all regular indices</li> 
    	   * <li>{@link XpathGenerators#IGNORED_IDX ignored index}</li>
    	   * <li>{@link XpathGenerators#ALL_IDX all index}</li>
    	   * <li>{@link XpathGenerators#LAST_IDX last index}</li>
    	   * </ul>
    	   * @param prefix &colon; {@link String} Prefix preceding the HTML-tag, defaults to "<tt>.//</tt>"
    	   * @param domOffsets &colon; Array of <tt>int</tt>, number of prev. siblings for this element and all its parents.<br>
    	   *      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    	   *      For second parameter <i>contextNode</i> of method <tt>document.evaluate(...)</tt>
    	   * 
    	   * @return {@link ByXp}: Extension of the <a href=https://docs.seleniumhq.org/projects/webdriver/>Selenium</a> {@link By} class.
    	   * 
    	   * @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium&nbsp;loc">Mr. Michael Eder</a>
    	   *
    	   */     
             public static ByXp loc(
          		   final Locator        locator,
          		   final String         using,
          		   final String         expectedTagName,
          		   final int            index,
          		   final String         prefix,
          		   final int      domOffsets[]) {
          	   
          	   DomOffset AO_dom_offsets[];
          	   ByXp O_retval_by_xp;
          	   
          	   AO_dom_offsets = DomRootElements.FAO_create_DOM_offsets(domOffsets);
          	   O_retval_by_xp = loc(
          			   locator,
          			   LocatorVariant.regular,
          			   using,
          			   expectedTagName,
          			   index,
          			   prefix,
          			   AO_dom_offsets);
          	   O_retval_by_xp.O_loc_sel_xp.M_ctor = ConstructorUtils.getAccessibleConstructor(
    			   Loc.class, 
    			   AT_e1_s2_i1_s1_ao);
          	   return O_retval_by_xp;
             }
           
     	  /**
    	   * 
    	   * @param locator &colon; {@link Locator} Attribute name used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}<br>
    	   *     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <tt>findElement</tt>(<tt>s</tt>) <tt>(By.</tt> ...<tt>)</tt>
           * @param using &colon; {@link String}[] Selectors - attribute values used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}
    	   * @param expectedTagName &colon; {@link String} HTML-tag, defaults to "<tt>&#42;</tt>"
    	   * @param index &colon; <tt>int</tt> <ul>
    	   * <li>&ge; 0 for all regular indices</li> 
    	   * <li>{@link XpathGenerators#IGNORED_IDX ignored index}</li>
    	   * <li>{@link XpathGenerators#ALL_IDX all index}</li>
    	   * <li>{@link XpathGenerators#LAST_IDX last index}</li>
    	   * </ul>
    	   * @param prefix &colon; {@link String} Prefix preceding the HTML-tag, defaults to "<tt>.//</tt>"
    	   * @param domOffsets &colon; Array of <tt>int</tt>, number of prev. siblings for this element and all its parents.<br>
    	   *      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    	   *      For second parameter <i>contextNode</i> of method <tt>document.evaluate(...)</tt>
    	   * 
    	   * @return {@link ByXp}: Extension of the <a href=https://docs.seleniumhq.org/projects/webdriver/>Selenium</a> {@link By} class.
    	   * 
    	   * @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium&nbsp;loc">Mr. Michael Eder</a>
    	   *
    	   */                  
             public static ByXp loc(
          		   final Locator        locator,
          		   final String[]       using,
          		   final String         expectedTagName,
          		   final int            index,
          		   final String         prefix,
          		   final int      domOffsets[]) {
                	
            	   DomOffset AO_dom_offsets[];
          	   ByXp O_retval_by_xp;
          	   
          	   AO_dom_offsets = DomRootElements.FAO_create_DOM_offsets(domOffsets);
          	   O_retval_by_xp = loc(
          			   locator,
          			   LocatorVariant.regular,
          			   using,
          			   expectedTagName,
          			   index,
          			   prefix,
          			   AO_dom_offsets);
          	   O_retval_by_xp.O_loc_sel_xp.M_ctor = ConstructorUtils.getAccessibleConstructor(
    			   Loc.class, 
    			   AT_e1_as_s1_i1_s1_ao);
          	   return O_retval_by_xp;
               }  
             
       	  /**
       	   *        	   
    	   * @param locator &colon; {@link Locator} Attribute name used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}<br>
    	   *    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; findElementBy ...
    	   *  @param variant &colon; {@link LocatorVariant} defaults to <i>regular</i>
    	   *  @param using &colon; {@link String} Selector - attribute value used to search for one or more {@link RemoteWebElement RemoteWebElement(s)} 
    	   * @param expectedTagName &colon; {@link String} HTML-tag, defaults to "<tt>&#42;</tt>"
    	   * @param index &colon; <tt>int</tt> <ul>
    	   * <li>&ge; 0 for all regular indices</li> 
    	   * <li>{@link XpathGenerators#IGNORED_IDX ignored index}</li>
    	   * <li>{@link XpathGenerators#ALL_IDX all index}</li>
    	   * <li>{@link XpathGenerators#LAST_IDX last index}</li>
    	   * </ul>
    	   * @param prefix &colon; {@link String} Prefix preceding the HTML-tag, defaults to "<tt>.//</tt>".
    	   * @param domOffsets &colon; Array of <tt>int</tt>, number of prev. siblings for this element and all its parents.<br>
    	   * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    	   *      For second parameter <i>contextNode</i> of method <tt>document.evaluate(...)</tt>
    	   * @return {@link ByXp}: Extension of the <a href=https://docs.seleniumhq.org/projects/webdriver/>Selenium</a> {@link By} class.
    	   * 
    	   * @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium&nbsp;loc">Mr. Michael Eder</a>
    	   *
    	   */     
              public static ByXp loc(
           		   final Locator        locator,
           		   final LocatorVariant variant,
           		   final String         using,
           		   final String         expectedTagName,
           		   final int            index,
           		   final String         prefix,
           	       final int      domOffsets[]) {
           	   
           	   DomOffset AO_dom_offsets[];
           	   ByXp O_retval_by_xp;
           	   
           	   AO_dom_offsets = DomRootElements.FAO_create_DOM_offsets(domOffsets);
           	   O_retval_by_xp = loc(
           			   locator,
           			   variant,
           			   using,
           			   expectedTagName,
           			   index,
           			   prefix,
           			   AO_dom_offsets);
           	   O_retval_by_xp.O_loc_sel_xp.M_ctor = ConstructorUtils.getAccessibleConstructor(
    			   Loc.class, 
    			   AT_e2_s2_i1_s1_ai);
           	   return O_retval_by_xp;
              }
 
             /**
       	   *        	   
    	   * @param locator &colon; {@link Locator} Attribute name used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}<br>
    	   *    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; findElementBy ...
    	   *  @param variant &colon; {@link LocatorVariant} defaults to <i>regular</i>
    	   * @param using &colon; {@link String}[] Selectors - attribute values used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}
    	   * @param expectedTagName &colon; {@link String} HTML-tag, defaults to "<tt>&#42;</tt>"
    	   * @param index &colon; <tt>int</tt> <ul>
    	   * <li>&ge; 0 for all regular indices</li> 
    	   * <li>{@link XpathGenerators#IGNORED_IDX ignored index}</li>
    	   * <li>{@link XpathGenerators#ALL_IDX all index}</li>
    	   * <li>{@link XpathGenerators#LAST_IDX last index}</li>
    	   * </ul>
    	   * @param prefix &colon; {@link String} Prefix preceding the HTML-tag, defaults to "<tt>.//</tt>".
    	   * @param domOffsets &colon; Array of <tt>int</tt>, number of prev. siblings for this element and all its parents.<br>
    	   * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    	   *      For second parameter <i>contextNode</i> of method <tt>document.evaluate(...)</tt>
    	   * @return {@link ByXp}: Extension of the <a href=https://docs.seleniumhq.org/projects/webdriver/>Selenium</a> {@link By} class.
    	   * 
    	   * @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium&nbsp;loc">Mr. Michael Eder</a>
    	   *
    	   */        
           public static ByXp loc(
           		   final Locator        locator,
           		   final LocatorVariant variant,
           		   final String[]       using,
           		   final String         expectedTagName,
           		   final int            index,
           		   final String         prefix,
           	       final int      domOffsets[]) {
        	   
        	   DomOffset AO_dom_offsets[];
           	   ByXp O_retval_by_xp;
        	   
        	   AO_dom_offsets = DomRootElements.FAO_create_DOM_offsets(domOffsets);
           	   O_retval_by_xp = loc(
           			   locator,
           			   variant,
           			   using,
           			   expectedTagName,
           			   index,
           			   prefix,
           			   AO_dom_offsets);
           	   O_retval_by_xp.O_loc_sel_xp.M_ctor = ConstructorUtils.getAccessibleConstructor(
    			   Loc.class, 
    			   AT_e2_as_s1_i1_s1_ai);
           	   return O_retval_by_xp;
           }
              
              /**
         	   * 
         	   * @param locator &colon; {@link Locator} Attribute name used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}<br>
         	   *    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; findElementBy ...
         	   * @param using &colon; {@link String} Selector - attribute value used to search for one or more {@link RemoteWebElement RemoteWebElement(s)} 
         	   * @param expectedTagName &colon; {@link String} HTML-tag, defaults to "<tt>&#42;</tt>"
         	   * @param index &colon; <tt>int</tt> <ul>
         	   * <li>&ge; 0 for all regular indices</li>
         	   * <li>{@link XpathGenerators#IGNORED_IDX ignored index}</li>
         	   * <li>{@link XpathGenerators#ALL_IDX all index}</li>
         	   * <li>{@link XpathGenerators#LAST_IDX last index}</li>
         	   * </ul>
         	   * @param prefix &colon; {@link String} Prefix preceding the HTML-tag, defaults to "<tt>.//</tt>"
         	   * @param domOffsets &colon; Array of {@link DomOffset DomOffsets}, tags and number of prev. siblings for this element and all its parents.<br>
         	   * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    	       *     For second parameter <i>contextNode</i> of method <tt>document.evaluate(...)</tt>
         	   * @return {@link ByXp}: Extension of the <a href=https://docs.seleniumhq.org/projects/webdriver/>Selenium</a> {@link By} class.
         	   * 
               * @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium&nbsp;loc">Mr. Michael Eder</a>
         	   *
         	   */	  
               public static ByXp loc(
            		   final Locator        locator,
            		   final String         using,
            		   final String         expectedTagName,
            		   final int            index,
            		   final String         prefix,
            	       final DomOffset      domOffsets[]) {
            	   
            	   ByXp O_retval_by_xp;
            	   
            	   O_retval_by_xp = loc(
            			   locator,
            			   LocatorVariant.regular,
            			   using,
            			   expectedTagName,
            			   index,
            			   prefix,
            			   domOffsets);
            	   O_retval_by_xp.O_loc_sel_xp.M_ctor = ConstructorUtils.getAccessibleConstructor(
    			        Loc.class, 
    			        AT_e1_s2_i1_s1_ao);
            	   return O_retval_by_xp;
               }  
               

                            /**
 	   * 
 	   * @param locator &colon; {@link Locator} Attribute name used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}<br>
 	   *    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; findElementBy ...
 	   * @param using &colon; {@link String}[] Selectors - attribute values used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}
 	   * @param expectedTagName &colon; {@link String} HTML-tag, defaults to "<tt>&#42;</tt>"
 	   * @param index &colon; <tt>int</tt> <ul>
 	   * <li>&ge; 0 for all regular indices</li>
 	   * <li>{@link XpathGenerators#IGNORED_IDX ignored index}</li>
 	   * <li>{@link XpathGenerators#ALL_IDX all index}</li>
 	   * <li>{@link XpathGenerators#LAST_IDX last index}</li>
 	   * </ul>
 	   * @param prefix &colon; {@link String} Prefix preceding the HTML-tag, defaults to "<tt>.//</tt>"
 	   * @param domOffsets &colon; Array of {@link DomOffset DomOffsets}, tags and number of prev. siblings for this element and all its parents.<br>
 	   * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       *     For second parameter <i>contextNode</i> of method <tt>document.evaluate(...)</tt>
 	   * @return {@link ByXp}: Extension of the <a href=https://docs.seleniumhq.org/projects/webdriver/>Selenium</a> {@link By} class.
 	   * 
       * @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium&nbsp;loc">Mr. Michael Eder</a>
 	   *
 	   */	  
      public static ByXp loc(
            		   final Locator        locator,
            		   final String[]       using,
            		   final String         expectedTagName,
            		   final int            index,
            		   final String         prefix,
            	       final DomOffset      domOffsets[]) {
            	   
            	   ByXp O_retval_by_xp;
            	   
            	   O_retval_by_xp = loc(
            			   locator,
            			   LocatorVariant.regular,
            			   using,
            			   expectedTagName,
            			   index,
            			   prefix,
            			   domOffsets);
            	   O_retval_by_xp.O_loc_sel_xp.M_ctor = ConstructorUtils.getAccessibleConstructor(
    			        Loc.class, 
    			        AT_e1_as_s1_i1_s1_ao);
            	   return O_retval_by_xp;
               }                        
     
     /**
 	   * 
 	   * @param locator &colon; {@link Locator} Attribute name used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}<br>
 	   *    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; findElementBy ...
 	   * @param variant &colon; {@link LocatorVariant} defaults to <i>regular</i>
 	   * @param using &colon; {@link String} Selector - attribute value used to search for one or more {@link RemoteWebElement RemoteWebElement(s)} 
 	   * @param expectedTagName &colon; {@link String} HTML-tag, defaults to "<tt>&#42;</tt>"
 	   * @param index &colon; <tt>int</tt> <ul>
 	   * <li>&ge; 0 for all regular indices</li>
 	   * <li>{@link XpathGenerators#IGNORED_IDX ignored index}</li>
 	   * <li>{@link XpathGenerators#ALL_IDX all index}</li>
 	   * <li>{@link XpathGenerators#LAST_IDX last index}</li>
 	   * </ul>
 	   * @param prefix &colon; {@link String} Prefix preceding the HTML-tag, defaults to "<tt>.//</tt>"
 	   * @param domOffsets &colon; Array of {@link DomOffset DomOffsets}, tags and number of prev. siblings for this element and all its parents.<br>
 	   * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       *     For second parameter <i>contextNode</i> of method <tt>document.evaluate(...)</tt>
 	   * @return {@link ByXp}: Extension of the <a href=https://docs.seleniumhq.org/projects/webdriver/>Selenium</a> {@link By} class.
 	   * 
       * @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium&nbsp;loc">Mr. Michael Eder</a>
 	   *
 	   */	   
       public static ByXp loc(
    		   final Locator        locator,
    		   final LocatorVariant variant,
    		   final String         using,
    		   final String         expectedTagName,
    		   final int            index,
    		   final String         prefix,
    	       final DomOffset      domOffsets[])     {
        	
        	ByXp O_retval_by_xp;
        	
        	O_retval_by_xp = loc_poly(
					locator,        
					variant,        
					using,          
					expectedTagName,
					index,          
					prefix,         
					domOffsets); 	
  
        	return O_retval_by_xp;
        }
        
        /**
     	   * 
     	   * @param locator &colon; {@link Locator} Attribute name used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}<br>
     	   *    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; findElementBy ...
     	   * @param using &colon; {@link String} selector
     	   * @param expectedTagName &colon; {@link String} HTML-tag, defaults to "<tt>&#42;</tt>"
     	   * @param index &colon; <tt>int</tt> <ul>
     	   * <li>&ge; 0 for all regular indices</li>
     	   * <li>{@link XpathGenerators#IGNORED_IDX ignored index}</li>
     	   * <li>{@link XpathGenerators#ALL_IDX all index}</li>
     	   * <li>{@link XpathGenerators#LAST_IDX last index}</li>
     	   * </ul>
     	   * @param prefix &colon; {@link String} Prefix preceding the HTML-tag, defaults to "<tt>.//</tt>"
     	   * @param domOffsets &colon; Array of {@link DomOffset DomOffsets}, tags and number of prev. siblings for this element and all its parents.<br>
     	   * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	       *     For second parameter <i>contextNode</i> of method <tt>document.evaluate(...)</tt>
     	   * @return {@link ByXp}: Extension of the <a href=https://docs.seleniumhq.org/projects/webdriver/>Selenium</a> {@link By} class.
     	   * 
           * @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium&nbsp;loc">Mr. Michael Eder</a>
     	   *
     	   */	          
         public static ByXp loc(
    		   final Locator        locator,
    		   final LocatorVariant variant,
    		   final String         using[],
    		   final String         expectedTagName,
    		   final int            index,
    		   final String         prefix,
    	       final DomOffset      domOffsets[]) {
        	
        	ByXp O_retval_by_xp;
        	
        	O_retval_by_xp = loc_poly(
					locator,        
					variant,        
					using,          
					expectedTagName,
					index,          
					prefix,         
					domOffsets); 	
  
        	return O_retval_by_xp;
        }
         
	  /**
	   * 
	   * @param locator &colon; {@link Locator} Attribute name used to search for one or more {@link RemoteWebElement RemoteWebElement(s)}<br>
	   *    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; findElementBy ...
	   * @param variant &colon; {@link LocatorVariant} defaults to <i>regular</i>
	   * @param using &colon; {@link String} selector
	   * @param expectedTagName &colon; {@link String} HTML-tag, defaults to "<tt>&#42;</tt>"
	   * @param index &colon; <tt>int</tt> <ul>
	   * <li>&ge; 0 for all regular indices</li> 
	   * <li>{@link XpathGenerators#IGNORED_IDX ignored index}</li>
	   * <li>{@link XpathGenerators#ALL_IDX all index}</li>
	   * <li>{@link XpathGenerators#LAST_IDX last index}</li>
	   * </ul>
	   * @param prefix &colon; {@link String} Prefix preceding the HTML-tag, defaults to "<tt>.//</tt>"
	   * @param domOffsets &colon; Array of {@link DomOffset Domoffsets}, tags and number of prev. siblings for this element and all its parents.<br>
	   *    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       *    For second parameter <i>contextNode</i> of method <tt>document.evaluate(...)</tt>
	   * @return {@link ByXp}: Extension of the <a href=https://docs.seleniumhq.org/projects/webdriver/>Selenium</a> {@link By} class.
	   * 
	   * @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium&nbsp;loc">Mr. Michael Eder</a>
	   *
	   */
       protected static ByXp loc_poly(
    		   final Locator        locator,
    		   final LocatorVariant variant,
    		   final Object         using,
    		   final String         expectedTagName,
    		   final int            index,
    		   final String         prefix,
    	       final DomOffset      domOffsets[]) {
    	   
    	   final LocatorRegularity E_min_loc_regularity = XpathGenerators.LocatorRegularity.xpathgen;
    	   final int I_min_regularity                   = E_min_loc_regularity.ordinal();
    	   Exception    E_cause = null;
    	 
    	   Constructor         M_ctor;
    	   Class<?>            T_clazz /*, T_clazz_super */;
    	   NullPointerException     E_np;
    	   AssertionError           E_assert;
    	   ClassCastException       E_cls_cast;
    	   IllegalArgumentException E_ill_arg;
    	   RuntimeException        E_rt;
    
    	   LocatorVariant E_locator_variant;
    	   Object O_res_by_xp;
    	   String  S_msg_1, S_msg_2, S_msg_3, S_tag_name, S_prefix, S_selector, AS_selectors[];
    	   int i1, I_nbr_selectors_f1;
    	    
    	   ByXp O_retval_by_loc = null;
    	   T_clazz              = Loc.class;
    
    	   try {
    	      if (locator == null) {
    		     S_msg_1 = "First argument of type \'" + Locator.class.getSimpleName() + "\' must not be null";
    		     E_cause = new NullPointerException(S_msg_1);
    	         throw E_cause;
    	         }
    	      
    	      LocatorRegularity E_act_loc_regularity;
    	      int I_act_regularity;
    	      
    	      E_act_loc_regularity = locator.E_regularity;
    	      I_act_regularity = E_act_loc_regularity.ordinal();
    	      
    		  if (I_act_regularity < I_min_regularity) {
    			  S_msg_1 = "Actual regularity: \'" + E_act_loc_regularity.name() + "\'(" +  I_act_regularity + 
    				") less than minimum required regularity: \'" + E_min_loc_regularity.name() + "\'"+
    				"(" + I_min_regularity + ").";
    			  E_assert = new AssertionError(S_msg_1);
    			  S_msg_2 = "Implementation restriction: Operation \'" + locator.name() + "\' not (yet) eligible for regular class generation.";
    			  E_cause = new IllegalArgumentException(S_msg_2, E_assert);
    			  throw E_cause;
    		      }
    		   
    		   if (variant == null) {
    			   E_locator_variant = LocatorVariant.regular;
    		       }
    	       else {
    	    	   E_locator_variant = variant;
    	           }
    		   
    		   if (expectedTagName == null) {
    			   S_tag_name = DEFAULT_TAG; 
    		      }
    		   else if (StringUtils.isBlank(expectedTagName)) {
    			   S_msg_1 = "Invalid tag name: \'" + expectedTagName + "\'";
    			   E_cause = new IllegalArgumentException(S_msg_1);
  		    	   throw E_cause;  
    		       }
    		   else {
  		          S_tag_name = expectedTagName;
    		      }
  		       
  		       if (prefix == null) {
  			      S_prefix = DEFAULT_PREFIX; 
  		          }
  		       else if (prefix.equals("")) {
  		    	  S_prefix = prefix;    
  		          }
  		       else if (StringUtils.isBlank(prefix)) {
  			      S_msg_1 = "Invalid prefix: \'" + prefix + "\'";
  			      E_cause = new IllegalArgumentException(S_msg_1);
		          throw E_cause;  
  		          }
  		       else {
		          S_prefix = prefix;
  		          }
		       
		       if (using == null) {
		    	  S_msg_1 = "Argument for selector(s) must not be null"; 
		    	  E_cause = new NullPointerException(S_msg_1); 
		    	  throw E_cause;
		             }
		       if (using instanceof String) {
    			   S_selector = (String)using; 
    			   AS_selectors = new String[1];
    			   AS_selectors[0] = S_selector;
    			   M_ctor = ConstructorUtils.getAccessibleConstructor(T_clazz, AT_e2_s2_i1_s1_ao);
                   }
		       else {
		          AS_selectors = (String[])using;
		          M_ctor = ConstructorUtils.getAccessibleConstructor(T_clazz, AT_e2_as_s1_i1_s1_ao);
		          }
		        
		       I_nbr_selectors_f1 = AS_selectors.length;
		       for (i1 = 0; i1 < I_nbr_selectors_f1; i1++) {
		    	   S_selector = AS_selectors[i1];
	    		   if (StringUtils.isBlank(S_selector)) {
	    			   S_msg_1 = "Invalid selector at index: " + i1 + "\'" + using + "\'";
	    			   E_cause = new IllegalArgumentException(S_msg_1);
	  		    	   throw E_cause;  
	    		       }
		            }
		     
		       if (M_ctor == null) {
		    	   S_msg_1 = "Unable to get new constructor of class \'" + T_clazz.getName() + "\'."; 
		    	   E_np = new NullPointerException(S_msg_1);
		    	   throw E_np;
		           }
		       O_res_by_xp = M_ctor.newInstance(locator, E_locator_variant, using, S_tag_name, index, S_prefix, domOffsets);
		       if (O_res_by_xp == null) {
		    	   S_msg_1 = "Unable to get new instance of class \'" + T_clazz.getName() + "\'";
		    	   E_np = new NullPointerException(S_msg_1);
		    	   throw E_np; 
		           }
		       if (!(O_res_by_xp instanceof Loc)) {
		    	   S_msg_1 = "\'" + O_res_by_xp.getClass().getName() + "\' not a subclass of \'" + T_clazz.getName() + "\'";
		    	   E_cls_cast = new ClassCastException(S_msg_1);
		    	   throw E_cls_cast;
		           }
		       O_retval_by_loc = (Loc)O_res_by_xp;
    	       }
    	    catch (Exception PI_E_cause) {
			    S_msg_3 = "unable to create instance of subclass of \'" + ByXp.class.getName() + "\'.";
			    E_rt = new RuntimeException(S_msg_3, PI_E_cause);
			    throw E_rt;
    	   }
    	   // O_retval_by_loc.O_loc_sel_xp.M_ctor = M_ctor;
    	   return O_retval_by_loc;   
       }         
         
	  
	//----------- E N D   R E G U L A R 
	  
	  
	//---------- I R R E G U L A R  -  S T A R T 
@Deprecated	  
     public static ByXp domOffsets (final int PI_AI_DOM_offset_vector[]) {
		  
		  ByDomOffsets O_retval_by_dom_offsets;
		  
		  O_retval_by_dom_offsets = new ByDomOffsets(PI_AI_DOM_offset_vector);
		  O_retval_by_dom_offsets.O_loc_sel_xp.M_ctor = 
				  ConstructorUtils.getAccessibleConstructor(ByDomOffsets.class, int[].class); 
		  return O_retval_by_dom_offsets;   
	  }
	  
	  /**
	   * Finds elements via the driver's underlying W3 Selector engine. If the browser does not
	   * implement the Selector API, a best effort is made to emulate the API. In this case, we strive
	   * for at least CSS2 support, but offer no guarantees.
	   *
	   * @param selector css expression
	   * @return a By which locates elements by CSS.
	   */
	  
	  @Override
	  public boolean equals(final Object PI_O_other) {
		
	    if (this == PI_O_other) {
	    	return true; 
	        }
	    if (PI_O_other == null)  {
	        return false;
	        }
	    else  {
	    	Class T_this, T_other;
	    	
	    	T_this  = this.getClass();
	    	T_other = PI_O_other.getClass();
	    	if (T_this != T_other) {
	    		return false;
	    	   }
	        }
	 
	     ByXp   by_xp_other;
	     String S_this, S_other;
	     boolean B_retval_equals;
	     
	     by_xp_other = (ByXp) PI_O_other;
         S_this = this.toString();
         S_other = by_xp_other.toString();
         B_retval_equals = S_this.equals(S_other);
	      
	    return B_retval_equals;
	  }
	  
	  
	  public static class Loc extends ByXp {
		  
		  public  Object O_selector;
		  private static final Class<? extends ByXp> T_clazz = Loc.class;
		
		  public Loc(
				  final Locator loc,
				  final String using) {
			  
			  this(loc, LocatorVariant.regular, using);
			  this.O_loc_sel_xp.M_ctor =  ConstructorUtils.getAccessibleConstructor(T_clazz, AT_e1_s1);
			  return;
		  }
		  
		  public Loc(
				  final Locator loc,
				  final LocatorVariant variant,
				  final String using) {
			  
			  LocatorEnums O_loc_enums;
			  
			  this.O_selector  = using;
			  O_loc_enums = new LocatorEnums(loc, variant);
		      this.O_loc_sel_xp = this.FO_get_loc_sel_xp(O_loc_enums);	
		      this.O_loc_sel_xp.S_tag_expected    = DEFAULT_TAG;
		      this.O_loc_sel_xp.I_idx_f0 = IGNORED_IDX;
		      this.O_loc_sel_xp.S_prefix = DEFAULT_PREFIX;
		      this.O_loc_sel_xp.M_ctor   = ConstructorUtils.getAccessibleConstructor(T_clazz, AT_e2_s1);
		      this.O_loc_sel_xp.SBO_using = XpathGenerators.FSBO_get_xpath(
		    		  O_loc_enums,
		    		 (String) this.O_selector);
		      return;
		    }

		  
	 public Loc(
				  final Locator loc,
				  final String using[]) {
		 this(loc, 
			  (loc == Locator.className) ? LocatorVariant.and : LocatorVariant.or, 
			  using);
		 this.O_loc_sel_xp.M_ctor   = ConstructorUtils.getAccessibleConstructor(T_clazz, AT_e1_as);
		 return; 
	 }	  
	
	 
	 public Loc(
				  final Locator loc,
				  final LocatorVariant variant,
				  final String using[]) {
			  
			  LocatorEnums O_loc_enums;
			  
			  this.O_selector  = using;
			  O_loc_enums = new LocatorEnums(loc, variant);
		      this.O_loc_sel_xp = this.FO_get_loc_sel_xp(O_loc_enums);	
		      this.O_loc_sel_xp.S_tag_expected = DEFAULT_TAG;
		      this.O_loc_sel_xp.I_idx_f0 = IGNORED_IDX;
		      this.O_loc_sel_xp.S_prefix = DEFAULT_PREFIX;
		      this.O_loc_sel_xp.M_ctor   = ConstructorUtils.getAccessibleConstructor(T_clazz, AT_e2_as);
		      this.O_loc_sel_xp.SBO_using  = XpathGenerators.FSBO_get_xpath(
		    		  O_loc_enums,
		    		 (String[]) this.O_selector);
		      return;
		    }	  

	 
		  public Loc(
				  final Locator loc,
				  final String using,
				  final String tag) {
			  
			  this(loc,
				   LocatorVariant.regular, 
				   using, tag);
			  this.O_loc_sel_xp.M_ctor =  ConstructorUtils.getAccessibleConstructor(T_clazz, AT_e1_s2);
			  return;
		  }

 		  
		  public Loc(
				  final Locator loc,
				  final LocatorVariant variant,
				  final String using,
				  final String tag) {
			  
			  LocatorEnums O_loc_enums;
			  
			  this.O_selector  = using;
			  O_loc_enums = new LocatorEnums(loc, variant);
		      this.O_loc_sel_xp = this.FO_get_loc_sel_xp(O_loc_enums);	
		      this.O_loc_sel_xp.S_tag_expected = tag;
		      this.O_loc_sel_xp.I_idx_f0  = IGNORED_IDX;
		      this.O_loc_sel_xp.S_prefix = DEFAULT_PREFIX;
		      this.O_loc_sel_xp.M_ctor =  ConstructorUtils.getAccessibleConstructor(T_clazz, AT_e2_s2);
		      this.O_loc_sel_xp.SBO_using = XpathGenerators.FSBO_get_xpath(
		    		  O_loc_enums,
		    		 (String)this.O_selector,
		    		  tag);
		      return;
		    }

		  
		public Loc(
		  final Locator loc,
		  final String using[],
		  final String tag) {
			  
		 this(loc,
				  (loc == Locator.className) ? LocatorVariant.and : LocatorVariant.or,
				  using,
				  tag);

		 this.O_loc_sel_xp.M_ctor =  ConstructorUtils.getAccessibleConstructor(T_clazz, AT_e1_as_s1);
			 return;
		    }
		  
		
  		  public Loc(
		     final Locator        loc,
		     final LocatorVariant variant,
		     final String         using[],
		     final String         tag) {
			  
			  LocatorEnums O_loc_enums;
			  
			  this.O_selector  = using;
			  O_loc_enums = new LocatorEnums(loc, variant);
		      this.O_loc_sel_xp = this.FO_get_loc_sel_xp(O_loc_enums);	
		      this.O_loc_sel_xp.S_tag_expected = tag;
		      this.O_loc_sel_xp.I_idx_f0 = IGNORED_IDX;
		      this.O_loc_sel_xp.S_prefix = DEFAULT_PREFIX;
		      this.O_loc_sel_xp.M_ctor =  ConstructorUtils.getAccessibleConstructor(T_clazz, AT_e2_as_s1);
		      this.O_loc_sel_xp.SBO_using = XpathGenerators.FSBO_get_xpath(
		    		  O_loc_enums,
		    		 (String[])this.O_selector,
		    		  tag);
		      return;
		    }

  		  
		  public Loc(
				  final Locator loc,
				  final String using,
				  final String tag,
				  final int    index) {
	
			  this(loc, LocatorVariant.regular, using, tag, index);
			  this.O_loc_sel_xp.M_ctor =  ConstructorUtils.getAccessibleConstructor(T_clazz, AT_e1_s2_i1);
			  
			  return;
		  }
		  
		  public Loc(
				  final Locator loc,
				  final LocatorVariant variant,
				  final String using,
				  final String tag,
				  final int    index) {
			  
			  LocatorEnums O_loc_enums;
			  
			  this.O_selector  = using;
			  O_loc_enums = new LocatorEnums(loc, variant);
		      this.O_loc_sel_xp = this.FO_get_loc_sel_xp(O_loc_enums);	
		      this.O_loc_sel_xp.S_tag_expected = tag;
		      this.O_loc_sel_xp.I_idx_f0 = index;
		      this.O_loc_sel_xp.S_prefix = DEFAULT_PREFIX;
		      this.O_loc_sel_xp.M_ctor =  ConstructorUtils.getAccessibleConstructor(T_clazz, AT_e2_s2_i1);
		      this.O_loc_sel_xp.SBO_using = XpathGenerators.FSBO_get_xpath(
		    		  O_loc_enums,
		    		  (String)this.O_selector,
		    		  tag,
		    		  index);
		      return;
		    }
		  
          public Loc(
			  final Locator loc,
			  final String using[],
			  final String tag,
			  final int    index) {
	    	  
			    this(loc, 
		           (loc == Locator.className) ? LocatorVariant.and : LocatorVariant.or, 
		           using, tag, index);
			    this.O_loc_sel_xp.M_ctor =  ConstructorUtils.getAccessibleConstructor(T_clazz, AT_e1_as_s1_i1);
			    return;
	      }

		  
		   public Loc(
				  final Locator loc,
				  final LocatorVariant variant,
				  final String using[],
				  final String tag,
				  final int    index) {
			  
			  LocatorEnums O_loc_enums;
			  
			  this.O_selector  = using;
			  O_loc_enums = new LocatorEnums(loc, variant);
		      this.O_loc_sel_xp = this.FO_get_loc_sel_xp(O_loc_enums);	
		      this.O_loc_sel_xp.S_tag_expected = tag;
		      this.O_loc_sel_xp.I_idx_f0 = index;
		      this.O_loc_sel_xp.S_prefix = DEFAULT_PREFIX;
		      this.O_loc_sel_xp.M_ctor =  ConstructorUtils.getAccessibleConstructor(T_clazz, AT_e2_as_s1_i1);
		      this.O_loc_sel_xp.SBO_using = XpathGenerators.FSBO_get_xpath(
		    		  O_loc_enums,
		    		  (String[])this.O_selector,
		    		  tag,
		    		  index);
		      return;
		    }
		   

		  public Loc(
				  final Locator loc,
				  final String using,
				  final String tag,
				  final int    index,
				  final String prefix) {
			  
			  this(loc, LocatorVariant.regular, using, tag, index, prefix);
			  this.O_loc_sel_xp.M_ctor = ConstructorUtils.getAccessibleConstructor(T_clazz, AT_e1_s2_i1_s1);
			  return;
		  }
		  
		  public Loc(
				  final Locator loc,
				  final LocatorVariant variant,
				  final String using,
				  final String tag,
				  final int    index,
				  final String prefix) {
			  
			  LocatorEnums O_loc_enums;
			  
			  this.O_selector  = using;
			  O_loc_enums = new LocatorEnums(loc, variant);
		      this.O_loc_sel_xp = this.FO_get_loc_sel_xp(O_loc_enums);	
		      this.O_loc_sel_xp.S_tag_expected = tag;
		      this.O_loc_sel_xp.I_idx_f0 = index;
		      this.O_loc_sel_xp.S_prefix = prefix;
		      this.O_loc_sel_xp.M_ctor =  ConstructorUtils.getAccessibleConstructor(T_clazz, AT_e2_s2_i1_s1);
		      this.O_loc_sel_xp.SBO_using = XpathGenerators.FSBO_get_xpath(
		    		  O_loc_enums,
		    		  (String)this.O_selector,
		    		  tag,
		    		  index,
		    		  prefix);
		      return;
		    }

		  
		  public Loc(
				  final Locator loc,
				  final String using[],
				  final String tag,
				  final int    index,
				  final String prefix) {
			  
			 this(loc, 
	            (loc == Locator.className) ? LocatorVariant.and : LocatorVariant.or, 
	            using,
	            tag,
	            index,
	            prefix);
		  this.O_loc_sel_xp.M_ctor = ConstructorUtils.getAccessibleConstructor(T_clazz, AT_e1_as_s1_i1_s1);		  
		  return;
		  }
		  
		  public Loc(
				  final Locator        loc,
				  final LocatorVariant variant,
				  final String         using[],
				  final String         tag,
				  final int            index,
				  final String         prefix) {
			  
			  LocatorEnums O_loc_enums;
			  
			  this.O_selector  = using;
			  O_loc_enums = new LocatorEnums(loc, variant);
		      this.O_loc_sel_xp = this.FO_get_loc_sel_xp(O_loc_enums);	
		      this.O_loc_sel_xp.S_tag_expected = tag;
		      this.O_loc_sel_xp.I_idx_f0 = index;
		      this.O_loc_sel_xp.S_prefix = prefix;
		      this.O_loc_sel_xp.M_ctor =  ConstructorUtils.getAccessibleConstructor(T_clazz, AT_e2_as_s1_i1_s1);
		      this.O_loc_sel_xp.SBO_using = XpathGenerators.FSBO_get_xpath(
		    		  O_loc_enums,
		    		  (String[])this.O_selector,
		    		  tag,
		    		  index,
		    		  prefix);
		      return;
		    }

		  
		  public Loc(
				  final Locator loc,
				  final String  using,
				  final String  tag,
				  final int     index,
				  final String  prefix,
				  final int[]   domOffsets) {
			   
			  this(loc, LocatorVariant.regular, using, tag, index, prefix, domOffsets);
			  this.O_loc_sel_xp.M_ctor = ConstructorUtils.getAccessibleConstructor(T_clazz, AT_e1_s2_i1_s1_ai);
			  return;
		  }
		  
		  public Loc(
				  final Locator loc,
				  final LocatorVariant variant,
				  final String using,
				  final String tag,
				  final int    index,
				  final String prefix,
				  final int[] domOffsets) {
			  
			  LocatorEnums O_loc_enums;
			  
			  this.O_selector  = using;
			  O_loc_enums = new LocatorEnums(loc, variant);
		      this.O_loc_sel_xp = this.FO_get_loc_sel_xp(O_loc_enums);	
		      this.O_loc_sel_xp.S_tag_expected = tag;
		      this.O_loc_sel_xp.I_idx_f0 = index;
		      this.O_loc_sel_xp.S_prefix = prefix;
		      this.O_loc_sel_xp.M_ctor =  ConstructorUtils.getAccessibleConstructor(T_clazz, AT_e2_s2_i1_s1_ai);
		      this.O_loc_sel_xp.SBO_using = XpathGenerators.FSBO_get_xpath(
		    		  O_loc_enums,
		    		  (String)this.O_selector,
		    		  tag,
		    		  index,
		    		  prefix,
		    		  domOffsets);
		      return;
		    }
		  
		  public Loc(
				  final Locator loc,
				  final String using,
				  final String tag,
				  final int    index,
				  final String prefix,
				  final DomOffset[] domOffsets) {
			  
			  this(loc, LocatorVariant.regular, using, tag, index, prefix, domOffsets);
			  this.O_loc_sel_xp.M_ctor = ConstructorUtils.getAccessibleConstructor(T_clazz, AT_e1_s2_i1_s1_ao);
			  return;
		  }
		  
		  public Loc(
				  final Locator loc,
				  final LocatorVariant variant,
				  final String using,
				  final String tag,
				  final int    index,
				  final String prefix,
				  final DomOffset[] domOffsets) {
			  
			  LocatorEnums O_loc_enums;
			  
			  this.O_selector  = using;
			  O_loc_enums = new LocatorEnums(loc, variant);
		      this.O_loc_sel_xp = this.FO_get_loc_sel_xp(O_loc_enums);	
		      this.O_loc_sel_xp.S_tag_expected = tag;
		      this.O_loc_sel_xp.I_idx_f0 = index;
		      this.O_loc_sel_xp.S_prefix = prefix;
		      this.O_loc_sel_xp.M_ctor =  ConstructorUtils.getAccessibleConstructor(T_clazz, AT_e2_s2_i1_s1_ao);
		      this.O_loc_sel_xp.SBO_using = XpathGenerators.FSBO_get_xpath(
		    		  O_loc_enums,
		    		  (String)this.O_selector,
		    		  tag,
		    		  index,
		    		  prefix,
		    		  domOffsets);
		      return;
		    }
		  
		   public Loc(
				  final Locator loc,
				  final String[] using,
				  final String tag,
				  final int    index,
				  final String prefix,
				  final DomOffset[] domOffsets) {
			   
			    this(loc, 
                   (loc == Locator.className) ? LocatorVariant.and : LocatorVariant.or, 
                   using,
                   tag,
                   index,
                   prefix,
                   domOffsets);
		      this.O_loc_sel_xp.M_ctor =  ConstructorUtils.getAccessibleConstructor(T_clazz, AT_e1_as_s1_i1_s1_ao);
			  return;
		   }

		   public Loc(
				  final Locator loc,
				  final LocatorVariant variant,
				  final String[] using,
				  final String tag,
				  final int    index,
				  final String prefix,
				  final DomOffset[] domOffsets) {
			  
			  LocatorEnums O_loc_enums;
			  
			  this.O_selector = using;
			  O_loc_enums = new LocatorEnums(loc, variant);
		      this.O_loc_sel_xp = this.FO_get_loc_sel_xp(O_loc_enums);	
		      this.O_loc_sel_xp.S_tag_expected = tag;
		      this.O_loc_sel_xp.I_idx_f0  = index;
		      this.O_loc_sel_xp.S_prefix  = prefix;
		      this.O_loc_sel_xp.M_ctor    = ConstructorUtils.getAccessibleConstructor(T_clazz, AT_e2_as_s1_i1_s1_ao);
		      this.O_loc_sel_xp.SBO_using = XpathGenerators.FSBO_get_xpath(
		    		  O_loc_enums,
		    		  (String[])this.O_selector,
		    		  tag,
		    		  index,
		    		  prefix,
		    		  domOffsets);
		      return;
		    }
		  
		  @Override
		  public List<WebElement> findElements(SearchContext context) {	
			    	
	            List<WebElement> AO_retval_web_elems;
	            Locator E_locator;
	 
	            this.O_loc_sel_xp.I_idx_f0 = XpathGenerators.ALL_IDX;
	            E_locator = this.O_loc_sel_xp.E_locator;
	            if (E_locator == Locator.idOrName) {
	            	NoSuchElementException E_cause;
	            	 List<WebElement> AO_res_web_elems_by_id, AO_res_web_elems_by_name;
	            	 AO_retval_web_elems = new ArrayList<WebElement>();
	            	 int I_nbr_elems_found_f1;
	            	 
	            	 E_cause = null;
	            	 this.O_loc_sel_xp.E_locator = Locator.id;
	            	 try {
	            	    AO_res_web_elems_by_id   = RemoteWebElementXp.FAO_find_elements_by_xpath(this);
	            	    }
	            	 catch (NoSuchElementException PI_E_no_such_elem) {
	            		 E_cause = PI_E_no_such_elem;
	            		 AO_res_web_elems_by_id = new ArrayList<WebElement>(); 
	            	   }
	            	 
	            	 this.O_loc_sel_xp.E_locator = Locator.name;
	            	 try {
	            	    AO_res_web_elems_by_name  = RemoteWebElementXp.FAO_find_elements_by_xpath(this);
	            	    }
	            	 catch (NoSuchElementException PI_E_no_such_elem) {
	            		 E_cause = PI_E_no_such_elem;
	            		 AO_res_web_elems_by_name = new ArrayList<WebElement>(); 
	            	     }
	            	 AO_retval_web_elems.addAll(AO_res_web_elems_by_id);
			         AO_retval_web_elems.addAll(AO_res_web_elems_by_name);
			         I_nbr_elems_found_f1 = AO_retval_web_elems.size();
			         if (I_nbr_elems_found_f1 == 0) {
			        	 if (E_cause != null) {
			        		 throw E_cause;
			        	    }
			             }
	                  }
	            else {
	                AO_retval_web_elems = RemoteWebElementXp.FAO_find_elements_by_xpath(this);
	               }
	            return AO_retval_web_elems;	
			    }

			@Override
			public WebElement findElement(SearchContext context) {
			    
		        Locator E_locator;
			    
		        WebElement  O_retval_web_elem;
		        
		        E_locator = this.O_loc_sel_xp.E_locator;
		        if (E_locator == Locator.idOrName) {
		           boolean B_use_by_name = false;
		           
		           O_retval_web_elem = null;
		           this.O_loc_sel_xp.E_locator = Locator.id;
		           try {
					    O_retval_web_elem = RemoteWebElementXp.FO_find_element_by_xpath(this);
				   } catch (NoSuchElementException PI_no_such_elem) {
					   B_use_by_name = true;
				       }
		           if (O_retval_web_elem == null) {
				      B_use_by_name = true;
				      }
		           if (B_use_by_name) {
		        	  this.O_loc_sel_xp.E_locator = Locator.name;
		        	  O_retval_web_elem = RemoteWebElementXp.FO_find_element_by_xpath(this);
		              }
		           }
		        else {
		        	 O_retval_web_elem = RemoteWebElementXp.FO_find_element_by_xpath(this);
		           }
			    return O_retval_web_elem;
			    }
	  }
	  
	  
	  //------------------------------------------------------------------
	  //  S T A R T - I R R E G U L A R
	  

/**
  * @deprecated use newer class {@link Loc} instead
  * 
  */		  
	  public static class ByDomOffsets extends ByXp implements Serializable {
		  
		  private static final Class<? extends ByXp> T_clazz = ByDomOffsets.class;
		    public DomOffset domOffsets[];
		    
		    public ByDomOffsets (final DomOffset PI_AO_DOM_offset_vector[]) {
		    	this.domOffsets = PI_AO_DOM_offset_vector;
		    	this.O_loc_sel_xp = this.FO_get_loc_sel_xp();	
		    	this.O_loc_sel_xp.M_ctor = ConstructorUtils.getAccessibleConstructor(T_clazz, DomOffset[].class);
		    	this.O_loc_sel_xp.SBO_using = new DomVectorExtendedSelector(".");
		        return;
		        }
		    	    
		    public ByDomOffsets(final int PI_AI_DOM_offset_vector[]) {
		    	if (PI_AI_DOM_offset_vector == null) {
		    		return;
		    	    }
		    
		    	this.domOffsets = DomRootElements.FAO_create_DOM_offsets(PI_AI_DOM_offset_vector);
		    	this.O_loc_sel_xp = this.FO_get_loc_sel_xp();	
		    	this.O_loc_sel_xp.M_ctor = ConstructorUtils.getAccessibleConstructor(T_clazz, int[].class);
		    	this.O_loc_sel_xp.SBO_using = new DomVectorExtendedSelector(".");
		    }
		    
		    @Override
		    public List<WebElement> findElements(SearchContext context) {	
		    	
	            List<WebElement> AO_retval_web_elems;
	            AO_retval_web_elems = RemoteWebElementXp.FAO_find_elements_by_xpath(this);
			    return AO_retval_web_elems;	
		    }
		    
		    @Override
		    public WebElement findElement(SearchContext context) {
		    
	         WebElement    O_retval_web_elem;
		    	
		     O_retval_web_elem = RemoteWebElementXp.FO_find_element_by_xpath(this);
		     return O_retval_web_elem;
		     } 
		    
		    @Override 
		    public String toString() {
		      String S_retval;
				    S_retval =  this.getClass().getSuperclass().getSimpleName() + "." + this.getClass().getSimpleName() + ":" + 
					     Arrays.toString(this.domOffsets);
			        return S_retval;
		    }
	  }
	  // ------------------------ DOM-OFFSET END  
	 
	  //------------------------- TAG-NAME START
	  public static class ByTagName extends ByXp implements Serializable {
	
	    private static final long serialVersionUID = 5341968046120372169L;
	    private static final Class<? extends ByXp> T_clazz = ByTagName.class;
	    
	    private final String S_selector;
	    
	    public ByTagName(final String tagName) {
	      this.S_selector = tagName;
	      this.O_loc_sel_xp = this.FO_get_loc_sel_xp();	
	      this.O_loc_sel_xp.S_tag_expected    = tagName;
	      this.O_loc_sel_xp.I_idx_f0 = IGNORED_IDX;
	      this.O_loc_sel_xp.S_prefix = DEFAULT_PREFIX;
	      this.O_loc_sel_xp.M_ctor   = ConstructorUtils.getAccessibleConstructor(T_clazz, String.class);
	      this.O_loc_sel_xp.SBO_using = XpathGenerators.FS_get_xpath_from_tagname(tagName);
	    }
	    
	    public ByTagName(final String tagName, final int PI_I_idx_f0) {
		      this.S_selector = tagName;
		      this.O_loc_sel_xp = this.FO_get_loc_sel_xp();	
		      this.O_loc_sel_xp.S_tag_expected    = tagName;
		      this.O_loc_sel_xp.I_idx_f0 = PI_I_idx_f0;
		      this.O_loc_sel_xp.S_prefix = DEFAULT_PREFIX;
		      this.O_loc_sel_xp.M_ctor =  ConstructorUtils.getAccessibleConstructor(
		    		  T_clazz, new Class<?>[]{String.class, int.class} );
		      this.O_loc_sel_xp.SBO_using = XpathGenerators.FS_get_xpath_from_tagname(tagName);
		    }
	    
	    public ByTagName(final String tagName, final int PI_I_idx_f0, final String PI_S_prefix) {
		      this.S_selector = tagName;
		      this.O_loc_sel_xp = this.FO_get_loc_sel_xp();	
		      this.O_loc_sel_xp.S_tag_expected    = tagName;
		      this.O_loc_sel_xp.I_idx_f0 = PI_I_idx_f0;
		      this.O_loc_sel_xp.S_prefix = PI_S_prefix;
		      this.O_loc_sel_xp.M_ctor =  ConstructorUtils.getAccessibleConstructor(
		    		  T_clazz, new Class<?>[]{String.class, int.class, String.class});
		      this.O_loc_sel_xp.SBO_using = XpathGenerators.FS_get_xpath_from_tagname(tagName);
		    }
	    
	    @Override
		  public List<WebElement> findElements(SearchContext context) {	
		    	
	      List<WebElement> AO_retval_web_elems;
	
	          this.O_loc_sel_xp.I_idx_f0 = XpathGenerators.ALL_IDX;
	          AO_retval_web_elems = RemoteWebElementXp.FAO_find_elements_by_xpath(this);
		      return AO_retval_web_elems;	
		      }
	
		  @Override
		  public WebElement findElement(SearchContext context) {
		    
	        WebElement    O_retval_web_elem;
		    	
		      O_retval_web_elem = RemoteWebElementXp.FO_find_element_by_xpath(this);
		      return O_retval_web_elem;
		      }
	    
	    @Override
	    public String toString() {
		   String S_retval;
		   S_retval =  this.getClass().getSuperclass().getSimpleName() + "." + this.getClass().getSimpleName() + ":" + 
		       this.O_loc_sel_xp.toString();
	       return S_retval;
		   }  
	  }
	  //------------------------- TAG-NAME END
	  
	  //----- xpath START
	  
//	  public static class ByXpath extends ByXp implements Serializable {
//		  
//		   private static final Class<? extends ByXp> T_clazz = ByXpath.class;
//		  
//		  private final String S_selector;
//		  public ByXpath(final String xpathExpression) {
//			  
//			  this.S_selector                      = xpathExpression;
//			  this.O_loc_sel_xp                    = this.FO_get_loc_sel_xp();
//			  this.O_loc_sel_xp.I_idx_f0           = IGNORED_IDX;
//		      this.O_loc_sel_xp.SBO_using = new DomVectorExtendedSelector(this.S_selector); // xpathExpression;
//		      this.O_loc_sel_xp.M_ctor             =  ConstructorUtils.getAccessibleConstructor(T_clazz, String.class); 
//	    }
//		  
// public ByXpath(final String xpathExpression, final int PI_I_idx_f0) {
//			  
//			  this.S_selector                    = xpathExpression;
//			  this.O_loc_sel_xp                    = this.FO_get_loc_sel_xp();
//			  this.O_loc_sel_xp.I_idx_f0           = PI_I_idx_f0;
//		      this.O_loc_sel_xp.SBO_using = XpathGenerators.FSBO_index_xpath(xpathExpression, PI_I_idx_f0);
//		      this.O_loc_sel_xp.M_ctor             = ConstructorUtils.getAccessibleConstructor(T_clazz, 
//		    		                                    new Class<?>[] {String.class, int.class}); 
//	    }
//		  
//      @Override
//	  public List<WebElement> findElements(SearchContext context) {	
//	    	
//      List<WebElement> AO_retval_web_elems;
//
//      this.O_loc_sel_xp.I_idx_f0 = XpathGenerators.ALL_IDX;
//      AO_retval_web_elems = RemoteWebElementXp.FAO_find_elements_by_xpath(this);
//	  return AO_retval_web_elems;	
//	      }
//
//	  @Override
//	  public WebElement findElement(SearchContext context) {
//	    
//     WebElement    O_retval_web_elem;
//	    	
//	      O_retval_web_elem = RemoteWebElementXp.FO_find_element_by_xpath(this);
//	      return O_retval_web_elem;
//	      }
//
//		   @Override
//		   public String toString() {
//		      String S_retval;
//		      S_retval =  this.getClass().getSuperclass().getSimpleName() + "." + this.getClass().getSimpleName() + ":" + 
//		          this.O_loc_sel_xp.toString();
//	          return S_retval;
//		      }  	  
//		  } 
}