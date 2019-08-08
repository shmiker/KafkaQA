package com.kafkaqa;

import com.google.gson.Gson;
import com.kafkaqa.dto.Message;
import com.kafkaqa.dto.OnHoldMessage;
import com.kafkaqa.dto.SwapDealIdentificationData;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;

@Service
public class KafkaQAService {

    KafkaConsumer kafkaConsumer;

    public void parseJsonObjectAndSendKafkaMessage(Topics topic) {
        JSONParser jsonParser = new JSONParser();
        Gson gson = new Gson();
        KafkaProducer messagingService = new KafkaProducer();
        switch (topic) {
            case TOPIC1: {
                try (FileReader reader = new FileReader(".\\src\\main\\json\\SwapDealIdentificationData.json")) {
                    for (Object object : parseAndStartProducer(reader, jsonParser, messagingService, topic)) {
                        SwapDealIdentificationData swapDealIdentificationData = gson.fromJson(object.toString(), SwapDealIdentificationData.class);
                        messagingService.sendMessage("SwapDealIdentificationData", swapDealIdentificationData, topic.getTopic());
                    }
                    messagingService.destroy();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }

            case TOPIC2: {
                try (FileReader reader = new FileReader(".\\src\\main\\json\\OnHoldMessage.json")) {
                    for (Object object : parseAndStartProducer(reader, jsonParser, messagingService, topic)) {
                        OnHoldMessage onHoldMessage = gson.fromJson(object.toString(), OnHoldMessage.class);
                        messagingService.sendMessage("OnHoldMessage", onHoldMessage, topic.getTopic());
                    }
                    messagingService.destroy();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            break;

            case TOPIC3: {
                try (FileReader reader = new FileReader(".\\src\\main\\json\\Message.json")) {
                    for (Object object : parseAndStartProducer(reader, jsonParser, messagingService, topic)) {
                        Message paymentMessage = gson.fromJson(object.toString(), Message.class);
                        messagingService.sendMessage("Message", paymentMessage, topic.getTopic());
                    }
                    messagingService.destroy();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    private static JSONArray parseAndStartProducer(FileReader reader, JSONParser jsonParser, KafkaProducer messagingService, Topics topic) throws IOException, ParseException {
        Object obj = jsonParser.parse(reader);
        JSONArray readObject = (JSONArray) obj;
        System.out.println(readObject);
        messagingService.startProducer(topic);
        return readObject;
    }

    public void getMessagesFromKafka(Topics topic) {
        kafkaConsumer = new KafkaConsumer(topic);
    }
}
