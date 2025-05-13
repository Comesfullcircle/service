package org.delivery.account.domain.token.service

import org.delivery.account.domain.token.ifs.TokenHelperIfs
import org.delivery.account.domain.token.model.TokenDto
import org.delivery.account.domain.token.redis.RedisTokenStore
import org.springframework.stereotype.Service

@Service
class TokenService(
    private val tokenHelperIfs: TokenHelperIfs,
    private val redisTokenStore: RedisTokenStore
) {
    fun issueAccessToken(userId: Long?): TokenDto? {
        return userId?.let {
            val data = mapOf("userId" to it)
            tokenHelperIfs.issueAccessToken(data)
        }
    }

    fun issueRefreshToken(userId: Long?): TokenDto? {
        requireNotNull(userId)
        val data = mapOf("userId" to userId)
        val refreshToken = tokenHelperIfs.issueRefreshToken(data)

        refreshToken?.token?.let {
            redisTokenStore.saveRefreshToken(userId, it)
        }

        return refreshToken
    }

    fun validationToken(token: String?): Long? {
        return token?.let {
            tokenHelperIfs.validationTokenWithThrow(it)
        }?.let { map ->
            map["userId"]?.toString()?.toLong()
        }
    }

    fun logout(userId: Long) {
        redisTokenStore.deleteRefreshToken(userId)
    }

    // ⛔ 추후에 토큰 재발급 처리 시, 이걸 사용
    fun isValidRefreshToken(userId: Long, token: String): Boolean {
        return redisTokenStore.getRefreshToken(userId) == token
    }
}
