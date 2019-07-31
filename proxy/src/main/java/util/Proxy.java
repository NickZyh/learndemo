package util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author nick.zhou
 * @Date 2019/7/31 10:21
 * @Description <p>TODO</p>
 **/
public class Proxy {

    /**
     * 动态生成代理类,解决静态代理中的手动新建类的操作
     * 1 生成一个.java文件
     * 2 加载为.class文件
     * 3 new对象
     * @return
     */
    public static Object newInstance(Class targetInterface, ClassLoader classLoader, InvocationHandler invocationHandler) {
        // 目标接口全路径
        String targetFullPathName = targetInterface.getName();
        // 目标接口方法name
        String targetSimpleName = targetInterface.getSimpleName();
        // 获取目标接口所有方法，不包括父类方法
        Method[] methods = targetInterface.getDeclaredMethods();
        // 默认生成代理类名
        String targetClassName = targetInterface + "$Proxy";
        // target属性名称
        String fieldName = "target";
        String handler = "handler";
        // 包路径,定义为target接口路径
        String targetPackagePath = targetInterface.getPackage().getName();
        String handlerPath = invocationHandler.getClass().getPackage().getName();
        // 换行
        String line = "\n";
        // 缩进
        String tab = "\t";

        /**
         * 动态生成.java文本
         */
        // 代理类生成包路径
        String proxyPackagePath = "package " + targetPackagePath + ";";
        // 代理类中的import的路径
        String importPath = "import " + targetFullPathName + ";"
                + "import " + handlerPath + ";";
        // 声明动态代理类,$Proxy为类名
        String statementClass = "public class " + targetClassName + "implements" + targetSimpleName + "{";
        // 声明被代理接口为属性
        String targetField = "private " + targetSimpleName + " " + fieldName + ";";
        String handlerField = "private InvocationHandler " + handler + ";";
        // 构造方法
        String constructor = "public " + targetSimpleName + "(" + targetSimpleName + " " + fieldName + ", InvocationHandler " + handler +"){"
                + "this." + fieldName + "=" + fieldName + ";"
                + "this." + handler + "=" + handler + ";"
                + "}";

        // 实现接口中所有的方法
        StringBuilder methodContent = new StringBuilder();
        for (Method method : methods) {
            // 声明方法
            String returnType = method.getReturnType().getSimpleName();
            String methodName = method.getName();
            methodContent.append("public " + returnType + " " + methodName + "(");

            //构建参数
            StringBuilder temp = new StringBuilder();
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length > 0) {
                int[] argsName = new int[parameterTypes.length];
                for (int i = 0; i < parameterTypes.length; i++) {
                    temp.append(parameterTypes[i].getSimpleName() + " " + i + ",") ;
                    argsName[i] = i;
                }
                methodContent.append(temp.substring(0, temp.length() - 1));
            }
            methodContent.append("){");

            // TODO:方法的具体实现,由于增强的实现是不固定的,所以设置一个接口,然后回调
            methodContent.append(fieldName + ".getClass().getDeclaredMethod(" + methodName + ", " + parameterTypes[i].getSimpleName())" + )
            methodContent.append(handler + ".invoke();");

        }
        String end = "}";
        return null;
    }
}
