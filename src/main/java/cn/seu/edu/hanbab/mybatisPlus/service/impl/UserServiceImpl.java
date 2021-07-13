package cn.seu.edu.hanbab.mybatisPlus.service.impl;

import cn.seu.edu.hanbab.mybatisPlus.entity.User;
import cn.seu.edu.hanbab.mybatisPlus.mapper.UserMapper;
import cn.seu.edu.hanbab.mybatisPlus.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Hanbab
 * @since 2021-06-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
