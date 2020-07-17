package br.com.telegram.mensagem.service;

import java.io.File;
import java.util.List;

import br.com.telegram.mensagem.model.Mensagem;

public interface MensagemService {
	
	void enviarMensagem();
	void enviarMensagemTexto(String grupoId, String mensagemTexto);
	void enviarMensagemFoto(String grupoId, File foto, String mensagemTexto);
	List<Mensagem> listarMensagem();
}
