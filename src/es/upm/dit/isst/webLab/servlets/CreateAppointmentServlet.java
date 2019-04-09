package es.upm.dit.isst.webLab.servlets;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.DateFormatter;

import org.apache.shiro.crypto.hash.Sha256Hash;

import es.upm.dit.isst.webLab.dao.DoctorDAO;
import es.upm.dit.isst.webLab.dao.DoctorDAOImplementation;
import es.upm.dit.isst.webLab.dao.PatientDAO;
import es.upm.dit.isst.webLab.dao.PatientDAOImplementation;
import es.upm.dit.isst.webLab.dao.AppointmentDAO;
import es.upm.dit.isst.webLab.dao.AppointmentDAOImplementation;
import es.upm.dit.isst.webLab.model.Doctor;
import es.upm.dit.isst.webLab.model.Patient;
import es.upm.dit.isst.webLab.model.Appointment;

@WebServlet("/CreateAppointmentServlet")
public class CreateAppointmentServlet extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer doc_id = Integer.valueOf(req.getParameter( "doc" ));
		
		System.out.println(req.getParameter("date"));

		System.out.println(req.getParameter("start_time"));
		
		Integer pat_id = Integer.valueOf(req.getParameter( "pat_id" ));
		Date date = Date.valueOf(req.getParameter( "date" ));
		//Time time = Time.valueOf(req.getParameter("start_time"));
		
		DateFormat dt = new SimpleDateFormat("HH:mm:ss");
		
		
		try {
			System.out.println(dt.parse(req.getParameter("start_time")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DoctorDAO ddao = DoctorDAOImplementation.getInstance();
		Doctor doctor = ddao.read(doc_id);

		PatientDAO pdao = PatientDAOImplementation.getInstance();
		Patient patient = pdao.read(pat_id);
		
		Appointment app = new Appointment();
		app.setApp_doctor(doctor);
		app.setApp_patient(patient);
		app.setDate(date);
		//app.setStart_time(time);
		
		AppointmentDAO adao = AppointmentDAOImplementation.getInstance();
		adao.create( app );
		
		resp.sendRedirect( req.getContextPath() + "/LoginServlet" );
	}
}