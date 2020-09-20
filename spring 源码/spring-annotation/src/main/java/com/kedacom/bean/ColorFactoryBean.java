package com.kedacom.bean;

import org.springframework.beans.factory.FactoryBean;

public class ColorFactoryBean implements FactoryBean<Color> {

    /**
     * 返回一个 Color 对象,这个对象会添加到容器中
     * @return Color
     * @throws Exception e
     */
    @Override
    public Color getObject() throws Exception {
        return new Color();
    }

    @Override
    public Class<?> getObjectType() {
        return Color.class;
    }

    /**
     * 是否为单实例 true or false
     * @return true
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
}
