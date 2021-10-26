package com.georgeisaev.fillthegaps.completablefuture.controller;

import com.georgeisaev.fillthegaps.completablefuture.service.PriceAggregatorService;
import com.georgeisaev.fillthegaps.completablefuture.service.PriceRetrieverService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

import static com.georgeisaev.fillthegaps.completablefuture.controller.PriceControllerTest.EXPECTED_LOWEST_PRICE;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Import(PriceControllerTestConfig.class)
@WebMvcTest(PriceController.class)
class PriceControllerTest {

    protected static final double EXPECTED_LOWEST_PRICE = 88.88d;

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldRetrieveLowestPrice() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/items/1/prices/lowest"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(String.valueOf(EXPECTED_LOWEST_PRICE)));
    }

}

@Configuration
@Import(PriceAggregatorService.class)
class PriceControllerTestConfig {

    @Bean
    @Primary
    PriceRetrieverService priceRetrieverService() {
        PriceRetrieverService priceRetrieverService = Mockito.mock(PriceRetrieverService.class);
        LongStream.rangeClosed(1, 100).forEach(shopId -> {
            when(priceRetrieverService.findPrice(1L, shopId))
                    .thenReturn(ThreadLocalRandom.current().nextDouble(90d, 10_000d));
        });
        when(priceRetrieverService.findPrice(1L, 3))
                .thenReturn(EXPECTED_LOWEST_PRICE);
        return priceRetrieverService;
    }

}