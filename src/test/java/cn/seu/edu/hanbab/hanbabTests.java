package cn.seu.edu.hanbab;


import cn.seu.edu.hanbab.mybatisPlus.entity.User;
import cn.seu.edu.hanbab.mybatisPlus.mapper.UserMapper;
import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.support.collections.RedisZSet;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class hanbabTests {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Test
    public void test(){
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(2,userList.size());
        userList.forEach(System.out::println);
    }
    @Test
    public void redis() {
    }
}
