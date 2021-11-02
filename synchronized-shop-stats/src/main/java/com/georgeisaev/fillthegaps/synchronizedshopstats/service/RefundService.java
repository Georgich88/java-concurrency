package com.georgeisaev.fillthegaps.synchronizedshopstats.service;

import com.georgeisaev.fillthegaps.synchronizedshopstats.data.dto.ReceiptDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class RefundService {

    ReceiptService receiptService;
    ShopStatisticsService stats;

    public synchronized void processRefund(long receiptId) {
        log.info("Process refund by receipt Id {}", receiptId);
        ReceiptDto receipt = receiptService.findById(receiptId).orElseThrow(NoSuchElementException::new);
        Long count = receipt.getCount();
        Long price = receipt.getPrice();
        synchronized (stats) {
            receiptService.deleteById(receiptId);
            stats.removeData(count, price);
        }
    }

}
