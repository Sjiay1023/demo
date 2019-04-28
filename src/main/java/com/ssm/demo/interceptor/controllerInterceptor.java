package com.ssm.demo.interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * 拦截器
 */
@Aspect
@Component
public class controllerInterceptor {
    static Logger logger = LoggerFactory.getLogger(controllerInterceptor.class);
    //ThreadLocal 维护变量避免同步
    //ThreadLocal 为每个使用该变量的线程提供独立的变量副本，
    //所以每个变量可以独立的改变自己的副本，而不会影响其他线程所有的副本
    ThreadLocal<Long> startTime = new ThreadLocal<>();//开始时间

    /**
     * map1存放方法被调用的次数
     */
    ThreadLocal<Map<String,Long>> map1 = new ThreadLocal<>();

    /**
     * map2存放方法被调用的时间
     */
    ThreadLocal<Map<String,Long>> map2 = new ThreadLocal<>();


    /**
     * 定义一个切入点
     * ~第一个 * 代表任意修饰符以及任意返回值。
     * ~第二个 * 定义在web包或者子包
     * ~第三个 * 任意方法 ~ .. 匹配任意数量的参数
     */
    static final String pCutStr = "execution(* com.ssm.demo.*..*(..))";

    @Pointcut(value = pCutStr)
    public void logPointCut(){

    }

    @Around("logPointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable{

        //初始化一次
        if(map1.get()==null){
            map1.set(new HashMap<>());
        }

        if(map2.get()==null){
            map2.set(new HashMap<>());
        }

        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            if(result == null){
                //如果切到了没有返回类型的void方法，直接返回
                return null;
            }
            long end = System.currentTimeMillis();

            logger.info("==================");
            String targetClassName = joinPoint.getSignature().getDeclaringTypeName();
            String methodName = joinPoint.getSignature().getName();

            Object[] args = joinPoint.getArgs();//参数
            int argsSize = args.length;
            String argsTypes = "";
            String typeStr = joinPoint.getSignature().getDeclaringType().toString().split(" ")[0];
            String returnType = joinPoint.getSignature().toString().split(" ")[0];
            logger.info("类/接口:" + targetClassName + "(" + typeStr + ")");
            logger.info("方法:" + methodName);
            logger.info("参数个数:" + argsSize);
            logger.info("返回类型:" + returnType);

            if(argsSize>0){
                //拿到参数的类型
                for(Object object : args){
                    argsTypes += object.getClass().getTypeName().toString() + " ";
                }
                logger.info("参数类型:" + argsTypes);
            }

            Long totle = end-start;
            logger.info("耗时:" + totle + "ms!");

            if(map1.get().containsKey(methodName)){
                Long count = map1.get().get(methodName);
                map1.get().remove(methodName);//先移除再添加
                map1.get().put(methodName,count+1);

                count = map2.get().get(methodName);
                map2.get().remove(methodName);
                map2.get().put(methodName,count+totle);
            }else{
                map1.get().put(methodName,1L);
                map2.get().put(methodName,totle);
            }
            return result;
        }catch (Throwable e ){
            long end = System.currentTimeMillis();
            logger.info("=====around" + joinPoint + "\tUser time : " + (end-start) +"ms with exception:"
                            + e.getMessage());
            throw e;
        }
    }
    /**
     * 对controller下面的方法执行前进行切入，初始化开始时间
     */
    @Before(value = "execution(* com.ssm.demo.controller.*.*(..))")
    public void  beforeMethod(JoinPoint jp){
        startTime.set(System.currentTimeMillis());
    }

    /**
     * 对controller 下面的方法执行后执行后进行切入，统计方法执行的次数和耗时情况
     * 注意：这里的执行方法统计的数据不止包括Controller下面的方法，也包括环绕切入的所有方法的统计信息
     */
    @AfterReturning(value = "execution(* com.ssm.demo.controller.*.*(..))")
    public void afterMethod(JoinPoint jp){
        long end = System.currentTimeMillis();
        long total = end - startTime.get();
        String methodName = jp.getSignature().getName();
        logger.info("连接点方法为：" + methodName + ",执行总耗时为：" + total + "ms");

        //重新new一个map
        Map<String,Long> map = new HashMap<>();

        //从map2中将最后的 连接点方法给移除了，替换成最终的，避免连接点方法多次进行叠加计算
        //由于map2受ThreadLocal的保护，这里不支持remove，因此，需要单开一个map进行数据交接
        for(Map.Entry<String,Long> entry : map2.get().entrySet()){
            if(entry.getKey().equals(methodName)){
                map.put(methodName,total);
            }else{
                map.put(entry.getKey(),entry.getValue());
            }
        }
         for(Map.Entry<String,Long> entry : map1.get().entrySet()){
            for(Map.Entry<String,Long> entry2 : map.entrySet()){
                if(entry.getKey().equals(entry2.getKey())){
                    System.err.println(entry.getKey()+ ",被调用次数：" +entry.getValue()+ ",综合耗时："+entry2.getValue()+"ms");

                }
            }
         }
    }


}
