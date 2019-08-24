package com.team.house;

import com.team.house.service.DistrictService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) {
        ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
        DistrictService districtService=(DistrictService) ctx.getBean("distrctServiceImpl");
        districtService.deleteBySingleId(1027);
    }
}
