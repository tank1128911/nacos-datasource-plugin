package ken.ext.alibaba.nacos.datasource.postgresql.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.plugin.datasource.mapper.ConfigInfoGrayMapper;
import ken.ext.alibaba.nacos.datasource.base.constants.DsConstants;
import com.alibaba.nacos.plugin.datasource.model.MapperContext;
import com.alibaba.nacos.plugin.datasource.model.MapperResult;

/**
 * config_info_gray表dml postgresql实现
 *
 * @author ken.yu
 **/

public class ConfigInfoGrayMapperByPostgresql extends AbstractMapperByPostgresql implements ConfigInfoGrayMapper {
    @Override
    public MapperResult findAllConfigInfoGrayForDumpAllFetchRows(MapperContext context) {
        int pageSize = context.getPageSize();
        int startRow = context.getStartRow();

        String sql = " SELECT id,data_id,group_id,tenant_id,gray_name,gray_rule,app_name,content,md5,gmt_modified "
                + " FROM  config_info_gray  ORDER BY id LIMIT ? OFFSET ?";

        return new MapperResult(sql, CollectionUtils.list(pageSize, startRow));
    }
}
