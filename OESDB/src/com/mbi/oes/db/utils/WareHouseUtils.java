package com.mbi.oes.db.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;

import com.oes.db.model.util.BasicDAO;
import com.oes.model.my_sql.entities.Orderdetails;
import com.oes.model.my_sql.entities.Warehouseitemdetails;


public class WareHouseUtils {
	static BasicDAO dao = new BasicDAO();
	private static Logger logger_ = Logger.getLogger(WareHouseUtils.class);
	
	public static int getOnHand(String itemcode, int warehouseid) {
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("itemcode", itemcode);
		paramsMap.put("warehouseid", warehouseid);
		
		List<Object> list = dao.findByPropertyMap(paramsMap, null, Warehouseitemdetails.class, "");
		if(Validator.isNotEmptyList(list)) {
			return ((Warehouseitemdetails)list.get(0)).getOnhand();
		}
		return 0;
	}
	
	public static int getOnHandAllWh(String itemcode) {
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("itemcode", itemcode);
		
		HashMap<String, String> opersMap = new HashMap<String, String>();
		List<Object> list = dao.findByPropertyMap(paramsMap, opersMap, Warehouseitemdetails.class, "");
		Projection p[] = new Projection[2];
		p[0] = Projections.sum("onhand");
		p[1] = Projections.groupProperty("itemcode");
		
		if(Validator.isNotEmptyList(list)) {
			int sum = 0;
			 for (int i = 0; i < list.size(); i++) {
				 Warehouseitemdetails od = ((Warehouseitemdetails) list.get(i));
				sum += od.getOnhand();
			}
			 return sum;
		}
		return 0;
	}
	
	public static int getOnHand(String itemcode) {
		logger_.info("ENTRY: getOnHand(1) " +itemcode);
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("itemcode", itemcode);
		//paramsMap.put("warehouseid", UserServiceUtil.getCurrentUserWarehouseId());
		
		List<Object> list = dao.findByPropertyMap(paramsMap, null, Warehouseitemdetails.class, "");
		if(Validator.isNotEmptyList(list)) {
			logger_.info("Warehousedetails for itemcode: "+itemcode+" -- "+list.get(0));
			return ((Warehouseitemdetails)list.get(0)).getOnhand();
		}
		return 0;
	}
	
	public static int getCommitted(String itemcode, int warehouseid) {
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("itemcode", itemcode);
		paramsMap.put("warehouseid", warehouseid);
		
		List<Object> list = dao.findByPropertyMap(paramsMap, null, Warehouseitemdetails.class, "");
		if(Validator.isNotEmptyList(list)) {
			return ((Warehouseitemdetails)list.get(0)).getCommitted();
		}
		return 0;
	}
	
	public static int getCommittedAllWh(String itemcode) {
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("itemcode", itemcode);
		
		List<Object> list = dao.findByPropertyMap(paramsMap, null, Warehouseitemdetails.class, "");
		if(Validator.isNotEmptyList(list)) {
			int sum = 0;
			 for (int i = 0; i < list.size(); i++) {
				sum += ((Warehouseitemdetails) list.get(i)).getCommitted();
			}
			 return sum;
		}
		return 0;
	}
	
	public static int getOnOrderAllWh(String itemcode) {
		return getOnOrderAllWh(itemcode, false);
	}
	/**
	 * Calculates the on order quantity for given item
	 * @param itemcode Item Code (Product ID)
	 * @param forCurrentYear For current year or for all data
	 * @return quantity on order
	 */
	public static int getOnOrderAllWh(String itemcode, boolean forCurrentYear) {
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("itemcode", itemcode);
		
		HashMap<String, String> opersMap = new HashMap<String, String>();
		
		if(forCurrentYear) {
			Calendar c = Calendar.getInstance();
			c.set(Calendar.MONTH, Calendar.JANUARY);
			c.set(Calendar.DAY_OF_MONTH, 1);
			List<Date> al = new ArrayList<Date>();
			c.set(Calendar.MONTH, Calendar.DECEMBER);
			c.set(Calendar.DAY_OF_MONTH, 31);
			
			paramsMap.put("orderdate", al);
			opersMap.put("orderdate", OES.OPR_BTWN);
		}
		List<Object> list;// = dao.findByPropertyMap(paramsMap, opersMap, Orderdetails.class, "");
		Projection p[] = new Projection[3];
		p[0] = Projections.sum("quantityordered");
		p[1] = Projections.sum("quantityshipped");
		p[2] = Projections.groupProperty("itemcode");
		
		list = dao.findByPropertyWithGrouping(paramsMap, opersMap, Orderdetails.class, 
				0, OES.ALL_ROWS, p, null);
		Object obj[] = (Object[]) list.get(0);
		return Integer.parseInt(obj[0]+"") - Integer.parseInt(obj[1]+"");
		/*if(Validator.isNotEmptyList(list)) {
			int sum = 0;
			 for (int i = 0; i < list.size(); i++) {
				Orderdetails od = ((Orderdetails) list.get(i));
				sum += od.getQuantityordered() - od.getQuantityshipped();
			}
			 return sum;
		}*/
		//return 0;
	}
	
	public static int getCommitted(String itemcode) {
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("itemcode", itemcode);
		//paramsMap.put("warehouseid", UserServiceUtil.getCurrentUserWarehouseId());
		
		List<Object> list = dao.findByPropertyMap(paramsMap, null, Warehouseitemdetails.class, "");
		if(Validator.isNotEmptyList(list)) {
			return ((Warehouseitemdetails)list.get(0)).getCommitted();
		}
		return 0;
	}
	
}
