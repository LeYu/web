package cn.yl.web.job;

import cn.yl.web.model.Ball;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jfinal.plugin.activerecord.Db;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by john on 2016/5/31.
 * 双色球
 * 此任务可以将历史双色球结果全部同步到系统
 * 实现方法，判断数据库记录数是否等于总记录数，如果等于，不用抓取了。不等于：抓取第一页数据，update/insert到数据库 在判断记录总数，
 * 如果不满足，则继续下一页，直到记录数大等于总开奖数
 */
@Slf4j
public class  DoubleChromoSphere implements Job {
    private static String url = "http://kaijiang.zhcw.com/zhcw/html/ssq/list.html";
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {}
    public void execute() {
        Ball ballDao = new Ball();
        Document doc = getDocumentFromUrl(url, 5);
        int dbCount = 0;
        int pageCount = 0;
        List<Ball> balls = null;
        if (doc !=null){
            dbCount = ballDao.count(); // 数据库查询出count
            Elements tables =  doc.getElementsByTag("table");
            Element table = tables.first();
            Element tbody = table.getElementsByTag("tbody").first();
            Elements trs = tbody.getElementsByTag("tr");
            Map<String,String> lastTR = parseLastTR(trs.last());
            pageCount = Integer.parseInt(lastTR.get("count"));
            if(pageCount <= dbCount){
                return;
            }
            int pageSize = Integer.parseInt( lastTR.get("pageSize"));
            String nextPageUrl = lastTR.get("nextPageUrl");
            String lastPageUrl = lastTR.get("lastPageUrl");
            String u = url;
            int num = 0;
            while(num < pageSize){ // 从第一页开始抓取数据入库
                doc = getDocumentFromUrl(u, 5);
                if(doc == null){ // 连不上就不用在继续了
                    return;
                }
                 tables =  doc.getElementsByTag("table");
                 table = tables.first();
                 tbody = table.getElementsByTag("tbody").first();
                 trs = tbody.getElementsByTag("tr");
                 lastTR = parseLastTR(trs.last());

                for( int i = 2, len = trs.size(); i < len-1; i++){
                    Map<String,String> map = parseTR(trs.get(i));
                    Ball ball1 = ballDao.findById(map.get("qh"));
                    if(ball1==null){
                        Ball ball = new Ball();
                        ball.setQh(map.get("qh"));
                        ball.setOpendate(Date.valueOf(map.get("date")));
                        ball.setRed1(map.get("red1"));
                        ball.setRed2(map.get("red2"));
                        ball.setRed3(map.get("red3"));
                        ball.setRed4(map.get("red4"));
                        ball.setRed5(map.get("red5"));
                        ball.setRed6(map.get("red6"));
                        ball.setBlue(map.get("blue"));
                        ball.setPrizes(Integer.parseInt(map.get("prizes")));
                        ball.setFirstnum(Integer.parseInt(map.get("firstNum")));
                        ball.setSecondnum(Integer.parseInt(map.get("secondNum")));
                        boolean bool = ball.save();
                        log.info("qh:"+map.get("qh")+" bool:"+bool);
                    }

                }
                log.info("-----------第"+lastTR.get("currIndex")+"页，记录数："+(trs.size()-3));
                // 新 dbCount
                dbCount = ballDao.count();
                if(pageCount <= dbCount){
                    log.info("--------记录一致，退出。");
                    return;
                }else{
                    log.info("-------记录不一致，继续...");
                }
                nextPageUrl = lastTR.get("nextPageUrl");
                lastPageUrl = lastTR.get("lastPageUrl");
                u = nextPageUrl;
                num++;
            }

        }

    }

