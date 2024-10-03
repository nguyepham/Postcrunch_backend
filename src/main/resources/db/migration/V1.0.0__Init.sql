CREATE SCHEMA IF NOT EXISTS postcrunch;

CREATE TABLE IF NOT EXISTS postcrunch.user (
    id CHAR(36) PRIMARY KEY NOT NULL,
    username VARCHAR(16) UNIQUE NOT NULL,
    password VARCHAR(128) NOT NULL,
    first_name VARCHAR(28),
    last_name VARCHAR(28),
    email VARCHAR(24),
    dob DATE,
    gender CHAR(1)
);

CREATE TABLE IF NOT EXISTS postcrunch.content (
    id CHAR(36) PRIMARY KEY NOT NULL,
    content_type VARCHAR(10) NOT NULL,
    created_at DATETIME DEFAULT NOW(),
    updated_at DATETIME DEFAULT NOW() ON UPDATE NOW(),
    author_id CHAR(36),
    text VARCHAR(3000),
    FOREIGN KEY (author_id) REFERENCES user(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS postcrunch.post (
    id CHAR(36) PRIMARY KEY NOT NULL,
    title VARCHAR(150) NOT NULL DEFAULT 'No Title',
    FOREIGN KEY (id) REFERENCES content(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS postcrunch.comment (
    id CHAR(36) PRIMARY KEY NOT NULL,
    target_id CHAR(36) NOT NULL,
    FOREIGN KEY (target_id) REFERENCES postcrunch.content(id) ON DELETE CASCADE,
    FOREIGN KEY (id) REFERENCES content(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS postcrunch.vote (
    id CHAR(36) PRIMARY KEY NOT NULL,
    vote_type VARCHAR(10) NOT NULL,
    created_at DATETIME DEFAULT NOW(),
    author_id CHAR(36),
    target_id CHAR(36) NOT NULL,
    FOREIGN KEY (author_id) REFERENCES user(id) ON DELETE SET NULL,
    FOREIGN KEY (target_id) REFERENCES content(id) ON DELETE CASCADE
);

-- Seeding users
INSERT INTO postcrunch.user (id, username, password, first_name, last_name, email, dob, gender) VALUES
    ('1a2b3c4d-1234-5678-9101-112131415161', 'user1', '$2a$10$E08xiq6U7e5R27NwZ346x.LXcv6djyx8kViA5ullcMx8f.8f6gCqi', 'Alice', 'Smith', 'alice@example.com', '1990-01-01', 'F'),
    ('2a2b3c4d-2234-5678-9101-112131415162', 'user2', '$2a$10$oYcpCdchv0ETkKeh5jbEl.HB4KIUxjZlUxlsk4zZfUXAtvbGxLz9i', 'Bob', 'Johnson', 'bob@example.com', '1985-05-12', 'M'),
    ('3a2b3c4d-3234-5678-9101-112131415163', 'user3', '$2a$10$CG24x1qlEe36c4tQJZLI9u.E6FQwzZOPYGJjMHdxvrWONe.PaqjU2', 'Charlie', 'Brown', 'charlie@example.com', '1992-11-23', 'M'),
    ('4a2b3c4d-4234-5678-9101-112131415164', 'user4', '$2a$10$xgRs0PECrlkp.Kyw9GQLj.ezvktxn7EM2GqAYIZVBWirhIQvwkM5.', 'Diana', 'Prince', 'diana@example.com', '1995-07-07', 'F'),
    ('5a2b3c4d-5234-5678-9101-112131415165', 'user5', '$2a$10$g0qVxP3XPw/XAiLyNHWz0.8unOqxhdY23T1YzyIpj.6Uvsy9f7zYS', 'Eve', 'Adams', 'eve@example.com', '1998-09-15', 'F');

-- Seeding posts (content_type = 'POST')
INSERT INTO postcrunch.content (id, content_type, text, author_id, updated_at) VALUES
    ('1a2b3c4d-aaaa-5678-9101-112131415161', 'POST', 'This is the first post by Alice.', '1a2b3c4d-1234-5678-9101-112131415161', '2024-10-11 15:30:00'),
    ('2a2b3c4d-bbbb-5678-9101-112131415162', 'POST', 'Bob\'s thoughts on the latest tech.', '2a2b3c4d-2234-5678-9101-112131415162', '2024-10-11 17:30:00'),
    ('3a2b3c4d-cccc-5678-9101-112131415163', 'POST', 'Charlie\'s review of a movie.', '3a2b3c4d-3234-5678-9101-112131415163', '2024-10-11 19:30:00'),
    ('4a2b3c4d-dddd-5678-9101-112131415164', 'POST', 'Diana writes about her travel experiences.', '4a2b3c4d-4234-5678-9101-112131415164', '2024-10-11 21:30:00'),
    ('5a2b3c4d-eeee-5678-9101-112131415165', 'POST', 'Eve shares a recipe for a healthy meal.', '5a2b3c4d-5234-5678-9101-112131415165', '2024-10-11 23:30:00');

-- Seeding comments (content_type = 'COMMENT')
INSERT INTO postcrunch.content (id, content_type, text, author_id, updated_at) VALUES
('1a2b3c4d-1111-5678-9101-112131415161', 'COMMENT', 'Great post, Alice!', '2a2b3c4d-2234-5678-9101-112131415162', '2024-10-17 15:30:00'),
('2a2b3c4d-2222-5678-9101-112131415162', 'COMMENT', 'I totally agree, Bob!', '1a2b3c4d-1234-5678-9101-112131415161', '2024-10-18 15:30:00'),
('3a2b3c4d-3333-5678-9101-112131415163', 'COMMENT', 'Nice review, Charlie.', '5a2b3c4d-5234-5678-9101-112131415165', '2024-10-19 15:30:00'),
('4a2b3c4d-4444-5678-9101-112131415164', 'COMMENT', 'This is inspiring, Diana!', '3a2b3c4d-3234-5678-9101-112131415163', '2024-10-20 15:30:00'),
('5a2b3c4d-5555-5678-9101-112131415165', 'COMMENT', 'Eve, I\'ll try this recipe!', '4a2b3c4d-4234-5678-9101-112131415164', '2024-10-21 15:30:00');

INSERT INTO postcrunch.post (id, title) VALUES
    ('1a2b3c4d-aaaa-5678-9101-112131415161', 'First Post by Alice'),
    ('2a2b3c4d-bbbb-5678-9101-112131415162', 'Tech Thoughts by Bob'),
    ('3a2b3c4d-cccc-5678-9101-112131415163', 'Movie Review by Charlie'),
    ('4a2b3c4d-dddd-5678-9101-112131415164', 'Diana\'s Travel Diary'),
    ('5a2b3c4d-eeee-5678-9101-112131415165', 'Eve\'s Healthy Meal Recipe');

INSERT INTO postcrunch.comment (id, target_id) VALUES
    ('1a2b3c4d-1111-5678-9101-112131415161', '1a2b3c4d-aaaa-5678-9101-112131415161'),
    ('2a2b3c4d-2222-5678-9101-112131415162', '2a2b3c4d-bbbb-5678-9101-112131415162'),
    ('3a2b3c4d-3333-5678-9101-112131415163', '3a2b3c4d-cccc-5678-9101-112131415163'),
    ('4a2b3c4d-4444-5678-9101-112131415164', '4a2b3c4d-dddd-5678-9101-112131415164'),
    ('5a2b3c4d-5555-5678-9101-112131415165', '5a2b3c4d-eeee-5678-9101-112131415165');

-- Seeding votes for content
INSERT INTO postcrunch.vote (id, vote_type, author_id, target_id) VALUES
    ('1a2b3c4d-6666-5678-9101-112131415160', 'UP', '2a2b3c4d-2234-5678-9101-112131415162', '1a2b3c4d-aaaa-5678-9101-112131415161'),
    ('2a2b3c4d-7777-5678-9101-112131415162', 'DOWN', '1a2b3c4d-1234-5678-9101-112131415161', '2a2b3c4d-bbbb-5678-9101-112131415162'),
    ('3a2b3c4d-8888-5678-9101-112131415163', 'UP', '4a2b3c4d-4234-5678-9101-112131415164', '3a2b3c4d-cccc-5678-9101-112131415163'),
    ('4a2b3c4d-9999-5678-9101-112131415164', 'UP', '3a2b3c4d-3234-5678-9101-112131415163', '4a2b3c4d-dddd-5678-9101-112131415164'),
    ('5a2b3c4d-aaaa-5678-9101-112131415165', 'DOWN', '5a2b3c4d-5234-5678-9101-112131415165', '5a2b3c4d-eeee-5678-9101-112131415165');