package com.runninglight.shared;

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

    /**
     * Command constructor
     *
     * @param className name of the class that will have a method invoked
     * @param methodName name of method to invoke
     * @param paramTypeNames collection of names of the parameters' types
     * @param paramValues collection of parameters
     *
     * @pre className, methodName, paramTypeNames, paramValues != null
     * @post A new Command object is initialized with the given parameters
     */
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

    /**
     * Server-side constructor that uses an InputStreamReader
     *
     * @param inputStreamReader the InputStreamReader that should contain the
     *                          JSON information necessary to create a Command object
     * @pre inputStreamReader != null and inputStreamReader contains JSON information for a Command
     * @post A Command object is initialized by drawing information from the InputStreamReader
     */
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

    /**
     * Populates paramTypes by searching for the class names in paramTypeNames
     *
     * @pre paramTypeNames != null
     * @post Fills paramTypes with the appropriate classes named in paramTypeNames
     */
    private void createParameterTypes() {
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

    /**
     * Executes a method in a specific class
     *
     * @return The object created from the specified class's method or an exception
     *
     * @pre className, methodName, paramTypes, paramValues != null
     * @post The method named methodName is invoked from the class named className if it exists
     */
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

    /**
     * Gets a class with the specified name
     *
     * @param className name of class to search for
     * @return A class with the specified name
     * @throws ClassNotFoundException if the class does not exist
     *
     * @pre className != null
     * @post returns the class with the specified name
     */
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
