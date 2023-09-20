package br.com.mineirin.cm.modelo;

import java.util.ArrayList;
import java.util.List;

public class Campo {

	private final int linha;
	private final int coluna;
	
	private boolean minado;
	private boolean aberto = false;
	private boolean marcado;
	
	private List<Campo> vizins = new ArrayList<>();
	private List<CampoInterfac> obs = new ArrayList<>();
	
	Campo(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}
	
	public void registrarObs(CampoInterfac ci) {
		obs.add(ci);
	}
	
	private void NotificarObs(CampoEvento e) {
		obs.stream().forEach(o -> o.eventoOcorreu(this, e));
	}
	
	boolean adcVizin(Campo vizin) {
		boolean linhDf = linha != vizin.linha;
		boolean colunDf = coluna != vizin.coluna;
		boolean diagonal = linhDf && colunDf;
		
		int deltL = Math.abs(linha - vizin.linha);
		int deltC = Math.abs(coluna - vizin.coluna);
		int deltG = deltC + deltL;
		
		if(deltG == 1 && !diagonal) {
			vizins.add(vizin);
		    return true;
		}else if(deltG == 2 && diagonal) {
			vizins.add(vizin);
		   return true;
		}else { 
			return false;
		}
	}	
	public void alterarMarcador() {
		if(!aberto) {
			marcado = !marcado;
			
			if(marcado) {
				NotificarObs(CampoEvento.MARCAR);
			}else {
				NotificarObs(CampoEvento.DESMARCAR);
			}
		}
	}
	void minador() {
			minado = true;
		}
	
	public boolean abrir() {
		if(!aberto && !marcado) {
			
			if(minado) {
			 //nova versao
				NotificarObs(CampoEvento.EXPLODIR);
				return true;
			}
			setAberto(true);
			
			if(vizinSegu()) {
				vizins.forEach(v -> v.abrir());
			}
			return true;
		}else
			return false;
	}
	
	public boolean isMarca() {
		return marcado;
	}
	public boolean isMinado() {
		return minado;
	}
	
	public boolean vizinSegu() {
		return vizins.stream().noneMatch(v -> v.minado);
	}
	
	public boolean isAberto() {
		return aberto;
	}
	
	public void setAberto(boolean aberto) {
		this.aberto = aberto;
		
		if(aberto) {
			NotificarObs(CampoEvento.ABRIR);
		}
	}

	public boolean isFechado() {
		return !isAberto();
	}

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}
	
	boolean objeticoAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		return desvendado || protegido;
	}
	
	public int minasVizinhan() {
		return (int) vizins.stream().filter(v -> v.minado).count();
	}
	
	void reiniciar() {
		aberto = false;
		marcado = false;
		minado = false;
		NotificarObs(CampoEvento.REINICIAR);
	}
}