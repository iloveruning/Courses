package com.hfut.glxy.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * @author chenliangliang
 * @date: 2017/11/30
 */
public class PropertiesUtil {

    private Properties prop;
    private File propertiesFile;
    private String lineSeparator = java.security.AccessController.doPrivileged(
            new sun.security.action.GetPropertyAction("line.separator"));


    public PropertiesUtil(File file) throws Exception {

        this.prop = new Properties();
        try (InputStream in = new FileInputStream(file)) {
            prop.load(in);
            this.propertiesFile = file;
        } catch (Exception e) {
            throw e;
        }
    }


    public PropertiesUtil(String fileName) throws Exception {
        this(new File(fileName));
    }

    public PropertiesUtil(URL fileUrl) throws Exception {
        this(new File(fileUrl.toURI()));
    }

    /**
     * 保存属性到properties文件
     *
     * @param key     键
     * @param value   值
     * @param comment 注释
     * @return boolean  是否保存成功
     */
    public boolean saveProperty(@NotNull String key, @NotNull String value, String comment) {
        String oldValue = prop.getProperty(key);
        System.out.println(key + "---->" + oldValue);
        InputStream in = null;
        try {
            if (oldValue == null) {
                //值不存在，增加属性
                System.out.println("值不存在，增加属性");
                if (add(key, value, comment)) {
                    in = new FileInputStream(propertiesFile);
                    prop.load(in);
                    return true;
                }
                return false;
            } else if (StringUtils.equals(oldValue, value)) {
                //值没有发生改变，不修改配置文件
                System.out.println("值没有发生改变，不修改配置文件");
                return true;
            } else {
                //值发生改变，修改相应属性
                System.out.println("值发生改变，修改相应属性");
                if (update(key, value, comment)) {
                    in = new FileInputStream(propertiesFile);
                    prop.load(in);
                    return true;
                }
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

    }


    /**
     * 添加属性值
     *
     * @param key
     * @param value
     * @param comment
     * @return
     */
    public boolean addProperty(@NotNull String key, @NotNull String value, String comment) {
        String oldValue = prop.getProperty(key);
        InputStream in = null;
        try {
            if (oldValue == null && add(key, value, comment)) {
                in = new FileInputStream(propertiesFile);
                prop.load(in);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

    }


    private boolean add(@NotNull String key, @NotNull String value, String comment) {

        try (OutputStream out = new FileOutputStream(propertiesFile, true);
             OutputStreamWriter output = new OutputStreamWriter(out, "UTF-8");
             BufferedWriter writer = new BufferedWriter(output)) {
            //值不存在，增加属性
            writer.newLine();
            if (comment != null && StringUtils.isNotEmpty(comment)) {
                String cmt = "#" + comment;
                writer.write(cmt);
                writer.newLine();
            }
            writer.write(key + "=" + value);
            writer.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 修改属性值
     *
     * @param key
     * @param value
     * @param comment
     * @return
     */
    public boolean updateProperty(@NotNull String key, @NotNull String value, String comment) {
        String oldValue = prop.getProperty(key);
        return oldValue != null && (StringUtils.equals(oldValue, value) || update(key, value, comment));

    }

    private boolean update(@NotNull String key, @NotNull String value, String comment) {

        LinkedList<String> list = new LinkedList<>();
        try (InputStream in = new FileInputStream(propertiesFile);
             InputStreamReader input = new InputStreamReader(in, "UTF-8");
             BufferedReader reader = new BufferedReader(input)) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                if (StringUtils.isNotEmpty(line)) {
                    list.add(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        try (OutputStream out = new FileOutputStream(propertiesFile, false);
             OutputStreamWriter output = new OutputStreamWriter(out, "UTF-8");
             BufferedWriter writer = new BufferedWriter(output)) {
            String str;
            while ((str = list.pollFirst()) != null) {
                if (str.startsWith("#")) {
                    writer.write(str);
                } else {
                    String[] s = StringUtils.split(str, "=");
                    String k = s[0];
                    if (StringUtils.equals(k, key)) {
                        if (comment != null && StringUtils.isNotEmpty(comment)) {
                            String cmt = "#" + comment;
                            writer.write(cmt);
                            writer.newLine();
                        }
                        writer.write(k + "=" + value);
                    } else {
                        writer.write(str);
                    }
                }
                writer.newLine();
            }
            writer.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     * 返回key对应的value
     *
     * @param key
     * @return String
     */
    public String getValue(String key) {
        return prop.getProperty(key);
    }


    /**
     * 返回key对应的value,如果不存在则返回默认值
     *
     * @param key
     * @param defautValue
     * @return
     */
    public String getValue(String key, String defautValue) {
        return prop.getProperty(key, defautValue);
    }

    /**
     * 返回所有属性名到链表
     *
     * @return
     */
    public List<String> listPropertiesNames() {
        List<String> list = new ArrayList<>();
        Enumeration enumeration = prop.propertyNames();
        while (enumeration.hasMoreElements()) {
            list.add(enumeration.nextElement().toString());
        }
        return list;
    }


    /**
     * 返回所有属性名到集合中
     *
     * @return
     */
    public Set<String> getPropertiesNames() {
        return prop.stringPropertyNames();
    }


    /**
     * 保存多个属性  但不保存注释
     *
     * @param properties
     * @return
     */
    public boolean saveProperties(Map<String, String> properties) {
        //true表示追加打开
        try (OutputStream in = new FileOutputStream(propertiesFile, true)) {
            for (Map.Entry<String, String> property : properties.entrySet()) {
                prop.setProperty(property.getKey(), property.getValue());
                prop.store(in, null);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 保存多个属性  并保存注释
     *
     * @param list
     * @return
     */
    public boolean saveProperties(List<KeyValueComment> list) {
        //true表示追加打开
        try (OutputStream out = new FileOutputStream(propertiesFile, true)) {
            for (KeyValueComment it : list) {
                prop.setProperty(it.getKey(), it.getValue());
                prop.store(out, it.getComment());
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class KeyValueComment {

        private String key;
        private String value;
        private String comment;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }

}
