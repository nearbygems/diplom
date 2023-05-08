package kz.satbayev.diplom.controller;

import kz.satbayev.diplom.model.db.Vacancy;
import kz.satbayev.diplom.model.web.SalaryRange;
import kz.satbayev.diplom.scrapper.Scrapper;
import kz.satbayev.diplom.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vacancies")
public class VacancyController {

    private final VacancyService service;
    private final Scrapper scrapper;

    @GetMapping("/start")
    public String start() {
        new Thread(scrapper::start).start();
        return "Scrapper successfully started";
    }

    @GetMapping("/title/{page}/{size}")
    public List<Vacancy> loadByTitle(@PathVariable Integer page,
                                     @PathVariable Integer size,
                                     @RequestParam String title) {
        return service.loadByTitle(title, PageRequest.of(page, size));
    }

    @GetMapping("/salary/{page}/{size}")
    public List<Vacancy> loadBySalary(@PathVariable Integer page,
                                      @PathVariable Integer size,
                                      @RequestParam Long from,
                                      @RequestParam Long to) {
        return service.loadBySalary(SalaryRange.from(from, to), PageRequest.of(page, size));
    }

    @GetMapping("/responsibility/{page}/{size}")
    public List<Vacancy> loadByResponsibility(@PathVariable Integer page,
                                              @PathVariable Integer size,
                                              @RequestParam String responsibility) {
        return service.loadByResponsibility(responsibility, PageRequest.of(page, size));
    }

    @GetMapping("/requirement/{page}/{size}")
    public List<Vacancy> loadByRequirement(@PathVariable Integer page,
                                           @PathVariable Integer size,
                                           @RequestParam String requirement) {
        return service.loadByRequirement(requirement, PageRequest.of(page, size));
    }

}
