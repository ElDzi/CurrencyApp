package pl.wdylewski.currencyapp.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pl.wdylewski.currencyapp.model.CurrencyLog;
import pl.wdylewski.currencyapp.model.dto.NbpCurrencyRates;
import pl.wdylewski.currencyapp.model.dto.NbpDataDto;
import pl.wdylewski.currencyapp.nbp.NbpClient;
import pl.wdylewski.currencyapp.repository.CurrencyLogRepository;
import pl.wdylewski.currencyapp.request.CurrencyLogRequest;
import pl.wdylewski.currencyapp.response.CurrencyLogResponse;
import pl.wdylewski.currencyapp.response.CurrentCurrencyResponse;

@Service
public class CurrencyService {

  @Autowired
  private NbpClient nbpClient;

  @Autowired
  private CurrencyLogRepository currencyLogRepository;

  public CurrentCurrencyResponse getCurrentCurrencyValue(CurrencyLogRequest currencyLogRequest) {
    String currencyCode = currencyLogRequest.getCurrencyCode();
    if (StringUtils.isEmpty(currencyCode)) {
      throw new RuntimeException("Currency code is empty");
    }

    String name = currencyLogRequest.getName();
    if (StringUtils.isEmpty(name)) {
      throw new RuntimeException("Name is missing");
    }

    currencyCode = currencyCode.toUpperCase();

    try {
      NbpDataDto nbpDataDto = nbpClient.getCurrentCurrencyValue(currencyCode);
      NbpCurrencyRates rate = nbpDataDto.getRates()[0];
      BigDecimal rateValue = rate.getMid().setScale(2, RoundingMode.HALF_UP);

      CurrencyLog log = createCurrencyLog(currencyCode, name, rateValue, true);
      currencyLogRepository.save(log);

      return CurrentCurrencyResponse.builder()
          .currencyCode(nbpDataDto.getCode())
          .currencyName(nbpDataDto.getCurrency())
          .currencyRate(rateValue)
          .build();
    } catch (RuntimeException e) {
      CurrencyLog log = createCurrencyLog(currencyCode, name, null, false);
      currencyLogRepository.save(log);
      throw e;
    }
  }
  private CurrencyLog createCurrencyLog(String currencyCode, String name, BigDecimal rateValue, boolean isValid) {
    CurrencyLog log = new CurrencyLog();
    log.setCurrencyCode(currencyCode);
    log.setDate(LocalDateTime.now());
    log.setName(name);
    log.setRate(rateValue);
    log.setValid(isValid);
    return log;
  }
  public Map<String, Object> getRequests(Pageable pageable) {
    Page<CurrencyLog> currencyLogs = currencyLogRepository.findAllByOrderByDateDesc(pageable);
    List<CurrencyLogResponse> currencyLogResponses = currencyLogs.stream().map(i -> CurrencyLogResponse.builder()
        .currencyCode(i.getCurrencyCode())
        .name(i.getName())
        .date(i.getDate())
        .value(i.getRate())
        .valid(i.getValid())
        .build()).collect(Collectors.toList());
    Map<String, Object> response = new HashMap<>();
    response.put("data", currencyLogResponses);
    response.put("currentPage", currencyLogs.getNumber());
    response.put("totalItems", currencyLogs.getTotalElements());
    response.put("totalPages", currencyLogs.getTotalPages());
    return response;
  }
public long getCount(){
    return currencyLogRepository.count();
}

}
