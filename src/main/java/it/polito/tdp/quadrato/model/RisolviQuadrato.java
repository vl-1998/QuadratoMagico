package it.polito.tdp.quadrato.model;

import java.util.ArrayList;
import java.util.List;

public class RisolviQuadrato {
	private int N ; // lato del quadrato
	private int N2 ; // numero di caselle (N^2)
	private int magica ; // costante magica
	
	private List <List <Integer>> soluzioni; //lista di liste che contiene tutte le soluzioni
	
	//COSTRUISCO I METODI DELLA CLASSE
	//costruttore
	public RisolviQuadrato (int N) {
		this.N=N;
		this.N2=N*N; //mi salvo il numero di caselle solo per comodità
		this.magica=N*(N2+1)/2;
	}
	
	//procedura di ricerca, calcola tutti i quadrati magici PUBBLICA
	public List <List<Integer>> quadrati() {
		//avviao la ricorsione, devo preparare la struttura dati per il livello 0
		List <Integer> parziale = new ArrayList <>();
		int livello=0;
		
		this.soluzioni= new ArrayList <List <Integer>>(); 
		
		cerca (parziale, livello);
		
		return this.soluzioni;
	}
	
	//procedura ricorsiva PRIVATA
	private void cerca(List <Integer> parziale, int livello) { //deve avere le informazioni sulla soluzione parziale su cui lavora
		//sono nel caso temrinale?
		if (livello==N2) {
			//caso terminale
			if (controlla(parziale)){ //perdiamo efficienza perchè controlliamo la soluzione solo nel caso terminale
				//è magico!!
				System.out.println(parziale); //non vogliamo che stampi, ma che inserisca il tutto in una struttura dati
				this.soluzioni.add(new ArrayList <>(parziale)); //non stampo le soluzioni, ma le metto nella lista, ma qui sto salvando il riferimento all'oggettp parziale
																//che continua a cambiare di valore, quindi quando vado a stampare ho un oggetto vuoto perchè sono all'ultimo livello
																// della ricorsione. Devo crearmi una copia 
			}
			return ; //arrivato al livello massimo non devo fare nulla, torno indietro ai livelli precedenti di ricorsione
		}
		
		//controlli intermedi quando il livello è mutliplo di N, ovvero ho delle righe complete, controllo una riga alla volta
		if (livello%N==0 && livello!=0) { //se il livello è multiplo di N e il livello è diverso da 0, perchè se è uguale a 0 non posso fare nulla
			if (!controllaRiga(parziale, livello/N-1)) { //se il livello vale 4 controlla la riga (4/4)-1=0, se questo è falso, quindi la somma sulla riga è errata io poto
				return ; //potatura dell'albero di ricerca, fa si che io non prosegua nella ricorsione
			}
		}
		
		
		//caso intermedio 
		for (int valore=1; valore<=N2; valore++) {
			if (!parziale.contains(valore)) { //se la soluzioen parziale NON contiene il valore lo posso provare
				//provo il valore
				parziale.add(valore);
				cerca (parziale, livello+1);
				parziale.remove(parziale.size()-1); //faccio backtracking, rimuovo la soluzione che ho appena provato
			}
		}
	}
	
	/**
	 * Verifica se una soluzione rispetta tutte le somme
	 * @param parziale
	 * @return
	 */
	private boolean controlla(List<Integer> parziale) { //fa tutta la somma delle righe e delle colonne
		if(parziale.size()!=this.N*this.N) //verifica che la soluzione sia piena e riempia completamente il quafrato
			throw new IllegalArgumentException("Numero di elementi insufficiente") ;
		
		// Fai la somma delle righe
		for(int riga=0; riga<this.N; riga++) {
			int somma = 0 ;
			for(int col=0; col<this.N; col++) {
				somma += parziale.get(riga*this.N+col) ; //per disporli a forma di quadrato, salto di una posizione
			}
			if(somma!=this.magica) //non è la costante magica
				return false ;
		}
		
		// Fai la somma delle colonne
		for(int col=0; col<this.N; col++) {
			int somma = 0 ;
			for(int riga=0; riga<this.N; riga++) {
				somma += parziale.get(riga*this.N+col) ;
			}
			if(somma!=this.magica)
				return false ;
		}
		
		// diagonale principale
		int somma = 0;
		for(int riga=0; riga<this.N; riga++) {
			somma += parziale.get(riga*this.N+riga) ;
		}
		if(somma!=this.magica) //non è la costante magica
			return false ;
		
		// diagonale inversa
		somma = 0;
		for(int riga=0; riga<this.N; riga++) {
			somma += parziale.get(riga*this.N+(this.N-1-riga)) ;
		}
		if(somma!=this.magica) //non è la costante magica
			return false ;

		return true ;
	}
	
	//controllo in più per cercare di non fare inutilmente la ricorsione
	private boolean controllaRiga(List<Integer> parziale, int riga) { //gli passo la soluzione parziale e il numero di riga che ho appena completato
		int somma=0;
		for (int col=0; col<N; col++) 
			somma+=parziale.get(riga*N+col);
	
		return somma==magica; //se somma uguale magica ritorna vero, altrimenti ritorna falso
	}
}
