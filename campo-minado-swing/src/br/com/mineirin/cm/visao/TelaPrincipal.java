package br.com.mineirin.cm.visao;

import javax.swing.JFrame;

import br.com.mineirin.cm.modelo.Tabuleiro;

@SuppressWarnings("serial")
public class TelaPrincipal extends JFrame{

	public TelaPrincipal() {
		Tabuleiro tabu = new Tabuleiro(16, 30, 35);
		add(new PainelTabuleiro(tabu));
		
		tabu.registrarObs(null);
		
		setTitle("Campo Minado");
		setSize(690, 438);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new TelaPrincipal();
	}
}