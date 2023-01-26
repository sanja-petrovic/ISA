package com.example.isa;

import com.example.isa.model.Appointment;
import com.example.isa.service.interfaces.AppointmentService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SpringBootTest
class ApplicationTests {

	@Test
	void contextLoads() {
	}

}
