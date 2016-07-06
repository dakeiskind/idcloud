package com.h3c.idcloud.infrastructure.common.util;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * JSON的工具类 <p/> <h3>Here is an example:</h3> <p/>
 * <pre>
 *     // 将json通过类型转换成对象
 *     {@link JsonUtil JsonUtil}.fromJson("{\"username\":\"username\", \"password\":\"password\"}",
 * User.class);
 * </pre>
 * <hr />
 * <pre>
 *     // 传入转换的引用类型
 *     {@link JsonUtil JsonUtil}.fromJson("[{\"username\":\"username\", \"password\":\"password\"},
 * {\"username\":\"username\", \"password\":\"password\"}]", new TypeReference&lt;List&lt;User&gt;&gt;);
 * </pre>
 * <hr />
 * <pre>
 *     // 将对象转换成json
 *     {@link JsonUtil JsonUtil}.toJson(user);
 * </pre>
 * <hr />
 * <pre>
 *     // 将对象转换成json, 可以设置输出属性
 *     {@link JsonUtil JsonUtil}.toJson(user, {@link Inclusion Inclusion.ALWAYS});
 * </pre>
 * <hr />
 * <pre>
 *     // 将对象转换成json, 传入配置对象
 *     {@link ObjectMapper ObjectMapper} mapper = new ObjectMapper();
 *     mapper.setSerializationInclusion({@link Inclusion Inclusion.ALWAYS});
 *     mapper.configure({@link Feature Feature.FAIL_ON_UNKNOWN_PROPERTIES}, false);
 *     mapper.configure({@link Feature Feature.FAIL_ON_NUMBERS_FOR_ENUMS}, true);
 *     mapper.setDateFormat(new {@link SimpleDateFormat SimpleDateFormat}("yyyy-MM-dd HH:mm:ss"));
 *     {@link JsonUtil JsonUtil}.toJson(user, mapper);
 * </pre>
 * <hr />
 * <pre>
 *     // 获取Mapper对象
 *     {@link JsonUtil JsonUtil}.mapper();
 * </pre>
 *
 * @see JsonUtil JsonUtil
 * @see Feature Feature
 * @see ObjectMapper ObjectMapper
 * @see Inclusion Inclusion
 * @see IOException IOException
 * @see SimpleDateFormat SimpleDateFormat
 */
@SuppressWarnings("unchecked")
public class JsonUtil {

    /**
     * ObjectMapper
     */
    private static ObjectMapper MAPPER;

    static {
        MAPPER = generateMapper(Inclusion.ALWAYS);
    }

    private JsonUtil() {
    }

    /**
     * 将json通过类型转换成对象 <p/>
     * <pre>
     *     {@link JsonUtil JsonUtil}.fromJson("{\"username\":\"username\",
     * \"password\":\"password\"}", User.class);
     * </pre>
     *
     * @param json  json字符串
     * @param clazz 泛型类型
     * @return 返回对象
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return clazz.equals(String.class) ? (T) json : MAPPER.readValue(json, clazz);
        } catch (IOException ex) {
            throw new RuntimeException("JSON格式转换错误。");
        }
    }

    /**
     * 将json通过类型转换成对象 <p/>
     * <pre>
     *     {@link JsonUtil JsonUtil}.fromJson("[{\"username\":\"username\",
     * \"password\":\"password\"}, {\"username\":\"username\", \"password\":\"password\"}]", new
     * TypeReference&lt;List&lt;User&gt;&gt;);
     * </pre>
     *
     * @param json          json字符串
     * @param typeReference 引用类型
     * @return 返回对象
     */
    public static <T> T fromJson(String json, TypeReference<?> typeReference) throws IOException {
        try {
            return (T) (typeReference.getType().equals(String.class) ? json : MAPPER
                    .readValue(json, typeReference));
        } catch (IOException ex) {
            throw new RuntimeException("JSON格式转换错误。");
        }
    }

    /**
     * 将对象转换成json <p/>
     * <pre>
     *     {@link JsonUtil JsonUtil}.toJson(user);
     * </pre>
     *
     * @param src 对象
     * @return 返回json字符串
     */
    public static <T> String toJson(T src) {
        String json = null;
        try {
            json = src instanceof String ? (String) src : MAPPER.writeValueAsString(src);
        } catch (IOException e) {
            throw new RuntimeException("JSON格式转换错误。");
        }
        return json;
    }

    /**
     * 将对象转换成json, 可以设置输出属性 <p/>
     * <pre>
     *     {@link JsonUtil JsonUtil}.toJson(user, {@link Inclusion Inclusion.ALWAYS});
     * </pre>
     * <p/> {@link Inclusion Inclusion 对象枚举} <ul> <li>{@link Inclusion Inclusion.ALWAYS 全部列入}</li>
     * <li>{@link Inclusion Inclusion.NON_DEFAULT 字段和对象默认值相同的时候不会列入}</li> <li>{@link Inclusion
     * Inclusion.NON_EMPTY 字段为NULL或者""的时候不会列入}</li> <li>{@link Inclusion Inclusion.NON_NULL
     * 字段为NULL时候不会列入}</li> </ul>
     *
     * @param src       对象
     * @param inclusion 传入一个枚举值, 设置输出属性
     * @return 返回json字符串
     */
    public static <T> String toJson(T src, Inclusion inclusion) {
        try {
            if (src instanceof String) {
                return (String) src;
            } else {
                ObjectMapper customMapper = generateMapper(inclusion);
                return customMapper.writeValueAsString(src);
            }
        } catch (IOException e) {
            throw new RuntimeException("JSON格式转换错误。");
        }
    }

    /**
     * 将对象转换成json, 传入置对象 <p/>
     * <pre>
     *     {@link ObjectMapper ObjectMapper} mapper = new ObjectMapper();
     *     mapper.setSerializationInclusion({@link Inclusion Inclusion.ALWAYS});
     *     mapper.configure({@link Feature Feature.FAIL_ON_UNKNOWN_PROPERTIES}, false);
     *     mapper.configure({@link Feature Feature.FAIL_ON_NUMBERS_FOR_ENUMS}, true);
     *     mapper.setDateFormat(new {@link SimpleDateFormat SimpleDateFormat}("yyyy-MM-dd
     * HH:mm:ss"));
     *     {@link JsonUtil JsonUtil}.toJson(user, mapper);
     * </pre>
     * <p/> {@link ObjectMapper ObjectMapper}
     *
     * @param src    对象
     * @param mapper 配置对象
     * @return 返回json字符串
     * @see ObjectMapper
     */
    public static <T> String toJson(T src, ObjectMapper mapper) {
        try {
            if (null != mapper) {
                if (src instanceof String) {
                    return (String) src;
                } else {
                    return mapper.writeValueAsString(src);
                }
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException("JSON格式转换错误。");
        }
    }

    /**
     * 返回{@link ObjectMapper ObjectMapper}对象, 用于定制性的操作
     *
     * @return {@link ObjectMapper ObjectMapper}对象
     */
    public static ObjectMapper mapper() {
        return MAPPER;
    }

    /**
     * 通过Inclusion创建ObjectMapper对象 <p/> {@link Inclusion Inclusion 对象枚举} <ul> <li>{@link Inclusion
     * Inclusion.ALWAYS 全部列入}</li> <li>{@link Inclusion Inclusion.NON_DEFAULT
     * 字段和对象默认值相同的时候不会列入}</li> <li>{@link Inclusion Inclusion.NON_EMPTY 字段为NULL或者""的时候不会列入}</li>
     * <li>{@link Inclusion Inclusion.NON_NULL 字段为NULL时候不会列入}</li> </ul>
     *
     * @param inclusion 传入一个枚举值, 设置输出属性
     * @return 返回ObjectMapper对象
     */
    public static ObjectMapper generateMapper(Inclusion inclusion) {

        ObjectMapper customMapper = new ObjectMapper();

        // 设置输出时包含属性的风格
        customMapper.setSerializationInclusion(inclusion);

        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        customMapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 禁止使用int代表Enum的order()來反序列化Enum,非常危險
        customMapper.configure(Feature.FAIL_ON_NUMBERS_FOR_ENUMS, true);

        // 所有日期格式都统一为以下样式
        customMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        return customMapper;
    }

    /**
     * 将json转换成list<Object>, json字符串和你要转换的javabean
     *
     * @param json           对象
     * @param elementClasses 配置对象
     * @return list<Object>
     * @see ObjectMapper
     */
    public static List<?> toListObject(String json, Class<?>... elementClasses) {
        List<Class<?>> list = null;
        try {
            JavaType
                    javaType =
                    MAPPER.getTypeFactory()
                            .constructParametricType(ArrayList.class, elementClasses);
            list = MAPPER.readValue(json, javaType);
        } catch (IOException e) {
            throw new RuntimeException("JSON格式转换错误。");
        }
        return list;
    }

    public static <T> T fromJson(String json, TypeReference<?> typeReference, Inclusion inclusion) {
        try {
            return (T) (typeReference.getType().equals(String.class) ? json : generateMapper
                    (inclusion)
                    .readValue(json, typeReference));
        } catch (IOException e) {
            throw new RuntimeException("JSON格式转换错误。");
        }
    }

    public static <T> T fromJson(FileReader fileReader, Class<T> clazz) {
        try {
            return MAPPER.readValue(fileReader, clazz);
        } catch (IOException e) {
            throw new RuntimeException("JSON格式转换错误。");
        }
    }

    public static JsonNode fromJson(String json) {
        if (json == null || json.length() == 0) {
            return null;
        }
        try {
            return mapper().getJsonFactory().createJsonParser(json).readValueAsTree();
        } catch (IOException e) {
            return null;
        }
    }

    public static void main(String args[]) {
        String spec = "{\n" +
                "    \"region\": \"10\",\n" +
                "    \"zone\": \"12e0c825-3ff5-11e5-8c09-005056ba3c46\",\n" +
                "    \"password\": \"\",\n" +
                "    \"hostname\": \"test\",\n" +
                "    \"instance\": [{\n" +
                "        \"instanceCategory\": \"idc-S\",\n" +
                "        \"cpu\": \"1\",\n" +
                "        \"memory\": \"1\"\n" +
                "    }],\n" +
                "    \"systemDisk\": [{\n" +
                "        \"systemDiskCategory\": \"cloud_efficiency\",\n" +
                "        \"systemDiskSize\": \"40\",\n" +
                "        \"systemDiskDevice\": \"/dev/xvda\"\n" +
                "    }],\n" +
                "    \"dataDisk\": [{\n" +
                "        \"dataDiskCategory\": \"cloud_ssd\",\n" +
                "        \"dataDiskSize\": \"100\",\n" +
                "        \"dataDiskSnapshot\": \"\",\n" +
                "        \"dataDiskDevice\": \"\",\n" +
                "        \"dataDiskDeletewithinstance\": \"true\",\n" +
                "        \"dataDiskInstanceId\": null\n" +
                "    }, {\n" +
                "        \"dataDiskCategory\": \"cloud_efficiency\",\n" +
                "        \"dataDiskSize\": \"150\",\n" +
                "        \"dataDiskSnapshot\": \"\",\n" +
                "        \"dataDiskDevice\": \"\",\n" +
                "        \"dataDiskDeletewithinstance\": \"true\",\n" +
                "        \"dataDiskInstanceId\": null\n" +
                "    }],\n" +
                "    \"networkType\": \"vpc\",\n" +
                "    \"networks\": [\"e581e6fa-efe3-11e5-9f4e-005056a50931\"],\n" +
                "    \"bandwidth\": \"0\",\n" +
                "    \"os\": {\n" +
                "        \"osType\": \"public\",\n" +
                "        \"os\": \"605cdc9a-585c-11e5-8dea-005056a5742a\"\n" +
                "    },\n" +
                "    \"keyPair\": \"ccd98605-77ba-11e5-b6e5-005056a52fbf\",\n" +
                "    \"securityGroup\": \"ccd98605-77ba-11e5-b6e5-005056a52fbf\"\n" +
                "}\n";
        JsonNode jsonNode = fromJson(spec);
        System.out.println();
        jsonNode.findValue("networks").forEach(System.out::println);
    }

}
