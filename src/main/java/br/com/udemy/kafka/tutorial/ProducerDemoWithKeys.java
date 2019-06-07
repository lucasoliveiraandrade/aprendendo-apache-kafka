package br.com.udemy.kafka.tutorial;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

public class ProducerDemoWithKeys {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final Logger logger = LoggerFactory.getLogger(ProducerDemoWithKeys.class);

        String server = "localhost:9092";

        // create Producer properties
        Properties properties = new Properties();
        properties.setProperty(BOOTSTRAP_SERVERS_CONFIG, server);
        properties.setProperty(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // create the producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        for (int i = 0 ; i < 10 ; i++) {

            String topic = "myFirstTopic";
            String value = "My message " + i;
            String key = "key_" + i;

            // create a producer record with key
            ProducerRecord<String, String> record =
                    new ProducerRecord<String, String>(topic, key, value);

            logger.info("\nKey: " + key);

            // INFO: quando as mensagens são enviadas com chaves elas vao sempre para a mesma partição
            // ex.: a mensagem com key 1 sempre vai ser enviada para a partição 0
            //      a mensagem com key 4 sempre vai ser enviada para a partição 3

            //send data - asynchronous
            producer.send (record, new Callback() {
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e == null) { // envio da mensagem sem erro

                        logger.info("\nTopic: " + recordMetadata.topic() + "\n" +
                                "Partition: " + recordMetadata.partition() + "\n" +
                                "Offset: " + recordMetadata.offset() + "\n" +
                                "Timestamp: " + recordMetadata.timestamp());
                    } else { // envio da mensagem com erro
                        logger.error("Error message: " + e);
                    }
                }
            }).get(); // block the .send() to make it synchronous - BAD PRACTICE!
        }

        producer.flush();
        producer.close();
    }
}
