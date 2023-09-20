package br.com.mineirin.cm.modelo;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Tabuleiro implements CampoInterfac{

	private final int linhas;
	private final int colunas;
	private final int mina;
	
	private final List<Campo> campos = new ArrayList<>();
	private final List<Consumer<ResultadoEvento>> obes = new ArrayList<>();
	
	
	public Tabuleiro(int linhas, int colunas, int mina) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.mina = mina;
	    
		gerarcampo();
		associarVizi();
		sortearMina();
	}
	
	public void parCada(Consumer<Campo> fun) {
		campos.forEach(fun);
	}
	
	public void registrarObs(Consumer<ResultadoEvento> ob) {
		obes.add(ob);
	}
	
	private void NotificarObs(Boolean resu) {
		obes.stream()
		     .filter(Objects::nonNull)
		    .forEach(o -> o.accept(new ResultadoEvento(resu)));
	}
	
	public void abrir(int linha, int coluna) {
			campos.stream()
			.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
			.findFirst()
			.ifPresent(c -> c.abrir());
}

	private void mostraMina(){
		campos.stream()
		      .filter(c -> c.isMinado())
		      .filter(c -> !c.isMarca())
		      .forEach(c -> c.setAberto(true));
	}
	
	public void marcar(int linha, int coluna) {
		campos.stream()
		.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
		.findFirst()
		.ifPresent(c -> c.alterarMarcador());
	}

	private void gerarcampo() {
		for(int i = 0; i < linhas; i++) {
			for(int j = 0; j < colunas; j++) {
				Campo cam = new Campo(i, j);
				cam.registrarObs(this);
				campos.add(cam);
			}
		}
	}
	
	private void associarVizi() {
		for(Campo c1 : campos) {
			for(Campo c2 : campos) {
				c1.adcVizin(c2);
			}
		}
	}
	
	private void sortearMina() {
		long minasArm = 0;
		Predicate<Campo> minado = c -> c.isMinado();
		
		do {
			int alea = (int) (Math.random() * campos.size());
			campos.get(alea).minador();
			minasArm = campos.stream().filter(minado).count();
		}while(minasArm < mina);
	}
	
	public boolean objetivoAlcan() {
		return campos.stream().allMatch(c -> c.objeticoAlcancado());
	}
	
	public void reiniciar() {
		campos.stream().forEach(c -> c.reiniciar());
		sortearMina();
	}
	
	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}
	
	public boolean isGanhou(Campo c){
		return c.objeticoAlcancado();
	}

	public void eventoOcorreu(Campo c, CampoEvento e) {
		if(e == CampoEvento.EXPLODIR) {
			mostraMina();
			NotificarObs(false);
		}else if(objetivoAlcan()) {
			NotificarObs(true);
		}
	}
}