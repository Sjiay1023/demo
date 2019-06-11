package com.ssm.demo.controller;

import com.ssm.demo.response.BaseResponse;
import com.ssm.demo.response.StatusCode;
import com.ssm.demo.service.InitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/8/25.
 */
@RestController
public class ConcurrencyController {

    private static final Logger log= LoggerFactory.getLogger(ConcurrencyController.class);

    private static final String Prefix="concurrency";

    @Autowired
    private InitService initService;

    @RequestMapping(value = Prefix+"/robbing/thread",method = RequestMethod.GET)
    public BaseResponse robbingThread(){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        initService.generateMultiThread();
        return response;
    }
}







































