CREATE SCHEMA IF NOT EXISTS postcrunch;

CREATE TABLE IF NOT EXISTS postcrunch.user (
    id CHAR(36) PRIMARY KEY NOT NULL,
    username VARCHAR(16) UNIQUE NOT NULL,
    password VARCHAR(40) NOT NULL,
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

CREATE TABLE IF NOT EXISTS postcrunch.report (
    id CHAR(36) PRIMARY KEY NOT NULL,
    reason VARCHAR(16) NOT NULL DEFAULT 'OTHER',
    created_at DATETIME DEFAULT NOW(),
    author_id CHAR(36),
    target_id CHAR(36) NOT NULL,
    FOREIGN KEY (author_id) REFERENCES user(id) ON DELETE SET NULL,
    FOREIGN KEY (target_id) REFERENCES content(id) ON DELETE CASCADE
);

-- Seeding users
INSERT INTO postcrunch.user (id, username, password, first_name, last_name, email, dob, gender) VALUES
    ('1a2b3c4d-1234-5678-9101-112131415161', 'user1', 'password1', 'Alice', 'Smith', 'alice@example.com', '1990-01-01', 'F'),
    ('2a2b3c4d-2234-5678-9101-112131415162', 'user2', 'password2', 'Bob', 'Johnson', 'bob@example.com', '1985-05-12', 'M'),
    ('3a2b3c4d-3234-5678-9101-112131415163', 'user3', 'password3', 'Charlie', 'Brown', 'charlie@example.com', '1992-11-23', 'M'),
    ('4a2b3c4d-4234-5678-9101-112131415164', 'user4', 'password4', 'Diana', 'Prince', 'diana@example.com', '1995-07-07', 'F'),
    ('5a2b3c4d-5234-5678-9101-112131415165', 'user5', 'password5', 'Eve', 'Adams', 'eve@example.com', '1998-09-15', 'F');

-- Seeding posts (content_type = 'POST')
INSERT INTO postcrunch.content (id, content_type, text, author_id, updated_at) VALUES
    ('1a2b3c4d-aaaa-5678-9101-222131415161', 'POST', 'Alice shares her thoughts on technology.', '1a2b3c4d-1234-5678-9101-112131415161', '2024-10-11 05:30:00'),
    ('1a2b3c4d-aaaa-5678-9101-222131415162', 'POST', 'Alice writes about her favorite books.', '1a2b3c4d-1234-5678-9101-112131415161', '2024-10-11 07:30:00'),
    ('1a2b3c4d-aaaa-5678-9101-222131415163', 'POST', 'Alice discusses her workout routine.', '1a2b3c4d-1234-5678-9101-112131415161', '2024-10-11 09:30:00'),
    ('1a2b3c4d-aaaa-5678-9101-222131415164', 'POST', 'Alice gives tips on productivity.', '1a2b3c4d-1234-5678-9101-112131415161', '2024-10-11 11:30:00'),
    ('1a2b3c4d-aaaa-5678-9101-222131415165', 'POST', 'Alice reviews her recent travel adventure.', '1a2b3c4d-1234-5678-9101-112131415161', '2024-10-11 13:30:00'),
    ('1a2b3c4d-aaaa-5678-9101-112131415161', 'POST', 'This is the first post by Alice.', '1a2b3c4d-1234-5678-9101-112131415161', '2024-10-11 15:30:00'),
    ('2a2b3c4d-bbbb-5678-9101-112131415162', 'POST', 'Bob\'s thoughts on the latest tech.', '2a2b3c4d-2234-5678-9101-112131415162', '2024-10-11 17:30:00'),
    ('3a2b3c4d-cccc-5678-9101-112131415163', 'POST', 'Charlie\'s review of a movie.', '3a2b3c4d-3234-5678-9101-112131415163', '2024-10-11 19:30:00'),
    ('4a2b3c4d-dddd-5678-9101-112131415164', 'POST', 'Diana writes about her travel experiences.', '4a2b3c4d-4234-5678-9101-112131415164', '2024-10-11 21:30:00'),
    ('5a2b3c4d-eeee-5678-9101-112131415165', 'POST', 'Eve shares a recipe for a healthy meal.', '5a2b3c4d-5234-5678-9101-112131415165', '2024-10-11 23:30:00');

-- Seeding comments (content_type = 'COMMENT')
INSERT INTO postcrunch.content (id, content_type, text, author_id, updated_at) VALUES
('4a2b3c4d-6666-5678-9101-112131415164', 'COMMENT', 'This recipe is amazing, Eve!', '4a2b3c4d-4234-5678-9101-112131415164', '2024-10-12 15:30:00'),
('4a2b3c4d-7777-5678-9101-112131415164', 'COMMENT', 'I tried this and it turned out great!', '4a2b3c4d-4234-5678-9101-112131415164', '2024-10-13 15:30:00'),
('4a2b3c4d-8888-5678-9101-112131415164', 'COMMENT', 'Thanks for sharing, Eve!', '4a2b3c4d-4234-5678-9101-112131415164', '2024-10-14 15:30:00'),
('4a2b3c4d-9999-5678-9101-112131415164', 'COMMENT', 'I\'ll recommend this to my friends!', '4a2b3c4d-4234-5678-9101-112131415164', '2024-10-15 15:30:00'),
('4a2b3c4d-aaaa-5678-9101-112131415164', 'COMMENT', 'Looking forward to more recipes from you!', '4a2b3c4d-4234-5678-9101-112131415164', '2024-10-16 15:30:00'),
('1a2b3c4d-1111-5678-9101-112131415161', 'COMMENT', 'Great post, Alice!', '2a2b3c4d-2234-5678-9101-112131415162', '2024-10-17 15:30:00'),
('2a2b3c4d-2222-5678-9101-112131415162', 'COMMENT', 'I totally agree, Bob!', '1a2b3c4d-1234-5678-9101-112131415161', '2024-10-18 15:30:00'),
('3a2b3c4d-3333-5678-9101-112131415163', 'COMMENT', 'Nice review, Charlie.', '5a2b3c4d-5234-5678-9101-112131415165', '2024-10-19 15:30:00'),
('4a2b3c4d-4444-5678-9101-112131415164', 'COMMENT', 'This is inspiring, Diana!', '3a2b3c4d-3234-5678-9101-112131415163', '2024-10-20 15:30:00'),
('5a2b3c4d-5555-5678-9101-112131415165', 'COMMENT', 'Eve, I\'ll try this recipe!', '4a2b3c4d-4234-5678-9101-112131415164', '2024-10-21 15:30:00');

INSERT INTO postcrunch.post (id, title) VALUES
    ('1a2b3c4d-aaaa-5678-9101-222131415161', 'Alice\'s Thoughts on Technology'),
    ('1a2b3c4d-aaaa-5678-9101-222131415162', 'Alice\'s Favorite Books'),
    ('1a2b3c4d-aaaa-5678-9101-222131415163', 'Alice\'s Workout Routine'),
    ('1a2b3c4d-aaaa-5678-9101-222131415164', 'Alice\'s Productivity Tips'),
    ('1a2b3c4d-aaaa-5678-9101-222131415165', 'Alice\'s Travel Adventure'),
    ('1a2b3c4d-aaaa-5678-9101-112131415161', 'First Post by Alice'),
    ('2a2b3c4d-bbbb-5678-9101-112131415162', 'Tech Thoughts by Bob'),
    ('3a2b3c4d-cccc-5678-9101-112131415163', 'Movie Review by Charlie'),
    ('4a2b3c4d-dddd-5678-9101-112131415164', 'Diana\'s Travel Diary'),
    ('5a2b3c4d-eeee-5678-9101-112131415165', 'Eve\'s Healthy Meal Recipe');

INSERT INTO postcrunch.comment (id, target_id) VALUES
    ('4a2b3c4d-6666-5678-9101-112131415164', '5a2b3c4d-eeee-5678-9101-112131415165'),
    ('4a2b3c4d-7777-5678-9101-112131415164', '5a2b3c4d-eeee-5678-9101-112131415165'),
    ('4a2b3c4d-8888-5678-9101-112131415164', '5a2b3c4d-eeee-5678-9101-112131415165'),
    ('4a2b3c4d-9999-5678-9101-112131415164', '5a2b3c4d-eeee-5678-9101-112131415165'),
    ('4a2b3c4d-aaaa-5678-9101-112131415164', '5a2b3c4d-eeee-5678-9101-112131415165'),
    ('1a2b3c4d-1111-5678-9101-112131415161', '1a2b3c4d-aaaa-5678-9101-112131415161'),
    ('2a2b3c4d-2222-5678-9101-112131415162', '2a2b3c4d-bbbb-5678-9101-112131415162'),
    ('3a2b3c4d-3333-5678-9101-112131415163', '3a2b3c4d-cccc-5678-9101-112131415163'),
    ('4a2b3c4d-4444-5678-9101-112131415164', '4a2b3c4d-dddd-5678-9101-112131415164'),
    ('5a2b3c4d-5555-5678-9101-112131415165', '5a2b3c4d-eeee-5678-9101-112131415165');

-- Seeding votes for content
INSERT INTO postcrunch.vote (id, vote_type, author_id, target_id) VALUES
    ('1a2b3c4d-6666-5678-9101-112131415161', 'UP', '2a2b3c4d-2234-5678-9101-112131415162', '1a2b3c4d-aaaa-5678-9101-112131415161'),
    ('2a2b3c4d-7777-5678-9101-112131415162', 'DOWN', '1a2b3c4d-1234-5678-9101-112131415161', '2a2b3c4d-bbbb-5678-9101-112131415162'),
    ('3a2b3c4d-8888-5678-9101-112131415163', 'UP', '4a2b3c4d-4234-5678-9101-112131415164', '3a2b3c4d-cccc-5678-9101-112131415163'),
    ('4a2b3c4d-9999-5678-9101-112131415164', 'UP', '3a2b3c4d-3234-5678-9101-112131415163', '4a2b3c4d-dddd-5678-9101-112131415164'),
    ('5a2b3c4d-aaaa-5678-9101-112131415165', 'DOWN', '5a2b3c4d-5234-5678-9101-112131415165', '5a2b3c4d-eeee-5678-9101-112131415165');

-- Seeding reports for content
INSERT INTO postcrunch.report (id, reason, author_id, target_id) VALUES
('1a2b3c4d-bbbb-5678-9101-112131415161', 'SPAM', '2a2b3c4d-2234-5678-9101-112131415162', '1a2b3c4d-aaaa-5678-9101-112131415161'),
('2a2b3c4d-cccc-5678-9101-112131415162', 'OTHER', '1a2b3c4d-1234-5678-9101-112131415161', '5a2b3c4d-5555-5678-9101-112131415165');
