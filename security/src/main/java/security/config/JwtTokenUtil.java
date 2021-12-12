package security.config;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.MD5;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import security.po.UserPO;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 类描述：
 * token工具类
 * @ClassName TokenUtil
 * @Author msi
 * @Date 2020/6/12 19:57
 * @Version 1.0
 */
@Component
@Slf4j
public class JwtTokenUtil {
    /**
     * 请求携带的token的请求头/响应头
     */
    public static final String TOKEN_HEADER = "Authorization";

    /**
     * Bearer token字符串前缀
     */
    public static final String TOKEN_BEARER_PREFIX = "Bearer ";

    /**
     * Basic Base64字符串前缀
     */
    public static final String TOKEN_BASIC_PREFIX = "Basic ";

    /**
     * 作者
     */
    public static final String ISSUER = "cfl";
    /**
     * 有效时长单位小时
     */
    public static final int VALID_HOUR = 7 * 24;

    /**
     * 生产token的盐
     */
    public static final String SALT = "99fa69b0-d9d5-4b5a-aa86-e75637df03c6";


    /**
     * 检查token格式是否正确
     * @return
     */
    public static boolean checkTokenFormat(String token) {
        // "Bearer "
        if (token.startsWith(JwtTokenUtil.TOKEN_BEARER_PREFIX)) {
            return true;
        }
        // "Basic "
        if (token.startsWith(JwtTokenUtil.TOKEN_BASIC_PREFIX)) {
            return true;
        }

       throw new RuntimeException("token 格式错误");
    }

    /**
     * 生产指定有效时长的token字符串
     * 将用户的基本信息、权限信息、能访问的菜单信息存储到token中
     * @param userPO 用户登录信息
     * @return
     */
    public static String generateBearerToken (UserPO userPO, int hour) throws JsonProcessingException {
        // secret 密钥，只有服务器知道
        Algorithm algorithm = Algorithm.HMAC256(JwtTokenUtil.SALT);
        // 当前时间
        LocalDateTime ldt = LocalDateTime.now();

        String token = JWT.create()
                // jwt唯一id
                .withJWTId(IdUtil.randomUUID())
                // 发布者
                .withIssuer(JwtTokenUtil.ISSUER)
                // 生成签名的时间
                .withIssuedAt(new Date())
                // 有效时长
                .withExpiresAt(Date.from(ldt.plusHours(hour).atZone(ZoneId.systemDefault()).toInstant()))
                // 绑定用户数据
                .withAudience(new ObjectMapper().writeValueAsString(userPO))
                // 主题
                .withSubject("狗东")
                // 签发的目标
                .sign(algorithm);

        return TOKEN_BEARER_PREFIX + token;
    }

    /**
     * 根据token生成redis key。
     * 将token字符串生成16位16进制的字符串，用作redis的key
     * @param token
     * @return
     */
    public static String generateRedisKey (String token) {
        boolean bool = JwtTokenUtil.checkTokenFormat(token);

        return MD5.create().digestHex16(token);
    }

    /**
     * 解析token字符串
     * @param token 需要被解析的字符串
     */
    public static UserPO resolveToken(String token) throws JsonProcessingException {

        // 判断请求头是Beare开头
        if (token.startsWith(JwtTokenUtil.TOKEN_BEARER_PREFIX)) {
            return getAuthorityUserDTOByBearer(token);
        }
        throw new RuntimeException("token 格式错误");
    }

    /**
     * Bearer方式 解码字符串
     * @param token
     * @return
     */
    private static UserPO getAuthorityUserDTOByBearer(String token) throws JsonProcessingException {
        // 将加工后的转换原生token（没有Bearer ，Basic 字符串）
        String nativeToken = generateNativeToken(token);

        Algorithm algorithm = Algorithm.HMAC256(JwtTokenUtil.SALT);
        JWTVerifier verifier = JWT.require(algorithm)
                // //匹配指定的token发布者
                .withIssuer(JwtTokenUtil.ISSUER)
                .build();

        DecodedJWT jwt = null;
        try {
            jwt = verifier.verify(nativeToken);
        } catch (TokenExpiredException | JWTDecodeException e) {
            throw new RuntimeException("解析token失败");
        }

        // token 失效时间是否满足要求
        JwtTokenUtil.checkValidToken(jwt);

        String result = jwt.getAudience().get(0);
        log.info("result:{}",result);
        return new ObjectMapper().readValue(result, UserPO.class);
    }

    /**
     * token失效时间校验
     * @param decodedJWT
     * @return
     */
    private static void checkValidToken(DecodedJWT decodedJWT) {
        // 失效的时间(提前一天，防止token失效)
        LocalDateTime expiresAtLocalDateTime = decodedJWT
                .getExpiresAt()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .minusDays(1);
        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(expiresAtLocalDateTime)) {
           throw new RuntimeException("token 失效");
        }
    }


    /**
     * 去掉token字符串的`Basic `前缀或`Bearer `前缀
     * @param token
     * @return
     */
    public static String generateNativeToken (String token) {

        // 去掉前面的 "Bearer " 字符串
        if (token.startsWith(JwtTokenUtil.TOKEN_BEARER_PREFIX)) {
            return token.replace(JwtTokenUtil.TOKEN_BEARER_PREFIX, "");
        }
        // 去掉前面的 "Basic "字符串
        if (token.startsWith(JwtTokenUtil.TOKEN_BASIC_PREFIX)) {
            return token.replace(JwtTokenUtil.TOKEN_BASIC_PREFIX, "");
        }

        throw new RuntimeException("token 格式错误");
    }
}
