package io.github.singlerr.retrowave.utils;

import mod.chiselsandbits.client.model.data.ChiseledBlockModelDataExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ThreadExecutors {

    private static final ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    public static ExecutorService getSingleThreadExecutor() {
        return singleThreadExecutor;
    }
}
