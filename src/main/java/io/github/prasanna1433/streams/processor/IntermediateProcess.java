package io.github.prasanna1433.streams.processor;

import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;

public class IntermediateProcess implements Processor<String,String> {

    private ProcessorContext context;

    public void init(ProcessorContext context) {
        this.context = context;
    }

    public void process(String key, String value) {
        context.forward(key,value);
        context.commit();
    }

    public void punctuate(long timestamp) {

    }

    public void close() {

    }

}
