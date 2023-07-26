package pl.wdylewski.currencyapp;

import java.util.Currency;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class CurrencyAppApplication {

  public static void main(String[] args) {
    SpringApplication.run(CurrencyAppApplication.class, args);

  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/currencies/**")
            .allowedOrigins( "http://localhost:4200");
      }
    };
  }
}
