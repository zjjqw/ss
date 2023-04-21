package top.metass.pro.module.pay.service.merchant;

import top.metass.pro.framework.common.exception.ServiceException;
import top.metass.pro.module.pay.controller.admin.merchant.vo.channel.PayChannelCreateReqVO;
import top.metass.pro.module.pay.controller.admin.merchant.vo.channel.PayChannelExportReqVO;
import top.metass.pro.module.pay.controller.admin.merchant.vo.channel.PayChannelPageReqVO;
import top.metass.pro.module.pay.controller.admin.merchant.vo.channel.PayChannelUpdateReqVO;
import top.metass.pro.framework.common.pojo.PageResult;
import top.metass.pro.module.pay.dal.dataobject.merchant.PayChannelDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 支付渠道 Service 接口
 *
 * @author aquan
 */
public interface PayChannelService {

    /**
     * 初始化支付客户端
     */
    void initLocalCache();

    /**
     * 创建支付渠道
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createChannel(@Valid PayChannelCreateReqVO createReqVO);

    /**
     * 更新支付渠道
     *
     * @param updateReqVO 更新信息
     */
    void updateChannel(@Valid PayChannelUpdateReqVO updateReqVO);

    /**
     * 删除支付渠道
     *
     * @param id 编号
     */
    void deleteChannel(Long id);

    /**
     * 获得支付渠道
     *
     * @param id 编号
     * @return 支付渠道
     */
    PayChannelDO getChannel(Long id);

    /**
     * 获得支付渠道列表
     *
     * @param ids 编号
     * @return 支付渠道
     * 列表
     */
    List<PayChannelDO> getChannelList(Collection<Long> ids);

    /**
     * 获得支付渠道分页
     *
     * @param pageReqVO 分页查询
     * @return 支付渠道
     * 分页
     */
    PageResult<PayChannelDO> getChannelPage(PayChannelPageReqVO pageReqVO);

    /**
     * 获得支付渠道
     * 列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 支付渠道列表
     */
    List<PayChannelDO> getChannelList(PayChannelExportReqVO exportReqVO);

    /**
     * 根据支付应用ID集合获得支付渠道列表
     *
     * @param appIds 应用编号集合
     * @return 支付渠道列表
     */
    List<PayChannelDO> getChannelListByAppIds(Collection<Long> appIds);

    /**
     * 根据条件获取渠道数量
     *
     * @param merchantId 商户编号
     * @param appid      应用编号
     * @param code       渠道编码
     * @return 数量
     */
    Integer getChannelCountByConditions(Long merchantId, Long appid, String code);

    /**
     * 根据条件获取渠道
     *
     * @param merchantId 商户编号
     * @param appid      应用编号
     * @param code       渠道编码
     * @return 数量
     */
    PayChannelDO getChannelByConditions(Long merchantId, Long appid, String code);

    /**
     * 支付渠道的合法性
     *
     * 如果不合法，抛出 {@link ServiceException} 业务异常
     *
     * @param id 渠道编号
     * @return 渠道信息
     */
    PayChannelDO validPayChannel(Long id);

    /**
     * 支付渠道的合法性
     *
     * 如果不合法，抛出 {@link ServiceException} 业务异常
     *
     * @param appId 应用编号
     * @param code 支付渠道
     * @return 渠道信息
     */
    PayChannelDO validPayChannel(Long appId, String code);

}
