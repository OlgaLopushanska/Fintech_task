package com.fintech.task.repository;

import com.fintech.task.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    List<Currency> findAllByDateAndSource(LocalDate date, String source);
}
