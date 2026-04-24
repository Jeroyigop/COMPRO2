enum EmployeeType {
    FULL_TIME,
    PART_TIME,
    CONTRACTOR
}
public abstract class Employee {
    private String name;
    private String employeeId;
    protected EmployeeType type;


        public Employee(String name, String employeeId, EmployeeType type) {
        this.name = name;
        this.employeeId = employeeId;
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public String getEmployeeId() {
        return employeeId;
    }
    public EmployeeType getType() {
        return type;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    public void setType(EmployeeType type) {
        this.type = type;
    }

    public abstract double calculateEarnings();

    @Override
    public String toString() {
        return "Name: " + name +
               ", ID: " + employeeId +
               ", Type: " + type;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;
        return employeeId != null && employeeId.equals(employee.employeeId);
    }


}


