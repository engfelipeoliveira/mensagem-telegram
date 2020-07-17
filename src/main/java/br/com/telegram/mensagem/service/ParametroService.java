package br.com.telegram.mensagem.service;

import br.com.telegram.mensagem.model.Parametro;

public interface ParametroService {
	
	Parametro obterParametroPorCodigo(String codigo);
}
