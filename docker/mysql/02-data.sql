INSERT INTO users
    (full_name,email,password,role)
VALUES
    ('admin', 'admin@test.com', '$2a$10$8QvJPWjbW.nHNK1ZFEU/OeXiq7qCrAVoCDETtjocCmhHdQzpM.YxW', 'ADMIN');

INSERT INTO authors
    (full_name,nationality,birth_date)
VALUES
    ('J.K. Rowling', 'British', '1965-07-31'),
    ('George Orwell', 'British', '1903-06-25'),
    ('Erich Gamma', 'Swiss', '1961-03-13');

INSERT INTO publishers
    (name,address,phone)
VALUES
    ('Penguin', 'London', '111111'),
    ('OReilly', 'USA', '222222'),
    ('Oxford', 'UK', '333333');

INSERT INTO categories
    (name)
VALUES
    ('Novel'),
    ('Programming'),
    ('Science');

INSERT INTO books
    (
    title,
    isbn,
    publish_year,
    quantity,
    available_quantity,
    author_id,
    publisher_id,
    category_id
    )
VALUES
    (
        'Harry Potter',
        '978111111',
        1997,
        10,
        8,
        1,
        1,
        1
),
    (
        '1984',
        '978222222',
        1949,
        5,
        5,
        2,
        1,
        1
),
    (
        'Design Patterns',
        '978333333',
        1994,
        4,
        4,
        3,
        2,
        2
);

INSERT INTO borrows
    (
    user_id,
    book_id,
    borrow_date,
    due_date,
    status
    )
VALUES
    (
        2,
        1,
        '2026-07-01',
        '2026-07-15',
        'BORROWED'
);