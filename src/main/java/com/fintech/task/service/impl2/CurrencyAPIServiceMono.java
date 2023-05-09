package com.fintech.task.service.impl2;

import com.fintech.task.dto.CurrencyDto;
import com.fintech.task.dto.CurrencyMonoDto;
import com.fintech.task.entity.Currency;
import com.fintech.task.exception.DateNotCorrectFormatException;
import com.fintech.task.mapper.CurrencyMapper;
import com.fintech.task.repository.CurrencyRepository;
import com.fintech.task.service.CurrencyAPIService;
import com.fintech.task.service.CurrencySaveService;
import com.fintech.task.service.impl2.utils.CurrencyServiceUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CurrencyAPIServiceMono implements CurrencyAPIService, CurrencySaveService {
    private final CurrencyRepository currencyRepository;
    private final WebClient webClient;
    @Value("${monotoday}")
    private String monotoday;
    @Value("${monoarchive}")
    private String monoarchive;
    @Override
    public List<CurrencyDto> getCurrencyBySource(String source) {
        List<Currency> currencies = currencyRepository.findAllByDateAndSource(LocalDate.now(), source);
        List<CurrencyDto> currencyDtoList = currencies.stream()
                .map(CurrencyMapper::mapToCurrencyDto)
                .distinct()
                .collect(Collectors.toList());
        return currencyDtoList;
    }

    @Override
    public List<CurrencyDto> getCurrencyBySourceAndDate(String source, String date) {
        List<Currency> currencies = new ArrayList<>();
        try {
            Period period = Period.between(CurrencyMapper.fixDate(date), LocalDate.now());
            for (int counter = 0; counter <= period.getDays(); counter++) {
                currencies.addAll(currencyRepository.findAllByDateAndSource(LocalDate.now()
                        .minusDays(counter), source));
            }
        }catch (Exception e) {
            throw new DateNotCorrectFormatException("Currency", "date", date);
        }
        List<CurrencyDto> currencyDtoList = currencies.stream()
                .map(CurrencyMapper::mapToCurrencyDto)
                .collect(Collectors.toList());
        List<CurrencyDto> currencyDtos = CurrencyServiceUtils.getCurrency(currencyDtoList).stream()
                .peek(e->e.setSource(source))
                .peek(e->e.setDate(CurrencyMapper.fixDate(date)))
                .collect(Collectors.toList());
        return currencyDtos;
    }

    @Override
    public List<Currency> saveArchiveData(String date) {
        Mono<List<CurrencyMonoDto>> response = webClient.get()
                .uri(monoarchive + date)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CurrencyMonoDto>>(){});
        List<CurrencyMonoDto> currencyMonoDto = response.block();
        List<Currency> currency = currencyMonoDto.stream()
                .map(CurrencyMapper::mapToCurrencyFromMono)
                .collect(Collectors.toList());
        List<Currency> savedCurrency = currency.stream()
                .filter(Objects::nonNull)
                .peek(e-> e.setDate(CurrencyMapper.fixDate(date)))
                .filter(e->e.getBaseCurrencyName().equals("UAH"))
                .filter(e->e.getBuy() != 0)
                .map(currencyRepository::save).collect(Collectors.toList());
        return savedCurrency;
    }

    @Override
    public List<Currency> saveTodayCurrency() {
        Mono<List<CurrencyMonoDto>> response = webClient.get()
                .uri(monotoday)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
        List<CurrencyMonoDto> currencyMonoDto = response.block();
        assert currencyMonoDto != null;
        List<Currency> currency = currencyMonoDto.stream()
                .map(CurrencyMapper::mapToCurrencyFromMono)
                .collect(Collectors.toList());
        List<Currency> savedCurrency = currency.stream()
                .filter(Objects::nonNull)
                .filter(e->e.getBaseCurrencyName().equals("UAH"))
                .filter(e->e.getBuy() != 0)
                .map(currencyRepository::save)
                .collect(Collectors.toList());
        return savedCurrency;
    }

    public CurrencyAPIServiceMono(CurrencyRepository currencyRepository, WebClient webClient) {
        this.currencyRepository = currencyRepository;
        this.webClient = webClient;
    }
}
