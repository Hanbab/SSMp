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
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Hanbab
 * @Date: 2021/06/13/16:32
 */

@Api("Project")
@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * project search
     *
     * @Author: Hanbab
     */
    @ApiOperation("project->message")
    @RequestMapping(value = "/pjMes/{project}", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> getByProject(
            @PathVariable String project){
        Map<String, Object> project_message = redisTemplate
                .opsForHash()
                .entries("Project:"+ project);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .body(project_message);
    }

    @ApiOperation("project+key->detailed message")
    @RequestMapping(value = "/pjMes/{project}/{key}", method = RequestMethod.GET)
    public ResponseEntity<Object> getDetailedProjectMessage(
            @PathVariable String project,
            @PathVariable String key){
        Object project_detailed_message = redisTemplate
                .opsForHash()
                .entries("Project:"+project)
                .get(key);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .body(project_detailed_message);
    }

    @ApiOperation("key->search project & keys set")
    @RequestMapping(value = "/pjKeysSet/{keyword}", method = RequestMethod.GET)
    public ResponseEntity<Set<Object>> getProjectKeysSet(
            @PathVariable String keyword
    ){
        Set<Object> set = redisTemplate
                .opsForSet()
                .members("Project:Keys:"+keyword);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .body(set);
    }
}
