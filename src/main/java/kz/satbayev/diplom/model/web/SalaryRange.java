package kz.satbayev.diplom.model.web;

import lombok.Data;

@Data
public class SalaryRange {

    private Long from;
    private Long to;

    public static SalaryRange from(Long from, Long to) {
        final var range = new SalaryRange();
        range.from = from;
        range.to = to;
        return range;
    }

}
