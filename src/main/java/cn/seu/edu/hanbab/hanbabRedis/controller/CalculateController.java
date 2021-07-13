package cn.seu.edu.hanbab.hanbabRedis.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Hanbab
 * @Date: 2021/6/21
 */

@Api("Calculate")
@RestController
@RequestMapping("/api/Calculate")
public class CalculateController {

    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation("set+list<set>->sets difference")
    @RequestMapping(value = "/setDifference", method = RequestMethod.GET)//need test from front
    public ResponseEntity<Map<List<String>, Set<Object>>> getSetDifference(
            @RequestBody Map<String, List<String>> map
    ){
        Iterator<Map.Entry<String, List<String>>> entries = map.entrySet().iterator();
        Map<List<String>, Set<Object>> ANSWER = new HashMap<>();
        while(entries.hasNext()){
            Map.Entry<String, List<String>> entry = entries.next();
            List<String> value = entry.getValue();
            if(value.add(entry.getKey())){
                ANSWER.put(value, redisTemplate
                        .opsForSet()
                        .difference(entry.getKey(),entry.getValue()));
            }
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .body(ANSWER);
    }

    @ApiOperation("list<key>->delete keys")
    @RequestMapping(value = "/deleteByKeys", method = RequestMethod.DELETE)
    public ResponseEntity<Long> deleteByKeys(
            @RequestBody List<String> list
    ){
        Long counter = redisTemplate.delete(list);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .body(counter);
    }
}
