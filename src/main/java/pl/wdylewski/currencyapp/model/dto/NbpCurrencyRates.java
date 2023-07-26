package pl.wdylewski.currencyapp.model.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NbpCurrencyRates {

  private String no;
  private String effectiveDate;
  private BigDecimal mid;

}
