# Apache Kafka Examples

[![Build Status](https://travis-ci.org/prasanna1433/kafka-examples.svg?branch=master)](https://travis-ci.org/prasanna1433/kafka-examples)

Apache Kafka is a popular pub-sub messaging queue.

Please find the detailed documentation for Apache Kafka [here](https://kafka.apache.org/).

In particular you need to read about the [introduction](https://kafka.apache.org/intro), [design](https://kafka.apache.org/documentation/#design) and [implementation](https://kafka.apache.org/documentation/#operations) of Apache Kafka

This repository will hold the example for all the basic API's that are available in Apache Kafka

We will have example for the following

* Producer API
* Consumer API
* Streams API
* Connect API

The above are all the tools that provided by Apache Kafka for moving data into and out of Apache Kafka.

# Introduction:

In order to get started with Apache Kafka start with the quick start manual which is found [here](https://kafka.apache.org/quickstart)

Once you have done that then we can start understanding Apache Kafka concepts one by one.

First Apache Kafka is a distribute queuing system which means that message that are produced will be sent out and into the basic building block of kafka called a topic.

# Building Blocks of Apache Kafka:

* **Topic:** It is nothing but a place that is designated to receive a homogeneous set of events. In order to understand a topic think of it as a place where we store all the message and provide it to the user when he asks.

* **Partition:** It is wasteful to store all the message in the distributed systems in a single file. So in case of the kafka a topic create multiple file which is called partition and it is equal to the number of partitions. Then each of
this file is store in number of cluster that is equal to replication factor of the topic.

* **Offset:** It is the position for each message that is sent to a topic and each partition has their own offset and ordering is guaranteed only within the partition.

* **Consumer Group:** It is the identification of the workers that want to consume from a topic as a combined group. We can add multiple workers under the same consumer group and the number is bounded by the number of partitons
as we only have that many files opened at a time.


# Configuration:

In case of Producer and Consumer there are a lot of properties that you need to understand in order to control the speed of message production and consumption.  Apart from the code most of the tuning and time should be spent on getting this
configurations right.


## Critical Producer Configurations:

* **acks: This configuration controls the number of followers that leader had to wait to get acknowledgement before responding to the producer client.
* **batch.size: This configuration controls the amount of message that need to be batched before sending to the partition.
* **linget.ms: This configuration controls the time that producer should wait in case of low/moderate message production where reaching batch.size take more time. This configuration places an upper bound of the amount of time the producer client
need to wait before sending messages. Nagle's Algorithm  is the base for this configuration.
* **retires: If there is a failure in sending the message to a topic producer client will retry the specified number of time before giving up. If we want to preserve the ordering we need to set max.inflight.requests.per.connection to 1
* **compression.type: We can use this configuration to control the size of the message that is produced.

Official definition and exhaustive list of producer configuration are [here](https://kafka.apache.org/documentation/#producerconfigs)

## Critical Consumer configuration:

* **group.id: This is the consumer Group name that the workers will use to subscribe to a particular topic. It should be unique for each consumer in the cluster.
* **session.timeout.ms: This is very important configuration that control the interval that the group coordinator should wait to hear back from the heart beat thread before initiating rebalancing the consumer group.
* **auto.offset.reset: During the start up of a consumer group or if the consumers are not able to retrieve their offset it uses this configuration to decide where it should start reading from in the topic.
* **max.poll.interval.ms: This configuration controls that amount of time that will take to do a poll for records or in other words the amount of time taken to process the fetched number of message by the consumer client.

Official definition and exhaustive list of consumer configuration are [here](https://kafka.apache.org/documentation/#newconsumerconfigs)

Also read about this interesting thread about [session.timeout.ms and max.poll.interval.ms](https://stackoverflow.com/questions/39730126/difference-between-session-timeout-ms-and-max-poll-interval-ms-for-kafka-0-10-0)
