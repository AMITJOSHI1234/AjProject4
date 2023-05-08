package in.co.rays.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.raystec.project4.bean.CourseBean;
import com.raystec.project4.exception.ApplicationException;
import com.raystec.project4.exception.DatabaseException;
import com.raystec.project4.exception.DublicateRecordException;
import com.raystec.project4.model.CourseModel;

/**
 * Course Model Test classes.
 * @author Amit Joshi
 *
 */


public class CourseModelTest {

	public static CourseModel model = new CourseModel();
	public static void main(String[] args) throws Exception {
		        //testadd();
				//testDelete();
				//testFindByName();
				testFindByPk();
				//testUpdate();
				//testSearch();
				//testList();
          
	}
	
public static void testadd() throws DublicateRecordException {
		
		try {
			CourseBean bean= new CourseBean();
			bean.setName("SSC");
			bean.setDescription("Government");
			bean.setDuration("1 Year");
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreateDateTime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			
			long pk=model.add(bean);
			System.out.println(bean.getId());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

public static void testDelete() {
	try {
		long pk = 3;
		CourseBean bean = new CourseBean();
		bean.setId(3);
		model.Delete(bean);
		System.out.println("Test Deleted");
		
		  CourseBean deleteBean=model.FindByPK(pk); 
		  if(deleteBean != null) {
		  System.out.println("Test Delete fail"); }
		 
	}catch(ApplicationException e) {
		e.printStackTrace();
	}
}
public static void testFindByName() {
	try {
		CourseBean bean=new CourseBean();
		bean=model.findByName("MBA");
		if(bean==null) {
			System.out.println("test findBy Name fail");
		}
	
		System.out.println(bean.getId());
		System.out.println(bean.getName());
		System.out.println(bean.getDescription());
		System.out.println(bean.getDuration());
		System.out.println(bean.getCreatedBy());
		System.out.println(bean.getCreateDateTime());
		System.out.println(bean.getModifiedBy());
		System.out.println(bean.getModifiedDatetime());
		
	}catch(ApplicationException e) {
		e.printStackTrace();
	}
	
		}

public static void testFindByPk() {
	try {
		CourseBean bean=new CourseBean();
		long pk=20L;
		bean=model.FindByPK(pk);
		if(bean==null) {
			System.out.println("test findbypk fail");
		}
		System.out.println(bean.getId());
		System.out.println(bean.getName());
		System.out.println(bean.getDescription());
		System.out.println(bean.getDuration());
		System.out.println(bean.getCreatedBy());
		System.out.println(bean.getModifiedBy());
		System.out.println(bean.getCreateDateTime());
		System.out.println(bean.getModifiedDatetime());
	}catch(ApplicationException e) {
		e.printStackTrace();
	}
	
}
public static void testUpdate() {
	try {
		CourseBean bean = model.FindByPK(23L);
		if(bean.getId() == 23L ) {
		bean.setName("SSB");
		bean.setDescription("Collector and forest");
		bean.setDuration("5");
		bean.setId(23L);
		model.update(bean);
		System.out.println("update succ");
		}else {
			System.out.println("Update not successfull!!!");
		}
		/*
		 * CourseBean updateBean=model.testFindByPK(2L); if(!
		 * "ram".equals(updateBean.getName())){ System.out.println("test update fail");
		 * }
		 */
		 
	}catch(ApplicationException e) {
		e.printStackTrace();
	}catch(DublicateRecordException e) {
		e.printStackTrace();
	}
}

public static void testSearch() throws DatabaseException {
	try {
		CourseBean bean = new CourseBean();
		List list = new ArrayList();
		bean.setId(1L);
		bean.setName("Mca");
		list=model.search(bean);
		
		
		Iterator it=list.iterator();
		while(it.hasNext()) {
			bean = (CourseBean) it.next();
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getDuration());
		}
	}catch(ApplicationException e) {
		e.printStackTrace();
	}
}

public static void testList() throws Exception {
	try {
		CourseBean bean = new CourseBean();
		  List list = new ArrayList();
		  list =model.list(1,10);
	  if(list.size() < 0) { 
		  System.out.println("test list fail");
		  } 
	  Iterator it=list.iterator();
	  while(it.hasNext()) {
		  bean=(CourseBean) it.next();
	  System.out.println(bean.getName());
	  System.out.println(bean.getDescription());
	  System.out.println(bean.getDuration());
	  
	  }
	  
	  }catch(ApplicationException e) {
		  e.printStackTrace(); 
		  } 
	
	}
}
