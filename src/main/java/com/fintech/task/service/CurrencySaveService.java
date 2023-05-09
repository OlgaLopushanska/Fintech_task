package com.fintech.task.service;

import com.fintech.task.entity.Currency;

import java.time.LocalDate;
import java.util.List;

public interface CurrencySaveService {
    List<Currency> saveArchiveData(String date);

    List<Currency> saveTodayCurrency();

}
