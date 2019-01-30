package com.runninglight.server;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.lang.reflect.Method;


public class Command {

    private String className;
    private String methodName;
    private Class<?>[] paramTypes;
    private String[] paramTypeNames;
    private String[] paramsAsJsonStrings;
    private Object[] paramValues;
    private static Gson gson = new Gson();

    public Command(String className, String methodName,
                   String[] paramTypeNames, Object[] paramValues) {
        this.className = className;
        this.methodName = methodName;
        this.paramTypeNames = paramTypeNames;
        this.paramValues = paramValues;
        this.paramsAsJsonStrings = new String[paramValues.length];
        for(int i = 0; i < paramValues.length; i++) {
            paramsAsJsonStrings[i] = gson.toJson(paramValues[i]);
        }
    }

    public Command(InputStreamReader inputStreamReader) {
        Command tempCommand = (Command) gson.fromJson(inputStreamReader, Command.class);
        className = tempCommand.getClassName();
        methodName = tempCommand.getMethodName();
        paramTypeNames = tempCommand.getParamTypeNames();
        paramsAsJsonStrings = tempCommand.getParamsAsJsonStrings();
        createParameterTypes();
        paramValues = new Object[paramsAsJsonStrings.length];
        for(int i = 0; i < paramsAsJsonStrings.length; i++) {
            paramValues[i] = gson.fromJson(paramsAsJsonStrings[i], paramTypes[i]);
        }
    }

    private final void createParameterTypes() {
        paramTypes = new Class<?>[paramTypeNames.length];
        for(int i = 0; i < paramTypeNames.length; i++) {
            try {
                paramTypes[i] = getClassFor(paramTypeNames[i]);
            } catch (ClassNotFoundException e) {
                System.err.println("ERROR: IN Command.execute could not create a parameter type from the parameter type name " +
                        paramTypeNames[i]);
                e.printStackTrace();
            }
        }
    }

    public Object execute() {
        try {
            Class<?> receiver = Class.forName(className);
            Object t = receiver.newInstance();
            Method method = receiver.getMethod(methodName, paramTypes);
            return method.invoke(t, paramValues);
        }
        catch (Exception e) {
            e.printStackTrace();
            return e;
        }
    }

    private static Class<?> getClassFor(String className)
            throws ClassNotFoundException
    {
        Class<?> result = null;
        switch (className) {
            case "boolean" :
                result = boolean.class; break;
            case "byte"    :
                result = byte.class;    break;
            case "char"    :
                result = char.class;    break;
            case "double"  :
                result = double.class;  break;
            case "float"   :
                result = float.class;   break;
            case "int"     :
                result = int.class;     break;
            case "long"    :
                result = long.class;    break;
            case "short"   :
                result = short.class;   break;
            default:
                result = Class.forName(className);
        }
        return result;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public Class<?>[] getParamTypes() {
        return paramTypes;
    }

    public String[] getParamTypeNames() {
        return paramTypeNames;
    }

    public Object[] getParamValues() {
        return paramValues;
    }

    public String[] getParamsAsJsonStrings() {
        return paramsAsJsonStrings;
    }
}
