/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.my.iud.test;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author dell
 */
public class Grab {

    public static void main(String[] args) {
        int count = 1;
        for (int i = 1; i <= 20; i++) {
            try {
                Document doc = Jsoup.connect("http://s.taobao.com/search?q=首饰9.9&s=" + (i - 1) * 40).get();
                Elements elements = doc.select("ul li.list-item");
                for (Element e : elements) {
                    String money = e.select("ul li em").html().trim();
                    if (money.indexOf("9.") != -1) {
                        String[] array = money.split("\\.");
                        if (array[0].length() == 1) {
                            System.out.println(e.select("div a").attr("href"));
                            count++;
                            if(count > 100) break;
                        }
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Grab.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
