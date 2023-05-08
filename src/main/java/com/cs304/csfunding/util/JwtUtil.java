package com.cs304.csfunding.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.cs304.csfunding.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    /**
     * 秘钥
     */
    private static final String SECRET = "my_secret";

    /**
     * 过期时间
     **/
    private static final long EXPIRATION = 18000L;//单位为秒

    /**
     * 生成用户token,设置token超时时间
     */
    public static String createToken(User user) {
        //过期时间
        Date expireDate = new Date(System.currentTimeMillis() + EXPIRATION * 1000);
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        return JWT.create()
                .withHeader(map)                            //添加头部
                //可以把数据存在claim中
                .withClaim("uuid", user.getUuid())     //userId
                .withClaim("name", user.getName())
                .withExpiresAt(expireDate)                  //超时设置,设置过期的日期
                .withIssuedAt(new Date())                   //签发时间
                .sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * 检验token并解析token
     */
    public static Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            logger.error(e.getMessage());
//            logger.error("解析编码异常");
            return null;
        }


        return jwt.getClaims();
    }
}
