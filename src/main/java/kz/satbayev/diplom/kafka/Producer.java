package kz.satbayev.diplom.kafka;

import kz.satbayev.diplom.model.kafka.ParsedVacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Producer {

  private final KafkaTemplate<String, ParsedVacancy> template;

  @Value("${scrapper.topic}")
  private String topic;

  public void produce(ParsedVacancy vacancy) {
    template.send(topic, vacancy);
  }

}
