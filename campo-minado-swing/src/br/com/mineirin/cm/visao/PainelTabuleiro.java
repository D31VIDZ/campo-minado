package br.com.mineirin.cm.visao;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import br.com.mineirin.cm.modelo.Tabuleiro;

@SuppressWarnings("serial")
public class PainelTabuleiro extends JPanel{

	public PainelTabuleiro(Tabuleiro tabu) {
		setLayout(new GridLayout(
				tabu.getLinhas(), tabu.getColunas()));
		
		tabu.parCada(c -> add(new BotaoCampo(c)));
		tabu.registrarObs(e -> {
			SwingUtilities.invokeLater(() -> {
				if(e.isGanhou()) {
					JOptionPane.showMessageDialog(this, "ganhou!!");
				}else {
					JOptionPane.showMessageDialog(this, "perdeu!!");
				}	
				tabu.reiniciar();
			});
		});
		}
	}