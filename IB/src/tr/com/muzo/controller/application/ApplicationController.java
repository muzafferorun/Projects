package tr.com.muzo.controller.application;

import java.io.Serializable;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.sun.javafx.collections.ObservableListWrapper;

import javafx.collections.ObservableList;
import tr.com.muzo.enums.EvetHayirEnum;

@Controller
@Scope("singleton")
@SuppressWarnings("restriction")
public class ApplicationController implements Serializable{

	private static final long serialVersionUID = 6175934392248456831L;
	
	private ObservableList<EvetHayirEnum> evetHayirList;

	@PostConstruct
	public void init(){
		evetHayirList = new ObservableListWrapper<EvetHayirEnum>(Arrays.asList(EvetHayirEnum.values()));
	}
	
	public ObservableList<EvetHayirEnum> getEvetHayirList() {
		return evetHayirList;
	}

}
