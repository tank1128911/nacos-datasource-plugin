package ken.ext.alibaba.nacos.datasource.postgresql.impl;

import com.alibaba.nacos.plugin.datasource.mapper.AbstractMapper;
import ken.ext.alibaba.nacos.datasource.base.constants.DsConstants;
import ken.ext.alibaba.nacos.datasource.postgresql.enums.TrustedPostgresqlFunctionEnum;

/**
 * 用于定义postgresql数据库特有的crud等dml的sql的公共方法资源
 *
 * @author ken.yu
 **/
public abstract class AbstractMapperByPostgresql extends AbstractMapper {
    @Override
    public String getFunction(String functionName) {
        return TrustedPostgresqlFunctionEnum.getFunctionByName(functionName);
    }

    @Override
    public String getDataSource() {
        return DsConstants.POSTGRESQL;
    }
}
