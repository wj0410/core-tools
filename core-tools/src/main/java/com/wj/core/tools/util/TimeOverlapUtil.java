package com.wj.core.tools.util;

import cn.hutool.core.collection.CollectionUtil;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.time.DateTimeException;
import java.util.*;

/**
 * 时间段重叠比较
 */
public class TimeOverlapUtil {

    /**
     * 判断多个时间段是否有重叠（交集）
     *
     * @param timePairs 时间段数组
     * @param isStrict  是否严格重叠，true 严格，没有任何相交或相等；false 不严格，可以首尾相等，比如2021-05-29到2021-05-31和2021-05-31到2021-06-01，不重叠。
     * @return 返回是否重叠
     */
    private static boolean isOverlap(TimePair[] timePairs, boolean isStrict) {
        if (timePairs == null || timePairs.length == 0) {
            throw new DateTimeException("timePairs不能为空");
        }

        Arrays.sort(timePairs, Comparator.comparingLong(TimePair::getStart));

        for (int i = 1; i < timePairs.length; i++) {
            if (isStrict) {
                if (!(timePairs[i - 1].getEnd() < timePairs[i].getStart())) {
                    return true;
                }
            } else {
                if (!(timePairs[i - 1].getEnd() <= timePairs[i].getStart())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断多个时间段是否有重叠（交集）
     *
     * @param timePairList 时间段列表
     * @param isStrict     是否严格重叠，true 严格，没有任何相交或相等；false 不严格，可以首尾相等，比如2021-05-29到2021-05-31和2021-05-31到2021-06-01，不重叠。
     * @return 返回是否重叠
     */
    public static boolean isOverlap(List<TimePair> timePairList, boolean isStrict) {
        if (CollectionUtil.isEmpty(timePairList)) {
            throw new DateTimeException("timePairList不能为空");
        }
        TimePair[] timePairs = new TimePair[timePairList.size()];
        timePairList.toArray(timePairs);
        return isOverlap(timePairs, isStrict);
    }

    public static class TimePair {
        public TimePair(long start, long end) {
            if (end < start) {
                throw new DateTimeException("end不能小于start");
            }
            this.start = start;
            this.end = end;
        }

        private long start;
        private long end;

        public long getStart() {
            return start;
        }

        public void setStart(long start) {
            this.start = start;
        }

        public long getEnd() {
            return end;
        }

        public void setEnd(long end) {
            this.end = end;
        }
    }
}
