package kz.satbayev.diplom.converter;

import kz.satbayev.diplom.converter.impl.DefaultConverter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {DefaultConverter.class})
public class DefaultConverterTest {

  @Autowired
  private Converter converter;

  @Test
  void test() {
    System.out.println("test");
  }

}
