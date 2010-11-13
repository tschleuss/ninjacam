package org.furb.processor;

public class Cor 
{

	public int calcularDifCores(int[] cor1, int[] cor2)
	{
		// use same formula as for two dimentional distance
		int vermelhoDif = cor1[0] - cor2[0];
		int verdeDif = cor1[1] - cor2[1];
		int azulDif = cor1[2] - cor2[2];
		
		int dif = (vermelhoDif * vermelhoDif) 
				 + (verdeDif * verdeDif)
				 + azulDif * azulDif;
		
		return (int) Math.sqrt(dif);
	}
	
}
