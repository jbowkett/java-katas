package info.bowkett.flink.pricing;

import java.util.Properties;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * Created by jbowkett on 01/04/2016.
 */
public class KafkaPriceSink implements PriceSink {

  private final Producer<String, Price> producer;

  public KafkaPriceSink() {
    this(new KafkaProducer<>(defaultKafkaProperties()));
  }

  public KafkaPriceSink(Producer<String, Price> producer){
    this.producer = producer;
  }

  @Override
  public void addPrice(Price p)  {
    producer.send(new ProducerRecord<>("prices", p));
  }


  @Override
  public void close(){
    producer.close();
  }


  private static Properties defaultKafkaProperties() {
    final Properties props = new Properties();
    props.put("bootstrap.servers", "localhost:9092");
    props.put("acks", "all");
    props.put("retries", 0);
    props.put("batch.size", 16384);
    props.put("linger.ms", 1);
    props.put("buffer.memory", 33554432);
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", "info.bowkett.flink.pricing.PriceSerializer");
    return props;
  }
}
