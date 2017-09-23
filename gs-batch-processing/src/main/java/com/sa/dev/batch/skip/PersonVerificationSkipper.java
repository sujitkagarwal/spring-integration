package com.sa.dev.batch.skip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

/**
 * Created by qu04jl on 4-9-2017.
 */
public class PersonVerificationSkipper implements SkipPolicy {
    private static final Logger log = LoggerFactory.getLogger(PersonVerificationSkipper.class);
    @Override
    public boolean shouldSkip(Throwable exception, int skipCount) throws SkipLimitExceededException {
           log.info(exception.getMessage());
           log.info("skip count is"+skipCount);
            return true;
    }


}
