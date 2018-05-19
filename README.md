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

**Topic:** It is nothing but a filesystems that is designated to receive a homogeneous set of events. In order to understand a topic think of it as a place where we store all the message and provide it to the user when he asks.

**Partition:** It is wasteful to store all the message in the distributed systems in a single file. So In case of the kafka a topic create multiple file that is equal to the number of partitions and store them in number of cluster
equal to replication factor of the topic.

**Offset:** It is the position for each message that is sent to a topic and each partition has their own offset and ordering is guaranteed only within the partition.

**Consumer Group:** It is the identification of the workers that want to consume from a topic as a combined group. We can add multiple workers under the same consumer group and the number is bounded by the number of partitons
as we only have that many files opened at a time.





