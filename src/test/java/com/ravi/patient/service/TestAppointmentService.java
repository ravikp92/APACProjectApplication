package com.ravi.patient.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ravi.patient.app.dao.AppointmentDAO;
import com.ravi.patient.app.model.Physician;
import com.ravi.patient.app.service.AppointmentService;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class TestAppointmentService {

	//We have annotated PatientService class with @InjectMocks, 
	//so mockito will create the mock object for PatientService class and inject the mock dependency of PatientDAO into it.
    @InjectMocks
    AppointmentService appointmentService;
    
    @Mock
    AppointmentDAO appDao;
    
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testGetAllPhysicians() throws ParseException {
    	List<Physician> list=new ArrayList<>();
    	list.add(new Physician(1, "Ravi_Puri"));
    	list.add(new Physician(2,"Rahul"));
    	list.add(new Physician(3,"Vijay"));
    	list.add(new Physician(4,"Sanjana"));
    	when(appointmentService.getAllPhysicians()).thenReturn(list);
    	assertEquals(4, appointmentService.getAllPhysicians().size());
    }
    
}
