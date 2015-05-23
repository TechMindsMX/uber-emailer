package com.tim.one.state;

import java.math.BigDecimal;

public interface ApplicationState {

  /**
   * Time
   */
	static final BigDecimal ONEHUNDERT = new BigDecimal("100");
	static final int TIME_UNIT = 60;
  static final int HOURS_IN_DAY = 24;
  static final int MILISECONDS = 1000;
  static final String TIMEOUT = "timeout";
	
	/**
	 * Prefixes
	 */
	
	static final String UNIT_TX_PREFIX = "U";
	static final String USER_TX_PREFIX = "S";
	static final String PROVIDER_TX_PREFIX = "R";
	static final String PROJECT_TX_PREFIX = "P";
	static final String COMMA = ",";
	static final String POINT = ".";
	static final String FILE_SEPARATOR = System.getProperty("file.separator");

	/**
	 * Project Status
	 */
	static final String AUTORIZADO_STATUS = "autorizadoStatus";
	static final String PRODUCTION_STATUS = "produccionStatus";
	static final String PRESENTACION_STATUS = "presentacionStatus";
	static final String PENDIENTE_STATUS = "pendienteStatus";
	static final String CERRANDO_STATUS = "cerrandoStatus";
	static final String RECHAZADO_STATUS = "rechazadoStatus";
  static final String FINALIZADO_STATUS = "finalizadoStatus";
	
  /**
   * Project variable costs
   */
  static final String RENT_MIN = "rentMin";
  static final String RENT_MAX = "rentMax";
  static final String ISEP_MIN = "isepMin";
  static final String ISEP_MAX = "isepMax";
  static final String SACM_MIN = "sacmMin";
  static final String SACM_MAX = "sacmMax";
  static final String SOGEM_MIN = "sogemMin";
  static final String SOGEM_MAX = "sogemMax";
  static final String TICKET_SERVICE_MIN = "ticketServiceMin";
  static final String TICKET_SERVICE_MAX = "ticketServiceMax";
  
  /**
   * Project reject reasons
   */
  static final String NO_BREAKEVEN_REACHED_REASON = "noBreakevenReachedReason";
  static final String INCOMPLETE_DOCUMENTATION_REASON = "incompleteDocumentationReason";
  static final String NON_DELIVERY_PRODUCT_REASON = "nonDeliveryProductReason";
  static final String NO_BREAKEVEN_REACHED_MESSAGE = "noBreakEvenReachedMessage";
  
  /**
   * Project paths
   */
  static final String TICKETMASTER_PATH = "ticketmasterPath";
  static final String TICKETMASTER_EXTENSION = "ticketmasterExtension";
  static final String BUSINESS_CASE_PATH = "business.case.path";
  static final String BUSINESS_CASE_EXTENSION = "businessCaseExtension";
  
  /**
   * Others
   */
	static final String MATH_PRECISION = "mathPrecision";
	static final String THE_BEGGINING_OF_THE_TIMES = "theBegginingOfTheTimes";
	static final String TRANSACTION_TEXT_EXTENSION = "transactionTextExtension";
	static final String TRAMA_FEE_PERCENTAGE = "tramaFeePercentage";
	static final String KEYWORD_MIN_LENGHT = "keywordMinLength";
	static final String PRODUCER_REVENUE_PERCENTAGE = "producerRevenuePercentage";
	static final String FUNDING_END_DAYS = "fundingEndDays";
	static final String TRAMA_TRANSACTION_FEE = "tramaTransactionFee";
	static final String ADMINISTRATOR_EMAIL = "administratorEmail";
  static final String BRIDGE_TO_TRAMA_CONCEPT = "brigeToTramaConcept";
	static final String WHITE_LIST = "white.list";
	  
}
