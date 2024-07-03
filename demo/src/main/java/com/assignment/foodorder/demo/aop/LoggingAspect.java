package com.assignment.foodorder.demo.aop;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@EnableAspectJAutoProxy
@Order(1)
@Aspect
@Component
public class LoggingAspect {

	private static Logger logger = (Logger) LoggerFactory.getLogger(LoggingAspect.class);

	@Pointcut("execution(* com.assignment.foodorder.demo.controller.*.*(..))")
	private void controllerMethods() {
	}

	@Around("controllerMethods()")
	public Object logRequestAndResponse(ProceedingJoinPoint joinPoint) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();

		// Log request details
		logger.info("Incoming request: {} {} from {}", request.getMethod(), request.getRequestURI(),
				request.getRemoteAddr());

		Object response;
		try {
			response = joinPoint.proceed();
		} catch (Throwable throwable) {
			logger.error("Exception in {}.{}: {}", joinPoint.getSignature().getDeclaringTypeName(),
					joinPoint.getSignature().getName(), throwable.getMessage());
			throw throwable;
		}

		logger.info("Response: {}", response);

		return response;
	}

	@Before("controllerMethods()")
	public void logBefore(JoinPoint joinPoint) {
		logger.info("Entering in Method :  " + joinPoint.getSignature().getName());
		logger.info("Class Name :  " + joinPoint.getSignature().getDeclaringTypeName());
		logger.info("Arguments :  " + Arrays.toString(joinPoint.getArgs()));
		logger.info("Target class : " + joinPoint.getTarget().getClass().getName());
	}

	@AfterReturning(pointcut = "controllerMethods()", returning = "result")
	public void logAfterReturning(JoinPoint joinPoint, Object result) {
		logger.info("Method Return value : " + result);
	}

	@AfterThrowing(pointcut = "controllerMethods()", throwing = "error")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
		logger.error("Method Exception : " + error);
	}
}