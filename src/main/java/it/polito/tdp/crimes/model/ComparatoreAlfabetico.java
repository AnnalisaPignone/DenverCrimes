package it.polito.tdp.crimes.model;

import java.util.Comparator;

public class ComparatoreAlfabetico implements Comparator <Object> {

	@Override
	public int compare(Object o1, Object o2) {
		String s1=(String)o1; 
		String s2=(String)o2;
		return s1.compareTo(s2);
	}

}
