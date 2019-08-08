package com.kafkaqa;

import com.kafkaqa.repository.MessageRepository;
import com.kafkaqa.repository.OnHoldMessageRepository;
import com.kafkaqa.repository.SwapDealIdentificationDataReposotory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class KafkaQAController {

    @Autowired
    KafkaQAService kafkaQAService;

    @Autowired
    SwapDealIdentificationDataReposotory swapDealIdentificationDataReposotory;

    @Autowired
    OnHoldMessageRepository onHoldMessageRepository;

    @Autowired
    MessageRepository messageRepository;

    @RequestMapping(method = RequestMethod.GET, path = "/send/{topic}")
    public ResponseEntity parseJsonObjectAndSendKafkaMessage(@PathVariable("topic") String topic) {
        switch (topic) {
            case "topic1":
                kafkaQAService.parseJsonObjectAndSendKafkaMessage(Topics.TOPIC1);
                break;
            case "topic2":
                kafkaQAService.parseJsonObjectAndSendKafkaMessage(Topics.TOPIC2);
                break;
            case "topic3":
                kafkaQAService.parseJsonObjectAndSendKafkaMessage(Topics.TOPIC3);
                break;
            default:
                return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
        }
        return ResponseEntity.ok(topic + " messages sent.");
    }

    @RequestMapping(method = RequestMethod.GET, path = "/consume/{topic}")
    public ResponseEntity getMessagesFromKafka(@PathVariable("topic") String topic) {
        switch (topic) {
            case "topic1":
                kafkaQAService.getMessagesFromKafka(Topics.TOPIC1);
                break;
            case "topic2":
                kafkaQAService.getMessagesFromKafka(Topics.TOPIC2);
                break;
            case "topic3":
                kafkaQAService.getMessagesFromKafka(Topics.TOPIC3);
                break;
            default:
                return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
        }
        return ResponseEntity.ok(topic + " consumer started.");
    }

    @RequestMapping(method = RequestMethod.GET, path = "/get/{topic}")
    public List<Object> getMessages(@PathVariable("topic") String topic) {
        switch (topic) {
            case "topic1":
                return Collections.singletonList(swapDealIdentificationDataReposotory.findAll());
            case "topic2":
                return Collections.singletonList(onHoldMessageRepository.findAll());
            case "topic3":
                return Collections.singletonList(messageRepository.findAll());
        }

        return null;
    }

}
