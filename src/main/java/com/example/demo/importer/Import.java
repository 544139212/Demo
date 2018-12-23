package com.example.demo.importer;


import com.example.demo.importer.ImageUtils;
import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Import {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(new File("D:\\backup\\json.txt")));

        String json = br.readLine();

        Gson gson = new Gson();

        A a = gson.fromJson(json, A.class);

        List<C> modules = a.getData().getModules();

        int num = 1;
        long timestamp = System.currentTimeMillis();
        for (C module : modules) {
//            System.out.println(module.getTitle()); // 女装

            System.out.println("insert into category(id, name, parent_id) values(" + num + ", '" + module.getTitle() + "', 0);");

            int parentId = num++;
            List<F> maitResources = module.getCateBox().getMaitResources();
            for (F maitResouce : maitResources) {
//                System.out.println(maitResouce.getTitle()); // 夏热卖
//                System.out.println(maitResouce.getImage()); // 夏热卖图片

//                ImageUtils.download(maitResouce.getImage(), module.getTitle() + "-" + maitResouce.getTitle(), timestamp);
                System.out.println("insert into category(id, name, parent_id, image_url) values(" + num++ + ", '" + maitResouce.getTitle() + "', " + parentId + ", '" + maitResouce.getImage() + "');");
            }

        }
    }

}
