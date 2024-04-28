package com.chess.card.api.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import com.chess.card.api.entity.InstanceItemRecord;
import com.chess.card.api.service.IInstanceItemRecordService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 游戏记录详情
 * @Author: jeecg-boot
 * @Date:   2024-04-28
 * @Version: V1.0
 */
@Api(tags="游戏记录详情")
@RestController
@RequestMapping("/com.chess.card.api/instanceItemRecord")
@Slf4j
public class InstanceItemRecordController extends JeecgController<InstanceItemRecord, IInstanceItemRecordService> {
	@Autowired
	private IInstanceItemRecordService instanceItemRecordService;
	
	/**
	 * 分页列表查询
	 *
	 * @param instanceItemRecord
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "游戏记录详情-分页列表查询")
	@ApiOperation(value="游戏记录详情-分页列表查询", notes="游戏记录详情-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<InstanceItemRecord>> queryPageList(InstanceItemRecord instanceItemRecord,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<InstanceItemRecord> queryWrapper = QueryGenerator.initQueryWrapper(instanceItemRecord, req.getParameterMap());
		Page<InstanceItemRecord> page = new Page<InstanceItemRecord>(pageNo, pageSize);
		IPage<InstanceItemRecord> pageList = instanceItemRecordService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param instanceItemRecord
	 * @return
	 */
	@AutoLog(value = "游戏记录详情-添加")
	@ApiOperation(value="游戏记录详情-添加", notes="游戏记录详情-添加")
	@RequiresPermissions("com.chess.card.api:instance_item_record:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody InstanceItemRecord instanceItemRecord) {
		instanceItemRecordService.save(instanceItemRecord);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param instanceItemRecord
	 * @return
	 */
	@AutoLog(value = "游戏记录详情-编辑")
	@ApiOperation(value="游戏记录详情-编辑", notes="游戏记录详情-编辑")
	@RequiresPermissions("com.chess.card.api:instance_item_record:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody InstanceItemRecord instanceItemRecord) {
		instanceItemRecordService.updateById(instanceItemRecord);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "游戏记录详情-通过id删除")
	@ApiOperation(value="游戏记录详情-通过id删除", notes="游戏记录详情-通过id删除")
	@RequiresPermissions("com.chess.card.api:instance_item_record:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		instanceItemRecordService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "游戏记录详情-批量删除")
	@ApiOperation(value="游戏记录详情-批量删除", notes="游戏记录详情-批量删除")
	@RequiresPermissions("com.chess.card.api:instance_item_record:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.instanceItemRecordService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "游戏记录详情-通过id查询")
	@ApiOperation(value="游戏记录详情-通过id查询", notes="游戏记录详情-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<InstanceItemRecord> queryById(@RequestParam(name="id",required=true) String id) {
		InstanceItemRecord instanceItemRecord = instanceItemRecordService.getById(id);
		if(instanceItemRecord==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(instanceItemRecord);
	}

}
