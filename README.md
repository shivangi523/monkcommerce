 Monk Commerce – Coupon Discount

This project is a simple coupon discount built using Spring Boot.
The idea was to support multiple coupon types (Cart-wise, Product-wise, and BxGy) and expose APIs to create, update, apply, and validate coupons.

I followed a strategy design pattern, so each coupon type has its own logic class, making it easy to add new types later.

##Features Implemented
I implemented three coupon types:

1. Cart-wise coupon- Applies a flat discount to the entire cart. Works only if the cart total crosses the minimum amount.
Example:
{"minAmount": 500, "discount": 50}

2. Product-wise coupon- Applies a percentage discount on selected product(s) inside the cart.
Example:
{"product_id": 101, "discount": 20}

3. BxGy coupon- “Buy X items, Get Y items free”.
Example:
{"productId": 101, "buyQty": 3, "getQty": 1}

Each coupon type has its own strategy class inside strategy/implementation.

 API Endpoints (Final)
1. POST /coupons – Create a new coupon
2. GET /coupons – Get all coupons
3. GET /coupons/{id} – Get coupon by ID
4. PUT /coupons/{id} – Update coupon
5. DELETE /coupons/{id} – Delete coupon
6. POST /coupons/applicable-coupons
7. POST /coupons/apply-coupon/{id}

Applies ANY coupon (cartwise/productwise/bxgy) using unified strategy.


## Design Pattern Used

I used the Strategy Pattern, so each coupon type has its own class:
CartWiseStrategy  
ProductWiseStrategy  
BxGyStrategy

The service layer decides which strategy to use based on coupon type.
This keeps the logic clean and avoids long if-else chains everywhere.

##How the system works?

A coupon is stored in the DB with:

type,code,rules (as JSON)

When applying a coupon:-
Service loads coupon
Chooses the correct strategy implementation
Strategy checks if it's applicable
then strategy calculates discount
Service returns the final payable amount + discount.

##Assumptions

Product catalog is not managed here; products are passed directly in cart DTO.

Cart DTO always contains valid productId, price, and quantity.

Coupon details JSON is assumed valid and matches expected structure.

Only one coupon is applied at a time.

##Limitations

No authentication/authorization added.
Coupon rules are stored as plain JSON strings (could be improved using structured DB tables).
BxGy implementation currently works for one product at a time.
Does not handle usage limits, expiry dates.

These could be added in the future.

## Unimplemented / Future Enhancements

Things I thought of, but did not implement to keep this project within time:
BxGy for multiple product groups.
Direct product catalog integration.
Coupon expiry date and max usage per user.

## Technologies Used

Java 17
Spring Boot
JPA / Hibernate
H2 (in-memory DB for testing)

Strategy Design Pattern

## How to run locally

Pull project
Open in IntelliJ
Run MonkcommereApplication

Database URL (H2 console):

http://localhost:8080/h2-console
Use Postman to test APIs.


This project helped me understand how coupon rules can be modularized using the strategy pattern.
The system is flexible, and adding new coupon types is straightforward because the structure is already organized for it.

If I had more time, I would add expiry dates, usage limits, better rule validation.

