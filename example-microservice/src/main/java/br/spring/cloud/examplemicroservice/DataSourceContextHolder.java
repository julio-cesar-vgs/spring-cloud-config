package br.spring.cloud.examplemicroservice;

public class DataSourceContextHolder {
    private static final ThreadLocal<String> ctx = new ThreadLocal<>();
    public static void setDataSourceType(String ds) { ctx.set(ds); }
    public static String getDataSourceType()  { return ctx.get(); }
    public static void clearDataSourceType()  { ctx.remove(); }
}
