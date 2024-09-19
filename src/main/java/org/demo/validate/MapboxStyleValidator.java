package org.demo.validate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public class MapboxStyleValidator {
    /**
     * 获取JsonNode
     * @param inputStream 待校验JSON文件输入流
     * @return JsonNode
     */
    public static JsonNode getJsonNode(InputStream inputStream) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(inputStream);
            inputStream.close();
            return jsonNode;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 初始化JSON校验模板
     * @param inputStream 模板文件输入流
     * @param versionFlag JSON Format标准版本
     * @param errorMessageKeyword 自定义的异常消息关键字
     * @return JsonSchema
     */
    public static JsonSchema initJsonSchema(InputStream inputStream, SpecVersion.VersionFlag versionFlag,String errorMessageKeyword) {
        SchemaValidatorsConfig config = SchemaValidatorsConfig.builder().errorMessageKeyword(errorMessageKeyword).build();
        JsonSchema schema = JsonSchemaFactory.getInstance(versionFlag).getSchema(inputStream, config);
        try {
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return schema;
    }

    /**
     * 校验JSON
     * @param jsonSchema JsonSchema
     * @param jsonNode JsonNode
     * @return 校验结果报错信息
     */
    public static String validateJson(JsonSchema jsonSchema, JsonNode jsonNode){
        Set<ValidationMessage> errors = jsonSchema.validate(jsonNode);
        return errors.toString();
    }
}
