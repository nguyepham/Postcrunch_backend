CREATE TABLE IF NOT EXISTS postcrunch.user_token (
    id CHAR(36) PRIMARY KEY NOT NULL,
    refresh_token VARCHAR(128),
    user_id CHAR(36) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES postcrunch.user(id)
)