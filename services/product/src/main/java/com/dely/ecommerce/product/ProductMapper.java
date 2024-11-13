package com.dely.ecommerce.product;

import com.dely.ecommerce.category.Category;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    public Product toProduct(ProductRequest request) {
        return Product.builder()
                .id(request.id())
                .namee(request.name())
                .description(request.description())
                .availableQuantity(request.availableQuantity())
                .price(request.price())
                .category(
                        Category.builder()
                                .id(request.categoryId())
                                .build()
                )
                .build();
    }

    public ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getNamee(),
                product.getDescription(),
                product.getAvailableQuantity(),
                product.getPrice(),
                product.getCategory().getId(),
                product.getCategory().getNamee(),
                product.getCategory().getDescription()


        );
    }

    public ProductPurchaseResponse toProductPurchaseResponse(Product product, double quantity) {
        return  new ProductPurchaseResponse(
                product.getId(),
                product.getNamee(),
                product.getDescription(),
                product.getPrice(),
                quantity
        );

    }
}
