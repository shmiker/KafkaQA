## Info

  KafkaQA is a RESTful webservice for managing sending and consuming messages for certain topics in/from Kafka.


## Start service

 - Make sure that Kafka broker is started
 - Unzip to any folder
 - Open with Intellij IDEA using build.gradle file
 - Get KafkaQAapplication class started


## Send/Consume messages from Kafka

 - fulfill JSON files from src/json folder with necessary data

 - http://localhost:8080/send/{topic} - sends parsed from JSON file data to specified Kafka topic

 - http://localhost:8080/consume/{topic} - starts the consumer for specified topic.
   Consumer will close in case of not getting messages for 3 minutes #pollCounter in KafkaConsumer class.

 - http://localhost:8080/get/{topic} - gets messages from specified topic as a JSON array

   Current topics:
    topic1, topic2, topic3