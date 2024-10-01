-- Seeding posts (content_type = 'POST')
INSERT INTO postcrunch.content (id, content_type, text, author_id, updated_at) VALUES
    ('1a2b3c4d-aaaa-5678-9101-222131415161', 'POST', 'Alice shares her thoughts on technology.', '1a2b3c4d-1234-5678-9101-112131415161', '2024-10-11 05:30:00'),
    ('1a2b3c4d-aaaa-5678-9101-222131415162', 'POST', 'Alice writes about her favorite books.', '1a2b3c4d-1234-5678-9101-112131415161', '2024-10-11 07:30:00'),
    ('1a2b3c4d-aaaa-5678-9101-222131415163', 'POST', 'Alice discusses her workout routine.', '1a2b3c4d-1234-5678-9101-112131415161', '2024-10-11 09:30:00'),
    ('1a2b3c4d-aaaa-5678-9101-222131415164', 'POST', 'Alice gives tips on productivity.', '1a2b3c4d-1234-5678-9101-112131415161', '2024-10-11 11:30:00'),
    ('1a2b3c4d-aaaa-5678-9101-222131415165', 'POST', 'Alice reviews her recent travel adventure.', '1a2b3c4d-1234-5678-9101-112131415161', '2024-10-11 13:30:00');

INSERT INTO postcrunch.post (id, title) VALUES
    ('1a2b3c4d-aaaa-5678-9101-222131415161', 'Alice\'s Thoughts on Technology'),
    ('1a2b3c4d-aaaa-5678-9101-222131415162', 'Alice\'s Favorite Books'),
    ('1a2b3c4d-aaaa-5678-9101-222131415163', 'Alice\'s Workout Routine'),
    ('1a2b3c4d-aaaa-5678-9101-222131415164', 'Alice\'s Productivity Tips'),
    ('1a2b3c4d-aaaa-5678-9101-222131415165', 'Alice\'s Travel Adventure');

-- Seeding comments (content_type = 'COMMENT')
INSERT INTO postcrunch.content (id, content_type, text, author_id, updated_at) VALUES
    ('4a2b3c4d-6666-5678-9101-112131415164', 'COMMENT', 'This recipe is amazing, Eve!', '4a2b3c4d-4234-5678-9101-112131415164', '2024-10-12 15:30:00'),
    ('4a2b3c4d-7777-5678-9101-112131415164', 'COMMENT', 'I tried this and it turned out great!', '4a2b3c4d-4234-5678-9101-112131415164', '2024-10-13 15:30:00'),
    ('4a2b3c4d-8888-5678-9101-112131415164', 'COMMENT', 'Thanks for sharing, Eve!', '4a2b3c4d-4234-5678-9101-112131415164', '2024-10-14 15:30:00'),
    ('4a2b3c4d-9999-5678-9101-112131415164', 'COMMENT', 'I\'ll recommend this to my friends!', '4a2b3c4d-4234-5678-9101-112131415164', '2024-10-15 15:30:00'),
    ('4a2b3c4d-aaaa-5678-9101-112131415164', 'COMMENT', 'Looking forward to more recipes from you!', '4a2b3c4d-4234-5678-9101-112131415164', '2024-10-16 15:30:00');

INSERT INTO postcrunch.comment (id, target_id) VALUES
    ('4a2b3c4d-6666-5678-9101-112131415164', '5a2b3c4d-eeee-5678-9101-112131415165'),
    ('4a2b3c4d-7777-5678-9101-112131415164', '5a2b3c4d-eeee-5678-9101-112131415165'),
    ('4a2b3c4d-8888-5678-9101-112131415164', '5a2b3c4d-eeee-5678-9101-112131415165'),
    ('4a2b3c4d-9999-5678-9101-112131415164', '5a2b3c4d-eeee-5678-9101-112131415165'),
    ('4a2b3c4d-aaaa-5678-9101-112131415164', '5a2b3c4d-eeee-5678-9101-112131415165');