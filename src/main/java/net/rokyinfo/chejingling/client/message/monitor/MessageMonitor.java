package net.rokyinfo.chejingling.client.message.monitor;

import net.rokyinfo.chejingling.client.message.exception.RkMessageException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yuanzhijian on 2017/2/21.
 */
public class MessageMonitor {

    public static class PerformanceStatistics {

        /**
         * 调用时间相关统计
         */
        private volatile long maxTime;
        private volatile long minTime;
        private volatile long avgTime;
        private volatile long totalTime;

        private volatile long startTimeMs;

        private volatile long currentTimeMs;

        private volatile long totalCount;

        private volatile long successCount;
        private volatile long errorCount;

        /**
         * 包大小统计
         */
        private volatile long maxMsgBodySize;
        private volatile long minMsgBodySize;
        private volatile long avgMsgBodySize;
        private volatile long currentMsgBodySize;

        private volatile Exception exception;
        private volatile Date exceptionTime;

        public boolean isAvailable() {
            if (System.currentTimeMillis() - startTimeMs > 2 * 60 * 1000) {
                return false;
            } else {
                return true;
            }
        }

        public void start() {
            startTimeMs = System.currentTimeMillis();
        }

        public void bodySize(long bodyLength) {

            if (bodyLength > 0) {
                if (bodyLength > maxMsgBodySize) {
                    maxMsgBodySize = bodyLength;
                }
                if (minMsgBodySize == 0) {
                    minMsgBodySize = bodyLength;
                }
                if (bodyLength < minMsgBodySize) {
                    minMsgBodySize = bodyLength;
                }
                currentMsgBodySize = bodyLength;
                if (avgMsgBodySize == 0) {
                    avgMsgBodySize = currentMsgBodySize;
                }
                avgMsgBodySize = (avgMsgBodySize + currentMsgBodySize) / 2;
            }

        }


        public void success() {
            if (successCount == Long.MAX_VALUE) {
                successCount = 0;
            }
            successCount++;
            complete();
        }

        public void error(RkMessageException e) {
            if (errorCount == Long.MAX_VALUE) {
                errorCount = 0;
            }
            errorCount++;
            complete();
            exception = e;
            exceptionTime = new Date();
        }

        private void complete() {

            if (totalCount >= Long.MAX_VALUE) {
                totalCount = 0;
                totalTime = 0;
            }
            totalCount++;
            currentTimeMs = System.currentTimeMillis() - startTimeMs;
            if (currentTimeMs > maxTime) {
                maxTime = currentTimeMs;
            }
            if (minTime == 0 || currentTimeMs < minTime) {
                minTime = currentTimeMs;
            }
            totalTime += currentTimeMs;
            avgTime = totalTime / totalCount;

        }

        public long getMaxTime() {
            return maxTime;
        }

        public long getMinTime() {
            return minTime;
        }

        public long getAvgTime() {
            return avgTime;
        }

        public long getTotalTime() {
            return totalTime;
        }

        public long getCurrentTimeMs() {
            return currentTimeMs;
        }

        public long getTotalCount() {
            return totalCount;
        }

        public long getSuccessCount() {
            return successCount;
        }

        public long getErrorCount() {
            return errorCount;
        }

        public long getStartTimeMs() {
            return startTimeMs;
        }

        public long getMaxMsgBodySize() {
            return maxMsgBodySize;
        }

        public long getMinMsgBodySize() {
            return minMsgBodySize;
        }

        public long getCurrentMsgBodySize() {
            return currentMsgBodySize;
        }

        public Exception getException() {
            return exception;
        }

        public Date getExceptionTime() {
            return exceptionTime;
        }

        public void setExceptionTime(Date exceptionTime) {
            this.exceptionTime = exceptionTime;
        }

        public long getAvgMsgBodySize() {
            return avgMsgBodySize;
        }
    }

    private static MessageMonitor instance;

    private PerformanceStatistics requestStatistics;

    private PerformanceStatistics postMsgStatistics;

    public MessageMonitor() {
        requestStatistics = new PerformanceStatistics();
        postMsgStatistics = new PerformanceStatistics();
    }

    public static MessageMonitor getInstance() {
        if (instance == null) {
            instance = new MessageMonitor();
        }
        return instance;
    }

    public void startRequest() {
        requestStatistics.start();
    }

    public void requestSuccess() {
        requestStatistics.success();
    }

    public void requestError(RkMessageException e) {
        requestStatistics.error(e);
    }

    public void startPostMessage() {
        postMsgStatistics.start();
    }

    public void postMessageComplete() {
        postMsgStatistics.success();
    }

    public void bodySize(long size) {
        postMsgStatistics.bodySize(size);
    }

    public void postMessageError(RkMessageException e) {
        postMsgStatistics.error(e);
    }

    public PerformanceStatistics getRequestStatistics() {
        return requestStatistics;
    }

