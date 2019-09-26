/**
 * Author : MIAOHY
 * Time :2019/7/27 13:34
 * Beauty is better than ugly!
 */
package com.miaohy.crawl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GetUrlt {
    static List<String> useragent = new ArrayList<>();
    static String[] Useragent = {
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/22.0.1207.1 Safari/537.1",
            "Mozilla/5.0 (X11; CrOS i686 2268.111.0) AppleWebKit/536.11 (KHTML, like Gecko) Chrome/20.0.1132.57 Safari/536.11",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.6 (KHTML, like Gecko) Chrome/20.0.1092.0 Safari/536.6",
            "Mozilla/5.0 (Windows NT 6.2) AppleWebKit/536.6 (KHTML, like Gecko) Chrome/20.0.1090.0 Safari/536.6",
            "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/19.77.34.5 Safari/537.1",
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.9 Safari/536.5",
            "Mozilla/5.0 (Windows NT 6.0) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.36 Safari/536.5",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1063.0 Safari/536.3",
            "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1063.0 Safari/536.3",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_0) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1063.0 Safari/536.3",
            "Mozilla/5.0 (Windows NT 6.2) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1062.0 Safari/536.3",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1062.0 Safari/536.3",
            "Mozilla/5.0 (Windows NT 6.2) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1061.1 Safari/536.3",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1061.1 Safari/536.3",
            "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1061.1 Safari/536.3",
            "Mozilla/5.0 (Windows NT 6.2) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1061.0 Safari/536.3",
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.24 (KHTML, like Gecko) Chrome/19.0.1055.1 Safari/535.24",
            "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/535.24 (KHTML, like Gecko) Chrome/19.0.1055.1 Safari/535.24",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36 OPR/26.0.1656.60",
            "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0",
            "Mozilla/5.0 (X11; U; Linux x86_64; zh-CN; rv:1.9.2.10) Gecko/20100922 Ubuntu/10.10 (maverick) Firefox/3.6.10",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/534.57.2 (KHTML, like Gecko) Version/5.1.7 Safari/534.57.2",
            "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.133 Safari/534.16",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36",
            "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.11 (KHTML, like Gecko) Chrome/20.0.1132.11 TaoBrowser/2.0 Safari/536.11",
    };

    public static String[] getUrlRealFromTxt(File file) {
        StringBuilder sb = new StringBuilder();
        BufferedReader bufr = null;
        try {
            bufr = new BufferedReader(new FileReader(file));
            String string = null;
            while ((string = bufr.readLine()) != null) {
                sb.append(string);
                sb.append(",");
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (bufr != null)
                try {
                    bufr.close();
                } catch (Exception e2) {
                    // TODO: handle exception
                }
        }
        String[] str = sb.toString().split(",");
        return str;
    }
    public static void main(String[] args) {

        String urlPath = "D:\\crawlerTest\\";
        LinkedList<String> urllist = new LinkedList<>();
        for (int i = 1; i < 462; i++) {
            String url = urlPath+"第"+i+"页.txt";
            File file = new File(url);
            String []  urls = getUrlRealFromTxt(file);
            for (int j = 0; j < urls.length; j++) {
                urllist.add(urls[j]);
            }
        }
        int length = urllist.size();

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            final int currentIndex = i;
            executorService.execute(new Runnable() {
                public void run() {
                    Random sRandom = new Random();
                    int index = sRandom.nextInt(Useragent.length);
                    System.setProperty("proxyType", "4");
                    System.setProperty("proxyPort", "1080");
                    System.setProperty("proxyHost", "127.0.0.1");
                    System.setProperty("proxySet", "true");

                    String url = urllist.get(currentIndex);
                    Document doc = null;
                    try {
                        doc = Jsoup.connect(url)
                                .userAgent(Useragent[index])
                                .get();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        System.out.println(currentIndex + " 获取失败");
                    }
                    Elements gclass = doc.select("a.gallerythumb");
                    // 得到html的所有东西
                    Element content = doc.getElementById("content");

                    // h1 文件夹名
                    Elements h  = content.getElementsByTag("h1");
                    String name1  = h.text();
                    String name = name1.replaceAll("/","");
                    File dir = new File("D:\\chineseBig\\"+name);
                    dir.mkdirs();

                    List<String> list1 = new LinkedList<>();
                    for (Element g : gclass) {
                        String urlt = g.getElementsByTag("img").attr("data-src");
                        StringBuilder sb = new StringBuilder(urlt);
                        String urlr = sb.replace(8, 9, "i").replace(sb.length() - 5, sb.length() - 4, "").toString();
                        list1.add(urlr);
                    }

                    for(String urlReal:list1){
                        InputStream in = null;
                        OutputStream out = null;
                        int num = 1;
                        HttpURLConnection connection = null;
                        try {
                            // 获取连接
                            URL fileUrl = new URL(urlReal);
                            connection = (HttpURLConnection) fileUrl.openConnection();
                            connection.setConnectTimeout(10 * 1000);
                            // 设置请求头
                            int index2 = sRandom.nextInt(Useragent.length);
                            connection.setRequestProperty("User-Agent", Useragent[index2]);
                            // 获取输入流
                            in = connection.getInputStream();
                            if (connection.getResponseCode() == 200) {
                                System.out.println(urlReal + "连接成功");
                            }
                            String savepath = "D:\\chineseBig\\"+name;

                            String[] filenames = urlReal.split("/");
                            String filename = filenames[filenames.length - 1];
                            File file = new File(savepath +"\\"+ filename);

                            if (file.exists()) {
                                System.out.println(filename + "已存在，自动跳过");
                                continue;
                            }

                            out = new FileOutputStream(file);
                            byte[] bytes = new byte[2048];
                            int len = 0;
                            while ((len = in.read(bytes)) != -1) {
                                out.write(bytes, 0, len);
                            }
                            System.out.println(filename + " download success!!!");
                            connection.disconnect();
                        } catch (IOException e) {
                            System.out.println(e.toString());
                            System.out.println("发生错误，进行第" + (++num) + "次尝试");

                        } finally {
                            if (in != null) {
                                try {
                                    in.close();
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                            if (out != null) {
                                try {
                                    out.close();
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }
                    }


                }
            });
        }

    }

}

