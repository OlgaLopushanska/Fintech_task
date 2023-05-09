package com.fintech.task.service.impl2.utils;

import com.fintech.task.dto.CurrencyDto;
import com.fintech.task.mapper.CurrencyMapper;
import com.fintech.task.service.impl2.CurrencyAPIServiceMinfin;
import com.fintech.task.service.impl2.CurrencyAPIServiceMono;
import com.fintech.task.service.impl2.CurrencyAPIServicePrivat;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CurrencyServiceUtils {
    private final CurrencyAPIServicePrivat currencyAPIServicePrivat;
    private final CurrencyAPIServiceMono currencyAPIServiceMono;
    private final CurrencyAPIServiceMinfin currencyAPIServiceMinfin;

    @EventListener(ApplicationReadyEvent.class)
    public void saveDataForAllSourcesForToday() throws InterruptedException {
        currencyAPIServicePrivat.saveTodayCurrency();
        currencyAPIServiceMono.saveTodayCurrency();
        //currencyAPIServiceMinfin.saveTodayCurrency();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void saveArchiveDataForAllSourcesFor1Year() throws InterruptedException {
        LocalDate date = LocalDate.now();
        for(int counter = 1; counter < 365; counter++) {
            Thread.currentThread().sleep(60000);
            currencyAPIServicePrivat.saveArchiveData(CurrencyMapper.fixDateToString(date.minusDays(counter)));
            currencyAPIServiceMono.saveArchiveData(CurrencyMapper.fixDateToString(date.minusDays(counter)));
            //currencyAPIServiceMinfin.saveArchiveData(CurrencyMapper.fixDateToString(date.minusDays(counter)));
        }
    }

    public CurrencyServiceUtils(CurrencyAPIServicePrivat currencyAPIServicePrivat,
                                CurrencyAPIServiceMono currencyAPIServiceMono,
                                CurrencyAPIServiceMinfin currencyAPIServiceMinfin) {
        this.currencyAPIServicePrivat = currencyAPIServicePrivat;
        this.currencyAPIServiceMono = currencyAPIServiceMono;
        this.currencyAPIServiceMinfin = currencyAPIServiceMinfin;
    }
    public static List<CurrencyDto> getCurrency(List<CurrencyDto> currencyDtoList) {
        List<CurrencyDto> currencyDtos = new ArrayList<>();
        Map<String, List<CurrencyDto>> map = currencyDtoList.stream()
                .collect(Collectors.groupingBy(CurrencyDto::getCurrencyName));
        for(Map.Entry<String, List<CurrencyDto>> entry : map.entrySet()) {
            double buy = BigDecimal.valueOf(entry.getValue().stream()
                    .mapToDouble(CurrencyDto::getBuy).average().orElse(0.0))
                    .setScale(4, RoundingMode.HALF_UP).doubleValue();
            double sell = BigDecimal.valueOf(entry.getValue().stream()
                    .mapToDouble(CurrencyDto::getSell).average().orElse(0.0))
                    .setScale(4, RoundingMode.HALF_UP).doubleValue();
            currencyDtos.add(new CurrencyDto(entry.getValue().get(0).getCurrencyName(),
                    entry.getValue().get(0).getCurrencyBaseName(), buy, sell, "average", LocalDate.now()));
        }
        return currencyDtos;
    }

}
