package com.kafkaqa.repository;

import com.kafkaqa.dto.OnHoldMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OnHoldMessageRepository extends JpaRepository<OnHoldMessage, Integer> {

    OnHoldMessage saveAndFlush(OnHoldMessage onHoldMessage);

    List<OnHoldMessage> findAll();

}
