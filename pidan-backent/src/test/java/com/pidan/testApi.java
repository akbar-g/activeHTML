package com.pidan;

import com.pidan.constant.CrawlConstant;
import com.pidan.model.entity.News;
import com.pidan.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 黄大头
 * @date 2023年04月02日 18:48
 */
@SpringBootTest
public class testApi {

    @Resource
    private NewsService newsService;

    @Test
    void testFecthPrcture() throws IOException {
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
        System.out.println(newsList);


//        for (Element element : elements) {
//            System.out.println("=========");
//            System.out.println("element = " + element);
//            String href = element.absUrl("href");
//            System.out.println("href = " + href);
//            //取路由地址(href)

//            Map<String, Object> map = JSONUtil.toBean(m, Map.class);
//            String murl = (String) map.get("murl");
//            System.out.println(murl);
//
//            //取标题
//            String title = element.select(".inflnk").get(0).attr("aria-label");
//            Picture picture = new Picture();
//            picture.setTitle(title);
//            picture.setUrl(murl);
//            pictureList.add(picture);
    }
}
