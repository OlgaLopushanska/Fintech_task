package com.fintech.task.controller;

import com.fintech.task.dto.CurrencyDto;
import com.fintech.task.service.impl2.CurrencyAPIServiceMinfin;
import com.fintech.task.service.impl2.CurrencyAPIServiceMono;
import com.fintech.task.service.impl2.CurrencyAPIServicePrivat;
import com.fintech.task.service.impl2.utils.CurrencyServiceUtils;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/currencies")
public class CurrencyAPIController {
    private final CurrencyAPIServicePrivat currencyAPIServicePrivat;
    private final CurrencyAPIServiceMono currencyAPIServiceMono;
    private final CurrencyAPIServiceMinfin currencyAPIServiceMinfin;

    @Operation(
            summary = "Retrieves currency rates from database for privat, mono, minfin and calculates average"
    )
    @GetMapping("/today")
    public ResponseEntity<List<CurrencyDto>> getCurrency() {
        List<CurrencyDto> currencyDtoList = currencyAPIServicePrivat.getCurrencyBySource("privat");
        List<CurrencyDto> currencyDtoMono = currencyAPIServiceMono.getCurrencyBySource("mono");
        List<CurrencyDto> currencyDtoMinfin = currencyAPIServiceMinfin.getCurrencyBySource("minfin");
        currencyDtoList.addAll(currencyDtoMono);
        currencyDtoList.addAll(currencyDtoMinfin);
        List<CurrencyDto> currencyDtoAverage = CurrencyServiceUtils.getCurrency(currencyDtoList);
        currencyDtoList.addAll(currencyDtoAverage);
        return new ResponseEntity<>(currencyDtoList, HttpStatus.OK);
    }

    @Operation(
            summary = "Retrieves currency rates from database starting from today till the day that you write " +
                    "and calculates average for each currency and source"
    )
    @GetMapping("/period/{date}")
    public ResponseEntity<List<CurrencyDto>> getCurrencyForPeriod(@PathVariable String date) {
        List<CurrencyDto> currencyDtoList = currencyAPIServicePrivat.getCurrencyBySourceAndDate("privat", date);
        List<CurrencyDto> currencyDtoMono = currencyAPIServiceMono.getCurrencyBySourceAndDate("mono", date);
        List<CurrencyDto> currencyDtoMinfin = currencyAPIServiceMinfin.getCurrencyBySourceAndDate("minfin", date);
        currencyDtoList.addAll(currencyDtoMono);
        currencyDtoList.addAll(currencyDtoMinfin);
        return new ResponseEntity<>(currencyDtoList, HttpStatus.OK);
    }

    public CurrencyAPIController(CurrencyAPIServicePrivat currencyAPIServicePrivat,
                                 CurrencyAPIServiceMono currencyAPIServiceMono,
                                 CurrencyAPIServiceMinfin currencyAPIServiceMinfin) {
        this.currencyAPIServicePrivat = currencyAPIServicePrivat;
        this.currencyAPIServiceMono = currencyAPIServiceMono;
        this.currencyAPIServiceMinfin = currencyAPIServiceMinfin;
    }
}
