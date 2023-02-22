package br.com.cod3r.cm.modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import br.com.cod3r.cm.visao.CampoObservador;



public class Tabuleiro implements CampoObservador{
	private final int linhas;
	private final int colunas;
	private final int minas;
	
	
	private final List<Campo>campos = new ArrayList<>();
	private final List<Consumer<Boolean>> observadores = 
			new ArrayList<>();
	

	public Tabuleiro(int linhas, int colunas, int minas) {
		
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
		
		gerarCampos();
		associarVizinhos();
		sortearMinas();
	}
	public void paraCadaCampo(Consumer<Campo> funcao) {
		campos.forEach(funcao);
		
	}
	
	public int getLinhas() {
		return linhas;
	}

	
	public int getColunas() {
		return colunas;
	}

	

	public void registrarObservador (Consumer<Boolean> observador) {
		observadores.add(observador);
	}

private void notificarObservadores(boolean resultado){
		
		observadores.stream()
		.forEach(o -> o.accept(resultado));
	} 

	public void abrir (int linha , int coluna) {
		
		try {
			campos.parallelStream().
			filter(c ->c.getLinha() == linha && c.getColuna() == coluna)
			.findFirst()
			.ifPresent(c -> c.abri());
			
		}catch(Exception e){
			// FIXME 
			campos.forEach(c -> c.setAberto(true));
			throw e;
		}	
	}
	

public void alterarMarcacao(int linhas, int colunas) {
	campos.parallelStream()
	.filter(c -> c.getLinha() == linhas && c.getColuna() == colunas).findFirst()
	.ifPresent(c -> c.alternarMarcacao());

	
	
	
}

	private void gerarCampos() {
		for (int l = 0; l < linhas; l++) {
			for (int c = 0; c < colunas; c++) {
				Campo campo = new Campo(l, c);
				campo.registrarObservador(this);
				campos.add(campo);
				
			}
			
		}
		
	}
	private void associarVizinhos() {
		for (Campo c1 : campos) {
		for(Campo c2 : campos) {
		c1.adicionarVizinho(c2);
		}
		}
		}
		
	
	private void sortearMinas() {
		long minasArmadas = 0;
		Predicate<Campo> mina = c -> c.isMinado();
		do {
			
			
			
			int aleatorio = (int)(Math.random()* campos.size());
			campos.get(aleatorio).minar();
			minasArmadas =	campos.stream().filter(mina).count();
		}while(minasArmadas < minas);
		
	}
	
	public boolean objetivoAlcancado () {
		return 
				campos.stream().allMatch(c -> c.objetivoAlcancado());
	}
	public void reiniciar() {
		campos.stream().forEach(C -> C.reinicia());
		sortearMinas();
	}
	
@Override
public void eventoOcorreu(Campo campo, CampoEvento evento) {
	if (evento == CampoEvento.EXPLODIR) {
		mostrarMinas();
		notificarObservadores(false);
		
	}else if(objetivoAlcancado()){
	notificarObservadores(true);
	}
	
	
}
private void mostrarMinas() {
	campos.stream()
	.filter(c -> c.isMinado())
	.filter(c -> !c.isMarcdo())
	.forEach(c -> c.setAberto(true));
}

}
	

