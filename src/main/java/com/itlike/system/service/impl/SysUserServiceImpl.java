package com.itlike.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.itlike.system.entity.SysUser;
import com.itlike.system.entity.query.SysUserQuery;
import com.itlike.system.mapper.SysUserMapper;
import com.itlike.system.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper,SysUser> implements SysUserService {
    @Autowired
    private DefaultKaptcha defaultKaptcha;
    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";
    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    @Override
    public SysUser getUserByUserName(String username) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUser::getUsername,username);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public void imageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code = defaultKaptcha.createText();
        System.out.println("生成"+code);
        request.getSession().setAttribute(SESSION_KEY,code);
        BufferedImage image = defaultKaptcha.createImage(code);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image,"jpg",out);
//        // 设置响应的类型格式为图片格式
//        response.setContentType("image/jpeg");
//        // 禁止图像缓存。
//        response.setHeader("Pragma", "no-cache");
//        response.setHeader("Cache-Control", "no-cache");
//        response.setDateHeader("Expires", 0);
//
//        // 自定义宽、高、字数和干扰线的条数
//        IdentifyCode code = new IdentifyCode(100, 30, 4, 10);
//        System.out.println("生成"+code.getCode());
//        // 存入session
//        request.getSession().setAttribute(SESSION_KEY,code.getCode());
//        // 响应图片
//        ServletOutputStream out = response.getOutputStream();
//        code.write(out);
//        try {
//            out.flush();
//        } finally {
//            out.close();
//        }
    }

    @Override
    public IPage<SysUser> Query(SysUserQuery sysUserQuery) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(sysUserQuery.getUsername())){
            queryWrapper.lambda().like(SysUser::getUsername,sysUserQuery.getUsername());
        }
        if(StringUtils.isNotEmpty(sysUserQuery.getMobile())){
            queryWrapper.lambda().eq(SysUser::getMobile,sysUserQuery.getMobile());
        }
        if(StringUtils.isNotEmpty(sysUserQuery.getPid())){
            queryWrapper.lambda().eq(SysUser::getDeptId,sysUserQuery.getPid());
        }
        if(StringUtils.isNotEmpty(sysUserQuery.getEmail())){
            queryWrapper.lambda().eq(SysUser::getEmail,sysUserQuery.getEmail());
        }
        return baseMapper.selectPage(sysUserQuery.getPage(),queryWrapper);
    }
}
