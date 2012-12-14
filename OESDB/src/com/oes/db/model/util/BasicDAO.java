package com.oes.db.model.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.PropertyExpression;
import org.hibernate.criterion.Restrictions;

import com.ibm.icu.util.Calendar;
import com.mbi.oes.db.utils.DatesUtils;
import com.mbi.oes.db.utils.OES;
import com.mbi.oes.db.utils.Validator;
import com.oes.model.my_sql.entities.Itemhistory;
import com.oes.model.my_sql.entities.Masteritem;
import com.oes.model.my_sql.entities.Orderdetails;


public class BasicDAO {

	private static Logger logger_ = Logger.getLogger(BasicDAO.class);

	@SuppressWarnings("rawtypes")
	public List findByProperty(String columnValue, String columnName,
			String tableName) {
		List list = null;

		// Read Data only from SQL Server and update on both SQL Server and My
		// SQL
		// but for the time being reading it from My SQL
		list = findByPropertyInternal(columnValue, columnName, tableName,
				HibernateUtil.MY_SQL);

		/*
		 * if (HibernateUtil.USE_MYSQL) {
		 * findByPropertyInternal(columnValue,columnName,tableName,
		 * HibernateUtil.MY_SQL); }
		 */

		return list;
	}
	@SuppressWarnings("rawtypes")
	public List findByPropertyLike(String columnValue,
			String columnName, String tableName) {
		List list = null;
		list = findByPropertyLikeInternal(columnValue, columnName, tableName,
				HibernateUtil.MY_SQL);
		return list;
	}
	@SuppressWarnings("rawtypes")
	public List findByProperty(String columnValue,
			String columnName, Class tableNameClass, String orderByColumnName) {   // Made the code generic to accept orderByColumnName
		List list = null;
		list = findByPropertyInternal(columnValue, columnName, tableNameClass,
				HibernateUtil.MY_SQL, 0, OES.ALL_ROWS, new String[]{orderByColumnName});
		return list;
	}
	@SuppressWarnings("rawtypes")
	public List findByProperty(String columnValue,
			String columnName, Class tableNameClass, String outputColumns[]) {   // Made the code generic to accept orderByColumnName
		List list = null;
		list = findByPropertyInternal(columnValue, columnName, tableNameClass,
				HibernateUtil.MY_SQL, 0, OES.ALL_ROWS, outputColumns);
		return list;
	}
	@SuppressWarnings("rawtypes")
	public List findByProperty(String columnValue,
			String columnName, Class tableNameClass, int from, int limit, String orderByColumnName) {   // Made the code generic to accept orderByColumnName
		List list = null;
		list = findByPropertyInternal(columnValue, columnName, tableNameClass,
				HibernateUtil.MY_SQL, from, limit, orderByColumnName != null && orderByColumnName.trim().length() > 0 ? new String[]{orderByColumnName} : new String[]{});
		return list;
	}
	@SuppressWarnings("rawtypes")
	public List findByProperty(String columnValue,
			String columnName, Class tableNameClass, int from, int limit, String orderByColumnNames[]) {   // Made the code generic to accept orderByColumnName
		List list = null;
		list = findByPropertyInternal(columnValue, columnName, tableNameClass,
				HibernateUtil.MY_SQL, from, limit, orderByColumnNames != null ? orderByColumnNames : new String[]{});
		return list;
	}
	@SuppressWarnings("rawtypes")
	private List findByPropertyInternal(String columnValue,
			String columnName, Class tableNameClass, String databaseName, int from, int limit,String outputColumns[]) {
		HibernateUtil hib_util = HibernateUtil.getInstance();
		Session session = hib_util.currentSession(databaseName);
		session.beginTransaction();
		//List result = new ArrayList<Object>(limit);
		Criteria c = null;
		c = session.createCriteria(tableNameClass);    // Made className generic than direct .class like Orders.class
		c.add(Expression.eq(columnName, columnValue));

		ProjectionList projections = Projections.projectionList();
		if(outputColumns != null) {
			for (String colName : outputColumns) {
				//c.addOrder(Order.desc(colName));
				Projection p = Projections.property(colName);
				projections.add(p);
			}
		}
		c.setProjection(projections);
		c.setFirstResult(from);

		if (limit != OES.ALL_ROWS) {
			c.setFetchSize(limit);
			c.setMaxResults(limit);
		}
		return c.list();
	}
	@SuppressWarnings("rawtypes")
	public List findByPropertyLike(String columnValue,
			String columnName, Class tableNameClass, int from, int limit, String orderByColumnName) {   // Made the code generic to accept orderByColumnName
		List list = null;
		list = findByPropertyLikeInternal(columnValue, columnName, tableNameClass,
				HibernateUtil.MY_SQL, from, limit, orderByColumnName);
		return list;
	}
	@SuppressWarnings("rawtypes")
	public List findByPropertyLike(java.util.Map<String, Object> columnConds,
			Class tableNameClass, int from, int limit, String orderByColumnName) { 
		List list = null;
		list = findByPropertyLikeInternal(columnConds, tableNameClass,
				HibernateUtil.MY_SQL, from, limit, orderByColumnName);
		return list;
	}
	@SuppressWarnings("rawtypes")
	public List findByPropertyLike(java.util.Map<String, Object> columnConds,
			Class tableNameClass, String orderByColumnName) { 
		List list = null;
		list = findByPropertyLikeInternal(columnConds, tableNameClass,
				HibernateUtil.MY_SQL, orderByColumnName);
		return list;
	}
	@SuppressWarnings("rawtypes")
	private List findByPropertyLikeInternal(java.util.Map<String, Object> columnConds,
			Class tableNameClass, String databaseName, String orderByColumnName) {
		HibernateUtil hib_util = HibernateUtil.getInstance();
		Session session = hib_util.currentSession(databaseName);
		session.beginTransaction();
		Criteria c = null;
		c = session.createCriteria(tableNameClass);
		Set<String> keys = columnConds.keySet();
		for (String key : keys) {
			c.add(Expression.eq(key, columnConds.get(key)));
		}

		if(StringUtils.isNotBlank(orderByColumnName)) {
			c.addOrder(Order.desc(orderByColumnName));
		}

		return c.list();
	}
	@SuppressWarnings("rawtypes")
	public List findByPropertyMap(java.util.Map<String, Object> columnConds, java.util.Map<String, String> operatorMap,
			Class tableNameClass, String orderByColumnName) { 
		List list = null;
		list = findByPropertyMap(columnConds, operatorMap, tableNameClass,
				HibernateUtil.MY_SQL, 0, OES.ALL_ROWS, orderByColumnName);
		return list;
	}
	@SuppressWarnings("rawtypes")
	public List findByPropertyMap(java.util.Map<String, Object> columnConds, java.util.Map<String, String> operatorMap,
			Class tableNameClass, int from, int limit, String orderByColumnName) { 
		List list = null;
		list = findByPropertyMap(columnConds, operatorMap, tableNameClass,
				HibernateUtil.MY_SQL, from, limit, orderByColumnName);
		return list;
	}


