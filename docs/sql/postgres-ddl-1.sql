CREATE TABLE blogs (
                       id VARCHAR(36) PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       content TEXT NOT NULL,
                       author VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP NOT NULL,
                       updated_at TIMESTAMP NOT NULL,
                       status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
                       version BIGINT NOT NULL DEFAULT 1,

                       CONSTRAINT blogs_status_check CHECK (status IN ('ACTIVE', 'DELETED'))
);

CREATE INDEX idx_blogs_author ON blogs(author);
CREATE INDEX idx_blogs_created_at ON blogs(created_at);
CREATE INDEX idx_blogs_title ON blogs(title);
