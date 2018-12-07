package com.github.michaelederaut.selenium3.poc;

import java.util.function.*;
public class TestFunction {

	public static void main(String[] args) {
		Function<Integer, Integer> f1 =  (x) -> {return x + 5;};
		System.out.println (f1.apply(3));
		
	}

}
