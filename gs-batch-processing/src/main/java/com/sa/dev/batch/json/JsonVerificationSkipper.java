package com.sa.dev.batch.json;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.stereotype.Component;

/**
 * Created by qu04jl on 5-9-2017.
 */
@Component
@Slf4j
public class JsonVerificationSkipper implements SkipPolicy {


    @Override
    public boolean shouldSkip(Throwable exception, int skipCount) throws SkipLimitExceededException {
        log.info(exception.getMessage());
        log.info("skip count is" + skipCount);
        return true;
    }
}