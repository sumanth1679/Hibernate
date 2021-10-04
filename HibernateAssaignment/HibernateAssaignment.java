package com.ty.hibernate.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.Transaction;

import com.mysql.jdbc.Statement;
import com.ty.hibernate.demo.Employee;

public class HibernateAssaignment {
	static Scanner sc=new Scanner(System.in); 
	public static void displayAll() {
		EntityManagerFactory factory=null;
		EntityManager manager=null;
		try {
			factory=Persistence.createEntityManagerFactory("emp");
			manager=factory.createEntityManager();
			String findall="from Student";
			Query query=manager.createQuery(findall);
			//student students=(student)query.getSingleResult();
			List<Student> list=query.getResultList();
			System.out.println(list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			factory.close();
			manager.close();
		}
	}
	public static boolean idVerification(int rn) {
		EntityManagerFactory factory=null;
		EntityManager manager=null;
		Student students=null;
		try {
			factory=Persistence.createEntityManagerFactory("emp");
			manager=factory.createEntityManager();
			students=manager.find(Student.class, rn);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(factory!=null)
				factory.close();
			if(manager!=null)
				manager.close();
			if(students!=null)
				return true;
			else
				return false;
		}
	}
	public static void displayById(int rno)
	{
		boolean check=idVerification(rno); 
		if(check==false)
		{
			try {
				throw new InvalidRollNo("Invalid roll number");
			} catch (InvalidRollNo e) {
				System.out.println(e.getMessage());
			}
		}
		else
		{
			EntityManagerFactory factory=null;
			EntityManager manager=null;
			EntityTransaction transaction=null;
			try {
				factory=Persistence.createEntityManagerFactory("emp");
				manager=factory.createEntityManager();
				transaction=manager.getTransaction();
				Student student1=manager.getReference(Student.class, rno);
				System.out.println(student1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if(factory!=null)
					factory.close();
				if(manager!=null)
					manager.close();
			}
		}
	}
	public  static void updateData(int roll) {
		boolean check=idVerification(roll);
		if(check==false)
		{
			try {
				throw new InvalidRollNo("Invalid roll number");
			} catch (InvalidRollNo e) {
				System.out.println(e.getMessage());
			}
		}
		else
		{
			EntityManagerFactory factory=null;
			EntityManager manager=null;
			EntityTransaction transaction=null;
			int count=0;
			try {
				factory=Persistence.createEntityManagerFactory("emp");
				manager=factory.createEntityManager();
				transaction=manager.getTransaction();
				Student student2=manager.getReference(Student.class, roll);
				char ch;
				transaction.begin();
				if(student2!=null)
				{
					System.out.println("Do you want to update the name (y/n)");
					ch=sc.next().charAt(0);
					if(ch=='y')
					{
						System.out.println("Enter name");
						String name=sc.next();
						student2.setName(name);
						count++;
					}
					if(ch=='n' || ch!='n')
					{
						System.out.println("Do you want to update the phone number (y/n)");
						char ch1=sc.next().charAt(0);
						if(ch1=='y')
						{
							System.out.println("Enter Phno");
							int phno=sc.nextInt();
							student2.setPhno(phno);
							count++;
						}
					}
				}
				transaction.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if(factory!=null)
					factory.close();
				if(manager!=null)
					manager.close();
				if(count>0)
				  System.out.println("updated successfully");
			  else
				  System.out.println("not updated");
			}
		}
	}
	public static void delete(int rno) {
		boolean check=idVerification(rno);
		if(check==false)
		{
			try {
				throw new InvalidRollNo("Invalid roll number");
			} catch (InvalidRollNo e) {
				System.out.println(e.getMessage());
			}
		}
		else
		{
			EntityManagerFactory factory=null;
			EntityManager manager=null;
			EntityTransaction transaction=null;
			int count=0;
			try {
				factory=Persistence.createEntityManagerFactory("emp");
				manager=factory.createEntityManager();
				transaction=manager.getTransaction();
				Student student=manager.getReference(Student.class, rno);
				
				transaction.begin();
				manager.remove(student);
				transaction.commit();
				count++;
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if(manager!=null)
					manager.close();
				if(factory!=null)
					factory.close();
				if(count>0)
					System.out.println("deleted successfully");
				else
					System.out.println("unable to delete");
			}
		}
	}
	
	public static void main(String[] args) {
		boolean exit=false;
		Scanner sc=new Scanner(System.in);
		Student student1=new Student();
		Statement statement=null;
			do {
			System.out.println("Press 1 to see all data");
			System.out.println("Press 2 to see any particular data");
			System.out.println("Press 3 to update any particular data");
			System.out.println("Press 4 to delete data");
			System.out.println("Enter 0 to exit");
			int choice=sc.nextInt();
			switch (choice) {
			case 1:displayAll();
				break;
			case 2:System.out.println("Enter rollno");
				   int rollno=sc.nextInt();
				   displayById(rollno);
				 break;
			case 3:System.out.println("Enter roll no you wish to update");
				  int roll=sc.nextInt();
				  updateData(roll);
				  break;
			case 4:System.out.println("Enter roll no you want to delete");
				   int rno=sc.nextInt();
				   delete(rno);
				   break;
			case 0: exit=true;
				    break;
			default:System.out.println("Invalid choice");
				break;
			}
		} while (!exit);

	}
}	
