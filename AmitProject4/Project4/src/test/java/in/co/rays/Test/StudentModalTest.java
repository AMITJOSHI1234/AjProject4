package in.co.rays.Test;
/**
 * Student  Model Test classes.
 * @author Amit joshi
 *
 */

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.raystec.project4.bean.CollegeBean;
import com.raystec.project4.bean.CourseBean;
import com.raystec.project4.bean.StudentBean;
import com.raystec.project4.exception.ApplicationException;
import com.raystec.project4.exception.DublicateRecordException;
import com.raystec.project4.model.CollegeModal;
import com.raystec.project4.model.CourseModel;
import com.raystec.project4.model.StudentModel;
import com.raystec.project4.util.DataUtility;

public class StudentModalTest {
public static StudentModel modal = new StudentModel();

public static void main(String[] args) throws DublicateRecordException, ParseException {
	//testAdd();
   //testDelete();
	testUpdate();
	//testFindByPK();
	//testFindByEmailId();
	//testSearch();
	//testList();
}

public static void testAdd() throws DublicateRecordException, ParseException {
	try {
		StudentBean bean = new StudentBean();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		bean.setFirstName("Shubham");
		bean.setLastName("Malvia");
		bean.setDob(sdf.parse("11/05/1999"));
		bean.setMobileNo("9407411234");
		bean.setEmail("Shubham@gmail.com");
		bean.setCollegeId(3L);
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreateDateTime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		long pk = modal.add(bean);
		
		StudentBean addbean = modal.findByPK(pk);
		if(addbean==null) {
			System.out.println("Test add fail!!!");
		}
	}catch(ApplicationException e) {
		e.printStackTrace();
	}catch(DublicateRecordException e) {
		e.printStackTrace();
	}
}
public static void testDelete() {
	try {
		StudentBean bean = new StudentBean();
		long pk = 3L;
		bean.setId(pk);
		modal.delete(bean);
		StudentBean deleteBean = modal.findByPK(pk);
		if(deleteBean!=null) {
			System.out.println("Test Delete fail");
		}
	}catch(Exception e) {
		System.out.println(e.getMessage());
	}
}
public static void testUpdate() throws DublicateRecordException {
	try {
		StudentBean bean = modal.findByPK(14L);
		bean.setEmail("om@gmail.com");
		
		CollegeModal cModal = new CollegeModal();
		CollegeBean collegebean = cModal.findByPK(bean.getCollegeId());
		System.out.println(bean.getCollegeId());
		   bean.setCollegeName(collegebean.getName());
		   bean.setId(15L);
	     modal.update(bean);
	     System.out.println("Test Update success ");	
	}catch(ApplicationException e) {
		e.printStackTrace();
	}catch(DublicateRecordException e) {
		e.printStackTrace();
	}
}

public static void testFindByPK(){
	try{
		StudentBean bean = new StudentBean();
		Long pk=2L;
		bean=modal.findByPK(pk);
		if(bean == null){
			System.out.println("Test Find By PK fail");
		}
		System.out.println(bean.getId());
		System.out.println(bean.getFirstName());
		System.out.println(bean.getLastName());
		System.out.println(bean.getDob());
		System.out.println(bean.getMobileNo());
		System.out.println(bean.getEmail());
		System.out.println(bean.getCollegeId());
	}catch(ApplicationException e){
		e.printStackTrace();
	}
}
 public static void testFindByEmailId() {
        try {
            StudentBean bean = new StudentBean();
            bean = modal.findByEmailId("ruchi@gmail.com");
            if (bean == null) {
                System.out.println("Test Find By EmailId fail");
            }
            System.out.println(bean.getId());
            System.out.println(bean.getFirstName());
            System.out.println(bean.getLastName());
            System.out.println(bean.getDob());
            System.out.println(bean.getMobileNo());
            System.out.println(bean.getEmail());
            System.out.println(bean.getCollegeId());
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }
 
 public static void testSearch() {
	 try {
		 StudentBean bean = new StudentBean();
		 List list = new ArrayList();
		 bean.setCollegeId(2L);
		 bean.setEmail("kapil30@gmail.com");
		 list = modal.search(bean, 0, 0);
		 
		 if(list.size()<0) {
			 System.out.println("Test Search fail!!!");
		 }
		 Iterator it = list.iterator();
		 
		 while(it.hasNext()) {
			 bean = (StudentBean) it.next();
			 System.out.println(bean.getId());
			 System.out.println(bean.getFirstName());
			 System.out.println(bean.getLastName());
			 System.out.println(bean.getDob());
			 System.out.println(bean.getMobileNo());
			 System.out.println(bean.getEmail());
			 System.out.println(bean.getCollegeId());
		 }
	 }catch(Exception e) {
		 e.printStackTrace();
	 }
 }
 
 public static void testList(){
	 try{
		 StudentBean bean=new StudentBean();
		 List list=new ArrayList();
		 list=modal.list(1,10);
		 
		 if(list.size() < 0){  
			 System.out.println("Test list fail");
		 }
		 Iterator it = list.iterator();
		 while(it.hasNext()){
			 bean=(StudentBean)it.next();
			 System.out.println(bean.getId());
			 System.out.println(bean.getFirstName());
			 System.out.println(bean.getLastName());
			 System.out.println(bean.getDob());
			 System.out.println(bean.getMobileNo());
			 System.out.println(bean.getEmail());
			 System.out.println(bean.getCollegeId());
			 System.out.println(bean.getCreatedBy());
			 System.out.println(bean.getModifiedBy());
			 System.out.println(bean.getCreateDateTime());
			 System.out.println(bean.getModifiedDatetime());
			 
		 }
	 }catch(ApplicationException e){
		 e.printStackTrace();
	 }
	 
 }


}