    /**
     *
     * @param url 地址
     * @param tryNum 失败尝试次数
     * @return doc
     */
    public static Document getDocumentFromUrl(String url, int tryNum){
        Document doc = null;
        try {
            return getDocumentFromUrl(url);
        } catch (IOException e) {
            log.error("抓去双色球开奖页面失败："+e.getMessage());
            while(tryNum > 0){
                try {
                    Thread.sleep(5000L);
                    log.info("--------尝试重新抓取双色球数据："+url);
                    return getDocumentFromUrl(url);
                } catch (IOException e1) {
                    tryNum--;
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return doc;
    }
    public static Document getDocumentFromUrl(String url) throws IOException {
        return  Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0").timeout(15000).get();
    }
    /**
     * 解析一行tr数据，如下形式
     * <tr>
         <td align="center">2016-05-26</td>
         <td align="center">2016060</td>
         <td align="center" style="padding-left:10px;">
             <em class="rr">04</em>
             <em class="rr">05</em>
             <em class="rr">22</em>
             <em class="rr">26</em>
             <em class="rr">29</em>
             <em class="rr">32</em>
             <em>08</em>
        </td>
         <td><strong>320,540,202</strong></td>
        <td align="left" style="color:#999;"><strong>15</strong>
            (京 苏 浙 鄂..)
        </td>
        <td align="center"><strong class="rc">47</strong></td>
        <td align="center">
            <a href="http://www.zhcw.com/ssq/kjgg/" target="_blank"><img src="http://images.zhcw.com/zhcw2010/kaijiang/zhcw/ssqpd_42.jpg" width="16" height="16" align="absmiddle" title="详细信息"></a>
            <a href="http://www.zhcw.com/video/kaijiangshipin/" target="_blank"><img src="http://images.zhcw.com/zhcw2010/kaijiang/zhcw/ssqpd_43.jpg" width="16" height="16" align="absmiddle" title="开奖视频"></a>
        </td>
     </tr>
     * @param tr
     * @return 该行结果的map
     */
    private Map<String,String> parseTR(Element tr){
        Map<String,String> result = Maps.newHashMap();
        List<String> list = Lists.newArrayList();
        String text = tr.text().replace(",","");
        Pattern p = Pattern.compile("(\\d+)");
        Matcher m = p.matcher(text);
        while(m.find()){
            list.add(m.group(1));
        }
        result.put("date", list.get(0) + "-" + list.get(1) + "-" + list.get(2));
        result.put("qh",list.get(3));
        result.put("red1",list.get(4));
        result.put("red2",list.get(5));
        result.put("red3",list.get(6));
        result.put("red4",list.get(7));
        result.put("red5",list.get(8));
        result.put("red6",list.get(9));
        result.put("blue",list.get(10));
        result.put("prizes",list.get(11));
        result.put("firstNum",list.get(12));
        result.put("secondNum",list.get(13));

        return result;
    }

    /**
     * 将最后一行解析，得知总记录数和页数
     * @param tr 最后一行
     * @return map
     * <tr>
        <td colspan="7" align="center" style="background:#fdf2e3;">
            <p class="zhu"></p>
            <p class="pg"> 共<strong>99</strong> 页 /<strong>1961 </strong>条记录 <strong>
            <a href="/zhcw/inc/ssq/ssq_wqhg.jsp">首页</a></strong>
            <strong><a href="/zhcw/inc/ssq/ssq_wqhg.jsp?pageNum=98">上一页</a></strong>
            <strong><a href="/zhcw/inc/ssq/ssq_wqhg.jsp?pageNum=99">下一页</a></strong>
            <strong><a href="/zhcw/inc/ssq/ssq_wqhg.jsp?pageNum=99">末页</a></strong>
            当前第<strong> 99 </strong>页</p>
        </td>
    </tr>
     */
    private Map<String,String> parseLastTR(Element tr){
        Map<String,String> result = Maps.newHashMap();
        List<String> list = Lists.newArrayList();
        String text = tr.text();
        Pattern p = Pattern.compile("(\\d+)");
        Matcher m = p.matcher(text);
        while (m.find()){
            list.add(m.group(1));
        }
        result.put("pageSize",list.get(0));
        result.put("count",list.get(1));
        result.put("currIndex", list.get(2));
        Elements links = tr.select("a[href]");
        result.put("nextPageUrl", links.get(2).attr("abs:href"));
        result.put("lastPageUrl", links.get(3).attr("abs:href"));
        return result;
    }
}
