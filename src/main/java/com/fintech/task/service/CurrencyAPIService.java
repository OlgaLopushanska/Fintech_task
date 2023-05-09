package com.fintech.task.service;

import com.fintech.task.dto.CurrencyDto;

import java.util.List;

public interface CurrencyAPIService {
    List<CurrencyDto> getCurrencyBySource(String source);

    List<CurrencyDto> getCurrencyBySourceAndDate(String source, String date);
}
