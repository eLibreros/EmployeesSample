package controllers;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import models.Employee;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.employees.*;

public class EmployeesController extends Controller {
	
	static Set<Employee> emps = new HashSet<>();
	
	static {
		emps.add(new Employee(1, "Erick", "Libreros", "Developer", 1.0, new Date()));
		emps.add(new Employee(2, "Juanito", "Perez", "Scrum Master", 2.0, new Date()));
	}
	
	@Inject
	FormFactory formFactory;
	
	public Result index() {
		return ok(index.render(emps));
	}
	
	public Result create() {
		Form<Employee> employeeForm = formFactory.form(Employee.class);
		return ok(create.render(employeeForm));
	}
	
	public Result save() {
		Form<Employee> employeeForm = formFactory.form(Employee.class).bindFromRequest();
		Employee emp = employeeForm.get();
		emps.add(emp);
		return redirect(routes.EmployeesController.index());
	}
	
	public Result edit(Integer id) {
		Employee emp = null;
		for (Employee e : emps) {
			if (e.getId().equals(id)) {
				emp = e;
				break;
			}
		}
		
		if (emp == null)
			return notFound("Employee not found....");
		
		Form<Employee> employeeForm = formFactory.form(Employee.class).fill(emp);
		return ok(edit.render(employeeForm));
	}
	
	public Result update() {
		Form<Employee> employeeForm = formFactory.form(Employee.class).bindFromRequest();
		Employee emp = employeeForm.get();
		Employee oldEmp = null;
		
		for (Employee e : emps) {
			if (e.getId().equals(emp.getId())) {
				oldEmp = e;
				break;
			}
		}
		
		if (oldEmp == null)
			return notFound("Employee not found....");
		
		oldEmp.setFirstName(emp.getFirstName());
		oldEmp.setLastName(emp.getLastName());
		oldEmp.setJob(emp.getJob());
		oldEmp.setSalary(emp.getSalary());
		oldEmp.setStartDate(emp.getStartDate());
		return redirect(routes.EmployeesController.index());		
	}
	
	public Result delete(Integer id) {
		Employee emp = null;
		for (Employee e : emps) {
			if (e.getId().equals(id)) {
				emp = e;
				break;
			}
		}
		
		if (emp == null)
			return notFound("Employee not found....");
		
		emps.remove(emp);
		return redirect(routes.EmployeesController.index());	
	}
	

}
