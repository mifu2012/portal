package com.infosmart.util.report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.infosmart.util.StringUtils;

/**
 * This class wraps around a {@link PreparedStatement} and allows the programmer
 * to set parameters by name instead of by index. This eliminates any confusion
 * as to which parameter index represents what. This also means that rearranging
 * the SQL statement or adding a parameter doesn't involve renumbering your
 * indices. Code such as this:
 * 
 * Connection con=getConnection(); String query="select * from my_table where
 * name=? or address=?"; PreparedStatement p=con.prepareStatement(query);
 * p.setString(1, "bob"); p.setString(2, "123 terrace ct"); ResultSet
 * rs=p.executeQuery();
 * 
 * can be replaced with:
 * 
 * Connection con=getConnection(); String query="select * from my_table where
 * name=:name or address=:address"; NamedParameterStatement p=new
 * NamedParameterStatement(con, query); p.setString("name", "bob");
 * p.setString("address", "123 terrace ct"); ResultSet rs=p.executeQuery();
 * 
 * @author adam_crume
 */
public class NamedParameterStatement {
	private final static Logger logger = Logger
			.getLogger(NamedParameterStatement.class);

	/** The statement this object is wrapping. */
	private final PreparedStatement statement;

	/** Maps parameter names to arrays of ints which are the parameter indices. */
	private final Map indexMap;

