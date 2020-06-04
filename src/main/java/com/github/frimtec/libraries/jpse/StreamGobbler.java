package com.github.frimtec.libraries.jpse;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

final class StreamGobbler implements Runnable {
    private final InputStream inputStream;
    private final Consumer<String> consumeInputLine;
    private final CountDownLatch finishedLatch = new CountDownLatch(1);

    public StreamGobbler(InputStream inputStream, Consumer<String> consumeInputLine) {
        this.inputStream = inputStream;
        this.consumeInputLine = consumeInputLine;
    }

    public void run() {
        new BufferedReader(new InputStreamReader(inputStream))
                .lines()
                .forEach(consumeInputLine);
        this.finishedLatch.countDown();
    }

    public void waitTillFinished() throws InterruptedException {
        finishedLatch.await();
    }
}
