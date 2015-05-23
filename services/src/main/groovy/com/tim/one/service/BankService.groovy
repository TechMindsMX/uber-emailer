package com.tim.one.service

import com.tim.one.model.TramaAccount

interface BankService {

  Iterable<TramaAccount> getAdministrativeAccounts()

}
