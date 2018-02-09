package info.bowkett.flink.pricing;

import info.bowkett.ticking.Price;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * Created by jbowkett on 04/04/2016.
 */
public class PriceSerializer implements Serializer<Price> {
  /**
   * Configure this class.
   *
   * @param configs configs in key/value pairs
   * @param isKey   whether is for key or value
   */
  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {

  }

  /**
   * @param topic topic associated with data
   * @param data  typed data
   * @return serialized bytes
   */
  @Override
  public byte[] serialize(String topic, Price data) {
    return (data.isin + '|' + data.bid + '|' + data.ask + '|' + data.mid).getBytes();
  }

  /**
   * Close this serializer.
   * This method has to be idempotent if the serializer is used in KafkaProducer because it might be called
   * multiple times.
   */
  @Override
  public void close() {

  }
}
