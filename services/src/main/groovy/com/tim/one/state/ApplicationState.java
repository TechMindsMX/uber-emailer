package com.tim.one.state;

public interface ApplicationState {
	static final String WHITE_LIST = "white.list";
	static final String HOST = "http://localhost:8080/cien-hundreds/";
	static final String FORGOT_PASSWORD_PREFIX = HOST + "recovery/show?token=";
	static final String REGISTER_PREFIX = HOST + "recovery/confirm?token=";
	static final String ADMIN_EMAIL = "joseluis.delacruz@gmail.com";
}
