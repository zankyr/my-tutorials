package com.rz.datajpa;

import com.rz.model.Employee;
import com.rz.repository.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void whenFindByCatena_therReturnEmployee(){
        // given
        Employee given = new Employee("Rik");
        testEntityManager.persist(given);
        testEntityManager.flush();

        // when
        Employee result = employeeRepository.findByName("Rik");

        // then
        assertThat(result.getName()).isEqualTo(given.getName());
    }
}
