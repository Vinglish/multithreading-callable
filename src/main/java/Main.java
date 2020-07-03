import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Main {
    public static final int ELEMENTS_NUM = 1_000_000;

    public static void main(String[] args) throws InterruptedException {
        List<Long> nums = LongStream.range(0, ELEMENTS_NUM)
                .boxed()
                .collect(Collectors.toList());

        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        SumRecursive recursiveSum = new SumRecursive(nums);
        long forkStart = System.currentTimeMillis();
        Long forkJoinSum = forkJoinPool.invoke(recursiveSum);
        long forkEnd = System.currentTimeMillis();
        System.out.println("ForkJoin: " + forkJoinSum + ", time: " + (forkEnd - forkStart));

        SumExecutorService sumExecutorService = new SumExecutorService(nums, 4);
        long executorStart = System.currentTimeMillis();
        long sum = sumExecutorService.calculateListSum();
        long executorEnd = System.currentTimeMillis();
        sumExecutorService.shutdown();
        System.out.println("Executor: " + sum + ", time: " + (executorEnd - executorStart));
    }
}
