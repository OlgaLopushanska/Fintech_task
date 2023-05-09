package com.fintech.task.mapper;

import com.fintech.task.dto.*;
import com.fintech.task.entity.Currency;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class CurrencyMapper {

    public static Currency mapToCurrencyFromPrivat(CurrencyPrivatDto currencyPrivatDto) {
        Currency currency = new Currency(
                currencyPrivatDto.getId(), currencyPrivatDto.getCcy(), currencyPrivatDto.getBase_ccy(),
                currencyPrivatDto.getBuy(), currencyPrivatDto.getSale(), LocalDate.now(), "privat");
        return currency;
    }

    public static CurrencyDto mapToCurrencyDto(Currency currency) {
        CurrencyDto currencyDto = new CurrencyDto(
                    currency.getCurrencyName(), currency.getBaseCurrencyName(),currency.getBuy(),
                    currency.getSell(), currency.getSource(), currency.getDate());
        return currencyDto;
    }

    public static Currency mapToCurrencyFromMono(CurrencyMonoDto currencyMonoDto) {
        HashMap<Integer, String> map = createCurrencyCodeMap();
        Currency currency = null;
        if (map.containsKey(currencyMonoDto.getCurrencyCodeA()) && map.containsKey(currencyMonoDto.getCurrencyCodeB())) {
            currency = new Currency(
                    currencyMonoDto.getId(), map.get(currencyMonoDto.getCurrencyCodeA()),
                    map.get(currencyMonoDto.getCurrencyCodeB()), currencyMonoDto.getRateBuy(),
                    currencyMonoDto.getRateSell(), LocalDate.now(), "mono");
        }
        return currency;
    }

    public static Currency mapToCurrencyFromMinfin(CurrencyMinfinDto currencyMinfinDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Currency currency = new Currency(
                currencyMinfinDto.getId(), currencyMinfinDto.getCurrency().toUpperCase(), "UAH",
                currencyMinfinDto.getAsk(), currencyMinfinDto.getBid(),
                LocalDate.parse(currencyMinfinDto.getDate(), formatter),"minfin");
        return currency;
    }

    public static LocalDate fixDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(date, formatter);
    }

    public static String fixDateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return  date.format(formatter).toString();
    }

    public static Currency mapToCurrencyFromBasePrivatArchive(CurrencyBasePrivatArchiveDto currencyArchiveDto) {
        Currency currency = new Currency(currencyArchiveDto.getId(), currencyArchiveDto.getCurrency(),
                currencyArchiveDto.getBaseCurrency(), currencyArchiveDto.getSaleRateNB(),
                currencyArchiveDto.getPurchaseRateNB(), LocalDate.now(), "privat");
        return currency;
    }

    public static List<Currency> mapToCurrencyFromPrivatArchive(CurrencyPrivatArchiveDto currencyPrivatArchiveDto) {
        List<CurrencyBasePrivatArchiveDto> listArchiveCurrency = currencyPrivatArchiveDto.getExchangeRate();
        List<Currency> currencyList = listArchiveCurrency.stream()
                .map(e->CurrencyMapper.mapToCurrencyFromBasePrivatArchive(e))
                .collect(Collectors.toList());
        return currencyList;
     }
    private static HashMap<Integer, String> createCurrencyCodeMap() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(840, "USD");
        map.put(978, "EUR");
        map.put(980, "UAH");
        map.put(756,"CNF");
        map.put(203,"CZK");
        map.put(826, "GBP");
        map.put(376, "ILS");
        map.put(392, "JPY");
        map.put(578,"NOK");
        map.put(616, "PLZ");
        map.put(752, "SEK");
        return map;
    }
    }
