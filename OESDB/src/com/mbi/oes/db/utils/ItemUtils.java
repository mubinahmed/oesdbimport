package com.mbi.oes.db.utils;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.oes.db.model.util.BasicDAO;
import com.oes.db.model.util.HibernateUtil;
import com.oes.model.my_sql.entities.Masteritem;

public class ItemUtils {

	static BasicDAO dao = new BasicDAO();
	
	public static String getItemDescriptionByID(String itemCode,
			String defaultIfNullValue) {
		Masteritem item = (Masteritem) dao.findByPropertyOne(itemCode,
				"productid", "Masteritem");
		String itemDesc = null;
		if (item != null) {
			itemDesc = StringUtils.defaultIfBlank(item.getDescription(),
					defaultIfNullValue);
		} else {
			itemDesc = itemCode;
		}
		return itemDesc;
	}
	
	/*public static void updateCommitted(String itemid, double quantity) {
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("itemcode", itemid);
		paramsMap.put("warehouseid", UserServiceUtil.getCurrentUserWarehouseId());
		
		List<Object> al = dao.findByPropertyMap(paramsMap, null, Warehouseitemdetails.class, "");
		if(Validator.isEmptyList(al)) {
			return;
		}
		Warehouseitemdetails w = (Warehouseitemdetails)al.get(0);
		int existingBalance = w.getCommitted();
		w.setCommitted((int)(existingBalance + quantity));
		HibernateUtil.getInstance().save(w);
	}*/
	
	/*public static void updateOnHand(String itemid, int quantity) {
		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("itemcode", itemid);
		paramsMap.put("warehouseid", UserServiceUtil.getCurrentUserWarehouseId());
		
		List<Object> al = dao.findByPropertyMap(paramsMap, null, Warehouseitemdetails.class, "");
		if(Validator.isEmptyList(al)) {
			return;
		}
		Warehouseitemdetails w = (Warehouseitemdetails)al.get(0);
		int existingBalance = w.getOnhand();
		w.setOnhand((int)(existingBalance - quantity));
		HibernateUtil.getInstance().save(w);
	}*/
	
	/*public static void itemShipped(String itemid, int quantity) {

		HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("itemcode", itemid);
		paramsMap.put("warehouseid", UserServiceUtil.getCurrentUserWarehouseId());
		
		List<Object> al = dao.findByPropertyMap(paramsMap, null, Warehouseitemdetails.class, "");
		if(Validator.isEmptyList(al)) {
			return;
		}
		Warehouseitemdetails w = (Warehouseitemdetails)al.get(0);
		int existingBalance = w.getOnhand();
		w.setOnhand((int)(existingBalance - quantity));
		w.setCommitted(w.getCommitted() - quantity);
		HibernateUtil.getInstance().save(w);
	
	}*/
	
}
