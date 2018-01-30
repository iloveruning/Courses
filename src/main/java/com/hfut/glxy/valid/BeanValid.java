package com.hfut.glxy.valid;


import com.hfut.glxy.valid.annotation.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author chenliangliang
 * @date 2018/1/2
 */
public class BeanValid {

    public static List<String> valid(Object obj) {
        List<String> res = new ArrayList<>();
        Class<?> clazz = obj.getClass();
        try {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                System.out.println(field.getName());
                Annotation[] annotation = field.getDeclaredAnnotations();

                for (Annotation anno : annotation) {
                    if (anno instanceof NotNull) {
                        NotNull notNull = (NotNull) anno;
                        if (field.get(obj) == null) {
                            res.add(notNull.msg());
                        }
                    } else if (anno instanceof NotBlank) {
                        NotBlank notBlank = (NotBlank) anno;
                        Object o = field.get(obj);
                        if (o == null) {
                            res.add(notBlank.msg());
                        } else {
                            if (o instanceof String) {
                                String s = (String) o;
                                if (s.trim().length() == 0) {
                                    res.add(notBlank.msg());
                                }
                            } else {
                                res.add("ERROR: 数据类型错误" + o.getClass().getTypeName());
                            }
                        }

                    }else if (anno instanceof NotEmpty) {
                        NotEmpty notEmpty = (NotEmpty) anno;
                        Object o = field.get(obj);
                        if (o == null) {
                            res.add(notEmpty.msg());
                        } else {
                            if (o instanceof Collection) {
                                Collection collection = (Collection) o;
                                if (collection.size() == 0) {
                                    res.add(notEmpty.msg());
                                }
                            } else if (o instanceof Map) {
                                Map map = (Map) o;
                                if (map.size() == 0) {
                                    res.add(notEmpty.msg());
                                }
                            } else if (o instanceof Object[]) {
                                Object[] objects = (Object[]) o;
                                if (objects.length == 0) {
                                    res.add(notEmpty.msg());
                                }
                            } else {
                                res.add("ERROR: 数据类型错误" + o.getClass().getTypeName());
                            }
                        }

                    } else if (anno instanceof Length) {
                        Length length = (Length) anno;
                        Object o = field.get(obj);
                        int min = length.min() < 0 ? 0 : length.min();
                        if (o == null) {
                            if (min > 0) {
                                res.add(length.msg());
                            }
                        } else {
                            if (o instanceof String) {
                                String s = (String) o;
                                if (s.length() < min || s.length() > length.max()) {
                                    res.add(length.msg());
                                }
                            } else if (o instanceof Collection) {
                                Collection collection = (Collection) o;
                                if (collection.size() < min || collection.size() > length.max()) {
                                    res.add(length.msg());
                                }
                            } else if (o instanceof Map) {
                                Map map = (Map) o;
                                if (map.size() < min || map.size() > length.max()) {
                                    res.add(length.msg());
                                }
                            } else if (o instanceof Object[]) {
                                Object[] objects = (Object[]) o;
                                if (objects.length < min || objects.length > length.max()) {
                                    res.add(length.msg());
                                }
                            } else {
                                res.add("ERROR: 数据类型错误" + o.getClass().getTypeName());
                            }
                        }

                    } else if(anno instanceof AssertTrue){
                        AssertTrue assertTrue=(AssertTrue)anno;
                        Object o=field.get(obj);
                        if (o==null){
                            res.add("ERROR: 数据为空");
                        }else {
                            if (o instanceof Boolean){
                                Boolean flag=(Boolean)o;
                                if (!flag){
                                    res.add(assertTrue.msg());
                                }
                            }else {
                                res.add("ERROR: 数据类型错误"+o.getClass().getTypeName());
                            }
                        }
                    }else if(anno instanceof AssertFalse){
                        AssertFalse assertFalse=(AssertFalse)anno;
                        Object o=field.get(obj);
                        if (o==null){
                            res.add("ERROR: 数据为空");
                        }else {
                            if (o instanceof Boolean){
                                Boolean flag=(Boolean)o;
                                if (flag){
                                    res.add(assertFalse.msg());
                                }
                            }else {
                                res.add("ERROR: 数据类型错误"+o.getClass().getTypeName());
                            }
                        }
                    }else if (anno instanceof Pattern) {
                        Pattern pattern = (Pattern) anno;
                        Object o = field.get(obj);
                        if (o == null) {
                            res.add("ERROR: 数据为空！");
                        } else {
                            if (o instanceof CharSequence) {
                                String s = (String) o;
                                if (!s.matches(pattern.regexp())) {
                                    res.add(pattern.msg());
                                }
                            } else {
                                res.add("ERROR: 数据类型错误" + o.getClass().getTypeName());
                            }
                        }
                    }else if (anno instanceof Email) {
                        Email email = (Email) anno;
                        Object o = field.get(obj);
                        if (o == null) {
                            res.add("ERROR:邮箱为空！");
                        } else {
                            if (o instanceof String) {
                                String s = (String) o;
                                if (!s.matches(email.pattern())) {
                                    res.add(email.msg());
                                }
                            } else {
                                res.add("ERROR: 邮箱数据类型" + o.getClass().getTypeName());
                            }
                        }
                    } else if (anno instanceof Before) {
                        Before before = (Before) anno;
                        Object o = field.get(obj);
                        if (o == null) {
                            res.add("ERROR: 日期为空");
                        } else {
                            if (o instanceof Date) {
                                Date date = (Date) o;
                                if (date.getTime() > System.currentTimeMillis()) {
                                    res.add(before.msg());
                                }

                            } else if (o instanceof LocalDate) {
                                LocalDate date = (LocalDate) o;
                                if (date.isAfter(LocalDate.now())) {
                                    res.add(before.msg());
                                }
                            } else if (o instanceof LocalDateTime) {
                                LocalDateTime dateTime = (LocalDateTime) o;
                                if (dateTime.isAfter(LocalDateTime.now())) {
                                    res.add(before.msg());
                                }
                            } else {
                                res.add("ERROR: 日期类型" + o.getClass().getTypeName());
                            }
                        }

                    } else if (anno instanceof After) {
                        After before = (After) anno;
                        Object o = field.get(obj);
                        if (o == null) {
                            res.add("ERROR: 日期为空");
                        } else {
                            if (o instanceof Date) {
                                Date date = (Date) o;
                                if (date.getTime() < System.currentTimeMillis()) {
                                    res.add(before.msg());
                                }
                            } else if (o instanceof LocalDate) {
                                LocalDate date = (LocalDate) o;
                                if (date.isBefore(LocalDate.now())) {
                                    res.add(before.msg());
                                }
                            } else if (o instanceof LocalDateTime) {
                                LocalDateTime dateTime = (LocalDateTime) o;
                                if (dateTime.isBefore(LocalDateTime.now())) {
                                    res.add(before.msg());
                                }
                            } else {
                                res.add("ERROR: 日期类型" + o.getClass().getTypeName());
                            }
                        }
                    }
                }

            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return res;
    }
}
