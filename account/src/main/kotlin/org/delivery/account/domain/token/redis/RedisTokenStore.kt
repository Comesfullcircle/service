package org.delivery.account.domain.token.redis

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class RedisTokenStore(
    private val redisTemplate: RedisTemplate<String, String>
) {
    fun saveRefreshToken(userId: Long, token: String, ttl: Duration = Duration.ofDays(7)) {
        redisTemplate.opsForValue().set("refresh:$userId", token, ttl)
    }

    fun getRefreshToken(userId: Long): String? {
        return redisTemplate.opsForValue().get("refresh:$userId")
    }

    fun deleteRefreshToken(userId: Long) {
        redisTemplate.delete("refresh:$userId")
    }
}
