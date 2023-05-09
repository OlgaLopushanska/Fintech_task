package com.fintech.task.service.impl2;

import com.fintech.task.dto.CurrencyBasePrivatArchiveDto;
import com.fintech.task.dto.CurrencyDto;
import com.fintech.task.dto.CurrencyPrivatArchiveDto;
import com.fintech.task.dto.CurrencyPrivatDto;
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
import java.util.stream.Collectors;

@Service
public class CurrencyAPIServicePrivat implements CurrencyAPIService, CurrencySaveService {
    private final CurrencyRepository currencyRepository;
    private final WebClient webClient;
    @Value("${privatoday}")
    private String privatoday;
    @Value("${privatarchive}")
    private String privatarchive;
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
            for(int counter = 0; counter <= period.getDays(); counter++) {
                currencies.addAll(currencyRepository.findAllByDateAndSource(LocalDate.now()
                        .minusDays(counter), source));
                }
            } catch (Exception e) {
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
        Mono<CurrencyPrivatArchiveDto> response = webClient.get()
                .uri(privatarchive + date)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<CurrencyPrivatArchiveDto>(){});
        CurrencyPrivatArchiveDto currencyPrivatDto = response.block();
        assert currencyPrivatDto != null;
        List<CurrencyBasePrivatArchiveDto> listArchiveCurrency = currencyPrivatDto.getExchangeRate();
        List<Currency> currencyList = listArchiveCurrency.stream()
                .map(CurrencyMapper::mapToCurrencyFromBasePrivatArchive)
                .peek(e-> e.setDate(CurrencyMapper.fixDate(date)))
                .filter(e->e.getCurrencyName().equals("USD") || e.getCurrencyName().equals("EUR"))
                .collect(Collectors.toList());
        List<Currency> savedCurrency = currencyList.stream()
                .map(currencyRepository::save)
                .collect(Collectors.toList());
        return savedCurrency;
    }

    @Override
    public List<Currency> saveTodayCurrency() {
        Mono<List<CurrencyPrivatDto>> response = webClient.get()
                .uri(privatoday)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
        List<CurrencyPrivatDto> currencyPrivatDto = response.block();
        assert currencyPrivatDto != null;
        List<Currency> currency = currencyPrivatDto.stream()
                .map(CurrencyMapper::mapToCurrencyFromPrivat)
                .collect(Collectors.toList());
        List<Currency> savedCurrency = currency.stream()
                .map(currencyRepository::save)
                .collect(Collectors.toList());
        return savedCurrency;
    }

    public CurrencyAPIServicePrivat(CurrencyRepository currencyRepository, WebClient webClient) {
        this.currencyRepository = currencyRepository;
        this.webClient = webClient;
    }
}
