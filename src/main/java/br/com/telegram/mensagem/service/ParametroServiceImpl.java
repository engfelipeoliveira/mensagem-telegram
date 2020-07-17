package br.com.telegram.mensagem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.telegram.mensagem.model.Parametro;
import br.com.telegram.mensagem.repository.ParametroRepository;

@Service
public class ParametroServiceImpl implements ParametroService {

	private static final String MSG_PARAMETRO_OBRIGATORIO = "'%s' e obrigatorio";

	@Autowired
	private ParametroRepository parametroRepository;
	
	@Override
	public Parametro obterParametroPorCodigo(String codigo) {
		Preconditions.checkNotNull(codigo, String.format(MSG_PARAMETRO_OBRIGATORIO, "codigo"));
		return parametroRepository.findParametroByCodigo(codigo);
	}

}
