package com.example.demo.importer2;

import com.example.demo.importer.ImageUtils;
import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Import {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(new File("D:\\backup\\json_goods.txt")));

        String json = br.readLine();

        Gson gson = new Gson();

        A a = gson.fromJson(json, A.class);

        List<N> docs = a.getData().getWall().getDocs();

        int num = 1;
        long timestamp = System.currentTimeMillis();
        for (N doc : docs) {
//            System.out.println(doc.getTitle()); // 商品名称
//            System.out.println(doc.getImg()); // 商品图片
//            System.out.println(doc.getPrice()); // 商品现价
//            System.out.println(doc.getSale()); // 商品销量
//            System.out.println(String.join(",", doc.getProps())); // 商品tag

            int id = num++;

            ImageUtils.download(doc.getImg(), id + "", timestamp);
            System.out.println("insert into goods(id, name, image_url, content) " +
                    "values(" + id + ", '" + doc.getTitle() + "', '" + doc.getImg() + "', '" + String.join(",", doc.getProps()) + "'" + ");");

            System.out.println("insert into goods_sku(id, goods_id, image_url, promote_price, market_price) " +
                    "values(" + id + ", " + id + ", '" + doc.getImg() + "', " + doc.getPrice() + ", " + doc.getSale() + ");");
        }
    }

}