	@SuppressWarnings({ "rawtypes" })
	private List findByPropertyMap(java.util.Map<String, Object> columnConds, java.util.Map<String, String> operatorMap,
			Class tableNameClass, String databaseName, int from, int limit, String orderByColumnName) {
		HibernateUtil hib_util = HibernateUtil.getInstance();
		Session session = hib_util.currentSession(databaseName);
		session.beginTransaction();
		Criteria c = null;
		c = session.createCriteria(tableNameClass);
		Set<String> keys = columnConds.keySet();
		for (String key : keys) {
			String operator = operatorMap != null ? operatorMap.get(key) : OES.OPR_EQ;
			if (operator == null || operator.trim().length() == 0 || OES.OPR_EQ.equalsIgnoreCase(operator)) {
				c.add(Expression.eq(key, columnConds.get(key)));
			} else if(OES.OPR_GT.equalsIgnoreCase(operator)) {
				c.add(Expression.gt(key, columnConds.get(key)));
			}  else if(OES.OPR_LT.equalsIgnoreCase(operator)) {
				c.add(Expression.lt(key, columnConds.get(key)));
			}else if(OES.OPR_GE.equalsIgnoreCase(operator)) {
				c.add(Expression.ge(key, columnConds.get(key)));
			}  else if(OES.OPR_LE.equalsIgnoreCase(operator)) {
				c.add(Expression.le(key, columnConds.get(key)));
			}  else if(OES.OPR_NE.equalsIgnoreCase(operator)) {
				c.add(Expression.ne(key, columnConds.get(key)));
			} else if(OES.OPR_LIKE.equalsIgnoreCase(operator)) {
				c.add(Expression.like(key, columnConds.get(key)));
			} else if(OES.OPR_BTWN.equalsIgnoreCase(operator)) {
				//NV -  changed the condition to handle the  date between conditions, make sure todate and fromDate is not null to have the previous behavior
				/*
				 * NV - New Code
				 */
				List al = (ArrayList)columnConds.get(key);
				if(Validator.isEmptyList(al)) {
					continue;
				}
				Object fromDate = al.get(0);  
				Object toDate = al.get(1);
				if(fromDate!=null && toDate!=null) {
					c.add(Expression.between(key, fromDate, toDate));
				}else if(fromDate == null && toDate!=null) {
					c.add(Expression.le(key, toDate));
				}else if(fromDate != null && toDate==null) {
					c.add(Expression.ge(key, fromDate));
				}
				
				/* Previous code
				 List al = (ArrayList)columnConds.get(key);
				 c.add(Expression.between(key, al.get(0), al.get(1)));
				*/
				
			} else if(OES.OPR_IN.equalsIgnoreCase(operator)) {
				List al = (ArrayList)columnConds.get(key);
				c.add(Expression.in(key, al));
			}
		}
		if(StringUtils.isNotBlank(orderByColumnName)) {
			c.addOrder(Order.desc(orderByColumnName));
		}

		c.setFirstResult(from);

		if (limit  != OES.ALL_ROWS) {
			c.setMaxResults(limit);
			c.setFetchSize(limit);
		}

		System.out.println("Criteria: "+c);
		return c.list();
	}
	@SuppressWarnings("rawtypes")
	public List findByPropertyMapWithRestrictions(java.util.Map<String, Object> columnConds, java.util.Map<String, String> operatorMap,
			List<PropertyExpression> restrictions, Class tableNameClass, String orderByColumnName) { 
		List list = null;
		list = findByPropertyMapWithRestrictions(columnConds, operatorMap, restrictions, tableNameClass,
				HibernateUtil.MY_SQL, orderByColumnName);
		return list;
	}

	@SuppressWarnings("rawtypes")
	private List findByPropertyMapWithRestrictions(java.util.Map<String, Object> columnConds, java.util.Map<String, String> operatorMap,
			List<PropertyExpression> restrictions, Class tableNameClass, String databaseName, String orderByColumnName) {
		HibernateUtil hib_util = HibernateUtil.getInstance();
		Session session = hib_util.currentSession(databaseName);
		session.beginTransaction();
		Criteria c = null;
		c = session.createCriteria(tableNameClass);

		for (PropertyExpression propertyExpression : restrictions) {
			c.add(propertyExpression);
		}
		Set<String> keys = columnConds.keySet();
		for (String key : keys) {
			if (operatorMap != null) {
				String operator = operatorMap.get(key);
				if (operator == null || operator.trim().length() == 0
						|| OES.OPR_EQ.equalsIgnoreCase(operator)) {
					c.add(Expression.eq(key, columnConds.get(key)));
				} else if (OES.OPR_GT.equalsIgnoreCase(operator)) {
					c.add(Expression.gt(key, columnConds.get(key)));
				} else if (OES.OPR_LT.equalsIgnoreCase(operator)) {
					c.add(Expression.lt(key, columnConds.get(key)));
				} else if (OES.OPR_NE.equalsIgnoreCase(operator)) {
					c.add(Expression.ne(key, columnConds.get(key)));
				}
			} else {
				c.add(Expression.eq(key, columnConds.get(key)));
			}
		}
		if(StringUtils.isNotBlank(orderByColumnName)) {
			c.addOrder(Order.desc(orderByColumnName));
		}

		return c.list();
	}

