package in.co.rays.Test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.raystec.project4.bean.CollegeBean;
import com.raystec.project4.bean.CourseBean;
import com.raystec.project4.bean.FacultyBean;
import com.raystec.project4.bean.SubjectBean;
import com.raystec.project4.exception.ApplicationException;
import com.raystec.project4.exception.DublicateRecordException;
import com.raystec.project4.model.CollegeModal;
import com.raystec.project4.model.CourseModel;
import com.raystec.project4.model.FacultyModel;
import com.raystec.project4.model.SubjectModel;

/**
 * Faculty  Model Test classes.
 * 
 * @author Amit Joshi
 *
 */

public class FacultyModelTest {

	public static FacultyModel model = new FacultyModel();
	
	public static void main(String[] args) throws DublicateRecordException {
		//testadd();
		//testDelete();
	testUpdate();
		//testFindByPk();
		//testFindByEmailId();
		//testList();
		//testsearch();
	}
	
public static void testadd() throws DublicateRecordException {
		
		try {
			FacultyBean bean = new FacultyBean();
			SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
			//bean.setId(1);
			System.out.println("2222222222");
			bean.setFirstName("Aniket");
			bean.setLastName("Sharma");
			bean.setGender("male");
			bean.setEmailId("aniket@gmail.com");
			bean.setMobileNo("9087654778");
			bean.setCollegeId(2);
			bean.setCollegeName("MLB");
			bean.setCourseId(3);
			bean.setCourseName("MBA");
			bean.setDob(sdf.parse("06/10/1997"));
			bean.setQualification("BCA");
			bean.setSubjectId(2);
			bean.setSubjectName("DBMS");
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreateDateTime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			
			if(bean.getCollegeName()=="MLB")
			{
			
			long pk=model.add(bean);
			System.out.println("success");
			}else {
				System.out.println("Not success!!");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
public static void testDelete(){
	
	try{
		FacultyBean bean=new FacultyBean();
		long pk=3L;
		
		bean.setId(pk);
		model.delete(bean);
		FacultyBean deletebean = model.findByPK(pk);
		if(deletebean != null){
			System.out.println("Test Delete fail");
		}
	}catch(ApplicationException e){
		e.printStackTrace();
	}
}
public static void testUpdate() {
	try {
		FacultyBean bean = model.findByPK(16L);
		//CollegeModal collegeModel = new CollegeModal();
		//CollegeBean collegeBean = collegeModel.findByPK(bean.getCollegeId());
		//bean.setCollegeName(collegeBean.getName());
		
		
		//bean.setFirstName("akash");
		//bean.setLastName("Gupta");
		//bean.setId(1L);
		//bean.setCollegeName("Maan Singh Colllege");
		//bean.setCourseName("B.E");
		//bean.setSubjectName("Data Structure");
		
//		       CourseModel courseModal = new CourseModel();
//				CourseBean courseBean = courseModal.FindByPK(bean.getCourseId());
//				bean.setCourseName(courseBean.getName());
				
//				SubjectModel subjectModel = new SubjectModel();
//				SubjectBean subjectBean = subjectModel.FindByPK(bean.getSubjectId());
//				bean.setSubjectName(subjectBean.getSubjectName());
		CollegeModal collegeModel = new CollegeModal();
		CollegeBean collegeBean = collegeModel.findByPK(bean.getCollegeId());
		//System.out.println(collegeBean.getName());
		bean.setCollegeName(collegeBean.getName());
		
		bean.setId(16L);
		model.update(bean);
		System.out.println("update succ");
		
			/*
			 * FacultyBean updateBean=model.findByPK(2L);
			 * if(!"ram".equals(updateBean.getFirstName())){
			 * System.out.println("test update fail"); }
			 */
		 
		 
	}catch(ApplicationException e) {
		e.printStackTrace();
	}catch(DublicateRecordException e) {
		e.printStackTrace();
	}
}
public static void testFindByPk() {
	try {
		FacultyBean bean=new FacultyBean();
		long pk=1L;
		bean=model.findByPK(pk);
		if(bean==null) {
			System.out.println("test findbypk fail");
		}
		System.out.println(bean.getId());
		System.out.println(bean.getFirstName());
		System.out.println(bean.getLastName());
		System.out.println(bean.getGender());
		System.out.println(bean.getEmailId());
		System.out.println(bean.getMobileNo());
		System.out.println(bean.getCreatedBy());
		System.out.println(bean.getModifiedBy());
		System.out.println(bean.getCreateDateTime());
		System.out.println(bean.getModifiedDatetime());
	}catch(ApplicationException e) {
		e.printStackTrace();
	}
}
public static void testFindByEmailId() {
    try {
        FacultyBean bean = new FacultyBean();
        bean = model.findByEmailId("rohan@gmail.com");
        if (bean == null) {
            System.out.println("Test Find By EmailId fail");
        }
        System.out.println(bean.getId());
		System.out.println(bean.getFirstName());
		System.out.println(bean.getLastName());
		System.out.println(bean.getGender());
		System.out.println(bean.getEmailId());
		System.out.println(bean.getMobileNo());
		System.out.println(bean.getCreatedBy());
		System.out.println(bean.getModifiedBy());
		System.out.println(bean.getCreateDateTime());
		System.out.println(bean.getModifiedDatetime());
    } catch (ApplicationException e) {
        e.printStackTrace();
    }
}

public static void testList(){
	 try{
		FacultyBean bean = new FacultyBean();
		 List list=new ArrayList();
		 list=model.list(1,10);
		 
		 if(list.size() < 0){
			 System.out.println("Test list fail");
		 }
		 Iterator it = list.iterator();
		 while(it.hasNext()){
			 bean=(FacultyBean) it.next();
			 System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getGender());
				System.out.println(bean.getEmailId());
				System.out.println(bean.getMobileNo());
				System.out.println(bean.getCollegeId());
				System.out.println(bean.getCollegeName());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getCreateDateTime());
				System.out.println(bean.getModifiedDatetime());
			 
		 }
	 }catch(ApplicationException e){
		 e.printStackTrace();
	 }
	 
}

public static void testsearch() {
	try {
		FacultyBean bean = new FacultyBean();
		List list = new ArrayList();
		bean.setFirstName("Mohit");
		list=model.search(bean);
		
		Iterator it = list.iterator();
		while(it.hasNext()) {
			bean= (FacultyBean) it.next();
			
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getGender());
			System.out.println(bean.getEmailId());
			System.out.println(bean.getMobileNo());
			
		}
	}catch(ApplicationException e) {
		e.printStackTrace();
	}
}

}
