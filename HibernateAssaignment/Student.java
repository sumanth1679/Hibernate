package com.ty.hibernate.dao;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
@Data
@Entity
public class Student implements Serializable {
@Id
 private int rollno;
 private String name;
 private int phno;
 private int standard;
 
}
