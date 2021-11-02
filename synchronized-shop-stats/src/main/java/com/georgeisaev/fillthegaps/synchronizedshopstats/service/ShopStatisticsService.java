package com.georgeisaev.fillthegaps.synchronizedshopstats.service;

import org.springframework.stereotype.Service;

@Service
public class ShopStatisticsService {

    private Long totalCount = 0L;
    private Long totalRevenue = 0L;

    public synchronized void addData(Long count, Long price) {
        totalCount += count;
        totalRevenue += (price * count);
    }

    public synchronized void removeData(Long count, Long price) {
        totalCount -= count;
        totalRevenue -= (price * count);
    }

    public synchronized Long getTotalCount() {
        return totalCount;
    }

    public synchronized Long getTotalRevenue() {
        return totalRevenue;
    }

    public synchronized void reset() {
        totalCount = 0L;
        totalRevenue = 0L;
    }

}
