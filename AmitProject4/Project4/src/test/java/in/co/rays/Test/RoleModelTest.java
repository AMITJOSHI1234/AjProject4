package in.co.rays.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.raystec.project4.bean.RoleBean;
import com.raystec.project4.exception.ApplicationException;
import com.raystec.project4.exception.DublicateRecordException;
import com.raystec.project4.model.RoleModel;

/**
 * Role Model Test classes.
 * 
 * @author Amit Joshi
 *
 */
public class RoleModelTest {

	public static RoleModel model = new RoleModel();
	
	public static void main(String[] args) throws ParseException, ApplicationException {
		//testAdd();
		//testDelete();
		//testUpdate();
		//testFindByPK();
		//testFindByName();
		//testSearch();
		//testList();
	}
	
	public static void testAdd() throws ParseException {
		
		try{
			RoleBean bean=new RoleBean();
			bean.setName("APJ");
			bean.setDescription("admin");
			bean.setCreatedBy("Amit");
			 bean.setModifiedBy("Amit");
			bean.setCreateDateTime(new Timestamp(new Date().getTime()));
		   
		    bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			long pk=model.add(bean);
			System.out.println("successfully add");
			RoleBean addedbean=model.findByPK(pk);
			if(addedbean==null){
				System.out.println("Test Add fail");
			}
		}catch(ApplicationException e){
			e.printStackTrace();
		}catch(DublicateRecordException e){
			e.printStackTrace();
		}
	}
	public static void testDelete() throws ApplicationException{
		
		RoleBean bean = new RoleBean();
		
		bean.setId(3l);
		
		model.delete(bean);
		
		System.out.println("record deleted");
		
	}
	
	public static void testUpdate(){
		try{
			RoleBean bean=model.findByPK(2L);
			bean.setName("piyush");
			bean.setDescription("ajjjjddj");
			model.update(bean);
			
			RoleBean updatedbean=model.findByPK(2L);
			
			if(!"piyush".equals(updatedbean.getName())){
				System.out.println("Test Update fail");
			}
			
		}catch(ApplicationException e){
			e.printStackTrace();
		}catch(DublicateRecordException e){
			e.printStackTrace();
		}
	}
	
	public static void testFindByPK(){
		try{
		RoleBean bean =new RoleBean();
		long pk=2L;
		bean=model.findByPK(pk);
		if(bean==null){
			System.out.println("Test Find By PK fill");
		}
		System.out.println(bean.getId());
		System.out.println(bean.getName());
		System.out.println(bean.getDescription());
		
		}catch(ApplicationException e){
			e.printStackTrace();
		}	
	}
	public static void testFindByName(){
		try{
			RoleBean bean=new RoleBean();
			bean=model.findByName("piyush");
			if(bean==null){
				System.out.println("Test Find By Name fill");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
		}catch(ApplicationException e){
			e.printStackTrace();
		}
	}
	
	public static void testSearch(){
		try{
			RoleBean bean=new RoleBean();
			List list=new ArrayList();
			bean.setName("piyush");
			list=model.search(bean,0,0);
			if(list.size() < 0){
				System.out.println("test Search fail");
			}
			Iterator it = list.iterator();
			
			while(it.hasNext()){
				bean=(RoleBean)it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
			}
			
		}catch(ApplicationException e){
			e.printStackTrace();
		}
	}
	
	public static void testList(){
		try{
			
			RoleBean bean=new RoleBean();
			List list=new ArrayList();
			list=model.list(1,10);
			if(list.size()<0){
				System.out.println("test List faill");
			}
			Iterator it=list.iterator();
			while(it.hasNext()){
				bean=(RoleBean)it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
			}
		}catch(ApplicationException e){
			e.printStackTrace();
		}
	}

	
}
