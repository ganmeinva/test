package com.zhengxin.one.controller;

/**
 * 1. @ClassName SnowflakeIdGenerator
 * 2. @Description TODO
 * 3. @Author sy20230011
 * 4. @Date 2024/11/1 10:50
 * 5. @Version 1.0
 */
public class SnowflakeIdGenerator {
    // 基础时间戳（可以设为一个固定时间）
    private final static long START_TIMESTAMP = 1609459200000L; // 2021-01-01 00:00:00

    // 各部分占用的位数
    private final static long SEQUENCE_BITS = 12;   // 序列号部分占用的位数
    private final static long MACHINE_BITS = 5;     // 机器ID部分占用的位数
    private final static long DATACENTER_BITS = 5;  // 数据中心ID部分占用的位数

    // 各部分的最大值
    private final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BITS);
    private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BITS);
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BITS);

    // 各部分向左的位移
    private final static long MACHINE_SHIFT = SEQUENCE_BITS;
    private final static long DATACENTER_SHIFT = SEQUENCE_BITS + MACHINE_BITS;
    private final static long TIMESTAMP_SHIFT = DATACENTER_SHIFT + DATACENTER_BITS;

    private long datacenterId;  // 数据中心ID
    private long machineId;     // 机器ID
    private long sequence = 0L; // 序列号
    private long lastTimestamp = -1L; // 上一次生成ID的时间戳

    public SnowflakeIdGenerator(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("DatacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("MachineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    // 生成唯一ID
    public synchronized long nextId() {
        long currentTimestamp = getCurrentTimestamp();

        if (currentTimestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id");
        }

        if (currentTimestamp == lastTimestamp) {
            // 相同毫秒内，序列号递增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            // 序列号已用尽
            if (sequence == 0L) {
                // 等待下一毫秒
                currentTimestamp = getNextMillisecond();
            }
        } else {
            // 不同毫秒内，序列号归0
            sequence = 0L;
        }

        lastTimestamp = currentTimestamp;

        // 生成唯一ID
        return (currentTimestamp - START_TIMESTAMP) << TIMESTAMP_SHIFT // 时间戳部分
                | (datacenterId << DATACENTER_SHIFT)                    // 数据中心部分
                | (machineId << MACHINE_SHIFT)                          // 机器ID部分
                | sequence;                                             // 序列号部分
    }

    // 获取当前时间戳（毫秒）
    private long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    // 获取下一毫秒的时间戳
    private long getNextMillisecond() {
        long mill = getCurrentTimestamp();
        while (mill <= lastTimestamp) {
            mill = getCurrentTimestamp();
        }
        return mill;
    }

    public static void main(String[] args) {
        SnowflakeIdGenerator generator = new SnowflakeIdGenerator(1, 1);
        for (int i = 0; i < 10; i++) {
            System.out.println(generator.nextId());
        }
    }
}
