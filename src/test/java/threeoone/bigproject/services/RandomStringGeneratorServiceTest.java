package threeoone.bigproject.services;

import org.junit.jupiter.api.Test;

class RandomStringGeneratorServiceTest {
  RandomStringGeneratorService service = new RandomStringGeneratorService();

  @Test
  void test() {
    for (int i = 0; i < 5; i++) {
      System.out.println(service.generate(10));
    }
  }
}