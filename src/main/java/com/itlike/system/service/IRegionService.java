package com.itlike.system.service;

import com.itlike.system.entity.Region;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author linlin
 * @since 2021-01-08
 */
public interface IRegionService extends IService<Region> {

	  List<Region> query(Region region);
}
