package com.chess.card.api.factory;

import lombok.NonNull;
import lombok.ToString;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.atomic.AtomicLong;

@ToString
public class ChessForkJoinWorkerThreadFactory implements ForkJoinPool.ForkJoinWorkerThreadFactory {
    private final String namePrefix;
    private final AtomicLong threadNumber = new AtomicLong(1);

    public ChessForkJoinWorkerThreadFactory(@NonNull String namePrefix) {
        this.namePrefix = namePrefix;
    }

    @Override
    public final ForkJoinWorkerThread newThread(ForkJoinPool pool) {
        ForkJoinWorkerThread thread = ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(pool);
        thread.setContextClassLoader(this.getClass().getClassLoader());
        thread.setName(namePrefix +"-"+thread.getPoolIndex()+"-"+threadNumber.getAndIncrement());
        return thread;
    }
}