	@SuppressWarnings("rawtypes")
	private List findByPropertyLikeInternal(java.util.Map<String, Object> columnConds,
			Class tableNameClass, String databaseName, int from, int limit,String orderByColumnName) {
		HibernateUtil hib_util = HibernateUtil.getInstance();
		Session session = hib_util.currentSession(databaseName);
		session.beginTransaction();
		Criteria c = null;
		c = session.createCriteria(tableNameClass);
		Set<String> keys = columnConds.keySet();
		for (String key : keys) {
			c.add(Expression.eq(key, columnConds.get(key)));
		}
		if(StringUtils.isNotBlank(orderByColumnName)) {
			c.addOrder(Order.desc(orderByColumnName));
		}
		c.setFirstResult(from);

		if (limit  != OES.ALL_ROWS) {
			c.setMaxResults(limit);
			c.setFetchSize(limit);
		}
		return c.list();
	}

	@SuppressWarnings("rawtypes")
	private List findByPropertyLikeInternal(String columnValue,
			String columnName, Class tableNameClass, String databaseName, int from, int limit,String orderByColumnName) {
		HibernateUtil hib_util = HibernateUtil.getInstance();
		Session session = hib_util.currentSession(databaseName);
		session.beginTransaction();
		//List result = new ArrayList<Object>(limit);
		Criteria c = null;
		c = session.createCriteria(tableNameClass);    // Made className generic than direct .class like Orders.class
		c.add(Expression.like(columnName, columnValue+"%"));
		if(StringUtils.isNotBlank(orderByColumnName)) {
			c.addOrder(Order.desc(orderByColumnName));
		}
		c.setFirstResult(from);

		if (limit != OES.ALL_ROWS) {
			c.setFetchSize(limit);
			c.setMaxResults(limit);
		}
		return c.list();
	}
	@SuppressWarnings("rawtypes")
	public List findByPropertyLike(String columnValue,
			String columnName, Class tableNameClass, String orderByColumnName) {   // Made the code generic to accept orderByColumnName
		List list = null;
		list = findByPropertyLikeInternal(columnValue, columnName, tableNameClass,
				HibernateUtil.MY_SQL, orderByColumnName);
		return list;
	}
	@SuppressWarnings("rawtypes")
	private List findByPropertyLikeInternal(String columnValue,
			String columnName, Class tableNameClass, String databaseName,
			String orderByColumnName) {
		HibernateUtil hib_util = HibernateUtil.getInstance();
		Session session = hib_util.currentSession(databaseName);
		session.beginTransaction();
		Criteria c = null;
		c = session.createCriteria(tableNameClass);    // Made className generic than direct .class like Orders.class
		c.add(Expression.like(columnName, columnValue+"%"));
		if(StringUtils.isNotBlank(orderByColumnName)) {
			c.addOrder(Order.desc(orderByColumnName));
		}
		return c.list();
	}

	@SuppressWarnings("rawtypes")
	private List findByPropertyInternal(String columnValue,
			String columnName, String tableName, String databaseName) {
		HibernateUtil hib_util = HibernateUtil.getInstance();
		Session session = hib_util.currentSession(databaseName);
		session.beginTransaction();
		StringBuffer buf = new StringBuffer(200);
		String queryString = buf.append("from ").append(tableName)
		.append(" m where m.").append(columnName)
		.append(" =:columnValue").toString();
		Query query = session.createQuery(queryString);
		query.setParameter("columnValue", columnValue);
		List list = query.list();
		session.getTransaction().commit();
		return list;
	}
	@SuppressWarnings("rawtypes")
	public List findColumnByProperty(Object colValue, String colName, String tableName, String colReq) {
		HibernateUtil hib_util = HibernateUtil.getInstance();
		Session session = hib_util.currentSession(HibernateUtil.MY_SQL);
		session.beginTransaction();
		String queryString = "SELECT * from "+tableName+" t where t."+colName+" = '"+colValue+"'";
		List list = session.createSQLQuery(queryString).addScalar(colReq).list();
		session.getTransaction().commit();
		return list;
	}
	@SuppressWarnings("rawtypes")
	public List list(String tableClassName){
		HibernateUtil hib_util = HibernateUtil.getInstance();
		Session session = hib_util.currentSession(HibernateUtil.MY_SQL);
		session.beginTransaction();
		StringBuffer buf = new StringBuffer(200);
		String queryString = buf.append("from ").append(tableClassName).toString();
		Query query = session.createQuery(queryString);
		List list = query.list();
		session.getTransaction().commit();
		return list;
	}

	@SuppressWarnings("rawtypes")
	private List findByPropertyLikeInternal(String columnValue,
			String columnName, String tableName, String databaseName) {
		HibernateUtil hib_util = HibernateUtil.getInstance();
		Session session = hib_util.currentSession(databaseName);
		session.beginTransaction();
		StringBuffer buf = new StringBuffer(200);
		String queryString = buf.append("from ").append(tableName)
		.append(" m where m.").append(columnName)
		.append(" like CONCAT(:columnValue, '%')").toString();
		Query query = session.createQuery(queryString);
		query.setParameter("columnValue", columnValue);
		List list = query.list();
		session.getTransaction().commit();
		return list;
	}

	@SuppressWarnings("rawtypes")
	public List inTextSearch(String columnValue,
			String columnName, String tableName) {
		HibernateUtil hib_util = HibernateUtil.getInstance();
		Session session = hib_util.currentSession(HibernateUtil.MY_SQL);
		session.beginTransaction();
		StringBuffer buf = new StringBuffer(200);
		String queryString = buf.append("from ").append(tableName)
		.append(" m where m.").append(columnName)
		.append(" like CONCAT('%' , ' ',:columnValue, '%')")
		.append(" OR ")
		.append(columnName)
		.append(" like CONCAT(:columnValue, '%') ").toString();
		Query query = session.createQuery(queryString);
		query.setParameter("columnValue", columnValue);
		List list = query.list();
		session.getTransaction().commit();
		return list;
	}
	@SuppressWarnings("rawtypes")
	public List findByPropertyLong(Long columnValue, String columnName,
			String tableName) {
		List list = null;
		list = findByPropertyInternalLong(columnValue, columnName, tableName,
				HibernateUtil.MY_SQL);
		return list;
	}

