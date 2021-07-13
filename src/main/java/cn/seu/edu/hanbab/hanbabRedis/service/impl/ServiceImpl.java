package cn.seu.edu.hanbab.hanbabRedis.service.impl;

import cn.seu.edu.hanbab.hanbabRedis.service.redisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @function: for basic services
 *
 * @Author: Hanbab
 * @Date: 2021/6/21
 */

@Service
public class ServiceImpl implements redisService {
    @Autowired
    RedisTemplate redisTemplate;

    public Map<List<String>, Set<Object>> getSetDifference(Map<String, List<String>> map){
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
        return ANSWER;
    }

    public Long delete(List<String> list){
        Long counter = redisTemplate.delete(list);
        return counter;
    }

    public Map<String,Object> getHash(String keyword){ //keyword example "Keyword:10000863" "Outcome:613_2493_1100"
        Map<String, Object> keyword_message = redisTemplate
                .opsForHash()
                .entries(keyword);
        return keyword_message;
    }

    public Map<Object, Double> getZSetAsc(String keyword){
        Set<ZSetOperations.TypedTuple<Object>> tuples = redisTemplate
                .opsForZSet()
                .rangeByScoreWithScores(keyword, 0, 1);
        Iterator<ZSetOperations.TypedTuple<Object>> iterator = tuples.iterator();
        Map<Object,Double> zset_map = new LinkedHashMap<>();
        while (iterator.hasNext()){
            ZSetOperations.TypedTuple<Object> typedTuple = iterator.next();
            zset_map.put(typedTuple.getValue(),typedTuple.getScore());
        }
        return zset_map;

    }

    public Map<Object, Double> getZSetDesc(String keyword){
        Set<ZSetOperations.TypedTuple<Object>> tuples = redisTemplate
                .opsForZSet()
                .reverseRangeByScoreWithScores(keyword, 0, 1);
        Iterator<ZSetOperations.TypedTuple<Object>> iterator = tuples.iterator();
        Map<Object,Double> zset_map = new LinkedHashMap<>();
        while (iterator.hasNext()){
            ZSetOperations.TypedTuple<Object> typedTuple = iterator.next();
            zset_map.put(typedTuple.getValue(),typedTuple.getScore());
        }
        return zset_map;
    }

    public Set<Object> getZSetNoScoresAsc(String keyword){
        Set<Object> set = redisTemplate
                .opsForZSet()
                .range(keyword,0,-1);
        return set;
    }

    public Set<Object> getZSetNoScoresDesc(String keyword){
        Set<Object> set = redisTemplate
                .opsForZSet()
                .reverseRange(keyword,0,-1);
        return set;
    }

    public Object getDetailedHash(String keyword, String key){
        Object keyword_detailed_message = redisTemplate
                .opsForHash()
                .entries(keyword)
                .get(key);
        return keyword_detailed_message;
    }

    public void hashPut(Boolean type, String key, Map<Object, Object> map){
        if(type){ //hard put
            redisTemplate.opsForHash().putAll(key, map);
        }else{ //soft put
            Iterator<Map.Entry<Object, Object>> entryIterator = map.entrySet().iterator();
            while (entryIterator.hasNext()){
                Map.Entry<Object, Object> entry = entryIterator.next();
                redisTemplate.opsForHash().putIfAbsent(key, entry.getKey(), entry.getValue());
            }
        }
    }

    public int deleteHashDetail(String key, List<String> list){
        Iterator<String> iterator = list.listIterator();
        int counter = 0;
        while (iterator.hasNext()){
            String str = iterator.next();
            counter += redisTemplate.opsForHash().delete(key, str);
        }
        return counter;
    }

    public int addZSetDetail(String key, Map<Object, Double> map){
        Iterator<Map.Entry<Object, Double>> entryIterator = map.entrySet().iterator();
        int counter = 0;
        while (entryIterator.hasNext()){
            Map.Entry<Object, Double> entry = entryIterator.next();
            if(redisTemplate.opsForZSet().add(key, entry.getKey(), entry.getValue())){
                counter++;
            }
        }
        return counter;
    }

    public int addSetDetail(String key, List<Object> list){
        Iterator<Object> iterator = list.listIterator();
        int counter = 0;
        while (iterator.hasNext()){
            Object obj = iterator.next();
            counter += redisTemplate.opsForSet().add(key, obj);
        }
        return counter;
    }



}
