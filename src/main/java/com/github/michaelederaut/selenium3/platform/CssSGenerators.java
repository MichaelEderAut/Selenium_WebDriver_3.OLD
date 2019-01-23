package com.github.michaelederaut.selenium3.platform;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.xpath.XPathException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.text.TextStringBuilder;

import com.github.michaelederaut.basics.ExecUtils;
import com.github.michaelederaut.basics.ExecUtils.ExecResult;
import com.github.michaelederaut.basics.RegexpUtils;
import com.github.michaelederaut.basics.RegexpUtils.GroupMatchResult;
import com.github.michaelederaut.basics.StreamUtils.EndCriterion;
import com.github.michaelederaut.selenium3.framework.ByCssS;
import com.github.michaelederaut.selenium3.framework.ByXp;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.DomOffset;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.DomVectorExtendedSelector;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.IndexedStrBuilder;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.Locator;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.LocatorEnums;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.LocatorVariant;
import com.ibm.icu.impl.TimeZoneGenericNames.Pattern;

// import regexodus.Pattern;
import static org.apache.commons.lang3.StringUtils.LF;
import static com.github.michaelederaut.basics.ToolsBasics.FS;

/**
* @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium&nbsp;CssSelector-Generators">Mr. Michael Eder</a>
* 
*/
public class CssSGenerators {
	
 	
//	
//	public static final String S_re_end_criterion_where = "^(.*?)\\(python.exe)";
//	public static final regexodus.Pattern P_end_criterion_where = regexodus.Pattern.compile(S_re_end_criterion_where);
//	
	public static final String DEFAULT_TAG    = "";
	public static final String DEFAULT_PREFIX = "";
	public static final String EMPTY_PREFIX = DEFAULT_PREFIX;
	public static final String S_re_tag_name = "^(\\*|[a-z]*)$";
	public static final regexodus.Pattern P_tag_name = regexodus.Pattern.compile(S_re_tag_name);
	public static final String S_bn_python = "python.exe";
	public static final String S_bn_script = "cssify.py";
	public static final String S_bnr_script = "/" + S_bn_script;
	public static final EndCriterion O_dflt_end_criterion = new EndCriterion();
	
	protected static String S_dna_parent_py;
	protected static String S_pna_py;
	protected static File F_pna_py, F_dna_parent_script;
	protected static String S_pna_py_scr;
	
	
//	public static final EndCriterion O_end_crit_where = 
//			new EndCriterion(EndCriterion.L_timeout_dflt, P_end_criterion_where);
	
	public static class LinkText {
		public String         S_selector;  // using
		public LocatorVariant E_variant;
		
		public LinkText() {
			this.E_variant      = LocatorVariant.regular;
			return;
		}
		
		public LinkText (final String using) {
			this.S_selector     = using;
			this.E_variant      = LocatorVariant.regular;
		    }
		
		public LinkText (
				final String using,
				final LocatorVariant PI_E_variant) {
			this.S_selector     = using;
			this.E_variant      = PI_E_variant;
		    }
	}
	
		
	public static class ExtendedCssSelector extends DomVectorExtendedSelector {
		public boolean B_identity; // equivalent to xpath "."
	   public LinkText O_lnk_txt;
	   
	   public ExtendedCssSelector() {
			super();
			return;
	   }
	  
	    public ExtendedCssSelector(final LinkText PI_O_link_text) {
			super();
			this.O_lnk_txt = PI_O_link_text;
			return;
	   }
	    
	    public ExtendedCssSelector(final String PI_S_using) {
			super (PI_S_using);
			return;
	    }
	
	    public ExtendedCssSelector(
	    		final String PI_S_using,
	    		final LinkText PI_O_link_text) {
			super (PI_S_using);
			this.O_lnk_txt = PI_O_link_text;
			return;
	    }
	    
      public ExtendedCssSelector(
    		  final String PI_S_using, 
    		  final int PI_I_starting_pos_of_idx) {
			super(PI_S_using, PI_I_starting_pos_of_idx);
			return;
		}
      
