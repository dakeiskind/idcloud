package com.h3c.idcloud.infrastructure.common.util;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 * 简易DB工具类
 *
 * @author Chaohong.Mao
 */
public class DBUtil {
    /** 数据库连接 */
    private static String url ;
    /** 数据库dirver */
    private static String driver;
    /** 数据库用户名 */
    private static String user;
    /** 数据库密码 */
    private static String password;
    /** 数据库查询类 */
    private static QueryRunner runner = new QueryRunner();

    static {
        Properties p = new Properties();
        InputStreamReader reader = null;
        InputStream is = ClassLoaderUtil.getResourceAsStream("db_config.properties", DBUtil.class);
        if (is != null) {
            try {
                reader = new InputStreamReader(is, "UTF-8");
                p.load(reader);
                driver = p.getProperty("db.driver");
                user = p.getProperty("db.username");
                password = p.getProperty("db.password");
                url = p.getProperty("db.url");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 查询表.
     *
     * @param sql   查询SQL
     * @param param SQL参数
     *
     * @return 结果
     */
    public static Map queryMap(String sql, Object... param) {
        DbUtils.loadDriver(driver);
        Connection conn;
        Map result = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            result = runner.query(conn, sql, new MapHandler(), param);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 插入数据.
     *
     * @param sql   SQL
     * @param param 参数
     *
     * @return 影响条数
     */
    public static int insert(String sql, Object... param) {
        DbUtils.loadDriver(driver);
        Connection conn;
        ResultSetHandler<String> resultHandler = new BeanHandler<>(String.class);
        String genKey = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            genKey = runner.insert(conn, sql, resultHandler, param);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return genKey == null ? 0 : 1;
    }

    /**
     * 更新、删除DB数据.
     *
     * @param sql   SQL
     * @param param 参数
     *
     * @return 影响条数
     */
    public static int update(String sql, Object... param) {
        DbUtils.loadDriver(driver);
        Connection conn;
        int result = 0;
        try {
            conn = DriverManager.getConnection(url, user, password);
            result = runner.update(conn, sql, param);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

//    public static void main(String args[]) {
//        String sql = "SELECT * FROM sys_m_user where ACCOUNT = ?";
//        System.out.println(queryMap(sql, "swq"));
//    }

}
