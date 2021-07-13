package cn.seu.edu.hanbab.hanbabRedis.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Hanbab
 * @Date: 2021/6/23
 */

@Data
public class basic {

    private String hash = "Hash";

    private String zset = "Zset";

    private String set = "Set";

    private String string = "String";

    private String list = "List";

    private String stream = "Stream";


}
