package it.polito.tdp.quadrato.model;

import java.util.List;

public class TestQuadrato {

	public static void main(String[] args) {
		RisolviQuadrato r = new RisolviQuadrato(5); //ogni riga è una soluzione
		List <List<Integer>> soluzioni= r.quadrati(); //salvo tutti i risultati in una lista che poi vado a stampare
		
		for (List<Integer> sol:soluzioni) {
			System.out.println(sol);
		}
	}

}
