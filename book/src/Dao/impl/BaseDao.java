package Dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.jdbcUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class BaseDao {

    //使用DbUtil操作数据库
   private QueryRunner queryRunner =  new QueryRunner();

    /**
     * update():Insert/Update/Delete语句
     * @return 如果返回-1，则返回失败
     */
   public int update(String sql,Object...args){
       Connection connection = jdbcUtils.getConnection();
       try {
           return queryRunner.update(connection,sql,args);
       } catch (SQLException throwables) {
           throwables.printStackTrace();
       }
        return -1;
   }

    /**
     * 返回一个JavaBean的sql语句
     * @param type 返回对象类型
     * @param sql 执行的sql语句
     * @param args sql 对应的参数值
     * @param <T> 返回的类型的泛型
     * @return
     */
   public <T> T queryForOne(Class<T> type,String sql,Object...args){
       Connection connection = jdbcUtils.getConnection();
       try {
           return queryRunner.query(connection,sql,new BeanHandler<T>(type),args);
       } catch (Exception e) {
           e.printStackTrace();
       }finally {
                  jdbcUtils.close(connection);
              }
       return null;
   }

   public <T>List<T> queryForList(Class<T> type,String sql,Object...args){
       Connection connection = jdbcUtils.getConnection();
       try {
           return queryRunner.query(connection,sql,new BeanListHandler<T>(type),args);
       } catch (Exception e) {
           e.printStackTrace();
       }finally {
           jdbcUtils.close(connection);
       }
       return null;
   }

    /**
     * 执行返回一行一列的sql语句
     * @param sql 执行的sql语句
     * @param args
     * @return
     */
   public Object queryForSingleValue(String sql,Object...args){
       Connection connection = jdbcUtils.getConnection();
       try {
           return queryRunner.query(connection,sql,new ScalarHandler(),args);
       } catch (Exception e) {
           e.printStackTrace();
       }
       return null;
   }

}
