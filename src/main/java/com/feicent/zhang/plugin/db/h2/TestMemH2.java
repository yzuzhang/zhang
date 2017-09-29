//                            _ooOoo_
//                           o8888888o
//                           88" . "88
//                           (| -_- |)
//                            O\ = /O
//                        ____/`---'\____
//                      .   ' \\| |// `.
//                       / \\||| : |||// \
//                     / _||||| -:- |||||- \
//                       | | \\\ - /// | |
//                     | \_| ''\---/'' | |
//                      \ .-\__ `-` ___/-. /
//                   ___`. .' /--.--\ `. . __
//                ."" '< `.___\_<|>_/___.' >'"".
//               | | : `- \`.;`\ _ /`;.`/ - ` : | |
//                 \ \ `-. \_ __\ /__ _/ .-` / /
//         ======`-.____`-.___\_____/___.-`____.-'======
//                            `=---='
//
//         .............................................
//                  佛祖镇楼                  BUG辟易
//          佛曰:
//                  写字楼里写字间，写字间里程序员；
//                  程序人员写程序，又拿程序换酒钱。
//                  酒醒只在网上坐，酒醉还来网下眠；
//                  酒醉酒醒日复日，网上网下年复年。
//                  但愿老死电脑间，不愿鞠躬老板前；
//                  奔驰宝马贵者趣，公交自行程序员。
//                  别人笑我忒疯癫，我笑自己命太贱；
//                  不见满街漂亮妹，哪个归得程序员？
package com.feicent.zhang.plugin.db.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

/**
* @ClassName: TestMemH2
* @Description:H2数据库的内存模式（数据只保存在内存中）
* @author: 孤傲苍狼
* @date: 2014-12-18 下午10:47:01
* 注意：如果使用H2数据库的内存模式，那么我们创建的数据库和表都只是保存在内存中，一旦服务器重启，那么内存中的数据库和表就不存在了。
*/ 
public class TestMemH2 {

        //数据库连接URL，通过使用TCP/IP的服务器模式（远程连接），当前连接的是内存里面的gacl数据库
        private static final String JDBC_URL = "jdbc:h2:tcp://localhost/mem:";
        //连接数据库时使用的用户名
        private static final String USER = "root";
        //连接数据库时使用的密码
        private static final String PASSWORD = "123456";
        //连接H2数据库时使用的驱动类，org.h2.Driver这个类是由H2数据库自己提供的，在H2数据库的jar包中可以找到
        private static final String DRIVER_CLASS="org.h2.Driver";
        
        public static void main(String[] args) throws Exception {
            // 加载H2数据库驱动
            Class.forName(DRIVER_CLASS);
            // 根据连接URL，用户名，密码获取数据库连接
            Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            Statement stmt = conn.createStatement();
            //如果存在USER_INFO表就先删除USER_INFO表
            stmt.execute("DROP TABLE IF EXISTS USER_INFO");
            //创建USER_INFO表
            stmt.execute("CREATE TABLE USER_INFO(id VARCHAR(36) PRIMARY KEY,name VARCHAR(100),sex VARCHAR(4))");
            //新增
            stmt.executeUpdate("INSERT INTO USER_INFO VALUES('" + UUID.randomUUID()+ "','大日如来','男')");
            stmt.executeUpdate("INSERT INTO USER_INFO VALUES('" + UUID.randomUUID()+ "','青龙','男')");
            stmt.executeUpdate("INSERT INTO USER_INFO VALUES('" + UUID.randomUUID()+ "','白虎','男')");
            stmt.executeUpdate("INSERT INTO USER_INFO VALUES('" + UUID.randomUUID()+ "','朱雀','女')");
            stmt.executeUpdate("INSERT INTO USER_INFO VALUES('" + UUID.randomUUID()+ "','玄武','男')");
            stmt.executeUpdate("INSERT INTO USER_INFO VALUES('" + UUID.randomUUID()+ "','苍狼','男')");
            //删除
            stmt.executeUpdate("DELETE FROM USER_INFO WHERE name='大日如来'");
            //修改
            stmt.executeUpdate("UPDATE USER_INFO SET name='孤傲苍狼' WHERE name='苍狼'");
            //查询
            ResultSet rs = stmt.executeQuery("SELECT * FROM USER_INFO");
            //遍历结果集
            while (rs.next()) {
                System.out.println(rs.getString("id") + "," + rs.getString("name")+ "," + rs.getString("sex"));
            }
            //释放资源
            stmt.close();
            //关闭连接
            conn.close();
        }
}