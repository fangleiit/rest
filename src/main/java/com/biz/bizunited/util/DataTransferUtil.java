package com.biz.bizunited.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.minidao.hibernate.IdEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biz.bizunited.vo.DataTransferVO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 将json字符串数组对象持久化到数据库
 * @author xiaogang
 * @param <T>
 *
 */
public class DataTransferUtil {
	
	private Connection connection;
	
	Logger logger = LoggerFactory.getLogger(DataTransferUtil.class);

//	private String[] tableNames = {"Qrcode_act_info_copy","qrcode_prize_config_copy","Qrcode_rate_config","Qrcode_rate_cust","Qrcode_rate_product"};
	private String[] tableNames = {
			"Qrcode_act_info",
			"Qrcode_prize_config",
			"Qrcode_rate_config",
			"Qrcode_rate_cust",
			"Qrcode_rate_product",
			"qrcode_act_series",
			"qrcode_terminal_info_config",
			"qrcode_rule_stipula",
			"qrcode_picture",
			"qrcode_putin_prize",
			"qrcode_page_background",
			"putin_area",
			"qrcode_terminal_info"
			};
	
	private String json = "[\n" + "    {\n" + "        \"qrcode_act_info_copy\": {\n" + "            \"id\": \"id\",\n"
			+ "            \"act_name\": \"act_name\",\n" + "            \"act_note\": \"act_note\",\n"
			+ "            \"start_time\": \"start_time\",\n" + "            \"end_time\": \"end_time\",\n"
			+ "            \"out_s_time\": \"out_s_time\",\n" + "            \"out_e_time\": \"out_e_time\"\n"
			+ "        }\n" + "    }\n" + "]";
	
	{
		for (int i = 0; i < tableNames.length; i++) {
			String string = tableNames[i];
			String lowerCase = string.toLowerCase();
			tableNames[i] = lowerCase;
		}
	}
	
	/**
	 * 存储接收到的数据
	 * @throws SQLException 
	 */
	public int storageData() throws SQLException{
		int i = 0;
		try {
			connection.setAutoCommit(false);
			List<String> sqlList = this.buildStorageSqlList();
			i = executeSql(sqlList);
			logger.info("一共同步{}条数据",i);
			logger.info("提交数据存储事务");
			connection.commit();
		} catch (Exception e) {
			connection.rollback();
			logger.info("出现异常，回滚事务");
			throw new RuntimeException(e);
		}finally{
			if (connection != null) {
				connection.close();
			}
		}
		return i;
	}
	
	/**
	 * 执行sql语句，存储接收到的数据
	 * @param sqlList
	 * @throws SQLException 
	 */
	public int executeSql(List<String> sqlList) throws SQLException{
		if (sqlList == null || sqlList.size() == 0) {
			logger.error("sqlList is empty!!!");
		}
		int i = 0;
		for (String string : sqlList) {
			i += executeSql(string);
		}
		return i;
	}
	
	/**
	 * 执行sql语句，存储接收到的数据
	 * @param sqlList
	 * @throws SQLException 
	 */
	public int executeSql(String sql) throws SQLException{
		if (sql == null || "".equals(sql)) {
			logger.error("sql is empty!!!");
		}
		PreparedStatement ps = connection.prepareStatement(sql);
		logger.info("executeSql : {}",sql);
		
		int i = ps.executeUpdate();
		
		logger.info("受到影响的行数:{}",i);
		return i;
	}
	/**
	 * 执行sql语句，查询。
	 * @param sqlList
	 * @throws SQLException 
	 */
	public int executeSqlQuery(String sql) throws SQLException{
		if (sql == null || "".equals(sql)) {
			logger.error("sql is empty!!!");
		}
		PreparedStatement ps = connection.prepareStatement(sql);
		logger.info("executeSql : {}",sql);
		
//		int i = ps.executeQuery().getRow();
		
		ResultSet rs = ps.executeQuery();
		int i = 0; 
		while(rs.next()){
			i++;
		}
		logger.info("已有数据:{}条",i);
		return i;
	}
	public void dateTransfer2Eisp(){
		
	}
	
