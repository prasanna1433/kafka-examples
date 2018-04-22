package io.github.prasanna1433.streams.processor;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;

import java.util.Properties;

public class ProcessorAPI {
    public static void main(String[] args){

        if(args.length<2){
            System.out.println("Please send the input arguments <boostrapServer> <applicationId>");
        }

        String bootstrapServer = args[0];
        String applicationId= args[1];

        Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServer);
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG,applicationId);
        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        Topology builder = new Topology();

        // add the source processor node that takes Kafka topic "source-topic" as input
        builder.addSource("Source", "source-topic")

                // add the WordCountProcessor node which takes the source processor as its upstream processor
                .addProcessor("Process", () -> new IntermediateProcess(), "Source")

                // add the sink processor node that takes Kafka topic "sink-topic" as output
                // and the WordCountProcessor node as its upstream processor
                .addSink("Sink", "sink-topic", "Process");

        KafkaStreams kafkaStreams=new KafkaStreams(builder,streamsConfiguration);
        kafkaStreams.start();

    }
}
