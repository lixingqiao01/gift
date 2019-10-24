package pers.lixingqiao.com.gift.until;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;

import javax.xml.crypto.Data;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JwtUntil {
    /*
    * 设置过期时间 15分钟
    * */
//    @Value("jwtUntil.exprotTime")
    private static final long EXPIRE_TIME = 3 * 30 * 24 * 60 * 60 * 1000l;

    /*
    * 设置token私钥  UUID
    * */
    private static final String TOKEN_SECRET = "f2cb0b30f20a11e9a2b00235d2b38928";

    /*
    * 组装一个token
    * */
    public static String build(String username,Integer user_id) {
        Date halfExpire_time = new Date(System.currentTimeMillis() + EXPIRE_TIME/2);
        String halfToken = sign(username,user_id,halfExpire_time);
        Date expire_time = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        String token = sign(username,user_id,expire_time);
        return token + "half" + halfToken;
    }

    /*
    * 根据username、user_id生成token
    * */
    public static String sign(String username,Integer userID,Date expire_time) {
        //过期时间
//        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        //私钥及加密方法
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        //设置头部信息
        Map<String,Object> header = new HashMap<>(2);
        header.put("typ","jwt");
        header.put("alg","HC256");

        //附带username、userid、生成签名
        return JWT.create()
                  .withHeader(header)
                  .withClaim("username",username)
                  .withClaim("user_id",userID)
                  .withExpiresAt(expire_time)
                  .sign(algorithm);
    }

    /*
    * 验证token
    * */
    public static boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    /*
    * refreshToken
    * */
    public  static String refreshToken(String token) {
        String[] list = token.split("half");
        if (verify(list[0]) && verify(list[1])) {
            //两个token都还没有过期
            return token;
        } else if (verify(list[0]) && !verify(list[1])) {
            //时间较长的token验证通过，一般时间的token验证不通过，需要更新token
            //1.获取username、user_id
            String username = getUsernameByToken(list[0]);
            Integer user_id = getIDByToken(list[0]);
            return build(username,user_id);
        } else {
            //两个都没有验证通过，需要重新登录
            return null;
        }
    }

    /*
    * 验证组装token
    * */
    public static boolean userVerify(String token) {
        String[] token_list = token.split("half");
        return verify(token_list[0]) || verify(token_list[1]);
    }

    /*
    * 根据token获取user_id
    * */
    public static Integer getIDByToken(String token) {
        String first_token = token.split("half")[0];
        try {
            DecodedJWT decodedJWT = JWT.decode(first_token);
            return decodedJWT.getClaim("user_id").asInt();
        } catch (JWTDecodeException decodeExcept) {
            return null;
        }
    }

    /*
    * 根据token获取用户名：username
    * */
    public  static String getUsernameByToken(String token) {
        String first_token = token.split("half")[0];
        try {
            DecodedJWT decodedJWT = JWT.decode(first_token);
            return decodedJWT.getClaim("username").asString();
        } catch (JWTDecodeException decodeExcept) {
            return null;
        }
    }
}
