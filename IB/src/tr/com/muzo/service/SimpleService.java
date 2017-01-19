package tr.com.muzo.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface SimpleService {
	
	@Transactional
	@SuppressWarnings("rawtypes")
	public List listele(Object aramaKriterClass);
	
	@Transactional
	public void kaydet(Object obje);
	
}