	@SuppressWarnings("rawtypes")
	private List findByPropertyInternalLong(Long columnValue,
			String columnName, String tableName, String databaseName) {
		HibernateUtil hib_util = HibernateUtil.getInstance();
		Session session = hib_util.currentSession(databaseName);
		session.beginTransaction();
		StringBuffer buf = new StringBuffer(200);
		String queryString = buf.append("from ").append(tableName)
		.append(" m where m.").append(columnName)
		.append(" =:columnValue").toString();
		Query query = session.createQuery(queryString);
		query.setParameter("columnValue", columnValue);
		List list = query.list();
		session.getTransaction().commit();
		return list;
	}
	@SuppressWarnings("rawtypes")
	public List findByPropertyFloat(Float columnValue, String columnName,
			String tableName) {
		List list = null;
		list = findByPropertyInternalFloat(columnValue, columnName, tableName,
				HibernateUtil.MY_SQL);
		return list;
	}
	@SuppressWarnings("rawtypes")
	private List findByPropertyInternalFloat(Float columnValue,
			String columnName, String tableName, String databaseName) {
		HibernateUtil hib_util = HibernateUtil.getInstance();
		Session session = hib_util.currentSession(databaseName);
		session.beginTransaction();
		StringBuffer buf = new StringBuffer(200);
		String queryString = buf.append("from ").append(tableName)
		.append(" m where m.").append(columnName)
		.append(" =:columnValue").toString();
		Query query = session.createQuery(queryString);
		query.setParameter("columnValue", columnValue);
		List list = query.list();
		session.getTransaction().commit();
		return list;
	}
	@SuppressWarnings("rawtypes")
	public List findByPropertyInt(Integer columnValue, String columnName,
			String tableName) {
		List list = null;
		list = findByPropertyInternalInt(columnValue, columnName, tableName,
				HibernateUtil.MY_SQL);
		return list;
	}
	@SuppressWarnings("rawtypes")
	private List findByPropertyInternalInt(Integer columnValue,
			String columnName, String tableName, String databaseName) {
		HibernateUtil hib_util = HibernateUtil.getInstance();
		Session session = hib_util.currentSession(databaseName);
		session.beginTransaction();
		StringBuffer buf = new StringBuffer(200);
		String queryString = buf.append("from ").append(tableName)
		.append(" m where m.").append(columnName)
		.append(" =:columnValue").toString();
		Query query = session.createQuery(queryString);
		query.setInteger("columnValue", columnValue);
		List list = query.list();
		session.getTransaction().commit();
		return list;
	}

	@SuppressWarnings("rawtypes")
	public List findByPropertyDate(Date columnValue, String columnName,
			Class tableClass) {
		List list = null;
		list = findByPropertyInternalDate(columnValue, columnName, tableClass,
				HibernateUtil.MY_SQL);
		return list;
	}
	@SuppressWarnings("rawtypes")
	private List findByPropertyInternalDate(Date columnValue,
			String columnName, Class tableName, String databaseName) {
		Calendar c = Calendar.getInstance();
		c.setTime(columnValue);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(columnValue);
		c2.set(Calendar.HOUR, 23);
		c2.set(Calendar.MINUTE, 59);
		c2.set(Calendar.SECOND, 59);

		return findByDateBetween(c.getTime(), c2.getTime(), columnName, tableName, 0, OES.ALL_ROWS, "");
		/*HibernateUtil hib_util = HibernateUtil.getInstance();
		Session session = hib_util.currentSession(databaseName);
		session.beginTransaction();
		StringBuffer buf = new StringBuffer(200);
		String queryString = buf.append("from ").append(tableName)
				.append(" m where m.").append(columnName)
				.append(" >:columnValue1")
				.append(" AND  <:columnValue2").toString();
		Query query = session.createQuery(queryString);
		//query.setDate("columnValue", columnValue);
		query.setDate("columnValue1", c.getTime());
		query.setDate("columnValue2", c2.getTime());
		//System.out.println("Query  fr Date--------->"+query.toString());
		List list = query.list();
		session.getTransaction().commit();
		//System.out.println("findByPropertyInternalDate size----------" + list.size());
		return list;*/
	}

