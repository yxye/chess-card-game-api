package com.chess.ws.api.exception;


import com.chess.ws.api.bean.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 异常处理器
 * 
 * @author yxye
 * @Date 2019
 */
@Slf4j
@RestControllerAdvice(basePackages = {"com.chess.ws.api.controller"})
@ResponseBody
public class ApiExceptionHandler {
	/**
	 * @param e
	 * @return
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Result<?> HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
		StringBuffer sb = new StringBuffer();
		sb.append("不支持");
		sb.append(e.getMethod());
		sb.append("请求方法，");
		sb.append("支持以下");
		String [] methods = e.getSupportedMethods();
		if(methods!=null){
			for(String str:methods){
				sb.append(str);
				sb.append("、");
			}
		}
		log.error(sb.toString(), e);
		//return Result.error("没有权限，请联系管理员授权");
		return Result.error(405,sb.toString());
	}
	
	 /** 
	  * spring默认上传大小100MB 超出大小捕获异常MaxUploadSizeExceededException 
	  */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Result<?> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
    	log.error(e.getMessage(), e);
        return Result.error("文件大小超出10MB限制, 请压缩或降低文件质量! ");
    }

	//内部服务异常处理
	@ResponseBody
	@ExceptionHandler(BuziException.class)
	public Result<?> handleGlobalException(HttpServletResponse response, BuziException tgyBuziException) {
		return Result.error(tgyBuziException.getMessage());
	}




	//内部服务异常处理
	@ResponseBody
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Result<?> handleGlobalException(HttpServletResponse response, MethodArgumentNotValidException e) {
		List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
		if(CollectionUtils.isEmpty(allErrors)){
			return Result.error("缺少必要参数！");
		}
		ObjectError objectError = allErrors.get(0);
		return Result.error(objectError.getDefaultMessage());
	}

	//内部服务异常处理
	@ResponseBody
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public Result<?> missingServletRequestParameterException(HttpServletRequest request,HttpServletResponse response, Exception e) {
		log.error("系统异常，请稍后再试 requestURL:{},userId:{},error:{}",request.getRequestURL(),this.getUserId(request),e);
		return Result.error(e.getMessage());
	}


	//内部服务异常处理
	@ResponseBody
	@ExceptionHandler(Exception.class)
	public Result<?> handleGlobalException(HttpServletRequest request,HttpServletResponse response, Exception e) {
    	log.error("操作失败 requestURL:{},userId:{},error:{}",request.getRequestURL(),this.getUserId(request),e);
		return Result.error("操作失败！");
	}

	private String getUserId(HttpServletRequest request){
		try {

		}catch (Exception e){
			return "";
		}
		return "";
	}
}
