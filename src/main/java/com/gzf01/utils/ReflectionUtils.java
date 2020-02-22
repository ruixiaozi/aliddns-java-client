package com.gzf01.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

public class ReflectionUtils {
    public static final int state = -1;

    public static void loadClasses(List<String> list) throws ClassNotFoundException {
        for(String s:list){
            Class.forName(s);
        }
    }

    public static void printClass(Class cl){

        String modifiers = Modifier.toString(cl.getModifiers());
        if(modifiers.length()>0) System.out.print(modifiers+" ");
        System.out.print("class "+cl.getName());

        Class supCl = cl.getSuperclass();
        if(supCl!=null && supCl!=Object.class)
            System.out.print(" extends "+supCl.getName());

        System.out.println("\n{\n");

        //输出域
        Field[] fields = cl.getFields();
        for(Field f:fields){
            System.out.print("    ");
            String fModifiers = Modifier.toString(f.getModifiers());
            if(fModifiers.length()>0) System.out.print(fModifiers+" ");

            System.out.println(f.getType().getName()+" " + f.getName()+";");
        }

        System.out.println();

        //输出构造方法
        Constructor[] constructors = cl.getConstructors();
        for(Constructor c : constructors){
            System.out.print("    ");
            String cModifiers = Modifier.toString(c.getModifiers());
            if(cModifiers.length()>0) System.out.print(cModifiers+" ");

            System.out.print(c.getName()+"(");

            Class[] paraTypes = c.getParameterTypes();
            for(int i=0;i<paraTypes.length;++i){
                if(i>0) System.out.print(", ");
                System.out.print(paraTypes[i].getName());
            }

            System.out.println(");");
        }

        System.out.println();

        //输出其他方法
        Method[] methods = cl.getMethods();
        for(Method m : methods){
            System.out.print("    ");
            String mModifiers = Modifier.toString(m.getModifiers());
            if(mModifiers.length()>0) System.out.print(mModifiers+" ");

            System.out.print(m.getReturnType().getName()+" "+m.getName()+"(");

            Class[] mparaTypes = m.getParameterTypes();

            for(int i=0;i<mparaTypes.length;++i){
                if(i>0) System.out.print(", ");
                System.out.print(mparaTypes[i].getName());
            }

            System.out.println(");");

        }

    }

}