	@SuppressWarnings("rawtypes")
	public List findByPropertyDateBetween(Date columnValue1, String columnName, Date columnValue2,
			String tableName) {
		List list = null;
		list = findByPropertyInternalDateBetween(columnValue1, columnName, columnValue2, tableName,
				HibernateUtil.MY_SQL);
		return list;
	}
	@SuppressWarnings("rawtypes")
	private List findByPropertyInternalDateBetween(Date columnValue1,
			String columnName, Date columnValue2, String tableName, String databaseName) {
		HibernateUtil hib_util = HibernateUtil.getInstance();
		Session session = hib_util.currentSession(databaseName);
		session.beginTransaction();
		StringBuffer buf = new StringBuffer(200);
		String queryString = buf.append("from ").append(tableName)
		.append(" m where m.").append(columnName)
		.append(" >=:columnValue1")
		.append(" AND m.").append(columnName)
		.append(" <=:columnValue2").toString();
		Query query = session.createQuery(queryString);
		query.setDate("columnValue1", columnValue1);
		query.setDate("columnValue2", columnValue2);
		//System.out.println("Query  fr Date--------->"+query.toString());
		List list = query.list();
		session.getTransaction().commit();
		//System.out.println("findByPropertyInternalDate size----------" + list.size());
		return list;
	}
	@SuppressWarnings("rawtypes")
	public List findByPropertyBoolean(Boolean columnValue, String columnName,
			String tableName) {
		List list = null;
		list = findByPropertyInternalBoolean(columnValue, columnName, tableName,
				HibernateUtil.MY_SQL);
		return list;
	}
	@SuppressWarnings("rawtypes")
	private List findByPropertyInternalBoolean(Boolean columnValue,
			String columnName, String tableName, String databaseName) {
		HibernateUtil hib_util = HibernateUtil.getInstance();
		Session session = hib_util.currentSession(databaseName);
		session.beginTransaction();
		StringBuffer buf = new StringBuffer(200);
		String queryString = buf.append("from ").append(tableName)
		.append(" m where m.").append(columnName)
		.append(" =:columnValue").toString();
		Query query = session.createQuery(queryString);
		query.setBoolean("columnValue", columnValue);
		//System.out.println("Query  fr bit--------->"+query.toString());
		List list = query.list();
		session.getTransaction().commit();
		//System.out.println("findByPropertyInternalbitsize----------" + list.size());
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List listColumnValues(String tableName, String colName, boolean unique){
		HibernateUtil hib_util = HibernateUtil.getInstance();
		Session session = hib_util.currentSession(HibernateUtil.MY_SQL);
		StringBuffer buf = new StringBuffer(50);
		buf.append("SELECT ").append(colName).append(" FROM ").append(tableName);
		Query query = session.createSQLQuery(buf.toString());
		List returnList = query.list();
		//System.out.println(returnList);
		if(unique && returnList != null && returnList.size() > 0){
			Set s = new TreeSet();
			for (Iterator iterator = returnList.iterator(); iterator.hasNext();) {
				Object object = iterator.next();
				if(object != null) {
					s.add(object);
				}
			}
			returnList.clear();
			returnList.addAll(s);
		}
		return returnList != null ? returnList : new ArrayList();
	}

	public void findByWhereClause(String tableName, String columnName,
			String columnValue, boolean unique) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List listByWhereClause(String tableName, String columnName,
			String condColumn, String condValue, boolean unique) {
		HibernateUtil hib_util = HibernateUtil.getInstance();
		Session session = hib_util.currentSession(HibernateUtil.MY_SQL);
		StringBuffer buf = new StringBuffer(50);
		buf.append("SELECT ").append(columnName).append(" FROM ").append(tableName);
		buf.append(" WHERE ").append(condColumn).append(" LIKE ").append(condValue);
		Query query = session.createSQLQuery(buf.toString());
		List returnList = query.list();
		//System.out.println(returnList);
		if(unique && returnList != null && returnList.size() > 0){
			Set s = new TreeSet();
			for (Iterator iterator = returnList.iterator(); iterator.hasNext();) {
				Object object = iterator.next();
				if(object != null) {
					s.add(object);
				}
			}
			returnList.clear();
			returnList.addAll(s);
		}
		return returnList != null ? returnList : new ArrayList();
	}
	@SuppressWarnings("rawtypes")
	public List listItemsByBarCode(String tableName, String condColmnVal) {
		HibernateUtil hib_util = HibernateUtil.getInstance();
		Session session = hib_util.currentSession(HibernateUtil.MY_SQL);
		session.beginTransaction();
		StringBuffer buf = new StringBuffer(50);
		buf.append("select {item.*} from ").append(tableName).append(" item where productid in (select b.productid from barcodes b where barcode like ").append(condColmnVal).append(")");
		List list = session.createSQLQuery(buf.toString()).addEntity("item", Masteritem.class).list();
		session.getTransaction().commit();
		return list;

	}
	@SuppressWarnings("rawtypes")
	public Object findByPropertyOne(String columnValue, String columnName, String tableName) {
		List list = findByProperty(columnValue, columnName, tableName);
		if(Validator.isNotEmptyList(list)) {
			return list.get(0);
		}else {
			return null;
		}
	}


	@SuppressWarnings("rawtypes")
	public long getCountByColumnEq(String columnName,String columnValue, Class tableNameClass){
		Number count = getCountByColumnEqInternal(columnName, columnValue, tableNameClass , HibernateUtil.MY_SQL)  ; 
		return count.longValue();
	}

	@SuppressWarnings("rawtypes")
	private Number getCountByColumnEqInternal(String columnName, String columnValue, Class tableNameClass, String databaseName) {
		HibernateUtil hib_util = HibernateUtil.getInstance();
		Session session = hib_util.currentSession(databaseName);
		session.beginTransaction();
		Criteria c =  session.createCriteria(tableNameClass);    
		if (columnName != null && columnValue != null) {
			c.add(Restrictions.eq(columnName, columnValue));
		}
		c.setProjection(Projections.rowCount());
		return (Number) c.list().get(0);
	}
	@SuppressWarnings("rawtypes")
	public List findByDateBetween(Date fromDate, Date toDate,
			String columnName, Class tableNameClass, int from, int limit, String orderByColumnName) {
		List list = null;
		list = findByDateBetweenInternal(fromDate,toDate , columnName, tableNameClass, HibernateUtil.MY_SQL, from, limit, orderByColumnName, 0);
		return list;
	}

	@SuppressWarnings("rawtypes")
	public List findByDateBetween(Date fromDate, Date toDate,
			String columnName, Class tableNameClass, int from, int limit, String orderByColumnName, int sortOrder) {

		Calendar c = Calendar.getInstance();
		c.setTime(fromDate);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 01);
		c.set(Calendar.SECOND, 01);
		c.set(Calendar.AM_PM, Calendar.AM);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(toDate);
		c2.set(Calendar.HOUR, 11);
		c2.set(Calendar.MINUTE, 59);
		c2.set(Calendar.SECOND, 59);
		c2.set(Calendar.AM_PM, Calendar.PM);

		List list = null;
		list = findByDateBetweenInternal(c.getTime(), c2.getTime() , columnName, tableNameClass, HibernateUtil.MY_SQL, from, limit, orderByColumnName , sortOrder);
		return list;
	}

	@SuppressWarnings("rawtypes")
	private List findByDateBetweenInternal(Date fromDate, Date toDate,
			String columnName, Class tableNameClass, String databaseName, int from, int limit,String orderByColumnName, int sortOrder) {
		HibernateUtil hib_util = HibernateUtil.getInstance();
		Session session = hib_util.currentSession(databaseName);
		session.beginTransaction();
		Criteria c = session.createCriteria(tableNameClass);    // Made className generic than direct .class like Orders.class
		//c.add(Expression.between(columnName, fromDate,toDate));
		c.add(Expression.ge(columnName, fromDate));
		c.add(Expression.le(columnName,toDate));
		if(StringUtils.isNotBlank(orderByColumnName)) {
			if (sortOrder == 0) {
				c.addOrder(Order.desc(orderByColumnName));
			} else {
				c.addOrder(Order.asc(orderByColumnName));
			}
		}
		c.setFirstResult(from);
		if(limit != OES.ALL_ROWS) {
			c.setMaxResults(limit);
			c.setFetchSize(limit);
		}
		System.out.println("Criteria: "+c);
		List x = c.list();
		System.out.println("Size.. "+x.size());
		return x;
	}

