package pl.wdylewski.currencyapp.response;

import java.math.BigDecimal;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CurrentCurrencyResponse {

  @NotNull
  private String currencyCode;
  @NotEmpty
  private String currencyName;
  @NotEmpty
  private BigDecimal currencyRate;

}
