package com.merapar.loadtest;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Future;

@Service
public interface JMeterService {

    boolean execute(Test test);

    void executeAsync(Test test);

    Future<Boolean> executeAsyncWithResult(Test test);

    DeferredResult<String> getStatus();
}
