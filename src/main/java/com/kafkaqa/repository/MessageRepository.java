package com.kafkaqa.repository;

import com.kafkaqa.dto.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    Message saveAndFlush(Message message);

    List<Message> findAll();
}
