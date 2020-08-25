package com.justdoit.demo.scheduling;

import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Profile("pre")
public class ScheduledJobPre {

	/**
	 * In this case, the duration between the end of last execution and the start of
	 * next execution is fixed. The task always waits until the previous one is
	 * finished.
	 * 
	 * This option should be used when it's mandatory that the previous execution is
	 * completed before running again.
	 */
	@Scheduled(fixedDelay = 3000)
	public void scheduleFixedDelayTask() {
		System.out.println("Fixed delay task - " + System.currentTimeMillis() / 1000);
	}

	/**
	 * This option should be used when each execution of the task is independent.
	 * 
	 * Note that scheduled tasks don't run in parallel by default. So even if we
	 * used fixedRate, the next task won't be invoked until the previous one is
	 * done.
	 * 
	 * If we want to support parallel behavior in scheduled tasks, we need to add
	 * the @Async annotation
	 */
	@Scheduled(fixedRate = 3000)
	public void scheduleFixedRateTask() {
		System.out.println("Fixed rate task - " + System.currentTimeMillis() / 1000);
	}

	@Scheduled(fixedDelay = 60000)
	public void cleanTempDir() {
		log.info("hello");
	}
}