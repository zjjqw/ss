package top.metass.pro.module.system.convert.social;

import top.metass.pro.module.system.api.social.dto.SocialUserBindReqDTO;
import top.metass.pro.module.system.api.social.dto.SocialUserUnbindReqDTO;
import top.metass.pro.module.system.controller.admin.socail.vo.SocialUserBindReqVO;
import top.metass.pro.module.system.controller.admin.socail.vo.SocialUserUnbindReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SocialUserConvert {

    SocialUserConvert INSTANCE = Mappers.getMapper(SocialUserConvert.class);

    SocialUserBindReqDTO convert(Long userId, Integer userType, SocialUserBindReqVO reqVO);

    SocialUserUnbindReqDTO convert(Long userId, Integer userType, SocialUserUnbindReqVO reqVO);

}