       public ExtendedCssSelector(
    		  final String PI_S_using,
    		  final LinkText PI_O_link_text,
    		  final int PI_I_starting_pos_of_idx) {
			super(PI_S_using, PI_I_starting_pos_of_idx);
			this.O_lnk_txt = PI_O_link_text;
			return;
		}
      
      public ExtendedCssSelector(final IndexedStrBuilder PI_SB_indexed) {
			super(PI_SB_indexed);
			return;
		    }
      
      public ExtendedCssSelector(
    		  final IndexedStrBuilder PI_SB_indexed,
    		  final LinkText PI_O_link_text
    		  ) {
			super(PI_SB_indexed);
			this.O_lnk_txt = PI_O_link_text;
			return;
		    }
      
      public ExtendedCssSelector(final DomOffset PI_AO_dom_offsets[]) {
    	    super(PI_AO_dom_offsets);
			return;
		    }
      
        public ExtendedCssSelector(
        		final LinkText PI_O_link_text,
        		final DomOffset PI_AO_dom_offsets[]) {
			super(PI_AO_dom_offsets);
			this.O_lnk_txt = PI_O_link_text;
			return;
		    }
      
      public ExtendedCssSelector(
    		  final String PI_S_using, 
    		  final DomOffset PI_AO_dom_offsets[]) {
			super(PI_S_using, PI_AO_dom_offsets);
			return;
		    }
		
       public ExtendedCssSelector(
    		  final String PI_S_using, 
    		  final LinkText PI_O_link_text,
    		  final DomOffset PI_AO_dom_offsets[]) {
			super(PI_S_using, PI_AO_dom_offsets);
			this.O_lnk_txt = PI_O_link_text;
			return;
		    }
      
		public ExtendedCssSelector(
				final String PI_S_using, 
				final int PI_I_starting_pos_of_idx, 
				final DomOffset PI_AO_dom_offsets[]) {
			super(PI_S_using, PI_I_starting_pos_of_idx, PI_AO_dom_offsets);
			return;
		}
		
		public ExtendedCssSelector(
				final String PI_S_using,
				final LinkText PI_O_link_text, 
				final int PI_I_starting_pos_of_idx, 
				final DomOffset PI_AO_dom_offsets[]) {
			super(PI_S_using, PI_I_starting_pos_of_idx, PI_AO_dom_offsets);
			this.O_lnk_txt = PI_O_link_text;
			return;
		}
		
		public ExtendedCssSelector(
				final IndexedStrBuilder PI_SB_indexed, 
				final DomOffset PI_AO_dom_offsets[]) {
				super(PI_SB_indexed,  PI_AO_dom_offsets);
				return;
		}
		
		public ExtendedCssSelector(
				final IndexedStrBuilder PI_SB_indexed,
				final LinkText PI_O_link_text, 
				final DomOffset PI_AO_dom_offsets[]) {
			
				super(PI_SB_indexed,  PI_AO_dom_offsets);
				this.O_lnk_txt = PI_O_link_text;
				return;
		}
		
		public ExtendedCssSelector(final int PI_AI_dom_offsets[]) {
			super(PI_AI_dom_offsets);
			return;
		    }
		
		public ExtendedCssSelector(
			    final LinkText PI_O_link_text, 
				final int PI_AI_dom_offsets[]) {
			super(PI_AI_dom_offsets);
			this.O_lnk_txt = PI_O_link_text;
			return;
		    }
		
		public ExtendedCssSelector(
				final String PI_S_value, 
				final int PI_AI_dom_offsets[]) {
			super(PI_S_value, PI_AI_dom_offsets);
			return;
		    }
		
		public ExtendedCssSelector(
				final String PI_S_value,
				final LinkText PI_O_link_text, 
				final int PI_AI_dom_offsets[]) {
			super(PI_S_value, PI_AI_dom_offsets);
			this.O_lnk_txt = PI_O_link_text;
			return;
		    }
		
