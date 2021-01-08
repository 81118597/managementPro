package com.itlike.system.controller;


import com.itlike.system.entity.Region;
import com.itlike.system.service.IRegionService;
import com.itlike.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author linlin
 * @since 2021-01-08
 */
@RestController
@RequestMapping("/api/region/")
public class RegionController {

	  @Autowired
	  private IRegionService regionService;

	  @RequestMapping("regionList")
	  public Result getRegionList(@RequestBody Region region){
			List<Region> regions = regionService.query(region);
			return Result.ok().data("regionList",regions);
	  }

}