	@SuppressWarnings("rawtypes")
	public List findByDateBetweenAndCondnInOrEq(String dateColumn, Date fromDate, Date toDate, String condnColumn, String condnColumnMultiValue,
			Class tableNameClass, int from, int limit, String orderByColumnName) {
		List list = null;
		list = findByDateBetweenAndCondnInOrEqInternal(dateColumn, fromDate,toDate ,condnColumn,condnColumnMultiValue, tableNameClass, HibernateUtil.MY_SQL, from, limit, orderByColumnName);
		return list;
	}

	@SuppressWarnings("rawtypes")
	private List findByDateBetweenAndCondnInOrEqInternal(String dateColumn, Date fromDate, Date toDate, String condnColumn, String condnColumnMultiValue,
			Class tableNameClass, String databaseName, int from, int limit, String orderByColumnName) {

		HibernateUtil hib_util = HibernateUtil.getInstance();
		Session session = hib_util.currentSession(databaseName);
		session.beginTransaction();
		Criteria c = session.createCriteria(tableNameClass);    // Made className generic than direct .class like Orders.class
		if(fromDate!=null && toDate!=null) {
			c.add(Restrictions.between(dateColumn, fromDate,toDate));
		}else if(fromDate == null && toDate!=null) {
			c.add(Restrictions.le(dateColumn,toDate));
		}else if(fromDate != null && toDate==null) {
			c.add(Restrictions.ge(dateColumn,toDate));
		}

		String[] inClauseValues = StringUtils.split(condnColumnMultiValue, OES.DB_MULTIVAL_SEPERATOR);
		if(inClauseValues!=null && inClauseValues.length > 1) {
			c.add(Restrictions.in(condnColumn,inClauseValues ));
		}else  if(inClauseValues!=null && inClauseValues.length == 1) {
			c.add(Restrictions.eq(condnColumn,inClauseValues[0] ));
		}

		if(StringUtils.isNotBlank(orderByColumnName)) {
			c.addOrder(Order.desc(orderByColumnName));
		}
		c.setFirstResult(from);
		if(limit != OES.ALL_ROWS) {
			c.setMaxResults(limit);
			c.setFetchSize(limit);
		}
		return c.list();
	}



	@SuppressWarnings("rawtypes")
	public List findByPropertyInOrEq(String columnName, List<String> columnValues,
			Class tableNameClass, int from, int limit, String orderByColumnName) {
		List list = null;
		list = findByPropertyInOrEqInternal(columnName, columnValues, tableNameClass, HibernateUtil.MY_SQL, from, limit, orderByColumnName);
		return list;
	}

	@SuppressWarnings("rawtypes")
	private List findByPropertyInOrEqInternal(String columnName, List<String> columnValues,
			Class tableNameClass, String databaseName, int from, int limit, String orderByColumnName) {

		HibernateUtil hib_util = HibernateUtil.getInstance();
		Session session = hib_util.currentSession(databaseName);
		session.beginTransaction();
		Criteria c = session.createCriteria(tableNameClass);    
		c.add(Restrictions.in(columnName, columnValues));

		if(Validator.isMultiValueList(columnValues)) {
			c.add(Restrictions.in(columnName,columnValues ));
		}else if(Validator.isSingleValueList(columnValues)) {
			c.add(Restrictions.eq(columnName,columnValues.get(0)));
		}

		if(StringUtils.isNotBlank(orderByColumnName)) {
			c.addOrder(Order.desc(orderByColumnName));
		}
		c.setFirstResult(from);
		System.out.println("Criteria Query: "+c);
		if(limit != OES.ALL_ROWS) {
			c.setMaxResults(limit);
			c.setFetchSize(limit);
		}
		return c.list();
	}

	@SuppressWarnings("rawtypes")
	public List findByDateBetweenAndCondnInOrEqAndColBetween(String dateColumn, Date fromDate, Date toDate, String condnColumn1, String condnColumn1MultiValue,String condnColumn2, String condnColumn2BetnValues,
			Class tableNameClass, int from, int limit, String orderByColumnName) {
		List list = null;
		list = findByDateBetweenAndCondnInOrEqAndColBetween(dateColumn, fromDate,toDate ,condnColumn1,condnColumn1MultiValue,condnColumn2,condnColumn2BetnValues, tableNameClass, HibernateUtil.MY_SQL, from, limit, orderByColumnName);
		return list;
	}


	@SuppressWarnings("rawtypes")
	private List findByDateBetweenAndCondnInOrEqAndColBetween(String dateColumn, Date fromDate, Date toDate, String condnColumn1, String condnColumn1MultiValue,String condnColumn2, String condnColumn2BetnValues,
			Class tableNameClass, String databaseName, int from, int limit, String orderByColumnName) {

		HibernateUtil hib_util = HibernateUtil.getInstance();
		Session session = hib_util.currentSession(databaseName);
		session.beginTransaction();
		Criteria c = session.createCriteria(tableNameClass); 

		if(fromDate!=null && toDate!=null) {
			c.add(Restrictions.between(dateColumn, fromDate,toDate));
		}else if(fromDate == null && toDate!=null) {
			c.add(Restrictions.le(dateColumn,toDate));
		}else if(fromDate != null && toDate==null) {
			c.add(Restrictions.ge(dateColumn,toDate));
		}


		String[] inClauseValues = StringUtils.split(condnColumn1MultiValue, OES.DB_MULTIVAL_SEPERATOR);
		if(inClauseValues!=null && inClauseValues.length > 1) {
			c.add(Restrictions.in(condnColumn1,inClauseValues ));
		}else  if(inClauseValues!=null && inClauseValues.length == 1) {
			c.add(Restrictions.eq(condnColumn1,inClauseValues[0] ));
		}

		String[] betnClauseValues = StringUtils.split(condnColumn2BetnValues, OES.DB_MULTIVAL_SEPERATOR);
		if(betnClauseValues!=null && betnClauseValues.length > 1) {
			for(int i = 0 ; i<betnClauseValues.length;i++) {
				c.add(Restrictions.like(condnColumn2,betnClauseValues[i]));
			}
		}

		if(StringUtils.isNotBlank(orderByColumnName)) {
			c.addOrder(Order.desc(orderByColumnName));
		}
		c.setFirstResult(from);
		if(limit != OES.ALL_ROWS) {
			c.setMaxResults(limit);
			c.setFetchSize(limit);
		}
		return c.list();
	}

