package com.offcn.project.test;

import com.offcn.project.service.Project;
import com.offcn.project.service.impl.ProjectImpl;

/**
 * @author zhangjian
 * @email 13120082225@163.com
 * @date 2020/8/18
 */
public class ProjectTest {

    public void m1(){
        ProjectImpl project = new ProjectImpl();
        if (project instanceof Project){
            System.out.println("这个对象是project业务线的");
        }
    }
}
