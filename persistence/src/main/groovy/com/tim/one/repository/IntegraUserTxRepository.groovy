package com.tim.one.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

import com.tim.one.model.IntegraUserTx

interface IntegraUserTxRepository extends CrudRepository<IntegraUserTx, Integer> {
	
	IntegraUserTx findByUuid(@Param("uuid") String uuid)

	@Query("SELECT tx FROM IntegraUserTx tx WHERE tx.origin=:uuid OR tx.destination=:uuid")
	List<IntegraUserTx> findTransactionsByUuid(@Param("uuid") String uuid)
	
	@Query("SELECT tx FROM IntegraUserTx tx WHERE (tx.origin=:uuid OR tx.destination=:uuid) AND tx.timestamp BETWEEN :startDate AND :endDate")
	List<IntegraUserTx> findTransactionsByUuidAndDate(@Param("uuid") String uuid,  @Param("startDate") Long startDate, @Param("endDate") Long endDate)
	
}
