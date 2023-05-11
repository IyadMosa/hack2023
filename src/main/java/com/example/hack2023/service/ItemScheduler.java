package com.example.hack2023.service;

import com.example.hack2023.model.Item;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Component
public class ItemScheduler {

    private final ItemService service;

    public ItemScheduler(ItemService service) {
        this.service = service;
    }

    private LocalDate convertToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDate localDate = instant.atZone(zone).toLocalDate();
        return localDate;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void checkExpiredDate() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
        List<Item> itemList = service.findAll(null);
        for (Item item : itemList) {
            if (convertToLocalDate(item.getExpDate()).isBefore(tomorrow)) {
                item.setExpiringNextDay(true);
            }
        }
    }
}
