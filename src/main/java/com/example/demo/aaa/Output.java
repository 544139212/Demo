package com.example.demo.aaa;

import com.example.demo.util.JsonUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Output {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("D:\\书签.txt"));
        String json = br.readLine();
        BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\书签导出.html"));

//        BookmarkListExtendResult result = JsonUtils.serializable(json, BookmarkListExtendResult.class);
//        List<Bookmark> bookmarks = result.getExtend().getBookmarks();

        BookmarkListDataResult result = JsonUtils.serializable(json, BookmarkListDataResult.class);
        List<Bookmark> bookmarks = result.getData().getBookmarks();
        List<Bookmark> toutiaoBookmarks = new ArrayList<>();
        List<Bookmark> partnerBookmarks = new ArrayList<>();
        List<Bookmark> csdnBookmarks = new ArrayList<>();
        List<Bookmark> cnBlogsBookmarks = new ArrayList<>();
        List<Bookmark> jianshuBookmarks = new ArrayList<>();
        List<Bookmark> githubBookmarks = new ArrayList<>();
        List<Bookmark> otherBookmarks = new ArrayList<>();
        for (Bookmark bookmark : bookmarks) {
            if (bookmark.getUrl().substring(0, 20).indexOf("open.toutia") != -1) {
                toutiaoBookmarks.add(bookmark);
            } else if (bookmark.getUrl().substring(0, 20).indexOf("partner.365") != -1) {
                partnerBookmarks.add(bookmark);
            } else if (bookmark.getUrl().substring(0, 20).indexOf("blog.csdn.n") != -1) {
                csdnBookmarks.add(bookmark);
            } else if (bookmark.getUrl().substring(0, 20).indexOf("www.cnblogs") != -1) {
                cnBlogsBookmarks.add(bookmark);
            } else if (bookmark.getUrl().substring(0, 20).indexOf("www.jianshu") != -1) {
                jianshuBookmarks.add(bookmark);
            } else if (bookmark.getUrl().substring(0, 20).indexOf("github.com") != -1) {
                githubBookmarks.add(bookmark);
            } else {
                otherBookmarks.add(bookmark);
            }
        }

        int i = 0;
        System.out.println("<DT><H3>今日头条</H3>");
        bw.write("<DT><H3>今日头条</H3>");
        bw.newLine();
        bw.flush();
        for (Bookmark bookmark : toutiaoBookmarks) {
            i++;
            bw.write("<DT><A HREF=\"" + bookmark.getUrl() + "\">" + bookmark.getTitle() + "</A>");
            bw.newLine();
            bw.flush();
        }
        System.out.println("共" + i + "条");

        i = 0;
        System.out.println("<DT><H3>partner</H3>");
        bw.write("<DT><H3>partner</H3>");
        bw.newLine();
        bw.flush();
        for (Bookmark bookmark : partnerBookmarks) {
            i++;
            bw.write("<DT><A HREF=\"" + bookmark.getUrl() + "\">" + bookmark.getTitle() + "</A>");
            bw.newLine();
            bw.flush();
        }
        System.out.println("共" + i + "条");

        i = 0;
        System.out.println("<DT><H3>CSDN</H3>");
        bw.write("<DT><H3>CSDN</H3>");
        bw.newLine();
        bw.flush();
        for (Bookmark bookmark : csdnBookmarks) {
            i++;
            bw.write("<DT><A HREF=\"" + bookmark.getUrl() + "\">" + bookmark.getTitle() + "</A>");
            bw.newLine();
            bw.flush();
        }
        System.out.println("共" + i + "条");

        i = 0;
        System.out.println("<DT><H3>博客园</H3>");
        bw.write("<DT><H3>博客园</H3>");
        bw.newLine();
        bw.flush();
        for (Bookmark bookmark : cnBlogsBookmarks) {
            i++;
            bw.write("<DT><A HREF=\"" + bookmark.getUrl() + "\">" + bookmark.getTitle() + "</A>");
            bw.newLine();
            bw.flush();
        }
        System.out.println("共" + i + "条");

        i = 0;
        System.out.println("<DT><H3>简书</H3>");
        bw.write("<DT><H3>简书</H3>");
        bw.newLine();
        bw.flush();
        for (Bookmark bookmark : jianshuBookmarks) {
            i++;
            bw.write("<DT><A HREF=\"" + bookmark.getUrl() + "\">" + bookmark.getTitle() + "</A>");
            bw.newLine();
            bw.flush();
        }
        System.out.println("共" + i + "条");

        i = 0;
        System.out.println("<DT><H3>GitHub</H3>");
        bw.write("<DT><H3>GitHub</H3>");
        bw.newLine();
        bw.flush();
        for (Bookmark bookmark : githubBookmarks) {
            i++;
            bw.write("<DT><A HREF=\"" + bookmark.getUrl() + "\">" + bookmark.getTitle() + "</A>");
            bw.newLine();
            bw.flush();
        }
        System.out.println("共" + i + "条");

        i = 0;
        System.out.println("<DT><H3>其它</H3>");
        bw.write("<DT><H3>其它</H3>");
        bw.newLine();
        bw.flush();
        for (Bookmark bookmark : otherBookmarks) {
            i++;
            bw.write("<DT><A HREF=\"" + bookmark.getUrl() + "\">" + bookmark.getTitle() + "</A>");
            bw.newLine();
            bw.flush();
        }
        System.out.println("共" + i + "条");

        br.close();
        bw.close();
    }
}
