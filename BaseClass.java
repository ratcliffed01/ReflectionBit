// to compile - C:\JobTests\MSS\com\mssint\concept>javac -cp ../  MainClass.java MainClass2.java BaseClass.java
// to execute C:\JobTests\MSS\com\mssint\concept>java -cp ../  com.mssint.concept.MainClass
// to execute C:\JobTests\MSS\com\mssint\concept>java -cp ../  com.mssint.concept.MainClass2

//package com.mssint.concept;
package MSS;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BaseClass {
	public void invokeMethod(Field f,Object ci){
		String subName = f.getName();
		try {
			Method m = ci.getClass().getDeclaredMethod(subName);
			f.set(ci,m);
			Method value = (Method) m.invoke(ci);

			while (value != null){
				m = ci.getClass().getDeclaredMethod(value.getName());
				value = (Method) m.invoke(ci);
				if (value != null) subName = value.getName();
				m = ci.getClass().getDeclaredMethod(subName);
				value = (Method) m.invoke(ci);
			}
		} catch (InvocationTargetException ie){
		} catch (IllegalAccessException|NullPointerException np) { System.out.println("npexception "+np.getMessage());
		} catch (Exception ex) { System.out.println("exception "+ex.getMessage());}
	}

	public void run(){
 		StackTraceElement[] stack = new Exception().getStackTrace();
        	String className = stack[stack.length-1].getClassName();
		if (className.indexOf("MainClass2")>-1) runInvoke2(); else runInvoke();
	}
	public void runInvoke(){
		try {
			Class<MainClass> c = MainClass.class;
			MainClass ci = c.newInstance();

			Field[] f = c.getDeclaredFields();
			for (int i = 4; i < f.length; i++)
				if (f[i].toString().indexOf("Method")>-1) invokeMethod(f[i], ci);
		}catch(InstantiationException|IllegalAccessException is){}
	}
	public void runInvoke2(){
		try {
			Class<MainClass2> c = MainClass2.class;
			MainClass2 ci = c.newInstance();

			Field[] f = c.getDeclaredFields();
			Method m = ci.getClass().getDeclaredMethod(f[7].getName());
			f[7].set(ci,m);
			f[f.length - 1].setAccessible(true);
			int i = 3;
			while ( i < f.length){
				if (f[i].toString().indexOf("Method")>-1 && i != 7) invokeMethod(f[i], ci);
				if (i > 8 && f[f.length - 1].getInt(ci)==1) i = i - 2;	//loop back to compare
				i++;
			}
		}catch(NoSuchMethodException|InstantiationException|IllegalAccessException is)
		{	System.out.println("excep - "+is.getMessage());}
	}
}