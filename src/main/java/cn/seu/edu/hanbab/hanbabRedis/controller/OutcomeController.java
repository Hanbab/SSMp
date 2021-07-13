package cn.seu.edu.hanbab.hanbabRedis.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Hanbab
 * @Date: 2021/06/13/16:16
 */

@Api("Outcome")
@RestController
@RequestMapping("/api/outcome")
public class OutcomeController {

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * outcome search
     *
     * @Author: Hanbab
     */
    @ApiOperation("outcome->message")
    @RequestMapping(value = "/otMes/{outcome}", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> getByOutcome(
            @PathVariable String outcome){
        Map<String, Object> outcome_message = redisTemplate
                .opsForHash()
                .entries("Outcome:"+ outcome);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .body(outcome_message);
    }

    @ApiOperation("outcome+key->detailed message")
    @RequestMapping(value = "/otMes/{outcome}/{key}", method = RequestMethod.GET)
    public ResponseEntity<Object> getDetailedOutcomeMessage(
            @PathVariable String outcome,
            @PathVariable String key){
        Object outcome_detailed_message = redisTemplate
                .opsForHash()
                .entries("Outcome:"+outcome)
                .get(key);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .body(outcome_detailed_message);
    }

    @ApiOperation("key->asc search outcome & neighbor Zset")
    @RequestMapping(value = "/otNbAsc/{keyword}", method = RequestMethod.GET)
    public ResponseEntity<Map<Object, Double>> getOutcomeNeighborByScoresAsc(
            @PathVariable String keyword
    ){
        Set<ZSetOperations.TypedTuple<Object>> tuples = redisTemplate
                .opsForZSet()
                .rangeByScoreWithScores("Outcome:Neighbor:"+keyword, 0, 1);
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

    @ApiOperation("key->desc search outcome & neighbor Zset")
    @RequestMapping(value = "/otNbDesc/{keyword}", method = RequestMethod.GET)
    public ResponseEntity<Map<Object, Double>> getOutcomeNeighborByScoresDesc(
            @PathVariable String keyword
    ){
        Set<ZSetOperations.TypedTuple<Object>> tuples = redisTemplate
                .opsForZSet()
                .reverseRangeByScoreWithScores("Outcome:Neighbor:"+keyword, 0, 1);
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

    @ApiOperation("key->asc search outcome & neighbor set(Z)")
    @RequestMapping(value = "/otNbMbAsc/{keyword}", method = RequestMethod.GET)
    public ResponseEntity<Set<Object>> getOutcomeNeighborOnlyMembersAsc(
            @PathVariable String keyword
    ){
        Set<Object> set = redisTemplate
                .opsForZSet()
                .range("Outcome:Neighbor:"+keyword,0,-1);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .body(set);
    }

    @ApiOperation("key->desc search outcome & neighbor set(Z)")
    @RequestMapping(value = "/otNbMbDesc/{keyword}", method = RequestMethod.GET)
    public ResponseEntity<Set<Object>> getOutcomeNeighborOnlyMembersDesc(
            @PathVariable String keyword
    ){
        Set<Object> set = redisTemplate
                .opsForZSet()
                .reverseRange("Outcome:Neighbor:"+keyword,0,-1);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .body(set);
    }

    @ApiOperation("key->search outcome & keys set")
    @RequestMapping(value = "/otKeysSet/{keyword}", method = RequestMethod.GET)
    public ResponseEntity<Set<Object>> getOutcomeKeysSet(
            @PathVariable String keyword
    ){
        Set<Object> set = redisTemplate
                .opsForSet()
                .members("Outcome:Keys:"+keyword);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .body(set);
    }
}
