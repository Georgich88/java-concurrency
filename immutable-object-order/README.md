
```shell
Benchmark                                                                          Mode  Cnt     Score       Error   Units
OrderServiceBenchmarkTest.shouldProceedImmutable                                  thrpt    3     0,001 ±     0,014  ops/ms
OrderServiceBenchmarkTest.shouldProceedMutable                                    thrpt    3     0,001 ±     0,014  ops/ms
OrderServiceBenchmarkTest.shouldProceedImmutable                                   avgt    3  3727,907 ± 53622,121   ms/op
OrderServiceBenchmarkTest.shouldProceedMutable                                     avgt    3  7384,789 ± 28983,345   ms/op
OrderServiceBenchmarkTest.shouldProceedImmutable                                 sample   15  2811,893 ±  3475,816   ms/op
OrderServiceBenchmarkTest.shouldProceedImmutable:shouldProceedImmutable·p0.00    sample          0,057               ms/op
OrderServiceBenchmarkTest.shouldProceedImmutable:shouldProceedImmutable·p0.50    sample       2604,663               ms/op
OrderServiceBenchmarkTest.shouldProceedImmutable:shouldProceedImmutable·p0.90    sample       8690,598               ms/op
OrderServiceBenchmarkTest.shouldProceedImmutable:shouldProceedImmutable·p0.95    sample       8740,930               ms/op
OrderServiceBenchmarkTest.shouldProceedImmutable:shouldProceedImmutable·p0.99    sample       8740,930               ms/op
OrderServiceBenchmarkTest.shouldProceedImmutable:shouldProceedImmutable·p0.999   sample       8740,930               ms/op
OrderServiceBenchmarkTest.shouldProceedImmutable:shouldProceedImmutable·p0.9999  sample       8740,930               ms/op
OrderServiceBenchmarkTest.shouldProceedImmutable:shouldProceedImmutable·p1.00    sample       8740,930               ms/op
OrderServiceBenchmarkTest.shouldProceedMutable                                   sample   21  1842,134 ±  2578,734   ms/op
OrderServiceBenchmarkTest.shouldProceedMutable:shouldProceedMutable·p0.00        sample          0,054               ms/op
OrderServiceBenchmarkTest.shouldProceedMutable:shouldProceedMutable·p0.50        sample          0,542               ms/op
OrderServiceBenchmarkTest.shouldProceedMutable:shouldProceedMutable·p0.90        sample       8115,139               ms/op
OrderServiceBenchmarkTest.shouldProceedMutable:shouldProceedMutable·p0.95        sample       9252,635               ms/op
OrderServiceBenchmarkTest.shouldProceedMutable:shouldProceedMutable·p0.99        sample       9328,132               ms/op
OrderServiceBenchmarkTest.shouldProceedMutable:shouldProceedMutable·p0.999       sample       9328,132               ms/op
OrderServiceBenchmarkTest.shouldProceedMutable:shouldProceedMutable·p0.9999      sample       9328,132               ms/op
OrderServiceBenchmarkTest.shouldProceedMutable:shouldProceedMutable·p1.00        sample       9328,132               ms/op
OrderServiceBenchmarkTest.shouldProceedImmutable                                     ss    3     0,167 ±     0,613   ms/op
OrderServiceBenchmarkTest.shouldProceedMutable                                       ss    3     0,296 ±     5,272   ms/op

Benchmark result is saved to /dev/null

Process finished with exit code 0

```