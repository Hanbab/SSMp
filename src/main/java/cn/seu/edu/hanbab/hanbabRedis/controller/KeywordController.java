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
 * @Date: 2021/06/13/15:31
 */

@Api("Keyword")
@RestController
@RequestMapping("/api/keyword")
public class KeywordController {

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * keyword search
     *
     * @Author: Hanbab
     */
    @ApiOperation("keyword->message")
    @RequestMapping(value = "/kwMes/{keyword}", method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> getByKeyword(
            @PathVariable String keyword){
        Map<String, Object> keyword_message = redisTemplate
                .opsForHash()
                .entries("Keyword:"+keyword);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .body(keyword_message);
    }

    @ApiOperation("keyword + key->detailed message")
    @RequestMapping(value = "/kwMes/{keyword}/{key}", method = RequestMethod.GET)
    public ResponseEntity<Object> getDetailedKeywordMessage(
            @PathVariable String keyword,
            @PathVariable String key){
        Object keyword_detailed_message = redisTemplate
                .opsForHash()
                .entries("Keyword:"+keyword)
                .get(key);
       return ResponseEntity
               .status(HttpStatus.OK)
               .header(HttpHeaders.CONTENT_DISPOSITION)
               .body(keyword_detailed_message);
    }

    @ApiOperation("key->asc search keyword & neighbor Zset")
    @RequestMapping(value = "/kwNbAsc/{keyword}", method = RequestMethod.GET)
    public ResponseEntity<Map<Object, Double>> getKeywordNeighborByScoresAsc(
            @PathVariable String keyword
    ){
        Set<ZSetOperations.TypedTuple<Object>> tuples = redisTemplate
                .opsForZSet()
                .rangeByScoreWithScores("Keyword:Neighbor:"+keyword, 0, 1);
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

    @ApiOperation("key->desc search keyword & neighbor Zset")
    @RequestMapping(value = "/kwNbDesc/{keyword}", method = RequestMethod.GET)
    public ResponseEntity<Map<Object, Double>> getKeywordNeighborByScoresDesc(
            @PathVariable String keyword
    ){
        Set<ZSetOperations.TypedTuple<Object>> tuples = redisTemplate
                .opsForZSet()
                .reverseRangeByScoreWithScores("Keyword:Neighbor:"+keyword, 0, 1);
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

    @ApiOperation("key->asc search keyword & neighbor set(Z)")
    @RequestMapping(value = "/kwNbMbAsc/{keyword}", method = RequestMethod.GET)
    public ResponseEntity<Set<Object>> getKeywordNeighborOnlyMembersAsc(
            @PathVariable String keyword
    ){
        Set<Object> set = redisTemplate
                .opsForZSet()
                .range("Keyword:Neighbor:"+keyword,0,-1);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .body(set);
    }

    @ApiOperation("key->desc search keyword & neighbor set(Z)")
    @RequestMapping(value = "/kwNbMbDesc/{keyword}", method = RequestMethod.GET)
    public ResponseEntity<Set<Object>> getKeywordNeighborOnlyMembersDesc(
            @PathVariable String keyword
    ){
        Set<Object> set = redisTemplate
                .opsForZSet()
                .reverseRange("Keyword:Neighbor:"+keyword,0,-1);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .body(set);
    }


}
