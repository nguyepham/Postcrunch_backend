CREATE SCHEMA IF NOT EXISTS postcrunch;

CREATE TABLE IF NOT EXISTS postcrunch.user (
    id CHAR(36) PRIMARY KEY NOT NULL,
    username VARCHAR(16) UNIQUE NOT NULL,
    password VARCHAR(40) NOT NULL,
    name VARCHAR(28),
    email VARCHAR(24),
    dob DATE,
    gender CHAR(1)
);

CREATE TABLE IF NOT EXISTS postcrunch.content (
    id CHAR(36) PRIMARY KEY NOT NULL,
    is_a_post BOOLEAN NOT NULL,
    num_reports SMALLINT UNSIGNED DEFAULT 0,
    created_at DATETIME DEFAULT NOW(),
    updated_at DATETIME DEFAULT NOW() ON UPDATE NOW(),
    text VARCHAR(3000),
    author_id CHAR(36),
    FOREIGN KEY (author_id) REFERENCES user(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS postcrunch.comment (
    id CHAR(36) PRIMARY KEY NOT NULL,
    target_id CHAR(36) NOT NULL,
    FOREIGN KEY (id) REFERENCES postcrunch.content(id) ON DELETE CASCADE,
    FOREIGN KEY (target_id) REFERENCES postcrunch.content(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS postcrunch.vote (
    id CHAR(36) PRIMARY KEY NOT NULL,
    is_up_vote BOOLEAN NOT NULL,
    created_at DATETIME DEFAULT NOW(),
    author_id CHAR(36),
    target_id CHAR(36) NOT NULL,
    FOREIGN KEY (author_id) REFERENCES user(id) ON DELETE SET NULL,
    FOREIGN KEY (target_id) REFERENCES content(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS postcrunch.report (
    id CHAR(36) PRIMARY KEY NOT NULL,
    created_at DATETIME DEFAULT NOW(),
    target_id CHAR(36) NOT NULL,
    FOREIGN KEY (target_id) REFERENCES content(id) ON DELETE CASCADE
);

-- Seeding users
INSERT INTO postcrunch.user (id, username, password, name, email, dob, gender) VALUES
    ('550e8400-e29b-41d4-a716-446655440000', 'user1', 'password1', 'John Doe', 'john@example.com', '1990-05-15', 'M'),
    ('660e8400-e29b-41d4-a716-446655440001', 'user2', 'password2', 'Jane Smith', 'jane@example.com', '1992-08-22', 'F'),
    ('770e8400-e29b-41d4-a716-446655440002', 'user3', 'password3', 'Sam Wilson', 'sam@example.com', '1995-03-10', 'M');

-- Seeding posts (is_a_post = TRUE)
INSERT INTO postcrunch.content (id, is_a_post, num_reports, created_at, updated_at, text, author_id) VALUES
    ('880e8400-e29b-41d4-a716-446655440003', TRUE, 0, NOW(), NOW(), 'This is the first post', '550e8400-e29b-41d4-a716-446655440000'),
    ('990e8400-e29b-41d4-a716-446655440004', TRUE, 1, NOW(), NOW(), 'Here is another post by user2', '660e8400-e29b-41d4-a716-446655440001');

-- Seeding comments (is_a_post = FALSE)
INSERT INTO postcrunch.content (id, is_a_post, num_reports, created_at, updated_at, text, author_id) VALUES
    ('a00e8400-e29b-41d4-a716-446655440005', FALSE, 0, NOW(), NOW(), 'This is a comment on the first post', '770e8400-e29b-41d4-a716-446655440002');

-- Linking comments to posts
INSERT INTO postcrunch.comment (id, target_id) VALUES
    ('a00e8400-e29b-41d4-a716-446655440005', '880e8400-e29b-41d4-a716-446655440003');  -- Comment on the first post

-- Seeding votes for content
INSERT INTO postcrunch.vote (id, is_up_vote, created_at, author_id, target_id) VALUES
    ('b10e8400-e29b-41d4-a716-446655440006', TRUE, NOW(), '550e8400-e29b-41d4-a716-446655440000', '880e8400-e29b-41d4-a716-446655440003'),  -- User1 upvotes first post
    ('c20e8400-e29b-41d4-a716-446655440007', FALSE, NOW(), '660e8400-e29b-41d4-a716-446655440001', '880e8400-e29b-41d4-a716-446655440003');  -- User2 downvotes first post

-- Seeding reports for content
INSERT INTO postcrunch.report (id, created_at, target_id) VALUES
    ('d30e8400-e29b-41d4-a716-446655440008', NOW(), '990e8400-e29b-41d4-a716-446655440004');  -- Report on the second post
