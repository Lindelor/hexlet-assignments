package exercise.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import exercise.dto.ProductParamsDTO;
import exercise.model.Product;

@Component
public class ProductSpecification {


    public Specification<Product> build(ProductParamsDTO params) {
        return withCategoryId(params.getCategoryId())
                .and(withPriceGT(params.getPriceGt()))
                .and(withPriceLT(params.getPriceLt()))
                .and(withRatingGT(params.getRatingGt()))
                .and(withTitleCont(params.getTitleCont()));
    }

    private Specification<Product> withCategoryId(Long categoryId) {
        return (root, query, cb) -> categoryId == null ? cb.conjunction() : cb.equal(root.get("category").get("id"), categoryId);
    }

    private Specification<Product> withPriceGT(Integer price) {
        return (root, query, cb) -> price == null ? cb.conjunction() : cb.greaterThan(root.get("price"), price);
    }

    private Specification<Product> withPriceLT(Integer price) {
        return (root, query, cb) -> price == null ? cb.conjunction() : cb.lessThan(root.get("price"), price);
    }

    private Specification<Product> withRatingGT(Double rating) {
        return (root, query, cb) -> rating == null ? cb.conjunction() : cb.greaterThan(root.get("rating"), rating);
    }

    private Specification<Product> withTitleCont(String titleCont) {
        return (root, query, cb) -> titleCont == null ? cb.conjunction() : cb.like(root.get("title"), "%" + titleCont + "%");
    }

}
