package com.kafkaqa;

public enum Topics {

    TOPIC1(ApplicationContextUtils.getEnvironmentProperty("topic1.topic")),
    TOPIC2(ApplicationContextUtils.getEnvironmentProperty("topic2.topic")),
    TOPIC3(ApplicationContextUtils.getEnvironmentProperty("topic3.topic"));

    private final String topic;

    Topics(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return this.topic;
    }
}
