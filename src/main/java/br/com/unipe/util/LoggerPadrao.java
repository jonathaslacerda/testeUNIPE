package br.com.unipe.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.sentry.Sentry;
import io.sentry.SentryClient;
import io.sentry.SentryClientFactory;
import io.sentry.context.Context;
import io.sentry.event.BreadcrumbBuilder;
import io.sentry.event.UserBuilder;

public class LoggerPadrao {

	private static SentryClient sentry;

	private static Logger logErro = LoggerFactory.getLogger("erros");
	private static Logger logInfo = LoggerFactory.getLogger("informacao");
	private static Logger logInfoStartApplication = LoggerFactory.getLogger("start_application");
	private static Logger logDebug = LoggerFactory.getLogger("depuracao");
	private static Logger logTransacao = LoggerFactory.getLogger("transacao");



	public static void info(String mensagem, Object ... args){
		logInfo.info(mensagem, args);
		Sentry.capture(mensagem);
	}

	public static void info(String mensagem){
		logInfo.info(mensagem);
		Sentry.capture(mensagem);
	}

	public static void transacao(String mensagem){
		logTransacao.info("loggerTransacao - "+mensagem);
		Sentry.capture(mensagem);
	}

	public static void debug(String mensagem, Object ... args){
		logDebug.debug(mensagem, args);
		Sentry.capture(mensagem);
	}

	public static void debug(String mensagem, long time){
		String message = mensagem+ " - "+(System.currentTimeMillis()-time)+" ms";
		logDebug.debug(message);
		Sentry.capture(message);
	}


	public static void error(String mensagem, Exception e) {
		logErro.error(mensagem, e);
		Sentry.capture(e);
	}

	public static void error(String string) {
		logErro.error(string);
		Sentry.capture(string);	
	}

	public static void info(String mensagem, long time, Object ... args){
		String message = mensagem+" - "+(System.currentTimeMillis()-time)+" ms";
		logInfo.info(mensagem+" - "+(System.currentTimeMillis()-time)+" ms", args);
		Sentry.capture(message);
	}

	public static void info(String mensagem, long time){
		String message = mensagem+ " - "+(System.currentTimeMillis()-time)+" ms";
		logInfo.info(message);
		Sentry.capture(message);
	}

	public static void transacao(String mensagem, long time){
		String message = "loggerTransacao - "+mensagem+" - "+(System.currentTimeMillis()-time)+" ms";
		logTransacao.info(message);
		Sentry.capture(message);
	}

	public static void debug(String mensagem, long time, Object ... args){
		String message = mensagem+" - "+(System.currentTimeMillis()-time)+" ms";
		logDebug.debug(mensagem+" - "+(System.currentTimeMillis()-time)+" ms", args);
		Sentry.capture(message);
	}

	public static void error(String mensagem, Exception e, long time) {
		logErro.error(mensagem+" - "+(System.currentTimeMillis()-time)+" ms", e);
		Sentry.capture(mensagem+" - "+(System.currentTimeMillis()-time)+" ms");
	}

	public static void error(String string, long time) {
		String mensagem = string+" - "+(System.currentTimeMillis()-time)+" ms"; 
		logErro.error(mensagem);
		Sentry.capture(mensagem);
	}
	
	static {
		Sentry.init();
		String dsn = "https://5642587651e14d6cb68da6fbb732647d@sentry.io/1238328";
		Sentry.init(dsn);
	}

	public static void startApplication(String mensagem, Object ... args){
		logInfoStartApplication.info(mensagem, args);
		Sentry.capture(mensagem);
	}
}
