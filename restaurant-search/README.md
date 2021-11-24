ConcurrentHashMap<String, Long> VS ConcurrentHashMap<String, LongAdder> benchmarks:
```shell
Benchmark                                                                             Mode      Cnt   Score    Error   Units
RestaurantSearchServiceBenchmarkTest.addToStatLong                                   thrpt       10  31,531 ±  0,837  ops/ms
RestaurantSearchServiceBenchmarkTest.addToStatLongAdder                              thrpt       10  35,298 ±  0,228  ops/ms
RestaurantSearchServiceBenchmarkTest.addToStatLong                                    avgt       10   0,035 ±  0,002   ms/op
RestaurantSearchServiceBenchmarkTest.addToStatLongAdder                               avgt       10   0,029 ±  0,002   ms/op
RestaurantSearchServiceBenchmarkTest.addToStatLong                                  sample  1371262   0,038 ±  0,001   ms/op
RestaurantSearchServiceBenchmarkTest.addToStatLong:addToStatLong·p0.00              sample            0,006            ms/op
RestaurantSearchServiceBenchmarkTest.addToStatLong:addToStatLong·p0.50              sample            0,032            ms/op
RestaurantSearchServiceBenchmarkTest.addToStatLong:addToStatLong·p0.90              sample            0,043            ms/op
RestaurantSearchServiceBenchmarkTest.addToStatLong:addToStatLong·p0.95              sample            0,049            ms/op
RestaurantSearchServiceBenchmarkTest.addToStatLong:addToStatLong·p0.99              sample            0,070            ms/op
RestaurantSearchServiceBenchmarkTest.addToStatLong:addToStatLong·p0.999             sample            0,221            ms/op
RestaurantSearchServiceBenchmarkTest.addToStatLong:addToStatLong·p0.9999            sample           12,497            ms/op
RestaurantSearchServiceBenchmarkTest.addToStatLong:addToStatLong·p1.00              sample           23,658            ms/op
RestaurantSearchServiceBenchmarkTest.addToStatLongAdder                             sample  1712873   0,029 ±  0,001   ms/op
RestaurantSearchServiceBenchmarkTest.addToStatLongAdder:addToStatLongAdder·p0.00    sample            0,006            ms/op
RestaurantSearchServiceBenchmarkTest.addToStatLongAdder:addToStatLongAdder·p0.50    sample            0,029            ms/op
RestaurantSearchServiceBenchmarkTest.addToStatLongAdder:addToStatLongAdder·p0.90    sample            0,032            ms/op
RestaurantSearchServiceBenchmarkTest.addToStatLongAdder:addToStatLongAdder·p0.95    sample            0,034            ms/op
RestaurantSearchServiceBenchmarkTest.addToStatLongAdder:addToStatLongAdder·p0.99    sample            0,043            ms/op
RestaurantSearchServiceBenchmarkTest.addToStatLongAdder:addToStatLongAdder·p0.999   sample            0,144            ms/op
RestaurantSearchServiceBenchmarkTest.addToStatLongAdder:addToStatLongAdder·p0.9999  sample            1,064            ms/op
RestaurantSearchServiceBenchmarkTest.addToStatLongAdder:addToStatLongAdder·p1.00    sample           16,105            ms/op
RestaurantSearchServiceBenchmarkTest.addToStatLong                                      ss       10   0,126 ±  0,057   ms/op
RestaurantSearchServiceBenchmarkTest.addToStatLongAdder                                 ss       10   0,153 ±  0,048   ms/op
```