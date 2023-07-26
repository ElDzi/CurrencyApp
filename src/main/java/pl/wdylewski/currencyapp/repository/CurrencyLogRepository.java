package pl.wdylewski.currencyapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.wdylewski.currencyapp.model.CurrencyLog;
@Repository
public interface CurrencyLogRepository extends PagingAndSortingRepository<CurrencyLog, Long> {

  Page<CurrencyLog> findAllByOrderByDateDesc(Pageable pageable);
}
