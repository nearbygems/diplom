package kz.satbayev.diplom.scrapper.impl;

import kz.satbayev.diplom.converter.Converter;
import kz.satbayev.diplom.kafka.Producer;
import kz.satbayev.diplom.scrapper.Scrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultScrapper implements Scrapper {

  private final Pattern digits = Pattern.compile("\\d+");

  private final String baseUrl =
      "https://hh.kz/search/vacancy?professional_role=156&professional_role=160&professional_role=10" +
      "&professional_role=12&professional_role=150&professional_role=25&professional_role=165&professional_role=34" +
      "&professional_role=36&professional_role=73&professional_role=155&professional_role=96&professional_role=164" +
      "&professional_role=104&professional_role=157&professional_role=107&professional_role=112&professional_role=113" +
      "&professional_role=148&professional_role=114&professional_role=116&professional_role=121&professional_role=124" +
      "&professional_role=125&professional_role=126&area=40&items_on_page=100&page=%s";

  private final Converter converter;
  private final Producer  producer;

  @Override
  public void start() {

    getTotalPagesCount().stream()
                        .flatMapToInt(count -> IntStream.range(0, count))
                        .parallel()
                        .mapToObj(this :: execute)
                        .filter(Objects :: nonNull)
                        .map(converter :: convert)
                        .flatMap(List :: parallelStream)
                        .forEach(producer :: produce);
  }

  private Optional<Integer> getTotalPagesCount() {
    try {

      final var url = String.format(baseUrl, 0);

      return Jsoup.connect(url)
                  .url(url)
                  .get()
                  .getElementsByClass("bloko-button").stream()
                  .map(Node :: childNodes)
                  .filter(nodes -> !nodes.isEmpty())
                  .flatMap(List :: stream)
                  .map(Node :: childNodes)
                  .filter(nodes -> !nodes.isEmpty())
                  .flatMap(List :: stream)
                  .filter(TextNode.class :: isInstance)
                  .map(TextNode.class :: cast)
                  .filter(node -> !node.isBlank())
                  .map(TextNode :: text)
                  .filter(text -> digits.matcher(text).matches())
                  .map(Integer :: parseInt)
                  .max(Comparator.comparingInt(Integer :: intValue));

    } catch (IOException e) {
      log.error("Couldn't get total pages count", e);
      return Optional.empty();
    }
  }

  private String execute(int page) {
    try {
      final var url = baseUrl + page;
      return Jsoup.connect(url).execute().body();
    } catch (IOException e) {
      log.error(String.format("Couldn't get body from page %s", page), e);
      return null;
    }
  }

}
