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
	}

	public static void info(String mensagem){
		logInfo.info(mensagem);
	}

	public static void transacao(String mensagem){
		logTransacao.info("loggerTransacao - "+mensagem);
	}

	public static void debug(String mensagem, Object ... args){
		logDebug.debug(mensagem, args);
	}

	public static void debug(String mensagem, long time){
		logDebug.debug(mensagem+ " - "+(System.currentTimeMillis()-time)+" ms");
	}


	public static void error(String mensagem, Exception e) {
		logErro.error(mensagem, e);
	}

	public static void error(String string) {
		logErro.error(string);
	}

	public static void info(String mensagem, long time, Object ... args){
		logInfo.info(mensagem+" - "+(System.currentTimeMillis()-time)+" ms", args);
	}

	public static void info(String mensagem, long time){
		logInfo.info(mensagem+ " - "+(System.currentTimeMillis()-time)+" ms");
	}

	public static void transacao(String mensagem, long time){
		logTransacao.info("loggerTransacao - "+mensagem+" - "+(System.currentTimeMillis()-time)+" ms");
	}

	public static void debug(String mensagem, long time, Object ... args){
		logDebug.debug(mensagem+" - "+(System.currentTimeMillis()-time)+" ms", args);
	}

	public static void error(String mensagem, Exception e, long time) {
		logErro.error(mensagem+" - "+(System.currentTimeMillis()-time)+" ms", e);
	}

	public static void error(String string, long time) {
		logErro.error(string+" - "+(System.currentTimeMillis()-time)+" ms");
	}

	public static void startApplication(String mensagem, Object ... args){

		Sentry.init();
		String dsn = "https://5642587651e14d6cb68da6fbb732647d@sentry.io/1238328";
		Sentry.init(dsn);
		sentry = SentryClientFactory.sentryClient();
		LoggerPadrao lp = new LoggerPadrao();
		lp.logWithStaticAPI();
		lp.logWithInstanceAPI();

//		logInfoStartApplication.info(mensagem, args);
	}

	void logWithStaticAPI() {
		// Note that all fields set on the context are optional. Context data is copied onto
		// all future events in the current context (until the context is cleared).

		// Record a breadcrumb in the current context. By default the last 100 breadcrumbs are kept.
		Sentry.getContext().recordBreadcrumb(
				new BreadcrumbBuilder().setMessage("User made an action").build()
				);

		// Set the user in the current context.
		Sentry.getContext().setUser(
				new UserBuilder().setEmail("hello@sentry.io").build()
				);

		// Add extra data to future events in this context.
		Sentry.getContext().addExtra("extra", "thing");

		// Add an additional tag to future events in this context.
		Sentry.getContext().addTag("tagName", "tagValue");

		/*
	        This sends a simple event to Sentry using the statically stored instance
	        that was created in the ``main`` method.
		 */
		Sentry.capture("This is a test");

		try {
			unsafeMethod();
		} catch (Exception e) {
			// This sends an exception event to Sentry using the statically stored instance
			// that was created in the ``main`` method.
			Sentry.capture(e);
		}
	}

	/**
	 * Examples that use the SentryClient instance directly.
	 */
	void logWithInstanceAPI() {
		// Retrieve the current context.
		Context context = sentry.getContext();

		// Record a breadcrumb in the current context. By default the last 100 breadcrumbs are kept.
		context.recordBreadcrumb(new BreadcrumbBuilder().setMessage("User made an action").build());

		// Set the user in the current context.
		context.setUser(new UserBuilder().setEmail("hello@sentry.io").build());

		// This sends a simple event to Sentry.
		sentry.sendMessage("This is a test");

		try {
			unsafeMethod();
		} catch (Exception e) {
			// This sends an exception event to Sentry.
			sentry.sendException(e);
		}
	}
	
	void unsafeMethod() {
        throw new UnsupportedOperationException("You shouldn't call this!");
    }
}
