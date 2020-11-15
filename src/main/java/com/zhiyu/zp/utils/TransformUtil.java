package com.zhiyu.zp.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * 转换辅助
 * @author wdj
 *
 */

public class TransformUtil {

	/**
	 * 将Map数据转成相应的对象
	 * @param map
	 * @param beanClass
	 * @return
	 * @throws Exception
	 */
	public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {    
        if (map == null)  
            return null;    
  
        Object obj = beanClass.newInstance();  
  
        Field[] fields = obj.getClass().getDeclaredFields();   
        for (Field field : fields) {    
            int mod = field.getModifiers();    
            if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){    
                continue;    
            }    
  
            field.setAccessible(true);    
            field.set(obj, map.get(field.getName()));   
        }   
  
        return obj;    
    }    
  
	/**
	 * 将对象转成Map数据格式
	 * @param obj
	 * @return
	 * @throws Exception
	 */
    public static Map<String, Object> objectToMap(Object obj) throws Exception {    
        if(obj == null){    
            return null;    
        }   
  
        Map<String, Object> map = new HashMap<String, Object>();    
  
        Field[] declaredFields = obj.getClass().getDeclaredFields();    
        for (Field field : declaredFields) {    
            field.setAccessible(true);  
            map.put(field.getName(), field.get(obj));  
        }    
  
        return map;  
    } 
    
    
    
 // 该方法的参数列表是一个类的 类名、成员变量、给变量的赋值
 	public static void setProperty(Object obj, String PropertyName, Object value)
 			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
 		// 获取obj类的字节文件对象
 		Class<?> c = obj.getClass();
 		// 获取该类的成员变量
 		Field f = c.getDeclaredField(PropertyName);
 		// 取消语言访问检查
 		f.setAccessible(true);
 		// 给变量赋值
 		f.set(obj, value);
 	}
}
