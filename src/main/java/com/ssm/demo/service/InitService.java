package com.ssm.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

/**
 * Created by steadyjack on 2018/8/24.
 */
@Service
public class InitService {
    private static final Logger log= LoggerFactory.getLogger(InitService.class);

    public static final int ThreadNum = 10000;

    private static int mobile=0;

    @Autowired
    private ConcurrencyService concurrencyService;

    @Autowired
    private CommonMqService commonMqService;

    public void generateMultiThread(){
        log.info("开始初始化线程数----> ");

        try {
            CountDownLatch countDownLatch=new CountDownLatch(1);
            for (int i=0;i<ThreadNum;i++){
                new Thread(new RunThread(countDownLatch)).start();
            }

            //TODO：启动多个线程
            countDownLatch.countDown();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private class RunThread implements Runnable{
        private final CountDownLatch startLatch;

        public RunThread(CountDownLatch startLatch) {
            this.startLatch = startLatch;
        }

        public void run() {
            try {
                //TODO：线程等待
                startLatch.await();
                mobile += 1;

                //TODO：发送消息入抢单队列：env.getProperty("user.order.queue.name")
                commonMqService.sendRobbingMsgV2(String.valueOf(mobile));

                //concurrencyService.manageRobbing(String.valueOf(mobile));//--v1.0
                //commonMqService.sendRobbingMsg(String.valueOf(mobile));//+v2.0
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
