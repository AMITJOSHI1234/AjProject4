package in.co.rays.Test;
/**
 * TimeTable Model Test classes.
 * 
 * @author Amit Joshi
 *
 */

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.raystec.project4.bean.TimeTableBean;
import com.raystec.project4.exception.ApplicationException;
import com.raystec.project4.exception.DublicateRecordException;
import com.raystec.project4.model.TimeTableModel;

public class TimeTableModelTest {

	static TimeTableModel model = new TimeTableModel();
	public static void main(String[] args) throws Exception {
	           //testadd();
				//testdelete();
				//testupdate();
				//testfindBypk();
				//testlist();
				//testsearch();
		       //testcheckbycds();
		       //testcheckbycss();
	}
	

	public static void testadd() {
		try {
			TimeTableBean bean = new TimeTableBean();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			//bean.setId(1);
			bean.setCourseId(2);
			bean.setCourseName("MSC");
			bean.setSubjectId(1);
			bean.setSubjectName("JavaScript");
			bean.setSemester("4");
			bean.setExamDate(sdf.parse("21/11/2022"));
			bean.setExamTime("10 am to 1 pm");
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreateDateTime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			model.add(bean);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void testdelete() {
		try {
			TimeTableBean bean = new TimeTableBean();
			long pk=3L;
			
			bean.setId(pk);
			 model.delete(bean);
			 
		}catch(ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testupdate() throws ParseException, DublicateRecordException {
		try {
			TimeTableBean bean = new TimeTableBean();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
			bean.setId(1L);
			
			bean.setCourseId(2);
			bean.setCourseName("MCA");
			bean.setSubjectId(1);
			bean.setSubjectName("Angular");
			bean.setExamTime("1 to 5 pm");
			bean.setExamDate(sdf.parse("22/08/2021"));
			bean.setSemester("5");
			model.update(bean);;
		}catch(ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testfindBypk() {
		try {
			TimeTableBean bean = new TimeTableBean();
			
			bean=model.findByPK(2);
			System.out.println(bean.getCourseId());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getSubjectId());
			System.out.println(bean.getSubjectName());
			System.out.println(bean.getSemester());
			System.out.println(bean.getExamDate());
			System.out.println(bean.getExamTime());
			
			
		}catch(ApplicationException e) {
			e.printStackTrace();
		}
	}
	public static void testlist() throws Exception {
		try {
		TimeTableBean bean = new TimeTableBean();
		List list= new ArrayList();
		list = model.list(0,3);
		
		Iterator it = list.iterator();
		while(it.hasNext()) {
			bean = (TimeTableBean) it.next();
			System.out.println(bean.getCourseId());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getSubjectId());
			System.out.println(bean.getSubjectName());
			System.out.println(bean.getSemester());
			System.out.println(bean.getExamDate());
			System.out.println(bean.getExamTime());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
		
		}
		}catch(ApplicationException e) {
			e.printStackTrace();
		}
	}
	public static void testsearch() throws ApplicationException {
		TimeTableBean bean = new TimeTableBean();
		List list = new ArrayList();
		bean.setSubjectName("java");
		list=model.search(bean,0,0);
		if(list.size() < 0) {
			System.out.println("test search fail");
		}
		
		Iterator it = list.iterator();
		while(it.hasNext()) {
			bean=(TimeTableBean) it.next();
			
			System.out.println(bean.getId());
			System.out.println(bean.getCourseId());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getSubjectId());
			System.out.println(bean.getSubjectName());
			System.out.println(bean.getSemester());
			System.out.println(bean.getExamDate());
			System.out.println(bean.getExamTime());
			System.out.println(bean.getDescription());
		}
	}
	
	public static void testcheckbycds() throws ParseException {
		TimeTableBean bean = new TimeTableBean();
		SimpleDateFormat sdf  = new SimpleDateFormat("dd/MM/yyyy");
		bean.setCourseId(1);
		bean.setSemester("5");
		//bean.setExamDate(sdf.parse("22/08/2021"));
		Date ExamDate = sdf.parse("22/08/2021");
		try {
		bean = 	model.checkBycds(bean.getCourseId(), bean.getSemester(),  ExamDate);
		bean.getCourseName();
		bean.getSubjectId();
		bean.getSubjectName();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public static void testcheckbycss() {
		TimeTableBean bean = new TimeTableBean();
		bean.setCourseId(2L);
		bean.setSemester("5");
		bean.setSubjectId(2L);
		
		try {
			TimeTableBean getBean = new TimeTableBean();
			getBean = model.checkBycss(bean.getCourseId(), bean.getSubjectId(),bean.getSemester());
			if(getBean==null) {
				System.out.println("Test Fail!!!!");
			}else {
				System.out.println(getBean.getCourseName());
				System.out.println(getBean.getExamDate());
				System.out.println(getBean.getSubjectName());
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	

}
