package com.kedacom.condition;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;


/**
 * 自定义逻辑返回需要导入的组件
 */
public class MyImportSelector implements ImportSelector {
    /**
     * 返回值,就是到导入到容器中的组件类全类名
     * @param importingClassMetadata 当前标注@import注解类的所有注解信息
     * @return new String[]
     */
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        // 这里不能访问 null 会造成空指针异常
        return new String[]{"com.kedacom.bean.Blue", "com.kedacom.bean.Yellow"};
    }
}