		public ExtendedCssSelector(
				final String PI_S_using,
				final int PI_I_starting_pos_of_idx,
				final int PI_AI_dom_offsets[]) {
			super(PI_S_using, PI_I_starting_pos_of_idx, PI_AI_dom_offsets);
			return;
		}
		
		public ExtendedCssSelector(
				final String PI_S_using,
				final LinkText PI_O_link_text, 
				final int PI_I_starting_pos_of_idx,
				final int PI_AI_dom_offsets[]) {
			super(PI_S_using, PI_I_starting_pos_of_idx, PI_AI_dom_offsets);
			this.O_lnk_txt = PI_O_link_text;
			return;
		}
		
		public ExtendedCssSelector(
				final IndexedStrBuilder PI_SB_indexed, 
				final int PI_AI_dom_offsets[]) {
			
				super(PI_SB_indexed, PI_AI_dom_offsets);
				return;
		        }
		
		public ExtendedCssSelector(
				final IndexedStrBuilder PI_SB_indexed,
				final LinkText PI_O_link_text, 
				final int PI_AI_dom_offsets[]) {
			
				super(PI_SB_indexed, PI_AI_dom_offsets);
				this.O_lnk_txt = PI_O_link_text;
				return;
		        }	
	}
	
	public static /* DomVectorExtendedSelector */ ExtendedCssSelector FSBO_get_csss(
		final LocatorEnums PI_O_locator_enums,
		final String    PI_S_using, 
		final String    PI_S_tag, 
		final LinkText  PI_O_link_text,
		final int       PI_I_idx_f0,
		final DomOffset PI_AO_dom_offsets[],
		final String    PI_S_prefix) {
		
		String AS_using[];
		/* DomVectorExtendedSelector */ ExtendedCssSelector  SBO_retval_csss;
		
		AS_using = new String[] {PI_S_using};
		SBO_retval_csss = FSBO_get_csss(
				PI_O_locator_enums,
				AS_using,
				PI_S_tag,
				PI_O_link_text,
	            PI_I_idx_f0,
	            PI_AO_dom_offsets,
	            PI_S_prefix
	            );
		return SBO_retval_csss;
	}
	
