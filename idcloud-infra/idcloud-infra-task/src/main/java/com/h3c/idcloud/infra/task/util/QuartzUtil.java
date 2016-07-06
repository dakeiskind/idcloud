package com.h3c.idcloud.infra.task.util;

import com.h3c.idcloud.infra.task.constants.TaskConstants;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Quartz工具类
 *
 * @author Chaohong.Mao
 */
public class QuartzUtil {

    /**
     * 获得 cron expression.
     *
     * @param updateCycle 周期时间
     * @return the cron expression
     */
    public static String getCronExpression(String updateCycle) {

        double hour = Double.parseDouble(updateCycle);
        String cronExpression = "";
        Calendar calendar = Calendar.getInstance();

        //设置cron周期为每1~59分钟执行一次
        if (hour < 1.0) {
            double minute = hour * 60;
            if (Math.floor(minute) == minute) {
                cronExpression = calendar.get(Calendar.SECOND) + " " +
                        calendar.get(Calendar.MINUTE) + "/" +
                        (int) minute + " * * * ?";
            } else {
                cronExpression = "* * */1 * * ?";
            }
            //设置cron周期为每1~23小时执行一次
        } else if (hour < 24.0 && Math.floor(hour) == hour) {
            cronExpression = calendar.get(Calendar.SECOND) + " " +
                    calendar.get(Calendar.MINUTE) + " " +
                    calendar.get(Calendar.HOUR_OF_DAY) + "/" +
                    (int) hour + " * * ?";

            //设置cron周期为每几天执行一次,最多不超过30天
        } else if (hour % 24.0 == 0 && hour / 24 < 30) {
            cronExpression = calendar.get(Calendar.SECOND) + " " +
                    calendar.get(Calendar.MINUTE) + " " +
                    calendar.get(Calendar.HOUR_OF_DAY) + " " +
                    calendar.get(Calendar.DAY_OF_MONTH) + "/" +
                    (int) hour / 24 + " * ?";

            //其他
        } else {
            cronExpression = "* * */12 * * ?";
        }
        return cronExpression;
    }

    /**
     * 获得 params.
     *
     * @param <T>     返回泛型
     * @param context 任务参数
     * @return 周期任务类需要的参数 params
     */
    public static <T> T getParams(JobExecutionContext context) {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        return (T) dataMap.get(TaskConstants.TASK_PARAM_KEY);
    }

    /**
     * 换算小时->分钟.
     *
     * @param updateCycle 换算小时数
     * @return 对应分钟
     */
    public static int getMinuteFromHour(String updateCycle) {
        double hour = Double.parseDouble(updateCycle);
        return BigDecimal.valueOf(hour * 60).intValue();
    }
}








