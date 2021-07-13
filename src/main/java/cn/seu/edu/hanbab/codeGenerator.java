package cn.seu.edu.hanbab;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

//In the specified path, the three-tier structure of one key generation is implemented for the specified table of the specified relational database
public class codeGenerator {
    public static void main(String[] args) {
        run();
    }


    public static void run() {

        AutoGenerator mpg = new AutoGenerator();

        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        // JVM can be set by "user.dir" + "your directory" for the Complete path（absolute path）
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("Hanbab");
        gc.setOpen(false); //Resource Manager
        gc.setFileOverride(false); //cover
        gc.setServiceName("%sService");
        gc.setIdType(IdType.ID_WORKER); //Primary key policy
        gc.setDateType(DateType.ONLY_DATE);
        gc.setSwagger2(true);

        mpg.setGlobalConfig(gc);

        DataSourceConfig dsc = new DataSourceConfig();

        dsc.setUrl("jdbc:mysql://localhost:3306/local_test?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT&useSSL=false");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        PackageConfig pc = new PackageConfig();

        pc.setParent("cn.seu.edu.hanbab.mybatisPlus");
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setMapper("mapper");
        mpg.setPackageInfo(pc);

        StrategyConfig strategy = new StrategyConfig();

        strategy.setInclude("user");
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setTablePrefix(pc.getModuleName() + "_");

        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);

        strategy.setRestControllerStyle(true);
        strategy.setControllerMappingHyphenStyle(true);

        mpg.setStrategy(strategy);

        mpg.execute();
    }
}

