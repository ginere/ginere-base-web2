package spoonapps.web.servlet;

import spoonapps.util.exception.ExceptionUtils;

public class Result {

	private static final String SUCCESS = "success";

	private static final String ERROR = "error";

	public final Object data;

	// success, warning , error
	public final String result;
	public final String msg;


	Result(Object data){
		this.data=data;
		this.msg=null;
		this.result=SUCCESS;
	}

	Result(Object data,String result,String msg){
		this.data=data;
		this.msg=msg;
		this.result=result;
	}

	public static final Result success(Object data){
		return new Result(data);
	}

	public static final Result exception(Throwable exception){
//		String message=ExceptionUtils.formatException(exception);
		String message=ExceptionUtils.getMessage(exception);
		
		return new Result(null,ERROR,message);
	}


}
