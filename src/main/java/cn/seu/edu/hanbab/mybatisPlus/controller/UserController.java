package cn.seu.edu.hanbab.mybatisPlus.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Hanbab
 * @since 2021-06-07
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @ApiOperation("mybatis")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        return "hanbab-mybatis！";
    }

}

