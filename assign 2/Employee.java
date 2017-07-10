import java.util.*;
class Employee
{
	String empID,empName,deptID,bloodGroup;
	double salary;
	
	public void setEmployeeDetails(String empID,String empName,String deptID,String bloodGroup,double salary)
	{
		this.empID=empID;
		this.empName=empName;
		this.deptID=deptID;
		this.bloodGroup=bloodGroup;
		this.salary=salary;
	}
	public void printEmployeeDetails()
	{
		System.out.println("EmpId : "+this.empID+"\nEmployee Name : "+this.empName+"\nDepartment ID : "+this.deptID+"\nBlood Group : "+this.bloodGroup+"\nSalary: "+this.salary);
	}
	public static void main(String args[])
	{
		
		Scanner sc=new Scanner(System.in);
		
		System.out.println("Enter empID");
		String empID=sc.nextLine();
		System.out.println("Enter empName");
		String empName=sc.nextLine();
		System.out.println("Enter deptID");
		String deptID=sc.nextLine();
		System.out.println("Enter Blood Group");
		String bloodGroup=sc.nextLine();
		System.out.println("Enter salary");
		double salary=sc.nextDouble();
		
		Employee e=new Employee();
		
		e.setEmployeeDetails(empID,empName,deptID,bloodGroup,salary);
		e.printEmployeeDetails();
		
	}
	
}









