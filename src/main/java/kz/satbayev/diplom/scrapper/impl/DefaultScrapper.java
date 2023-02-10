package kz.satbayev.diplom.scrapper.impl;

import kz.satbayev.diplom.converter.Converter;
import kz.satbayev.diplom.kafka.Producer;
import kz.satbayev.diplom.scrapper.Scrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultScrapper implements Scrapper {

  private final Pattern digits = Pattern.compile("\\d+");

  @Value("${scrapper.url}")
  private String baseUrl;

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

      final var document = Jsoup.connect(url).get();

      return Optional.of(document.getElementsByClass("bloko-button"))
                     .flatMap(doc -> doc.stream()
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
                                        .max(Comparator.comparingInt(Integer :: intValue)));

    } catch (IOException e) {
      log.error("Couldn't get total pages count", e);
      return Optional.empty();
    }
  }

  private Document execute(int page) {
    try {
      final var url = baseUrl + page;
      return Jsoup.connect(url).get();
    } catch (IOException e) {
      log.error(String.format("Couldn't get body from page %s", page), e);
      return null;
    }
  }

}
