package com.fintech.task.service.impl2;

import com.fintech.task.dto.CurrencyDto;
import com.fintech.task.dto.CurrencyMinfinDto;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CurrencyAPIServiceMinfin implements CurrencyAPIService, CurrencySaveService {
    private final CurrencyRepository currencyRepository;
    private final WebClient webClient;
    @Value("${minfintoday}")
    private String minfintoday;
    @Value("${minfinarchive}")
    private String minfinarchive;
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
                currencies.addAll(currencyRepository.findAllByDateAndSource(LocalDate.now().minusDays(counter), source));
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);
        Mono<List<CurrencyMinfinDto>> response = webClient.get()
                .uri(minfinarchive + localDate.toString())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CurrencyMinfinDto>>(){});
        List<CurrencyMinfinDto> currencyMinfinDto = response.block();
        assert currencyMinfinDto != null;
        List<Currency> currencyList = currencyMinfinDto.stream()
                .map(CurrencyMapper::mapToCurrencyFromMinfin)
                .collect(Collectors.toList());
        Map<String, List<Currency>> map = currencyList.stream()
                .collect(Collectors.groupingBy(Currency::getCurrencyName));
        List<Currency> list = new ArrayList<>();
        for(Map.Entry<String, List<Currency>> entry : map.entrySet()) {
            List<Currency> currencies = entry.getValue();
            double buy = currencies.stream().mapToDouble(Currency::getBuy).average().orElse(0.0);
            double sell = currencies.stream().mapToDouble(Currency::getSell).average().orElse(0.0);
            list.add(new Currency(currencies.get(0).getId(), entry.getKey(), "UAH", buy, sell, currencies.get(0).getDate(), currencies.get(0).getSource()));
        }
        List<Currency> savedCurrency = list.stream()
                .peek(e->e.setDate(localDate))
                .map(currencyRepository::save)
                .collect(Collectors.toList());
        return  savedCurrency;
    }


    @Override
    public List<Currency> saveTodayCurrency() {
        Mono<List<CurrencyMinfinDto>> response = webClient.get()
                .uri(minfintoday)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>(){});
        List<CurrencyMinfinDto> currencyMinfinDto = response.block();
        assert currencyMinfinDto != null;
        List<Currency> currencyList = currencyMinfinDto.stream()
                .map(CurrencyMapper::mapToCurrencyFromMinfin)
                .collect(Collectors.toList());
        Map<String, List<Currency>> map = currencyList.stream()
                .collect(Collectors.groupingBy(Currency::getCurrencyName));
        List<Currency> list = new ArrayList<>();
        for(Map.Entry<String, List<Currency>> entry : map.entrySet()) {
            List<Currency> currencies = entry.getValue();
            double buy = currencies.stream().mapToDouble(Currency::getBuy).average().orElse(0.0);
            double sell = currencies.stream().mapToDouble(Currency::getSell).average().orElse(0.0);
            list.add(new Currency(currencies.get(0).getId(), entry.getKey(), "UAH", buy, sell,
                    currencies.get(0).getDate(), currencies.get(0).getSource()));
        }
        List<Currency> savedCurrency = list.stream()
                .map(currencyRepository::save)
                .collect(Collectors.toList());
        return  savedCurrency;
    }

    public CurrencyAPIServiceMinfin(CurrencyRepository currencyRepository, WebClient webClient) {
        this.currencyRepository = currencyRepository;
        this.webClient = webClient;
    }
}