	/**
	 * 通过java对象，构建对应的sql语句
	 * @return
	 */
	public  List<String> buildStorageSqlList(){
		
		List<String> sqlList = new ArrayList<>();
		
		try {
			List<DataTransferVO> dataList = transform(json);
			
			boolean validData = validData(dataList);
			if (validData)
			for (DataTransferVO dataTransferVO : dataList) {
				//这层循环循环的是表，一个表也许有多条SQL
				
				
				//sql temp : insert into table_name (id,name,age) values (?,?,?)
				String tableName = dataTransferVO.getTableName();
				List<Map<String,Object>> rowData = dataTransferVO.getColumnNameAndValue();

				//分离出columnNames和values，分别收集
				//有多少行，这里就循环多少次
				for(int i = 0; i < rowData.size(); i++){
					//查询数据有没有此id下数据
					String sql = "select * from %s where id='%s'";
					String id = StringUtil.isNotEmpty(rowData.get(i).get("ID"))?rowData.get(i).get("ID").toString():"-1";
					sql = String.format(sql, tableName,id);
					int count = executeSqlQuery(sql);
					Map<String, Object> map = rowData.get(i);
					Set<Entry<String,Object>> entrySet = map.entrySet();
					if (count == 0) {
						//新增操作
						StringBuilder sqlBuilder =  new StringBuilder("insert into ");
						sqlBuilder.append(tableName);
						StringBuilder nameBuilder = new StringBuilder(" ( ");
						StringBuilder valueBuilder = new StringBuilder(" values ( ");
						int j = 0;
						for (Entry<String, Object> entry : entrySet) {
							//如果value为null，或者空白，不插入该列
							Object value = entry.getValue();
							if (value == null || "".equals(value.toString()) || "null".equals(value.toString().toLowerCase())) {
								if(j == (map.size() - 1)){
									nameBuilder = new StringBuilder(nameBuilder.substring(0, nameBuilder.length() - 2));
									nameBuilder.append(") ");
									valueBuilder = new StringBuilder(valueBuilder.substring(0, valueBuilder.length() - 2));
									valueBuilder.append(") ");
								}
								j++;
								continue;
							}
							nameBuilder.append(entry.getKey());
							valueBuilder.append("\"").append(entry.getValue()).append("\"");
							if (j < (map.size() - 1)) {
								nameBuilder.append(", ");
								valueBuilder.append(", ");
							}
							if(j == (map.size() - 1)){
								nameBuilder.append(") ");
								valueBuilder.append(") ");
							}
							j++;
						}
						sqlBuilder.append(nameBuilder).append(valueBuilder);
						sqlList.add(sqlBuilder.toString());
					}else{
						//更新操作
						StringBuilder sqlBuilder =  new StringBuilder("update ");
						sqlBuilder.append(tableName).append(" set ");
						//修改的字段
						int j = 0;
						for (Entry<String, Object> entry : entrySet) {
							//如果value为null，或者空白，直接插入
							String value = StringUtil.isNotEmpty(entry.getValue())?entry.getValue().toString():null;
							sqlBuilder.append(entry.getKey()).append("=");
							if (StringUtil.isEmpty(value)) {
								sqlBuilder.append("null");
								if (j < (map.size() - 1)) {
									sqlBuilder.append(",");
								}
								j++;
								continue;
							}
							sqlBuilder.append("'").append(value).append("'");
							if (j < (map.size() - 1)) {
								sqlBuilder.append(",");
							}
							j++;
						}
						sqlBuilder.append(" where id=").append("'").append(id).append("'");
						sqlList.add(sqlBuilder.toString());
					}
					
				}
				
				
			}
			for(String sql : sqlList){
				logger.info("build the sql : "+sql);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throwException(e.toString());
		}
		return sqlList;
	}
	
	/**
	 * 将json对象数组转换为java对象，方便操作数据
	 * @param jsonData
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public  List<DataTransferVO> transform(String jsonData) throws JsonParseException, JsonMappingException, IOException{
		//需要传输的数据集合
		List<DataTransferVO> dataList = new ArrayList<>();
		
		if (jsonData == null || "".equals(jsonData.trim())) {
			throw new RuntimeException("json data is empty!");
		}
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, Object>> list = mapper.readValue(json, List.class);
		
		for (Map<String, Object> map : list) {
			Set<Entry<String, Object>> entrySet = map.entrySet();
			DataTransferVO vo = new DataTransferVO();
			//获取表名称
			String tableName = map.get("tableName").toString();
			vo.setTableName(tableName);
			//列名称和对应的值
			List<Map<String, Object>> nv = (List<Map<String, Object>>) map.get("columnNameAndValue");
			vo.setColumnNameAndValue(nv);
			dataList.add(vo);
		}
		
		return dataList;
	}
	
	/**
	 * 校验入参是否合法，是否可以传输该数据
	 * @param jsonData
	 * @return
	 */
	public  boolean validData(List<DataTransferVO> dataList){
		for (DataTransferVO dataTransferVO : dataList) {
			//1.表名称是否为空
			String tableName = dataTransferVO.getTableName();
			if (tableName == null || "".equals(tableName.trim())) {
				throw new RuntimeException("table name is empty!!!");
			}
			//2.是否配置了指定表可以同步数据
			List<String> tableNameList = Arrays.asList(tableNames);
			if (!tableNameList.contains(tableName.toLowerCase())) {
				throw new RuntimeException("that table['"+tableName+"']is not allow to transfer data ,please contact administrater!!!");
			}
			//3.属性名称是否和列名称对应
			List<String> names = findTableColumnNames(tableName);
			List<Map<String, Object>> columnNameAndValue = dataTransferVO.getColumnNameAndValue();
			if (columnNameAndValue.size() < 1) {
				//throwException("row data size is zero !!!");
			}
			for (Map<String, Object> map : columnNameAndValue) {
				Set<String> keySet = map.keySet();
				for (String string : keySet) {
					if (!names.contains(string)) {
						throwException("that column \""+string+"\" is not in this table \""+tableName+"\"");
					};
				}
			}
			
		}
		return true;
	}
	
	public  void throwException(String msg){
		throw new RuntimeException(msg);
	}
	
	/**
	 * 通过表名称，获取该表所有的列名称
	 * @param connection
	 * @param tableName
	 * @return
	 */
	public  List<String> findTableColumnNames(String tableName){
		if (connection == null) {
			throwException("connection is null");
		}
		if (tableName == null || "".equals(tableName)) {
			throwException("table name '"+tableName+"' is null");
		}
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		//列名称集合
		List<String> nameList = new ArrayList<>();
		try {
			String sql = "select * from "+tableName+" where 1=1 limit 0,1";
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			
			for(int i = 1; i <= columnCount; i++){
				String columnName = metaData.getColumnName(i);
				nameList.add(columnName.toUpperCase());
			}
		} catch (Exception e) {
			throwException("transfer data exception :"+e.toString());
		}
		return nameList;
	}

	/**
	 * 将对象转换成传输对象,通过对象的标签获取表名和列名,封装数据
	 * @param object
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public DataTransferVO object2DataTransVo(List<?> oneTableData) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		if (oneTableData == null || oneTableData.size() < 1) {
			logger.error("target is "+oneTableData);
			return null;
		}
		DataTransferVO vo = new DataTransferVO();
		//列数据
		List<Map<String, Object>> columnNameAndValue = new ArrayList<>();
		vo.setColumnNameAndValue(columnNameAndValue);
		for (Object object : oneTableData) {
			
			Class<? extends Object> clz = null;
			if(clz == null){
				clz = object.getClass();
			}
			if(vo.getTableName() == null){
				//表名称
				String tableName = clz.getAnnotation(Table.class).name();
				vo.setTableName(tableName);
			}
			//?????
			Map<String, Object> nameAndValue = findCLassColumnNameAndValue(clz, object);
			columnNameAndValue.add(nameAndValue);
		}
		vo.setColumnNameAndValue(columnNameAndValue);
		return vo;
	} 
	
	/**
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * 
	 */
	public Map<String, Object> findCLassColumnNameAndValue(Class<? extends Object> clz,Object object) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		if(clz == null){
			clz = object.getClass();
		}
		Map<String, Object> map = new HashMap<>();
		Method[] methods = clz.getDeclaredMethods();
		
		
		for (Method method : methods) {
			method.setAccessible(true);
			String methodName = method.getName();
//			logger.info("methodName :{}",methodName);
			if (methodName == null || !methodName.startsWith("get")) {
				continue;
			}
			//获取列名称
			Column column = method.getAnnotation(Column.class);
			if (column == null) {
				continue;
			}
			String columnName = column.name();
			//?????
			Object value = method.invoke(object);
			map.put(columnName, value == null ? value : value.toString());
		}
		//?????
		if(object instanceof IdEntity){
			IdEntity entity = (IdEntity) object;
			map.put("id", entity.getId());
		}
		return map;
	}
	
	public DataTransferUtil(Connection connection, String json) {
		super();
		this.connection = connection;
		this.json = json;
	}

	public DataTransferUtil() {
		super();
	}
	
	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
}
