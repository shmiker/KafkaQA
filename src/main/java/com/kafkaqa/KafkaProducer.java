package com.kafkaqa;

import com.google.gson.Gson;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.lang.Nullable;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class KafkaProducer<K, V> {

    private static final Logger log = LogManager.getLogger(KafkaProducer.class);
    private static final Gson gson = new Gson();
    private KafkaTemplate<String, String> kafkaTemplate;
    private DefaultKafkaProducerFactory<String, String> producerFactory;

    private DefaultKafkaProducerFactory<String, String> producerFactory(String truststoreLocation, String keystoreLocation) throws UnknownHostException {
        producerFactory = new DefaultKafkaProducerFactory<>(producerConfigs(truststoreLocation, keystoreLocation));
        return producerFactory;
    }

    private Map<String, Object> producerConfigs(String truststoreLocation, String keystoreLocation) throws UnknownHostException {

        Map<String, Object> props = new HashMap<>();
        props.put("bootstrap.servers", ApplicationContextUtils.getEnvironmentProperty("bootstrap.servers"));
        props.put("client.id", InetAddress.getLocalHost().getHostName());
        props.put("retries", 3);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", StringSerializer.class);
        props.put("value.serializer", StringSerializer.class);
        props.put("acks", "all");

//        props.put("security.protocol", ApplicationContextUtils.getEnvironmentProperty("security.protocol"));
//        props.put("ssl.endpoint.identification.algorithm", "");
//        props.put("ssl.truststore.password", ApplicationContextUtils.getEnvironmentProperty("ssl.truststore.password"));
//        props.put("ssl.keystore.password", ApplicationContextUtils.getEnvironmentProperty("ssl.keystore.password"));
//        props.put("ssl.key.password", ApplicationContextUtils.getEnvironmentProperty("ssl.key.password"));
//        props.put("ssl.truststore.location", truststoreLocation);
//        props.put("ssl.keystore.location", keystoreLocation);

        return props;
    }

    public void sendMessage(K key, V value, String topic) {
        String messageToJSON = gson.toJson(value, value.getClass());
        //        ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send(topic, 0, (String) key, messageToJSON);
        ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send(topic, (String) key, messageToJSON);
        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("{} object was unable to be sent via Kafka message {} because of {} topic: {}", value.getClass().getName(), gson.toJson(messageToJSON, messageToJSON.getClass()), ex.getMessage(), topic);
            }

            @Override
            public void onSuccess(@Nullable SendResult<String, String> result) {
                log.info("{} object is sent via Kafka message {} with offset of: {} partition: {}  topic: {}", value.getClass().getName(), gson.toJson(messageToJSON, messageToJSON.getClass()), result.getRecordMetadata().offset(), result.getRecordMetadata().partition(), topic);
            }
        });

    }

    public void startProducer(Topics topic) throws UnknownHostException {
        switch (topic) {
            case TOPIC1:
                kafkaTemplate = new KafkaTemplate<>(producerFactory(
                        ApplicationContextUtils.getEnvironmentProperty("topic1.ssl.truststore.producer"),
                        ApplicationContextUtils.getEnvironmentProperty("topic1.ssl.keystore.producer")));
                break;
            case TOPIC2:
                kafkaTemplate = new KafkaTemplate<>(producerFactory(
                        ApplicationContextUtils.getEnvironmentProperty("topic2.ssl.truststore.producer"),
                        ApplicationContextUtils.getEnvironmentProperty("topic2.ssl.keystore.producer")));
                break;
            case TOPIC3:
                kafkaTemplate = new KafkaTemplate<>(producerFactory(
                        ApplicationContextUtils.getEnvironmentProperty("topic3.ssl.truststore.producer"),
                        ApplicationContextUtils.getEnvironmentProperty("topic3.ssl.keystore.producer")));
                break;
        }

    }

    public void destroy() throws Exception {
        producerFactory.destroy();
    }
}
