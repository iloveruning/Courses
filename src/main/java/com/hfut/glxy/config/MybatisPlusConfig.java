package com.hfut.glxy.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenliangliang
 * @date: 2017/11/27
 */
@Configuration
@MapperScan(basePackages="com.hfut.glxy.mapper*")
/*@ComponentScan("com.hfut.glxy.dao*")*/
public class MybatisPlusConfig {


    /**
     * mybatis-plus SQL执行效率插件【生产环境可以关闭】
     */
   /* @Bean
    public PerformanceInterceptor performanceInterceptor(){
        return new PerformanceInterceptor();
    }
*/

    /**
     * mybatis-plus分页插件<br>
     * 文档：http://mp.baomidou.com<br>
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setDialectType("mysql");
        paginationInterceptor.setDialectClazz("com.baomidou.mybatisplus.plugins.pagination.dialects.MySqlDialect");

        // 开启 PageHelper 的支持
        //paginationInterceptor.setLocalPage(true);
         //【测试多租户】 SQL 解析处理拦截器<br>
         //这里固定写成住户 1 实际情况你可以从cookie读取，因此数据看不到 【 麻花藤 】 这条记录（ 注意观察 SQL ）<br>
       /* List<ISqlParser> sqlParserList = new ArrayList<>();
        TenantSqlParser tenantSqlParser = new TenantSqlParser();
        tenantSqlParser.setTenantHandler(new TenantHandler() {
            @Override
            public Expression getTenantId() {
                return new LongValue(1L);
            }

            @Override
            public String getTenantIdColumn() {
                return "tenant_id";
            }

            @Override
            public boolean doTableFilter(String tableName) {
                // 这里可以判断是否过滤表

                if ("user".equals(tableName)) {
                    return true;
                }
                return false;
            }
        });


        sqlParserList.add(tenantSqlParser);
        paginationInterceptor.setSqlParserList(sqlParserList);
        paginationInterceptor.setSqlParserFilter(metaObject->{
            MappedStatement ms = PluginUtils.getMappedStatement(metaObject);
            // 过滤自定义查询此时无租户信息约束【 麻花藤 】出现
            if ("com.baomidou.springboot.mapper.UserMapper.selectListBySQL".equals(ms.getId())) {
                return true;
            }
            return false;
        });*/
        return paginationInterceptor;
    }

}
