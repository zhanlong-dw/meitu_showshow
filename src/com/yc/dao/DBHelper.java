package com.yc.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 关于数据库操作的所有的功能
 *
 */
public class DBHelper {
	
	private static Env env=Env.getenv();

		/**
		 * 加载驱动的功能：静态块加载驱动  ->因为静态块在程序一开始加载到jvm就会运行，这时，驱动就加载好了
		 */
		static{
			try {
				Class.forName(env.getProperty("driver"));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * 获取连接的方法
		 */
		public Connection getCon(){
			Connection con=null;
			try {
				con=DriverManager.getConnection(env.getProperty("url"),env.getProperty("user"),env.getProperty("password"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return con;
		}

		/**
	 * 带事务的多条语句的执行
	 * @param sqls :没有?的语句
	 * @return
	 */
		public int doUpdate(List<String> sqls) {
			Connection con = getCon();
			PreparedStatement pstmt = null;
			int result = 0;
			try {
				con.setAutoCommit(   false);    //  设置自动提交模式为false
				if(  sqls!=null&&sqls.size()>0){
					for( int i=0;i<sqls.size();i++){
						pstmt = con.prepareStatement(    sqls.get(i));
						result = pstmt.executeUpdate();
					}
				}
				con.commit();           //提交操作的结果
			} catch (SQLException e) {
				e.printStackTrace();
				try {
					con.rollback();    //出异常了，则回gun数据, 恢复原来的数据
				} catch (SQLException e1) {
					e1.printStackTrace();
				}    
			} finally {
				try {
					con.setAutoCommit(  true );     //恢复现场
				} catch (SQLException e) {
					e.printStackTrace();
				}
				closeAll(null, pstmt, con);
			}
			return result;
		}
		
		private void doParams(   PreparedStatement pstmt,  List<String> params ) throws SQLException{
		if (params != null && params.size() > 0) {
			for (int i = 0; i < params.size(); i++) {
				//所有的参数都当成了字符串在处理,   如果参数是其它类型就不行  (    photo, text  )
				//TODO:  将来这里有可能要增加要处理的数据类型 
				pstmt.setString(i + 1, params.get(i) + "");
			}
		}
	}
		
		/**
		* 带事务的多条语句的执行
		* @param sqls :没有?的语句
		* @return
		*/
		public int doUpdate(List<String> sqls,  List<List<String>>  params) {
		Connection con = getCon();
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			con.setAutoCommit(   false);    //  设置自动提交模式为false
			if(  sqls!=null&&sqls.size()>0){
				for( int i=0;i<sqls.size();i++){
					pstmt = con.prepareStatement(    sqls.get(i));
					if(   params!=null&&params.size()>0&&  params.get(i)!=null ){
						List<String> ll=params.get(i);   //取出每条语句对应的参数列表
						
						doParams(   pstmt,ll);   //    pstmt是每一条语句循环生成一个,   ll是这个pstmt对应的参数的集合
					}
					result = pstmt.executeUpdate();
				}
			}
			con.commit();           //提交操作的结果
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();    //出异常了，则回gun数据, 恢复原来的数据
			} catch (SQLException e1) {
				e1.printStackTrace();
			}    
		} finally {
			closeAll(null, pstmt, con);
			try {
				con.setAutoCommit(  true );     //恢复现场
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}		
		/**
		 * 更新的方法（DML中insert，update，delete）
		 * sql要执行的语句，里面可能有？的占位符
		 * ？ 所代表的值
		 */
		public int doUpdate(String sql,List<String> params){
			Connection con=getCon();
			PreparedStatement ps=null;
			int result=0;
			try{
				ps=con.prepareStatement(sql);
				setParams(params, ps);
				result=ps.executeUpdate();
			}catch(SQLException e){
				e.printStackTrace();
				throw new RuntimeException(e);
			}finally{
				closeAll(ps,con);
			}
			return result;
		}

		
		private void setParams(List<String> params, PreparedStatement ps) throws SQLException {
			if(params!=null && params.size()>0){
				for(int i=1;i<=params.size();i++){
					ps.setString(i,params.get(i-1));
				}
			}
		}
		
		/**
		 * ？条件单值查询
		 * @param sql
		 * @param params
		 * @return
		 */
		public String comFind(String sql,List<String> params){
			Connection con = getCon();
			PreparedStatement ps = null;
			String result=null;
			ResultSet rs =null;
			try {
				ps = con.prepareStatement(sql);
				setParams(params, ps);
				rs=ps.executeQuery();
				if(rs.next()){         //聚合函数查询的结果是单行单列
					result=rs.getString(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e); //1.实现了早抛出（在dao层代码中不处理异常,将异常抛出,由界面友好的方式处理）
			}finally{						   //2.异常类型的选择
				closeAll(rs, ps, con);
			}
			return result;
		}
		

		public ResultSet doSelect(String sql,List<String> params){
			Connection con = getCon();
			PreparedStatement ps = null;
			
			ResultSet rs =null;
			try {
				ps = con.prepareStatement(sql);
				setParams(params, ps);
				rs=ps.executeQuery();
				} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e); //1.实现了早抛出（在dao层代码中不处理异常,将异常抛出,由界面友好的方式处理）
			}finally{						   //2.异常类型的选择
				//closeAll(rs, ps, con);
			}
			return rs;
		}
		
		/**
		 * 查询一：聚合函数的查询
		 */
		public double doSelectFunction(String sql,List<String>params){
			Connection con = getCon();
			PreparedStatement ps = null;
			double result=0;
			ResultSet rs =null;
			try {
				ps = con.prepareStatement(sql);
				setParams(params, ps);
				rs=ps.executeQuery();
				if(rs.next()){         //聚合函数查询的结果是单行单列
					result=rs.getDouble(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e); //1.实现了早抛出（在dao层代码中不处理异常,将异常抛出,由界面友好的方式处理）
			}finally{						   //2.异常类型的选择
				closeAll(rs, ps, con);
			}
			return result;
		}
		
		/**
		 * 查询二：查询出来的结果是一个List<Map<String,String>> map 中键就是记录的列名，map 的值就是记录的值
		 * Map对应数据表中的一条记录，List对应数据表中的全部记录
		 */
		public List<Map<String,String>> find(String sql,List<String> params){
			Connection con=null;
			PreparedStatement ps=null;
			ResultSet rs=null;
			
			List<Map<String,String>> list = new ArrayList<Map<String,String>>();
			try{
				//查询开始
				con=getCon();
				ps=con.prepareStatement(sql);
				setParams(params,ps);
				//得到结果集
				rs=ps.executeQuery();
				//得到所有的列名
				List<String> columnNameList =getColumnName(rs);
				//循环结果集，取出结果
				while(rs.next()){
					Map<String,String>map=new HashMap<String,String>();
					//rs.get类型（列的序号，列名）
					//循环columnNameList,从中取出每个列的名字，再根据列名，以rs.get类型（列名）
					//取出这一列的值
					for(String cn:columnNameList){
						String value =rs.getString(cn);
						map.put(cn,value);
					}
				list.add(map);  //将这个map存到list中，一个list就对应了查询的结果
				}
			}catch(SQLException e){
				e.printStackTrace();
				throw new RuntimeException(e);
			}finally{
				closeAll(rs,ps,con);
			}
			return list;			
		}
		
		/**
		 * 查询   【与上面的比较（泛型不同）】
		 * @param sql
		 * @param params
		 * @return
		 * @throws SQLException
		 */
		public List<Map<String, String>> doFind(String sql, List<String> params)
				throws SQLException {
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			Connection con = getCon();
			PreparedStatement pstmt = con.prepareStatement(sql);
			doParams(   pstmt,   params);
			ResultSet rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			String[] cs = new String[rsmd.getColumnCount()];
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				cs[i] = rsmd.getColumnLabel(i + 1);
				// System.out.println("列名:"+ cs[i] );
			}
			// //////////以上完成了取出查询结果的列名
			while (rs.next()) {
				Map<String, String> map = new HashMap<String, String>(); // 每循环
																			// rs.next()一次，就是一行数据，一行数据可以放在一个
																			// map中
				// map的键是列名， 值是这个列的值
				if (cs != null && cs.length > 0) {
					for (int i = 0; i < cs.length; i++) {
						map.put(cs[i].toLowerCase(), rs.getString(cs[i]));
					}
				}
				list.add(map);
			}
			closeAll(rs, pstmt, con);
			return list;
		}
		
		/**
		 * 从ResultSet中取出列名 包装成一个方法
		 */
		public List<String> getColumnName(ResultSet rs) throws SQLException{
			if(rs==null){
				return null;
			}
			List<String> columnList =new ArrayList<String>();
			ResultSetMetaData rsmd=rs.getMetaData();
			for(int i=0;i<rsmd.getColumnCount();i++){
				columnList.add(rsmd.getColumnLabel(i+1));
			}
			return columnList;
		}
		
		/**
		 * 创建(DDL):创建，删除，修改表，约束，序列...
		 */
		public void doDDL(String sql,List<String>params){
			Connection con = getCon();
			PreparedStatement ps = null;
			try {
				ps = con.prepareStatement(sql);
				setParams(params,ps);
				ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}finally{
				closeAll(ps, con);
			}
		}
		
		/**
		 * 关闭
		 */
		public void closeAll(PreparedStatement ps,Connection con){
			if(ps!=null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
			if(con!=null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		}
		
		public void closeAll(ResultSet rs,PreparedStatement ps, Connection con) {
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
			closeAll(ps, con);
		}
}
