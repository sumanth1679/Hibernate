package com.ty.hibernate.dao;

public class InvalidRollNo extends Throwable {
	InvalidRollNo(String str)
	{
		super(str);
	}
}
