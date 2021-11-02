package com.georgeisaev.fillthegaps.synchronizedshopstats.service;

import com.georgeisaev.fillthegaps.synchronizedshopstats.data.dto.ReceiptDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class ReceiptService {

    ShopStatisticsService stats;
    Map<Long, ReceiptDto> receipts;

    public ReceiptService(ShopStatisticsService stats) {
        this.stats = stats;
        this.receipts = new ConcurrentHashMap<>();
    }

    public Optional<ReceiptDto> findById(long receiptId) {
        return Optional.ofNullable(receipts.getOrDefault(receiptId, null));
    }

    public void processReceipt(ReceiptDto receipt) {
        log.info("Add receipt {}", receipt);
        synchronized (stats) {
            receipts.put(receipt.getId(), receipt);
            stats.addData(receipt.getCount(), receipt.getPrice());
            receipts.put(receipt.getId(), receipt);
        }
    }

    public void deleteById(long receiptId) {
        log.info("Delete by receipt id {}", receiptId);
        receipts.remove(receiptId);
    }

}
