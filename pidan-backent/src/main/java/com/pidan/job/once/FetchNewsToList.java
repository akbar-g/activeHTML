package com.pidan.job.once;

import com.pidan.constant.CrawlConstant;
import com.pidan.model.entity.News;
import com.pidan.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.boot.CommandLineRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 黄大头
 * @date 2023年04月04日 13:34
 * 初始化新闻
 */
//@Component
@Slf4j
public class FetchNewsToList implements CommandLineRunner {

    @Resource
    private NewsService newsService;

    @Override
    public void run(String... args) throws Exception {
        //首页 https://www.lnist.edu.cn/index/xxxw.htm
        //https://www.lnist.edu.cn/index/xxxw/150.htm
        String url = String.format("https://www.lnist.edu.cn/index/xxxw.htm");
        Document doc = Jsoup.connect(url).get();
        //todo 校验为空
        List<News> newsList = new ArrayList<>();
        Elements elements = doc.select(".ColumnBlockRight div table");
        System.out.println(doc);

        Elements elements1 = doc.select(".pb_sys_common");

        System.out.println("elements1 = " + elements1);
        //学校新闻首页 20 条数据
        for (int i = 0; i < 20; i++) {
            try {
                //取 herf
                String m = elements.select("a").get(i).attr("href");
                m.replace("..", "");
                //拼接herf
                String herf = CrawlConstant.HREF_PREFIX + m;
                //取标题
                String title = elements.select("a").get(i).attr("title");
                //取发布时间
                String publishTime = elements.select(".SimpleDateTd").get(i).text();

                System.out.println("publishTime = " + publishTime);
                News news = new News();
                //todo
                news.setTitle(title);
                news.setHerf(herf);
                news.setPublishTime(publishTime);
                //newsService.save(news);
                newsList.add(news);
                //System.out.println("title = " + title);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        boolean b = newsService.saveBatch(newsList);
    }
}
