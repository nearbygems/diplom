package kz.satbayev.diplom.schedule;

import kz.satbayev.diplom.scrapper.Scrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class Job {

  private final Scrapper scrapper;

  @Scheduled(cron = "0 0 0 * * *")
  public void execute() {
    scrapper.start();
  }

}
