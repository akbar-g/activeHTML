package com.pidan.job.once;

import cn.hutool.core.date.StopWatch;
import com.pidan.mapper.UserMapper;
import com.pidan.model.entity.User;
import com.pidan.service.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author 黄大头
 * @date 2023年04月19日 16:42\
 * 模拟用户数据
 */
@Component
public class InsertUser {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserService userService;

    /**             10w 条数据数据测试
     * 循环插入用户   单条插入 timeout 81548 ms
     *              批量插入 timeout 11495 ms
     *              并行线程   timeout 5583 ms
     */
//    @Scheduled(initialDelay = 5000,fixedRate = Long.MAX_VALUE )
    public void doInsertUser() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int INSERT_NUM = 100000;
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < INSERT_NUM; i++) {
            User user = new User();
            user.setUsername("臭皮蛋");
            user.setUserAccount("臭皮蛋");
            user.setAvatarUrl("臭皮蛋.臭皮蛋.com/臭皮蛋/臭皮蛋.png");
            user.setGender(0);
            user.setUserPassword("12345678");
            user.setPhone("123456789108");
            user.setEmail("臭皮蛋-臭皮蛋@qq.com");
            user.setUserStatus(0);
            user.setUserRole(0);
            user.setTags("[]");
            //userMapper.insert(user);
            userList.add(user);
        }
        userService.saveBatch(userList,100);
        stopWatch.stop();
        System.out.println( stopWatch.getLastTaskTimeMillis());

    }

    //线程设置
    private ExecutorService executorService = new ThreadPoolExecutor(16, 1000, 10000, TimeUnit.MINUTES, new ArrayBlockingQueue<>(10000));
    /**
     * 并发批量插入用户   100000  耗时：
     */
    public void doConcurrencyInsertUser() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int INSERT_NUM = 100000;
        // 分十组
        int j = 0;
        //批量插入数据的大小
        int batchSize = 5000;
        List<CompletableFuture<Void>> futureList = new ArrayList<>();
        // i 要根据数据量和插入批量来计算需要循环的次数。（鱼皮这里直接取了个值，会有问题,我这里随便写的）
        for (int i = 0; i < INSERT_NUM/batchSize; i++) {
            List<User> userList = new ArrayList<>();
            while (true){
                j++;
                User user = new User();
                user.setUsername("臭皮蛋");
                user.setUserAccount("臭皮蛋");
                user.setAvatarUrl("臭皮蛋.臭皮蛋.com/臭皮蛋/臭皮蛋.png");
                user.setGender(0);
                user.setUserPassword("12345678");
                user.setPhone("123456789108");
                user.setEmail("臭皮蛋-臭皮蛋@qq.com");
                user.setUserStatus(0);
                user.setUserRole(0);
                user.setTags("[]");
                userList.add(user);
                if (j % batchSize == 0 ){
                    break;
                }
            }
            //异步执行
            CompletableFuture<Void> future = CompletableFuture.runAsync(() ->{
                System.out.println("ThreadName：" + Thread.currentThread().getName());
                userService.saveBatch(userList,batchSize);
            },executorService);
            futureList.add(future);
        }
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[]{})).join();

        stopWatch.stop();
        System.out.println( stopWatch.getLastTaskTimeMillis());

    }
}