    public PerformanceStatistics getPostMsgStatistics() {
        return postMsgStatistics;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("服务运行状态：");
        stringBuilder.append(requestStatistics.isAvailable() ? "正常" : "异常");
        stringBuilder.append("\n");
        stringBuilder.append("\n");

        stringBuilder.append("当前请求启动时间：");
        stringBuilder.append(dateFm.format(new Date(requestStatistics.getStartTimeMs())));
        stringBuilder.append("\n");

        stringBuilder.append("当前请求耗时：");
        stringBuilder.append(requestStatistics.getCurrentTimeMs());
        stringBuilder.append("毫秒");
        stringBuilder.append("\n");

        stringBuilder.append("请求最大耗时：");
        stringBuilder.append(requestStatistics.getMaxTime());
        stringBuilder.append("毫秒");
        stringBuilder.append("\n");

        stringBuilder.append("请求最小耗时：");
        stringBuilder.append(requestStatistics.getMinTime());
        stringBuilder.append("毫秒");
        stringBuilder.append("\n");

        stringBuilder.append("请求平均耗时：");
        stringBuilder.append(requestStatistics.getAvgTime());
        stringBuilder.append("毫秒");
        stringBuilder.append("\n");

        stringBuilder.append("请求总次数：");
        stringBuilder.append(requestStatistics.getTotalCount());
        stringBuilder.append("\n");

        stringBuilder.append("请求成功次数：");
        stringBuilder.append(requestStatistics.getSuccessCount());
        stringBuilder.append("\n");

        stringBuilder.append("请求失败次数：");
        stringBuilder.append(requestStatistics.getErrorCount());
        stringBuilder.append("\n");

        stringBuilder.append("请求成功率：");
        if (requestStatistics.getTotalCount() != 0)
            stringBuilder.append((requestStatistics.getSuccessCount() * 100 / (double) requestStatistics.getTotalCount()));
        else
            stringBuilder.append("0");
        stringBuilder.append("%");
        stringBuilder.append("\n");
        if (requestStatistics.getException() != null) {
            stringBuilder.append("请求异常发生时间：");
            stringBuilder.append(dateFm.format(requestStatistics.getExceptionTime()));
            stringBuilder.append("\n");
            stringBuilder.append("请求异常信息：[");
            stringBuilder.append(requestStatistics.getException().getLocalizedMessage());
            stringBuilder.append("]");
        }

        stringBuilder.append("\n");
        stringBuilder.append("\n");
        stringBuilder.append("\n");

        ///////////////////////////////////////////////////////////////////////////////////
        stringBuilder.append("当前提交消息时间：");
        stringBuilder.append(dateFm.format(new Date(postMsgStatistics.getStartTimeMs())));
        stringBuilder.append("\n");

        stringBuilder.append("当前提交消息耗时：");
        stringBuilder.append(postMsgStatistics.getCurrentTimeMs());
        stringBuilder.append("毫秒");
        stringBuilder.append("\n");

        stringBuilder.append("处理消息最大耗时：");
        stringBuilder.append(postMsgStatistics.getMaxTime());
        stringBuilder.append("毫秒");
        stringBuilder.append("\n");

        stringBuilder.append("处理消息最小耗时：");
        stringBuilder.append(postMsgStatistics.getMinTime());
        stringBuilder.append("毫秒");
        stringBuilder.append("\n");

        stringBuilder.append("处理消息平均耗时：");
        stringBuilder.append(postMsgStatistics.getAvgTime());
        stringBuilder.append("毫秒");
        stringBuilder.append("\n");

        stringBuilder.append("处理消息总次数：");
        stringBuilder.append(postMsgStatistics.getTotalCount());
        stringBuilder.append("\n");

        stringBuilder.append("处理消息成功次数：");
        stringBuilder.append(postMsgStatistics.getSuccessCount());
        stringBuilder.append("\n");

        stringBuilder.append("处理消息失败次数：");
        stringBuilder.append(postMsgStatistics.getErrorCount());
        stringBuilder.append("\n");

        stringBuilder.append("处理消息成功率：");
        if (postMsgStatistics.getTotalCount() != 0)
            stringBuilder.append((postMsgStatistics.getSuccessCount() * 100 / (double) postMsgStatistics.getTotalCount()));
        else
            stringBuilder.append("0");
        stringBuilder.append("%");
        stringBuilder.append("\n");

        if (postMsgStatistics.getException() != null) {
            stringBuilder.append("处理消息异常发生时间：");
            stringBuilder.append(dateFm.format(postMsgStatistics.getExceptionTime()));
            stringBuilder.append("\n");
            stringBuilder.append("处理消息异常信息：[");
            stringBuilder.append(postMsgStatistics.getException().getLocalizedMessage());
            stringBuilder.append("]");
            stringBuilder.append("\n");
        }
        stringBuilder.append("最大消息包：");
        stringBuilder.append(postMsgStatistics.getMaxMsgBodySize() / 1024.0f);
        stringBuilder.append(" kb");
        stringBuilder.append("\n");

        stringBuilder.append("最小消息包：");
        stringBuilder.append(postMsgStatistics.getMinMsgBodySize() / 1024.0f);
        stringBuilder.append(" kb");
        stringBuilder.append("\n");

        stringBuilder.append("平均消息包：");
        stringBuilder.append(postMsgStatistics.getAvgMsgBodySize() / 1024.0f);
        stringBuilder.append(" kb");
        stringBuilder.append("\n");

        stringBuilder.append("\n");
        stringBuilder.append("\n");
        ///////////////////////////////////////////////////////////////////////////////////


        return stringBuilder.toString();
    }
}
