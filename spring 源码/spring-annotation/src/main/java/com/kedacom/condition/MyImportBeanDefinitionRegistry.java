package com.kedacom.condition;

import com.kedacom.bean.RainBow;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefinitionRegistry implements ImportBeanDefinitionRegistrar {
    /**
     * @param importingClassMetadata 当前类的注解信息
     * @param registry 把所需要的添加到容器中的 bean 注册进来
     *        -> BeanDefinitionRegistry.registerBeanDefinition 注册 bean
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        boolean red = registry.containsBeanDefinition("com.kedacom.bean.Red");
        boolean blue = registry.containsBeanDefinition("com.kedacom.bean.Blue");
        if (red && blue) {
            RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(RainBow.class);
            registry.registerBeanDefinition("rainBow",rootBeanDefinition);
        }
    }
}
