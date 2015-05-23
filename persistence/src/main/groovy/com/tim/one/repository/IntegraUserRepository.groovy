package com.tim.one.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

import com.tim.one.model.IntegraUser

interface IntegraUserRepository extends CrudRepository<IntegraUser, Integer> {
	IntegraUser findByIntegraUuid(@Param("integraUuid") String integraUuid)
	IntegraUser findByTimoneUuid(@Param("timoneUuid") String timoneUuid)
	IntegraUser findByStpClabe(@Param("stpClabe") String stpClabe)
}
