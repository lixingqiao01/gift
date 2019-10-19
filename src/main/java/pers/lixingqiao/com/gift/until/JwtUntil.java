package pers.lixingqiao.com.gift.until;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUntil {
    /*
    * 设置过期时间 15分钟
    * */
    @Value("jwtUntil.exprotTime")
    private static long EXPIRE_TIME;

    /*
    * 设置token私钥  UUID
    * */
    private static final String TOKEN_SECRET = "f2cb0b30f20a11e9a2b00235d2b38928";


    /*
    * 根据username、user_id生成token
    * */
    public static String sign(String username,Integer userID) {
        //过期时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
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
//                .withClaim("user_id",userID)
                .withClaim("user_id",userID)
                .withExpiresAt(date)
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
        } catch (JWTDecodeException exception) {
            return false;
        }
    }

    /*
    * 根据token获取user_id
    * */
    public static Integer getIDByToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            return decodedJWT.getClaim("user_id").asInt();
        } catch (JWTDecodeException decodeExcept) {
            return null;
        }
    }
}
