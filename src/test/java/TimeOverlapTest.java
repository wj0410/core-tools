//import com.wj.core.tools.util.TimeOverlapUtil;
//import org.apache.commons.lang3.time.DateUtils;
//import org.junit.Test;
//
//import java.text.ParseException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//public class TimeOverlapTest {
//    @Test
//    public static void main(String[] args) throws ParseException {
//        String d1s = "2022-01-01 10:00:00";
//        String d1e = "2022-01-01 18:00:00";
//        String d2s = "2022-01-02 10:00:00";
//        String d2e = "2022-01-02 18:00:00";
//        String d3s = "2022-01-03 10:00:00";
//        String d3e = "2022-01-03 18:00:00";
//        Date date1s = DateUtils.parseDate(d1s, "yyyy-MM-dd HH:mm:ss");
//        Date date1e = DateUtils.parseDate(d1e, "yyyy-MM-dd HH:mm:ss");
//        Date date2s = DateUtils.parseDate(d2s, "yyyy-MM-dd HH:mm:ss");
//        Date date2e = DateUtils.parseDate(d2e, "yyyy-MM-dd HH:mm:ss");
//        Date date3s = DateUtils.parseDate(d3s, "yyyy-MM-dd HH:mm:ss");
//        Date date3e = DateUtils.parseDate(d3e, "yyyy-MM-dd HH:mm:ss");
//        List<TimeOverlapUtil.TimePair> timePairList = new ArrayList<>();
//        TimeOverlapUtil.TimePair t1 = new TimeOverlapUtil.TimePair(date1s.getTime(), date1e.getTime());
//        TimeOverlapUtil.TimePair t2 = new TimeOverlapUtil.TimePair(date2s.getTime(), date2e.getTime());
//        TimeOverlapUtil.TimePair t3 = new TimeOverlapUtil.TimePair(date3s.getTime(), date3e.getTime());
//        timePairList.add(t1);
//        timePairList.add(t2);
//        timePairList.add(t3);
//        System.out.println(TimeOverlapUtil.isOverlap(timePairList, true));
//    }
//}