	public static /* DomVectorExtendedSelector */ ExtendedCssSelector FSBO_get_csss(
		final LocatorEnums PI_O_locator_enums,
		final String    PI_AS_using[], 
		final String    PI_S_tag, 
		final LinkText  PI_O_link_text,
		final int       PI_I_idx_f0,
		final DomOffset PI_AO_dom_offsets[],
		final String    PI_S_prefix) {
		
		RuntimeException         E_rt;
		AssertionError           E_assert;
	    IllegalArgumentException E_ill_arg;
	    NullPointerException     E_np;
	    IndexOutOfBoundsException E_ind_out_of_boundary; 
	    IOException              E_io;
	    
	    GroupMatchResult O_grp_match_result;
	    String S_msg_1, S_msg_2, S_csss, S_prefix,
	           S_using, S_bool_op, S_tag_name;
	    Locator        E_locator;
	    LocatorVariant E_locator_variant;
	    ExtendedCssSelector SBO_retval_csss;
	    boolean B_has_valid_tag_name, B_identity;
	    
	    B_identity = false;
	    
	    if (PI_O_locator_enums == null) {
	    	E_locator         = Locator.domOffsets;
	    	E_locator_variant = LocatorVariant.regular;
	        }
	    else {
	    	E_locator = PI_O_locator_enums.E_locator;
	    	E_locator_variant = PI_O_locator_enums.E_locator_variant;
	        }
	    if (PI_S_tag == null) {
	    	S_tag_name = "";
	        }
	    else {
	        O_grp_match_result = RegexpUtils.FO_match(PI_S_tag, CssSGenerators.P_tag_name);
	        if (O_grp_match_result.I_array_size_f1 > 0) { 
	    	   B_has_valid_tag_name = true; 
	    	   S_tag_name = PI_S_tag;
	           }
	       else {
    	       S_msg_1 = "Invalid value for tag: \'" + PI_S_tag + "\'";
    	       E_ill_arg = new IllegalArgumentException(S_msg_1);
    	       throw E_ill_arg;
               }
	       }

	 if (PI_O_locator_enums.E_locator == E_locator.domOffsets) {
		SBO_retval_csss = new ExtendedCssSelector((String)null, PI_O_link_text, PI_I_idx_f0, PI_AO_dom_offsets);  
		return SBO_retval_csss;
	    }
	   
	 if (PI_S_prefix == null) {
		S_prefix = "";
	    }
	 else {
		S_prefix = PI_S_prefix; 
	    }
	 
	  int I_nbr_selectors_f1, I_nbr_selectors_f0, i1;
	  String S_csss_single_term;
	  StringBuffer SB_csss_tail;
	
	if (PI_AS_using == null) {
	   I_nbr_selectors_f1 = 0;
	   }
	else {
	    I_nbr_selectors_f1 = PI_AS_using.length;
	    }
	if (I_nbr_selectors_f1 == 0) {
		S_msg_1 = "No selectors provided.";
		E_ill_arg = new IllegalArgumentException(S_msg_1);
		S_msg_2 = "Unable to create css-selector with the arguments given.";
		E_rt = new RuntimeException(S_msg_2, E_ill_arg);
		throw E_rt;
	}
	 
	// E_locator = PI_O_locator_enums.E_locator;
	S_csss    = "";
	S_using   = null;
	
	switch (E_locator) {
	   case className:
		  ArrayList<String>        AS_class_names;
		  TextStringBuilder        SB_csss;
		  int                      I_idx_old_f0;  
		
		  if (I_nbr_selectors_f1 == 1) {
		     E_locator_variant = LocatorVariant.regular;   
	         }	
		  else if (E_locator_variant == LocatorVariant.regular) {
			  if (I_nbr_selectors_f1 > 1) {
				 E_locator_variant = LocatorVariant.and;
			     }
		      }
		  else if (!((E_locator_variant == LocatorVariant.and) || (E_locator_variant == LocatorVariant.or))) {
			  S_msg_1 = "Invalid or unimplemented locator variant for " + E_locator_variant.name();
			  E_ill_arg = new IllegalArgumentException(S_msg_1);
		      throw E_ill_arg;
		      }
		  AS_class_names = new ArrayList<String>();	
		  for (i1 = 0; i1 < I_nbr_selectors_f1; i1 ++) {   // check for duplicate class names
		      S_using = PI_AS_using[i1];
		    //--- https://benfrain.com/when-and-where-you-can-use-numbers-in-id-and-class-names/
		    // ^\p{Alpha}[\w\-]*$
		    O_grp_match_result = RegexpUtils.FO_match(S_using, XpathGenerators.P_clazz_name);
		    if (O_grp_match_result.I_array_size_f1 == 0) { 
		        S_msg_1 = "Class-name parameter " + i1 + " \'" + S_using + "\' must be alpha-numeric with - or _";
		        E_ill_arg = new IllegalArgumentException(S_msg_1);
		        throw E_ill_arg;
		        }
		    I_idx_old_f0 = AS_class_names.indexOf(S_using);
		    if (I_idx_old_f0 > 0) {
		       S_msg_1 = "Duplicate class-name parameter " + i1 + " \'" + S_using + "\' already occurs at position :" + I_idx_old_f0;
		       E_ill_arg = new IllegalArgumentException(S_msg_1);
		       throw E_ill_arg;
		       }
		    AS_class_names.add(S_using);
		    }
		  I_nbr_selectors_f0 = I_nbr_selectors_f1 - 1;
		  
		  if (E_locator_variant == LocatorVariant.orPrefixWithSeparatePos) {
			 SB_csss = new TextStringBuilder(PI_S_prefix);
			 S_bool_op = ",";
		     }
		  else if (E_locator_variant == LocatorVariant.or) {
			 SB_csss = new TextStringBuilder();
			 S_bool_op = ",";
		     }
		  else {  // and OR regular
			 SB_csss = new TextStringBuilder(PI_S_prefix + S_tag_name);
			 S_bool_op = "";
		     }
		 
	      for (i1 = 0; i1 < I_nbr_selectors_f1; i1++) {
		     S_using = PI_AS_using[i1];
		     
		     if (E_locator_variant == LocatorVariant.orPrefixWithSeparatePos) {
		        SB_csss.append(S_tag_name + "." + S_using);
		        }
		     else if (E_locator_variant == LocatorVariant.or) {   
		         SB_csss.append(S_prefix + S_tag_name + "." + S_using);
		       }
		     else { // and OR regular
		    	 SB_csss.append("." + S_using);  
		         }
		     if (i1 < I_nbr_selectors_f0) {
		    	 SB_csss.append(S_bool_op);  // "" or ","
	            }
	         }
	      S_csss = SB_csss.toString();        
		 
	      break; // classname
	   
	   case cssSelector:
		   if (I_nbr_selectors_f1 > 1) {
	    	   S_msg_1 = "Number of selectors exceeding 1";
	    	   E_ill_arg = new IllegalArgumentException(S_msg_1);
	    	   S_msg_2 = "Implementation restriction: Operation \'" + E_locator.name() + "\' not (yet) eligible for multiple (css-)selectors.";
			   E_rt = new IllegalArgumentException(S_msg_2, E_ill_arg);
			   throw E_rt;
		       }
		   S_csss = PI_AS_using[0];
		   break; // css-selector
	   case id:
		  ArrayList<String>        AS_id_names;
		 	
		  if (I_nbr_selectors_f1 == 1) {
		         E_locator_variant = LocatorVariant.regular;   
	            }	
		  else if (E_locator_variant == LocatorVariant.regular) {
			  if (I_nbr_selectors_f1 > 1) {
				E_locator_variant = LocatorVariant.or;
			    }
		      }
		  else if (E_locator_variant != LocatorVariant.or) {
			  S_msg_1 = "Invalid or unimplemented locator variant for " + E_locator_variant.name();
			  E_ill_arg = new IllegalArgumentException(S_msg_1);
		      throw E_ill_arg;
		      }
		   AS_id_names = new ArrayList<String>();
		   for (i1 = 0; i1 < I_nbr_selectors_f1; i1 ++) {   // check for duplicate class names
		      S_using = PI_AS_using[i1];
		    //--- https://benfrain.com/when-and-where-you-can-use-numbers-in-id-and-class-names/
		    // ^\p{Alpha}[\w\-]*$
		     O_grp_match_result = RegexpUtils.FO_match(S_using, XpathGenerators.P_clazz_name);
		     if (O_grp_match_result.I_array_size_f1 == 0) { 
		        S_msg_1 = "id-name parameter " + i1 + " \'" + S_using + "\' must be alpha-numeric with - or _";
		        E_ill_arg = new IllegalArgumentException(S_msg_1);
		        throw E_ill_arg;
		        }
		     if (i1 > 0) {
			    I_idx_old_f0 = AS_id_names.indexOf(S_using);
			    if (I_idx_old_f0 > 0) {
			       S_msg_1 = "Duplicate id-name parameter " + i1 + " \'" + S_using + "\' already occurs at position :" + I_idx_old_f0;
			       E_ill_arg = new IllegalArgumentException(S_msg_1);
			       throw E_ill_arg;
			       }}
		    AS_id_names.add(S_using);
		   }
		   
		   I_nbr_selectors_f0 = I_nbr_selectors_f1 - 1;
		  
		   S_bool_op = ",";
		   if (E_locator_variant == LocatorVariant.orPrefixWithSeparatePos) {
			 SB_csss = new TextStringBuilder(PI_S_prefix);
		     }
		   else if (E_locator_variant == LocatorVariant.or) {
			 SB_csss = new TextStringBuilder();
		     }
		   else {  // and OR regular
			 SB_csss = new TextStringBuilder(PI_S_prefix + S_tag_name);
		     }
		 
	       for (i1 = 0; i1 < I_nbr_selectors_f1; i1++) {
		     S_using = PI_AS_using[i1];
		     
		     if (E_locator_variant == LocatorVariant.orPrefixWithSeparatePos) {
		        SB_csss.append(S_tag_name + "." + S_using);
		        }
		     else if (E_locator_variant == LocatorVariant.or) {   
		         SB_csss.append(S_prefix + S_tag_name + "." + S_using);
		       }
		     if (i1 < I_nbr_selectors_f0) {
		    	 SB_csss.append(S_bool_op);  // "" or ","
	            }
	         }
	      S_csss = SB_csss.toString();        
		  break; // id
	   case linkText:
		     S_msg_1  = "Implementation restriction: Current version of css doesn't support Operation \'" + E_locator.name() + "\'." + LF +
					     "Use separate parameters tag_name == '*' and link-text instead.";
			  E_ill_arg = new IllegalArgumentException(S_msg_1);
			  S_msg_2 = "Unable to convert: " + S_using + "' to a valid css-selector";
			  E_rt = new RuntimeException(S_msg_2,  E_ill_arg);
		      throw E_rt;
		   // break;
		case tagName:
			  if (I_nbr_selectors_f1 > 1) {
		    	 S_msg_1 = "Number of selectors exceeding 1";
		    	 E_ill_arg = new IllegalArgumentException(S_msg_1);
		    	 S_msg_2 = "Implementation restriction: Operation \'" + E_locator.name() + "\' not eligible for multiple selectors.";
		    
				 E_rt = new IllegalArgumentException(S_msg_2, E_ill_arg);
				 throw E_rt;
		         }
			  S_csss = PI_S_prefix + PI_AS_using[0]; 
			 break;  
			 
	   case xpath:
		   XPathException E_xp;
		   ExecResult O_exec_res;
		   List<String> AAS_retvals[], AS_retvals;
		   URL O_url_script;
		   File F_pna_script;
		   String /* S_pna_script,*/ S_pnr_script, AS_cmd[];
		   
		   S_using = PI_AS_using[0];
		   if (StringUtils.equals(S_using, ".")) {
			  B_identity = true;
			  S_csss = "";
			  break; 
		      }
		   if (S_dna_parent_py == null) {
			  S_msg_1 = "Locator " + E_locator.name() + " is discouraged in this context " + LF +
					    "Use " + ByXp.Loc.class.getName() + " to use the native xpath browser api, instead.";
			      E_ill_arg = new IllegalArgumentException(S_msg_1);
			      E_ill_arg.printStackTrace(System.out);  
			  S_dna_parent_py = ExecUtils.FS_get_parent_of_executable(S_bn_python);
			  try {
				if (S_dna_parent_py == null) {
					 S_msg_1 = "Unable to find: \'" + S_bn_python + "\'.";
					 E_io = new IOException(S_msg_1);
					 throw E_io;
				     }
				   S_pna_py = S_dna_parent_py + FS + S_bn_python;
				   F_pna_py = new File(S_pna_py);
				   if (!F_pna_py.canExecute()) {
					   S_msg_1 = "Unable to execute: \'" + S_pna_py + "\'."; 
					   E_io = new IOException(S_msg_1);
					   throw E_io;
				      }
				   O_url_script = CssSGenerators.class.getResource(S_bnr_script);
				   if (O_url_script == null) {
					  S_msg_1 = "Unable to find script: \'" + S_bn_script + "\'.";
					  E_io = new IOException(S_msg_1);
					  throw E_io;
				     }
				   S_pnr_script = O_url_script.getPath();
				   if (S_pnr_script.startsWith("/")) {
					  S_pna_py_scr = S_pnr_script.substring(1);
				       }
				   else {
					  S_pna_py_scr = S_pnr_script;
				      }
				  if (SystemUtils.IS_OS_WINDOWS) {
					 S_pna_py_scr =  StringUtils.replaceChars(S_pna_py_scr, "/", "\\");
				     }
				   F_pna_script = new File(S_pna_py_scr);
				   if (!F_pna_script.canRead()) {
					   S_msg_1 = "Unable to open script file: \"" + S_pna_py_scr + "\" for reading."; 
					   E_io = new IOException(S_msg_1);
					   throw E_io;
				      }
				   F_dna_parent_script =  F_pna_script.getParentFile();
			} catch (IOException PI_E_io) {
				 S_msg_2 = "Unable to convert an xpath to css-selector";
				 E_rt = new RuntimeException(S_msg_2, PI_E_io);
				 throw E_rt;
			   }
			//  O_exec_res = ExecUtils.FAAS_exec_sync(PI_S_cmd, PI_AS_envp, PI_F_wd);
			 
		   } // end if script part not yet initilized;
		   
		   AS_cmd = new String[] {S_pna_py, S_pna_py_scr, S_using};
		   O_exec_res = ExecUtils.FAAS_exec_sync(AS_cmd, (String[])null, F_dna_parent_script, O_dflt_end_criterion);
		   AAS_retvals = O_exec_res.AAS_retvals;
		   try {
			 if (O_exec_res.I_exit_value != 0) {
				 S_msg_1 = "Exit code returned from script interperter: \"" + S_pna_py + "\" : " + O_exec_res.I_exit_value + ".";
				 E_io = new IOException(S_msg_1);
				 throw E_io;
			 }
			 if (AAS_retvals == null) {
				   S_msg_1 = "Unable to get any result from script interperter: \"" + S_pna_py + "\" on stdout and stederr.";
				   E_np = new NullPointerException(S_msg_1);
				   throw E_np;
			       }
			if (AAS_retvals.length == 0) {
				 S_msg_1 = "Unable to get any result from script interperter: \"" + S_pna_py + "\" on stdout.";
				 E_ind_out_of_boundary = new IndexOutOfBoundsException(S_msg_1);
				 throw E_ind_out_of_boundary;
			     }
			AS_retvals = AAS_retvals[0];
			if (AS_retvals == null) {
				 S_msg_1 = "Unable to get any lines from script interperter: \"" + S_pna_py + "\" on stdout.";
				 E_np = new NullPointerException(S_msg_1);
				 throw E_np;
			    }
			if (AS_retvals.size() == 0) {
				 S_msg_1 = "Unable to get any lines from script interperter: \"" + S_pna_py + "\" on stdout.";
				 E_ind_out_of_boundary = new IndexOutOfBoundsException(S_msg_1);
				 throw E_ind_out_of_boundary; 
			     }
			S_csss = AS_retvals.get(0);
			if (StringUtils.isBlank(S_csss)) {
				S_msg_1 = "Invalid first line " 
						   + "\'" + S_csss + "\'" +
						   "returned by script interperter: \"" + S_pna_py + "\" on stdout.";
				E_assert = new AssertionError(S_msg_1);
				throw E_assert;
			}
			if (S_csss.startsWith("Invalid or unsupported Xpath:")) {
				S_msg_1 = "Error occurred during conversion" + LF +
						  S_csss;
				E_xp = new XPathException(S_msg_1);
//				S_msg_2 = "Unable to convert xpath:" + LF + 
//						  "\t" + S_using + LF +
//						   "to a css-selector.";
//				E_rt = new RuntimeException(S_msg_2, E_xp);
				throw E_xp;		
			}
		} catch (AssertionError|IndexOutOfBoundsException|IOException|NullPointerException|XPathException PI_E_assert) {
			S_msg_2 = "Unable to convert: " + S_using + "' to a valid css-selector";
			E_rt = new RuntimeException(S_msg_2, PI_E_assert);
			throw E_rt;
		    }
		   
		   S_csss = O_exec_res.AAS_retvals[0].get(0);
		   break; // xpath.
	   default:
		   String S_locator, S_op;
		   ArrayList<String>        AS_selector_names;
		   
		   if ((E_locator_variant == LocatorVariant.regular) || 
			   (E_locator_variant == LocatorVariant.or) || 
			   (E_locator_variant == LocatorVariant.orPrefixWithSeparatePos)) {
		       S_op = "="; }
		  
		    else if (E_locator_variant == LocatorVariant.prefix) {
		       S_op = "^="; 
		       }
		    else if (E_locator_variant == LocatorVariant.prefixWord) {
		       S_op = "|="; 
		       }
		    else if (E_locator_variant == LocatorVariant.partial) {
		    	S_op = "*="; 
		        }
		    else if (E_locator_variant == LocatorVariant.partialWord) {
		    	S_op = "~=";
		        }
		    else if (E_locator_variant == LocatorVariant.suffix) {
		    	S_op = "$=";
		        }		   
		   else  {
			  S_msg_1 = "Invalid or unimplemented locator variant for " + E_locator_variant.name();
			  E_ill_arg = new IllegalArgumentException(S_msg_1);
		      throw E_ill_arg;
		      }
		   if (E_locator == Locator.forLabel) {
				S_locator = "for";
			    }
			else {
			   S_locator = E_locator.name();
			   }
		    AS_selector_names = new ArrayList<String>();
		    for (i1 = 0; i1 < I_nbr_selectors_f1; i1 ++) { // check for duplicate class names
		        S_using = PI_AS_using[i1];
			    if (i1 > 0) {
			       I_idx_old_f0 = AS_selector_names.indexOf(S_using);
			       if (I_idx_old_f0 > 0) {
			           S_msg_1 = "Duplicate selector parameter " + i1 + " \'" + S_using + "\' already occurs at position :" + I_idx_old_f0;
			           E_ill_arg = new IllegalArgumentException(S_msg_1);
			           throw E_ill_arg;
			           }}
			    AS_selector_names.add(S_using);
		    }
		    I_nbr_selectors_f0 = I_nbr_selectors_f1 - 1;
		//    S_bool_op = ",";
		    if (E_locator_variant == LocatorVariant.orPrefixWithSeparatePos) {
			   SB_csss = new TextStringBuilder(PI_S_prefix);
		       }
		    else if (E_locator_variant == LocatorVariant.or) {
			   SB_csss = new TextStringBuilder();
		       }
		    else {  // and OR regular
			   SB_csss = new TextStringBuilder(PI_S_prefix + S_tag_name);
		      }
		    for (i1 = 0; i1 < I_nbr_selectors_f1; i1++) {
		       S_using = PI_AS_using[i1];
		     
		       if (E_locator_variant == LocatorVariant.orPrefixWithSeparatePos) {
		          SB_csss.append(S_tag_name + "[" + S_locator + "=" + S_using + "]");
		           }
		       else if (E_locator_variant == LocatorVariant.or) {   
		          SB_csss.append(S_prefix + S_tag_name + "[" + S_locator + S_op + S_using + "]");
		       }
		    if (i1 < I_nbr_selectors_f0) {
		       SB_csss.append(",");  // or
	           }
	         }
	      S_csss = SB_csss.toString();
		   
	   break; // default
	      }
    if (S_csss.equals("")) {
	   S_csss = "*";
	   }   
	SBO_retval_csss = new ExtendedCssSelector(S_csss, PI_O_link_text, PI_I_idx_f0, PI_AO_dom_offsets);
	if (B_identity == true) {
		SBO_retval_csss.B_identity = B_identity;
	    }
	return SBO_retval_csss;
	}

}