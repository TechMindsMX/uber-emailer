package com.tim.one.common.state;

public interface ApplicationState {
  /**
   * Time
   */
  static final int TIME_UNIT = 60;
	static final int HOURS_IN_DAY = 24;
	static final int MILISECONDS = 1000;
	static final String TIMEOUT = "timeout";
	
	/**
   * Project Status
   */
	static final String AUTORIZADO_STATUS = "autorizadoStatus";
	static final String PENDIENTE_STATUS = "pendienteStatus";
	static final String PRODUCTION_STATUS = "produccionStatus";
	static final String PRESENTACION_STATUS = "presentacionStatus";
	static final String RECHAZADO_STATUS = "rechazadoStatus";
	
	/**
   * Project reject reasons
   */
	static final String NO_BREAKEVEN_REACHED_REASON = "noBreakevenReachedReason";
	static final String NO_BREAKEVEN_REACHED_MESSAGE = "noBreakEvenReachedMessage";

	/**
   * Others
   */
	static final String ADMINISTRATOR_EMAIL = "administratorEmail";
	static final String BRIDGE_TO_TRAMA_CONCEPT = "brigeToTramaConcept";
}
