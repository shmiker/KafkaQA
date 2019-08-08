package com.kafkaqa;

import com.google.gson.Gson;
import com.kafkaqa.dto.Message;
import com.kafkaqa.dto.OnHoldMessage;
import com.kafkaqa.dto.SwapDealIdentificationData;
import com.kafkaqa.repository.MessageRepository;
import com.kafkaqa.repository.OnHoldMessageRepository;
import com.kafkaqa.repository.SwapDealIdentificationDataReposotory;
import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);
    private final org.apache.kafka.clients.consumer.KafkaConsumer consumer;
    private final AtomicBoolean terminated = new AtomicBoolean(false);
    private Gson gson = new Gson();
    private Topics topic;

    public KafkaConsumer(Topics paramTopic) {
        consumer = createKafkaConsumer(paramTopic);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(this::start);
        topic = paramTopic;
        synchronized (consumer) {
            consumer.subscribe(Collections.singletonList(topic.getTopic()));
        }
    }

    protected void start() {
        int pollCounter = 0;
        while (isNotTerminated()) {
            LOGGER.info("Waiting for messages");
            ConsumerRecords<String, String> records;
            synchronized (consumer) {
                records = consumer.poll(30000);
            }
            if (!records.isEmpty()) {
                for (ConsumerRecord<String, String> record : records) {
                    switch (topic) {
                        case TOPIC1:
                            SwapDealIdentificationData swapDealIdentificationData = gson.fromJson(record.value(), SwapDealIdentificationData.class);
                            LOGGER.info(swapDealIdentificationData.toString());
                            ApplicationContextUtils.getBean(SwapDealIdentificationDataReposotory.class).saveAndFlush(swapDealIdentificationData);
                            break;
                        case TOPIC2:
                            OnHoldMessage onHoldMessage = gson.fromJson(record.value(), OnHoldMessage.class);
                            LOGGER.info(onHoldMessage.toString());
                            ApplicationContextUtils.getBean(OnHoldMessageRepository.class).saveAndFlush(onHoldMessage);
                            break;
                        case TOPIC3:
                            Message message = gson.fromJson(record.value(), Message.class);
                            LOGGER.info(message.toString());
                            ApplicationContextUtils.getBean(MessageRepository.class).saveAndFlush(message);
                            break;
                    }
                    try {
                        LOGGER.info("Received message from topic: {} | object: {} | offset: {}", topic.getTopic(), record.value(), record.offset());
                    }
                    catch (Exception e) {
                        LOGGER.info("Exception while handling a swap deal notification message: " + record);
                    }
                    try {
                        consumer.commitSync();
                    }
                    catch (CommitFailedException e) {
                        LOGGER.error("Could  not commit sync with Kafka server", e);
                    }
                }
            }
            else {
                pollCounter++;
                if (pollCounter == 6) {
                    LOGGER.info("Closing consumer for topic: {}", topic);
                    synchronized (consumer) {
                        shutdown();
                        consumer.close();
                    }
                }
            }
        }

    }

    org.apache.kafka.clients.consumer.KafkaConsumer createKafkaConsumer(Topics topic) {
        Properties props = new Properties();
        props.put("bootstrap.servers", ApplicationContextUtils.getEnvironmentProperty("bootstrap.servers"));
        props.put("enable.auto.commit", ApplicationContextUtils.getEnvironmentProperty("enable.auto.commit"));
        props.put("auto.commit.interval.ms", ApplicationContextUtils.getEnvironmentProperty("auto.commit.interval.ms"));
        props.put("auto.offset.reset", ApplicationContextUtils.getEnvironmentProperty("auto.offset.reset"));
        props.put("session.timeout.ms", ApplicationContextUtils.getEnvironmentProperty("session.timeout.ms"));
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        //        props.put("security.protocol", ApplicationContextUtils.getEnvironmentProperty("security.protocol"));
        //        props.put("ssl.endpoint.identification.algorithm", "");
        //        props.put("ssl.truststore.password", ApplicationContextUtils.getEnvironmentProperty("ssl.truststore.password"));
        //        props.put("ssl.keystore.password", ApplicationContextUtils.getEnvironmentProperty("ssl.keystore.password"));
        //        props.put("ssl.key.password", ApplicationContextUtils.getEnvironmentProperty("ssl.keystore.password"));

        switch (topic) {
            case TOPIC1:
                props.put("group.id", ApplicationContextUtils.getEnvironmentProperty("topic1.group.id"));
                //                props.put("ssl.truststore.location", ApplicationContextUtils.getEnvironmentProperty("topic1.ssl.truststore.consumer"));
                //                props.put("ssl.keystore.location", ApplicationContextUtils.getEnvironmentProperty("topic1.ssl.keystore.consumer"));
                break;
            case TOPIC2:
                props.put("group.id", ApplicationContextUtils.getEnvironmentProperty("topic2.group.id"));
                //                props.put("ssl.truststore.location", ApplicationContextUtils.getEnvironmentProperty("topic2.ssl.truststore.consumer"));
                //                props.put("ssl.keystore.location", ApplicationContextUtils.getEnvironmentProperty("topic2.ssl.keystore.consumer"));
                break;
            case TOPIC3:
                props.put("group.id", ApplicationContextUtils.getEnvironmentProperty("topic3.group.id"));
                //                props.put("ssl.truststore.location", ApplicationContextUtils.getEnvironmentProperty("topic3.ssl.truststore.consumer"));
                //                props.put("ssl.keystore.location", ApplicationContextUtils.getEnvironmentProperty("topic3.ssl.keystore.consumer"));
                break;
        }

        return new org.apache.kafka.clients.consumer.KafkaConsumer(props);
    }

    boolean isNotTerminated() {
        return !terminated.get();
    }

    void shutdown(){
        terminated.set(true);
    }

}
