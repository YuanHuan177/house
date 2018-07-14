package com.babyjuan.house.service.manager;

import com.babyjuan.house.base.BaseTest;
import com.babyjuan.house.dao.entity.RentingHouse;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: hjg
 * @Date: Create in 2018/6/21 17:48
 * @Description:
 */
public class RentingHouseServiceTest extends BaseTest {

    @Autowired
    private RentingHouseService rentingHouseService;

    private Date lastPushDate = DateTime.parse("1000-01-01").toDate();

    @Test
    public void getRelativeHouseList() throws Exception {
        String com1 = "蓝筹";
        String com2 = "阅城";
        queryHouse(com1, com2);
        lastPushDate = DateTime.parse("2100-01-01").toDate();
        queryHouse(com1, com2);
    }

    private void queryHouse(String com1, String com2) {
        List<RentingHouse> list1 = rentingHouseService.getLatestRelativeHouseList(com1, lastPushDate);
        List<RentingHouse> list2 = rentingHouseService.getLatestRelativeHouseList(com2, lastPushDate);
        System.out.println(com1 + " : ");
        printHouse(list1);
        System.out.println(com2 + " :");
        printHouse(list2);
    }

    private void printHouse(List<RentingHouse> houseList) {
        for (RentingHouse house : houseList) {
            System.out.println(house);
        }
    }

}