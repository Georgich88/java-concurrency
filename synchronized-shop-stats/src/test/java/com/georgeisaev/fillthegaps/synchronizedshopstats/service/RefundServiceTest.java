package com.georgeisaev.fillthegaps.synchronizedshopstats.service;

import com.georgeisaev.fillthegaps.synchronizedshopstats.data.dto.ReceiptDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@SpringBootTest
class RefundServiceTest {

    @Autowired
    ShopStatisticsService shopStatisticsService;

    @Autowired
    RefundService refundService;

    @Autowired
    ReceiptService receiptService;


    @Test
    void shouldRefund() {

        List<ReceiptDto> receipts = LongStream.rangeClosed(0, 10_000)
                .mapToObj(price -> ReceiptDto.builder()
                        .id(price)
                        .count(1L)
                        .price(price)
                        .build())
                .collect(Collectors.toList());

        receipts.forEach(receiptService::processReceipt);

        @SuppressWarnings("unchecked")
        CompletableFuture<Void>[] tasks = receipts.stream()
                .filter(receipt -> receipt.getPrice() >= 9_000)
                .map(receipt -> CompletableFuture.runAsync(() -> refundService.processRefund(receipt.getId())))
                .toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(tasks).join();

        long expectedTotal = LongStream.range(0, 9_000).sum();

        Assertions.assertEquals(expectedTotal, shopStatisticsService.getTotalRevenue());


    }

    @Test
    void shouldCalculateStatsAfterRefund() {

        List<ReceiptDto> receipts = new ArrayList<>();
        receipts.add(ReceiptDto.builder()
                .id(1L)
                .count(1L)
                .price(10_000L)
                .build());
        receipts.add(ReceiptDto.builder()
                .id(2L)
                .count(1L)
                .price(20_000L)
                .build());
        receipts.add(ReceiptDto.builder()
                .id(3L)
                .count(2L)
                .price(20_000L)
                .build());

        receipts.forEach(receiptService::processReceipt);

        refundService.processRefund(3L);

        Assertions.assertEquals(2L, shopStatisticsService.getTotalCount());
        Assertions.assertEquals(30_000, shopStatisticsService.getTotalRevenue());

    }

}