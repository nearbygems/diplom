package kz.satbayev.diplom.scrapper.impl;

import kz.satbayev.diplom.kafka.Producer;
import kz.satbayev.diplom.scrapper.Scrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultScrapper implements Scrapper {

  private final Producer producer;

  @Override
  public void start() {
    throw new UnsupportedOperationException("Not implemented yet");
  }

}
