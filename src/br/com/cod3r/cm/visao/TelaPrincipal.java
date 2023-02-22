package br.com.cod3r.cm.visao;

import javax.swing.JFrame;

import br.com.cod3r.cm.modelo.Tabuleiro;

@SuppressWarnings("serial")
public class TelaPrincipal extends JFrame {
	public TelaPrincipal() {
		Tabuleiro tabuleiro = new Tabuleiro(16,30,50);
	
		add(new PainelTabuleiro(tabuleiro));
		setTitle("Campo Minado");
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(690,438);
	}
	public static void main(String[] args) {
		new TelaPrincipal();
		
		
	}
	
	
}
