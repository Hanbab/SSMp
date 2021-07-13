package cn.seu.edu.hanbab.hanbabRedis.service;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Hanbab
 * @Date: 2021/6/21
 */

public interface redisService {

    Map<List<String>, Set<Object>> getSetDifference(Map<String, List<String>> map);

    Long delete(List<String> list);

    Map<String,Object> getHash(String keyword);

    Map<Object, Double> getZSetAsc(String keyword);

    Map<Object, Double> getZSetDesc(String keyword);

    Set<Object> getZSetNoScoresAsc(String keyword);

    Set<Object> getZSetNoScoresDesc(String keyword);

    Object getDetailedHash(String keyword, String key);

    void hashPut(Boolean type, String key, Map<Object, Object> map);

    int deleteHashDetail(String key, List<String> list);

    int addZSetDetail(String key, Map<Object, Double> map);

    int addSetDetail(String key, List<Object> list);
}
