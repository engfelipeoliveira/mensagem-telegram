package br.com.telegram.mensagem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.telegram.mensagem.model.Mensagem;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
	
}