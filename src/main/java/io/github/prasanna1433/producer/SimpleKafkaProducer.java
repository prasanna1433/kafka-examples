package io.github.prasanna1433.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class SimpleKafkaProducer {
    public static void main(String[] args){

        //defines the properties for the kafka producer
        Properties producerProperties=new Properties();
        //specify the kafka cluster that producer has to connect
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        //specify the number of acknowledgement that leader have to receive from it followers before acknowledging a received record
        producerProperties.put(ProducerConfig.ACKS_CONFIG,"all");
        //In case if an error occurs when sending the record the producer will try this specified number of time
        producerProperties.put(ProducerConfig.RETRIES_CONFIG,10);
        //Since we have allowed retired we need to set this property to 1 inorder to guarantee the delivery order
        producerProperties.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION,1);
        //buffer size that is available to batch the records before sending them to the topic
        producerProperties.put(ProducerConfig.BATCH_SIZE_CONFIG,16384);
        //the producer will wait this time period before sending the message to the topic in case the batch.size is not reached
        producerProperties.put(ProducerConfig.LINGER_MS_CONFIG,1000);
        //total bytes that is allocate to the kafka producer
        producerProperties.put(ProducerConfig.BUFFER_MEMORY_CONFIG,33554432);
        //specify the compression type that needs to be applied to the batch that is sent to a topic
        producerProperties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG,"gzip");
        //serialization that should be used for the key that is produced to a topic
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        //serialization that should be used for the value that is produced to a topic
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");

        //instantiate the kafka producer with teh above mentioned properties
        Producer<String,String> producer=new KafkaProducer<String, String>(producerProperties);

        for(int i=0;i<100;i++){
            producer.send(new ProducerRecord<>("new-topic",Integer.toString(i%100),new StringBuilder().append("message number").append(i).toString()));
        }

        //properly close the produce so that there is no memory leaks
        producer.close();
    }
}
