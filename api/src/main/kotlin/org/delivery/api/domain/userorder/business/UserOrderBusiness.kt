package org.delivery.api.domain.userorder.business


import org.delivery.api.common.Log
import org.delivery.api.domain.store.converter.StoreConverter
import org.delivery.api.domain.store.service.StoreService
import org.delivery.api.domain.storemenu.converter.StoreMenuConverter
import org.delivery.api.domain.storemenu.service.StoreMenuService
import org.delivery.api.domain.user.model.User
import org.delivery.api.domain.userorder.controller.model.UserOrderDetailResponse
import org.delivery.api.domain.userorder.controller.model.UserOrderRequest
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse
import org.delivery.api.domain.userorder.converter.UserOrderConverter
import org.delivery.api.domain.userorder.producer.UserOrderProducer
import org.delivery.api.domain.userorder.service.UserOrderService
import org.delivery.api.domain.userordermenu.converter.UserOrderMenuConverter
import org.delivery.api.domain.userordermenu.service.UserOrderMenuService
import org.delivery.common.annotation.Business
import org.delivery.db.store.StoreEntity
import org.delivery.db.storemenu.StoreMenuEntity
import org.delivery.db.userorder.UserOrderEntity
import org.delivery.db.userordermenu.UserOrderMenuEntity
import org.delivery.db.userordermenu.enums.UserOrderMenuStatus

@Business
class UserOrderBusiness(
    private val userOrderService: UserOrderService,
    private val userOrderConverter: UserOrderConverter,

    private val storeMenuService: StoreMenuService,
    private val storeMenuConverter: StoreMenuConverter,

    private val userOrderMenuService: UserOrderMenuService,
    private val userOrderMenuConverter: UserOrderMenuConverter,

    private val storeService: StoreService,
    private val storeConverter: StoreConverter,

    private val userOrderProducer: UserOrderProducer
) {

    companion object: Log

    fun userOrder(
        user: User?,
        body: UserOrderRequest?
    ) : UserOrderResponse {
        //가게찾기
        val storeEntity: StoreEntity =  storeService.getStoreWithThrow(body?.storeId)

        //주문한 메뉴찾기
        val storeMenuEntityList: List<StoreMenuEntity>? = body?.storeMenuIdList
            ?.mapNotNull { storeMenuService.getStoreMenuWithThrow(it) }
            ?.toList()

        val userOrderEntity: UserOrderEntity = userOrderConverter.toEntity(
            user = user,
            storeEntity = storeEntity,
            storeMenuEntityList = storeMenuEntityList
        ).run{
            userOrderService.order(this)
        }

        //user order menu list 생성
        val userOrderMenuEnityList : List<UserOrderMenuEntity>? = storeMenuEntityList
            ?.map{ userOrderMenuConverter.toEntity(userOrderEntity, it)}
            ?.toList()

        userOrderMenuEnityList?.forEach{ userOrderMenuService.order(it)}

        //비동기로 주문 알리기
        userOrderProducer.sendOrder(userOrderEntity)

        return userOrderConverter.toResponse(userOrderEntity)
    }

    fun current(
        user: User?
    ): List<UserOrderDetailResponse>? {
        return userOrderService.current(user?.id)
            .map{ userOrderEntity ->

                val storeMenuEntityList : List<StoreMenuEntity>? = userOrderEntity.userOrderMenuList
                    ?.filter{ userOrderMenuEntity ->
                        userOrderMenuEntity.status == UserOrderMenuStatus.REGISTERED
                    }?.map{ userOrderMenuEntity ->
                        userOrderMenuEntity.storeMenu
                    }?.toList()

                UserOrderDetailResponse(
                    userOrderResponse = userOrderConverter.toResponse(userOrderEntity),
                    storeResponse = storeConverter.toResponse(userOrderEntity.store),
                    storeMenuResponseList = storeMenuConverter.toResponse(storeMenuEntityList)
                )
            }.toList()
    }

    fun history(
        user: User?
    ): List<UserOrderDetailResponse>? {
        return userOrderService.history(user?.id)
            .map{ userOrderEntity ->

                val storeMenuEntityList : List<StoreMenuEntity>? = userOrderEntity.userOrderMenuList
                    ?.filter{ userOrderMenuEntity ->
                        userOrderMenuEntity.status == UserOrderMenuStatus.REGISTERED
                    }?.map{ userOrderMenuEntity ->
                        userOrderMenuEntity.storeMenu
                    }?.toList()

                UserOrderDetailResponse(
                    userOrderResponse = userOrderConverter.toResponse(userOrderEntity),
                    storeResponse = storeConverter.toResponse(userOrderEntity.store),
                    storeMenuResponseList = storeMenuConverter.toResponse(storeMenuEntityList)
                )
            }.toList()
    }

    fun read(
        user: User?,
        orderId: Long?
    ): UserOrderDetailResponse{

        return userOrderService.getUserOrderWithOutStatusWithThrow(orderId, user?.id)
            .let{ userOrderEntity ->

                val storeMenuEntityList : List<StoreMenuEntity>? = userOrderEntity.userOrderMenuList
                    ?.stream()
                    ?.filter { it.status == UserOrderMenuStatus.REGISTERED }
                    ?.map { it.storeMenu }
                    ?.toList()
                    ?: listOf()


                UserOrderDetailResponse(
                    userOrderResponse = userOrderConverter.toResponse(userOrderEntity),
                    storeResponse = storeConverter.toResponse(userOrderEntity.store),
                    storeMenuResponseList = storeMenuConverter.toResponse(storeMenuEntityList)
                )
        }
    }

}