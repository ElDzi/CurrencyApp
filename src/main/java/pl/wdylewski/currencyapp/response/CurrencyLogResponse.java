package pl.wdylewski.currencyapp.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CurrencyLogResponse {
  private String currencyCode;
  private String name;
  private LocalDateTime date;
  private BigDecimal value;
  private Boolean valid;

}
