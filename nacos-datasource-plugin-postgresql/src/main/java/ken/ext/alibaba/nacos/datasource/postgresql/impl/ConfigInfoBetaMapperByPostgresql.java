package ken.ext.alibaba.nacos.datasource.postgresql.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.plugin.datasource.mapper.ConfigInfoBetaMapper;
import com.alibaba.nacos.plugin.datasource.model.MapperContext;
import com.alibaba.nacos.plugin.datasource.model.MapperResult;

/**
 * config_info_beta表dml postgresql实现
 *
 * @author ken.yu
 **/
public class ConfigInfoBetaMapperByPostgresql extends AbstractMapperByPostgresql implements ConfigInfoBetaMapper {
    @Override
    public MapperResult findAllConfigInfoBetaForDumpAllFetchRows(MapperContext context) {
        int pageSize = context.getPageSize();
        int startRow = context.getStartRow();

        String sql = "SELECT id,data_id,group_id,tenant_id,app_name,content,md5,gmt_modified,beta_ips,encrypted_data_key" +
                " FROM config_info_beta ORDER BY id LIMIT " + pageSize + " OFFSET " + startRow;
        
        return new MapperResult(sql, CollectionUtils.list(pageSize, startRow));
    }
}
