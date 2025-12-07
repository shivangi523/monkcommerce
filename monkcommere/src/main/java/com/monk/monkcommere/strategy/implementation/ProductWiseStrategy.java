package com.monk.monkcommere.strategy.implementation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monk.monkcommere.dto.CartDto;
import com.monk.monkcommere.dto.CartItemDto;
import com.monk.monkcommere.model.Coupon;
import com.monk.monkcommere.strategy.CouponStrategy;
import org.springframework.stereotype.Component;

/**
 * Strategy class for handling product wise coupon logic.
 *
 * A product-wise coupon applies a  discount to a specific product.
 * The product ID and discount percentage are sent as part of the coupon "details"
 * field in JSON format. example:
 *
 *
 * {
 *   "product_id": 101,
 *   "discount": 15
 * }
 *
 * This strategy checks:
 *  - whether the required product is present in the cart
 *  - and if yes, it calculates the discount on only that product
 *
 * Everything else is ignored.
 */
@Component
public class ProductWiseStrategy implements CouponStrategy {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Checks whether the coupon can be applied to the given cart.
     * A coupon is applicable only if the target product exists in the cart.
     *
     * @param cart   full cart sent by the user
     * @param coupon coupon containing details (product_id, discount)
     * @return true if the product exists in the cart, false otherwise
     */
    @Override
    public boolean isApplicable(CartDto cart, Coupon coupon) {
        try {
            JsonNode details = objectMapper.readTree(coupon.getDetails());
            long productId = details.get("product_id").asLong();

            // Check if this product exists in cart
            return cart.getItems().stream()
                    .anyMatch(item -> item.getProductId() == productId);

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Calculates the discount based on the quantity and price of the target product.
     * If multiple quantities of the product exist, discount applies on all of them.
     *
     * Example:
     *  price = 100, quantity = 2, discount = 10%
     *  => discount = (100 * 2) * 10/100 = 20
     *
     * @param cart   user's cart containing list of items
     * @param coupon coupon holding discount rules
     * @return total discount amount for the target product
     */
    @Override
    public double calculateDiscount(CartDto cart, Coupon coupon) {
        try {
            JsonNode details = objectMapper.readTree(coupon.getDetails());

            long productId = details.get("product_id").asLong();
            double discountPercent = details.get("discount").asDouble();

            double discountAmount = 0.0;

            for (CartItemDto item : cart.getItems()) {
                if (item.getProductId() == productId) {
                    double itemTotal = item.getPrice() * item.getQuantity();

                    // % discount calculation
                    discountAmount += (itemTotal * discountPercent) / 100.0;
                }
            }

            return discountAmount;

        } catch (Exception e) {
            return 0.0;
        }
    }
}
