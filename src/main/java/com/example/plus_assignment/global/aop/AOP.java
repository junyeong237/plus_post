package com.example.plus_assignment.global.aop;

import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Slf4j(topic = "UserRoleAop")
@Aspect
@Component
public class AOP {

    //@Pointcut("execution(* com.example.dp.domain.admin.controller..*.*(..))") // 되는거
    @Pointcut("execution(* com.example.plus_assignment.domain.post.controller.*.*(..))") // 되는거
    //@Pointcut("execution(* com.example.dp.domain.admin.controller.*(..))") //랑 다르다???//안되는거
    //@Pointcut("execution(* com.example.dp.domain.admin.*.controller.*(..))") //된다
    //execution(* com.example.dp.domain.admin.*.controller.*(..))
    private void checkPost() {}//asdasd

    @Pointcut("execution(* com.example.plus_assignment.domain.user.controller.*.*(..))") // 되는거
    private void checkUser() {}
    @Before("checkPost() || checkUser()")

    public void execute(JoinPoint joinPoint) throws Throwable {

        //실행되는 함수 이름을 가져오고 출력
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        System.out.println(method.getName() + "메서드 실행");

        log.info("AOP실행확인로그");






    }
}