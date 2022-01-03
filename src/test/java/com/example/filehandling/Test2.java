package com.example.filehandling;

import com.example.rats.AdultRat;
import com.example.rats.BabyRat;
import com.example.rats.Rat;

public class Test2 {

	public static void main (String[] args) {
		BabyRat test = new BabyRat(false);
		Rat test2 = new AdultRat(new BabyRat(false));
		System.out.print(test2.getClass().equals(AdultRat.class));
	}

}
