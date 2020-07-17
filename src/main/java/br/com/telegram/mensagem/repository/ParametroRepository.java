package br.com.telegram.mensagem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.telegram.mensagem.model.Parametro;

public interface ParametroRepository extends JpaRepository<Parametro, Long> {
	
	Parametro findParametroByCodigo(@Param("codigo") String codigo);
	
}