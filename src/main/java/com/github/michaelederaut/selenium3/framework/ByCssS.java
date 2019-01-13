package com.github.michaelederaut.selenium3.framework;

import java.lang.reflect.Constructor;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import com.github.michaelederaut.selenium3.framework.ByXp.Loc;
import com.github.michaelederaut.selenium3.platform.CssSGenerators.LinkText;
import com.github.michaelederaut.selenium3.platform.XpathGenerators;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.DomOffset;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.Locator;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.LocatorRegularity;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.LocatorVariant;

import static com.github.michaelederaut.selenium3.platform.XpathGenerators.DEFAULT_PREFIX;
import static com.github.michaelederaut.selenium3.platform.XpathGenerators.DEFAULT_TAG;
import static org.apache.commons.lang3.StringUtils.LF;


 
/**
 * @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium&nbsp;ByCssSelector">Mr. Michael Eder</a>
 */
public abstract class ByCssS extends By {
	
	public static final Class<?>[] AT_e2_s2_o1_i1_s1_ao = new Class<?>[] {
		   Locator.class, 
		   LocatorVariant.class, 
		   String.class, 
		   String.class,
		   LinkText.class,
		   int.class,
		   String.class,
		   DomOffset[].class};
	
	public static final Class<?>[] AT_e2_as_s1_o1_i1_s1_ao = new Class<?>[] {
		   Locator.class, 
		   LocatorVariant.class, 
		   String[].class, 
		   String.class,
		   LinkText.class,
		   int.class,
		   String.class,
		   DomOffset[].class};	
	
		   
	public static ByCssS loc(
    		   final Locator        locator,
    		   final LocatorVariant variant,
    		   final String         using,
    		   final String         expectedTagName,
    		   final LinkText       linkText,
    		   final int            index,
    		   final String         prefix,
    	       final DomOffset      domOffsets[])     {
        	
        	ByCssS O_retval_by_css;
        	
        	O_retval_by_css = loc_poly(
					locator,        
					variant,        
					using,          
					expectedTagName,
					linkText,
					index,          
					prefix,         
					domOffsets); 	
  
        	return O_retval_by_css;
        }	   
		
	    public static ByCssS loc(
    		   final Locator        locator,
    		   final LocatorVariant variant,
    		   final String         using[],
    		   final String         expectedTagName,
    		   final LinkText       linkText,
    		   final int            index,
    		   final String         prefix,
    	       final DomOffset      domOffsets[]) {
        	
        	ByCssS O_retval_by_css;
        	
        	O_retval_by_css = loc_poly(
					locator,        
					variant,        
					using,          
					expectedTagName,
					linkText,
					index,          
					prefix,         
					domOffsets); 	
  
        	return O_retval_by_css;
        }
	
	protected static ByCssS loc_poly(
    		   final Locator        locator,
    		   final LocatorVariant variant,
    		   final Object         using,
    		   final String         expectedTagName,
    		   final LinkText       linkText,
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
    	    
    	   ByCssS O_retval_by_css = null;
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
    			   M_ctor = ConstructorUtils.getAccessibleConstructor(T_clazz, AT_e2_s2_o1_i1_s1_ao);
                   }
		       else {
		          AS_selectors = (String[])using;
		          M_ctor = ConstructorUtils.getAccessibleConstructor(T_clazz, AT_e2_as_s1_o1_i1_s1_ao);
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
		       O_retval_by_css = (Loc)O_res_by_xp;
    	       }
    	    catch (Exception PI_E_cause) {
			    S_msg_3 = "unable to create instance of subclass of \'" + ByXp.class.getName() + "\'.";
			    E_rt = new RuntimeException(S_msg_3, PI_E_cause);
			    throw E_rt;
    	   }
    	   // O_retval_by_loc.O_loc_sel_xp.M_ctor = M_ctor;
    	   return O_retval_by_css;   
       }         
         
	
	public static class Loc extends ByCssS {
		@Override
			public List<WebElement> findElements(SearchContext context) {	
			// TODO
			return null;
		}
		@Override
			public WebElement findElement(SearchContext context) {
			// TODO
			return null;
		}
	}
	  @Override
	  public boolean equals(final Object PI_O_other) {
		  return true;
	  }
	}
