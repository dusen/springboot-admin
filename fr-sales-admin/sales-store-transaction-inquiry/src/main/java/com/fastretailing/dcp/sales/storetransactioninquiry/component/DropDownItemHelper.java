/**
 * @(#)DropDownItemHelper.java
 *
 *                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.storetransactioninquiry.component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.fastretailing.dcp.sales.storetransactioninquiry.constant.DropDownItem;


/**
 * Drop down item helper class.
 */
@Component
public class DropDownItemHelper {

    /**
     * Create drop down list used for display in view.
     * 
     * @param listObject List value of any object.
     * @param keyProperty Property of item which used for get value and set to key.
     * @param nameProperty Property of item which used for get value and set to value.
     * @return Drop down item list.
     */
    public List<DropDownItem> createDropDownList(List<?> listObject, String keyProperty,
            String nameProperty) {
        return createDropDownList(listObject, keyProperty, nameProperty, false);
    }

    /**
     * Create drop down list used for display in view.
     * 
     * @param listObject List value of any object.
     * @param keyProperty Property of item which used for get value and set to key.
     * @param nameProperty Property of item which used for get value and set to value.
     * @param hasKey If value true, attach key into value.
     * @return Drop down item list.
     */
    private List<DropDownItem> createDropDownList(List<?> listObject, String keyProperty,
            String nameProperty, boolean hasKey) {
        return listObject.stream().map(obj -> {
            try {
                Method methodGetKey = obj.getClass().getMethod(keyProperty);
                Method methodGetName = obj.getClass().getMethod(nameProperty);
                String key = String.valueOf(methodGetKey.invoke(obj));
                String value = String.valueOf(methodGetName.invoke(obj));
                if (hasKey) {
                    return new DropDownItem(key, key + " " + value);
                }
                return new DropDownItem(key, value);
            } catch (SecurityException | IllegalArgumentException | NoSuchMethodException
                    | IllegalAccessException | InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toList());
    }
}
