package tr.com.muzo.db.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tr.com.muzo.parameter.SimpleAramaKriter;

@Repository
public class DBUtil {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getCurrentSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public void kaydet(Object obje){
		getCurrentSession().saveOrUpdate(obje);
	}
	
	@SuppressWarnings({ "rawtypes"})
	public List queryOlustur(String pojoName , Object data){
		String sorgu = " from "+pojoName+" as t where 1=1 ";
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		Map<String,Map<String,String>> aramaKriterleri = null;
		aramaKriterleri = ((SimpleAramaKriter)data).aramaKriter();
		if(aramaKriterleri == null){
			return new ArrayList();
		}
		Field[] fieldler = data.getClass().getDeclaredFields();
		for(Field field : fieldler){
			if(!field.getName().equals("serialVersionUID") && !field.getName().equals("aramaKriterleri")){
				Object o = null;
				try {
					o = PropertyUtils.getProperty(data, field.getName());
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				}
				if(o != null && !"".equals(o.toString())){
					parameterMap.put(field.getName(), o);
					String aramaTip = aramaKriterleri.get(field.getName()).get("aramaTip");
					String aramaYeri = aramaKriterleri.get(field.getName()).get("aramaYeri");
					if("like".equals(aramaTip)){
						if("anyWhere".equals(aramaYeri)){
							parameterMap.put(field.getName(), "%"+o+"%");
						}else if("start".equals(aramaYeri)){
							parameterMap.put(field.getName(), "%"+o);
						}else if("end".equals(aramaYeri)){
							parameterMap.put(field.getName(), o+"%");
						}
						sorgu = sorgu + " and t."+field.getName() + " like :"+field.getName() + "\n";
					}else if("=".equals(aramaTip)){
						sorgu = sorgu + " and t."+field.getName() + " = :"+field.getName() + "\n";
					}
				}
			}
		}
		Query tempQuery  = getCurrentSession().createQuery(sorgu);
		for(String paramName : parameterMap.keySet()){
			tempQuery.setParameter(paramName, parameterMap.get(paramName));
		}
		return tempQuery.getResultList();
	}
	
}
