class Employee {
    private String empId;
    private String empName;
    private double salary;

    public Employee(String empId, String empName, double salary) {
        this.empId = empId;
        this.empName = empName;
        this.salary = salary;
    }

    public String getEmpId() {
        return empId;
    }

    public String getEmpName() {
        return empName;
    }

    public double getSalary() {
        return salary;
    }
}

class ManagerEmployee extends Employee {
    private double teamBonus;

    public ManagerEmployee(String empId, String empName, double salary, double teamBonus) {
        super(empId, empName, salary);
        this.teamBonus = teamBonus;
    }

    public double getTeamBonus() {
        return teamBonus;
    }

    public double effectiveSalary() {
        return getSalary() + teamBonus;
    }
}

class InternEmployee extends Employee {
    private double stipendCap;

    public InternEmployee(String empId, String empName, double salary, double stipendCap) {
        super(empId, empName, salary);
        this.stipendCap = stipendCap;
    }

    public double getStipendCap() {
        return stipendCap;
    }

    public double effectiveSalary() {
        return Math.min(getSalary(), stipendCap);
    }
}

public class EmployeeExtension {

    public static void printPay(Employee emp) {
        if (emp instanceof ManagerEmployee) {
            ManagerEmployee mgr = (ManagerEmployee) emp;
            System.out.printf("Manager effective pay: Rs %.1f%n", mgr.effectiveSalary());
        } else if (emp instanceof InternEmployee) {
            InternEmployee intern = (InternEmployee) emp;
            System.out.printf("Intern effective pay: Rs %.1f%n", intern.effectiveSalary());
        } else if (emp instanceof Employee) {
            System.out.printf("Plain employee pay: Rs %.1f%n", emp.getSalary());
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Week 3 Problem F2: Extending Employee Without Touching It ===");

        Employee plain = new Employee("EMP01", "Alex", 40000.0);
        ManagerEmployee manager = new ManagerEmployee("MGR01", "Divya", 70000.0, 8000.0);
        InternEmployee intern = new InternEmployee("INT01", "Meera", 12000.0, 10000.0);

        printPay(plain);
        printPay(manager);
        printPay(intern);
    }
}