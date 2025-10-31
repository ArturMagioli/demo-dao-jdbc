package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {
    
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);

        // testellerDao(sc);

        testDepartmentDao();

        sc.close();
    }

    private static void testSellerDao(Scanner sc) {
        SellerDao sellerDao = DaoFactory.createSellerDao();
        Department department = new Department(2, null);

        System.out.println("=== TEST 1: seller findById ====");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("\n=== TEST 2: seller findByDepartment ====");
        List<Seller> sellers = sellerDao.findByDepartment(department);
        for (Seller s : sellers) {
            System.out.println(s);
        }

        System.out.println("\n=== TEST 3: seller findAll ====");
        sellers = sellerDao.findAll();
        for (Seller s : sellers) {
            System.out.println(s);
        }

        System.out.println("\n=== TEST 4: seller insert ====");
        Seller seller2 = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, department);
        sellerDao.insert(seller2);
        System.out.println("Inserted! New id = " + seller2.getId());

        System.out.println("\n=== TEST 5: seller update ====");
        seller = sellerDao.findById(1);
        seller.setName("Martha Waine");
        sellerDao.update(seller);
        System.out.println("Update completed!");

        System.out.println("\n=== TEST 6: seller delete ====");
        System.out.print("Enter id for delete test: ");
        int id = sc.nextInt();
        sellerDao.deleteById(id);
        System.out.println("Delete completed!");
    }

    private static void testDepartmentDao() {
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
        Department department = new Department(8, "Balls");
        
        System.out.println("=== TEST 1: department insert ====");
        departmentDao.insert(department);
        System.out.println("Insert completed!");

        System.out.println("=== TEST 2: department update ====");
        department.setName("Stick Nation");
        departmentDao.update(department);
        System.out.println("update completed!");

        System.out.println("=== TEST 3: department delete ====");
        departmentDao.deleteById(8);
        System.out.println("delete completed!");

        System.out.println("=== TEST 4: department findById ====");
        Department dep = departmentDao.findById(7);
        System.out.println("Result: " + dep);
    }
}
