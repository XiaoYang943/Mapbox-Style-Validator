import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.*;
import org.demo.validate.MapboxStyleValidator;

import java.io.InputStream;

public class ValidJson {
    private static final String SCHEMA_VALIDATION_FILE = "schema.json"; // 校验模板

    private static final String DATA_FILE = "data.json";    // 数据

    private static final String ERROR_MESSAGE_KEYWORD = "errorMessage";    // 自定义的异常消息关键字，必须和schema中保持一致

    private static final SpecVersion.VersionFlag VERSION_FLAG = SpecVersion.VersionFlag.V7;    // JSON Format标准版本，必须和schema中的 $schema 属性保持一致

    public static void main(String[] args) {
        InputStream data = ValidJson.class.getResourceAsStream(DATA_FILE);
        InputStream schema = ValidJson.class.getResourceAsStream(SCHEMA_VALIDATION_FILE);

        JsonSchema jsonSchema = MapboxStyleValidator.initJsonSchema(schema,VERSION_FLAG,ERROR_MESSAGE_KEYWORD);
        JsonNode jsonNode = MapboxStyleValidator.getJsonNode(data);

        String errorStr= MapboxStyleValidator.validateJson(jsonSchema,jsonNode);

        System.err.println(errorStr);
    }

}

