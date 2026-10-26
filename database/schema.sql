PRAGMA foreign_keys = ON;

DROP TABLE IF EXISTS "Order";
DROP TABLE IF EXISTS Event;

CREATE TABLE Event (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL CHECK(length(trim(name)) > 0),
    starts_at TEXT NOT NULL CHECK(starts_at IS date(starts_at))
);

CREATE TABLE "Order" (
    id INTEGER PRIMARY KEY,
    event_id INTEGER NOT NULL,
    FOREIGN KEY (event_id) REFERENCES Event(id)
);

CREATE INDEX idx_event_name ON Event(name);
CREATE INDEX idx_event_starts_at ON Event(starts_at);