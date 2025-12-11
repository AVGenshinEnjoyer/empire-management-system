package services;

import models.Employee;
import storage.DataStorage;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {

    public static void addEmployee(Employee employee) {
        employee.setId(DataStorage.getNextEmployeeId());
        DataStorage.employees.add(employee);
    }

    public static List<Employee> getAllEmployees() {
        return new ArrayList<>(DataStorage.employees);
    }

    public static void deleteEmployee(int employeeId) {
        DataStorage.employees.removeIf(e -> e.getId() == employeeId);
    }
}