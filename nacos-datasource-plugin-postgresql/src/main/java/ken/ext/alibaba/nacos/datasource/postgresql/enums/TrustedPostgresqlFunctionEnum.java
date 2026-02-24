package ken.ext.alibaba.nacos.datasource.postgresql.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * TrustedSqlFunctionEnum 枚举类用于枚举和管理可信的内置 SQL 函数列表。
 * 通过使用此枚举，您可以验证给定的 SQL 函数是否属于可信函数列表，
 * 从而避免潜在的 SQL 注入风险。
 *
 * @author ken.yu
 */
public enum TrustedPostgresqlFunctionEnum {
    /**
     * NOW().
     */
    NOW("NOW()", "NOW()");

    private static final Map<String, TrustedPostgresqlFunctionEnum> LOOKUP_MAP = new HashMap<>();

    static {
        for (TrustedPostgresqlFunctionEnum entry : TrustedPostgresqlFunctionEnum.values()) {
            LOOKUP_MAP.put(entry.functionName, entry);
        }
    }

    private final String functionName;

    private final String function;

    TrustedPostgresqlFunctionEnum(String functionName, String function) {
        this.functionName = functionName;
        this.function = function;
    }

    /**
     * Get the function name.
     *
     * @param functionName function name
     * @return function
     */
    public static String getFunctionByName(String functionName) {
        TrustedPostgresqlFunctionEnum entry = LOOKUP_MAP.get(functionName);
        if (entry != null) {
            return entry.function;
        }
        throw new IllegalArgumentException(String.format("Invalid function name: %s", functionName));
    }
}
