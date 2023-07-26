package pl.wdylewski.currencyapp.controller;

import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.wdylewski.currencyapp.request.CurrencyLogRequest;
import pl.wdylewski.currencyapp.response.CurrentCurrencyResponse;
import pl.wdylewski.currencyapp.service.CurrencyService;

@RestController
@RequestMapping("/currencies")
public class CurrencyController {

  @Autowired
  private CurrencyService currencyService;

  @PostMapping("/get-current-currency-value-command")
  public ResponseEntity<?> getCurrentCurrencyValueCommand(
      @RequestBody @Valid CurrencyLogRequest currencyLogRequest) {
    try {
      CurrentCurrencyResponse response = currencyService.getCurrentCurrencyValue(
          currencyLogRequest);
      return ResponseEntity.ok(response);
    } catch (RuntimeException e) {
      String message = e.getMessage();
      if (message.equals("Currency not found")) {
        return ResponseEntity.badRequest().body("CURRENCY_NOT_FOUND");
      } else if (message.equals("Limit reached")) {
        return ResponseEntity.badRequest().body("LIMIT_REACHED");
      } else if (message.equals("Name is missing")) {
        return ResponseEntity.badRequest().body("NAME_SURNAME_MISSING");
      } else if (message.equals("Currency code is empty")) {
        return ResponseEntity.badRequest().body("CURRENCY_CODE_EMPTY");
      } else if(message.equals("Bad request")) {
        return ResponseEntity.badRequest().body("BAD_REQUEST");
      }else{
        return ResponseEntity.badRequest().body("UNKNOWN_ERROR");
      }
    }
  }

  @GetMapping("/requests")
  public ResponseEntity<?> getRequests(@RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size);
    Map<String, Object> currencyLogs = currencyService.getRequests(pageable);
    return ResponseEntity.ok(currencyLogs);
  }
}
