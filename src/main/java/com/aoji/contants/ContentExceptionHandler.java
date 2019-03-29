package com.aoji.contants;
import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author yangsaixing
 * @description  ServiceException处理控制器
 * @date Created in 下午4:15 2017/10/10
 */
@ControllerAdvice
public class ContentExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ContentExceptionHandler.class);

    @Value("${exception.marked.words}")
    private String markedWords;

    /**
     * 处理Exception
     * @param e 异常
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseMessage> processException(Exception e) {
        ResponseMessage body = new ResponseMessage();
        //如果是ServiceException则将异常解析为可读的错误代码，否则转换为服务器异常，并打印日志
        if (e instanceof ContentException) {
            ContentException serviceException = (ContentException) e;
            body.getHead().setCode(serviceException.getCode());
            body.getHead().setMessage(serviceException.getMessage());
            logger.info("ContentException {}", e);
        }else if(e instanceof AuthenticationException){
            AuthenticationException serviceException = (AuthenticationException) e;
            body.getHead().setMessage(markedWords);
            logger.info("ContentException {}", e);
        } else {
            body.getHead().setCode(99999);
            body.getHead().setMessage("服务异常");
            logger.error("服务异常", e);
        }
        return new ResponseEntity<ResponseMessage>(body, HttpStatus.OK);
    }
}