	@SuppressWarnings("rawtypes")
	public List findByDateBetweenAndCondnInOrEqAndStringBetween(String dateColumn, Date fromDate, Date toDate, String condnColumn1, String condnColumn1MultiValue,String condnColumn2, String condnColumn2BetnValues,
			Class tableNameClass, int from, int limit, String orderByColumnName) {
		List list = null;
		list = findByDateBetweenAndCondnInOrEqAndStringBetweenInternal(dateColumn, fromDate,toDate ,condnColumn1,condnColumn1MultiValue,condnColumn2,condnColumn2BetnValues, tableNameClass, HibernateUtil.MY_SQL, from, limit, orderByColumnName);
		return list;
	}

	@SuppressWarnings("rawtypes")
	private List findByDateBetweenAndCondnInOrEqAndStringBetweenInternal(String dateColumn, Date fromDate, Date toDate, String condnColumn1, String condnColumn1MultiValue,String condnColumn2, String condnColumn2BetnValues,
			Class tableNameClass, String databaseName, int from, int limit, String orderByColumnName) {

		HibernateUtil hib_util = HibernateUtil.getInstance();
		Session session = hib_util.currentSession(databaseName);
		session.beginTransaction();
		Criteria c = session.createCriteria(tableNameClass); 

		if(fromDate!=null && toDate!=null) {
			c.add(Restrictions.between(dateColumn, fromDate,toDate));
		}else if(fromDate == null && toDate!=null) {
			c.add(Restrictions.le(dateColumn,toDate));
		}else if(fromDate != null && toDate==null) {
			c.add(Restrictions.ge(dateColumn,toDate));
		}


		String[] inClauseValues = StringUtils.split(condnColumn1MultiValue, OES.DB_MULTIVAL_SEPERATOR);
		if(inClauseValues!=null && inClauseValues.length > 1) {
			c.add(Restrictions.in(condnColumn1,inClauseValues ));
		}else  if(inClauseValues!=null && inClauseValues.length == 1) {
			c.add(Restrictions.eq(condnColumn1,inClauseValues[0] ));
		}

		String[] betnClauseValues = StringUtils.split(condnColumn2BetnValues, OES.DB_MULTIVAL_SEPERATOR);

		if(betnClauseValues!=null && betnClauseValues.length > 1) {
			c.add(Restrictions.sqlRestriction("strcmp(customercode, '"+betnClauseValues[0].substring(0, betnClauseValues[0].length()-1)+"') >= 0"));
			c.add(Restrictions.sqlRestriction("strcmp(customercode, '"+betnClauseValues[1].substring(1, betnClauseValues[0].length())+"') <= 0"));
		}

		if(StringUtils.isNotBlank(orderByColumnName)) {
			c.addOrder(Order.desc(orderByColumnName));
		}
		c.setFirstResult(from);
		if(limit != OES.ALL_ROWS) {
			c.setMaxResults(limit);
			c.setFetchSize(limit);
		}

		return c.list();
	}
	
	public List getInventoryHistoricalData(HashMap<String, Object> conds, String year) {
		List retVal = new ArrayList();
		
		List<Date> monthStartDates = DatesUtils.monthStartDates(year);
		List<Date> monthEndDates = DatesUtils.monthEndDates(year);
		
		HibernateUtil hib_util = HibernateUtil.getInstance();
		Session session = hib_util.currentSession(HibernateUtil.MY_SQL);
		session.beginTransaction();
		 
		
		for(int i = 0; i < 12; i++) {
			Criteria c = session.createCriteria(Orderdetails.class);
			c.add(Restrictions.ge("orderdate", monthStartDates.get(i)));
			c.add(Restrictions.le("orderdate", monthEndDates.get(i)));
			if(conds != null) {
				for (String condAttr : conds.keySet()) {
					c.add(Restrictions.eq(condAttr, conds.get(condAttr)));
				}
			}
			c.add(Restrictions.not(Restrictions.like("itemcode", "%Note%")));
			ProjectionList projectionList = Projections.projectionList();
			/*projectionList.add(Projections.groupProperty("itemcode"));
			projectionList.add(Projections.sum("quantityordered"));
			projectionList.add(Projections.sum("quantityshipped"));*/
			projectionList.add(Projections.property("itemcode"));
			projectionList.add(Projections.property("quantityordered"));
			projectionList.add(Projections.property("quantityshipped"));

			/*projectionList.add(Projections.property("listprice"));
			projectionList.add(Projections.property("description"));
			projectionList.add(Projections.property("pricea"));
			projectionList.add(Projections.property("productcategory"));
			projectionList.add(Projections.property("category"));
			projectionList.add(Projections.property("subcategoryid"));*/
			
			c.setProjection(projectionList);
			
			System.out.println("Criteria "+i+": "+c);
			retVal.add(c.list());
		}
		return retVal;
	}
	@SuppressWarnings("rawtypes")
	public List findByDateBetweenAndCondnInOrEq2(String dateColumn, Date fromDate, Date toDate, String condnColumn1, String condnColumn1MultiValue,String condnColumn2, String condnColumn2MultiValue,
			Class tableNameClass, int from, int limit, String orderByColumnName) {
		List list = null;
		list = findByDateBetweenAndCondnInOrEq2Internal(dateColumn, fromDate,toDate ,condnColumn1,condnColumn1MultiValue,condnColumn2,condnColumn2MultiValue, tableNameClass, HibernateUtil.MY_SQL, from, limit, orderByColumnName);
		return list;
	}


