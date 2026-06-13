INSERT INTO orders (
    id,
    status,
    customer_name,
    customer_email,
    shipping_address,
    billing_address,
    currency,
    total_amount,
    notes,
    created_at,
    updated_at
) VALUES (
    UNHEX('11111111111111111111111111111111'),
    'PENDING',
    'John Doe',
    'john.doe@example.com',
    '123 Shipping Lane, Austin, TX',
    '456 Billing Road, Austin, TX',
    'USD',
    1299.97,
    'Leave the package at the front desk',
    '2026-06-13 09:15:00.000000',
    '2026-06-13 09:15:00.000000'
), (
    UNHEX('22222222222222222222222222222222'),
    'SHIPPED',
    'Priya Sharma',
    'priya.sharma@example.com',
    '77 Brigade Road, Bengaluru, KA',
    '88 Residency Road, Bengaluru, KA',
    'INR',
    18497.50,
    'Call before delivery',
    '2026-06-13 09:20:00.000000',
    '2026-06-13 10:05:00.000000'
);

INSERT INTO order_items (
    id,
    order_id,
    product_code,
    product_name,
    quantity,
    unit_price,
    line_total
) VALUES (
    UNHEX('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa'),
    UNHEX('11111111111111111111111111111111'),
    'SKU-LAP-001',
    'Laptop',
    1,
    1199.99,
    1199.99
), (
    UNHEX('bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb'),
    UNHEX('11111111111111111111111111111111'),
    'SKU-MSE-001',
    'Wireless Mouse',
    1,
    99.98,
    99.98
), (
    UNHEX('cccccccccccccccccccccccccccccccc'),
    UNHEX('22222222222222222222222222222222'),
    'SKU-PHN-009',
    'Smartphone',
    2,
    8999.00,
    17998.00
), (
    UNHEX('dddddddddddddddddddddddddddddddd'),
    UNHEX('22222222222222222222222222222222'),
    'SKU-CVR-002',
    'Phone Cover',
    5,
    99.90,
    499.50
);
