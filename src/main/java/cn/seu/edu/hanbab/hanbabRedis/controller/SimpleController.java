package cn.seu.edu.hanbab.hanbabRedis.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Hanbab
 * @Date: 2021/6/17
 */

@Api("Simple")
@RestController
@RequestMapping("/api/Simple")
public class SimpleController {

    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation("set+list<set>->sets difference")
    @RequestMapping(value = "/setDifference", method = RequestMethod.POST)//Front end assistance is required for testing
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

    /**
     * Fixed point query
     *
     * @Author: Hanbab
     */
    @ApiOperation("key->hash")
    @RequestMapping(value = "/fixedPointQuery/hash/{keyword}", method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> getHash(
            @PathVariable String keyword //keyword example "Keyword:10000863" "Outcome:613_2493_1100"
    ){
        Map<String, Object> keyword_message = redisTemplate
                .opsForHash()
                .entries(keyword);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .body(keyword_message);
    }

    @ApiOperation("sortMethod+key->Zset")
    @RequestMapping(value = "/fixedPointQuery/ZSet/{keyword}", method = RequestMethod.POST)
    public ResponseEntity<Map<Object, Double>> getZSetAsc(
            @PathVariable String keyword //keyword example "Keyword:10000863" "Outcome:613_2493_1100"
    ){
        Set<ZSetOperations.TypedTuple<Object>> tuples = redisTemplate
                .opsForZSet()
                .rangeByScoreWithScores(keyword, 0, 1);
        Iterator<ZSetOperations.TypedTuple<Object>> iterator = tuples.iterator();
        Map<Object,Double> zset_map = new LinkedHashMap<>();
        while (iterator.hasNext()){
            ZSetOperations.TypedTuple<Object> typedTuple = iterator.next();
            zset_map.put(typedTuple.getValue(),typedTuple.getScore());
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .body(zset_map);

    }

    @RequestMapping(value = "/fixedPointQuery/ZSetDe/{keyword}", method = RequestMethod.POST)
    public ResponseEntity<Map<Object, Double>> getZSetDesc(
            @PathVariable String keyword
    ){
        Set<ZSetOperations.TypedTuple<Object>> tuples = redisTemplate
                .opsForZSet()
                .reverseRangeByScoreWithScores(keyword, 0, 1);
        Iterator<ZSetOperations.TypedTuple<Object>> iterator = tuples.iterator();
        Map<Object,Double> zset_map = new LinkedHashMap<>();
        while (iterator.hasNext()){
            ZSetOperations.TypedTuple<Object> typedTuple = iterator.next();
            zset_map.put(typedTuple.getValue(),typedTuple.getScore());
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .body(zset_map);
    }

    @RequestMapping(value = "/fixedPointQuery/ZSetMb/{keyword}", method = RequestMethod.GET)
    public ResponseEntity<Set<Object>> getZSetNoScoresAsc(
            @PathVariable String keyword
    ){
        Set<Object> set = redisTemplate
                .opsForZSet()
                .range(keyword,0,-1);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .body(set);
    }

    @RequestMapping(value = "/fixedPointQuery/ZSetDeMb/{keyword}", method = RequestMethod.GET)
    public ResponseEntity<Set<Object>> getZSetNoScoresDesc(
            @PathVariable String keyword
    ){
        Set<Object> set = redisTemplate
                .opsForZSet()
                .reverseRange(keyword,0,-1);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .body(set);
    }

    @RequestMapping(value = "/fixedPointQuery/hash/{keyword}/{key}", method = RequestMethod.GET)
    public ResponseEntity<Object> getDetailedHash(
            @PathVariable String keyword,
            @PathVariable String key){
        Object keyword_detailed_message = redisTemplate
                .opsForHash()
                .entries(keyword)
                .get(key);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .body(keyword_detailed_message);
    }


}
