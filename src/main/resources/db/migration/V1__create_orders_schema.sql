CREATE TABLE orders (
    id BINARY(16) NOT NULL,
    status VARCHAR(32) NOT NULL,
    customer_name VARCHAR(200) NOT NULL,
    customer_email VARCHAR(255) NOT NULL,
    shipping_address VARCHAR(500) NOT NULL,
    billing_address VARCHAR(500) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    total_amount DECIMAL(19,2) NOT NULL,
    notes VARCHAR(1000) NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE INDEX idx_orders_customer_email ON orders (customer_email);
CREATE INDEX idx_orders_status ON orders (status);
CREATE INDEX idx_orders_created_at ON orders (created_at);

CREATE TABLE order_items (
    id BINARY(16) NOT NULL,
    order_id BINARY(16) NOT NULL,
    product_code VARCHAR(64) NOT NULL,
    product_name VARCHAR(200) NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(19,2) NOT NULL,
    line_total DECIMAL(19,2) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_order_items_order
        FOREIGN KEY (order_id)
        REFERENCES orders (id)
        ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE INDEX idx_order_items_order_id ON order_items (order_id);
