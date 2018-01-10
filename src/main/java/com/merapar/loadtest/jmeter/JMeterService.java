package com.merapar.loadtest.jmeter;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Future;

@Service
public interface JMeterService {

    boolean execute(JMeterParameters parameters);

    void executeAsync(JMeterParameters parameters);

    Future<Boolean> executeAsyncWithResult(JMeterParameters parameters);

    DeferredResult<String> getStatus();
}
