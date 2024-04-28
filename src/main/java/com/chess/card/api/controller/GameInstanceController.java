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
import com.chess.card.api.entity.GameInstance;
import com.chess.card.api.service.IGameInstanceService;

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
 * @Description: 游戏实例
 * @Author: jeecg-boot
 * @Date:   2024-04-28
 * @Version: V1.0
 */
@Api(tags="游戏实例")
@RestController
@RequestMapping("/com.chess.card.api/gameInstance")
@Slf4j
public class GameInstanceController extends JeecgController<GameInstance, IGameInstanceService> {
	@Autowired
	private IGameInstanceService gameInstanceService;
	
	/**
	 * 分页列表查询
	 *
	 * @param gameInstance
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "游戏实例-分页列表查询")
	@ApiOperation(value="游戏实例-分页列表查询", notes="游戏实例-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<GameInstance>> queryPageList(GameInstance gameInstance,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<GameInstance> queryWrapper = QueryGenerator.initQueryWrapper(gameInstance, req.getParameterMap());
		Page<GameInstance> page = new Page<GameInstance>(pageNo, pageSize);
		IPage<GameInstance> pageList = gameInstanceService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param gameInstance
	 * @return
	 */
	@AutoLog(value = "游戏实例-添加")
	@ApiOperation(value="游戏实例-添加", notes="游戏实例-添加")
	@RequiresPermissions("com.chess.card.api:game_instance:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody GameInstance gameInstance) {
		gameInstanceService.save(gameInstance);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param gameInstance
	 * @return
	 */
	@AutoLog(value = "游戏实例-编辑")
	@ApiOperation(value="游戏实例-编辑", notes="游戏实例-编辑")
	@RequiresPermissions("com.chess.card.api:game_instance:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody GameInstance gameInstance) {
		gameInstanceService.updateById(gameInstance);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "游戏实例-通过id删除")
	@ApiOperation(value="游戏实例-通过id删除", notes="游戏实例-通过id删除")
	@RequiresPermissions("com.chess.card.api:game_instance:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		gameInstanceService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "游戏实例-批量删除")
	@ApiOperation(value="游戏实例-批量删除", notes="游戏实例-批量删除")
	@RequiresPermissions("com.chess.card.api:game_instance:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.gameInstanceService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "游戏实例-通过id查询")
	@ApiOperation(value="游戏实例-通过id查询", notes="游戏实例-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<GameInstance> queryById(@RequestParam(name="id",required=true) String id) {
		GameInstance gameInstance = gameInstanceService.getById(id);
		if(gameInstance==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(gameInstance);
	}


}