package com.fintech.task.cronjob;

import com.fintech.task.service.impl2.utils.CurrencyServiceUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask{
    private CurrencyServiceUtils currencyServiceUtils;

    @Scheduled(cron = "0 0 * * * *")
    public void uploadFreshData() throws InterruptedException {
        currencyServiceUtils.saveDataForAllSourcesForToday();
    }

    public ScheduledTask(CurrencyServiceUtils currencyServiceUtils) {
        this.currencyServiceUtils = currencyServiceUtils;
    }
}
