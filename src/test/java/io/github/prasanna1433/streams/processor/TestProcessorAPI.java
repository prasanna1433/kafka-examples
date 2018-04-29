package io.github.prasanna1433.streams.processor;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.TopologyTestDriver;
import org.apache.kafka.streams.test.ConsumerRecordFactory;
import org.apache.kafka.streams.test.OutputVerifier;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

public class TestProcessorAPI {
    private TopologyTestDriver testDriver;
    private StringDeserializer stringDeserializer = new StringDeserializer();
    private ConsumerRecordFactory<String, String> recordFactory = new ConsumerRecordFactory<>(new StringSerializer(), new StringSerializer());


    @Before
    public void setup() {
        //Configuration properties for Kafka Streams Application
        Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,"bootstrapServer");
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG,"applicationId");
        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.BUFFERED_RECORDS_PER_PARTITION_CONFIG,200);

        //build the topology for Kafka Streams
        Topology builder = new Topology();

        // add the source processor node that takes Kafka topic "source-topic" as input
        builder.addSource("Source", "source-topic")

                // add the IntermediateProcessor node which takes the source processor as its upstream processor
                .addProcessor("Process", () -> new IntermediateProcess(), "Source")

                // add the sink processor node that takes Kafka topic "sink-topic" as output
                // and the IntermediateProcessor node as its upstream processor
                .addSink("Sink", "sink-topic", "Process");
        testDriver=new TopologyTestDriver(builder,streamsConfiguration);
    }

    @Test
    public void testTopology(){
        testDriver.pipeInput(recordFactory.create("source-topic", "a", "b", 9999L));
        ProducerRecord<String,String> producerRecord = testDriver.readOutput("sink-topic", stringDeserializer, stringDeserializer);
        System.out.println(new StringBuilder().append("Key = ").append(producerRecord.key()).append(" Value = ").append(producerRecord.value()));
        OutputVerifier.compareKeyValue(producerRecord, "a", "b");
    }

}
