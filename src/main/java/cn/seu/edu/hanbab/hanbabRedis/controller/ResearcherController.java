package cn.seu.edu.hanbab.hanbabRedis.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Hanbab
 * @Date: 2021/6/10
 */


@Api("Researcher")
@RestController
@RequestMapping("/api/researcher")
public class ResearcherController {

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * researcher search
     *
     * @Author: Hanbab
     */
    @ApiOperation("researcher->message")
    @RequestMapping(value = "/rshMes/{faculty_id}", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> getByFaculty_id(
            @PathVariable String faculty_id){
        Map<String, String> researcher_message = redisTemplate
                .opsForHash()
                .entries("Researcher:"+ faculty_id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .body(researcher_message);
    }

    @ApiOperation("researcher+key->detailed message")
    @RequestMapping(value = "/rshMes/{faculty_id}/{info}", method = RequestMethod.GET)
    public ResponseEntity<Object> getDetailedResearcherMessage(
            @PathVariable String faculty_id,
            @PathVariable String info){
            Object researcher_detailed_message = redisTemplate
                    .opsForHash()
                    .entries("Researcher:"+ faculty_id)
                    .get(info);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .body(researcher_detailed_message);
    }

}
