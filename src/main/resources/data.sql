SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE coupon_usage_history;
TRUNCATE TABLE issued_coupon;
TRUNCATE TABLE coupon;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO coupon (name, description, coupon_type, status, discount_value, min_order_amount, max_discount_amount,
                    total_quantity, issued_quantity, valid_days, start_date, end_date)
VALUES ('선착순 10% 할인 쿠폰',
        '1,000명이 동시에 요청하지만 100개만 발급 가능',
        'PERCENTAGE',
        'ACTIVE',
        10,
        10000,
        5000,
        100,
        0,
        7,
        NOW() - INTERVAL 1 HOUR,
        NOW() + INTERVAL 30 DAY);
