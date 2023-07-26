package pl.wdylewski.currencyapp.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyLogRequest {
  private String currencyCode;
  private String name;
}
