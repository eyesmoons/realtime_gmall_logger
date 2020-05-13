package com.atguigu.gmall1122.logger.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //返回字符串值  ==  @Controller + 方法上 @ResponseBody
@Slf4j
public class LoggerController {

    private final static Logger logger = LoggerFactory.getLogger(LoggerController.class);

    @Autowired
    KafkaTemplate kafkaTemplate;

    @RequestMapping("/applog")
    public  String applog(@RequestBody String logString ){
        logger.info(logString);
        JSONObject jsonObject = JSON.parseObject(logString);
        if(jsonObject.getString("start")!=null&&jsonObject.getString("start").length()>0){
            kafkaTemplate.send("GMALL_START",logString);
        }else{
            kafkaTemplate.send("GMALL_EVENT",logString);
        }
        return  "success";
    }
}
