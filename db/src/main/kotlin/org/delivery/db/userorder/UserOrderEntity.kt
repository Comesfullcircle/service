package org.delivery.db.userorder

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.delivery.db.store.StoreEntity
import org.delivery.db.userorder.enums.UserOrderStatus
import org.delivery.db.userordermenu.UserOrderMenuEntity
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "user_order")
class UserOrderEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var userId: Long? = null,

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    var store: StoreEntity? = null,

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    var status: UserOrderStatus? = null,

    @Column(precision = 11, scale = 4, nullable = false)
    var amount: BigDecimal? = null,

    var orderedAt: LocalDateTime? = null,
    var acceptedAt: LocalDateTime? = null,
    var cookingStartedAt: LocalDateTime? = null,
    var deliveryStartedAt: LocalDateTime? = null,
    var receivedAt: LocalDateTime? = null,

    @OneToMany(mappedBy = "userOrder")
    @JsonIgnore
    var userOrderMenuList: MutableList<UserOrderMenuEntity>? = null

) {
    override fun toString(): String {
        return "UserOrderEntity(id=$id, userId=$userId, store=$store, status=$status, amount=$amount, orderedAt=$orderedAt, acceptedAt=$acceptedAt, cookingStartedAt=$cookingStartedAt, deliveryStartedAt=$deliveryStartedAt, receivedAt=$receivedAt, userOrderMenuList=$userOrderMenuList)"
    }
}