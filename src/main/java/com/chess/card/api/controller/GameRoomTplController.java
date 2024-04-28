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
import com.chess.card.api.entity.GameRoomTpl;
import com.chess.card.api.service.IGameRoomTplService;

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
 * @Description: 游戏房间模板
 * @Author: jeecg-boot
 * @Date:   2024-04-28
 * @Version: V1.0
 */
@Api(tags="游戏房间模板")
@RestController
@RequestMapping("/com.chess.card.api/gameRoomTpl")
@Slf4j
public class GameRoomTplController extends JeecgController<GameRoomTpl, IGameRoomTplService> {
	@Autowired
	private IGameRoomTplService gameRoomTplService;
	
	/**
	 * 分页列表查询
	 *
	 * @param gameRoomTpl
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "游戏房间模板-分页列表查询")
	@ApiOperation(value="游戏房间模板-分页列表查询", notes="游戏房间模板-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<GameRoomTpl>> queryPageList(GameRoomTpl gameRoomTpl,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<GameRoomTpl> queryWrapper = QueryGenerator.initQueryWrapper(gameRoomTpl, req.getParameterMap());
		Page<GameRoomTpl> page = new Page<GameRoomTpl>(pageNo, pageSize);
		IPage<GameRoomTpl> pageList = gameRoomTplService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param gameRoomTpl
	 * @return
	 */
	@AutoLog(value = "游戏房间模板-添加")
	@ApiOperation(value="游戏房间模板-添加", notes="游戏房间模板-添加")
	@RequiresPermissions("com.chess.card.api:game_room_tpl:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody GameRoomTpl gameRoomTpl) {
		gameRoomTplService.save(gameRoomTpl);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param gameRoomTpl
	 * @return
	 */
	@AutoLog(value = "游戏房间模板-编辑")
	@ApiOperation(value="游戏房间模板-编辑", notes="游戏房间模板-编辑")
	@RequiresPermissions("com.chess.card.api:game_room_tpl:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody GameRoomTpl gameRoomTpl) {
		gameRoomTplService.updateById(gameRoomTpl);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "游戏房间模板-通过id删除")
	@ApiOperation(value="游戏房间模板-通过id删除", notes="游戏房间模板-通过id删除")
	@RequiresPermissions("com.chess.card.api:game_room_tpl:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		gameRoomTplService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "游戏房间模板-批量删除")
	@ApiOperation(value="游戏房间模板-批量删除", notes="游戏房间模板-批量删除")
	@RequiresPermissions("com.chess.card.api:game_room_tpl:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.gameRoomTplService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "游戏房间模板-通过id查询")
	@ApiOperation(value="游戏房间模板-通过id查询", notes="游戏房间模板-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<GameRoomTpl> queryById(@RequestParam(name="id",required=true) String id) {
		GameRoomTpl gameRoomTpl = gameRoomTplService.getById(id);
		if(gameRoomTpl==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(gameRoomTpl);
	}

}