package ken.ext.alibaba.nacos.datasource.base.constants;

import com.alibaba.nacos.plugin.datasource.constants.DataSourceConstant;

/**
 * 扩展数据源标识定义，注意nacos-standalone.env中的SPRING_DATASOURCE_PLATFORM需要配置本类文件中定义的某个常量值
 *
 * @author ken
 */
public class DsConstants extends DataSourceConstant {
    //Postgresql
    public static final String POSTGRESQL = "postgresql";
}