	/**
	 * Creates a NamedParameterStatement. Wraps a call to c.
	 * {@link Connection#prepareStatement(java.lang.String) prepareStatement}.
	 * 
	 * @param connection
	 *            the database connection
	 * @param query
	 *            the parameterized query
	 * @throws SQLException
	 *             if the statement could not be created
	 */
	public NamedParameterStatement(Connection connection, String querySQL,
			Map<String, Object> paramValMap) throws SQLException {
		if (connection == null || connection.isClosed()) {
			throw new SQLException("数据库连接为空或未定义或已关闭");
		}
		if (querySQL == null || querySQL.length() == 0
				|| "null".equalsIgnoreCase(querySQL)) {
			throw new SQLException("查询语句为空或未定义");
		}
		this.logger.info("-->原查询语句：" + querySQL);
		Map<String, List<String>> paramNameMap = NamedParameterStatement
				.getSQLParam(querySQL);
		querySQL = NamedParameterStatement
				.getNewQuerySql(querySQL, paramValMap);
		this.logger.info("-->新查询语句：" + querySQL);
		indexMap = new HashMap();
		// 下面的处理，对参数值为null或空的数据不注入参数值
		String parsedQuery = parseSql(querySQL, indexMap);
		this.logger.info("-->实际查询语句：" + querySQL);
		statement = connection.prepareStatement(parsedQuery);
		// 注入参数值
		if (paramValMap != null && !paramValMap.isEmpty()) {
			// this.logger.info("--->所有的参数为:" + paramNameMap);
			// this.logger.info("---->已解析的参数:" + indexMap);
			Map.Entry<String, Object> entry = null;
			Iterator iter = paramValMap.entrySet().iterator();
			while (iter.hasNext()) {
				entry = (Map.Entry) iter.next();
				String key = entry.getKey().toString();// 参数名
				this.logger.info("-->NEW参数赋值：" + key + "=" + entry.getValue());
				if (entry.getValue() == null
						|| !StringUtils.notNullAndSpace(entry.getValue()
								.toString()))
					continue;
				if (entry.getValue() instanceof java.lang.String) {
					if (paramNameMap.containsKey("%" + key + "%")) {
						// this.logger.info("%参数名%:" + entry.getValue());
						this.setString("%" + key + "%", "%"
								+ entry.getValue().toString() + "%");
					} else if (paramNameMap.containsKey("%" + key)) {
						// this.logger.info("%参数名:" + entry.getValue());
						this.setString("%" + key, "%"
								+ entry.getValue().toString());
					} else if (paramNameMap.containsKey(key + "%")) {
						// this.logger.info("参数名%:" + entry.getValue());
						this.setString(key + "%", entry.getValue().toString()
								+ "%");
					} else {
						// this.logger.info("参数名:" + entry.getValue());
						this.setString(key, entry.getValue().toString());
					}
				} else {
					try {
						Integer.parseInt(entry.getValue().toString());
						this.setLong(key,
								Integer.parseInt(entry.getValue().toString()));
					} catch (Exception e) {
						this.setObject(key, entry.getValue());
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param querySQL
	 * @param paramValMap
	 * @return {userId=[t.user_id=${userId}, t.user_code=${userId}]}
	 */
	public static String getNewQuerySql(String querySQL,
			final Map<String, Object> paramValMap) {
		//
		querySQL = replaceNewQuerySql(querySQL);
		logger.info("-->新查询语句:" + querySQL);
		Map<String, List<String>> paramNameMap = getSQLParam(querySQL);
		if (paramNameMap != null && !paramNameMap.isEmpty()) {
			Map.Entry<String, Object> entry = null;
			Iterator iter = paramNameMap.entrySet().iterator();
			while (iter.hasNext()) {
				entry = (Map.Entry) iter.next();
				// 查询参数
				String paramName = entry.getKey().toString();
				// paramName=StringUtils.replace(paramName, "%", "");
				String paramVal = null;
				if (paramValMap.get(paramName) != null
						&& StringUtils.notNullAndSpace(paramValMap.get(
								paramName).toString())) {
					paramVal = paramValMap.get(paramName).toString();
				}
				// 可能是模糊查询
				if (paramVal == null || paramVal.length() == 0) {
					Object tmpParamVal = paramValMap.get(StringUtils
							.replaceAll(paramName, "%", ""));
					if (tmpParamVal != null)
						paramVal = tmpParamVal.toString();
				}
				// logger.info("参数将赋值：" + paramName + "=" + paramVal);
				if (paramVal == null || paramVal.length() == 0) {
					// logger.info("替换为默认值:1");
					List<String> queryWhere = paramNameMap.get(paramName);
					for (String str : queryWhere) {
						// logger.info("将替换为默认值:" + str);
						if (str.contains("%")) {
							querySQL = StringUtils.replaceAll(querySQL, str,
									"'%%'");
						} else {
							querySQL = StringUtils.replaceAll(querySQL, str,
									"1=1");// t.user_id=${userId}-->1=1
						}
						// logger.info("已替换为:" + querySQL);
					}
				} else {
					// logger.info("--------->替换为真实值:" + "${" + paramName +
					// "}-->"+ paramName);
					// //////////////////
					querySQL = StringUtils.replaceAll(querySQL, "${"
							+ paramName + "}", ":" + paramName);// t.user_id=${userId}-->t.user_id=:userId
				}
			}
		}
		return querySQL;
	}

	private static String replaceNewQuerySql(String querySQL) {
		// 去掉多余的空格
		querySQL = StringUtils.replaceAll(querySQL, "  ", " ");
		querySQL = StringUtils.replaceAll(querySQL, "  ", " ");
		querySQL = StringUtils.replaceAll(querySQL, "  =  ", "=");
		querySQL = StringUtils.replaceAll(querySQL, "  =", "=");
		querySQL = StringUtils.replaceAll(querySQL, "=  ", "=");
		// System.out.println(querySQL);
		querySQL = StringUtils.replaceAll(querySQL, " = ", "=");
		querySQL = StringUtils.replaceAll(querySQL, " =", "=");
		querySQL = StringUtils.replaceAll(querySQL, "= ", "=");
		//
		querySQL = StringUtils.replaceAll(querySQL, " >=", ">=");
		querySQL = StringUtils.replaceAll(querySQL, " <=", "<=");
		//
		querySQL = StringUtils.replaceAll(querySQL, " >", ">");
		querySQL = StringUtils.replaceAll(querySQL, " <", "<");
		//
		querySQL = StringUtils.replaceAll(querySQL, " !=", "!=");
		querySQL = StringUtils.replaceAll(querySQL, " <> ", "<>");
		querySQL = StringUtils.replaceAll(querySQL, " <>", "<>");
		querySQL = StringUtils.replaceAll(querySQL, "<> ", "<>");
		querySQL = StringUtils.replaceAll(querySQL, "$ ", "$");
		// 对日期可能有影响 如createDate>= ${createDate}(默认根据空格取此查询参数和字段，即createDate>=
		// ${createDate}),不去掉中间的空格会有问题
		querySQL = StringUtils.replaceAll(querySQL, " $", "$");

		// 对LIKE 影响了（不区分大小写）
		querySQL = StringUtils.replaceAll(querySQL, "like${", " like ${");
		// logger.info("--->对LIKE处理后:"+querySQL);
		querySQL = StringUtils.replaceAll(querySQL, "$ ", "$");
		querySQL = StringUtils.replaceAll(querySQL, "$ {", " ${");

		querySQL = StringUtils.replaceAll(querySQL, "\n", " ");
		querySQL = StringUtils.replaceAll(querySQL, "\t", " ");
		querySQL = StringUtils.replaceAll(querySQL, "{ ", "{");
		querySQL = StringUtils.replaceAll(querySQL, " }", "} ");
		// logger.info("之前：去空格后查询语句："+querySQL);
		querySQL = StringUtils.replaceAll(querySQL, " %}", "%}");
		querySQL = StringUtils.replaceAll(querySQL, " % }", "%}");

		querySQL = StringUtils.replaceAll(querySQL, "{% ", "{%");
		querySQL = StringUtils.replaceAll(querySQL, "{ % ", "{%");

		querySQL = StringUtils.replaceAll(querySQL, "% }", "%}");
		querySQL = StringUtils.replaceAll(querySQL, " % }", "%}");
		logger.info("去空格后查询语句：" + querySQL);
		return querySQL;
	}

	public static Map<String, List<String>> getSQLParam(String querySql) {
		Map<String, List<String>> paramMap = new HashMap<String, List<String>>();
		if (!StringUtils.notNullAndSpace(querySql)) {
			return paramMap;
		}
		//
		querySql = replaceNewQuerySql(querySql);
		char c = 0;
		boolean inSingleQuote = false;
		boolean inDoubleQuote = false;
		List<String> paramList = new ArrayList<String>();
		for (int i = 0; i < querySql.length(); i++) {
			c = querySql.charAt(i);
			if (inSingleQuote) {
				if (c == '\'') {
					inSingleQuote = false;
				}
			} else if (inDoubleQuote) {
				if (c == '"') {
					inDoubleQuote = false;
				}
			} else {
				// System.out.println(c);
				if (c == '\'') {
					inSingleQuote = true;
				} else if (c == '"') {
					inDoubleQuote = true;
				} else if (c == '$') {
					String subQuerySql = querySql.substring(i);
					// 参数<tableField,paramValue>
					// System.out.println(querySql.substring(0, i));
					String paramName = subQuerySql.substring(2,
							subQuerySql.indexOf("}"));
					// System.out.println(paramName);
					// logger.info("参数名:" + paramName);
					if (!paramList.contains(paramName)) {
						paramList.add(paramName);
					}
				}
			}
		}
		// 查询SQL语句参数关联的字段
		for (String paramName : paramList) {
			/*
			 * Pattern p = Pattern.compile(paramName); int num = 0; Matcher m =
			 * p.matcher(querySql); while (m.find()) { num++; }
			 */
			String querySqlClone = querySql;
			while (querySqlClone.indexOf("${" + paramName + "}") != -1) {
				String subString = querySqlClone.substring(0,
						querySqlClone.indexOf("${" + paramName + "}"));
				// System.out.println(subString.lastIndexOf(" "));
				// logger.info("------------->subString:" + subString);
				if (subString.lastIndexOf(" ") != -1) {
					String subCondition = subString.substring(
							subString.lastIndexOf(" ")).trim()
							+ "${" + paramName + "}";
					// logger.info("--------------->subCondition:" +
					// subCondition);
					if (paramMap.containsKey(paramName)) {
						paramMap.get(paramName).add(subCondition);
					} else {
						List list = new ArrayList();
						list.add(subCondition);
						paramMap.put(paramName, list);
					}
				}
				// System.out.println("ok:" + subString);
				querySqlClone = querySqlClone.substring(
						querySqlClone.indexOf("${" + paramName + "}")
								+ ("${" + paramName + "}").length(),
						querySqlClone.length());
				if (querySqlClone == null || querySqlClone.length() == 0)
					break;
				// System.out.println("------->" + querySqlClone);
			}
		}
		return paramMap;
	}

	/**
	 * Parses a query with named parameters. The parameter-index mappings are
	 * put into the map, and the parsed query is returned. DO NOT CALL FROM
	 * CLIENT CODE. This method is non-private so JUnit code can test it.
	 * 
	 * @param query
	 *            query to parse
	 * @param paramMap
	 *            map to hold parameter-index mappings
	 * @return the parsed query
	 */
	private final String parseSql(String query, final Map paramMap) {
		// I was originally using regular expressions, but they didn't work well
		// parameter-like strings inside quotes.
		int length = query.length();
		StringBuffer parsedQuery = new StringBuffer(length);
		boolean inSingleQuote = false;
		boolean inDoubleQuote = false;
		int index = 1;

		for (int i = 0; i < length; i++) {
			char c = query.charAt(i);
			if (inSingleQuote) {
				if (c == '\'') {
					inSingleQuote = false;
				}
			} else if (inDoubleQuote) {
				if (c == '"') {
					inDoubleQuote = false;
				}
			} else {
				if (c == '\'') {
					inSingleQuote = true;
				} else if (c == '"') {
					inDoubleQuote = true;
				} else if (c == ':' && i + 1 < length) {
					// &&Character.isJavaIdentifierStart(query.charAt(i + 1))
					int j = i + 2;
					while (j < length
							&& (query.charAt(j) == '%' || Character
									.isJavaIdentifierPart(query.charAt(j)))) {
						j++;
					}
					String name = query.substring(i + 1, j);
					// this.logger.info("----------->Name: " + name);
					c = '?'; // replace the parameter with a question mark
					i += name.length(); // skip past the end if the parameter

					List indexList = (List) paramMap.get(name);
					if (indexList == null) {
						indexList = new LinkedList();
						paramMap.put(name, indexList);
					}
					indexList.add(new Integer(index));

					index++;
				}
			}
			parsedQuery.append(c);
		}

		// replace the lists of Integer objects with arrays of ints
		for (Iterator itr = paramMap.entrySet().iterator(); itr.hasNext();) {
			Map.Entry entry = (Map.Entry) itr.next();
			List list = (List) entry.getValue();
			int[] indexes = new int[list.size()];
			int i = 0;
			for (Iterator itr2 = list.iterator(); itr2.hasNext();) {
				Integer x = (Integer) itr2.next();
				indexes[i++] = x.intValue();
			}
			entry.setValue(indexes);
		}
		// System.out.println(paramMap.toString());
		this.logger.info("----->查询预处理SQL语句为：" + parsedQuery.toString());
		return parsedQuery.toString();
	}

	/**
	 * Returns the indexes for a parameter.
	 * 
	 * @param name
	 *            parameter name
	 * @return parameter indexes
	 * @throws IllegalArgumentException
	 *             if the parameter does not exist
	 */
	private int[] getIndexes(String name) {
		int[] indexes = (int[]) indexMap.get(name);
		if (indexes == null) {
			throw new IllegalArgumentException("Parameter not found: " + name);
		}
		return indexes;
	}

	/**
	 * Sets a parameter.
	 * 
	 * @param name
	 *            parameter name
	 * @param value
	 *            parameter value
	 * @throws SQLException
	 *             if an error occurred
	 * @throws IllegalArgumentException
	 *             if the parameter does not exist
	 * @see PreparedStatement#setObject(int, java.lang.Object)
	 */
	private void setObject(String name, Object value) throws SQLException {
		int[] indexes = getIndexes(name);
		if (indexes == null || indexes.length == 0)
			return;
		for (int i = 0; i < indexes.length; i++) {
			statement.setObject(indexes[i], value);
		}
	}

	/**
	 * Sets a parameter.
	 * 
	 * @param name
	 *            parameter name
	 * @param value
	 *            parameter value
	 * @throws SQLException
	 *             if an error occurred
	 * @throws IllegalArgumentException
	 *             if the parameter does not exist
	 * @see PreparedStatement#setString(int, java.lang.String)
	 */
	private void setString(String name, String value) throws SQLException {
		int[] indexes = getIndexes(name);
		if (indexes == null || indexes.length == 0)
			return;
		for (int i = 0; i < indexes.length; i++) {
			statement.setString(indexes[i], value);
		}
	}

	/**
	 * Sets a parameter.
	 * 
	 * @param name
	 *            parameter name
	 * @param value
	 *            parameter value
	 * @throws SQLException
	 *             if an error occurred
	 * @throws IllegalArgumentException
	 *             if the parameter does not exist
	 * @see PreparedStatement#setInt(int, int)
	 */
	private void setInt(String name, int value) throws SQLException {
		int[] indexes = getIndexes(name);
		if (indexes == null || indexes.length == 0)
			return;
		for (int i = 0; i < indexes.length; i++) {
			statement.setInt(indexes[i], value);
		}
	}

	/**
	 * Sets a parameter.
	 * 
	 * @param name
	 *            parameter name
	 * @param value
	 *            parameter value
	 * @throws SQLException
	 *             if an error occurred
	 * @throws IllegalArgumentException
	 *             if the parameter does not exist
	 * @see PreparedStatement#setInt(int, int)
	 */
	private void setLong(String name, long value) throws SQLException {
		int[] indexes = getIndexes(name);
		if (indexes == null || indexes.length == 0)
			return;
		for (int i = 0; i < indexes.length; i++) {
			statement.setLong(indexes[i], value);
		}
	}

	/**
	 * Sets a parameter.
	 * 
	 * @param name
	 *            parameter name
	 * @param value
	 *            parameter value
	 * @throws SQLException
	 *             if an error occurred
	 * @throws IllegalArgumentException
	 *             if the parameter does not exist
	 * @see PreparedStatement#setTimestamp(int, java.sql.Timestamp)
	 */
	private void setTimestamp(String name, Timestamp value) throws SQLException {
		int[] indexes = getIndexes(name);
		for (int i = 0; i < indexes.length; i++) {
			statement.setTimestamp(indexes[i], value);
		}
	}

	/**
	 * Returns the underlying statement.
	 * 
	 * @return the statement
	 */
	public PreparedStatement getStatement() {
		return statement;
	}

	/**
	 * Executes the statement.
	 * 
	 * @return true if the first result is a {@link ResultSet}
	 * @throws SQLException
	 *             if an error occurred
	 * @see PreparedStatement#execute()
	 */
	public boolean execute() throws SQLException {
		return statement.execute();
	}

	/**
	 * Executes the statement, which must be a query.
	 * 
	 * @return the query results
	 * @throws SQLException
	 *             if an error occurred
	 * @see PreparedStatement#executeQuery()
	 */
	public ResultSet executeQuery() throws SQLException {
		// System.out.println(this.statement.getMetaData().getColumnCount());
		return statement.executeQuery();
	}

	/**
	 * Executes the statement, which must be an SQL INSERT, UPDATE or DELETE
	 * statement; or an SQL statement that returns nothing, such as a DDL
	 * statement.
	 * 
	 * @return number of rows affected
	 * @throws SQLException
	 *             if an error occurred
	 * @see PreparedStatement#executeUpdate()
	 */
	public int executeUpdate() throws SQLException {
		return statement.executeUpdate();
	}

	/**
	 * Closes the statement.
	 * 
	 * @throws SQLException
	 *             if an error occurred
	 * @see Statement#close()
	 */
	public void close() throws SQLException {
		statement.close();
	}

	/**
	 * Adds the current set of parameters as a batch entry.
	 * 
	 * @throws SQLException
	 *             if something went wrong
	 */
	public void addBatch() throws SQLException {
		statement.addBatch();
	}

	/**
	 * Executes all of the batched statements.
	 * 
	 * See {@link Statement#executeBatch()} for details.
	 * 
	 * @return update counts for each statement
	 * @throws SQLException
	 *             if something went wrong
	 */
	public int[] executeBatch() throws SQLException {
		return statement.executeBatch();
	}
}
