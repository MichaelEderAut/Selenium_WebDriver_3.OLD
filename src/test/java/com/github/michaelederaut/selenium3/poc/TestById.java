package com.github.michaelederaut.selenium3.poc;

import org.openqa.selenium.By;
import org.openqa.selenium.By.ById;

public class TestById extends TestByHref {
	
	@Override
	 public  By id(final String id) {
	    if (id == null)
	      throw new IllegalArgumentException(
	          "Cannot find elements with a null id attribute.");

	    return new ById(id);
	  }
	
	
}
