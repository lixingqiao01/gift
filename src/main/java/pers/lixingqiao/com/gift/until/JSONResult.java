package pers.lixingqiao.com.gift.until;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;


public class JSONResult {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private Integer status;
    private String msg;
    private Object response;
    private boolean success;
    private String token;

    public enum JsonResultStatus{
        SUCCESS("请求成功",10000),
        UNREGIST("用户还未注册",10001),
        LOGIN_ERROR("密码错误",10002),
        TOKEN_EXPIRE("token即将过期",10003),

        PERMISSIONS_ERROR("没有权限访问或登录已经过期",99999);

        public String msg;
        public Integer status;
        JsonResultStatus(String msg, int status) {
            this.msg = msg;
            this.status = status;
        }
    }

    public static ObjectMapper getMAPPER() {
        return MAPPER;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static JSONResult build(Integer status, String msg, Object data) {
        return new JSONResult(status, msg, data);
    }
    public static JSONResult build(Integer status, String msg, Object data,String token) {
        if (JwtUntil.verifyTokenWillExpire(token) == null) {
            return new JSONResult(status, msg, data);
        }
        return new JSONResult(JsonResultStatus.TOKEN_EXPIRE.status,JsonResultStatus.TOKEN_EXPIRE.msg,data,JwtUntil.verifyTokenWillExpire(token));
    }

    public static JSONResult ok(Object data) {
        return new JSONResult(data);
    }

    public static JSONResult ok() {
        return new JSONResult(null);
    }

    public static JSONResult errorMsg(String msg) {
        return new JSONResult(500, msg, null);
    }

    public static JSONResult errorMap(Object data) {
        return new JSONResult(501, "error", data);
    }

    public static JSONResult errorTokenMsg(String msg) {
        return new JSONResult(502, msg, null);
    }

    public static JSONResult errorException(String msg) {
        return new JSONResult(555, msg, null);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public JSONResult() {
    }

    public JSONResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.response = data;
    }

    public JSONResult(Integer status, String msg,Object data, String token){
        this.status = status;
        this.msg = msg;
        this.response = data;
        this.token = token;
    }

    public JSONResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.response = data;
        this.success = true;
    }

    public Boolean isOK() {
        return this.status == 200;
    }

    public static JSONResult formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, JSONResult.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg")
                    .asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @Description: 没有object对象的转化
     * @param json
     * @return
     * @author leechenxiang
     * @date 2016年4月22日 下午8:35:21
     */
    public static JSONResult format(String json) {
        try {
            return MAPPER.readValue(json, JSONResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Description: Object是集合转化
     *               需要转换的对象是一个list
     * @param jsonData
     * @param clazz
     * @return
     * @author leechenxiang
     * @date 2016年4月22日 下午8:35:31
     */
    public static JSONResult formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(
                        data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(
                                List.class, clazz));
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg")
                    .asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }
}
