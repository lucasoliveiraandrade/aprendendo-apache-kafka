package br.com.udemy.kafka.tutorial;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

public class ConsumerDemo {

    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger(ConsumerDemo.class.getClass());

        // create consumer configs
        String bootstrapServer = "localhost:9092";
        String groupId = "any-group-id";
        String topic = "myFirstTopic";

        Properties properties = new Properties(); // ver documentação para ver todas as propriedades possiveis
        properties.setProperty(BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        properties.setProperty(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());  // a mensagem vem serializada do Kafka, ou seja, em bites e bytes
        properties.setProperty(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(GROUP_ID_CONFIG, groupId);
        properties.setProperty(AUTO_OFFSET_RESET_CONFIG, "earliest"); // earliest > ler todas as mensagens desde o começo
                                                                      // latest > ler apenas as novas mensagens sem lançar erro
                                                                      // none > ler apenas as novas mensagens e lança erro se não nenhum offset foi salvo

        // create a consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);

        // subscribe consumer to our topic(s)
        consumer.subscribe(Arrays.asList(topic));

        //poll for new data
        while (true){
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

            records.forEach(record -> {
                logger.info("Key: " + record.key() + ", Value: " + record.value());
                logger.info("Partition: " + record.partition() + ", Offset: " + record.offset());
            });
        }
    }
}
