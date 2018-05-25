package io.github.prasanna1433.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Properties;

public class KafkaPartitionConsumer {

    private static final Logger logger = Logger.getLogger(KafkaConsumerWithAutoOffsetCommit.class);

    public static void main(String[] args){


        //define the properties for the kafka consumer
        Properties consumerProperties=new Properties();
        //specify the kafka cluster that the consumer has to connect
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        //specify the consumer group name to which all the works should join
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG,"new-consumer-group-1");
        //allowing the consumer to commit their offset automatically
        consumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        //specify the interval with between offsets commits to the __consumer_offset topic
        consumerProperties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        //specify the key deserializer for the messages in the topic
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        //specify the value deserializer for the messages in the topic
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        //specify where to start the consumption in case the offset is not available for a partition in the consumer group
        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        //specify the client id to identify the consumers in kafka logs
        consumerProperties.put(ConsumerConfig.CLIENT_ID_CONFIG,"client-for-new-topic");
        //get data only from a paticular partition in a topic
        TopicPartition partition0 = new TopicPartition("new-topic", 0);

        //instantiate kafka consumer where both the key and value from the topic consumed are string
        KafkaConsumer<String,String> kafkaConsumer=new KafkaConsumer(consumerProperties);
        //provide the list of topic that the kafka consumer should listen to
        kafkaConsumer.assign(Arrays.asList(partition0));
        //infinite loop for polling the topic continuously for consumption of all the messages that are flowing in the topic
        while(true){
            ConsumerRecords<String,String> newRecords = kafkaConsumer.poll(100);
            //iterate one record at a time from the list os kafka records that are returned by the consumer
            for(ConsumerRecord record:newRecords){
                //each kafka message will have a topic name, partition number, timestamp at which that record for inserted, offset in that partition, key and value
                System.out.printf("Topic name=%s Partiton=%d Timestamp=%d Offset = %d Key=%s Value=%s \n",record.topic(),record.partition(),record.timestamp(),record.offset(),record.key(),record.value());
            }
        }
    }
}
