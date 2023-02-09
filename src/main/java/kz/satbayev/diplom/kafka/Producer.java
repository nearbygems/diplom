package kz.satbayev.diplom.kafka;

import kz.satbayev.diplom.model.kafka.ParsedVacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Producer {

  private final KafkaTemplate<String, ParsedVacancy> template;

  public static final String VACANCY = "vacancy";

  public void produce(ParsedVacancy vacancy) {
    template.send(VACANCY, vacancy);
  }

}
