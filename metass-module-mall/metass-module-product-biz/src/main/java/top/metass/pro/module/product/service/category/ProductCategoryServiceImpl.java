package top.metass.pro.module.product.service.category;

import top.metass.pro.framework.common.enums.CommonStatusEnum;
import top.metass.pro.module.product.controller.admin.category.vo.ProductCategoryCreateReqVO;
import top.metass.pro.module.product.controller.admin.category.vo.ProductCategoryListReqVO;
import top.metass.pro.module.product.controller.admin.category.vo.ProductCategoryUpdateReqVO;
import top.metass.pro.module.product.convert.category.ProductCategoryConvert;
import top.metass.pro.module.product.dal.dataobject.category.ProductCategoryDO;
import top.metass.pro.module.product.dal.mysql.category.ProductCategoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static top.metass.pro.framework.common.exception.util.ServiceExceptionUtil.exception;
import static top.metass.pro.module.product.enums.ErrorCodeConstants.*;

/**
 * 商品分类 Service 实现类
 *
 * @author 三生宇宙
 */
@Service
@Validated
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Resource
    private ProductCategoryMapper productCategoryMapper;

    @Override
    public Long createCategory(ProductCategoryCreateReqVO createReqVO) {
        // 校验父分类存在
        validateParentProductCategory(createReqVO.getParentId());

        // 插入
        ProductCategoryDO category = ProductCategoryConvert.INSTANCE.convert(createReqVO);
        productCategoryMapper.insert(category);
        // 返回
        return category.getId();
    }

    @Override
    public void updateCategory(ProductCategoryUpdateReqVO updateReqVO) {
        // 校验分类是否存在
        validateProductCategoryExists(updateReqVO.getId());
        // 校验父分类存在
        validateParentProductCategory(updateReqVO.getParentId());

        // 更新
        ProductCategoryDO updateObj = ProductCategoryConvert.INSTANCE.convert(updateReqVO);
        productCategoryMapper.updateById(updateObj);
    }

    @Override
    public void deleteCategory(Long id) {
        // 校验分类是否存在
        validateProductCategoryExists(id);
        // 校验是否还有子分类
        if (productCategoryMapper.selectCountByParentId(id) > 0) {
            throw exception(CATEGORY_EXISTS_CHILDREN);
        }
        // TODO 三生 补充只有不存在商品才可以删除
        // 删除
        productCategoryMapper.deleteById(id);
    }

    private void validateParentProductCategory(Long id) {
        // 如果是根分类，无需验证
        if (Objects.equals(id, ProductCategoryDO.PARENT_ID_NULL)) {
            return;
        }
        // 父分类不存在
        ProductCategoryDO category = productCategoryMapper.selectById(id);
        if (category == null) {
            throw exception(CATEGORY_PARENT_NOT_EXISTS);
        }
        // 父分类不能是二级分类
        if (Objects.equals(id, ProductCategoryDO.PARENT_ID_NULL)) {
            throw exception(CATEGORY_PARENT_NOT_FIRST_LEVEL);
        }
    }

    private void validateProductCategoryExists(Long id) {
        ProductCategoryDO category = productCategoryMapper.selectById(id);
        if (category == null) {
            throw exception(CATEGORY_NOT_EXISTS);
        }
    }

    @Override
    public ProductCategoryDO getCategory(Long id) {
        return productCategoryMapper.selectById(id);
    }

    @Override
    public void validateCategory(Long id) {
        ProductCategoryDO category = productCategoryMapper.selectById(id);
        if (category == null) {
            throw exception(CATEGORY_NOT_EXISTS);
        }
        if (Objects.equals(category.getStatus(), CommonStatusEnum.ENABLE.getStatus())) {
            throw exception(CATEGORY_DISABLED, category.getName());
        }
    }

    @Override
    public Integer getCategoryLevel(Long id) {
        if (Objects.equals(id, ProductCategoryDO.PARENT_ID_NULL)) {
            return 0;
        }
        int level = 1;
        for (int i = 0; i < 100; i++) {
            ProductCategoryDO category = productCategoryMapper.selectById(id);
            // 如果没有父节点，break 结束
            if (category == null
                    || Objects.equals(category.getParentId(), ProductCategoryDO.PARENT_ID_NULL)) {
                break;
            }
            // 继续递归父节点
            level++;
            id = category.getParentId();
        }
        return level;
    }

    @Override
    public List<ProductCategoryDO> getEnableCategoryList(ProductCategoryListReqVO listReqVO) {
        return productCategoryMapper.selectList(listReqVO);
    }

    @Override
    public List<ProductCategoryDO> getEnableCategoryList() {
        return productCategoryMapper.selectListByStatus(CommonStatusEnum.ENABLE.getStatus());
    }

}
