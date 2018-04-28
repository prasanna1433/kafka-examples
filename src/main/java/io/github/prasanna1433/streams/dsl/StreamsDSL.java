package io.github.prasanna1433.streams.dsl;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Arrays;
import java.util.Properties;

public class StreamsDSL {
    public static void main(String[] args){
        if(args.length < 2){
            System.out.println("Please send the input arguments <boostrapServer> <applicationId>");
        }

        String bootstrapServer = args[0];
        String applicationId= args[1];

        //Configuration properties for Kafka Streams Application
        Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServer);
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG,applicationId);
        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.BUFFERED_RECORDS_PER_PARTITION_CONFIG,200L);

        //build kafka streams application using streams DSL
        StreamsBuilder streamsBuilder=new StreamsBuilder();
        KStream<String,String> source=streamsBuilder.stream("source-topic");
        KStream<String, String> words = source.flatMapValues(value -> Arrays.asList(value.split("\\W+")));
        words.to("sink-topic");

        //build topology using StreamsBuilder
        Topology topology = streamsBuilder.build();

        //print the topology
        System.out.println(topology.describe());

        //define and start the kafka streams application
        KafkaStreams kafkaStreams = new KafkaStreams(topology,streamsConfiguration);
        kafkaStreams.start();

    }
}
