package br.com.mineirin.cm;

import br.com.mineirin.cm.modelo.Tabuleiro;

public class Test {

	public static void main(String[] args) {
		
		Tabuleiro tab = new Tabuleiro(20, 20, 20);
		tab.abrir(2, 2);
	}
}