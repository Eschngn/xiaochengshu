package com.swc.orangeBook.note;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author Wilson
 * @Description: TODO
 * @date 2024/9/18 16:13
 */
public class CompletableFutureExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            // 模拟一个耗时的任务
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return 42; // 返回任务的结果
        });

        // 等待任务完成
        Integer result = future.get();
        System.out.println("Result: " + result);
    }
}
