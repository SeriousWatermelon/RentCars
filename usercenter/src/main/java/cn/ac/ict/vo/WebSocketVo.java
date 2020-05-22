package cn.ac.ict.vo;

import com.alibaba.fastjson.JSONObject;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * 编码器，防止发送对象出错
 */
public class WebSocketVo implements Encoder.Text<Object> {
    @Override
    public String encode(Object o) throws EncodeException {
        return JSONObject.toJSONString(o);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
    }

    @Override
    public void destroy() {

    }
}
