package kz.satbayev.diplom.controller;

import kz.satbayev.diplom.scrapper.Scrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vacancies")
public class VacancyController {

  private final Scrapper scrapper;

  @GetMapping("/start")
  public String start() {
    new Thread(scrapper :: start).start();
    return "Scrapper successfully started";
  }

}