	@SuppressWarnings("rawtypes")
	private List findByDateBetweenAndCondnInOrEq2Internal(String dateColumn, Date fromDate, Date toDate, String condnColumn1, String condnColumn1MultiValue,String condnColumn2, String condnColumn2MultiValue,
			Class tableNameClass, String databaseName, int from, int limit, String orderByColumnName) {

		HibernateUtil hib_util = HibernateUtil.getInstance();
		Session session = hib_util.currentSession(databaseName);
		session.beginTransaction();
		Criteria c = session.createCriteria(tableNameClass);    

		if(fromDate!=null && toDate!=null) {
			c.add(Restrictions.between(dateColumn, fromDate,toDate));
		}else if(fromDate == null && toDate!=null) {
			c.add(Restrictions.le(dateColumn,toDate));
		}else if(fromDate != null && toDate==null) {
			c.add(Restrictions.ge(dateColumn,toDate));
		}

		String[] inClauseValues = StringUtils.split(condnColumn1MultiValue, OES.DB_MULTIVAL_SEPERATOR);
		if(inClauseValues!=null && inClauseValues.length > 1) {
			c.add(Restrictions.in(condnColumn1,inClauseValues ));
		}else  if(inClauseValues!=null && inClauseValues.length == 1) {
			c.add(Restrictions.eq(condnColumn1,inClauseValues[0] ));
		}

		String[] inClauseValues2 = StringUtils.split(condnColumn2MultiValue, OES.DB_MULTIVAL_SEPERATOR);
		if(inClauseValues2!=null && inClauseValues2.length > 1) {
			c.add(Restrictions.in(condnColumn2,inClauseValues2 ));
		}else  if(inClauseValues2!=null && inClauseValues2.length == 1) {
			c.add(Restrictions.eq(condnColumn2,inClauseValues2[0] ));
		}
		if(StringUtils.isNotBlank(orderByColumnName)) {
			c.addOrder(Order.desc(orderByColumnName));
		}
		c.setFirstResult(from);
		if(limit != OES.ALL_ROWS) {
			c.setMaxResults(limit);
			c.setFetchSize(limit);
		}

		logger_.info(c);
		return c.list();
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List findByPropertyWithGrouping(java.util.Map<String, Object> columnConds, java.util.Map<String, String> operatorMap, 
			Class tableNameClass, int from, int limit, Projection[] projections,
			String[] orderByColumnNames) {
		return findByPropertyWithGroupingInternal(columnConds, operatorMap, tableNameClass, from, limit, projections, orderByColumnNames, HibernateUtil.MY_SQL);

	}
	@SuppressWarnings("rawtypes")
	private List findByPropertyWithGroupingInternal(java.util.Map<String, Object> columnConds, java.util.Map<String, String> operatorMap, 
			Class<Itemhistory> tableNameClass, int from, int limit, Projection[] projections,
			String[] orderByColumnNames, String databaseName ) {
		HibernateUtil hib_util = HibernateUtil.getInstance();
		Session session = hib_util.currentSession(databaseName);
		session.beginTransaction();
		//List result = new ArrayList<Object>(limit);
		Criteria c = null;
		c = session.createCriteria(tableNameClass);    // Made className generic than direct .class like Orders.class


		Set<String> keys = columnConds.keySet();
		for (String key : keys) {
			String operator = operatorMap != null ? operatorMap.get(key) : OES.OPR_EQ;
			if (operator == null || operator.trim().length() == 0 || OES.OPR_EQ.equalsIgnoreCase(operator)) {
				c.add(Expression.eq(key, columnConds.get(key)));
			} else if(OES.OPR_GT.equalsIgnoreCase(operator)) {
				c.add(Expression.gt(key, columnConds.get(key)));
			}  else if(OES.OPR_LT.equalsIgnoreCase(operator)) {
				c.add(Expression.lt(key, columnConds.get(key)));
			}  else if(OES.OPR_NE.equalsIgnoreCase(operator)) {
				c.add(Expression.ne(key, columnConds.get(key)));
			} else if(OES.OPR_LIKE.equalsIgnoreCase(operator)) {
				c.add(Expression.like(key, columnConds.get(key)));
			} else if(OES.OPR_BTWN.equalsIgnoreCase(operator)) {
				List al = (ArrayList)columnConds.get(key);
				if(Validator.isEmptyList(al)) 
					continue;
				c.add(Expression.between(key, al.get(0), al.get(1)));
			}
		}

		//	c.add(Expression.eq(columnName, columnValue));

		ProjectionList projectionList = Projections.projectionList();
		if (projections != null) {
			for (Projection proj : projections) {
				projectionList.add(proj);
			}
			c.setProjection(projectionList);
		}
		if(orderByColumnNames != null) {
			for (String colName : orderByColumnNames) {
				c.addOrder(Order.desc(colName));
			}
		}

		c.setFirstResult(from);

		if (limit != OES.ALL_ROWS) {
			c.setFetchSize(limit);
			c.setMaxResults(limit);
		}
		return c.list();
	}
	@SuppressWarnings("rawtypes")
	// generalize this method for further options like order by column etc
	public List findAll(Class tableNameClass) {
		List result = findAllInternal(tableNameClass, HibernateUtil.MY_SQL);
		return result;

	}
	@SuppressWarnings("rawtypes")
	private List findAllInternal(Class tableNameClass, String databaseName) {
		HibernateUtil hib_util = HibernateUtil.getInstance();
		Session session = hib_util.currentSession(databaseName);
		session.beginTransaction();
		Criteria c = null;
		c = session.createCriteria(tableNameClass); 	
		return c.list();
	}
	
	public boolean delete(String columnName, String columnValue, String tableName){
		return deleteInternal(columnName, columnValue, tableName,  HibernateUtil.MY_SQL);
	}

	public boolean deleteInternal(String columnName, String columnValue, String tableName, String databaseName){
		HibernateUtil hib_util = HibernateUtil.getInstance();
		Session session = hib_util.currentSession(databaseName);
		session.beginTransaction();
		String hql = "delete from " + tableName +" where " + columnName + " = :columnValue";
		int result = 	session.createQuery(hql).setString("columnValue", columnValue).executeUpdate();
		session.getTransaction().commit();
		return true;
	}
}
