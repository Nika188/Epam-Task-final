package com.epam.rd.tasks.sqlqueries;

/**
 * Implement sql queries like described
 */
public class SqlQueries {
    //Select all employees sorted by last name in ascending order
    //language=HSQLDB
    String select01 = "select * from employee order by lastname";

    //Select employees having no more than 5 characters in last name sorted by last name in ascending order
    //language=HSQLDB
    String select02 = "select * from employee where length(lastname)<=5 order by lastname";

    //Select employees having salary no less than 2000 and no more than 3000
    //language=HSQLDB
    String select03 = "select * from employee where salary>=2000 and salary <=3000";

    //Select employees having salary no more than 2000 or no less than 3000
    //language=HSQLDB
    String select04 = "select * from employee where salary<=2000 or salary >=3000";

    //Select all employees assigned to departments and corresponding department
    //language=HSQLDB
    String select05 = "select E.*, D.name from employee E join department D on E.department = D.id";

    //Select all employees and corresponding department name if there is one.
    //Name column containing name of the department "depname".
    //language=HSQLDB
    String select06 = "select E.*, D.name as depname from employee E left join department D on E.department = D.id";

    //Select total salary pf all employees. Name it "total".
    //language=HSQLDB
    String select07 = "select sum(salary) as total from employee";

    //Select all departments and amount of employees assigned per department
    //Name column containing name of the department "depname".
    //Name column containing employee amount "staff_size".
    //language=HSQLDB
    String select08 = "select D.name as depname, count(E.id) as staff_size from employee E join department D on E.department = D.id group by D.id, D.name";

    //Select all departments and values of total and average salary per department
    //Name column containing name of the department "depname".
    //language=HSQLDB
    String select09 = "select D.name as depname, sum(salary) as total, avg(E.salary) as average from employee E join department D on E.department = D.id group by D.id, D.name";

    //Select lastnames of all employees and lastnames of their managers if an employee has a manager.
    //Name column containing employee's lastname "employee".
    //Name column containing manager's lastname "manager".
    //language=HSQLDB
    String select10 = "select E.lastname as employee, M.lastname as manager from employee E left join employee M on E.manager=M.id";


}
