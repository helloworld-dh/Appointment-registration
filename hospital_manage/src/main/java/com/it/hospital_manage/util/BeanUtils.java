package com.it.hospital_manage.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

public class BeanUtils extends org.springframework.beans.BeanUtils{

    public static void copyBean(Object source, Object target, Class<?> editable, String... ignoreProperties)
            throws BeansException {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Source must not be null");

        Class<?> actualEditable = target.getClass();
        if (editable != null){
            if (!editable.isInstance(target)){     //target是否可以强转为editable这个类型
                throw new IllegalArgumentException("Target class [" + target.getClass().getName() + "] not assignable to Editable class [" + editable.getName() + "]");
            }
            actualEditable = editable;
        }
        //根据传入对象可以找到对应的属性数组
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        List<String> ignoreList = (ignoreProperties !=null) ? Arrays.asList(ignoreProperties) :null;

        for (PropertyDescriptor targetPd : targetPds) {
            //获取属性的get方法
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod !=null && (ignoreProperties ==null || (!ignoreList.contains(targetPd.getName())))){
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd !=null){
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod!=null && ClassUtils.isAssignable(writeMethod.getParameterTypes()[0],readMethod.getReturnType())){
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())){
                                readMethod.setAccessible(true);   // 可获取属性的值
                            }
                            Object value = readMethod.invoke(source);
                            if (value!=null){ // 只复制不为null的属性
                                if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())){
                                    writeMethod.setAccessible(true);
                                }
                                writeMethod.invoke(target,value);
                            }
                        } catch (Throwable ex){
                            throw new FatalBeanException("Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                        }
                    }
                }
            }
        }
    }

}
