package it.polito.tdp.crimes.model;

public class Arco {

	String v1; 
	String v2; 
	double pesoArco;
	public Arco(String v1, String v2, double pesoArco) {
		super();
		this.v1 = v1;
		this.v2 = v2;
		this.pesoArco = pesoArco;
	}
	public String getV1() {
		return v1;
	}
	public void setV1(String v1) {
		this.v1 = v1;
	}
	public String getV2() {
		return v2;
	}
	public void setV2(String v2) {
		this.v2 = v2;
	}
	public double getPesoArco() {
		return pesoArco;
	}
	public void setPesoArco(double pesoArco) {
		this.pesoArco = pesoArco;
	}
	@Override
	public String toString() {
		return "\nArco ("+v1+" "+v2+" "+pesoArco+")";
	} 
	
	
	
}
