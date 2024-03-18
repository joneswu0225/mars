package com.jones.mars.util;

import java.io.Serializable;
import java.util.Date;

/**
 * Twitter的Snowflake 算法<br>
 * 分布式系统中，有一些需要使用全局唯一ID的场景，有些时候我们希望能使用一种简单一些的ID，并且希望ID能够按照时间有序生成。
 *
 * <p>
 * snowflake的结构如下(每部分用-分开):<br>
 *
 * <pre>
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 0000000000 000 - 000000 - 000
 * </pre>
 * <p>
 * 分布式ID采用雪花算法（64位），各位含义：
 * 预留：1
 * 毫秒相对时间戳：41，69年
 * 部署ID：13，最大支持8191个落地部署（数据库id从1开始，8192-1=8191）
 * 表ID：6，最大支持64张表
 * 毫秒内自增序列：3，1毫秒内最多产生8条记录
 * <p>
 * 并且可以通过生成的id反推出生成时间,deployId和tableId
 * <p>
 * 参考：http://www.cnblogs.com/relucent/p/4955340.html
 *
 * @author Looly
 * @since 3.0.1
 */
public class Snowflake implements Serializable {
    private static final long serialVersionUID = 1L;

    private final long twepoch = 1398038779076L;
    private final long tableIdBits = 6L;
    private final long deployIdBits = 13L;
    private final long maxTableId = -1L ^ (-1L << tableIdBits);
    @SuppressWarnings({"PointlessBitwiseExpression", "FieldCanBeLocal"})
    private final long maxDeployId = -1L ^ (-1L << deployIdBits);
    private final long sequenceBits = 3L;
    private final long tableIdShift = sequenceBits;
    private final long deployIdShift = sequenceBits + tableIdBits;
    private final long timestampLeftShift = sequenceBits + tableIdBits + deployIdBits;
    @SuppressWarnings({"PointlessBitwiseExpression", "FieldCanBeLocal"})
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);// 4095

    private volatile long sequence = 0L;
    private volatile long lastTimestamp = -1L;


    /**
     * 构造
     */
    public Snowflake(){

    }

    /**
     * 根据Snowflake的ID，获取机器id
     *
     * @param id snowflake算法生成的id
     * @return 所属机器的id
     */
    public long getTableId(long id) {
        return id >> tableIdShift & ~(-1L << tableIdBits);
    }

    /**
     * 根据Snowflake的ID，获取部署id
     *
     * @param id snowflake算法生成的id
     * @return 所属部署
     */
    public long getDeployId(long id) {
        return id >> deployIdShift & ~(-1L << deployIdBits);
    }

    /**
     * 根据Snowflake的ID，获取生成时间
     *
     * @param id snowflake算法生成的id
     * @return 生成的时间
     */
    public long getGenerateDateTime(long id) {
        return (id >> timestampLeftShift & ~(-1L << 41L)) + twepoch;
    }

    /**
     * 下一个ID
     *
     * @return ID
     */
    public synchronized long nextId(long deployId, long tableId) {
        long timestamp = genTime();
        if (timestamp < lastTimestamp) {
            if(lastTimestamp - timestamp < 2000){
                // 容忍2秒内的回拨，避免NTP校时造成的异常
                timestamp = lastTimestamp;
            } else{
                // 如果服务器时间有问题(时钟后退) 报错。
                throw new IllegalStateException(String.format("Clock moved backwards. Refusing to generate id for {}ms", lastTimestamp - timestamp));
            }
        }
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                System.out.println(String.format("timestamp crash wait for 1 ms, sequence:{}", sequence));
                timestamp = tilNextMillis(lastTimestamp);
            }


        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        long id = ((timestamp - twepoch) << timestampLeftShift) | (deployId << deployIdShift) | (tableId << tableIdShift) | sequence;
        return id;
    }

    /**
     * 下一个ID（字符串形式）
     *
     * @return ID 字符串形式
     */
    public String nextIdStr(long deployId, long tableId) {
        return Long.toString(nextId(deployId, tableId));
    }

    // ------------------------------------------------------------------------------------------------------------------------------------ Private method start

    /**
     * 循环等待下一个时间
     *
     * @param lastTimestamp 上次记录的时间
     * @return 下一个时间
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = genTime();
        // 循环直到操作系统时间戳变化
        while (timestamp == lastTimestamp) {
            timestamp = genTime();
        }
        if (timestamp < lastTimestamp) {
            // 如果发现新的时间戳比上次记录的时间戳数值小，说明操作系统时间发生了倒退，报错
            throw new IllegalStateException(
                    String.format("Clock moved backwards. Refusing to generate id for {}ms", lastTimestamp - timestamp));
        }
        return timestamp;
    }

    /**
     * 生成时间戳
     *
     * @return 时间戳
     */
    private long genTime() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        long deployId = 111l;
        long tableId = 63l;
        Snowflake snowflake = new Snowflake();
        for(int i=0;i<=10;i++){
            long id = snowflake.nextId(deployId, tableId);
            System.out.println(id );
        }

    }
}



