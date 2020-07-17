package br.com.telegram.mensagem.service;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.SendResponse;

import br.com.telegram.mensagem.model.Mensagem;
import br.com.telegram.mensagem.repository.MensagemRepository;

@Service
public class MensagemServiceImpl implements MensagemService {

	private static final Logger LOG = getLogger(MensagemServiceImpl.class);

	/*
	 * obter atualizacoes do bot
	 * https://api.telegram.org/bot1371039721:AAHU4WBOdFPaQ3jXunlNyy6TAdVL6UWyavA/
	 * getUpdates
	 */

	private static final String MSG_PARAMETRO_OBRIGATORIO = "'%s' e obrigatorio";
	private static final String MSG_ENVIADA = "Mensagem enviada com sucesso!";
	private static final String MSG_NAO_ENVIADA = "Mensagem nao enviada !";
	private static final String MSG_ERRO_RETORNO = "Mensagem de erro Telegram: '%s'";
	private static final String EXT_FOTO = ".jpg";

	private TelegramBot telegramBot;
	
	@Autowired
	private MensagemRepository mensagemRepository;
	
	@Autowired
	private ParametroService parametroService;
	
	@Override
	@Scheduled(fixedDelay = 20000)
	public void enviarMensagem() {
		List<Mensagem> listaMensagens = this.listarMensagem();
		List<String> listaGrupos = this.listarGruposId();
		
		LOG.info(String.format("Total de '%s' mensagens a serem enviadas aos grupos '%s'", listaMensagens.size(), listaGrupos));
		
		telegramBot = new TelegramBot(parametroService.obterParametroPorCodigo("telegram.bot.token").getValor());

		listaMensagens.stream().forEach(mensagem -> {
			listaGrupos.stream().forEach(grupoId -> {
				enviarMensagemFoto(grupoId, new File("c://tmp//" + mensagem.getFoto() + EXT_FOTO), mensagem.getTexto());
				//enviarMensagemTexto(grupoId, mensagem.getTexto());
			});
			mensagem.setPublicadoTelegram(1L);
			mensagemRepository.save(mensagem);
		});
	}
	
	@Override
	public void enviarMensagemTexto(String grupoId, String mensagemTexto) {
		Preconditions.checkNotNull(grupoId, String.format(MSG_PARAMETRO_OBRIGATORIO, "grupoId"));
		Preconditions.checkNotNull(mensagemTexto, String.format(MSG_PARAMETRO_OBRIGATORIO, "mensagemTexto"));

		LOG.info(String.format("Envio de mensagem de texto '%s' ao grupo '%s'", mensagemTexto, grupoId));

		mensagemTexto = mensagemTexto.replaceAll("aaaa", "bbb");
		
		SendMessage sendMessage = new SendMessage(grupoId, mensagemTexto);
		sendMessage.disableWebPagePreview(true);
		SendResponse sendResponse = telegramBot.execute(sendMessage);

		if (sendResponse.errorCode() != 0) {
			LOG.error(MSG_NAO_ENVIADA);
			LOG.error(String.format(MSG_ERRO_RETORNO, sendResponse.description()));
		} else {
			LOG.info(MSG_ENVIADA);
		}
	}

	@Override
	public void enviarMensagemFoto(String grupoId, File foto, String mensagemTexto) {
		Preconditions.checkNotNull(grupoId, String.format(MSG_PARAMETRO_OBRIGATORIO, "grupoId"));
		Preconditions.checkNotNull(foto, String.format(MSG_PARAMETRO_OBRIGATORIO, "foto"));

		LOG.info(String.format("Envio de foto '%s' ao grupo '%s'", foto.getName(), grupoId));

		SendPhoto sendPhoto = new SendPhoto(grupoId, foto);
		sendPhoto.caption(mensagemTexto);
		SendResponse sendResponse = telegramBot.execute(sendPhoto);

		if (sendResponse.errorCode() != 0) {
			LOG.error(MSG_NAO_ENVIADA);
			LOG.error(String.format(MSG_ERRO_RETORNO, sendResponse.description()));
		} else {
			LOG.info(MSG_ENVIADA);
		}

	}

	@Override
	public List<Mensagem> listarMensagem() {
		return this.mensagemRepository.findAll().stream().filter(mensagem -> mensagem.getPublicadoTelegram() != null ).collect(Collectors.toList());
	}
	
	private List<String> listarGruposId() {
		return Arrays.asList(parametroService.obterParametroPorCodigo("telegram.grupos.id").getValor().split("\\s*,\\s*"));
	}
	
	

}
