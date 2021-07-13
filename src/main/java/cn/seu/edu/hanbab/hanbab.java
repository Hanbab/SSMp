package cn.seu.edu.hanbab;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Hanbab
 * @Date: 2021/05/14/9:53
 */

@SpringBootApplication
@MapperScan("cn/seu/edu/hanbab/mybatisPlus/mapper")
public class hanbab {
    public static void main(String[] args){
        SpringApplication.run(hanbab.class, args);
    }
}
