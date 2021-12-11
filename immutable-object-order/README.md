
```shell
Benchmark                                                                          Mode    Cnt     Score     Error   Units
OrderServiceBenchmarkTest.shouldProceedImmutable                                  thrpt      3     1,115 ±  29,331  ops/ms
OrderServiceBenchmarkTest.shouldProceedMutable                                    thrpt      3     1,086 ±  31,331  ops/ms
OrderServiceBenchmarkTest.shouldProceedImmutable                                   avgt      3    10,823 ± 298,453   ms/op
OrderServiceBenchmarkTest.shouldProceedMutable                                     avgt      3    14,684 ± 384,639   ms/op
OrderServiceBenchmarkTest.shouldProceedImmutable                                 sample  36925     1,028 ±   0,917   ms/op
OrderServiceBenchmarkTest.shouldProceedImmutable:shouldProceedImmutable·p0.00    sample            0,005             ms/op
OrderServiceBenchmarkTest.shouldProceedImmutable:shouldProceedImmutable·p0.50    sample            0,026             ms/op
OrderServiceBenchmarkTest.shouldProceedImmutable:shouldProceedImmutable·p0.90    sample            0,029             ms/op
OrderServiceBenchmarkTest.shouldProceedImmutable:shouldProceedImmutable·p0.95    sample            0,030             ms/op
OrderServiceBenchmarkTest.shouldProceedImmutable:shouldProceedImmutable·p0.99    sample            0,039             ms/op
OrderServiceBenchmarkTest.shouldProceedImmutable:shouldProceedImmutable·p0.999   sample            0,110             ms/op
OrderServiceBenchmarkTest.shouldProceedImmutable:shouldProceedImmutable·p0.9999  sample         2836,639             ms/op
OrderServiceBenchmarkTest.shouldProceedImmutable:shouldProceedImmutable·p1.00    sample         3523,215             ms/op
OrderServiceBenchmarkTest.shouldProceedMutable                                   sample  32500     1,121 ±   0,998   ms/op
OrderServiceBenchmarkTest.shouldProceedMutable:shouldProceedMutable·p0.00        sample            0,004             ms/op
OrderServiceBenchmarkTest.shouldProceedMutable:shouldProceedMutable·p0.50        sample            0,027             ms/op
OrderServiceBenchmarkTest.shouldProceedMutable:shouldProceedMutable·p0.90        sample            0,031             ms/op
OrderServiceBenchmarkTest.shouldProceedMutable:shouldProceedMutable·p0.95        sample            0,035             ms/op
OrderServiceBenchmarkTest.shouldProceedMutable:shouldProceedMutable·p0.99        sample            0,046             ms/op
OrderServiceBenchmarkTest.shouldProceedMutable:shouldProceedMutable·p0.999       sample            0,166             ms/op
OrderServiceBenchmarkTest.shouldProceedMutable:shouldProceedMutable·p0.9999      sample         2803,881             ms/op
OrderServiceBenchmarkTest.shouldProceedMutable:shouldProceedMutable·p1.00        sample         3024,093             ms/op
OrderServiceBenchmarkTest.shouldProceedImmutable                                     ss      3     0,112 ±   0,827   ms/op
OrderServiceBenchmarkTest.shouldProceedMutable                                       ss      3     0,196 ±   0,836   ms/op

Benchmark result is saved to /dev/null

Process finished with exit code 0
```