import cn.hutool.core.thread.RejectPolicy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPOutputStream;

/**
 * 1. @ClassName Test
 * 2. @Description TODO
 * 3. @Author sy20230011
 * 4. @Date 2024/10/21 16:30
 * 5. @Version 1.0
 */
@SpringBootTest
public class Test {


    @org.junit.Test
    public void unit1() {
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        threadLocal.set("haha");
        threadLocal.set("hehe");
        System.out.println(threadLocal.get());
    }

    @org.junit.Test
    public void unit2() {
        new ThreadPoolExecutor(2, 2, 0, TimeUnit.SECONDS, new LinkedBlockingDeque<>(10), r -> new Thread(r, "gcyTest"), (r, executor) -> {
            throw new RuntimeException("ahah");
        });
    }

    // 将数据进行压缩传输
    @org.junit.Test
    public void  unit3() throws IOException, BrokenBarrierException, InterruptedException {

        String content = new ObjectMapper().writeValueAsString("你好啊");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(content.getBytes(StandardCharsets.UTF_8));
        gzip.close();

        byte[] compressedBytes = out.toByteArray();
        String str=new String(compressedBytes);
        System.out.println("压缩前字节数："+content.getBytes().length+"压缩前字符串为："+content);
        System.out.println("压缩后字节数："+compressedBytes.length+"压缩前字符串为："+str);
    }

    @org.junit.Test
    public void unit4() throws BrokenBarrierException, InterruptedException {
        CyclicBarrier cyclicBarrier=new CyclicBarrier(3);
        ThreadLocal<String> threadLocal=new ThreadLocal<>();
       new Thread(()->{
           try {
               cyclicBarrier.await();
               threadLocal.set("haha");
               Thread.sleep(4000);
               System.out.println(Thread.currentThread().getName()+"执行");
           } catch (InterruptedException e) {
               throw new RuntimeException(e);
           } catch (BrokenBarrierException e) {
               throw new RuntimeException(e);
           }
       }).start();
        new Thread(()->{
            try {
                cyclicBarrier.await();
                System.out.println(Thread.currentThread().getName()+"执行");

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName()+"执行");
        }).start();
        new Thread(()->{
            try {
                cyclicBarrier.await();
                System.out.println(Thread.currentThread().getName()+"执行");

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName()+"执行");
        }).start();
    }
}
