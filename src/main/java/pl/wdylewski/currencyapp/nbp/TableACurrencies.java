package pl.wdylewski.currencyapp.nbp;

public enum TableACurrencies {
  THB,
  USD,
  AUD,
  HKD,
  CAD,
  NZD,
  SGD,
  EUR,
  HUF,
  CHF,
  GBP,
  UAH,
  JPY,
  CZK,
  DKK,
  ISK,
  NOK,
  SEK,
  RON,
  BGN,
  TRY,
  ILS,
  CLP,
  PHP,
  MXN,
  ZAR,
  BRL,
  MYR,
  IDR,
  INR,
  KRW,
  CNY,
  XDR;

  public static boolean isPresent(String currencyCode) {
    try {
      TableACurrencies.valueOf(currencyCode);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }
}
