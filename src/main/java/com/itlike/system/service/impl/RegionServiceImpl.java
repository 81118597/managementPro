package com.itlike.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itlike.system.entity.Region;
import com.itlike.system.entity.SysMenu;
import com.itlike.system.mapper.RegionMapper;
import com.itlike.system.service.IRegionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author linlin
 * @since 2021-01-08
 */
@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements IRegionService {

	  /**
	   * 查询
	   * @param region
	   * @return
	   */
	  @Override
	  public List<Region> query(Region region) {
			QueryWrapper<Region> wrapper = new QueryWrapper<>();
			if(region.getId()!=null){
				  wrapper.lambda().eq(Region::getId,region.getId());
			}
			if(StringUtils.isNotEmpty(region.getName())){
				  wrapper.lambda().eq(Region::getName,region.getName());
			}
			return baseMapper.selectList(wrapper);
	  }
}
