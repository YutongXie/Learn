package com.huitong.learn.schedule;

import com.huitong.learn.BatchConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class ScheduleBatch {
    @Autowired
   private BatchConfiguration batchConfiguration;

   @Scheduled(initialDelay = 10000, fixedRate = 10000)
   public void fixedRateBatch() {
       System.out.println("Fixed rate batch running...");
       batchConfiguration.run();
   }
}
