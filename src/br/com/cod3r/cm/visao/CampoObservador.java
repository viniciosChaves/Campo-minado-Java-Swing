package br.com.cod3r.cm.visao;

import br.com.cod3r.cm.modelo.Campo;
import br.com.cod3r.cm.modelo.CampoEvento;

public interface CampoObservador {
	
	public void eventoOcorreu(Campo campo,CampoEvento eveneto);

}
