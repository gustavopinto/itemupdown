/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.my.iud.comparator;

import com.taobao.api.domain.Item;
import java.util.Comparator;

/**
 *
 * @author dell
 */
public class ItemListingComparator implements Comparator<Item> {

    @Override
    public int compare(Item o1, Item o2) {
       boolean cp = o1.getDelistTime().before(o2.getDelistTime());
       if(cp){
           return 1;
       }
       return -1;
    }

}
