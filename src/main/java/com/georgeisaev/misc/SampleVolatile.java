package com.georgeisaev.misc;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.IntStream;

public class SampleVolatile {

	private volatile int common;
	private volatile boolean ready;
	private static final Set<Integer> result;

	static {
		result = new ConcurrentSkipListSet<>();
	}

	public static void main(String[] args) throws InterruptedException {
		IntStream.range(0, 1000).forEach(i -> {
			var sample = new SampleVolatile();
			if (i % 2 == 0) {
				sample.new Modifier().run();
				sample.new Accessor().run();
			} else {
				sample.new Modifier().run();
				sample.new Accessor().run();
			}
		});
		Thread.sleep(1_000);
		System.out.println(result);
	}

	class Modifier implements Runnable {

		@Override
		public void run() {
			IntStream.range(0, 43).forEach(value -> common = value);
			ready = true;
			common = 43;
		}

	}

	class Accessor implements Runnable {

		@Override
		public void run() {
			while (!ready) {
				//Thread.onSpinWait();
			}
			result.add(common);
		}

	}

}
