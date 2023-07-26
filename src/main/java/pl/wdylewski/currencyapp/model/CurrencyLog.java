package pl.wdylewski.currencyapp.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Table(name = "currency_log")
public class CurrencyLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String currencyCode;
  private LocalDateTime date;
  private BigDecimal rate;
  private Boolean valid;



}
