package com.fintech.task.service;

import com.fintech.task.dto.CurrencyDto;
import com.fintech.task.dto.CurrencyPrivatDto;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface CurrencyService {
    List<CurrencyDto> getCurrency();


    List<CurrencyDto> getCurrencyForDate(String date);
}
