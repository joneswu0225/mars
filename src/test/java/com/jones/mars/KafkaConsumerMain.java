/*
package com.jones.mars;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.*;

public class KafkaConsumerMain {

    */
/**
     * 自动提交offset
     *//*

    public void commitAuto(String topic) throws Exception {
//        Properties props = KafkaUtils.getProperties("cosumer");
//        props.put("enable.auto.commit", "true");
//        props.put("auto.commit.interval.ms", "1000");
        Map<String, Object> props = new HashMap<>();
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("bootstrap.servers", "10.24.21.152:9092");
        props.put("group.id", "test222");
        props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset","earliest");
        props.put("partition.assignment.strategy","range");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(topic);
        while (true) {
            Map<String, ConsumerRecords<String, String>> records = consumer.poll(1000);
            for (ConsumerRecords<String, String> record : records.values())
                for(ConsumerRecord<String, String> record1: record.records(0))
                    System.err.printf("offset = %d, key = %s, value = %s%n", record1.offset(), record1.key(), record1.value());
        }
    }

    */
/**
     * 手动提交offset
     *
     * @throws FileNotFoundException
     *//*

//    public void commitControl(List<String> topics) throws FileNotFoundException {
//        Properties props = KafkaUtils.getProperties("cosumer");
//        props.put("enable.auto.commit", "false");
//
//        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
//        consumer.subscribe(topics);
//        final int minBatchSize = 2;
//        List<ConsumerRecord<String, String>> buffer = new ArrayList<>();
//        while (true) {
//            ConsumerRecords<String, String> records = consumer.poll(1000);
//            for (ConsumerRecord<String, String> record : records) {
//                buffer.add(record);
//            }
//            if (buffer.size() >= minBatchSize) {
//                insertIntoDb(buffer);
//                // 阻塞同步提交
//                consumer.commitSync();
//                buffer.clear();
//            }
//        }
//    }

    */
/**
     * 手动设置分区
     *//*

//    public void setOffSet(List<String> topics) throws FileNotFoundException {
//        Properties props = KafkaUtils.getProperties("cosumer");
//        props.put("enable.auto.commit", "false");
//
//        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
//        consumer.subscribe(topics);
//
//        while (true) {
//            ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
//            // 处理每个分区消息后, 提交偏移量
//            for (TopicPartition partition : records.partitions()) {
//                List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);
//
//                for (ConsumerRecord<String, String> record : partitionRecords) {
//                    System.out.println(record.offset() + ": " + record.value());
//                }
//                long lastOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
//                consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastOffset + 1)));
//            }
//        }
//    }

    */
/**
     * 手动设置消息offset
     *//*

//    public void setSeek(List<String> topics) throws FileNotFoundException {
//        Properties props = KafkaUtils.getProperties("cosumer");
//        props.put("enable.auto.commit", "false");
//
//        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
//        consumer.subscribe(topics);
//        consumer.seek(new TopicPartition("http_zhixin", 0), 797670770);
//        ConsumerRecords<String, String> records = consumer.poll(100);
//
//        for (ConsumerRecord<String, String> record : records) {
//            System.err.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
//            consumer.commitSync();
//        }
//
//    }

//    @Test
    public void test() throws Exception {
        ArrayList<String> topics = new ArrayList<>();
        topics.add("http_zhixin");

        commitAuto("applog-15-QA");
//        commitControl(topics);
//        setOffSet(topics);
//        setSeek(topics);
    }

    */
/**
     * doSomethings
     *//*

//    private void insertIntoDb(List<ConsumerRecord<String, String>> buffer) {
//        buffer.stream().map(x -> x.value()).forEach(System.err::println);
//    }


}*/
