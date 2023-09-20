package br.com.mineirin.cm.visao;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import br.com.mineirin.cm.modelo.Campo;
import br.com.mineirin.cm.modelo.CampoEvento;
import br.com.mineirin.cm.modelo.CampoInterfac;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

@SuppressWarnings("serial")
public class BotaoCampo extends JButton 
implements CampoInterfac, MouseListener{

	private final Color BG_PADRAO = new Color(184, 184, 184);
	private final Color BG_MARCAR = new Color(8, 179, 247);
	private final Color BG_EXPLODIR = new Color(184, 66, 68);
	private final Color TEXTO_PADRAO = new Color(0, 100, 0);
	
	private Campo campo;
	
	public BotaoCampo(Campo c) {
		this.campo = c;
		setBorder(BorderFactory.createBevelBorder(0));
		setBackground(BG_PADRAO);
		setOpaque(true);
		
		addMouseListener(this);
		c.registrarObs(this);
	}
	public void eventoOcorreu(Campo c, CampoEvento e) {
		switch(e){
			case ABRIR:
			aplicarEstiloAbrir();
			break;
			case MARCAR:
			aplicarEstiloMarcar();
			break;
			case EXPLODIR:
			aplicarEstiloExplodir();
			break;
			default:
			aplicarEstiloAPadrao();
			break;
		}
		SwingUtilities.invokeLater(() -> {
			repaint();
			validate();
		});
	}
		
	private void aplicarEstiloAbrir() {
		
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		if(campo.isMinado()) {
			setBackground(BG_EXPLODIR);
			return;
		}
		setBackground(BG_PADRAO);
		
		switch (campo.minasVizinhan()) {
		case 1:
			setForeground(TEXTO_PADRAO);
			break;
		case 2:
			setForeground(Color.BLUE);
			break;
		case 3:
			setForeground(Color.YELLOW);
			break;
		case 4:
		case 5:
		case 6:	
			setForeground(Color.RED);
			break;	
		default:
			setForeground(Color.PINK);
			break;	
		}	
		String valor = !campo.vizinSegu() ? 
				campo.minasVizinhan() +"" : "";
		setText(valor);
	}
		private void aplicarEstiloAPadrao() {
		setBackground(BG_PADRAO);
		setBorder(BorderFactory.createBevelBorder(0));
		setText("");
	}
		private void aplicarEstiloExplodir() {
			setBackground(BG_EXPLODIR);
			setForeground(Color.WHITE);
			setText("☼");
	}
		private void aplicarEstiloMarcar() {
			setBackground(BG_MARCAR);
			setForeground(Color.BLACK);
			setText("¶");
	}
		public void mousePressed(MouseEvent e) {
			if(e.getButton() == 1) {
				campo.abrir();
			}else {
				campo.alterarMarcador();
			}
			
		}
		public void mouseClicked(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
}