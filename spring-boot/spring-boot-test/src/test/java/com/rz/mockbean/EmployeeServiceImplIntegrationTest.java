package com.rz.mockbean;

import com.rz.model.Employee;
import com.rz.repository.EmployeeRepository;
import com.rz.service.EmployeeService;
import com.rz.service.EmployeeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class EmployeeServiceImplIntegrationTest {

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
        @Bean
        public EmployeeService employeeService() {
            return new EmployeeServiceImpl();
        }
    }

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Before
    public void setUp() {
        Employee given = new Employee("rik");

        Mockito
                .when(employeeRepository.findByName(given.getName()))
                .thenReturn(given);
    }

    @Test
    public void whenValidName_thenEmployeeShouldBeFound() {
        // given
        String given = "rik";

        // when
        Employee result = employeeService.getEmployeeByName(given);

        // then
        assertThat(result.getName()).isEqualTo(given);
    }


}
