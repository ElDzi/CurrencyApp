package pl.wdylewski.currencyapp.nbp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.wdylewski.currencyapp.model.dto.NbpDataDto;

@Component
@RequiredArgsConstructor
public class NbpClient {

  private final String NBP_API_ENDPOINT = "http://api.nbp.pl/api/exchangerates/rates/";
  @Autowired
  private RestTemplate restTemplate;

  public NbpDataDto getCurrentCurrencyValue(String currencyCode) {
    String table = TableACurrencies.isPresent(currencyCode) ? "a" : "b";
    URI uri = getCurrencyRateUri(currencyCode, table);
    try {
      NbpDataDto response = restTemplate.getForObject(uri, NbpDataDto.class);
      return response;
    }catch (HttpClientErrorException e) {
      HttpStatus statusCode = e.getStatusCode();
      if(statusCode == HttpStatus.NOT_FOUND) {
       throw new RuntimeException("Currency not found");
      } else if (statusCode == HttpStatus.BAD_REQUEST) {
        String responseBodyAsString = e.getResponseBodyAsString();
        System.out.println(responseBodyAsString);
        if(responseBodyAsString.contains("Przekroczony limit")) {
          throw new RuntimeException("Limit reached");
        }
        throw new RuntimeException("Bad request");
      }
    }
    throw new RuntimeException("Unknown error occurred");
  }
  private URI getCurrencyRateUri(String currencyCode, String table) {
    return UriComponentsBuilder.fromHttpUrl(NBP_API_ENDPOINT + table + "/" + currencyCode).build()
        .encode().toUri();
  }

}
